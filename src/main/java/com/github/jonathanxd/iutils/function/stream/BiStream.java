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
package com.github.jonathanxd.iutils.function.stream;

import com.github.jonathanxd.iutils.function.function.BiToDoubleFunction;
import com.github.jonathanxd.iutils.function.function.BiToIntFunction;
import com.github.jonathanxd.iutils.function.function.BiToLongFunction;
import com.github.jonathanxd.iutils.function.binary.BiBinaryOperator;
import com.github.jonathanxd.iutils.function.binary.NodeBiBinaryOperator;
import com.github.jonathanxd.iutils.function.binary.StackBiBinaryOperator;
import com.github.jonathanxd.iutils.function.collector.BiCollector;
import com.github.jonathanxd.iutils.function.comparators.BiComparator;
import com.github.jonathanxd.iutils.function.consumer.TriConsumer;
import com.github.jonathanxd.iutils.function.function.NodeArrayIntFunction;
import com.github.jonathanxd.iutils.function.function.NodeFunction;
import com.github.jonathanxd.iutils.function.function.TriFunction;
import com.github.jonathanxd.iutils.object.Node;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by jonathan on 05/03/16.
 */
public interface BiStream<T, U> extends BaseStream<Node<T, U>, BiStream<T, U>> {
    BiStream<T, U> filter(BiPredicate<? super T, ? super U> predicate);

    <RK, RV> BiStream<RK, RV> map(NodeFunction<? super T, ? super U, ? extends RK, ? extends RV> mapper);

    <R> Stream<R> streamMap(BiFunction<? super T, ? super U, ? extends R> mapper);
    <R> Stream<R> streamKeyMap(Function<? super T, ? extends R> mapper);
    <R> Stream<R> streamValueMap(Function<? super U, ? extends R> mapper);

    IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper);

    LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper);

    DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper);

    <R, V> BiStream<R, V> flatMap(BiFunction<? super T, ? super U, ? extends BiStream<? extends R, ? extends V>> mapper);

    <R> Stream<R> streamFlatMap(BiFunction<? super T, ? super U, ? extends Stream<? extends R>> mapper);
    <R> Stream<R> streamKeyFlatMap(Function<? super T,? extends Stream<? extends R>> mapper);
    <R> Stream<R> streamValueFlatMap(Function<? super U, ? extends Stream<? extends R>> mapper);

    IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper);

    LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper);

    DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper);

    BiStream<T, U> distinctTwo();

    BiStream<T, U> distinctFirst();

    BiStream<T, U> distinctSecond();

    /**
     * Irrelugar Sort
     *
     * @return This
     */
    BiStream<T, U> sortedTwo();

    BiStream<T, U> sortedFirst();

    BiStream<T, U> sortedSecond();

    BiStream<T, U> sorted(BiComparator<? super T, ? super U> comparator);

    BiStream<T, U> peek(BiConsumer<? super T, ? super U> action);

    BiStream<T, U> limit(long maxSize);

    BiStream<T, U> skip(long n);

    void forEach(BiConsumer<? super T, ? super U> action);

    void forEachOrdered(BiConsumer<? super T, ? super U> action);

    Node<T, U>[] toArray();

    <A, V> Node<A, V>[] toArray(IntFunction<Node<A, V>[]> generator);

    <A, V> Node<A, V>[] toArray(NodeArrayIntFunction<A, V> generator);

    Node<T, U> reduceTwo(T identity, U identity2, NodeBiBinaryOperator<T, U> accumulator);

    Node<List<T>, U> reduceMixed(List<T> init, U identity2, StackBiBinaryOperator<List<T>, T, U> accumulator);

    T reduceFirst(T identity, U identity2, BiBinaryOperator<T, U> accumulator);

    U reduceSecond(T identity, U identity2, BiBinaryOperator<U, T> accumulator);

    Optional<Node<T, U>> reduceTwo(NodeBiBinaryOperator<T, U> accumulator);

    Optional<T> reduceFirst(BiBinaryOperator<T, U> accumulator);

    Optional<U> reduceSecond(BiBinaryOperator<U, T> accumulator);

    <R> R reduce(R identity,
                 TriFunction<R, ? super T, ? super U, R> accumulator,
                 BinaryOperator<R> combiner);

    <R> R collectFirst(Supplier<R> supplier,
                       BiConsumer<R, ? super T> accumulator,
                       BiConsumer<R, R> combiner);

    <R> R collectSecond(Supplier<R> supplier,
                        BiConsumer<R, ? super U> accumulator,
                        BiConsumer<R, R> combiner);

    <R> R collectTwo(Supplier<R> supplier,
                     TriConsumer<R, ? super T, ? super U> accumulator,
                     BiConsumer<R, R> combiner);

    <R, A> R collect(BiCollector<? super T, ? super U, A, R> collector);

    Optional<Node<T, U>> min(BiComparator<? super T, ? super U> comparator);

    Optional<Node<T, U>> max(BiComparator<? super T, ? super U> comparator);

    long count();

    boolean anyMatch(BiPredicate<? super T, ? super U> predicate);

    boolean allMatch(BiPredicate<? super T, ? super U> predicate);

    boolean noneMatch(BiPredicate<? super T, ? super U> predicate);

    Optional<Node<T, U>> findFirst();

    Optional<Node<T, U>> findAny();

}
