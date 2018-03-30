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
import com.github.jonathanxd.iutils.text.TextComponent;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A localizer which localizes only to a single locale (recommended for multi-user contexts).
 */
public interface SingleLocalizer {

    /**
     * Gets the locale which this localizer localizes to.
     *
     * @return Locale which this localizer localizes to.
     */
    Locale getLocale();

    /**
     * Gets the text localizer that this localizer will use as backend.
     *
     * @return Text localizer that this localizer will use as backend.
     */
    TextLocalizer getTextLocalizer();

    /**
     * See {@link TextLocalizer#getLocalizations(TextComponent, Map, Locale)}.
     */
    @NotNull
    default List<TextComponent> getLocalizations(@NotNull TextComponent textComponent,
                                                 @NotNull Map<String, TextComponent> args) {
        return this.getTextLocalizer().getLocalizations(textComponent, args, this.getLocale());
    }

    /**
     * See {@link TextLocalizer#getLocalizations(TextComponent, Map, Locale)}.
     */
    @NotNull
    default List<TextComponent> getLocalizations(@NotNull TextComponent textComponent) {
        return this.getLocalizations(textComponent, Collections.emptyMap());
    }


    /**
     * Localizes {@code textComponent} to {@link #getLocale() locale}.
     *
     * @param textComponent Component to localize.
     * @param args          Arguments.
     * @return Localized text.
     */
    @NotNull
    default String localize(@NotNull TextComponent textComponent, @NotNull Map<String, TextComponent> args) {
        return this.getTextLocalizer().localize(textComponent, args, this.getLocale());
    }

    /**
     * Localizes {@code textComponent} to {@link #getLocale() locale}.
     *
     * @param textComponent Component to localize.
     * @return Localized text.
     */
    @NotNull
    default String localize(@NotNull TextComponent textComponent) {
        return this.localize(textComponent, Collections.emptyMap());
    }

    /**
     * Simple implementation of {@link SingleLocalizer}.
     */
    class Impl implements SingleLocalizer {

        private final Locale locale;
        private final TextLocalizer localizer;

        public Impl(Locale locale, TextLocalizer localizer) {
            this.locale = locale;
            this.localizer = localizer;
        }

        @Override
        public Locale getLocale() {
            return this.locale;
        }

        @Override
        public TextLocalizer getTextLocalizer() {
            return this.localizer;
        }
    }
}
