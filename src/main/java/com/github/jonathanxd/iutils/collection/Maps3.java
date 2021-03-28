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
package com.github.jonathanxd.iutils.collection;

import com.github.jonathanxd.iutils.collection.immutable.ImmutableMap;
import com.github.jonathanxd.iutils.collection.wrapper.WrapperMaps;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.Pairs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Maps3 {

    /**
     * Gets first key-value pair of {@code map}, if {@code map} is sequential (like {@link
     * java.util.LinkedHashMap}), the first element of sequence is returned, otherwise the returned
     * pair is unpredictable. Also if there is no elements in the map, a {@link Pairs#nullPair()
     * Null Pair} is returned.
     *
     * @param map Map to get first element.
     * @param <K> Key Type.
     * @param <V> Value type.
     * @return First key-value pair of {@code map}.
     */
    public static <K, V> Pair<K, V> first(Map<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();

        if (iterator.hasNext()) {
            Map.Entry<K, V> next = iterator.next();
            return Pairs.of(next.getKey(), next.getValue());
        }

        return Pairs.nullPair();
    }

    /**
     * Gets last key-value pair of {@code map}, if {@code map} is sequential (like {@link
     * java.util.LinkedHashMap}), the last element of sequence is returned, otherwise the returned
     * pair is unpredictable. Also if there is no elements in the map, a {@link Pairs#nullPair()
     * Null Pair} is returned.
     *
     * @param map Map to get last element.
     * @param <K> Key Type.
     * @param <V> Value type.
     * @return Last key-value pair of {@code map}.
     */
    public static <K, V> Pair<K, V> last(Map<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<K, V> next = iterator.next();

            if (!iterator.hasNext())
                return Pairs.of(next.getKey(), next.getValue());
        }

        return Pairs.nullPair();
    }

    /**
     * Returns a new map with {@code key}-{@code value} pair prepended to it.
     *
     * @param key   Key to prepend.
     * @param value Value to associated to {@code key}.
     * @param map   List with elements to add after {@code element}.
     * @param <K>   Key type.
     * @param <V>   Value type.
     * @return A new map with {@code key}-{@code value} pair prepended to {@code map}.
     */
    public static <K, V> Map<K, V> prepend(K key, V value, Map<K, V> map) {
        return Maps3.prepend(key, value, map, LinkedHashMap::new);
    }

    /**
     * Returns a new map with elements of both {@code first} and {@code second} map.
     *
     * @param first  Map with element to add before {@code second}.
     * @param second Map with elements to add after {@code first}.
     * @param <K>    Key type.
     * @param <V>    Value type.
     * @return A new map with elements of both {@code first} and {@code second} map.
     */
    public static <K, V> Map<K, V> prepend(Map<K, V> first, Map<K, V> second) {
        return Maps3.prepend(first, second, LinkedHashMap::new);
    }

    /**
     * Returns a new map with {@code key} and {@code value} prepended to {@code map} elements.
     *
     * @param key     Key to prepend.
     * @param value   Value to associate to {@code key}.
     * @param factory Factory of new map (must be mutable).
     * @param <K>     Key type.
     * @param <V>     Value type.
     * @return A new map with {@code key}-{@code value} pair prepended.
     */
    public static <K, V> Map<K, V> prepend(K key, V value, Map<K, V> map,
                                           Supplier<? extends Map<K, V>> factory) {
        Map<K, V> newMap = factory.get();
        newMap.put(key, value);
        newMap.putAll(map);
        return newMap;
    }

    /**
     * Returns a new map with elements of both {@code first} and {@code second} map.
     *
     * @param first   Map with element to add before {@code second}.
     * @param second  Map with elements to add after {@code first}.
     * @param factory Factory of new Map (must be mutable).
     * @param <K>     Key type.
     * @param <V>     Value type.
     * @return A new map with elements of both {@code first} and {@code second} map.
     */
    public static <K, V> Map<K, V> prepend(Map<K, V> first, Map<K, V> second,
                                           Supplier<? extends Map<K, V>> factory) {
        Map<K, V> newMap = factory.get();
        newMap.putAll(first);
        newMap.putAll(second);
        return newMap;
    }

    /**
     * Creates a {@link Map} of {@link K}-{@link V} and add {@code keyValuePairs} to it.
     *
     * @param factory       Map factory.
     * @param <K>           Key type.
     * @param <V>           Value type.
     * @param keyValuePairs Pair of keys and values.
     * @return {@link Map} of {@link K}-{@link V} with {@code keyValuePairs}.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> mapOf(Supplier<? extends Map<K, V>> factory, Pair<K, V>... keyValuePairs) {
        Map<K, V> map = factory.get();

        for (Pair<K, V> keyValuePair : keyValuePairs) {
            map.put(keyValuePair.getFirst(), keyValuePair.getSecond());
        }

        return map;
    }

    /**
     * Creates a {@link Map} of {@link K}-{@link V} and add {@code keyValuePairs} to it.
     *
     * @param <K>           Key type.
     * @param <V>           Value type.
     * @param keyValuePairs Pair of keys and values.
     * @return {@link Map} of {@link K}-{@link V} with {@code keyValuePairs}.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> mapOf(Pair<K, V>... keyValuePairs) {
        return Maps3.mapOf(HashMap::new, keyValuePairs);
    }


    /**
     * Creates a {@link ImmutableMap} of {@link K}-{@link V} and add {@code keyValuePairs} to it.
     *
     * @param factory       Map factory.
     * @param <K>           Key type.
     * @param <V>           Value type.
     * @param keyValuePairs Pair of keys and values.
     * @return {@link Map} of {@link K}-{@link V} with {@code keyValuePairs}.
     */
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> immutableMapOf(Supplier<? extends Map<K, V>> factory, Pair<K, V>... keyValuePairs) {
        return WrapperMaps.immutableMap(Maps3.mapOf(factory, keyValuePairs));
    }

    /**
     * Creates a {@link ImmutableMap} of {@link K}-{@link V} and add {@code keyValuePairs} to it.
     *
     * @param <K>           Key type.
     * @param <V>           Value type.
     * @param keyValuePairs Pair of keys and values.
     * @return {@link Map} of {@link K}-{@link V} with {@code keyValuePairs}.
     */
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> immutableMapOf(Pair<K, V>... keyValuePairs) {
        return WrapperMaps.immutableMap(Maps3.mapOf(keyValuePairs));
    }

    /**
     * Creates a {@link Map} of all elements of {@code maps}.
     *
     * @param factory Map factory.
     * @param <K>     Key type.
     * @param <V>     Value type.
     * @param maps    Maps with values to add to new map.
     * @return {@link Map} of all elements of {@code maps}.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> concat(Supplier<? extends Map<K, V>> factory,
                                          Map<K, V>... maps) {
        Map<K, V> map = factory.get();

        for (Map<K, V> kvMap : maps) {
            map.putAll(kvMap);
        }

        return map;
    }

    /**
     * Creates a {@link Map} of all elements of {@code maps}.
     *
     * @param <K>     Key type.
     * @param <V>     Value type.
     * @param maps    Maps with values to add to new map.
     * @return {@link Map} of all elements of {@code maps}.
     */
    @SafeVarargs
    public static <K, V> Map<K, V> concat(Map<K, V>... maps) {
        return Maps3.concat(HashMap::new, maps);
    }

    /**
     * Creates a {@link ImmutableMap} of all elements of {@code maps}.
     *
     * @param factory       Map factory.
     * @param <K>           Key type.
     * @param <V>           Value type.
     * @param maps    Maps with values to add to new map.
     * @return {@link Map} of all elements of {@code maps}.
     */
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> immutableConcat(Supplier<? extends Map<K, V>> factory,
                                                            Map<K, V>... maps) {
        return WrapperMaps.immutableMap(Maps3.concat(factory, maps));
    }

    /**
     * Creates a {@link ImmutableMap} of all elements of {@code maps}.
     *
     * @param <K>           Key type.
     * @param <V>           Value type.
     * @param maps    Maps with values to add to new map.
     * @return {@link Map} of all elements of {@code maps}.
     */
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> immutableConcat(Map<K, V>... maps) {
        return WrapperMaps.immutableMap(Maps3.concat(maps));
    }
}
