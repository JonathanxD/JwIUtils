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
import com.github.jonathanxd.iutils.string.ToStringHelper;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AbstractValue<T> implements Value<T> {

    private final Property<T> property;

    protected AbstractValue(@NotNull Property<T> property) {
        Objects.requireNonNull(property, "Property cannot be null.");
        this.property = property;
    }

    @NotNull
    @Override
    public Property<T> getProperty() {
        return this.property;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getProperty(), this.getValue(), this.getValueType());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Value<?>))
            return super.equals(obj);

        Value<?> v = (Value<?>) obj;

        return Objects.equals(this.getProperty(), v.getProperty())
                && Objects.equals(this.getValue(), v.getValue())
                && Objects.equals(this.getValueType(), v.getValueType());
    }

    @Override
    public String toString() {
        return ToStringHelper.defaultHelper("Value")
                .add("property", this.getProperty())
                .add("value", this.getValue())
                .add("valueType", this.getValueType())
                .toString();
    }
}
