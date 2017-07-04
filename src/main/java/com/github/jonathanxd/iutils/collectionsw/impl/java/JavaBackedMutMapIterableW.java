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
package com.github.jonathanxd.iutils.collectionsw.impl.java;

import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.collection.view.ViewSet;
import com.github.jonathanxd.iutils.collectionsw.EntryW;
import com.github.jonathanxd.iutils.collectionsw.IterableW;
import com.github.jonathanxd.iutils.collectionsw.IteratorW;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import java.util.Iterator;
import java.util.Map;

public class JavaBackedMutMapIterableW<K, V> implements IterableW<EntryW.Mut<K, V>> {

    private final Map<K, V> wrapped;
    private final Iterable<EntryW.Mut<K, V>> wrappedi;

    public JavaBackedMutMapIterableW(Map<K, V> wrapped) {
        this.wrapped = wrapped;
        this.wrappedi = new MutableIterableWrapper<>(wrapped);
    }


    @Override
    public Iterable<EntryW.Mut<K, V>> asJavaIterable() {
        return this.wrappedi;
    }

    @Override
    public IteratorW<EntryW.Mut<K, V>> iterator() {
        return new JavaBackedIteratorW<>(this.wrappedi.iterator());
    }

    @Override
    public IterableW<EntryW.Mut<K, V>> copy() {
        return new JavaBackedMutMapIterableW<>(this.wrapped);
    }

    static class MutableIterableWrapper<K, V> implements Iterable<EntryW.Mut<K, V>> {

        private final Map<K, V> wrapped;

        MutableIterableWrapper(Map<K, V> wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Iterator<EntryW.Mut<K, V>> iterator() {
            ViewSet<Map.Entry<K, V>, EntryW.Mut<K, V>> set = ViewCollections.setMapped(this.wrapped.entrySet(),
                    (kvEntry, entryIterator) ->
                            IteratorUtil.mapped(kvEntry, entryIterator, kvEntry1 ->
                                    (EntryW.Mut<K, V>) new JavaMapBackedMutEntry<>(this.wrapped,
                                            kvEntry1.getKey(), kvEntry1.getValue())),
                    kvMutable -> {
                        this.wrapped.put(kvMutable.getKey(), kvMutable.getValue());
                        return true;
                    },
                    kvMutable -> this.wrapped.remove(kvMutable.getKey(), kvMutable.getValue()));

            return new JavaBackedIteratorW<>(set.iterator()).asJavaIterator();
        }
    }
}
