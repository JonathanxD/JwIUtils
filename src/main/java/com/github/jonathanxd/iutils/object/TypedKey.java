/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.data.TypedData;
import com.github.jonathanxd.iutils.map.TypedMap;
import com.github.jonathanxd.iutils.type.TypeInfo;

/**
 * A Typed data object key which holds a type {@link T}.
 */
public final class TypedKey<T> {

    private final Object key;
    private final TypeInfo<T> type;

    public TypedKey(Object key, TypeInfo<T> type) {
        this.key = key;
        this.type = type;
    }

    public Object getKey() {
        return this.key;
    }

    public TypeInfo<T> getType() {
        return this.type;
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code data}.
     *
     * @param data Data to find associated value.
     * @return Associated value or null if not present.
     */
    public T getOrNull(TypedData data) {
        return data.getOrNull(this.getKey(), this.getType());
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code map}.
     *
     * @param map Map to find associated value.
     * @return Associated value or null if not present.
     */
    public T getOrNull(TypedMap<Object, ? super T> map) {
        return map.getTyped(this.getKey(), this.getType());
    }

    /**
     * Returns true if {@code data} does not contains a value associated to {@link #key} and {@link
     * #type}.
     *
     * @param data Data to check if has any value associated to key and type.
     * @return True if {@code data} does not contains a value associated to {@link #key} and {@link
     * #type}.
     */
    public boolean contains(TypedData data) {
        return data.contains(this.getKey(), this.getType());
    }

    /**
     * Returns true if {@code map} does not contains a value associated to {@link #key} and {@link
     * #type}.
     *
     * @param map Map to check if has any value associated to key and type.
     * @return True if {@code map} does not contains a value associated to {@link #key} and {@link
     * #type}.
     */
    public boolean contains(TypedMap<Object, ? super T> map) {
        return map.containsTyped(this.getKey(), this.getType());
    }

    /**
     * Removes value associated to {@link #key} and {@link #type}.
     *
     * @param data Data to remove associated value.
     * @return Value associated to {@link #key} and {@link #type}.
     */
    public T remove(TypedData data) {
        return data.remove(this.getKey(), this.getType());
    }

    /**
     * Removes value associated to {@link #key} and {@link #type}.
     *
     * @param map Map to remove associated value.
     * @return Value associated to {@link #key} and {@link #type}.
     */
    public T remove(TypedMap<Object, ? super T> map) {
        return map.removeTyped(this.getKey(), this.getType());
    }

    /**
     * Removes first value associated to {@link #key} regardless the {@link #type}.
     *
     * @param data Data to remove associated value.
     * @return Value associated to {@link #key} regardless the {@link #type}.
     */
    public Pair<?, TypeInfo<?>> removeAny(TypedData data) {
        return data.remove(this.getKey());
    }

    /**
     * Removes first value associated to {@link #key} regardless the {@link #type}.
     *
     * @param map Map to remove associated value.
     * @return Value associated to {@link #key} regardless the {@link #type}.
     */
    @SuppressWarnings("unchecked")
    public Pair<?, TypeInfo<?>> removeAny(TypedMap<Object, ? super T> map) {
        return (Pair<?, TypeInfo<?>>) map.removeTyped(this.getKey());
    }

    /**
     * Sets value associated to {@link #key} and {@link #type} to {@code value}.
     *
     * @param data  Data to set associated value.
     * @param value Value to associated to {@link #key} and {@link #type}.
     * @return {@link Pair} of replaced value and value type.
     */
    public Pair<?, TypeInfo<?>> set(TypedData data, T value) {
        return data.set(this.getKey(), value, this.getType());
    }

    /**
     * Sets value associated to {@link #key} and {@link #type} to {@code value}.
     *
     * @param map   Map to set associated value.
     * @param value Value to associated to {@link #key} and {@link #type}.
     * @return {@link Pair} of replaced value and value type.
     */
    @SuppressWarnings("unchecked")
    public Pair<?, TypeInfo<?>> set(TypedMap<Object, ? super T> map, T value) {
        return (Pair<?, TypeInfo<?>>) map.putTyped(this.getKey(), value, this.getType());
    }


}
