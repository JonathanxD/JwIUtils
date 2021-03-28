/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A hash map that holds a {@link List} of {@link V values}.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class ListMap<K, V> implements Map<K, List<V>> {

    private final Map<K, List<V>> wrapped;

    public ListMap() {
        this(new HashMap<>());
    }

    public ListMap(Map<K, List<V>> map) {
        this.wrapped = map;
    }

    /**
     * Puts a {@code value} to list associated to the {@code key}.
     *
     * @param key   Key to get list.
     * @param value Value to add to list.
     */
    public void putToList(K key, V value) {

        List<V> list;

        if (this.containsKey(key))
            list = this.get(key);
        else {
            list = new ArrayList<>();
            this.put(key, list);
        }


        list.add(value);
    }

    /**
     * Removes a {@code value} from a list associated to the {@code key}.
     *
     * @param key   Key to get list.
     * @param value Value to remove from list.
     * @return True if successfully removed the value, false otherwise.
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean removeFromList(K key, Object value) {
        if (!this.containsKey(key))
            return false;

        List<V> vs = this.get(key);

        boolean success = vs.remove(value);

        if (vs.isEmpty()) {
            this.remove(key);
        }

        return success;
    }

    /**
     * Removes all {@code elements} from a list associated to the {@code key}.
     *
     * @param key      Key to get list.
     * @param elements Elements to remove from the list.
     * @return True if successfully removed at least one element, false otherwise.
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean removeAllFromList(K key, Collection<?> elements) {
        if (!this.containsKey(key))
            return false;

        List<V> vs = this.get(key);

        boolean success = vs.removeAll(elements);

        if (vs.isEmpty()) {
            this.remove(key);
        }

        return success;
    }

    protected Map<K, List<V>> getWrapped() {
        return this.wrapped;
    }

    @Override
    public int size() {
        return this.getWrapped().size();
    }

    @Override
    public boolean isEmpty() {
        return this.getWrapped().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.getWrapped().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.getWrapped().containsValue(value);
    }

    @Override
    public List<V> get(Object key) {
        return this.getWrapped().get(key);
    }

    @Override
    public List<V> put(K key, List<V> value) {
        return this.getWrapped().put(key, value);
    }

    @Override
    public List<V> remove(Object key) {
        return this.getWrapped().remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        this.getWrapped().putAll(m);
    }

    @Override
    public void clear() {
        this.getWrapped().clear();
    }

    @Override
    public Set<K> keySet() {
        return this.getWrapped().keySet();
    }

    @Override
    public Collection<List<V>> values() {
        return this.getWrapped().values();
    }

    @Override
    public Set<Entry<K, List<V>>> entrySet() {
        return this.getWrapped().entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return this.getWrapped().equals(o);
    }

    @Override
    public int hashCode() {
        return this.getWrapped().hashCode();
    }
}
