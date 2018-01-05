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
package com.github.jonathanxd.iutils.property.optionalnav;

import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.value.ConstantValue;
import com.github.jonathanxd.iutils.property.value.CurrentValue;
import com.github.jonathanxd.iutils.property.value.ReachValue;
import com.github.jonathanxd.iutils.property.value.Value;
import com.github.jonathanxd.iutils.property.value.ValueType;
import com.github.jonathanxd.iutils.property.value.Values;

import java.util.Objects;
import java.util.Optional;

/**
 * Helper of {@link Values}.
 *
 * This class works in this way: When you get a value that is present in {@link #values}, same
 * instance is returned, when you get a value that is not present, an empty optional is returned,
 * and other methods will always returns an empty optional.
 */
public abstract class OptionalValues {
    private final Values values;

    OptionalValues(Values values) {
        this.values = values;
    }

    /**
     * Creates a some optional values.
     *
     * @param values Values to help with.
     * @return Some optional values.
     */
    public static OptionalValues some(Values values) {
        return new Some(values);
    }

    /**
     * Creates a empty optional values.
     *
     * @param values Values to help with.
     * @return Empty optional values.
     */
    public static OptionalValues empty(Values values) {
        return new Empty(values);
    }

    /**
     * Gets values instance that this optional is holding.
     *
     * @return Values instance that this optional is holding.
     */
    public final Values getValues() {
        return this.values;
    }

    /**
     * Gets value of {@code property}.
     *
     * @param property Property.
     * @param <T>      Type of value.
     * @return Optional with property value, if present, or empty if not, or if this is an empty
     * {@link OptionalValues}.
     */
    public <T> Optional<? extends Value<T>> getValue(Property<T> property) {
        return Optional.empty();
    }

    /**
     * Gets current value of {@code property}.
     *
     * @param property Property.
     * @param <T>      Type of value.
     * @return Optional with property current value, if present, or empty if not, or if this is an
     * empty {@link OptionalValues}.
     */
    public <T> Optional<CurrentValue<T>> getCurrentValue(Property<T> property) {
        return Optional.empty();
    }

    /**
     * Gets constant value of {@code property}.
     *
     * @param property Property.
     * @param <T>      Type of value.
     * @return Optional with property constant value, if present, or empty if not, or if this is an
     * empty {@link OptionalValues}.
     */
    public <T> Optional<ConstantValue<T>> getConstantValue(Property<T> property) {
        return Optional.empty();
    }

    /**
     * Gets reach value of {@code property}.
     *
     * @param property Property.
     * @param <T>      Type of value.
     * @return Optional with property reach value, if present, or empty if not, or if this is an
     * empty {@link OptionalValues}.
     */
    public <T> Optional<ReachValue<T>> getReachValue(Property<T> property) {
        return Optional.empty();
    }

    /**
     * Returns same instance if {@code property} value matches {@code value}, empty otherwise (or if
     * this is already empty).
     *
     * @param property Property.
     * @param value    Value to check if property value matches.
     * @param <T>      Type of value.
     * @return Same instance if {@code property} value matches {@code value}, empty otherwise (or if
     * this is already empty).
     */
    public <T> OptionalValues match(Property<T> property, T value) {
        return this;
    }

    /**
     * Returns same instance if {@code property} value matches {@code value} and {@code property}
     * value type is equal to {@code valueType}, empty otherwise (or if this is already empty).
     *
     * @param property Property.
     * @param value    Value to check if property value matches.
     * @param <T>      Type of value.
     * @return Same instance if {@code property} value matches {@code value} and {@code property}
     * value type is equal to {@code valueType}, empty otherwise (or if this is already empty).
     */
    public <T> OptionalValues match(Property<T> property, T value, ValueType valueType) {
        return this;
    }

    /**
     * Returns same instance if {@code property} value matches constant {@code value}, empty
     * otherwise (or if this is already empty).
     *
     * @param property Property.
     * @param value    Value to check if property value matches.
     * @param <T>      Type of value.
     * @return Same instance if {@code property} value matches constant {@code value}, empty
     * otherwise (or if this is already empty).
     */
    public <T> OptionalValues matchConstant(Property<T> property, T value) {
        return this.match(property, value, ValueType.CONSTANT);
    }

    /**
     * Returns same instance if {@code property} value matches current {@code value}, empty
     * otherwise (or if this is already empty).
     *
     * @param property Property.
     * @param value    Value to check if property value matches.
     * @param <T>      Type of value.
     * @return Same instance if {@code property} value matches current {@code value}, empty
     * otherwise (or if this is already empty).
     */
    public <T> OptionalValues matchCurrent(Property<T> property, T value) {
        return this.match(property, value, ValueType.CURRENT);
    }

    /**
     * Returns same instance if {@code property} value matches reach {@code value}, empty otherwise
     * (or if this is already empty).
     *
     * @param property Property.
     * @param value    Value to check if property value matches.
     * @param <T>      Type of value.
     * @return Same instance if {@code property} value matches reach {@code value}, empty otherwise
     * (or if this is already empty).
     */
    public <T> OptionalValues matchReach(Property<T> property, T value) {
        return this.match(property, value, ValueType.REACH);
    }

    /**
     * Returns an empty instance of this optional.
     *
     * @return Empty instance of this optional.
     */
    OptionalValues empty() {
        return new Empty(this.values);
    }

    static class Empty extends OptionalValues {

        Empty(Values values) {
            super(values);
        }

    }

    static class Some extends OptionalValues {

        Some(Values values) {
            super(values);
        }

        @Override
        public <T> Optional<? extends Value<T>> getValue(Property<T> property) {
            return this.getValues().getValue(property);
        }

        @Override
        public <T> Optional<CurrentValue<T>> getCurrentValue(Property<T> property) {
            return this.getValues().getCurrentValue(property);
        }

        @Override
        public <T> Optional<ConstantValue<T>> getConstantValue(Property<T> property) {
            return this.getValues().getConstantValue(property);
        }

        @Override
        public <T> Optional<ReachValue<T>> getReachValue(Property<T> property) {
            return this.getValues().getReachValue(property);
        }

        @Override
        public <T> OptionalValues match(Property<T> property, T value) {
            Optional<? extends Value<T>> v = this.getValues().getValue(property);

            if (!v.isPresent() || !v.get().reached(value))
                return this.empty();

            return this;
        }

        @Override
        public <T> OptionalValues match(Property<T> property, T value, ValueType valueType) {
            Optional<? extends Value<T>> v = this.getValues().getValue(property);

            if (!v.isPresent()
                    || !v.get().reached(value)
                    || !Objects.equals(valueType, v.get().getValueType()))
                return this.empty();

            return this;
        }
    }
}
