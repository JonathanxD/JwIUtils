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
package com.github.jonathanxd.iutils.function.stream;

import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.Pairs;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class StreamUtil {
    private StreamUtil() {
    }

    // Stream filterAs

    /**
     * Filter all elements that is of {@code type}, cast to {@code type} and return stream of casted
     * objects.
     *
     * @param stream Stream to filter.
     * @param type   Type to filter and cast.
     * @param <T>    Element type.
     * @param <R>    Target type.
     * @return Stream of {@link R}.
     */
    public static <T, R> Stream<R> filterAs(Stream<T> stream, Class<R> type) {
        return stream.filter(type::isInstance).map(type::cast);
    }

    /**
     * Unsafe version of {@link #filterAs(Stream, Class)}.
     *
     * @param stream    Stream to filter.
     * @param predicate Predicate that checks if {@link T element of type T} is instance of {@link R
     *                  type R}.
     * @param <T>       Element type.
     * @param <R>       Target type.
     * @return Unsafe checked stream of {@link R}.
     */
    @SuppressWarnings("unchecked")
    public static <T, R> Stream<R> filterAs(Stream<T> stream, Predicate<T> predicate) {
        return (Stream<R>) stream.filter(predicate);
    }

    /**
     * Filter all elements that {@code function} returns an optional of present value.
     *
     * @param stream   Stream to filter.
     * @param function Function that returns {@link Optional#of(Object) optional of value} to
     *                 filter.
     * @param <T>      Element type.
     * @param <R>      Target type.
     * @return Checked stream of {@link R}.
     */
    @SuppressWarnings("unchecked")
    public static <T, R> Stream<R> filterAsOpt(Stream<T> stream, Function<T, Optional<R>> function) {
        return stream.map(function).filter(Optional::isPresent).map(Optional::get);
    }

    // BiStream filterAs

    /**
     * Filter first and second elements that is instance of {@code firstType} and {@code
     * secondType}, cast them and return a BiStream with values of these types.
     *
     * @param stream     Stream to filter.
     * @param firstType  Type of first elements.
     * @param secondType Type of second elements.
     * @param <A>        First element type.
     * @param <B>        Second element type.
     * @param <AR>       First target element type.
     * @param <BR>       Second target element type.
     * @return BiStream of {@link AR} and {@link BR}.
     */
    public static <A, B, AR, BR> BiStream<AR, BR> filterAs(BiStream<A, B> stream, Class<AR> firstType, Class<BR> secondType) {
        return stream.filter((a, b) -> firstType.isInstance(a) && secondType.isInstance(b))
                .map((a, b) -> Pairs.of(firstType.cast(a), secondType.cast(b)));
    }

    /**
     * Unsafe version of {@link #filterAs(BiStream, Class, Class)}.
     *
     * @param stream          Stream to filter.
     * @param firstPredicate  Filter of first element of pair.
     * @param secondPredicate Filter of second element of pair.
     * @param <A>             First element type.
     * @param <B>             Second element type.
     * @param <AR>            Target first element type.
     * @param <BR>            Target second element type.
     * @return Unsafe BiStream of {@link AR} and {@link BR}.
     */
    @SuppressWarnings("unchecked")
    public static <A, B, AR, BR> BiStream<AR, BR> filterAs(BiStream<A, B> stream,
                                                           Predicate<A> firstPredicate,
                                                           Predicate<B> secondPredicate) {
        return (BiStream<AR, BR>) stream.filter((a, b) -> firstPredicate.test(a) && secondPredicate.test(b));
    }

    /**
     * Filter all elements that {@code function} returns an optional of present value.
     *
     * @param stream   Stream to filter.
     * @param function Function that returns {@link Optional#of(Object) optional of pair of first
     *                 and second element} to filter.
     * @param <A>      First element type.
     * @param <B>      Second element type.
     * @param <AR>     Target first element type.
     * @param <BR>     Target second element type.
     * @return BiStream of {@link AR} and {@link BR}.
     */
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static <A, B, AR, BR> BiStream<AR, BR> filterAsOpt(BiStream<A, B> stream, BiFunction<A, B, Optional<Pair<AR, BR>>> function) {
        return stream.map(createOptionalComplex(function)::apply)
                .filter((a, b) -> a.isPresent() && b.isPresent())
                .map((a, b) -> Pairs.of(a.get(), b.get()));
    }

    private static <A, B, AR, BR>
    BiFunction<A, B, Pair<Optional<AR>, Optional<BR>>> createOptionalComplex(BiFunction<A, B, Optional<Pair<AR, BR>>> function) {
        return function
                .andThen(x -> x.map(p -> Pair.of(Optional.of(p.getFirst()), Optional.of(p.getSecond())))
                        .orElse(Pair.of(Optional.empty(), Optional.empty())));
    }
}
