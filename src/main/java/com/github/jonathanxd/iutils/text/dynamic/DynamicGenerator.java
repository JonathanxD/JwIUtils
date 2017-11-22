/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

                texts.put(method, new MethodText(Text.localizable(section), args));
            }
        }

        return texts;
    }

    private static String getSection(String[] sections) {
        return Arrays.stream(sections).collect(Collectors.joining("."));
    }

    private static class Handler implements InvocationHandler {
        private final Class<?> itf;
        private final String defaultSection;
        private final Map<Method, MethodText> methodTexts;
        private static final Object[] EMPTY_ARGS = new Object[0];

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

                if (argNames.length == 0)
                    return methodText.getComponent();

                Map<String, TextComponent> textArgs = new HashMap<>();

                for (int i = 0; i < args.length; i++) {
                    String name = argNames[i];
                    Object arg = args[i];

                    if (arg instanceof TextComponent)
                        textArgs.put(name, (TextComponent) arg);
                    else if (arg instanceof String)
                        textArgs.put(name, Text.of((String) arg));
                    else
                        textArgs.put(name, Text.of(arg.toString()));

                }

                return methodText.getComponent().apply(textArgs);
            }

            return null;
        }
    }

    private static final class MethodText {
        private final TextComponent component;
        private final String[] args;

        private MethodText(TextComponent component, String[] args) {
            this.component = component;
            this.args = args;
        }

        public TextComponent getComponent() {
            return this.component;
        }

        public String[] getArgs() {
            return this.args;
        }
    }
}
