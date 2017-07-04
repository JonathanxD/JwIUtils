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
package com.github.jonathanxd.iutils.collectionsw.impl.mutable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JavaSubMap<K, V> implements Map<K, V> {

    private final Map<K, V> parent;
    private final int fromIndex;
    private int size;

    public JavaSubMap(Map<K, V> parent, int fromIndex, int toIndex) {
        this.parent = parent;
        this.fromIndex = fromIndex;
        this.size = toIndex - fromIndex;
    }

    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        SubEntryIterator subEntryIterator = new SubEntryIterator(parent.entrySet().iterator());

        while (subEntryIterator.hasNext()) {
            if (Objects.equals(subEntryIterator.next().getKey(), key))
                return true;
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        SubEntryIterator subEntryIterator = new SubEntryIterator(parent.entrySet().iterator());

        while (subEntryIterator.hasNext()) {
            if (Objects.equals(subEntryIterator.next().getValue(), value))
                return true;
        }

        return false;
    }

    @Override
    public V get(Object key) {
        SubEntryIterator subEntryIterator = new SubEntryIterator(parent.entrySet().iterator());

        while (subEntryIterator.hasNext()) {
            Entry<K, V> next = subEntryIterator.next();

            if (Objects.equals(next.getKey(), key))
                return next.getValue();
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        return this.parent.put(key, value);
    }

    @Override
    public V remove(Object key) {
        SubEntryIterator subEntryIterator = new SubEntryIterator(parent.entrySet().iterator());

        while (subEntryIterator.hasNext()) {
            Entry<K, V> next = subEntryIterator.next();

            if (Objects.equals(next.getKey(), key)) {
                V value = next.getValue();

                this.parent.remove(key, value);

                return value;
            }
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.parent.putAll(m);
    }

    @Override
    public void clear() {
        SubEntryIterator subEntryIterator = new SubEntryIterator(parent.entrySet().iterator());

        while (subEntryIterator.hasNext()) {
            subEntryIterator.remove();
        }
    }

    @Override
    public Set<K> keySet() {
        return new JavaSubSet<>(this.parent.keySet(), this.fromIndex, this.fromIndex + this.size);
    }

    @Override
    public Collection<V> values() {
        return new JavaSubCollection<>(this.parent.values(), this.fromIndex, this.fromIndex + this.size);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new JavaSubSet<>(this.parent.entrySet(), this.fromIndex, this.fromIndex + this.size);
    }


    private class SubEntryIterator implements Iterator<Entry<K, V>> {

        private final Iterator<Entry<K, V>> iterator;
        private int at = 0;

        private SubEntryIterator(Iterator<Entry<K, V>> iterator) {
            this.iterator = iterator;
            this.skip(JavaSubMap.this.fromIndex);
        }

        @Override
        public boolean hasNext() {
            return this.at != JavaSubMap.this.size;
        }

        @Override
        public Entry<K, V> next() {
            return this.iterator.next();
        }

        @Override
        public void remove() {
            this.iterator.remove();
            JavaSubMap.this.size--;
        }

        void skip(int n) {
            int pos = 0;

            while (pos != n) {
                this.iterator.next();
            }
        }
    }


}
