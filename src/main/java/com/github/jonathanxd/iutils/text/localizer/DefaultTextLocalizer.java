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

/**
 * Default text localizer.
 *
 * This localizer does not support colors, but can be safely extended, the localizer function is
 * {@link #localize(TextComponent, Map, Locale)}, so you can override it and handle color components, falling
 * back to {@code super} implementation when a non-color component is found. The function use recursion, then
 * color parsing through function override is safe.
 */
public class DefaultTextLocalizer extends AbstractTextLocalizer {
    public DefaultTextLocalizer(LocaleManager localeManager,
                                Locale defaultLocale,
                                Locale locale) {
        super(localeManager, defaultLocale, locale);
    }

    public DefaultTextLocalizer(LocaleManager localeManager,
                                Locale defaultLocale) {
        this(localeManager, defaultLocale, defaultLocale);
    }

    @Override
    public String localize(TextComponent textComponent, Map<String, TextComponent> args, Locale locale) {

        if (textComponent instanceof Color
                || textComponent instanceof Style)
            return "";
        if (textComponent instanceof Text) {
            return this.getTextString((Text) textComponent, args, locale);
        } else if (textComponent instanceof StringComponent) {
            return ((StringComponent) textComponent).getText();
        } else if (textComponent instanceof CapitalizeComponent) {
            String s = this.localize(((CapitalizeComponent) textComponent).getTextComponent(), args, locale);
            if (s.length() == 0)
                return "";

            return Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length());
        } else if (textComponent instanceof DecapitalizeComponent) {
            String s = this.localize(((DecapitalizeComponent) textComponent).getTextComponent(), args, locale);
            if (s.length() == 0)
                return "";

            return Character.toLowerCase(s.charAt(0)) + s.substring(1, s.length());
        } else if (textComponent instanceof VariableComponent) {
            String variable = ((VariableComponent) textComponent).getVariable();
            TextComponent component = args.get(variable);

            if (component != null)
                return this.localize(component, args, locale);

            return "$" + ((VariableComponent) textComponent).getVariable();
        } else if (textComponent instanceof LocalizableComponent) {
            LocalizableComponent localizableComponent = (LocalizableComponent) textComponent;
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

            return this.localize(localization, args, locale);
        } else if (textComponent instanceof ArgsAppliedText) {
            ArgsAppliedText argsAppliedText = (ArgsAppliedText) textComponent;
            Map<String, TextComponent> arguments = new HashMap<>(argsAppliedText.getArgs());
            arguments.putAll(args);

            return this.localize(argsAppliedText.getComponent(), arguments, locale);
        }

        throw new IllegalArgumentException("Invalid component '" + textComponent + "'!");
    }

    private String getTextString(Text text, Map<String, TextComponent> variableValues, Locale locale) {
        StringBuilder builder = new StringBuilder();

        for (TextComponent textComponent : text) {
            builder.append(this.localize(textComponent, variableValues, locale));
        }

        return builder.toString();
    }


}
