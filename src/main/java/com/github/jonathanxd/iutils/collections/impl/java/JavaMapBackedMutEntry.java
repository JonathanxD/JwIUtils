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

import java.util.Map;

/**
 * A mutable entry backed to a Java map. This entry is not backed to Java Map entry because Java
 * maps entries does not allow mutation on key.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public final class JavaMapBackedMutEntry<K, V> implements EntryW.Mut<K, V> {

    private final Map<K, V> javaMutableMap;
    private K key;
    private V value;

    public JavaMapBackedMutEntry(Map<K, V> javaMutableMap, K key, V value) {
        this.javaMutableMap = javaMutableMap;
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
        return new JavaMapBackedMutEntry<>(this.javaMutableMap, this.key, this.value);
    }

    @Override
    public K setKey(K key) {
        K old = this.key;
        this.key = key;

        this.javaMutableMap.remove(old, this.value);
        this.javaMutableMap.put(key, this.value);

        return old;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;

        this.javaMutableMap.remove(this.key, old);
        this.javaMutableMap.put(this.key, value);

        return old;
    }
}
