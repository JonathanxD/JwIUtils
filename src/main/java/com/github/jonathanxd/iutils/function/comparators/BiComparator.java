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
package com.github.jonathanxd.iutils.function.comparators;

import com.github.jonathanxd.iutils.function.function.BiToDoubleFunction;
import com.github.jonathanxd.iutils.function.function.BiToIntFunction;
import com.github.jonathanxd.iutils.function.function.BiToLongFunction;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Comparator of two values.
 *
 * Same as {@link Comparator} but receives two values instead of one.
 *
 * @param <T> First value.
 * @param <U> Second value.
 */
@FunctionalInterface
public interface BiComparator<T, U> {

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

    int compare(T t, U u, T t2, U u2);

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

    default BiComparator<T, U> reversed() {
        return (t, u, t2, u2) -> this.compare(t2, u2, t, u);
    }

}
