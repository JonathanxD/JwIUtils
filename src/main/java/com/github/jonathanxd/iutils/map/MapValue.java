/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.map;

import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.Objects;

/**
 * Value holder.
 *
 * @param <T> Type of value.
 */
public class MapValue<T> {

    /**
     * Value instance.
     */
    private final T value;

    /**
     * Type of value instance.
     */
    private final TypeInfo<T> type;

    /**
     * Is value temporary.
     */
    private final boolean isTemporary;

    /**
     * Constructs a value holder.
     *
     * @param value       Value instance.
     * @param type        Reified type of instance.
     * @param isTemporary Is temporary.
     */
    MapValue(T value, TypeInfo<T> type, boolean isTemporary) {
        this.value = value;
        this.type = type;
        this.isTemporary = isTemporary;
    }

    /**
     * Constructs a non temporary value holder.
     *
     * @param value Value instance.
     * @param type  Reified type of instance.
     * @return Non temporary value holder
     */
    public static <T> MapValue<T> create(T value, TypeInfo<T> type) {
        return new MapValue<>(value, type, false);
    }

    /**
     * Constructs a value holder.
     *
     * @param value       Value instance.
     * @param type        Reified type of instance.
     * @param isTemporary Is temporary.
     * @return Value holder.
     */
    public static <T> MapValue<T> create(T value, TypeInfo<T> type, boolean isTemporary) {
        return new MapValue<>(value, type, isTemporary);
    }

    /**
     * Gets value instance.
     *
     * @return Value instance.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Gets reified type of value instance.
     *
     * @return Reified type of value instance.
     */
    public TypeInfo<T> getType() {
        return this.type;
    }

    /**
     * Returns true if value is temporary.
     *
     * @return True if value is temporary.
     */
    public boolean isTemporary() {
        return this.isTemporary;
    }

    @Override
    public String toString() {
        return "{value="+this.getValue()+", type="+this.getType()+"}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue(), this.getType());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MapValue<?>))
            return super.equals(obj);

        return ((MapValue) obj).getValue() == this.getValue()
                && ((MapValue) obj).getType() == this.getType();
    }
}
