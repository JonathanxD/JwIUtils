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
package com.github.jonathanxd.iutils.collection;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Collection of utilities for Java comparators.
 */
public class Comparators3 {

    /**
     * Maps values to {@link U} with {@code transformer} before calling {@code comparator} to
     * compare values.
     *
     * @param comparator  Original comparator.
     * @param transformer Transformer of value.
     * @param <T>         Origin type.
     * @param <U>         Target type.
     * @return A comparator that maps value using {@code transformer} and then call {@link
     * Comparator#compare(Object, Object)} of {@code comparator} with mapped values.
     */
    public static <T, U> Comparator<U> map(Comparator<T> comparator, Function<U, T> transformer) {
        return (o1, o2) -> comparator.compare(transformer.apply(o1), transformer.apply(o2));
    }

    /**
     * Returns the first element of {@code iterable} according to {@code comparator}.
     *
     * @param comparator Comparator of elements.
     * @param iterable   Iterable with elements.
     * @param <T>        Type of value.
     * @return First element of {@code iterable} according to {@code comparator}, or null if {@code
     * iterable} does not have any element to iterate over.
     */
    public static <T> T first(Comparator<T> comparator, Iterable<T> iterable) {
        T first = null;

        for (T t : iterable) {
            if (first == null)
                first = t;
            else {
                int c = comparator.compare(first, t);

                if (c > 0)
                    first = t;
            }
        }

        return first;
    }

    /**
     * Returns the first element of {@code array} according to {@code comparator}.
     *
     * @param comparator Comparator of elements.
     * @param array      Iterable with elements.
     * @param <T>        Type of value.
     * @return First element of {@code array} according to {@code comparator}, or null if {@code
     * array} does not have any element to iterate over.
     */
    public static <T> T first(Comparator<T> comparator, T[] array) {
        T first = null;

        for (T t : array) {
            if (first == null)
                first = t;
            else {
                int c = comparator.compare(first, t);

                if (c > 0)
                    first = t;
            }
        }

        return first;
    }

    /**
     * Returns the last element of {@code iterable} according to {@code comparator}.
     *
     * @param comparator Comparator of elements.
     * @param iterable   Iterable with elements.
     * @param <T>        Type of value.
     * @return Last element of {@code iterable} according to {@code comparator}, or null if {@code
     * iterable} does not have any element to iterate over.
     */
    public static <T> T last(Comparator<T> comparator, Iterable<T> iterable) {
        T last = null;

        for (T t : iterable) {
            if (last == null)
                last = t;
            else {
                int c = comparator.compare(last, t);

                if (c < 0)
                    last = t;
            }
        }

        return last;
    }

    /**
     * Returns the last element of {@code array} according to {@code comparator}.
     *
     * @param comparator Comparator of elements.
     * @param array      Array of elements.
     * @param <T>        Type of value.
     * @return Last element of {@code array} according to {@code comparator}, or null if {@code
     * array} does not have any element to iterate over.
     */
    public static <T> T last(Comparator<T> comparator, T[] array) {
        T last = null;

        for (T t : array) {
            if (last == null)
                last = t;
            else {
                int c = comparator.compare(last, t);

                if (c < 0)
                    last = t;
            }
        }

        return last;
    }

}
