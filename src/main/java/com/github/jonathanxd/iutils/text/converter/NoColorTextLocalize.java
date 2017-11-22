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
import com.github.jonathanxd.iutils.text.LocalizableComponent;
import com.github.jonathanxd.iutils.text.StringComponent;
import com.github.jonathanxd.iutils.text.Style;
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.VariableComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NoColorTextLocalize implements TextLocalize {
    private final LocaleManager localeManager;
    private Locale defaultLocale;

    public NoColorTextLocalize(LocaleManager localeManager, Locale defaultLocale) {
        this.localeManager = Objects.requireNonNull(localeManager, "Locale manager cannot be null.");
        this.defaultLocale = Objects.requireNonNull(defaultLocale, "Default locale cannot be null.");
    }

    @Override
    public Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public LocaleManager getLocaleManager() {
        return this.localeManager;
    }

    @Override
    public Locale setDefaultLocale(Locale locale) {
        Objects.requireNonNull(locale, "Default locale cannot be null.");
        Locale old = this.defaultLocale;
        this.defaultLocale = locale;
        return old;
    }


    @Override
    public String getString(TextComponent textComponent, Map<String, TextComponent> args, Locale locale) {

        if (textComponent instanceof Color
                || textComponent instanceof Style)
            return "";
        if (textComponent instanceof Text) {
            return this.getTextString((Text) textComponent, args, locale);
        } else if (textComponent instanceof StringComponent) {
            return ((StringComponent) textComponent).getText();
        } else if (textComponent instanceof CapitalizeComponent) {
            String s = this.getString(((CapitalizeComponent) textComponent).getTextComponent(), args, locale);
            if (s.length() == 0)
                return "";

            return Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length());
        } else if (textComponent instanceof VariableComponent) {
            String variable = ((VariableComponent) textComponent).getVariable();
            TextComponent component = args.get(variable);

            if (component != null)
                return this.getString(component, args, locale);

            return "$" + ((VariableComponent) textComponent).getVariable();
        } else if (textComponent instanceof LocalizableComponent) {
            LocalizableComponent localizableComponent = (LocalizableComponent) textComponent;
            String componentLocalization = localizableComponent.getLocalization();

            String localeStr = localizableComponent.getLocale();
            Locale localLocale = localeStr == null
                    ? this.defaultLocale
                    : this.localeManager.getRequiredLocale(localeStr);

            TextComponent localization =
                    locale.getLocalizationManager().getLocalization(componentLocalization);

            if (localization == null)
                localLocale.getLocalizationManager()
                        .getLocalization(componentLocalization);

            if (localization == null)
                localization = this.defaultLocale.getLocalizationManager()
                        .getRequiredLocalization(componentLocalization);

            return this.getString(localization, args, locale);
        } else if (textComponent instanceof ArgsAppliedText) {
            ArgsAppliedText argsAppliedText = (ArgsAppliedText) textComponent;
            Map<String, TextComponent> arguments = new HashMap<>(argsAppliedText.getArgs());
            arguments.putAll(args);

            return this.getString(argsAppliedText.getComponent(), arguments, locale);
        }

        throw new IllegalArgumentException("Invalid component '" + textComponent + "'!");
    }

    private String getTextString(Text text, Map<String, TextComponent> variableValues, Locale locale) {
        StringBuilder builder = new StringBuilder();

        for (TextComponent textComponent : text) {
            builder.append(this.getString(textComponent, variableValues, locale));
        }

        return builder.toString();
    }


}
