/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.conditions.Conditions;
import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.comparators.BiComparator;
import com.github.jonathanxd.iutils.function.stream.MapStream;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by jonathan on 05/03/16.
 */
public class MapUtils {

    public static <K, V> Map<K, V> sorted(Map<K, V> map, BiComparator<K, V> biComparator) {
        return MapStream.of(map).sorted(biComparator).collect(BiCollectors.toMap());
    }

    public static <K, V, MAP extends Map<K, V>> MAP sortedAs(Map<K, V> map, Supplier<MAP> mapSupplier, BiComparator<K, V> biComparator) {
        return MapStream.of(map).sorted(biComparator).collect(BiCollectors.toMap(mapSupplier));
    }

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

}
