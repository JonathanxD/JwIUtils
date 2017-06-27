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
import com.github.jonathanxd.iutils.collections.EntryW;
import com.github.jonathanxd.iutils.collections.SetW;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class WBackedJavaEntrySet<K, V> extends WBackedAbstractJavaCollection<Map.Entry<K, V>> implements Set<Map.Entry<K, V>> {

    private final SetW<Map.Entry<K, V>> wrapped;

    public WBackedJavaEntrySet(SetW<EntryW<K, V>> wrapped) {
        this.wrapped = new JavaWrappedSetW<>(ViewCollections.setMapped(wrapped.asJavaSet(), (e, eIterator) ->
                        IteratorUtil.mapped(e, eIterator, WBackedJavaMapEntry::new),
                y -> {
                    throw new RuntimeException();
                },
                l -> {
                    throw new RuntimeException();
                }), HashSet::new);
    }

    @Override
    public SetW<Map.Entry<K, V>> getWrapped() {
        return this.wrapped;
    }
}
