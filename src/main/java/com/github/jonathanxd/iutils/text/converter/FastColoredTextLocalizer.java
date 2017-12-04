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
package com.github.jonathanxd.iutils.text.converter;

import com.github.jonathanxd.iutils.localization.Locale;
import com.github.jonathanxd.iutils.localization.LocaleManager;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Fast text localizer.
 *
 * This localizer does not use recursion and is final because is not intended for inheritance. Color
 * support is available through function.
 */
public final class FastColoredTextLocalizer extends AbstractTextLocalizer {

    private final Function<Color, TextComponent> colorTransformer;
    private final Function<Style, TextComponent> styleTransformer;

    public FastColoredTextLocalizer(LocaleManager localeManager,
                                    Locale defaultLocale,
                                    Locale locale,
                                    Function<Color, TextComponent> colorTransformer,
                                    Function<Style, TextComponent> styleTransformer) {
        super(localeManager, defaultLocale, locale);
        this.colorTransformer = colorTransformer;
        this.styleTransformer = styleTransformer;
    }

    public FastColoredTextLocalizer(LocaleManager localeManager,
                                    Locale defaultLocale,
                                    Locale locale) {
        this(localeManager, defaultLocale, locale, c -> Text.single(""), s -> Text.single(""));
    }

    public FastColoredTextLocalizer(LocaleManager localeManager,
                                    Locale defaultLocale) {
        this(localeManager, defaultLocale, defaultLocale);
    }

    public Function<Color, TextComponent> getColorTransformer() {
        return this.colorTransformer;
    }

    public Function<Style, TextComponent> getStyleTransformer() {
        return this.styleTransformer;
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
                components.insert(new Element<>(((CapitalizeComponent) next).getTextComponent()));
                nextMode = UPPER;
            } else if (next instanceof DecapitalizeComponent) {
                components.insert(new Element<>(((DecapitalizeComponent) next).getTextComponent()));
                nextMode = LOWER;
            } else if (next instanceof VariableComponent) {
                String variable = ((VariableComponent) next).getVariable();
                TextComponent component = args.get(variable);

                if (component != null) {
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
                            .getRequiredLocalization(componentLocalization);

                components.insert(new Element<>(localization));
            } else if (next instanceof ArgsAppliedText) {
                ArgsAppliedText argsAppliedText = (ArgsAppliedText) next;
                Map<String, TextComponent> tc = new HashMap<>(argsAppliedText.getArgs());
                tc.putAll(args);
                TextComponent component = argsAppliedText.getComponent();
                this.localize(component, tc, locale, result);
            } else if (next instanceof Text) {
                Element<TextComponent> element = null;
                Element<TextComponent> last = null;
                for (TextComponent component : ((Text) next).getComponents()) {
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
                throw new IllegalArgumentException("Invalid component: '"+next+"'!");
            }
        }


    }

    static final int NORMAL = 0;
    static final int UPPER = 1;
    static final int LOWER = 2;

    final static class Element<E> {
        final E value;
        Element<E> next;

        Element(E value) {
            this.value = value;
        }

    }

    final static class Elements<E> {
        Element<E> first;

        void insert(Element<E> eElement) {
            this.insert(eElement, eElement);
        }

        void insert(Element<E> eElement, Element<E> end) {
            if (end.next != null)
                throw new IllegalArgumentException("Element to insert has a next element");

            if (first == null) {
                first = eElement;
            } else {
                Element<E> f = first;
                first = end;
                end.next = f;
            }
        }

        Element<E> nextElement() {
            if (first != null) {
                Element<E> elem = first;
                this.first = first.next;
                return elem;
            }
            return null;
        }
    }

}
