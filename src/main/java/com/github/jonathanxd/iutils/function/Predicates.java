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
package com.github.jonathanxd.iutils.function;

import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * Set of predefined predicates.
 */
public final class Predicates {

    private Predicates() {
        throw new IllegalStateException();
    }


    /**
     * Returns a predicate that accepts any value.
     *
     * @param <T> Type.
     * @return Predicate that accepts any value.
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> acceptAll() {
        return value -> true;
    }

    /**
     * Returns a predicate that negates any value.
     *
     * @param <T> Type.
     * @return Predicate that negates any value.
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> negateAll() {
        return value -> false;
    }

    /**
     * Returns a predicate that accepts non-null values.
     *
     * @param <T> Type.
     * @return Predicate that accepts non-null values.
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> notNull() {
        return Objects::nonNull;
    }

    /**
     * Returns a predicate that accepts null values.
     *
     * @param <T> Type.
     * @return Predicate that accepts null values.
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> onlyNull() {
        return Objects::isNull;
    }

    /**
     * Returns a predicate that accepts positive ({@code > 0}) values.
     *
     * @return Predicate that accepts positive ({@code > 0}) values.
     */
    @SuppressWarnings("unchecked")
    public static IntPredicate positive() {
        return value -> value > 0;
    }

    /**
     * Returns a predicate that accepts negative ({@code < 0}) values.
     *
     * @return Predicate that accepts negative ({@code < 0}) values.
     */
    @SuppressWarnings("unchecked")
    public static IntPredicate negative() {
        return value -> value < 0;
    }

    /**
     * Returns a predicate that accepts only zero value.
     *
     * @return Predicate that accepts only zero value.
     */
    @SuppressWarnings("unchecked")
    public static IntPredicate zero() {
        return value -> value == 0;
    }

    /**
     * Returns a predicate that accepts positive ({@code > 0}) values.
     *
     * @return Predicate that accepts positive ({@code > 0}) values.
     */
    @SuppressWarnings("unchecked")
    public static LongPredicate positiveLong() {
        return value -> value > 0;
    }

    /**
     * Returns a predicate that accepts negative ({@code < 0}) values.
     *
     * @return Predicate that accepts negative ({@code < 0}) values.
     */
    @SuppressWarnings("unchecked")
    public static LongPredicate negativeLong() {
        return value -> value < 0;
    }

    /**
     * Returns a predicate that accepts only zero value.
     *
     * @return Predicate that accepts only zero value.
     */
    @SuppressWarnings("unchecked")
    public static LongPredicate zeroLong() {
        return value -> value == 0L;
    }

    /**
     * Returns a predicate that accepts positive ({@code > 0}) values.
     *
     * @return Predicate that accepts positive ({@code > 0}) values.
     */
    @SuppressWarnings("unchecked")
    public static DoublePredicate positiveDouble() {
        return value -> value > 0;
    }

    /**
     * Returns a predicate that accepts negative ({@code < 0}) values.
     *
     * @return Predicate that accepts negative ({@code < 0}) values.
     */
    @SuppressWarnings("unchecked")
    public static DoublePredicate negativeDouble() {
        return value -> value < 0;
    }

    /**
     * Returns a predicate that accepts only zero value.
     *
     * @return Predicate that accepts only zero value.
     */
    @SuppressWarnings("unchecked")
    public static DoublePredicate zeroDouble() {
        return value -> value == 0D;
    }
}
