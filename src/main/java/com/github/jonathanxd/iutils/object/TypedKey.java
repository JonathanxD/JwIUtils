/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.data.DataBase;
import com.github.jonathanxd.iutils.map.TempTypedMap;
import com.github.jonathanxd.iutils.map.TypedMap;
import com.github.jonathanxd.iutils.type.TypeInfo;

/**
 * A Typed data object key which holds a type {@link T}.
 */
public final class TypedKey<T> {

    /**
     * Key value
     */
    private final Object key;

    /**
     * Type of key
     */
    private final TypeInfo<T> type;

    /**
     * Constructs a TypedKey holding a object {@code key} instance and value {@code type}.
     *
     * @param key  Key object instance.
     * @param type Type of value.
     */
    public TypedKey(Object key, TypeInfo<T> type) {
        this.key = key;
        this.type = type;
    }

    /**
     * Gets the key
     *
     * @return Key
     */
    public Object getKey() {
        return this.key;
    }

    /**
     * Gets the type of key.
     *
     * @return Type of key.
     */
    public TypeInfo<T> getType() {
        return this.type;
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code data}.
     *
     * @param data Data to find associated value.
     * @return Associated value or null if not present.
     */
    public T getOrNull(DataBase<?> data) {
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
     * Gets the value associated to {@link #key} and {@link #type} in {@code data} or sets to {@code
     * value} if no one value was set.
     *
     * @param data Data to find associated value.
     * @return Associated value if present, {@code value} otherwise.
     */
    public T getOrSet(DataBase<?> data, T value) {
        return data.getOrSet(this.getKey(), value, this.getType());
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code map} or sets to {@code
     * value} if no one value was set.
     *
     * @param map Map to find associated value.
     * @return Associated value if present, {@code value} otherwise.
     */
    public T getOrSet(TypedMap<Object, ? super T> map, T value) {
        if (map.containsTyped(this.getKey(), this.getType()))
            map.putTyped(this.getKey(), value, this.getType());

        return map.getTyped(this.getKey(), this.getType());
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code data} or evaluate
     * {@code value} lazy and sets to value if no one was set.
     *
     * @param data Data to find associated value.
     * @return Associated value if present, {@code value} otherwise.
     */
    public T getOrSetLazily(DataBase<?> data, Lazy<T> value) {
        return data.getOrSetLazily(this.getKey(), value, this.getType());
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code map} or evaluate {@code
     * value} lazy and sets to value if no one was set.
     *
     * @param map Map to find associated value.
     * @return Associated value if present, {@code value} otherwise.
     */
    public T getOrSetLazily(TypedMap<Object, ? super T> map, Lazy<T> value) {
        if (map.containsTyped(this.getKey(), this.getType()))
            map.putTyped(this.getKey(), value.get(), this.getType());

        return map.getTyped(this.getKey(), this.getType());
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code map} or return {@code
     * orValue} if the value is not present.
     *
     * @param map     Map to find associated value.
     * @param orValue Value to return if no one value is associated to @link #key} and {@link
     *                #type}.
     * @return Associated value if present, {@code orValue} otherwise.
     */
    public T getOrElse(DataBase<?> map, T orValue) {
        return map.getOptional(this.getKey(), this.getType()).orElse(orValue);
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code map} or return {@code
     * orValue} if the value is not present.
     *
     * @param map     Map to find associated value.
     * @param orValue Value to return if no one value is associated to @link #key} and {@link
     *                #type}.
     * @return Associated value if present, {@code orValue} otherwise.
     */
    public T getOrElse(TypedMap<Object, ? super T> map, T orValue) {
        if (!map.containsTyped(this.getKey(), this.getType()))
            return orValue;

        return map.getTyped(this.getKey(), this.getType());
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code map} or return
     * evaluated {@code orValue} if the value is not present.
     *
     * @param map     Map to find associated value.
     * @param orValue Value to return if no one value is associated to @link #key} and {@link
     *                #type}.
     * @return Associated value if present, evaluated {@code orValue} otherwise.
     */
    public T getOrElseLazily(DataBase<?> map, Lazy<T> orValue) {
        return map.getOptional(this.getKey(), this.getType()).orElse(orValue.get());
    }

    /**
     * Gets the value associated to {@link #key} and {@link #type} in {@code map} or return
     * evaluated {@code orValue} if the value is not present.
     *
     * @param map     Map to find associated value.
     * @param orValue Value to return if no one value is associated to @link #key} and {@link
     *                #type}.
     * @return Associated value if present, evaluated {@code orValue} otherwise.
     */
    public T getOrElseLazily(TypedMap<Object, ? super T> map, Lazy<T> orValue) {
        if (!map.containsTyped(this.getKey(), this.getType()))
            return orValue.get();

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
    public boolean contains(DataBase<?> data) {
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
    public T remove(DataBase<?> data) {
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
    public Pair<?, TypeInfo<?>> removeAny(DataBase<?> data) {
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
    public Pair<?, TypeInfo<?>> set(DataBase<?> data, T value) {
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

    /**
     * Sets value associated to {@link #key} and {@link #type} to {@code value}.
     *
     * @param data  Data to set associated value.
     * @param value Value to associated to {@link #key} and {@link #type}.
     * @return {@link Pair} of replaced value and value type.
     */
    public Pair<?, TypeInfo<?>> set(DataBase<?> data, T value, boolean isTemporary) {
        return data.set(this.getKey(), value, this.getType(), isTemporary);
    }

    /**
     * Sets value associated to {@link #key} and {@link #type} to {@code value}.
     *
     * @param map   Map to set associated value.
     * @param value Value to associated to {@link #key} and {@link #type}.
     * @return {@link Pair} of replaced value and value type.
     */
    @SuppressWarnings("unchecked")
    public Pair<?, TypeInfo<?>> set(TempTypedMap<Object, ? super T> map, T value, boolean isTemporary) {
        return (Pair<?, TypeInfo<?>>) map.putTyped(this.getKey(), value, this.getType(), isTemporary);
    }
}
