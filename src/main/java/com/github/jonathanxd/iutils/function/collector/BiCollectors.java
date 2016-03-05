/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.jonathanxd.iutils.function.collector;

import com.github.jonathanxd.iutils.function.consumer.TriConsumer;
import com.github.jonathanxd.iutils.object.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by jonathan on 05/03/16.
 */
public enum BiCollectors {
    ;

    public static <K, V> BiCollector<K, V, Map<K, V>, Map<K, V>> toMap() {
        return new CommonBiCollector<>(HashMap::new, Map::put, (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        }, map -> map);
    }

    public static <K, V, MAP extends Map<K, V>> BiCollector<K, V, MAP, MAP> toMap(Supplier<MAP> mapSupplier) {
        return new CommonBiCollector<>(mapSupplier, Map::put, (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        }, map -> map);
    }

    public static <K, V> BiCollector<K, V, HashMap<K, V>, HashMap<K, V>> toHashMap() {
        return new CommonBiCollector<>(HashMap::new, Map::put, (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        }, map -> map);
    }

    public static <K, V, LIST extends List<Node<K, V>>> BiCollector<K, V, LIST, LIST> toNodeList(Supplier<LIST> listSupplier) {
        return new CommonBiCollector<>(listSupplier, (list, key, value) -> list.add(new Node<>(key, value)), (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        }, list -> list);
    }

    public static <K, V> BiCollector<K, V, List<Node<K, V>>, List<Node<K, V>>> toNodeList() {
        return new CommonBiCollector<>(ArrayList::new, (list, key, value) -> list.add(new Node<>(key, value)), (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        }, list -> list);
    }

    public static <K, V> BiCollector<K, V, ArrayList<Node<K, V>>, ArrayList<Node<K, V>>> toNodeArrayList() {
        return new CommonBiCollector<>(ArrayList::new, (list, key, value) -> list.add(new Node<>(key, value)), (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        }, list -> list);
    }

    static class CommonBiCollector<T, U, A, R> implements BiCollector<T, U, A, R> {
        private final Supplier<A> supplier;
        private final TriConsumer<A, T, U> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;

        @SuppressWarnings("unchecked")
        protected CommonBiCollector(Supplier<A> supplier, TriConsumer<A, T, U> accumulator, BinaryOperator<A> combiner) {
            this(supplier, accumulator, combiner, t -> (R) t);
        }

        protected CommonBiCollector(Supplier<A> supplier, TriConsumer<A, T, U> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
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
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }
    }
}
