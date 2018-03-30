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
import com.github.jonathanxd.iutils.text.MapLocalizedOperators;
import com.github.jonathanxd.iutils.text.MapLocalizedText;
import com.github.jonathanxd.iutils.text.StringComponent;
import com.github.jonathanxd.iutils.text.Style;
import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;
import com.github.jonathanxd.iutils.text.VariableComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Default text localizer.
 *
 * This localizer does not support colors, but can be safely extended, the localizer function is
 * {@link #localize(TextComponent, Map, Locale)}, so you can override it and handle color
 * components, falling back to {@code super} implementation when a non-color component is found. The
 * function use recursion, then color parsing through function override is safe.
 */
public class DefaultTextLocalizer extends AbstractTextLocalizer {
    public DefaultTextLocalizer(@NotNull LocaleManager localeManager,
                                @NotNull Locale defaultLocale,
                                @NotNull Locale locale) {
        super(localeManager, defaultLocale, locale);
    }

    public DefaultTextLocalizer(@NotNull LocaleManager localeManager,
                                @NotNull Locale defaultLocale) {
        this(localeManager, defaultLocale, defaultLocale);
    }

    @NotNull
    @Override
    public String localize(@NotNull TextComponent textComponent,
                           @NotNull Map<String, TextComponent> args,
                           @Nullable Locale locale) {
        StringBuilder sb = new StringBuilder();
        this.localize(textComponent, args, locale, sb);
        return sb.toString();
    }

    public void localize(@NotNull TextComponent textComponent,
                         @NotNull Map<String, TextComponent> args,
                         @Nullable Locale locale,
                         @NotNull StringBuilder sb) {

        if (!(textComponent instanceof Color)
                && !(textComponent instanceof Style)) {
            if (textComponent instanceof Text) {
                this.getTextString((Text) textComponent, args, locale, sb);
            } else if (textComponent instanceof StringComponent) {
                sb.append(((StringComponent) textComponent).getText());
            } else if (textComponent instanceof CapitalizeComponent) {
                String s = this.localize(((CapitalizeComponent) textComponent).getTextComponent(), args, locale);
                if (s.length() != 0) {
                    sb.append(Character.toUpperCase(s.charAt(0))).append(s, 1, s.length());
                }
            } else if (textComponent instanceof DecapitalizeComponent) {
                String s = this.localize(((DecapitalizeComponent) textComponent).getTextComponent(), args, locale);
                if (s.length() != 0) {
                    sb.append(Character.toLowerCase(s.charAt(0))).append(s, 1, s.length());
                }
            } else if (textComponent instanceof VariableComponent) {
                String variable = ((VariableComponent) textComponent).getVariable();
                TextComponent component = args.get(variable);

                if (component != null) {
                    this.localize(component, args, locale, sb);
                } else {
                    sb.append("$").append(((VariableComponent) textComponent).getVariable());
                }
            } else if (textComponent instanceof LocalizableComponent || textComponent instanceof MapLocalizedText) {
                LocalizableComponent localizableComponent;
                UnaryOperator<List<TextComponent>> operator;

                if (textComponent instanceof LocalizableComponent) {
                    localizableComponent = (LocalizableComponent) textComponent;
                    operator = MapLocalizedOperators.lineJump();
                } else {
                    localizableComponent = ((MapLocalizedText) textComponent).getLocalizableComponent();
                    operator = ((MapLocalizedText) textComponent).getOperator();
                }

                String componentLocalization = localizableComponent.getLocalization();

                String localeStr = localizableComponent.getLocale();
                Locale localLocale = localeStr != null
                        ? this.getLocaleManager().getRequiredLocale(localeStr)
                        : null;

                Locale toUse = locale != null ? locale : localLocale != null ? localLocale : this.getLocale();

                List<TextComponent> localizations =
                        toUse.getLocalizationManager().getLocalizations(componentLocalization);

                Locale current = this.getLocale();
                if (localizations.isEmpty() && toUse != current)
                    localizations = current.getLocalizationManager().getLocalizations(componentLocalization);

                if (localizations.isEmpty())
                    localizations = this.getDefaultLocale().getLocalizationManager().getLocalizations(componentLocalization);

                if (localizations.isEmpty())
                    localizations = Collections.singletonList(Text.single(componentLocalization));

                List<TextComponent> apply = operator.apply(localizations);

                for (TextComponent component : apply) {
                    this.localize(component, args, locale, sb);
                }
            } else if (textComponent instanceof ArgsAppliedText) {
                ArgsAppliedText argsAppliedText = (ArgsAppliedText) textComponent;
                Map<String, TextComponent> arguments = new HashMap<>(argsAppliedText.getArgs());
                arguments.putAll(args);

                this.localize(argsAppliedText.getComponent(), arguments, locale, sb);
            } else {
                throw new IllegalArgumentException("Invalid component '" + textComponent + "'!");
            }
        }
    }

    private void getTextString(@NotNull Text text,
                               @NotNull Map<String, TextComponent> variableValues,
                               @Nullable Locale locale,
                               @NotNull StringBuilder sb) {
        for (TextComponent textComponent : text) {
            this.localize(textComponent, variableValues, locale, sb);
        }
    }


}
