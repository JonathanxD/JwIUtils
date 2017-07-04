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
package com.github.jonathanxd.iutils.collectionsw;

import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaMapEntry;

import java.util.Map;
import java.util.Objects;

/**
 * An entry of element of type {@link K} associated to an element of type {@link V}.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public interface EntryW<K, V> {

    /**
     * Returns Java equivalent instance.
     *
     * @return Java equivalent instance.
     */
    Map.Entry<K, V> asJavaEntry();

    /**
     * Gets the key.
     *
     * @return Key.
     */
    K getKey();

    /**
     * Gets the value.
     *
     * @return Value.
     */
    V getValue();

    /**
     * Creates a copy of this entry.
     *
     * @return Copy of this entry.
     */
    EntryW<K, V> copy();

    interface Mut<K, V> extends EntryW<K, V> {
        /**
         * Sets the key of entry.
         *
         * @param key Key to set.
         * @return Old key associated to value.
         */
        K setKey(K key);

        /**
         * Sets the value of entry.
         *
         * @param value Value to set.
         * @return Old value associated to key.
         */
        V setValue(V value);
    }

    /**
     * Immutable entry.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     */
    final class Immutable<K, V> implements EntryW<K, V> {

        private final K key;
        private final V value;

        public Immutable(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Map.Entry<K, V> asJavaEntry() {
            return new WBackedJavaMapEntry<>(this);
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public EntryW<K, V> copy() {
            return new Immutable<>(this.key, this.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getKey(), this.getValue());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof EntryW<?, ?>)
                return Objects.equals(((EntryW) obj).getKey(), this.getKey())
                        && Objects.equals(((EntryW) obj).getValue(), this.getValue());

            return super.equals(obj);
        }
    }

    /**
     * Mutable entry.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     */
    final class Mutable<K, V> implements EntryW.Mut<K, V> {
        private K key;
        private V value;

        public Mutable(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Map.Entry<K, V> asJavaEntry() {
            return new WBackedJavaMapEntry<>(this);
        }

        @Override
        public K setKey(K key) {
            Objects.requireNonNull(key);
            K old = this.key;
            this.key = key;
            return old;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V setValue(V value) {
            Objects.requireNonNull(value);
            V old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public EntryW<K, V> copy() {
            return new Mutable<>(this.key, this.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getKey(), this.getValue());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof EntryW<?, ?>)
                return Objects.equals(((EntryW) obj).getKey(), this.getKey())
                        && Objects.equals(((EntryW) obj).getValue(), this.getValue());

            return super.equals(obj);
        }
    }
}
