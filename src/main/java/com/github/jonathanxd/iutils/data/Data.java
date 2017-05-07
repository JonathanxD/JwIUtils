/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import com.github.jonathanxd.iutils.object.Lazy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Data storage
 */
public final class Data {

    private final Map<Object, Object> map = new HashMap<>();
    private final Map<Object, Object> unmodifiable = Collections.unmodifiableMap(this.getMap());

    public Data() {
    }

    /**
     * Creates a Data from a map of default values.
     *
     * @param defaults Map of default values.
     */
    public Data(Map<Object, Object> defaults) {
        defaults.forEach(this::set);
    }

    /**
     * Sets the key to value.
     *
     * @param key   Key.
     * @param value Value.
     */
    public void set(Object key, Object value) {
        Conditions.checkNotNull(key, "Key cannot be null");
        Conditions.checkNotNull(value, "Value cannot be null");

        this.getMap().put(key, value);
    }

    /**
     * Removes the value linked to {@code key}.
     *
     * @param key Key.
     * @return Old value linked to {@code key}.
     */
    public Object remove(Object key) {
        Conditions.checkNotNull(key, "Key cannot be null");

        return this.getMap().remove(key);
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

    /**
     * Returns true if any value is linked to {@code key}.
     *
     * @param key Key.
     * @return True if any value is linked to {@code key}.
     */
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

    /**
     * Returns true if the data map is empty.
     *
     * @return True if the data map is empty.
     */
    public boolean isEmpty() {
        return this.getMap().isEmpty();
    }

    private Map<Object, Object> getMap() {
        return this.map;
    }

    /**
     * Returns immutable version of data map.
     *
     * @return Immutable version of data map.
     */
    public Map<Object, Object> getDataMap() {
        return this.unmodifiable;
    }

    /**
     * Creates a copy of {@code this} data.
     *
     * @return Copy of {@code this} data.
     */
    public Data copy() {
        return new Data(this.getDataMap());
    }
}
