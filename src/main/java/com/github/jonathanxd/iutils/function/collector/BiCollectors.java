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
package com.github.jonathanxd.iutils.function.collector;

import com.github.jonathanxd.iutils.function.consumer.TriConsumer;
import com.github.jonathanxd.iutils.object.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Common collectors.
 */
public enum BiCollectors {
    ;

    /**
     * {@link Map} collector.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     * @return {@link Map} collector.
     */
    public static <K, V> BiCollector<K, V, Map<K, V>, Map<K, V>> toMap() {
        return new CommonBiCollector<>(HashMap::new, Map::put, map -> map);
    }

    /**
     * {@link Map} collector.
     *
     * @param mapSupplier Map instance supplier.
     * @param <K>         Key type.
     * @param <V>         Value type.
     * @param <MAP>       Map type.
     * @return {@link Map} collector.
     */
    public static <K, V, MAP extends Map<K, V>> BiCollector<K, V, MAP, MAP> toMap(Supplier<MAP> mapSupplier) {
        return new CommonBiCollector<>(mapSupplier, Map::put, map -> map);
    }

    /**
     * {@link HashMap} collector.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     * @return {@link HashMap} collector.
     */
    public static <K, V> BiCollector<K, V, HashMap<K, V>, HashMap<K, V>> toHashMap() {
        return new CommonBiCollector<>(HashMap::new, Map::put, map -> map);
    }

    /**
     * {@link Node} {@link List} collector.
     *
     * @param listSupplier List supplier.
     * @param <K>          Key type.
     * @param <V>          Value type.
     * @param <LIST>       List type.
     * @return {@link Node} {@link List} collector.
     */
    public static <K, V, LIST extends List<Node<K, V>>> BiCollector<K, V, LIST, LIST> toNodeList(Supplier<LIST> listSupplier) {
        return new CommonBiCollector<>(listSupplier, (list, key, value) -> list.add(new Node<>(key, value)), list -> list);
    }

    /**
     * {@link Node} {@link List} collector.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     * @return {@link Node} {@link List} collector.
     */
    public static <K, V> BiCollector<K, V, List<Node<K, V>>, List<Node<K, V>>> toNodeList() {
        return new CommonBiCollector<>(ArrayList::new, (list, key, value) -> list.add(new Node<>(key, value)), list -> list);
    }

    /**
     * {@link Node} {@link ArrayList} collector.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     * @return {@link Node} {@link ArrayList} collector.
     */
    public static <K, V> BiCollector<K, V, ArrayList<Node<K, V>>, ArrayList<Node<K, V>>> toNodeArrayList() {
        return new CommonBiCollector<>(ArrayList::new, (list, key, value) -> list.add(new Node<>(key, value)), list -> list);
    }

    static class CommonBiCollector<T, U, A, R> implements BiCollector<T, U, A, R> {
        private final Supplier<A> supplier;
        private final TriConsumer<A, T, U> accumulator;
        private final Function<A, R> finisher;

        @SuppressWarnings("unchecked")
        protected CommonBiCollector(Supplier<A> supplier, TriConsumer<A, T, U> accumulator) {
            this(supplier, accumulator, t -> (R) t);
        }

        protected CommonBiCollector(Supplier<A> supplier, TriConsumer<A, T, U> accumulator, Function<A, R> finisher) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.finisher = finisher;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public TriConsumer<A, T, U> accumulator() {
            return accumulator;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }
    }
}
