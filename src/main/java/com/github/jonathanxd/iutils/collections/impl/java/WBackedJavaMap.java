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
package com.github.jonathanxd.iutils.collections.impl.java;

import com.github.jonathanxd.iutils.collections.EntryW;
import com.github.jonathanxd.iutils.collections.MapW;
import com.github.jonathanxd.iutils.collections.SetW;
import com.github.jonathanxd.iutils.collections.impl.MutationOperationOnImmutableData;
import com.github.jonathanxd.iutils.collections.mutable.MutableMapW;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class WBackedJavaMap<K, V> implements Map<K, V> {

    private final MapW<K, V> wrapped;

    public WBackedJavaMap(MapW<K, V> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int size() {
        return this.wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsKey(Object key) {
        return this.wrapped.containsKey((K) key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsValue(Object value) {
        return this.wrapped.containsValue((V) value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        return this.wrapped.get((K) key);
    }

    @Override
    public V put(K key, V value) {
        this.checkMutation();

        OptObject<V> opt = this.wrapped.getOpt(key);

        this.wrapped.put(key, value);

        return opt.orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(Object key) {
        this.checkMutation();

        OptObject<V> opt = this.wrapped.getOpt((K) key);

        this.wrapped.remove((K) key);

        return opt.orElse(null);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.checkMutation();

        m.forEach(this.wrapped::put);
    }

    @Override
    public void clear() {
        this.checkMutation();
        this.wrapped.clear();
    }

    @Override
    public Set<K> keySet() {
        return new WBackedJavaSet<>(this.wrapped.keys());
    }

    @Override
    public Collection<V> values() {
        return new WBackedJavaCollection<>(this.wrapped.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {

        SetW<EntryW<K, V>> entries = this.wrapped.entries();

        return new WBackedJavaEntrySet<>(entries);
    }

    private void checkMutation() {
        if (!(this.wrapped instanceof MutableMapW<?, ?>))
            throw new MutationOperationOnImmutableData();
    }
}
