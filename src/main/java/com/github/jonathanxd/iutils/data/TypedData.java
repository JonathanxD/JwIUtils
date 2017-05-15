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
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.condition.Conditions;
import com.github.jonathanxd.iutils.map.BackedTypedMap;
import com.github.jonathanxd.iutils.map.TypedMap;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.Optional;

/**
 * A {@link DataBase} which hold type information.
 */
public final class TypedData implements DataBase {

    private final TypedMap<Object, Object> typedMap = new BackedTypedMap<>();

    public TypedData() {
    }

    /**
     * Constructs a {@link TypedMap} with all values of {@code map}.
     *
     * @param map Base map to get values.
     */
    public TypedData(TypedMap<Object, Object> map) {
        Conditions.checkNotNull(map, "Map cannot be null");

        for (TypedMap.TypedEntry<Object, ?> objectTypedEntry : map.typedEntrySet()) {
            this.typedMap.putTyped(objectTypedEntry.getKey(), objectTypedEntry.getValue(), objectTypedEntry.getType().cast());
        }
    }

    /**
     * Associates {@code key} and {@code type} to {@code value}.
     *
     * @param key   Key to associate.
     * @param value Value to associate.
     * @param type  Type of value to associate.
     * @param <T>   Type of value.
     * @return {@link Pair} of replaced value and value type associated to {@code key} or a {@link
     * Pair#nullPair()}if no one value was replaced.
     */
    public <T> Pair<?, TypeInfo<?>> set(Object key, T value, TypeInfo<T> type) {
        return this.typedMap.putTyped(key, value, type);
    }

    /**
     * Removes the value associated to {@code key} regardless the type.
     *
     * @param key Key to remove associated value.
     * @return {@link Pair} of value and type associated to {@code key}, or {@link Pair#nullPair()}
     * if no one was removed.
     */
    public Pair<?, TypeInfo<?>> remove(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return this.typedMap.removeTyped(key);
    }

    /**
     * Removes the value associated to {@code key} and {@code type}.
     *
     * @param key  Key to remove associated value.
     * @param type Type of value.
     * @return Removed value associated to {@code key} and {@code type}, or null if no one value was
     * removed.
     */
    public <T> T remove(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return this.typedMap.removeTyped(key, type);
    }

    /**
     * Removes the {@code value} associated to {@code key} and {@code type}.
     *
     * @param key   Key to remove associated value.
     * @param type  Type of value.
     * @param value Value to remove.
     * @return True if element was removed.
     */
    public <T> boolean remove(Object key, T value, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Value cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return this.typedMap.removeTyped(key, value, type);
    }

    /**
     * Gets the value associated to {@code key} and {@code type}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @return Value associated to {@code key} and {@code type} or null if not found.
     */
    public <T> T getOrNull(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(key, "Type cannot be null");

        return this.typedMap.getTyped(key, type);
    }

    /**
     * Gets the value associated to {@code key} and {@code type}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @return {@link Optional} of value associated to {@code key} and {@code type} or {@link
     * Optional#empty()} if not found.
     */
    public <T> Optional<T> getOptional(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return Optional.ofNullable(this.getOrNull(key, type));
    }

    /**
     * Gets the value associated to {@code key} and {@code type} as a value of type {@link A}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @param <A>  Type to cast.
     * @return Value associated to {@code key} and {@code type} as a value of type {@link A} or null
     * if not found.
     */
    @SuppressWarnings("unchecked")
    public <T, A> A getOrNullAs(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return (A) this.typedMap.getTyped(key, type);
    }

    /**
     * Gets the value associated to {@code key} and {@code type} as a value of type {@link A}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @param <T>  Type of associated value.
     * @param <A>  Type to cast.
     * @return {@link Optional} of value associated to {@code key} and {@code type} as a value of
     * type {@link A} or {@link Optional#empty()} if not found.
     */
    @SuppressWarnings("unchecked")
    public <T, A> Optional<A> getOptionalAs(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return Optional.ofNullable((A) this.getOrNull(key, type));
    }

    /**
     * Returns true if this data has any value associated to {@code key}.
     *
     * @param key Key to check if any value is associated.
     * @return True if this data has any value associated to {@code key}.
     */
    public boolean contains(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return !this.typedMap.containsKey(key);
    }

    /**
     * Returns true if this data has any value associated to both {@code key} and {@code type}.
     *
     * @param key  Key to check if any value is associated.
     * @param type Type to check if any value is associated.
     * @return True if this data has any value associated to both {@code key} and {@code type}.
     */
    public boolean contains(Object key, TypeInfo<?> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return this.typedMap.containsTyped(key, type);
    }

    /**
     * Gets value associated to {@code key} and {@code type} if present, otherwise sets to {@code
     * value} and returns {@code value}.
     *
     * @param key   Key.
     * @param value Value to set if none value is set to {@code key}.
     * @param type  Type of value.
     * @param <T>   Type of value.
     * @return Value associated to {@code key} and {@code type} or {@code value} if no one is
     * defined to {@code key} and {@code type}.
     */
    public <T> T getOrSet(Object key, T value, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        if (!this.contains(key, type))
            this.set(key, value, type);

        return this.getOrNull(key, type);
    }

    /**
     * Gets value associated to {@code key} nad {@code type} if present, otherwise sets to {@code
     * value} and returns {@code value}.
     *
     * @param key   Key.
     * @param value Lazy value to set if none value is associated to {@code key} and {@code type}.
     * @param type  Type of value.
     * @param <T>   Type of value.
     * @return Value associated to {@code key} and {@code type} or {@code value} if no one is
     * associated to {@code key} and {@code type}.
     */
    public <T> T getOrSetLazily(Object key, Lazy<T> value, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Lazy value evaluator cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        if (!this.contains(key, type))
            this.set(key, value.get(), type);

        return this.getOrNull(key, type);
    }

    /**
     * Returns true if this data holder is empty.
     *
     * @return True if this data holder is empty.
     */
    public boolean isEmpty() {
        return this.typedMap.isEmpty();
    }

    /**
     * Creates a copy of {@code this} data.
     *
     * @return Copy of {@code this} data.
     */
    public TypedData copy() {
        return new TypedData(this.getTypedDataMap());
    }

    @Override
    public TypedMap<Object, Object> getTypedDataMap() {
        return this.typedMap.createUnmodifiable();
    }
}
