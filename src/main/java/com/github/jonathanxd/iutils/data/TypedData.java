/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.condition.Conditions;
import com.github.jonathanxd.iutils.map.BackedTempTypedMap;
import com.github.jonathanxd.iutils.map.BackedTypedMap;
import com.github.jonathanxd.iutils.map.TempTypedMap;
import com.github.jonathanxd.iutils.map.TypedMap;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.Optional;

/**
 * A {@link DataBase} which hold type information.
 */
public final class TypedData implements DataBase<TypedData> {

    private final TempTypedMap<Object, Object> typedMap = new BackedTempTypedMap<>();
    private final TypedData parent;

    /**
     * Constructs typed data with parent holder {@code parent}.
     *
     * @param parent Parent holder.
     */
    public TypedData(TypedData parent) {
        this.parent = parent;
    }

    /**
     * Creates a typed data.
     */
    public TypedData() {
        this((TypedData) null);
    }

    /**
     * Constructs a {@link TypedMap} with all values of {@code map}.
     *
     * @param map Base map to get values.
     */
    public TypedData(TypedMap<Object, Object> map) {
        this(null, map);
    }

    /**
     * Constructs a {@link TypedMap} with all values of {@code map}  with parent holder {@code
     * parent}.
     *
     * @param parent Parent data holder.
     * @param map    Base map to get values.
     */
    public TypedData(TypedData parent, TypedMap<Object, Object> map) {
        this(parent);
        Conditions.checkNotNull(map, "Map cannot be null");

        for (TypedMap.TypedEntry<Object, ?> objectTypedEntry : map.typedEntrySet()) {
            this.typedMap.putTyped(objectTypedEntry.getKey(), objectTypedEntry.getValue(), objectTypedEntry.getType().cast());
        }
    }

    @Override
    public TypedData getMainData() {
        return DataBase.super.getMainData();
    }

    @Override
    public TypedData getParent() {
        return this.parent;
    }

    @Override
    public <T> Pair<?, TypeInfo<?>> set(Object key, T value, TypeInfo<T> type, boolean isTemporary) {

        if(isTemporary)
            return this.typedMap.putTypedTemporary(key, value, type);

        return this.typedMap.putTyped(key, value, type);
    }

    @Override
    public Pair<?, TypeInfo<?>> remove(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return this.typedMap.removeTyped(key);
    }

    @Override
    public <T> T remove(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return this.typedMap.removeTyped(key, type);
    }

    @Override
    public <T> boolean remove(Object key, T value, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Value cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return this.typedMap.removeTyped(key, value, type);
    }

    @Override
    public <T> T getOrNull(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(key, "Type cannot be null");

        return this.typedMap.getTyped(key, type);
    }

    @Override
    public <T> Optional<T> getOptional(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return Optional.ofNullable(this.getOrNull(key, type));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, A> A getOrNullAs(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return (A) this.typedMap.getTyped(key, type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, A> Optional<A> getOptionalAs(Object key, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return Optional.ofNullable((A) this.getOrNull(key, type));
    }

    @Override
    public boolean contains(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return !this.typedMap.containsKey(key);
    }

    @Override
    public boolean contains(Object key, TypeInfo<?> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        return this.typedMap.containsTyped(key, type);
    }

    @Override
    public <T> T getOrSet(Object key, T value, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        if (!this.contains(key, type))
            this.set(key, value, type);

        return this.getOrNull(key, type);
    }

    @Override
    public <T> T getOrSetLazily(Object key, Lazy<T> value, TypeInfo<T> type) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Lazy value evaluator cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        if (!this.contains(key, type))
            this.set(key, value.get(), type);

        return this.getOrNull(key, type);
    }

    @Override
    public boolean isEmpty() {
        return this.typedMap.isEmpty();
    }

    @Override
    public TypedData copy() {
        return new TypedData(this.getTypedDataMap());
    }

    @Override
    public TypedData copy(TypedData parent) {
        return new TypedData(parent, this.getTypedDataMap());
    }

    @Override
    public TypedMap<Object, Object> getTypedDataMap() {
        return this.typedMap.createUnmodifiable();
    }
}
