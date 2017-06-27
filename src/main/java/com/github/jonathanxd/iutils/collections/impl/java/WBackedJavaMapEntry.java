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
import com.github.jonathanxd.iutils.collections.impl.MutationOperationOnImmutableData;

import java.util.Map;

public final class WBackedJavaMapEntry<K, V> implements Map.Entry<K, V> {

    private final EntryW<K, V> wrapped;

    public WBackedJavaMapEntry(EntryW<K, V> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public K getKey() {
        return this.wrapped.getKey();
    }

    @Override
    public V getValue() {
        return this.wrapped.getValue();
    }

    @Override
    public V setValue(V value) {
        if (this.wrapped instanceof EntryW.Mutable<?, ?>) {
            return ((EntryW.Mutable<K, V>) this.wrapped).setValue(value);
        }

        throw new MutationOperationOnImmutableData();
    }
}
