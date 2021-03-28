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
package com.github.jonathanxd.iutils.function.collector;

import com.github.jonathanxd.iutils.function.consumer.TriConsumer;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * BiCollector version of {@link java.util.stream.Collector}.
 *
 * @param <T> Type of input element.
 * @param <U> Type of second input element.
 * @param <A> Accumulator type.
 * @param <R> Result type.
 */
public interface BiCollector<T, U, A, R> {

    /**
     * Creates a BiCollector from {@code supplier} and {@code accumulator}.
     *
     * @param supplier    Supplier.
     * @param accumulator Accumulator.
     * @param <T>         Type of input element.
     * @param <U>         Type of second input element.
     * @param <R>         Result type.
     * @return BiCollector from {@code supplier} and {@code accumulator}.
     */
    static <T, U, R> BiCollector<T, U, R, R> of(Supplier<R> supplier, TriConsumer<R, T, U> accumulator) {
        return new BiCollectors.CommonBiCollector<>(supplier, accumulator);
    }

    /**
     * Creates a BiCollector from {@code supplier} and {@code accumulator} and finishes with {@code
     * finisher}.
     *
     * @param supplier    Supplier.
     * @param accumulator Accumulator.
     * @param <T>         Type of input element.
     * @param <U>         Type of second input element.
     * @param <A>         Accumulator type.
     * @param <R>         Result type.
     * @return BiCollector from {@code supplier} and {@code accumulator} and finishes with {@code
     * finisher}.
     */
    static <T, U, A, R> BiCollector<T, U, A, R> of(Supplier<A> supplier, TriConsumer<A, T, U> accumulator, Function<A, R> finisher) {
        return new BiCollectors.CommonBiCollector<>(supplier, accumulator, finisher);
    }

    /**
     * Accumulator supplier.
     *
     * @return Accumulator supplier.
     */
    Supplier<A> supplier();

    /**
     * Accumulator.
     *
     * @return Accumulator.
     */
    TriConsumer<A, T, U> accumulator();

    /**
     * Reduction finisher.
     *
     * @return Reduction finisher.
     */
    Function<A, R> finisher();


}
