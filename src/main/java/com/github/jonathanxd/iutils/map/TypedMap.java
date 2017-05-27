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
package com.github.jonathanxd.iutils.map;

import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A map that stores a key and a value with a type.
 *
 * Keys are identified by the value type and the key itself, values can be get either by {@link K
 * key} and a pair of {@link K key} and {@link TypeInfo type}.
 *
 * All values have a reified {@link TypeInfo}, make sure to insert correct types.
 *
 * Implementations are free to chose the way to store {@link TypeInfo reified value type}, but keys
 * remains unique, this means that if the same key is associated to a different value or value type,
 * the old key should be replaced.
 *
 * @param <K> Key
 * @param <V> Value.
 * @see java.util.Map
 */
public interface TypedMap<K, V> extends Map<K, V> {

    /**
     * Associates key and a value reified type ({@code type}) to a {@code value}.
     *
     * Obs: Keys are unique, if the same key is associated to a different reified value type, old
     * key should be replaced.
     *
     * @param key   Key to associate
     * @param value Value to associate to key.
     * @param type  Reified type of value.
     * @param <B>   Type of value.
     * @return Replaced value and reified type associated to {@code key}, or {@link Pair#nullPair()}
     * if no one value was replaced.
     */
    <B extends V> Pair<? extends V, TypeInfo<? extends V>> putTyped(K key, B value, TypeInfo<B> type);

    /**
     * Gets the {@link Pair pair} of {@link V value} and {@link TypeInfo<V> reified type} associated
     * to {@code key}.
     *
     * @param key Key to get associated value and type.
     * @return {@link Pair pair} of {@link V value} and {@link TypeInfo<V> reified type} associated
     * to {@code key}, or {@link Pair#nullPair()} if no one value is associated to {@code key}.
     */
    Pair<? extends V, TypeInfo<? extends V>> getTyped(K key);

    /**
     * Gets a {@link List list} of {@link Pair pairs} of {@code value} {@link K key} and {@link
     * TypeInfo<B> reified type}.
     *
     * @param value Value.
     * @param <B>   Type of value.
     * @return {@link List list} of {@link Pair pairs} of {@code value} {@link K key} and {@link
     * TypeInfo<B> reified type}.
     */
    <B extends V> List<Pair<? extends K, TypeInfo<? extends B>>> getValueTyped(B value);

    /**
     * Gets the {@link B value} associated to {@link K key} and {@link TypeInfo<B> reified type}.
     *
     * @param key  Key to get associated value.
     * @param type Reified type of value.
     * @param <B>  Type of Value.
     * @return Value associated to exact {@code key} and {@code type}, if types are mismatch or no
     * one value was set, returns {@code null}.
     */
    <B extends V> B getTyped(K key, TypeInfo<B> type);

    /**
     * Removes value associated to {@code key} regardless the type.
     *
     * @param key Key to remove associated value.
     * @return {@link Pair} of value and type associated to {@code key}, or {@link Pair#nullPair()}
     * if no one value was removed.
     */
    Pair<? extends V, TypeInfo<? extends V>> removeTyped(K key);

    /**
     * Removes value associated to {@code key} and {@code type}.
     *
     * @param key  Key to remove associated value.
     * @param type Value type to remove associated value.
     * @param <B>  Value type.
     * @return Removed value associated to {@code key} and {@code type}, or {@code null} if no one
     * value was removed.
     */
    @SuppressWarnings("unchecked")
    default <B extends V> B removeTyped(K key, TypeInfo<B> type) {
        Iterator<TypedEntry<K, ? extends V>> iter = this.typedEntrySet().iterator();

        while (iter.hasNext()) {
            TypedEntry<K, ? extends V> kTypedEntry = iter.next();

            if (Objects.equals(kTypedEntry.getKey(), key)
                    && Objects.equals(kTypedEntry.getType(), type)) {
                iter.remove();

                return (B) kTypedEntry.getValue();
            }
        }

        return null;
    }

    /**
     * Removes {@code value} associated to {@code key} and {@code type}.
     *
     * @param key   Key to remove associated value.
     * @param type  Value type to remove associated value.
     * @param value Value to remove.
     * @param <B>   Value type.
     * @return True if {@code value} was removed, false otherwise.
     */
    <B extends V> boolean removeTyped(K key, B value, TypeInfo<B> type);

    /**
     * Returns true if this map contains any value associated to {@code key} and {@code type}.
     *
     * @param key  Key to get associated value.
     * @param type Type of associated value.
     * @return True if this map contains any value associated to {@code key} and {@code type}.
     */
    boolean containsTyped(K key, TypeInfo<?> type);

    /**
     * Gets the set of typed entries.
     *
     * Changes in map is not reflected in this set and vice-versa.
     *
     * @return Set of typed entries.
     */
    Set<TypedEntry<K, ? extends V>> typedEntrySet();

    /**
     * Returns a unmodifiable typed map (cached or created).
     *
     * @return Unmodifiable typed map (cached or created).
     */
    TypedMap<K, V> createUnmodifiable();

    /**
     * Typed entry class.
     *
     * @param <K> Key type.
     * @param <B> Reified value type.
     */
    class TypedEntry<K, B> {

        /**
         * Key associated to value.
         */
        private final K key;

        /**
         * Value associated to key.
         */
        private final B value;

        /**
         * Type of value.
         */
        private final TypeInfo<B> type;

        /**
         * Creates a typed entry.
         *
         * @param key   Key associated to value.
         * @param value Value associated to key.
         * @param type  Reified type of value.
         */
        public TypedEntry(K key, B value, TypeInfo<B> type) {
            this.key = key;
            this.value = value;
            this.type = type;
        }

        /**
         * Gets the key.
         *
         * @return Key.
         */
        public K getKey() {
            return this.key;
        }

        /**
         * Gets the value.
         *
         * @return Value.
         */
        public B getValue() {
            return this.value;
        }

        /**
         * Gets the reified type of value.
         *
         * @return Reified type of value.
         */
        public TypeInfo<B> getType() {
            return this.type;
        }

        /**
         * Tries to cast {@code this} to a {@link TypedEntry} of a value of type {@code type}.
         * Returns null if failed.
         *
         * The cast only succeeds if {@code type} is equal to {@link #getType()}.
         *
         * @param type Type to try to cast.
         * @param <LK> Key type.
         * @param <LB> Target type to cast
         * @return a {@link TypedEntry} casted to {@code type}, or null if cast fails.
         */
        @SuppressWarnings("unchecked")
        public <LK, LB> TypedEntry<LK, LB> as(TypeInfo<LB> type) {
            if (this.getType().compareTo(type) == 0)
                return (TypedEntry<LK, LB>) this;

            return null;
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof TypedEntry<?, ?>))
                return super.equals(obj);

            TypedEntry objEntry = (TypedEntry) obj;

            return Objects.equals(this.getKey(), objEntry.getKey())
                    && Objects.equals(this.getValue(), objEntry.getValue())
                    && Objects.equals(this.getType(), objEntry.getType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getKey(), this.getValue(), this.getType());
        }
    }
}
