/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.map;

import com.github.jonathanxd.iutils.condition.Conditions;
import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.comparators.BiComparator;
import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.object.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Map utilities.
 */
public final class MapUtils {

    private MapUtils() {
        throw new UnsupportedOperationException();
    }


    /**
     * Sorts the {@code map} using {@code biComparator}
     *
     * @param map          Map to sort.
     * @param biComparator Key and value comparator.
     * @param <K>          Key type.
     * @param <V>          Value type.
     * @return Sorted map.
     */
    public static <K, V> Map<K, V> sorted(Map<K, V> map, BiComparator<K, V> biComparator) {
        return BiStreams.mapStream(map).sorted(biComparator).collect(BiCollectors.toMap());
    }

    /**
     * Sorts the {@code map} using {@code biComparator} and creates the new map instance using
     * {@code mapSupplier}.
     *
     * @param map          Map to sort.
     * @param biComparator Key and value comparator.
     * @param mapSupplier  Supplier of the map instance.
     * @param <K>          Key type.
     * @param <V>          Value type.
     * @param <MAP>        Map type.
     * @return Sorted map of type {@link MAP}.
     */
    public static <K, V, MAP extends Map<K, V>> MAP sorted(Map<K, V> map, Supplier<MAP> mapSupplier, BiComparator<K, V> biComparator) {
        return BiStreams.mapStream(map).sorted(biComparator).collect(BiCollectors.toMap(mapSupplier));
    }

    /**
     * Creates a map from a array of elements (pairs).
     *
     * @param elements Pair of elements (element at x is the key and element at x + 1 is the
     *                 value).
     * @param <K>      Key type.
     * @param <V>      Value type.
     * @return Map from a array of elements (pairs).
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> mapOf(Object... elements) {

        Map<Object, Object> map = new HashMap<>();

        for (int i = 0; i < elements.length; i += 2) {

            Object key = elements[i];

            Conditions.require((i + 1) < elements.length, "Missing value of key '" + key + "'");

            map.put(key, elements[i + 1]);
        }

        return (Map<K, V>) map;
    }

    /**
     * Creates a map from a pair array..
     *
     * @param pairs Pairs.
     * @param <K>   Key type.
     * @param <V>   Value type.
     * @return Map from a pair array.
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> mapFromPairs(Pair<K, V>... pairs) {

        Map<Object, Object> map = new HashMap<>();

        for (Pair<K, V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }

        return (Map<K, V>) map;
    }

}
