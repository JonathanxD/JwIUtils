/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.text.localizer;

import com.github.jonathanxd.iutils.localization.Locale;
import com.github.jonathanxd.iutils.localization.LocaleManager;
import com.github.jonathanxd.iutils.recursion.Element;
import com.github.jonathanxd.iutils.recursion.Elements;
import com.github.jonathanxd.iutils.text.ArgsAppliedText;
import com.github.jonathanxd.iutils.text.CapitalizeComponent;
import com.github.jonathanxd.iutils.text.Color;
import com.github.jonathanxd.iutils.text.DecapitalizeComponent;
import com.github.jonathanxd.iutils.text.LocalizableComponent;
import com.github.jonathanxd.iutils.text.StringComponent;
import com.github.jonathanxd.iutils.text.Style;
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.VariableComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Fast text localizer.
 *
 * This localizer does not use recursion and is final because is not intended for inheritance. Color
 * support is available through function.
 */
public final class FastTextLocalizer extends AbstractTextLocalizer {

    private final Function<Color, TextComponent> colorTransformer;
    private final Function<Style, TextComponent> styleTransformer;
    private final Function<TextComponent, TextComponent> additionalTransformer;

    public FastTextLocalizer(LocaleManager localeManager,
                             Locale defaultLocale,
                             Locale locale,
                             Function<Color, TextComponent> colorTransformer,
                             Function<Style, TextComponent> styleTransformer) {
        this(localeManager, defaultLocale, locale, colorTransformer, styleTransformer, Function.identity());
    }

    public FastTextLocalizer(LocaleManager localeManager,
                             Locale defaultLocale,
                             Locale locale,
                             Function<Color, TextComponent> colorTransformer,
                             Function<Style, TextComponent> styleTransformer,
                             Function<TextComponent, TextComponent> additionalTransformer) {
        super(localeManager, defaultLocale, locale);
        this.colorTransformer = colorTransformer;
        this.styleTransformer = styleTransformer;
        this.additionalTransformer = additionalTransformer;
    }

    public FastTextLocalizer(LocaleManager localeManager,
                             Locale defaultLocale,
                             Locale locale) {
        this(localeManager, defaultLocale, locale, c -> Text.single(""), s -> Text.single(""));
    }

    public FastTextLocalizer(LocaleManager localeManager,
                             Locale defaultLocale) {
        this(localeManager, defaultLocale, defaultLocale);
    }

    public Function<Color, TextComponent> getColorTransformer() {
        return this.colorTransformer;
    }

    public Function<Style, TextComponent> getStyleTransformer() {
        return this.styleTransformer;
    }

    public Function<TextComponent, TextComponent> getAdditionalTransformer() {
        return this.additionalTransformer;
    }

    @Override
    public String localize(TextComponent textComponent, Map<String, TextComponent> args, Locale locale) {
        StringBuilder result = new StringBuilder();
        this.localize(textComponent, args, locale, result);
        return result.toString();
    }

    private void localize(TextComponent textComponent,
                          Map<String, TextComponent> args,
                          Locale locale,
                          StringBuilder result) {
        Elements<TextComponent> components = new Elements<>();
        components.first = new Element<>(textComponent);
        int nextMode = NORMAL;
        Map<TextComponent, Map<String, TextComponent>> privateArgs = new HashMap<>();

        Element<TextComponent> elem;
        while ((elem = components.nextElement()) != null) {
            TextComponent next = elem.value;
            if (next instanceof Color) {
                components.insert(new Element<>(this.getColorTransformer().apply((Color) next)));
            } else if (next instanceof Style) {
                components.insert(new Element<>(this.getStyleTransformer().apply((Style) next)));
            } else if (next instanceof StringComponent) {
                String s = ((StringComponent) next).getText();

                if (!s.isEmpty()) {
                    switch (nextMode) {
                        case UPPER: {
                            s = Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length());
                            nextMode = NORMAL;
                            break;
                        }
                        case LOWER: {
                            s = Character.toLowerCase(s.charAt(0)) + s.substring(1, s.length());
                            nextMode = NORMAL;
                            break;
                        }
                    }
                }
                result.append(s);
            } else if (next instanceof CapitalizeComponent) {
                TextComponent component = ((CapitalizeComponent) next).getTextComponent();

                if (privateArgs.containsKey(next))
                    privateArgs.put(component, privateArgs.get(next));

                components.insert(new Element<>(component));
                nextMode = UPPER;
            } else if (next instanceof DecapitalizeComponent) {
                TextComponent component = ((DecapitalizeComponent) next).getTextComponent();

                if (privateArgs.containsKey(next))
                    privateArgs.put(component, privateArgs.get(next));

                components.insert(new Element<>(component));
                nextMode = LOWER;
            } else if (next instanceof VariableComponent) {
                String variable = ((VariableComponent) next).getVariable();
                TextComponent component = args.get(variable);

                if (component == null && privateArgs.containsKey(next))
                    component = privateArgs.get(next).get(variable);

                if (component != null) {
                    if (privateArgs.containsKey(next))
                        privateArgs.put(component, privateArgs.get(next));

                    components.insert(new Element<>(component)); // Capitalize or decapitalize normally
                } else {
                    components.insert(new Element<>(Text.single("$" + variable)));
                }
            } else if (next instanceof LocalizableComponent) {
                LocalizableComponent localizableComponent = (LocalizableComponent) next;
                String componentLocalization = localizableComponent.getLocalization();

                String localeStr = localizableComponent.getLocale();
                Locale localLocale = localeStr != null
                        ? this.getLocaleManager().getRequiredLocale(localeStr)
                        : null;

                Locale toUse = locale != null ? locale : localLocale != null ? localLocale : this.getLocale();

                TextComponent localization =
                        toUse.getLocalizationManager().getLocalization(componentLocalization);

                Locale current = this.getLocale();
                if (localization == null && toUse != current)
                    current.getLocalizationManager().getLocalization(componentLocalization);

                if (localization == null)
                    localization = this.getDefaultLocale().getLocalizationManager()
                            .getLocalization(componentLocalization);

                if (localization == null)
                    localization = Text.single(componentLocalization);

                if (privateArgs.containsKey(next))
                    privateArgs.put(localization, privateArgs.get(next));

                components.insert(new Element<>(localization));
            } else if (next instanceof ArgsAppliedText) {
                ArgsAppliedText argsAppliedText = (ArgsAppliedText) next;
                TextComponent component = argsAppliedText.getComponent();
                Map<String, TextComponent> textArgs = new HashMap<>();

                if (privateArgs.containsKey(next))
                    textArgs.putAll(privateArgs.get(next));

                textArgs.putAll(argsAppliedText.getArgs());

                privateArgs.put(component, textArgs);
                components.insert(new Element<>(component));
            } else if (next instanceof Text) {
                Element<TextComponent> element = null;
                Element<TextComponent> last = null;
                Map<String, TextComponent> privateArgsF = null;

                if (privateArgs.containsKey(next))
                    privateArgsF = privateArgs.get(next);

                for (TextComponent component : ((Text) next).getComponents()) {
                    if (privateArgsF != null)
                        privateArgs.put(component, privateArgsF);

                    Element<TextComponent> toSet = new Element<>(component);
                    if (element == null) {
                        element = toSet;
                    } else {
                        last.next = toSet;
                    }

                    last = toSet;
                }

                if (element != null) {
                    components.insert(element, last);
                }
            } else {
                TextComponent apply = this.getAdditionalTransformer().apply(next);

                if (apply == next) { // Identity
                    components.insert(new Element<>(Text.single(apply.toString())));
                } else {
                    components.insert(new Element<>(apply));
                }
            }

            privateArgs.remove(next);
        }

    }

    static final int NORMAL = 0;
    static final int UPPER = 1;
    static final int LOWER = 2;

}
