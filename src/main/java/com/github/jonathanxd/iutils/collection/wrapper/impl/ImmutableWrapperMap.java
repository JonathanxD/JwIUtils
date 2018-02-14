/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.collection.wrapper.impl;

import com.github.jonathanxd.iutils.collection.immutable.ImmutableMap;
import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.collection.view.ViewUtils;
import com.github.jonathanxd.iutils.collection.wrapper.WrapperCollections;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ImmutableWrapperMap<K, V> implements ImmutableMap<K, V> {
    private final Map<K, V> map;

    public ImmutableWrapperMap(Map<K, V> map) {
        this.map = map;
    }

    protected Map<K, V> getMap() {
        return this.map;
    }

    @Override
    public int size() {
        return this.getMap().size();
    }

    @Override
    public boolean isEmpty() {
        return this.getMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.getMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.getMap().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.getMap().get(key);
    }

    @Override
    public V remove(Object key) {
        return this.getMap().remove(key);
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return WrapperCollections.immutableSet(this.getMap().keySet());
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return WrapperCollections.immutableCollection(this.getMap().values());
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return ViewCollections.setMapped(this.getMap().entrySet(),
                t -> new AbstractMap.SimpleImmutableEntry<>(t.getKey(), t.getValue()),
                ViewUtils.unmodifiable("Immutable collection"),
                ViewUtils.unmodifiable("Immutable collection")
        );
    }
}
