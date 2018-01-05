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
import com.github.jonathanxd.iutils.text.TextComponent;

import java.util.Collections;
import java.util.Map;

/**
 * Converts text into string.
 */
public interface TextLocalizer {

    /**
     * Gets the locale manager.
     *
     * @return Locale manager.
     */
    LocaleManager getLocaleManager();

    /**
     * Gets default locale.
     *
     * @return Default locale.
     */
    Locale getDefaultLocale();

    /**
     * Sets default locale to be used in translation, this locale is also used as a fallback when text cannot
     * be localized with {@link #getLocale() current locale}.
     *
     * @param locale New default locale.
     * @return Old locale.
     */
    Locale setDefaultLocale(Locale locale);

    /**
     * Gets current locale.
     *
     * @return Current locale.
     */
    Locale getLocale();

    /**
     * Sets current locale to be used in translation.
     *
     * @param locale New locale.
     * @return Old locale.
     */
    Locale setLocale(Locale locale);

    /**
     * Localizes {@code textComponent} to {@link #getLocale() current locale}.
     *
     * Uses current locale.
     *
     * @param textComponent Text component.
     * @return String of text component.
     */
    default String localize(TextComponent textComponent) {
        return this.localize(textComponent, (Locale) null);
    }

    /**
     * Localizes {@code textComponent} to {@link #getLocale() current locale}.
     *
     * Uses current locale.
     *
     * @param textComponent Text component.
     * @param args          Values of text variables.
     * @return String of text component.
     */
    default String localize(TextComponent textComponent, Map<String, TextComponent> args) {
        return this.localize(textComponent, args, null);
    }

    /**
     * Localizes {@code textComponent} to {@code locale}.
     *
     * If text cannot be localized using {@code locale}, current locale will be used, or the locale
     * defined by component. If component defined a locale, but text cannot be localized, current
     * locale is used, if current locale does localize the text, default locale is used.
     *
     * @param textComponent Text component.
     * @param locale        Locale to be used to localize text.
     * @return String of text component.
     */
    default String localize(TextComponent textComponent, Locale locale) {
        return this.localize(textComponent, Collections.emptyMap(), locale);
    }

    /**
     * Localizes {@code textComponent} to {@code locale}.
     *
     * If text cannot be localized using {@code locale}, current locale will be used, or the locale
     * defined by component. If component defined a locale, but text cannot be localized, current
     * locale is used, if current locale does localize the text, default locale is used.
     *
     * @param textComponent Text component.
     * @param args          Values of text variables.
     * @param locale        Locale to be used to localize text.
     * @return String of text component.
     */
    String localize(TextComponent textComponent, Map<String, TextComponent> args, Locale locale);
}
