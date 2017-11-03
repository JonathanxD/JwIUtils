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
package com.github.jonathanxd.iutils.option;

import com.github.jonathanxd.iutils.function.stream.BiStream;
import com.github.jonathanxd.iutils.function.stream.BiStreams;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Option} holder. This class holds all options and store values defined for the option.
 */
public final class Options {

    /**
     * Map to stores values defined for options.
     */
    private final Map<Option<?>, Object> optionMap = new HashMap<>();
    private final Map<Option<?>, Object> immutableOptionMap = Collections.unmodifiableMap(this.optionMap);

    /**
     * Sets a {@link Option} value.
     *
     * @param option Option.
     * @param value  Option value.
     * @param <T>    Type of option value.
     * @return {@code this}.
     */
    public <T> Options set(Option<T> option, T value) {
        this.optionMap.put(option, value);
        return this;
    }

    /**
     * Gets the value of {@code option}.
     *
     * @param option Option.
     * @param <T>    Option value type.
     * @return Value defined for {@code option}, or the default value of the option (may be null).
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Option<T> option) {
        if (!this.optionMap.containsKey(option))
            return option.getDefaultValue();

        return (T) this.optionMap.get(option);
    }

    /**
     * Gets the option value if present, or returns {@code value} if not.
     *
     * @param option Option.
     * @param value  Value to return if option is not set.
     * @param <T>    Option value type.
     * @return Value defined for the {@code option}, or the {@code value} if option value is not
     * defined.
     */
    public <T> T getOrElse(Option<T> option, T value) {

        if (!this.optionMap.containsKey(option))
            return value;

        return this.get(option);
    }

    /**
     * Returns true if a value for the {@code option} is defined.
     *
     * @param option Option.
     * @param <T>    Option value type.
     * @return True if a value for the {@code option} is defined.
     */
    public <T> boolean contains(Option<T> option) {
        return this.optionMap.containsKey(option);
    }

    /**
     * Returns true if {@code value} is defined for any option.
     *
     * @param value Value.
     * @return True if {@code value} is defined for any option.
     */
    public boolean containsValue(Object value) {
        return this.optionMap.containsValue(value);
    }

    /**
     * Returns a {@link BiStream} of all options and their values.
     *
     * @return {@link BiStream} of all options and their values.
     */
    public BiStream<Option<?>, Object> stream() {
        return BiStreams.mapStream(this.optionMap);
    }

    /**
     * Gets a immutable map of options and their defined values.
     *
     * @return Immutable map of options and their defined values.
     */
    public Map<Option<?>, Object> getOptionValueMap() {
        return this.immutableOptionMap;
    }
}
