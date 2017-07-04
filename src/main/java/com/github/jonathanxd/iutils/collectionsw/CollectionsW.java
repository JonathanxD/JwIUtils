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
package com.github.jonathanxd.iutils.collectionsw;

import com.github.jonathanxd.iutils.collectionsw.impl.LinkedListW;
import com.github.jonathanxd.iutils.collectionsw.impl.LinkedSetW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaWrappedMapW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutListW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutMapW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutSetW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.WBackedSizedListW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableListW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableMapW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSetW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSizedListW;
import com.github.jonathanxd.iutils.object.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper list factory.
 */
public final class CollectionsW {

    private CollectionsW() {
        throw new IllegalStateException();
    }

    /**
     * Creates a persistent {@link ListW} which hold {@code elements}.
     *
     * This method returns {@code CollectionsW} implementation version.
     *
     * @param elements Elements of list.
     * @param <E>      Element type.
     * @return Persistent list of {@code elements}.
     */
    @SafeVarargs
    public static <E> ListW<E> listOf(E... elements) {
        return LinkedListW.fromArray(elements);
    }

    /**
     * Creates a persistent {@link SetW} which hold {@code elements}.
     *
     * This method returns {@code CollectionsW} implementation version.
     *
     * @param elements Elements of set.
     * @param <E>      Element type.
     * @return Persistent list of {@code elements}.
     */
    @SafeVarargs
    public static <E> SetW<E> setOf(E... elements) {
        return new LinkedSetW<>(elements);
    }

    /**
     * Creates a persistent {@link MapW} which hold {@code pairs}.
     *
     * @param pairs Pair of key-value to associate.
     * @param <K>   Key type.
     * @param <V>   Value type.
     * @return Persistent Map of {@code pairs}.
     */
    @SafeVarargs
    public static <K, V> MapW<K, V> mapOf(Pair<K, V>... pairs) {
        Map<K, V> map = new LinkedHashMap<>();

        for (Pair<K, V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }

        return new JavaWrappedMapW<>(map, LinkedHashMap::new);
    }

    // Mutable

    /**
     * Creates a {@link MutableListW} which hold {@code elements}.
     *
     * This method returns {@code Wrapped} implementation version.
     *
     * @param elements Elements of list.
     * @param <E>      Element type.
     * @return Mutable list of {@code elements}.
     */
    @SafeVarargs
    public static <E> MutableListW<E> mutableListOf(E... elements) {
        List<E> list = new LinkedList<>();

        Collections.addAll(list, elements);

        return new JavaBackedMutListW<>(list);
    }

    /**
     * Creates a persistent {@link SetW} which hold {@code elements}.
     *
     * This method returns {@code Wrapped} implementation version.
     *
     * @param elements Elements of set.
     * @param <E>      Element type.
     * @return Persistent list of {@code elements}.
     */
    @SafeVarargs
    public static <E> MutableSetW<E> mutableSetOf(E... elements) {
        Set<E> set = new HashSet<>();

        Collections.addAll(set, elements);

        return new JavaBackedMutSetW<>(set);
    }

    /**
     * Creates a persistent {@link MapW} which hold {@code pairs}.
     *
     * @param pairs Pair of key-value to associate.
     * @param <K>   Key type.
     * @param <V>   Value type.
     * @return Persistent Map of {@code pairs}.
     */
    @SafeVarargs
    public static <K, V> MutableMapW<K, V> mutableMapOf(Pair<K, V>... pairs) {
        Map<K, V> map = new LinkedHashMap<>();

        for (Pair<K, V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }

        return new JavaBackedMutMapW<>(map, LinkedHashMap::new);
    }

    /**
     * Creates a mutable {@link ListW} which a {@code max} of {@code elements}.
     *
     * @param max      Max of elements.
     * @param elements Elements of list.
     * @param <E>      Element type.
     * @return Fixed size list of {@code elements}.
     */
    @SafeVarargs
    public static <E> MutableSizedListW<E> sizedListOf(int max, E... elements) {
        return new WBackedSizedListW<>(CollectionsW.mutableListOf(elements), max, CollectionsW::mutableListOf);
    }


}
