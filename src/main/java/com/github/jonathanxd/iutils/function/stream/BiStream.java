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
package com.github.jonathanxd.iutils.function.stream;

import com.github.jonathanxd.iutils.function.binary.BiBinaryOperator;
import com.github.jonathanxd.iutils.function.binary.NodeBiBinaryOperator;
import com.github.jonathanxd.iutils.function.binary.StackBiBinaryOperator;
import com.github.jonathanxd.iutils.function.collector.BiCollector;
import com.github.jonathanxd.iutils.function.comparators.BiComparator;
import com.github.jonathanxd.iutils.function.consumer.TriConsumer;
import com.github.jonathanxd.iutils.function.function.BiToDoubleFunction;
import com.github.jonathanxd.iutils.function.function.BiToIntFunction;
import com.github.jonathanxd.iutils.function.function.BiToLongFunction;
import com.github.jonathanxd.iutils.function.function.NodeArrayIntFunction;
import com.github.jonathanxd.iutils.function.function.NodeFunction;
import com.github.jonathanxd.iutils.function.function.TriFunction;
import com.github.jonathanxd.iutils.object.Node;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A BiStream is an adaptation of Java {@link Stream} operations to a 'bi' version. All operations
 * do the same thing as the {@link Stream} do, but takes and returns the double of values (key and value).
 *
 * {@link BiStream} is intended to be used for {@link java.util.Map} and other types of collections
 * that stores 2 values.
 *
 * {@link BiStream} have two default implementations, first is the {@link
 * com.github.jonathanxd.iutils.function.stream.walkable.WalkableNodeBiStream} and the second is
 * {@link BiJavaStream}. The first implementation is a non-lazy and non-parallel version of
 * BiStream, the second implementation is a wrapper that delegates adapted operations to the {@link
 * Stream}, some operations may not be delegated because is too much hard to adapt to {@link Stream
 * Java Stream}, but these operations don't take advantage of parallel operations (because does not
 * runs in parallel).
 *
 * @param <T> First value type.
 * @param <U> Second value type.
 */
public interface BiStream<T, U> extends BaseStream<Node<T, U>, BiStream<T, U>> {

    /**
     * @see Stream#filter(Predicate)
     */
    BiStream<T, U> filter(BiPredicate<? super T, ? super U> predicate);

    /**
     * @see Stream#map(Function)
     */
    <RK, RV> BiStream<RK, RV> map(NodeFunction<? super T, ? super U, ? extends RK, ? extends RV> mapper);

    /**
     * Maps keys and values to Java stream.
     *
     * @param mapper Mapper.
     * @param <R>    Value type.
     * @return Java stream.
     */
    <R> Stream<R> streamMap(BiFunction<? super T, ? super U, ? extends R> mapper);

    /**
     * Maps keys to Java stream.
     *
     * @param mapper Mapper.
     * @param <R>    Value type.
     * @return Java stream.
     */
    <R> Stream<R> streamKeyMap(Function<? super T, ? extends R> mapper);

    /**
     * Maps values to Java stream.
     *
     * @param mapper Mapper.
     * @param <R>    Value type.
     * @return Java stream.
     */
    <R> Stream<R> streamValueMap(Function<? super U, ? extends R> mapper);

    /**
     * @see Stream#mapToInt(ToIntFunction)
     */
    IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper);

    /**
     * @see Stream#mapToLong(ToLongFunction)
     */
    LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper);

    /**
     * @see Stream#mapToDouble(ToDoubleFunction)
     */
    DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper);

    /**
     * @see Stream#flatMap(Function)
     */
    <R, V> BiStream<R, V> flatMap(BiFunction<? super T, ? super U, ? extends BiStream<? extends R, ? extends V>> mapper);

    /**
     * Flat maps to Java stream.
     *
     * @param mapper Mapper.
     * @param <R>    Value type.
     * @return Java stream.
     */
    <R> Stream<R> streamFlatMap(BiFunction<? super T, ? super U, ? extends Stream<? extends R>> mapper);

    /**
     * Flat maps keys to Java stream.
     *
     * @param mapper Mapper.
     * @param <R>    Value type.
     * @return Java stream.
     */
    <R> Stream<R> streamKeyFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    /**
     * Flat maps values to Java stream.
     *
     * @param mapper Mapper.
     * @param <R>    Value type.
     * @return Java stream.
     */
    <R> Stream<R> streamValueFlatMap(Function<? super U, ? extends Stream<? extends R>> mapper);


    /**
     * @see Stream#flatMapToInt(Function)
     */
    IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper);

    /**
     * @see Stream#flatMapToLong(Function)
     */
    LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper);

    /**
     * @see Stream#flatMapToDouble(Function)
     */
    DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper);

    /**
     * @see Stream#distinct()
     */
    BiStream<T, U> distinctTwo();

    /**
     * Distinct keys.
     *
     * @see Stream#distinct()
     */
    BiStream<T, U> distinctFirst();

    /**
     * Distinct values.
     *
     * @see Stream#distinct()
     */
    BiStream<T, U> distinctSecond();

    /**
     * @see Stream#sorted()
     */
    BiStream<T, U> sortedTwo();

    /**
     * Sort by key.
     *
     * @see Stream#sorted()
     */
    BiStream<T, U> sortedFirst();

    /**
     * Sort by value.
     *
     * @see Stream#sorted()
     */
    BiStream<T, U> sortedSecond();

    /**
     * @see Stream#sorted(Comparator)
     */
    BiStream<T, U> sorted(BiComparator<? super T, ? super U> comparator);

    /**
     * @see Stream#peek(Consumer)
     */
    BiStream<T, U> peek(BiConsumer<? super T, ? super U> action);

    /**
     * @see Stream#limit(long)
     */
    BiStream<T, U> limit(long maxSize);

    /**
     * @see Stream#skip(long)
     */
    BiStream<T, U> skip(long n);

    /**
     * @see Stream#forEach(Consumer)
     */
    void forEach(BiConsumer<? super T, ? super U> action);

    /**
     * @see Stream#forEachOrdered(Consumer)
     */
    void forEachOrdered(BiConsumer<? super T, ? super U> action);

    /**
     * @see Stream#toArray()
     */
    Node<T, U>[] toArray();

    /**
     * @see Stream#toArray(IntFunction)
     */
    <A, V> Node<A, V>[] toArray(IntFunction<Node<A, V>[]> generator);

    /**
     * @see Stream#toArray(IntFunction)
     */
    <A, V> Node<A, V>[] toArray(NodeArrayIntFunction<A, V> generator);

    /**
     * @see Stream#reduce(Object, BinaryOperator)
     */
    Node<T, U> reduceTwo(T identity, U identity2, NodeBiBinaryOperator<T, U> accumulator);

    /**
     * A mixed reduce (accumulate keys).
     *
     * @see Stream#reduce(Object, BinaryOperator)
     */
    Node<List<T>, U> reduceMixed(List<T> init, U identity2, StackBiBinaryOperator<List<T>, T, U> accumulator);

    /**
     * Reduce keys.
     *
     * @see Stream#reduce(Object, BinaryOperator)
     */
    T reduceFirst(T identity, U identity2, BiBinaryOperator<T, U> accumulator);

    /**
     * Reduce values.
     *
     * @see Stream#reduce(Object, BinaryOperator)
     */
    U reduceSecond(T identity, U identity2, BiBinaryOperator<U, T> accumulator);

    /**
     * @see Stream#reduce(BinaryOperator)
     */
    Optional<Node<T, U>> reduceTwo(NodeBiBinaryOperator<T, U> accumulator);

    /**
     * Reduce keys.
     *
     * @see Stream#reduce(BinaryOperator)
     */
    Optional<T> reduceFirst(BiBinaryOperator<T, U> accumulator);

    /**
     * Reduce values.
     *
     * @see Stream#reduce(BinaryOperator)
     */
    Optional<U> reduceSecond(BiBinaryOperator<U, T> accumulator);

    /**
     * @see Stream#reduce(Object, BiFunction, BinaryOperator)
     */
    <R> R reduce(R identity,
                 TriFunction<R, ? super T, ? super U, R> accumulator);


    /**
     * Collect keys.
     *
     * @see Stream#collect(Collector)
     */
    <R, A> R collectKey(Collector<? super T, A, R> collector);

    /**
     * Collect values.
     *
     * @see Stream#collect(Collector)
     */
    <R, A> R collectValue(Collector<? super U, A, R> collector);

    /**
     * Collect keys.
     *
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     */
    <R> R collectKey(Supplier<R> supplier,
                     BiConsumer<R, ? super T> accumulator);

    /**
     * Collect values.
     *
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     */
    <R> R collectValue(Supplier<R> supplier,
                       BiConsumer<R, ? super U> accumulator);

    /**
     * Collect keys or values.
     *
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     */
    <R> R collectOne(Supplier<R> supplier,
                     TriConsumer<R, ? super T, ? super U> accumulator);

    /**
     * @see Stream#collect(Collector)
     */
    <R, A> R collect(BiCollector<? super T, ? super U, A, R> collector);


    /**
     * @see Stream#min(Comparator)
     */
    Optional<Node<T, U>> min(BiComparator<? super T, ? super U> comparator);

    /**
     * @see Stream#max(Comparator)
     */
    Optional<Node<T, U>> max(BiComparator<? super T, ? super U> comparator);

    /**
     * @see Stream#count()
     */
    long count();

    /**
     * @see Stream#anyMatch(Predicate)
     */
    boolean anyMatch(BiPredicate<? super T, ? super U> predicate);

    /**
     * @see Stream#allMatch(Predicate)
     */
    boolean allMatch(BiPredicate<? super T, ? super U> predicate);

    /**
     * @see Stream#noneMatch(Predicate)
     */
    boolean noneMatch(BiPredicate<? super T, ? super U> predicate);

    /**
     * @see Stream#findFirst()
     */
    Optional<Node<T, U>> findFirst();

    /**
     * @see Stream#findAny()
     */
    Optional<Node<T, U>> findAny();

}
