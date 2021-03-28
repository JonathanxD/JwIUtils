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
package com.github.jonathanxd.iutils.property.value;

import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.internal.ValuesUtil;

import java.util.Objects;

public enum ValueType {
    CONSTANT {
        @Override
        public <T> boolean reached(Value<T> value, T valueToReach) {
            return Objects.equals(value.getValue(), valueToReach);
        }

        @Override
        public <T> Value<T> create(Property<T> property, T value) {
            return ValueFactory.createConstantValue(property, value);
        }
    },
    CURRENT {
        @Override
        public <T> boolean reached(Value<T> value, T valueToReach) {
            return ValuesUtil.reached(value, valueToReach);
        }

        @Override
        public <T> Value<T> create(Property<T> property, T value) {
            return ValueFactory.createCurrentValue(property, value);
        }
    },
    REACH {
        @Override
        public <T> boolean reached(Value<T> value, T valueToReach) {
            return Objects.equals(value.getValue(), valueToReach);
        }

        @Override
        public <T> Value<T> create(Property<T> property, T value) {
            return ValueFactory.createReachValue(property, value);
        }
    };

    /**
     * Returns whether {@code value} reached {@code valueToReach}.
     *
     * @param value        Value instance.
     * @param valueToReach Value to check whether {@code value} reached.
     * @param <T>          Type of value.
     * @return Whether {@code value} reached {@code valueToReach}.
     */
    public abstract <T> boolean reached(Value<T> value, T valueToReach);

    /**
     * Creates a {@link Value value instance} for {@code property} and {@code value}.
     *
     * @param property Property to create value.
     * @param value    Value of instance (starting property value).
     * @param <T>      Type of value.
     * @return {@link Value Value instance} for {@code property} and {@code value}.
     */
    public abstract <T> Value<T> create(Property<T> property, T value);
}
