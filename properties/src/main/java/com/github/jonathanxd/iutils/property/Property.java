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
package com.github.jonathanxd.iutils.property;

import com.github.jonathanxd.iutils.opt.specialized.OptObject;
import com.github.jonathanxd.iutils.string.ToStringHelper;
import com.github.jonathanxd.iutils.type.TypeInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Specifies a property to reach in system, example, amount of users.
 *
 * To create an instance, use {@link PropertyFactory#createProperty(TypeInfo, String, OptObject)}.
 *
 * @param <T> Type of value.
 */
public final class Property<T> {

    private final TypeInfo<T> type;
    private final String name;
    private final OptObject<T> defaultValue;

    Property(@NotNull TypeInfo<T> type,
             @NotNull String name,
             @NotNull OptObject<T> defaultValue) {
        Objects.requireNonNull(type, "Type cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(defaultValue, "Default value cannot be null");
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public TypeInfo<T> getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public OptObject<T> getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getType(), this.getName(), this.getDefaultValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Property<?>))
            return super.equals(obj);

        Property<?> objProperty = (Property<?>) obj;

        return Objects.equals(this.getType(), objProperty.getType())
                && Objects.equals(this.getName(), objProperty.getName())
                && Objects.equals(this.getDefaultValue(), objProperty.getDefaultValue());
    }

    @Override
    public String toString() {
        return ToStringHelper.defaultHelper("Property")
                .add("type", this.getType())
                .add("name", this.getName())
                .add("defaultValue", this.getDefaultValue())
                .toString();
    }
}
