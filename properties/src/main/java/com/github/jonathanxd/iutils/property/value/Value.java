/*
 *      JwIUtils-Properties - Properties module of JwIUtils <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.property.value;

import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.internal.ValuesUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Represents a property value.
 *
 * @param <T> Type of value.
 */
public interface Value<T> {
    /**
     * Gets the property that this value is defined to.
     *
     * @return Property that this value is defined to.
     */
    @NotNull
    Property<T> getProperty();

    /**
     * Gets the value.
     *
     * @return Value.
     */
    @NotNull
    T getValue();

    /**
     * Gets the value type.
     *
     * @return Value type.
     */
    @NotNull
    ValueType getValueType();

    /**
     * Returns whether {@code this} {@link Value value} reached {@code value}.
     *
     * @param value Value to check if was reached.
     * @return Whether {@code this} {@link Value value} reached {@code value}.
     */
    default boolean reached(T value) {
        return this.getValueType().reached(this, value);
    }

    /**
     * Gets the value comparator.
     *
     * @return Value comparator.
     */
    default Comparator<T> getComparator() {
        return ValuesUtil.getValueComparator();
    }
}
