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

/**
 * A hash map that holds a {@link List} of {@link V values}.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class ListHashMap<K, V> extends HashMap<K, List<V>> {

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

}
