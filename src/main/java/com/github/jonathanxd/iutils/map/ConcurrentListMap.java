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
package com.github.jonathanxd.iutils.map;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Uses {@code synchronized} clause for {@link ListMap} operations. This class itself is not synchronized
 * unless provided {@link #ConcurrentListMap(Map) constructor} map is.
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class ConcurrentListMap<K, V> extends ListMap<K, V> {

    public ConcurrentListMap() {
        super(new ConcurrentHashMap<>());
    }

    public ConcurrentListMap(Map<K, List<V>> map) {
        super(map);
    }

    @Override
    public void putToList(K key, V value) {
        synchronized (this.getWrapped()) {
            super.putToList(key, value);
        }
    }

    @Override
    public boolean removeFromList(K key, Object value) {
        synchronized (this.getWrapped()) {
            return super.removeFromList(key, value);
        }
    }

    @Override
    public boolean removeAllFromList(K key, Collection<?> elements) {
        synchronized (this.getWrapped()) {
            return super.removeAllFromList(key, elements);
        }
    }
}
