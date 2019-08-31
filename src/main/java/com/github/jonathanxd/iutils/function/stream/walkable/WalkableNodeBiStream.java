/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.function.stream.walkable;

import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.container.IMutableContainer;
import com.github.jonathanxd.iutils.container.MutableContainer;
import com.github.jonathanxd.iutils.function.binary.BiBinaryOperator;
import com.github.jonathanxd.iutils.function.binary.PairBiBinaryOperator;
import com.github.jonathanxd.iutils.function.binary.StackBiBinaryOperator;
import com.github.jonathanxd.iutils.function.collector.BiCollector;
import com.github.jonathanxd.iutils.function.comparators.BiComparator;
import com.github.jonathanxd.iutils.function.consumer.TriConsumer;
import com.github.jonathanxd.iutils.function.function.BiToDoubleFunction;
import com.github.jonathanxd.iutils.function.function.BiToIntFunction;
import com.github.jonathanxd.iutils.function.function.BiToLongFunction;
import com.github.jonathanxd.iutils.function.function.PairArrayIntFunction;
import com.github.jonathanxd.iutils.function.function.PairFunction;
import com.github.jonathanxd.iutils.function.function.TriFunction;
import com.github.jonathanxd.iutils.function.stream.BiStream;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.Pairs;
import com.github.jonathanxd.iutils.sort.SortingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A {@link BiStream} backed by a {@link Walkable} of {@link Pair}.
 *
 * Obs: This implementation is not a real implementation of a Stream, the name is only for
 * convention, this implementation is nether lazy nor parallel-capable. This implementation is
 * eager, sequential and backed by a {@link Walkable}.
 *
 * @param <T> First value type.
 * @param <U> Second value type.
 * @see Walkable
 */
public class WalkableNodeBiStream<T, U> extends WalkableBiStream<T, U, Walkable<Pair<T, U>>> {

    public WalkableNodeBiStream(Walkable<Pair<T, U>> entries) {
        super(entries);
    }

    protected WalkableNodeBiStream(List<Pair<T, U>> entries) {
        super(Walkable.fromList(entries));
    }


    protected WalkableNodeBiStream(Walkable<Pair<T, U>> walkable, Runnable closeRunnable) {
        super(walkable, closeRunnable);
    }

    @Override
    public BiStream<T, U> filter(BiPredicate<? super T, ? super U> predicate) {

        WalkableNodeBiStream<T, U> biStream = newBi();

        Walkable<Pair<T, U>> iterator = biStream.unsafeIter();

        while (iterator.hasNext()) {
            Pair<T, U> entry = iterator.next();

            if (!predicate.test(entry.getFirst(), entry.getSecond()))
                iterator.remove();
        }

        // Improved performance
        iterator.resetIndex();

        return biStream;
    }

    private WalkableNodeBiStream<T, U> newBi() {
        return new WalkableNodeBiStream<>(this.getWalkable().newWithoutState());
    }

    private WalkableNodeBiStream<T, U> newCloned() {
        return new WalkableNodeBiStream<>(this.getWalkable().clone());
    }

    private WalkableNodeBiStream<T, U> newClonedWithRunnable(Runnable runnable) {
        return new WalkableNodeBiStream<>(this.getWalkable().clone(), runnable);
    }

    @Override
    public <RK, RV> BiStream<RK, RV> map(PairFunction<? super T, ? super U, ? extends RK, ? extends RV> mapper) {

        Map<RK, RV> newMap = new HashMap<>();

        loop(entry -> {
            Pair<? extends RK, ? extends RV> node = mapper.apply(entry.getFirst(), entry.getSecond());
            newMap.put(node.getFirst(), node.getSecond());
        });

        return new WalkableNodeBiStream<>(Walkable.fromList(newMap));
    }

    @Override
    public <R> Stream<R> streamMap(BiFunction<? super T, ? super U, ? extends R> mapper) {
        List<R> rList = new ArrayList<>();

        loop(entry -> {
            R ret = mapper.apply(entry.getFirst(), entry.getSecond());
            rList.add(ret);
        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamKeyMap(Function<? super T, ? extends R> mapper) {
        List<R> rList = new ArrayList<>();

        loop(entry -> {
            R ret = mapper.apply(entry.getFirst());
            rList.add(ret);
        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamValueMap(Function<? super U, ? extends R> mapper) {
        List<R> rList = new ArrayList<>();

        loop(entry -> {
            R ret = mapper.apply(entry.getSecond());
            rList.add(ret);
        });

        return rList.stream();
    }

    @Override
    public IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper) {

        IntStream.Builder builder = IntStream.builder();

        loop(entry -> builder.add(mapper.applyAsInt(entry.getFirst(), entry.getSecond())));

        return builder.build();
    }

    @Override
    public LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper) {

        LongStream.Builder builder = LongStream.builder();

        loop(entry -> builder.add(mapper.applyAsLong(entry.getFirst(), entry.getSecond())));

        return builder.build();
    }

    @Override
    public DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper) {
        DoubleStream.Builder builder = DoubleStream.builder();

        loop(entry -> builder.add(mapper.applyAsDouble(entry.getFirst(), entry.getSecond())));

        return builder.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R, V> BiStream<R, V> flatMap(BiFunction<? super T, ? super U, ? extends BiStream<? extends R, ? extends V>> mapper) {

        List<Pair<R, V>> nodes = new ArrayList<>();

        loop(e -> {
            BiStream<? extends R, ? extends V> bi = mapper.apply(e.getFirst(), e.getSecond());

            Iterator<? extends Pair<? extends R, ? extends V>> iterator = bi.iterator();

            while (iterator.hasNext()) {
                Pair<? extends R, ? extends V> next = iterator.next();

                nodes.add((Pair<R, V>) next);
            }
        });

        return new WalkableNodeBiStream<>(Walkable.fromList(nodes));

    }

    @Override
    public <R> Stream<R> streamFlatMap(BiFunction<? super T, ? super U, ? extends Stream<? extends R>> mapper) {

        List<R> rList = new ArrayList<>();

        loop(e -> {

            Stream<? extends R> streamA = mapper.apply(e.getFirst(), e.getSecond());
            streamA.forEach(rList::add);

        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamKeyFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        List<R> rList = new ArrayList<>();

        loop(e -> {

            Stream<? extends R> streamA = mapper.apply(e.getFirst());
            streamA.forEach(rList::add);

        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamValueFlatMap(Function<? super U, ? extends Stream<? extends R>> mapper) {
        List<R> rList = new ArrayList<>();

        loop(e -> {

            Stream<? extends R> streamA = mapper.apply(e.getSecond());
            streamA.forEach(rList::add);

        });

        return rList.stream();
    }

    @Override
    public IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper) {
        IntStream.Builder builder = IntStream.builder();

        loop(e -> {

            IntStream is = mapper.apply(e.getFirst(), e.getSecond());
            is.forEach(builder::add);

        });

        return builder.build();
    }

    @Override
    public LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper) {
        LongStream.Builder builder = LongStream.builder();

        loop(e -> {

            LongStream ls = mapper.apply(e.getFirst(), e.getSecond());
            ls.forEach(builder::add);

        });

        return builder.build();
    }

    @Override
    public DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper) {
        DoubleStream.Builder builder = DoubleStream.builder();

        loop(e -> {

            DoubleStream ds = mapper.apply(e.getFirst(), e.getSecond());
            ds.forEach(builder::add);

        });

        return builder.build();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public BiStream<T, U> distinctTwo() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        Walkable<Pair<T, U>> iterator = biStream.unsafeIter();

        iterator.distinctInternal();

        iterator.resetIndex();

        return biStream;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public BiStream<T, U> distinctFirst() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        List<Integer> hashCodes = new ArrayList<>();

        Walkable<Pair<T, U>> iterator = biStream.unsafeIter();

        iterator.distinctInternal(Pair::getFirst);

        iterator.resetIndex();

        return biStream;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public BiStream<T, U> distinctSecond() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        List<Integer> hashCodes = new ArrayList<>();

        Walkable<Pair<T, U>> iterator = biStream.unsafeIter();

        iterator.distinctInternal(Pair::getSecond);

        iterator.resetIndex();

        return biStream;
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public BiStream<T, U> sortedTwo() {
        WalkableNodeBiStream<T, U> biStream = newBi();
        biStream.getWalkable().sort((e, e2) -> {

            Comparable<T> comparableE1T = (Comparable<T>) e.getFirst();
            Comparable<U> comparableE1U = (Comparable<U>) e.getSecond();

            T e2T = e2.getFirst();
            U e2U = e2.getSecond();

            int compare;

            if ((compare = comparableE1T.compareTo(e2T)) == comparableE1U.compareTo(e2U)) {
                return compare;
            } else {
                if ((compare = comparableE1T.compareTo(e2T)) > comparableE1U.compareTo(e2U))
                    return compare;
                else
                    return comparableE1U.compareTo(e2U);
            }
        });

        return biStream;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiStream<T, U> sortedFirst() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        biStream.getWalkable().sort((e, e2) -> {

            Comparable<T> comparableE1T = (Comparable<T>) e.getFirst();

            T e2T = e2.getFirst();

            return comparableE1T.compareTo(e2T);
        });

        return biStream;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiStream<T, U> sortedSecond() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        biStream.getWalkable().sort((e, e2) -> {

            Comparable<U> comparableE1U = (Comparable<U>) e.getSecond();

            U e2U = e2.getSecond();

            return comparableE1U.compareTo(e2U);
        });

        return biStream;
    }

    @Override
    public BiStream<T, U> sorted(BiComparator<? super T, ? super U> comparator) {
        WalkableNodeBiStream<T, U> biStream = newBi();
        biStream.getWalkable().sort((e, e2) -> comparator.compare(e.getFirst(), e.getSecond(), e2.getFirst(), e2.getSecond()));
        return biStream;
    }

    @Override
    public BiStream<T, U> peek(BiConsumer<? super T, ? super U> action) {
        WalkableNodeBiStream<T, U> biStream = newBi();
        biStream.consume(e -> action.accept(e.getFirst(), e.getSecond()));
        return biStream;
    }

    @Override
    public BiStream<T, U> limit(long maxSize) {
        return limitTo(maxSize);
    }

    /**
     * Stateful - Keep State Intermediate - New Stream
     *
     * @param n 0
     * @return 0
     */
    @Override
    public BiStream<T, U> skip(long n) {
        WalkableNodeBiStream<T, U> biStream = newBi();
        biStream.skipTo(n);
        return biStream;
    }

    @Override
    public void forEach(BiConsumer<? super T, ? super U> action) {
        if (!getWalkable().hasNext())
            return;

        consume(e -> action.accept(e.getFirst(), e.getSecond()));
    }

    @Override
    public void forEachOrdered(BiConsumer<? super T, ? super U> action) {
        if (!getWalkable().hasNext())
            return;

        consume(e -> action.accept(e.getFirst(), e.getSecond()));
    }

    @Override
    public Pair<T, U>[] toArray() {
        return this.getWalkable().toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A, V> Pair<A, V>[] toArray(IntFunction<Pair<A, V>[]> generator) {
        return toArray(new BackPortIntFunc<>(generator));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A, V> Pair<A, V>[] toArray(PairArrayIntFunction<A, V> generator) {
        Pair<T, U>[] pairArray = toArray();

        Pair<A, V>[] avPair = generator.apply(pairArray.length);

        for (int x = 0; x < pairArray.length; ++x) {
            Pair<T, U> pair = pairArray[x];
            avPair[x] = Pairs.of((A) pair.getFirst(), (V) pair.getSecond());
        }

        return avPair;
    }

    @Override
    public Pair<T, U> reduceTwo(T identity, U identify2, PairBiBinaryOperator<T, U> accumulator) {

        IMutableContainer<Pair<T, U>> nodeContainer = new MutableContainer<>(Pairs.of(identity, identify2));

        consume(n -> nodeContainer.set(
                accumulator.apply(nodeContainer.get().getFirst(), nodeContainer.get().getSecond(),
                        n.getFirst(), n.getSecond())
                )
        );

        return nodeContainer.get();
    }

    @Override
    public Pair<List<T>, U> reduceMixed(List<T> init, U identify, StackBiBinaryOperator<List<T>, T, U> accumulator) {

        IMutableContainer<Pair<List<T>, U>> nodeContainer = new MutableContainer<>(Pairs.of(init, identify));

        consume(n -> nodeContainer.mapThisValue(
                current -> Pairs.of(current.getFirst(),
                        accumulator.apply(nodeContainer.get().getFirst(), nodeContainer.get().getSecond(),
                                n.getFirst(), n.getSecond())
                ))
        );

        return nodeContainer.get();
    }

    @Override
    public T reduceFirst(T identify, U identify2, BiBinaryOperator<T, U> accumulator) {

        IMutableContainer<Pair<T, U>> nodeContainer = new MutableContainer<>(Pairs.of(identify, identify2));

        consume(n -> nodeContainer.mapThisValue(
                (current) -> Pairs.of(
                        accumulator.apply(current.getFirst(), current.getSecond(), n.getFirst(), n.getSecond()),
                        current.getSecond())
                )
        );
        return nodeContainer.get().getFirst();
    }

    @Override
    public U reduceSecond(T identify, U identify2, BiBinaryOperator<U, T> accumulator) {
        IMutableContainer<Pair<T, U>> nodeContainer = new MutableContainer<>(Pairs.of(identify, identify2));

        consume(n -> nodeContainer.mapThisValue(
                (current) -> Pairs.of(current.getFirst(),
                        accumulator.apply(current.getSecond(), current.getFirst(), n.getSecond(), n.getFirst())
                )
                )
        );

        return nodeContainer.get().getSecond();
    }

    @Override
    public Optional<Pair<T, U>> reduceTwo(PairBiBinaryOperator<T, U> accumulator) {
        IMutableContainer<Pair<T, U>> nodeContainer = new MutableContainer<>(null);

        consume(n -> {
            if (!nodeContainer.isPresent())
                nodeContainer.set(n);
            else
                nodeContainer.mapThisValue(current -> accumulator.apply(current.getFirst(), current.getSecond(),
                        n.getFirst(), n.getSecond()));
        });

        return !nodeContainer.isPresent() ? Optional.empty() : Optional.of(nodeContainer.get());
    }

    @Override
    public Optional<T> reduceFirst(BiBinaryOperator<T, U> accumulator) {
        IMutableContainer<Pair<T, U>> pairContainer = new MutableContainer<>(null);

        consume(n -> {
                    if (!pairContainer.isPresent())
                        pairContainer.set(n);
                    else
                        pairContainer.mapThisValue(
                                (current) -> Pairs.of(
                                        accumulator.apply(current.getFirst(), current.getSecond(), n.getFirst(), n.getSecond()),
                                        current.getSecond()
                                )
                        );
                }
        );

        return !pairContainer.isPresent() ? Optional.empty() : Optional.of(pairContainer.get().getFirst());
    }

    @Override
    public Optional<U> reduceSecond(BiBinaryOperator<U, T> accumulator) {
        IMutableContainer<Pair<T, U>> pairContainer = new MutableContainer<>(null);

        consume(n -> {
                    if (!pairContainer.isPresent())
                        pairContainer.set(n);
                    else
                        pairContainer.mapThisValue((current) ->
                                Pairs.of(current.getFirst(),
                                        accumulator.apply(current.getSecond(), current.getFirst(), n.getSecond(), n.getFirst()))
                        );
                }
        );

        return !pairContainer.isPresent() ? Optional.empty() : Optional.of(pairContainer.get().getSecond());
    }

    @Override
    public <R> R reduce(R identity, TriFunction<R, ? super T, ? super U, R> accumulator) {

        IMutableContainer<R> ret = new MutableContainer<>(identity);

        consume(n -> ret.mapThisValue(current -> accumulator.apply(current, n.getFirst(), n.getSecond())));

        return ret.get();
    }

    @Override
    public <R, A> R collectKey(Collector<? super T, A, R> collector) {

        Supplier<A> supplier = collector.supplier();
        BiConsumer<A, ? super T> accumulator = collector.accumulator();
        Function<A, R> finisher = collector.finisher();

        if (!this.getWalkable().hasNext())
            return finisher.apply(supplier.get());

        final A retVal = supplier.get();

        consume(n -> accumulator.accept(retVal, n.getFirst()));

        return finisher.apply(retVal);

    }

    @Override
    public <R, A> R collectValue(Collector<? super U, A, R> collector) {
        Supplier<A> supplier = collector.supplier();
        BiConsumer<A, ? super U> accumulator = collector.accumulator();
        Function<A, R> finisher = collector.finisher();

        if (!this.getWalkable().hasNext())
            return finisher.apply(supplier.get());

        final A retVal = supplier.get();

        consume(n -> accumulator.accept(retVal, n.getSecond()));

        return finisher.apply(retVal);
    }

    @Override
    public <R> R collectKey(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator) {
        return collectOne(supplier, (ret, key, value) -> accumulator.accept(ret, key));
    }

    @Override
    public <R> R collectValue(Supplier<R> supplier, BiConsumer<R, ? super U> accumulator) {

        return collectOne(supplier, (ret, key, value) -> accumulator.accept(ret, value));
    }

    @Override
    public <R> R collectOne(Supplier<R> supplier, TriConsumer<R, ? super T, ? super U> accumulator) {
        if (!getWalkable().hasNext())
            return supplier.get();

        final R retVal = supplier.get();

        consume(n -> accumulator.accept(retVal, n.getFirst(), n.getSecond()));

        return retVal;
    }

    @Override
    public <R, A> R collect(BiCollector<? super T, ? super U, A, R> collector) {

        Supplier<A> supplier = collector.supplier();
        TriConsumer<A, ? super T, ? super U> accumulator = collector.accumulator();
        Function<A, R> finisher = collector.finisher();

        if (!this.getWalkable().hasNext())
            return finisher.apply(supplier.get());

        final A retVal = supplier.get();

        consume(n -> {
            accumulator.accept(retVal, n.getFirst(), n.getSecond());
        });

        return finisher.apply(retVal);

    }

    /**
     * terminal
     *
     * @param comparator t
     * @return t
     */
    @Override
    public Optional<Pair<T, U>> min(BiComparator<? super T, ? super U> comparator) {
        SortingResult<Pair<T, U>> sortingResult = this.getWalkable()
                .compareToComparison((c, c2) -> comparator.compare(c.getFirst(), c.getSecond(), c2.getFirst(), c2.getSecond()));

        return sortingResult.min();
    }

    /**
     * terminal
     *
     * @param comparator t
     * @return t
     */
    @Override
    public Optional<Pair<T, U>> max(BiComparator<? super T, ? super U> comparator) {
        SortingResult<Pair<T, U>> sortingResult =
                this.getWalkable()
                        .compareToComparison((c, c2) -> comparator.compare(c.getFirst(), c.getSecond(), c2.getFirst(), c2.getSecond()));

        return sortingResult.max();
    }

    @Override
    public long count() {
        return getWalkable().size();
    }

    @Override
    public boolean anyMatch(BiPredicate<? super T, ? super U> predicate) {

        Walkable<Pair<T, U>> iter = unsafeIter();

        while (iter.hasNext()) {
            Pair<T, U> node = iter.next();

            if (predicate.test(node.getFirst(), node.getSecond())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean allMatch(BiPredicate<? super T, ? super U> predicate) {

        Walkable<Pair<T, U>> iter = unsafeIter();

        while (iter.hasNext()) {
            Pair<T, U> node = iter.next();

            if (!predicate.test(node.getFirst(), node.getSecond())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean noneMatch(BiPredicate<? super T, ? super U> predicate) {
        return !allMatch(predicate);
    }

    @Override
    public Optional<Pair<T, U>> findFirst() {

        List<Pair<T, U>> currentList = getWalkable().toList();

        return currentList == null || currentList.isEmpty() ? Optional.empty() : Optional.of(currentList.get(0));
    }

    @Override
    public Optional<Pair<T, U>> findAny() {
        return !getWalkable().hasCurrent() ? Optional.empty() : Optional.of(getWalkable().getCurrent());
    }

    @Override
    public Iterator<Pair<T, U>> iterator() {

        return nodeList().iterator();
    }

    @Override
    public Spliterator<Pair<T, U>> spliterator() {
        return nodeList().spliterator();
    }

    @Override
    public BiStream<T, U> sequential() {

        return this;
    }

    @Override
    public BiStream<T, U> parallel() {
        return this;
    }

    @Override
    public BiStream<T, U> unordered() {
        return this;
    }

    @Override
    public BiStream<T, U> onClose(Runnable closeHandler) {
        return newClonedWithRunnable(closeHandler);
    }

    @Deprecated
    private Walkable<Pair<T, U>> iter() {
        return getWalkable().newWithoutState();
    }

    private Walkable<Pair<T, U>> safeIter() {
        return getWalkable().newWithoutState();
    }

    private Walkable<Pair<T, U>> unsafeIter() {

        if (!this.getWalkable().hasNext())
            //throw new NoSuchElementException("End of stream!");
            return Walkable.empty();

        return getWalkable();
    }

    private void loop(Consumer<Pair<T, U>> consumer) {
        getWalkable().forEach(consumer::accept);
        updateState();
    }

    private void consume(Consumer<Pair<T, U>> consumer) {

        /*if (!this.getWalkable().hasNext())
            throw new NoSuchElementException("End of stream!");*/

        while (this.getWalkable().hasNext()) {
            consumer.accept(this.getWalkable().next());
            this.getWalkable().remove();
        }

        updateState();
    }

    private void skipTo(long n) {
        long current = 0;
        while (this.getWalkable().hasNext() && current < n) {
            this.getWalkable().next();
            this.getWalkable().remove();
            ++current;
        }
        this.updateState();
    }

    private WalkableNodeBiStream<T, U> limitTo(long n) {
        Walkable<Pair<T, U>> iter = safeIter();

        List<Pair<T, U>> entries = new ArrayList<>();

        long current = 0;
        while (iter.hasNext() && current < n) {
            entries.add(iter.next());
            ++current;
        }

        WalkableNodeBiStream<T, U> walkableNodeBiStream = new WalkableNodeBiStream<>(entries);

        walkableNodeBiStream.updateState();

        return walkableNodeBiStream;
    }

    private List<Pair<T, U>> nodeList() {
        List<Pair<T, U>> nodeList = new ArrayList<>();

        loop(nodeList::add);

        return nodeList;
    }


    private static class BackPortIntFunc<T, E> implements PairArrayIntFunction<T, E> {

        private final IntFunction<Pair<T, E>[]> intFunction;

        private BackPortIntFunc(IntFunction<Pair<T, E>[]> intFunction) {
            this.intFunction = intFunction;
        }

        @Override
        public Pair<T, E>[] apply(int i) {
            return intFunction.apply(i);
        }
    }

}
