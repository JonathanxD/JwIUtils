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

import java.util.Objects;

/**
 * Abstract text localizer.
 */
public abstract class AbstractTextLocalizer implements TextLocalizer {
    private final LocaleManager localeManager;
    private Locale defaultLocale;
    private Locale locale;

    public AbstractTextLocalizer(LocaleManager localeManager,
                                 Locale defaultLocale,
                                 Locale locale) {
        this.localeManager = Objects.requireNonNull(localeManager, "Locale manager cannot be null.");
        this.defaultLocale = Objects.requireNonNull(defaultLocale, "Default locale cannot be null.");
        this.locale = Objects.requireNonNull(locale, "Current locale cannot be null.");
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
    public Locale getLocale() {
        return this.locale;
    }


    @Override
    public Locale setLocale(Locale locale) {
        Objects.requireNonNull(locale, "Locale cannot be null.");
        Locale old = this.locale;
        this.locale = locale;
        return old;
    }
}
