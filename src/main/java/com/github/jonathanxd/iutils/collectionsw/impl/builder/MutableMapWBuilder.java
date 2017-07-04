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
package com.github.jonathanxd.iutils.collectionsw.impl.builder;

import com.github.jonathanxd.iutils.collectionsw.EntryW;
import com.github.jonathanxd.iutils.collectionsw.MapW;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutMapW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableMapW;
import com.github.jonathanxd.iutils.object.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This implementation is backed by a {@link JavaBackedMutMapW}.
 *
 * THis constructs a {@link JavaBackedMutMapW} backed by a {@link HashMap}.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public final class MutableMapWBuilder<K, V> implements MutableMapW.Builder<K, V> {

    private final MutableMapW<K, V> map = new JavaBackedMutMapW<>(new HashMap<>());

    private MutableMapWBuilder() {
    }

    /**
     * Creates an builder instance without elements.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Builder.
     */
    public static <K, V> MutableMapWBuilder<K, V> builder() {
        return new MutableMapWBuilder<>();
    }

    /**
     * Creates an builder instance with a single key associated to value.
     *
     * @param k1  First key.
     * @param v1  First value.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Builder.
     */
    public static <K, V> MutableMapWBuilder<K, V> builder(K k1, V v1) {
        MutableMapWBuilder<K, V> builder = new MutableMapWBuilder<>();
        builder.put(k1, v1);
        return builder;
    }

    /**
     * Creates an builder instance with a two keys associated to two values respectively.
     *
     * @param k1  First key.
     * @param v1  First value.
     * @param k2  Second key.
     * @param v2  Second value.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Builder.
     */
    public static <K, V> MutableMapWBuilder<K, V> builder(K k1, V v1, K k2, V v2) {
        MutableMapWBuilder<K, V> builder = new MutableMapWBuilder<>();
        builder.put(k1, v1);
        builder.put(k2, v2);
        return builder;
    }

    /**
     * Creates an builder instance with {@code n} keys associated to {@code n} values.
     *
     * @param pairs Key-value pair.
     * @param <K>   Key type.
     * @param <V>   Value type.
     * @return Builder.
     */
    @SafeVarargs
    public static <K, V> MutableMapWBuilder<K, V> builder(Pair<K, V>... pairs) {
        MutableMapWBuilder<K, V> builder = new MutableMapWBuilder<>();

        for (Pair<K, V> pair : pairs) {
            builder.put(pair.getFirst(), pair.getSecond());
        }

        return builder;
    }

    /**
     * Creates an builder instance with {@code n} keys associated to {@code n} values.
     *
     * @param map Map with associated keys and values.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Builder.
     */
    public static <K, V> MutableMapWBuilder<K, V> builder(Map<K, V> map) {
        MutableMapWBuilder<K, V> builder = new MutableMapWBuilder<>();

        map.forEach(builder::put);

        return builder;
    }

    /**
     * Creates an builder instance with {@code n} keys associated to {@code n} values.
     *
     * @param map Map with associated keys and values.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Builder.
     */
    public static <K, V> MutableMapWBuilder<K, V> builder(MapW<K, V> map) {
        MutableMapWBuilder<K, V> builder = new MutableMapWBuilder<>();

        map.forEach(builder::put);

        return builder;
    }

    @Override
    public Stream<EntryW<K, V>> elements() {
        return this.map.stream();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public MutableMapW.Builder<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    @Override
    public MutableMapW.Builder<K, V> remove(K key, V value) {
        this.map.remove(key, value);
        return this;
    }

    @Override
    public MutableMapW.Builder<K, V> clear() {
        this.map.clear();
        return this;
    }

    @Override
    public MutableMapW<K, V> build() {
        return new JavaBackedMutMapW<>(new HashMap<>(this.map.asJavaMap()));
    }
}
