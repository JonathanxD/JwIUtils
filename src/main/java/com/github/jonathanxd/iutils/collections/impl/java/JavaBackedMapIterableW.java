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

import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.collection.view.ViewSet;
import com.github.jonathanxd.iutils.collections.EntryW;
import com.github.jonathanxd.iutils.collections.IterableW;
import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import java.util.Iterator;
import java.util.Map;

public class JavaBackedMapIterableW<K, V> implements IterableW<EntryW<K, V>> {

    private final Map<K, V> wrapped;
    private final Iterable<EntryW<K, V>> wrappedi;

    public JavaBackedMapIterableW(Map<K, V> wrapped) {
        this.wrapped = wrapped;
        this.wrappedi = new UnmodIterableWrapper<>(wrapped);
    }


    @Override
    public Iterable<EntryW<K, V>> asJavaIterable() {
        return this.wrappedi;
    }

    @Override
    public IteratorW<EntryW<K, V>> iterator() {
        return new JavaBackedIteratorW<>(this.wrappedi.iterator());
    }

    @Override
    public IterableW<EntryW<K, V>> copy() {
        return new JavaBackedMapIterableW<>(this.wrapped);
    }

    static class UnmodIterableWrapper<K, V> implements Iterable<EntryW<K, V>> {

        private final Map<K, V> wrapped;

        UnmodIterableWrapper(Map<K, V> wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Iterator<EntryW<K, V>> iterator() {
            ViewSet<Map.Entry<K, V>, EntryW<K, V>> set = ViewCollections.setMapped(this.wrapped.entrySet(),
                    (kvEntry, entryIterator) ->
                            IteratorUtil.mapped(kvEntry, entryIterator, kvEntry1 ->
                                    (EntryW<K, V>) new EntryW.Immutable<>(kvEntry1.getKey(), kvEntry1.getValue())),
                    kvImmutable -> {
                        throw new UnsupportedOperationException();
                    },
                    kvImmutable -> {
                        throw new UnsupportedOperationException();
                    }
            );

            return new JavaBackedIteratorW<>(set.iterator()).asJavaIterator();
        }
    }
}
