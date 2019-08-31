/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import java.util.function.UnaryOperator;

/**
 * Text component that can be localized. Localized text are returned in a new instance of the same
 * type or a type that extends the current type. Also, the same instance may be returned if the text
 * is already localized for specified locale.
 *
 * Locale is provided in form of string, the locale must be registered before text transformation
 * into string.
 */
public interface Localizable<T extends Localizable<T>> extends TextComponent {

    /**
     * Gets the locale.
     *
     * @return Locale.
     */
    String getLocale();

    /**
     * Localize the text for {@code locale}. By default, text is localized for default locale. The
     * same instance may be returned when it is called with same {@code locale} as it was before.
     *
     * If {@link #getLocalization() localization} of this {@link Localizable} is not registered for
     * {@code locale}, the default {@code locale} will be used to localized the component.
     *
     * @param locale Locale to localize the text.
     * @return Localized text.
     */
    T localize(String locale);

    /**
     * Gets the localization path of this component, commonly a lower case name, with dots,
     * underscore, letters and/or numbers.
     *
     * Constants are recommended for better maintainability.
     *
     * @return Localization path of this component.
     */
    String getLocalization();

}
