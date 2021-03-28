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
import com.github.jonathanxd.iutils.property.optionalnav.OptionalValues;

import java.util.Optional;

/**
 * Holder of {@link Values}.
 */
public interface ValuesHolder {

    /**
     * Gets all values that this instance holds.
     *
     * @return All values that this instance holds.
     */
    Values getValues();

    /**
     * Creates optional values helper.
     *
     * @return Optional values helper.
     */
    default OptionalValues values() {
        return this.getValues().values();
    }

    /**
     * Gets optional of value of {@code property}.
     *
     * @param property Property to get value.
     * @param <T>      Type of value.
     * @return Optional with found value, or empty if values was not found.
     */
    default <T> Optional<? extends Value<T>> getValue(Property<T> property) {
        return this.getValues().getValue(property);
    }

    /**
     * Gets optional of current value of {@code property}.
     *
     * @param property Property to get value.
     * @param <T>      Type of value.
     * @return Optional with found value, or empty if values was not found.
     */
    default <T> Optional<CurrentValue<T>> getCurrentValue(Property<T> property) {
        return this.getValues().getCurrentValue(property);
    }

    /**
     * Gets optional of constant value of {@code property}.
     *
     * @param property Property to get value.
     * @param <T>      Type of value.
     * @return Optional with found value, or empty if values was not found.
     */
    default <T> Optional<ConstantValue<T>> getConstantValue(Property<T> property) {
        return this.getValues().getConstantValue(property);
    }

    /**
     * Gets optional of constant value of {@code property}.
     *
     * @param property Property to get value.
     * @param <T>      Type of value.
     * @return Optional with found value, or empty if values was not found.
     */
    default <T> Optional<ReachValue<T>> getReachValue(Property<T> property) {
        return this.getValues().getReachValue(property);
    }
}
