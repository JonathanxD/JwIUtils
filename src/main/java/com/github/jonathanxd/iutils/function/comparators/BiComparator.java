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
package com.github.jonathanxd.iutils.function.comparators;

import com.github.jonathanxd.iutils.function.function.BiToDoubleFunction;
import com.github.jonathanxd.iutils.function.function.BiToIntFunction;
import com.github.jonathanxd.iutils.function.function.BiToLongFunction;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Created by jonathan on 05/03/16.
 */
@FunctionalInterface
public interface BiComparator<T, U> {
    static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
        return Collections.reverseOrder();
    }

    static <T, U, V> BiComparator<T, U> comparing(
            BiFunction<? super T, ? super U, ? extends V> keyExtractor,
            Comparator<? super V> keyComparator) {
        Objects.requireNonNull(keyExtractor);
        Objects.requireNonNull(keyComparator);
        return (BiComparator<T, U> & Serializable)
                (c1, c2, a1, a2) -> keyComparator.compare(keyExtractor.apply(c1, c2),
                        keyExtractor.apply(a1, a2));
    }

    static <T, U, V extends Comparable<? super V>> BiComparator<T, U> comparing(
            BiFunction<? super T, ? super U, ? extends V> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (BiComparator<T, U> & Serializable)
                (c1, c2, a1, a2) -> keyExtractor.apply(c1, c2).compareTo(keyExtractor.apply(a1, a2));
    }

    static <T, U> BiComparator<T, U> comparingInt(BiToIntFunction<? super T, ? super U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (BiComparator<T, U> & Serializable) (c1, c2, a1, a2) -> Integer.compare(keyExtractor.applyAsInt(c1, c2), keyExtractor.applyAsInt(a1, a2));
    }

    static <T, U> BiComparator<T, U> comparingLong(BiToLongFunction<? super T, ? super U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (BiComparator<T, U> & Serializable) (c1, c2, a1, a2) -> Long.compare(keyExtractor.applyAsLong(c1, c2), keyExtractor.applyAsLong(a1, a2));
    }

    static <T, U> BiComparator<T, U> comparingDouble(BiToDoubleFunction<? super T, ? super U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (BiComparator<T, U> & Serializable) (c1, c2, a1, a2) -> Double.compare(keyExtractor.applyAsDouble(c1, c2), keyExtractor.applyAsDouble(a1, a2));
    }

    int compare(T o1, U o2, T a1, U a2);

    boolean equals(Object obj);

    default BiComparator<T, U> thenComparing(BiComparator<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (BiComparator<T, U> & Serializable) (c1, c2, a1, a2) -> {
            int res = compare(c1, c2, a1, a2);
            return (res != 0) ? res : other.compare(c1, c2, a1, a2);
        };
    }

    default <V, E> BiComparator<T, U> thenComparing(
            BiFunction<? super T, ? super U, ? extends V> keyExtractor,
            Comparator<? super V> keyComparator) {
        return thenComparing(comparing(keyExtractor, keyComparator));
    }

    default <V extends Comparable<? super V>> BiComparator<T, U> thenComparing(
            BiFunction<? super T, ? super U, ? extends V> keyExtractor) {
        return thenComparing(comparing(keyExtractor));
    }

    default BiComparator<T, U> thenComparingInt(BiToIntFunction<? super T, ? super U> keyExtractor) {
        return thenComparing(comparingInt(keyExtractor));
    }

    default BiComparator<T, U> thenComparingLong(BiToLongFunction<? super T, ? super U> keyExtractor) {
        return thenComparing(comparingLong(keyExtractor));
    }

    default BiComparator<T, U> thenComparingDouble(BiToDoubleFunction<? super T, ? super U> keyExtractor) {
        return thenComparing(comparingDouble(keyExtractor));
    }

}