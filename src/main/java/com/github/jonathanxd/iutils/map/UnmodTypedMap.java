/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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

import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnmodTypedMap<K, V> implements TypedMap<K, V> {

    private final TypedMap<K, V> original;

    public UnmodTypedMap(TypedMap<K, V> original) {
        this.original = original;
    }

    @Override
    public <B extends V> Pair<? extends V, TypeInfo<? extends V>> putTyped(K key, B value, TypeInfo<B> type) {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public Pair<? extends V, TypeInfo<? extends V>> getTyped(K key) {
        return this.original.getTyped(key);
    }

    @Override
    public <B extends V> List<Pair<? extends K, TypeInfo<? extends B>>> getValueTyped(B value) {
        return this.original.getValueTyped(value);
    }

    @Override
    public <B extends V> B getTyped(K key, TypeInfo<B> type) {
        return this.original.getTyped(key, type);
    }

    @Override
    public Pair<? extends V, TypeInfo<? extends V>> removeTyped(K key) {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public <B extends V> B removeTyped(K key, TypeInfo<B> type) {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public <B extends V> boolean removeTyped(K key, B value, TypeInfo<B> type) {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public boolean containsTyped(K key, TypeInfo<?> type) {
        return this.original.containsTyped(key, type);
    }

    @Override
    public Set<TypedEntry<K, ? extends V>> typedEntrySet() {
        return Collections.unmodifiableSet(this.original.typedEntrySet());
    }

    @Override
    public TypedMap<K, V> createUnmodifiable() {
        return this;
    }

    @Override
    public int size() {
        return this.original.size();
    }

    @Override
    public boolean isEmpty() {
        return this.original.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.original.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.original.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.original.get(key);
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Immutable.");
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(this.original.keySet());
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(this.original.values());
    }

    @Override
    public String toString() {
        return this.original.toString();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = this.original.entrySet();

        return ViewCollections.setMapped(set,
                (kvEntry, entryIterator) -> IteratorUtil.mapped(kvEntry, entryIterator, ImmutableBackedEntry::new),
                set::add,
                set::remove);
    }

    static class ImmutableBackedEntry<K, V> implements Entry<K, V> {

        private final Entry<K, V> original;

        ImmutableBackedEntry(Entry<K, V> original) {
            this.original = original;
        }

        @Override
        public K getKey() {
            return this.original.getKey();
        }

        @Override
        public V getValue() {
            return this.original.getValue();
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("Immutable!");
        }

        @Override
        public String toString() {
            return this.original.toString();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj) || this.original.equals(obj);
        }

        @Override
        public int hashCode() {
            return this.original.hashCode();
        }
    }
}
