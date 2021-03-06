/*
 *      JwIUtils-Properties - Properties module of JwIUtils <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.property.format;

import com.github.jonathanxd.iutils.property.value.Value;
import com.github.jonathanxd.iutils.text.localizer.TextLocalizer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Helper of value format.
 */
public class ValueFormatterHelper {

    private final Set<ValueFormatter> formatterSet = new HashSet<>();

    /**
     * Registers {@code formatter}.
     *
     * @param formatter Formatter to register.
     */
    public void registerFormatter(ValueFormatter formatter) {
        this.formatterSet.add(formatter);
    }

    /**
     * Formats {@code value} and return a user readable formatted representation.
     *
     * @param value         Value to format.
     * @param textLocalizer Localizer to use.
     * @return Optional of user readable formatted value, or empty if cannot format.
     */
    public Optional<String> format(Value<?> value, TextLocalizer textLocalizer) {
        for (ValueFormatter valueFormatter : this.formatterSet) {
            Optional<String> format = valueFormatter.format(value, textLocalizer);

            if (format.isPresent())
                return format;
        }

        return Optional.empty();
    }

}
