/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.jonathanxd.iutils.text.dynamic;

import com.github.jonathanxd.iutils.annotation.Named;
import com.github.jonathanxd.iutils.function.Functions;
import com.github.jonathanxd.iutils.localization.Locale;
import com.github.jonathanxd.iutils.matching.When;
import com.github.jonathanxd.iutils.text.ArgsAppliedText;
import com.github.jonathanxd.iutils.text.LocalizableComponent;
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.localizer.TextLocalizer;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class DynamicGenerator {
    private DynamicGenerator() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    public static <T> T generate(Class<T> itf) {
        if (!itf.isInterface())
            throw new IllegalArgumentException("Provided class is not an interface.");

        return (T) Proxy.newProxyInstance(itf.getClassLoader(), new Class[]{itf}, new Handler(itf));
    }

    private static Map<Method, MethodText> resolveTexts(String defaultSection, Class<?> itf) {
        Map<Method, MethodText> texts = new HashMap<>();

        for (Method method : itf.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Section.class)
                    && Arrays.stream(method.getParameters())
                    .allMatch(parameter -> parameter.isAnnotationPresent(Named.class))) {

                String[] args = new String[method.getParameterCount()];
                Parameter[] parameters = method.getParameters();

                for (int i = 0; i < args.length; i++) {
                    args[i] = parameters[i].getDeclaredAnnotation(Named.class).value();
                }

                String section = defaultSection + getSection(method.getDeclaredAnnotation(Section.class).value());
                LocalizableComponent localizable = Text.localizable(section);

                Function<TextComponent, Object> stringResolveFunc = StringResolve::new;
                Function<TextComponent, Object> componentsResolveFunc =
                        l -> new ComponentsResolve(section,
                                l instanceof ArgsAppliedText ? ((ArgsAppliedText) l).getArgs() : Collections.emptyMap());
                Function<TextComponent, Object> componentResolveFunc = ComponentResolve::new;
                Function<TextComponent, Object> self = x -> x;

                Function<TextComponent, Object> resolve =
                        When.When(method.getReturnType(),
                                When.EqualsTo(Resolve.Str.class, f -> stringResolveFunc),
                                When.EqualsTo(Resolve.Components.class, f -> componentsResolveFunc),
                                When.EqualsTo(TextComponent.class, f -> self),
                                When.Else(f -> componentResolveFunc)
                        ).evaluate().getValue();


                if (args.length == 0)
                    resolve = Functions.once(resolve);

                texts.put(method, new MethodText(localizable, args, resolve));
            }
        }

        return texts;
    }

    private static String getSection(String[] sections) {
        return Arrays.stream(sections).collect(Collectors.joining("."));
    }

    private static class Handler implements InvocationHandler {
        private static final Object[] EMPTY_ARGS = new Object[0];
        private final Class<?> itf;
        private final String defaultSection;
        private final Map<Method, MethodText> methodTexts;

        private Handler(Class<?> itf) {
            this.itf = itf;
            Section section = itf.getDeclaredAnnotation(Section.class);
            this.defaultSection = section == null ? "" : DynamicGenerator.getSection(section.value());
            this.methodTexts = DynamicGenerator.resolveTexts(this.defaultSection, this.itf);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            MethodText methodText = this.methodTexts.get(method);

            if (args == null) {
                args = EMPTY_ARGS;
            }

            if (methodText != null) {
                String[] argNames = methodText.getArgs();

                if (argNames.length != args.length)
                    throw new IllegalStateException("Mismatch arguments size for '" + method + "', invoked with '" + args.length + "' arguments, but only '" + argNames.length + "' was found.");

                if (argNames.length == 0) {
                    return methodText.getResolveFunc().apply(methodText.getComponent());
                } else {

                    Map<String, TextComponent> textArgs = new HashMap<>();

                    for (int i = 0; i < args.length; i++) {
                        String name = argNames[i];
                        Object arg = args[i];

                        if (arg instanceof TextComponent)
                            textArgs.put(name, (TextComponent) arg);
                        else if (arg instanceof String)
                            textArgs.put(name, Text.of(arg));
                        else
                            textArgs.put(name, Text.of(arg.toString()));
                    }

                    TextComponent apply = methodText.getComponent().apply(textArgs);

                    return methodText.getResolveFunc().apply(apply);
                }
            }

            return null;
        }
    }

    private static final class MethodText {
        private final TextComponent component;
        private final String[] args;
        private final Function<TextComponent, Object> resolveFunc;

        private MethodText(TextComponent component, String[] args,
                           Function<TextComponent, Object> resolveFunc) {
            this.component = component;
            this.args = args;
            this.resolveFunc = resolveFunc;
        }

        public TextComponent getComponent() {
            return this.component;
        }

        public String[] getArgs() {
            return this.args;
        }

        public Function<TextComponent, Object> getResolveFunc() {
            return this.resolveFunc;
        }
    }

    private static final class ComponentResolve extends Resolve.Component {
        private final TextComponent component;

        private ComponentResolve(TextComponent component) {
            this.component = component;
        }

        @NotNull
        @Override
        public TextComponent resolve(@NotNull TextLocalizer localizer) {
            return this.component;
        }
    }

    private static final class StringResolve extends Resolve.Str {

        private final TextComponent component;

        private StringResolve(TextComponent component) {
            this.component = component;
        }

        @Override
        public @NotNull String resolve(@NotNull TextLocalizer localizer) {
            return localizer.localize(this.component);
        }

        @Override
        public @NotNull String resolve(@NotNull TextLocalizer localizer,
                                       @NotNull Locale locale) {
            return localizer.localize(this.component, locale);
        }

        @Override
        public @NotNull String resolve(@NotNull TextLocalizer localizer,
                                       @NotNull Map<String, TextComponent> args) {
            return localizer.localize(this.component, args);
        }

        @Override
        public @NotNull String resolve(@NotNull TextLocalizer localizer,
                                       @NotNull Map<String, TextComponent> args,
                                       @NotNull Locale locale) {
            return localizer.localize(this.component, args, locale);
        }
    }

    private static final class ComponentsResolve extends Resolve.Components {
        private final String path;
        private final Map<String, TextComponent> args;

        private ComponentsResolve(String path,
                                  Map<String, TextComponent> args) {
            this.path = path;
            this.args = args;
        }

        @NotNull
        @Override
        public List<TextComponent> resolve(@NotNull TextLocalizer localizer) {
            Locale current = localizer.getLocale();
            Locale def = localizer.getDefaultLocale();

            List<TextComponent> localized = current.getLocalizationManager().getLocalizations(this.path);

            if (localized.isEmpty())
                localized = def.getLocalizationManager().getLocalizations(this.path);

            if (!args.isEmpty()) {
                return localized.stream().map(f -> f.apply(args)).collect(Collectors.toList());
            }

            return localized;
        }

        @NotNull
        @Override
        public List<TextComponent> resolve(@NotNull TextLocalizer localizer, @NotNull Locale locale) {
            List<TextComponent> localizations = locale.getLocalizationManager().getLocalizations(this.path);

            if (localizations.isEmpty()) {
                localizations = this.resolve(localizer);
            }

            return localizations;
        }

        @NotNull
        @Override
        public List<TextComponent> resolve(@NotNull TextLocalizer localizer,
                                           @NotNull Map<String, TextComponent> args,
                                           @NotNull Locale locale) {
            return this.resolve(localizer, locale).stream()
                    .map(f -> f.apply(args))
                    .collect(Collectors.toList());
        }

        @NotNull
        @Override
        public List<TextComponent> resolve(@NotNull TextLocalizer localizer,
                                           @NotNull Map<String, TextComponent> args) {
            return this.resolve(localizer).stream()
                    .map(f -> f.apply(args))
                    .collect(Collectors.toList());
        }
    }
}
