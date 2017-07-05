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
import com.github.jonathanxd.iutils.map.BackedTempTypedMap;
import com.github.jonathanxd.iutils.map.BackedTypedMap;
import com.github.jonathanxd.iutils.map.TempTypedMap;
import com.github.jonathanxd.iutils.map.TypedMap;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.Pairs;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.Map;
import java.util.Optional;

/**
 * Data storage
 */
public final class Data implements DataBase<Data> {

    private final TempTypedMap<Object, Object> map = new BackedTempTypedMap<>();

    private final Data parent;

    /**
     * Constructs a Data with parent holder {@code parent}.
     *
     * @param parent Parent data holder.
     */
    public Data(Data parent) {
        this.parent = parent;
    }

    /**
     * Constructs a data without parent holder.
     */
    public Data() {
        this((Data) null);
    }

    /**
     * Creates a Data from a map of default values without parent holder.
     *
     * @param defaults Map of default values.
     */
    public Data(Map<Object, Object> defaults) {
        this(null, defaults);
    }

    /**
     * Creates a Data from a map of default values with parent holder {@code parent}.
     *
     * @param parent   Parent data holder.
     * @param defaults Map of default values.
     */
    public Data(Data parent, Map<Object, Object> defaults) {
        this(parent);
        defaults.forEach(this::set);
    }

    @Override
    public Data getMainData() {
        return DataBase.super.getMainData();
    }

    @Override
    public Data getParent() {
        return this.parent;
    }

    /**
     * Sets the key to value.
     *
     * @param key   Key.
     * @param value Value.
     * @return Old value associated to key.
     */
    public Object set(Object key, Object value) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Value cannot be null");

        return this.getMap().put(key, value);
    }

    @Override
    public <T> Pair<?, TypeInfo<?>> set(Object key, T value, TypeInfo<T> type, boolean isTemporary) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Value cannot be null");
        Conditions.checkNotNull(type, "Type cannot be null");

        if(isTemporary)
            return this.getMap().putTypedTemporary(key, value, type);

        return this.getMap().putTyped(key, value, type);
    }

    /**
     * Removes the value linked to {@code key}.
     *
     * @param key Key.
     * @return Old value linked to {@code key}.
     */
    public Pair<?, TypeInfo<?>> remove(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        Object removed = this.getMap().remove(key);

        if(removed == null)
            return Pairs.nullPair();

        return Pair.of(removed, TypeInfo.of(removed.getClass()));
    }

    /**
     * Removes the {@code value} linked to {@code key}.
     *
     * @param key   Key.
     * @param value Value to remove.
     * @return True if removed with success.
     */
    public boolean remove(Object key, Object value) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Value cannot be null");

        return this.getMap().remove(key, value);
    }

    @Override
    public boolean contains(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");
        return this.getMap().containsKey(key);
    }

    /**
     * Gets the value linked to {@code key}.
     *
     * @param key Key.
     * @return Value linked to {@code key}.
     */
    public Object get(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return this.getMap().get(key);
    }

    /**
     * Gets the {@link Optional} value linked to {@code key}.
     *
     * @param key Key.
     * @return {@link Optional} of value linked to {@code key}, or a {@link Optional#empty()} if no
     * one value is linked to {@code key}.
     */
    public Optional<Object> getOptional(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return Optional.ofNullable(this.getMap().get(key));
    }

    /**
     * Gets the value linked to {@code key} as {@link T}.
     *
     * @param key Key.
     * @return Value linked to {@code key} as {@link T}.
     */
    @SuppressWarnings("unchecked")
    public <T> T getAs(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return (T) this.get(key);
    }

    /**
     * Gets the {@link Optional} value linked to {@code key} as {@link Optional} of {@link T}.
     *
     * @param key Key.
     * @param <T> Type to cast the value.
     * @return {@link Optional} of value linked to {@code key}, or a {@link Optional#empty()} if no
     * one value is linked to {@code key}.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOptionalAs(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return (Optional<T>) this.getOptional(key);
    }

    /**
     * Gets value linked to {@code key} if present, otherwise sets to {@code value} and returns
     * {@code value}.
     *
     * @param key   Key.
     * @param value Value to set if none value is set to {@code key}.
     * @param <T>   Type of value.
     * @return Value linked to {@code key} or {@code value} if none is defined to {@code key}.
     */
    public <T> T getOrSet(Object key, T value) {
        if (!this.contains(key))
            this.set(key, value);

        return this.getAs(key);
    }

    /**
     * Gets value linked to {@code key} if present, otherwise sets to {@code value} and returns
     * {@code value}.
     *
     * @param key   Key.
     * @param value Lazy value to set if none value is set to {@code key}.
     * @param <T>   Type of value.
     * @return Value linked to {@code key} or {@code value} if none is defined to {@code key}.
     */
    public <T> T getOrSetLazily(Object key, Lazy<T> value) {
        if (!this.contains(key))
            this.set(key, value.get());

        return this.getAs(key);
    }

    @Override
    public boolean isEmpty() {
        return this.getMap().isEmpty();
    }

    private TempTypedMap<Object, Object> getMap() {
        return this.map;
    }

    /**
     * Returns immutable version of data map.
     *
     * @return Immutable version of data map.
     */
    public Map<Object, Object> getDataMap() {
        return this.map.createUnmodifiable();
    }

    @Override
    public Data copy() {
        return new Data(this.getDataMap());
    }

    @Override
    public Data copy(Data parent) {
        return new Data(parent, this.getDataMap());
    }

    @Override
    public TypedMap<Object, Object> getTypedDataMap() {
        return this.map.createUnmodifiable();
    }

    // Backings

    @Override
    public <T> Pair<?, TypeInfo<?>> set(Object key, T value, TypeInfo<T> type) {
        Object set = this.set(key, value);

        if(set == null)
            return Pairs.nullPair();

        return Pair.of(set, TypeInfo.of(set.getClass()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T remove(Object key, TypeInfo<T> type) {
        return (T) this.remove(key).toOptional().map(Pair::getFirst).orElse(null);
    }

    @Override
    public <T> boolean remove(Object key, T value, TypeInfo<T> type) {
        return this.remove(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOrNull(Object key, TypeInfo<T> type) {
        return (T) this.get(key);
    }

    @Override
    public <T> Optional<T> getOptional(Object key, TypeInfo<T> type) {
        return Optional.ofNullable(this.getOrNull(key, type));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, A> A getOrNullAs(Object key, TypeInfo<T> type) {
        return (A) this.getOrNull(key, type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, A> Optional<A> getOptionalAs(Object key, TypeInfo<T> type) {
        return this.getOptional(key, type).map(t -> (A) t);
    }

    @Override
    public boolean contains(Object key, TypeInfo<?> type) {
        return this.contains(key);
    }

    @Override
    public <T> T getOrSet(Object key, T value, TypeInfo<T> type) {
        return this.getOrSet(key, value);
    }

    @Override
    public <T> T getOrSetLazily(Object key, Lazy<T> value, TypeInfo<T> type) {
        return this.getOrSetLazily(key, value);
    }
}
