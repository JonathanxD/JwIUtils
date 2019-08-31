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
package com.github.jonathanxd.iutils.container;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface IMutableContainer<T> extends HistoryContainer<T> {

    /**
     * Value mapper.
     *
     * @param mapper Mapper.
     */
    void setMapper(BiFunction<BaseContainer<T>, T, T> mapper);

    /**
     * Map {@code value} with {@link #setMapper(BiFunction) defined mapper} and {@link
     * #setValue(Object) sets} {@code this} container value.
     *
     * @param value Value to map and set.
     */
    void setMapped(T value);

    /**
     * Sets the current value.
     *
     * @param value Value.
     */
    void setValue(T value);

    /**
     * Sets the current value.
     *
     * @param value Value.
     */
    default void set(T value) {
        this.setValue(value);
    }

    /**
     * Maps the current value.
     *
     * @param mapper Mapper.
     */
    default void mapThisValue(Function<T, T> mapper) {
        this.set(mapper.apply(this.get()));
    }

}
