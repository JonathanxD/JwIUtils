/*
 *      JwIUtils-Properties - Properties module of JwIUtils <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.property.value;

import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.internal.ValuesUtil;
import com.github.jonathanxd.iutils.property.optionalnav.OptionalValues;
import com.github.jonathanxd.iutils.string.ToStringHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Values implements Iterable<Value<?>> {

    private final List<Value<?>> valueList;

    private Values(List<Value<?>> valueList) {
        this.valueList = Collections.unmodifiableList(new ArrayList<>(valueList));
    }

    /**
     * Creates a {@link Values values} instance from a {@code values} list.
     *
     * @param values Values list to create values instance.
     * @return Values instance with {@code values} as value list.
     */
    public static Values createValues(List<Value<?>> values) {
        return new Values(values);
    }

    /**
     * Creates current values from {@code values}.
     *
     * Current values are created from {@link ReachValue} instances with {@link
     * Property#getDefaultValue()}, other instances in {@code values} does not have their values
     * changed.
     *
     * @param values Values to create current values.
     * @return Values instance with current values.
     */
    public static Values createCurrentValues(Values values) {
        List<Value<?>> newValueList = new ArrayList<>();
        List<Value<?>> valueList = values.getValueList();

        for (Value<?> value : valueList) {
            if (value instanceof ReachValue<?>) {
                newValueList.add(ValueFactory.createCurrentValue((ReachValue<?>) value));
            } else {
                newValueList.add(value);
            }
        }

        return Values.createValues(newValueList);
    }

    public List<Value<?>> getValueList() {
        return this.valueList;
    }

    @NotNull
    @Override
    public Iterator<Value<?>> iterator() {
        return this.getValueList().iterator();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<? extends Value<T>> getValue(Property<T> property) {
        for (Value<?> value : valueList) {
            if (Objects.equals(value.getProperty(), property))
                return Optional.of((Value<T>) value);
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<ConstantValue<T>> getConstantValue(Property<T> property) {
        for (Value<?> value : valueList) {
            if (value instanceof ConstantValue<?>
                    && Objects.equals(value.getProperty(), property))
                return Optional.of((ConstantValue<T>) value);
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<CurrentValue<T>> getCurrentValue(Property<T> property) {
        for (Value<?> value : valueList) {
            if (value instanceof CurrentValue<?>
                    && Objects.equals(value.getProperty(), property))
                return Optional.of((CurrentValue<T>) value);
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<ReachValue<T>> getReachValue(Property<T> property) {
        for (Value<?> value : valueList) {
            if (value instanceof ReachValue<?>
                    && Objects.equals(value.getProperty(), property))
                return Optional.of((ReachValue<T>) value);
        }

        return Optional.empty();
    }


    /**
     * Returns {@link Optional} of {@code this} if a value for {@code property} is present.
     *
     * @param property Property to get value.
     * @return {@link Optional} of {@code this} if a value for {@code property} is present.
     */
    @SuppressWarnings("unchecked")
    public Optional<Values> ifValuePresent(Property<?> property) {
        return this.getValue(property).map(it -> this);
    }

    /**
     * Returns {@link Optional} of {@code this} if a value for {@code property} is present.
     *
     * @param property Property to get value.
     * @return {@link Optional} of {@code this} if a value for {@code property} is present.
     */
    @SuppressWarnings("unchecked")
    public Optional<Values> ifValueEq(Property<?> property) {
        return this.getValue(property).map(it -> this);
    }

    /**
     * Creates optional values helper.
     *
     * @return Optional values helper.
     */
    public OptionalValues values() {
        return OptionalValues.some(this);
    }

    /**
     * Returns whether all {@link CurrentValue current values} reached all {@link ReachValue reach
     * values} and all {@link ConstantValue constant values} matches all {@link ConstantValue
     * current values} of {@code base}.
     *
     * Also {@link CurrentValue current values} of {@code base} will be compared with {@link
     * CurrentValue current values} of this.
     *
     * @param base Values instance with all {@link ReachValue values to reach}.
     * @return Whether these values reached values of {@code base}.
     */
    public boolean isComplete(Values base) {
        if (Objects.equals(base, this))
            throw new IllegalStateException("Comparing with same values instance.");

        for (Value<?> reachValue : base) {
            Optional<? extends Value<?>> thisValue;

            if (reachValue instanceof ConstantValue<?>) {
                thisValue = this.getConstantValue(reachValue.getProperty());
            } else if (reachValue instanceof CurrentValue<?>) {
                thisValue = this.getCurrentValue(reachValue.getProperty());
            } else if (reachValue instanceof ReachValue<?>) {
                thisValue = this.getCurrentValue(reachValue.getProperty());
            } else {
                throw new IllegalArgumentException("Illegal found reach value: '" + reachValue + "'!");
            }

            if (!thisValue.isPresent() || !ValuesUtil.reached(thisValue.get(), reachValue))
                return false;

        }

        return true;
    }

    @Override
    public String toString() {
        return ToStringHelper.defaultHelper("Values")
                .add("values", this.getValueList())
                .toString();
    }
}
