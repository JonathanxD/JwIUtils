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

import com.github.jonathanxd.iutils.box.MutableBox;
import com.github.jonathanxd.iutils.opt.OptObject;
import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.internal.PropertyUtil;

public class ValueFactory {
    /**
     * Creates a instance of representation of a value to reach of {@code property}.
     *
     * @param property     Property.
     * @param valueToReach Value to reach of {@code property}.
     * @param <T>          Type of value.
     * @return Instance of representation of value to reach of {@code property}.
     */
    public static <T> ReachValue<T> createReachValue(Property<T> property, T valueToReach) {
        if (!property.getDefaultValue().isPresent())
            throw new IllegalArgumentException("Provided a reach value for a property that does" +
                    " not have default value. Property: " + property + ". Reach value: " + valueToReach);

        return new ReachValue<>(property, valueToReach);
    }

    /**
     * Creates a instance of representation of a constant value of {@code property}.
     *
     * @param property Property.
     * @param constant Constant value.
     * @param <T>      Type of value.
     * @return Instance of representation of a constant value of {@code property}.
     */
    public static <T> ConstantValue<T> createConstantValue(Property<T> property, T constant) {
        return new ConstantValue<>(property, constant);
    }

    /**
     * Creates a instance of representation of current value of {@code property}.
     *
     * @param property Property.
     * @param value    Current value.
     * @param <T>      Type of value.
     * @return instance of representation of current value of {@code property}.
     */
    public static <T> CurrentValue<T> createCurrentValue(Property<T> property, T value) {
        return new CurrentValue<>(property, MutableBox.of(value));
    }

    /**
     * Creates a instance of representation of current value of a {@link Property} with {@link
     * Property#getDefaultValue()} property default value} as current value, the default value of
     * property will be retrieved with {@link PropertyUtil#getRequiredDefaultValue(Property)}, which
     * throws exception if not default value is defined (but remember that default value is required
     * to create a reach value, so there is no problem here, unless the semantic was broken in a
     * hacky way...).
     *
     * @param value Instance with value to reach.
     * @param <T>   Type of value.
     * @return Instance of representation of current value of a {@link Property} with {@link
     * ReachValue#getValueToReach() reach value} as current value.
     */
    public static <T> CurrentValue<T> createCurrentValue(ReachValue<T> value) {
        return ValueFactory.createCurrentValue(value.getProperty(), PropertyUtil.getRequiredDefaultValue(value.getProperty()));
    }

    /**
     * Creates a instance of representation of current value of a {@link Property} with {@link
     * ReachValue#getValueToReach() reach value} as current value.
     *
     * @param value Instance with value to reach.
     * @param <T>   Type of value.
     * @return Instance of representation of current value of a {@link Property} with {@link
     * ReachValue#getValueToReach() reach value} as current value.
     */
    public static <T> CurrentValue<T> createCurrentValueWithReachValue(ReachValue<T> value) {
        return ValueFactory.createCurrentValue(value.getProperty(), value.getValueToReach());
    }

    /**
     * Creates a instance of representation of current value of {@code property} with {@link
     * Property#getDefaultValue() default value} as current value. The default value of property
     * must be present.
     *
     * @param property Property.
     * @param <T>      Type of value.
     * @return Instance of representation of current value of {@code property} with {@link
     * Property#getDefaultValue() default value} as current value.
     */
    public static <T> CurrentValue<T> createCurrentValue(Property<T> property) {
        OptObject<T> defaultValue = property.getDefaultValue();

        if (!defaultValue.isPresent())
            throw new IllegalArgumentException("Cannot create current value of a property that does not have a default value.");

        return ValueFactory.createCurrentValue(property, defaultValue.getValue());
    }
}
