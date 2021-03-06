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
package com.github.jonathanxd.iutils.property;

import com.github.jonathanxd.iutils.opt.OptObject;
import com.github.jonathanxd.iutils.type.TypeInfo;

/**
 * Factory of {@link Property}.
 */
public final class PropertyFactory {

    private PropertyFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a {@link Property} or returns the cached one.
     *
     * Created properties are unique and cached, this means that calling this method more than one
     * time with same parameters always returns the same instance of {@link Property}.
     *
     * @param type         Type of property value.
     * @param name         Name of property.
     * @param defaultValue Default value of property.
     * @param <T>          Type of property value.
     * @return A new {@link Property}, or a cached one.
     */
    @SuppressWarnings("unchecked")
    public static <T> Property<T> createPropertyWithValue(TypeInfo<T> type, String name, T defaultValue) {
        return PropertyFactory.createProperty(type, name, OptObject.optObject(defaultValue));
    }

    /**
     * Creates a {@link Property} or returns the cached one.
     *
     * Created properties are unique and cached, this means that calling this method more than one
     * time with same parameters always returns the same instance of {@link Property}.
     *
     * @param type         Type of property value.
     * @param name         Name of property.
     * @param defaultValue Default property value.
     * @param <T>          Type of property value.
     * @return A new {@link Property}, or a cached one.
     */
    @SuppressWarnings("unchecked")
    public static <T> Property<T> createProperty(TypeInfo<T> type, String name, OptObject<T> defaultValue) {
        return new Property<>(type, name, defaultValue);
    }

    /**
     * Creates a variant of {@code property}. The variant only differs by name and value type.
     *
     * This method calls {@link #createProperty(TypeInfo, String, OptObject)} with values of {@link
     * Property} and {@code name}.
     *
     * @param property Base property.
     * @param name     Name of variant.
     * @param <T>      Value type.
     * @return A new {@link Property} based on {@code property} or a cached one.
     */
    public static <T> Property<T> createPropertyVariant(Property<T> property, String name) {
        return PropertyFactory.createProperty(property.getType(), name, property.getDefaultValue());
    }
}
