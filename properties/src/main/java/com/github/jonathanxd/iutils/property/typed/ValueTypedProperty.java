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
package com.github.jonathanxd.iutils.property.typed;

import com.github.jonathanxd.iutils.property.Property;
import com.github.jonathanxd.iutils.property.value.Value;
import com.github.jonathanxd.iutils.property.value.ValueType;
import com.github.jonathanxd.iutils.string.ToStringHelper;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ValueTypedProperty<T> {

    private final Property<T> property;
    private final ValueType valueType;

    ValueTypedProperty(@NotNull Property<T> property, @NotNull ValueType valueType) {
        Objects.requireNonNull(property, "Property cannot be null.");
        Objects.requireNonNull(valueType, "Value type cannot be null.");
        this.property = property;
        this.valueType = valueType;
    }

    public Property<T> getProperty() {
        return this.property;
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public Value<T> createValue(T value) {
        return this.getValueType().create(this.getProperty(), value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getProperty(), this.getValueType());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ValueTypedProperty<?>))
            return super.equals(obj);

        ValueTypedProperty<?> other = (ValueTypedProperty<?>) obj;

        return Objects.equals(this.getProperty(), other.getProperty())
                && Objects.equals(this.getValueType(), other.getValueType());
    }

    @Override
    public String toString() {
        return ToStringHelper.defaultHelper("ValueTypedProperty")
                .add("property", this.getProperty())
                .add("valueType", this.getValueType())
                .toString();
    }
}
