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
package com.github.jonathanxd.iutils.text;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * A component that is resolved to another during text localization. This is commonly used to
 * represents a component that should be translated.
 *
 * A localizable element is like a {@link VariableComponent}, but in a global scope and the value is
 * stored in {@link com.github.jonathanxd.iutils.localization.LocalizationManager}.
 */
public final class LocalizableComponent implements Localizable<LocalizableComponent> {
    private final String locale;
    private final String localization;

    private LocalizableComponent(String locale, String localization) {
        this.locale = locale;
        this.localization = localization;
    }

    public static LocalizableComponent of(String locale, String localization) {
        return new LocalizableComponent(locale, localization);
    }

    public static LocalizableComponent of(String localization) {
        return new LocalizableComponent(null, localization);
    }

    @Override
    public LocalizableComponent localize(String locale) {
        return new LocalizableComponent(locale, this.localization);
    }

    @Override
    public TextComponent mapLocalized(UnaryOperator<List<TextComponent>> operator) {
        return new MapLocalizedText(this, operator);
    }

    public String getLocalization() {
        return this.localization;
    }

    @Override
    public String getLocale() {
        return this.locale;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LocalizableComponent)
            return Objects.equals(((LocalizableComponent) obj).getLocalization(), this.getLocalization())
                    && Objects.equals(((LocalizableComponent) obj).getLocale(), this.getLocale());

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLocale(), this.getLocalization());
    }

    @Override
    public String toString() {
        return "Localizable[" + this.getLocalization() + (this.getLocale() != null ? ", locale=" + this.getLocale() : "") + "]";
    }
}
