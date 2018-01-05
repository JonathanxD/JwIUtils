/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.Pairs;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
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
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Wraps Java {@link Stream} of entries into a {@link BiStream}.
 *
 * Most of operations is adapted and wrapped to the {@code wrappedStream}.
 */
public final class BiJavaStream<T, U> implements BiStream<T, U> {

    private final Stream<Map.Entry<T, U>> wrapped;

    BiJavaStream(Stream<Map.Entry<T, U>> stream) {
        this.wrapped = stream;
    }

    @SuppressWarnings("unchecked")
    private <X, Y> BiJavaStream<X, Y> stream(Stream<Map.Entry<T, U>> stream) {
        if (this.wrapped == stream)
            return (BiJavaStream<X, Y>) this;

        return (BiJavaStream<X, Y>) new BiJavaStream<>(stream);
    }

    @Override
    public BiStream<T, U> filter(BiPredicate<? super T, ? super U> predicate) {
        return stream(this.wrapped.filter(o -> predicate.test(o.getKey(), o.getValue())));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <RK, RV> BiStream<RK, RV> map(PairFunction<? super T, ? super U, ? extends RK, ? extends RV> mapper) {
        return stream(this.wrapped.map(o -> {
            Pair<? extends RK, ? extends RV> apply = mapper.apply(o.getKey(), o.getValue());

            return (Map.Entry) this.entryFromPair(apply);
        }));
    }

    @Override
    public <R> Stream<R> streamMap(BiFunction<? super T, ? super U, ? extends R> mapper) {
        return this.wrapped.map(o -> mapper.apply(o.getKey(), o.getValue()));
    }

    @Override
    public <R> Stream<R> streamKeyMap(Function<? super T, ? extends R> mapper) {
        return this.wrapped.map(o -> mapper.apply(o.getKey()));
    }

    @Override
    public <R> Stream<R> streamValueMap(Function<? super U, ? extends R> mapper) {
        return this.wrapped.map(o -> mapper.apply(o.getValue()));
    }

    @Override
    public IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper) {
        return this.wrapped.mapToInt(value -> mapper.applyAsInt(value.getKey(), value.getValue()));
    }

    @Override
    public LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper) {
        return this.wrapped.mapToLong(value -> mapper.applyAsLong(value.getKey(), value.getValue()));
    }

    @Override
    public DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper) {
        return this.wrapped.mapToDouble(value -> mapper.applyAsDouble(value.getKey(), value.getValue()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R, V> BiStream<R, V> flatMap(BiFunction<? super T, ? super U, ? extends BiStream<? extends R, ? extends V>> mapper) {
        return new BiJavaStream<>(this.wrapped.flatMap(o ->
                mapper.apply(o.getKey(), o.getValue()).<Map.Entry<R, V>>streamMap(AbstractMap.SimpleEntry<R, V>::new)
        ));
    }

    @Override
    public <R> Stream<R> streamFlatMap(BiFunction<? super T, ? super U, ? extends Stream<? extends R>> mapper) {
        return this.wrapped.flatMap(o -> mapper.apply(o.getKey(), o.getValue()));
    }

    @Override
    public <R> Stream<R> streamKeyFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return this.wrapped.flatMap(o -> mapper.apply(o.getKey()));
    }

    @Override
    public <R> Stream<R> streamValueFlatMap(Function<? super U, ? extends Stream<? extends R>> mapper) {
        return this.wrapped.flatMap(o -> mapper.apply(o.getValue()));
    }

    @Override
    public IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper) {
        return this.wrapped.flatMapToInt(o -> mapper.apply(o.getKey(), o.getValue()));
    }

    @Override
    public LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper) {
        return this.wrapped.flatMapToLong(o -> mapper.apply(o.getKey(), o.getValue()));
    }

    @Override
    public DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper) {
        return this.wrapped.flatMapToDouble(o -> mapper.apply(o.getKey(), o.getValue()));
    }

    @Override
    public BiStream<T, U> distinctTwo() {
        return stream(this.wrapped.distinct());
    }

    @Override
    public BiStream<T, U> distinctFirst() {
        return this.distinctInternal(Map.Entry::getKey);
    }

    @Override
    public BiStream<T, U> distinctSecond() {
        return this.distinctInternal(Map.Entry::getValue);
    }

    private <R> BiStream<T, U> distinctInternal(Function<Map.Entry<T, U>, R> function) {
        List<Map.Entry<T, U>> collect = this.wrapped.collect(Collectors.toList());

        List<Map.Entry<T, U>> list = new ArrayList<>();

        for (Map.Entry<T, U> tuEntry : collect) {
            boolean any = false;

            R r = function.apply(tuEntry);

            for (Map.Entry<T, U> t1 : list) {

                R r1 = function.apply(t1);

                if (r.hashCode() == r1.hashCode() && r.equals(r1)) {
                    any = true;
                }
            }

            if (!any)
                list.add(tuEntry);
        }

        return new BiJavaStream<>(list.stream());
    }

    @Override
    public BiStream<T, U> sortedTwo() {
        return stream(this.wrapped.sorted());
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiStream<T, U> sortedFirst() {
        return stream(this.wrapped.sorted((o1, o2) -> ((Comparable<T>) o1.getKey()).compareTo(o2.getKey())));
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiStream<T, U> sortedSecond() {
        return stream(this.wrapped.sorted((o1, o2) -> ((Comparable<U>) o1.getValue()).compareTo(o2.getValue())));
    }

    @Override
    public BiStream<T, U> sorted(BiComparator<? super T, ? super U> comparator) {
        return stream(this.wrapped.sorted((o1, o2) -> comparator.compare(o1.getKey(), o1.getValue(), o2.getKey(), o2.getValue())));
    }

    @Override
    public BiStream<T, U> peek(BiConsumer<? super T, ? super U> action) {
        return stream(this.wrapped.peek(tuEntry -> action.accept(tuEntry.getKey(), tuEntry.getValue())));
    }

    @Override
    public BiStream<T, U> limit(long maxSize) {
        return stream(this.wrapped.limit(maxSize));
    }

    @Override
    public BiStream<T, U> skip(long n) {
        return stream(this.wrapped.skip(n));
    }

    @Override
    public void forEach(BiConsumer<? super T, ? super U> action) {
        this.wrapped.forEach(tuEntry -> action.accept(tuEntry.getKey(), tuEntry.getValue()));
    }

    @Override
    public void forEachOrdered(BiConsumer<? super T, ? super U> action) {
        this.wrapped.forEachOrdered(tuEntry -> action.accept(tuEntry.getKey(), tuEntry.getValue()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<T, U>[] toArray() {
        Object[] objects = this.wrapped.toArray();
        Pair<T, U>[] nodes = new Pair[objects.length];

        for (int i = 0; i < objects.length; i++) {
            Map.Entry<T, U> entry = (Map.Entry<T, U>) objects[i];

            nodes[i] = this.pairFromEntry((Map.Entry<T, U>) entry);
        }

        return nodes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A, V> Pair<A, V>[] toArray(IntFunction<Pair<A, V>[]> generator) {
        Map.Entry[] entries = this.wrapped.toArray(Map.Entry[]::new);
        Pair<A, V>[] nodes = generator.apply(entries.length);

        for (int i = 0; i < entries.length; i++) {
            Map.Entry entry = entries[i];
            nodes[i] = this.pairFromEntry((Map.Entry<A, V>) entry);
        }

        return nodes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A, V> Pair<A, V>[] toArray(PairArrayIntFunction<A, V> generator) {
        Map.Entry[] entries = this.wrapped.toArray(Map.Entry[]::new);
        Pair<A, V>[] nodes = generator.apply(entries.length);

        for (int i = 0; i < entries.length; i++) {
            Map.Entry entry = entries[i];
            nodes[i] = this.pairFromEntry((Map.Entry<A, V>) entry);
        }

        return nodes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<T, U> reduceTwo(T identity, U identity2, PairBiBinaryOperator<T, U> accumulator) {

        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduceTwo(identity, identity2, accumulator);
    }

    @Override
    public Pair<List<T>, U> reduceMixed(List<T> init, U identity2, StackBiBinaryOperator<List<T>, T, U> accumulator) {
        return BiStreams.mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduceMixed(init, identity2, accumulator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T reduceFirst(T identity, U identity2, BiBinaryOperator<T, U> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduceFirst(identity, identity2, accumulator);
    }

    @Override
    public U reduceSecond(T identity, U identity2, BiBinaryOperator<U, T> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduceSecond(identity, identity2, accumulator);
    }

    @Override
    public Optional<Pair<T, U>> reduceTwo(PairBiBinaryOperator<T, U> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduceTwo(accumulator);
    }

    @Override
    public Optional<T> reduceFirst(BiBinaryOperator<T, U> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduceFirst(accumulator);
    }

    @Override
    public Optional<U> reduceSecond(BiBinaryOperator<U, T> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduceSecond(accumulator);
    }

    @Override
    public <R> R reduce(R identity, TriFunction<R, ? super T, ? super U, R> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .reduce(identity, accumulator);
    }

    @Override
    public <R, A> R collectKey(Collector<? super T, A, R> collector) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .collectKey(collector);
    }

    @Override
    public <R, A> R collectValue(Collector<? super U, A, R> collector) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .collectValue(collector);
    }

    @Override
    public <R> R collectKey(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .collectKey(supplier, accumulator);
    }

    @Override
    public <R> R collectValue(Supplier<R> supplier, BiConsumer<R, ? super U> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .collectValue(supplier, accumulator);
    }

    @Override
    public <R> R collectOne(Supplier<R> supplier, TriConsumer<R, ? super T, ? super U> accumulator) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .collectOne(supplier, accumulator);
    }

    @Override
    public <R, A> R collect(BiCollector<? super T, ? super U, A, R> collector) {
        return BiStreams
                .mapJavaToBiStream(this.wrapped, this::pairFromEntry)
                .collect(collector);
    }

    @Override
    public Optional<Pair<T, U>> min(BiComparator<? super T, ? super U> comparator) {
        return this.wrapped.min((o1, o2) -> comparator
                .compare(o1.getKey(), o1.getValue(), o2.getKey(), o2.getValue()))
                .map(this::pairFromEntry);
    }

    @Override
    public Optional<Pair<T, U>> max(BiComparator<? super T, ? super U> comparator) {
        return this.wrapped.max((o1, o2) -> comparator.compare(o1.getKey(), o1.getValue(), o2.getKey(), o2.getValue()))
                .map(this::pairFromEntry);
    }

    @Override
    public long count() {
        return this.wrapped.count();
    }

    @Override
    public boolean anyMatch(BiPredicate<? super T, ? super U> predicate) {
        return this.wrapped.anyMatch(tuEntry -> predicate.test(tuEntry.getKey(), tuEntry.getValue()));
    }

    @Override
    public boolean allMatch(BiPredicate<? super T, ? super U> predicate) {
        return this.wrapped.allMatch(tuEntry -> predicate.test(tuEntry.getKey(), tuEntry.getValue()));
    }

    @Override
    public boolean noneMatch(BiPredicate<? super T, ? super U> predicate) {
        return this.wrapped.noneMatch(tuEntry -> predicate.test(tuEntry.getKey(), tuEntry.getValue()));
    }

    @Override
    public Optional<Pair<T, U>> findFirst() {
        return this.wrapped.findFirst().map(this::pairFromEntry);
    }

    @Override
    public Optional<Pair<T, U>> findAny() {
        return this.wrapped.findAny().map(this::pairFromEntry);
    }

    @Override
    public Iterator<Pair<T, U>> iterator() {
        return new BackingIterator<>(this.wrapped.iterator(), this::pairFromEntry);
    }

    @Override
    public Spliterator<Pair<T, U>> spliterator() {
        return new BackingSpliterator<>(this.wrapped.spliterator(),
                this::pairFromEntry,
                this::entryFromPair
        );
    }

    @Override
    public boolean isParallel() {
        return this.wrapped.isParallel();
    }

    @NotNull
    @Override
    public BiStream<T, U> sequential() {
        return stream(this.wrapped.sequential());
    }

    @NotNull
    @Override
    public BiStream<T, U> parallel() {
        return stream(this.wrapped.parallel());
    }

    @NotNull
    @Override
    public BiStream<T, U> unordered() {
        return stream(this.wrapped.unordered());
    }

    @NotNull
    @Override
    public BiStream<T, U> onClose(Runnable closeHandler) {
        return stream(this.wrapped.onClose(closeHandler));
    }

    private <X, Y> Pair<X, Y> pairFromEntry(Map.Entry<X, Y> entry) {
        return Pairs.of(entry.getKey(), entry.getValue());
    }

    private <X, Y> Map.Entry<X, Y> entryFromPair(Pair<X, Y> pair) {
        return new AbstractMap.SimpleImmutableEntry<>(pair.getFirst(), pair.getSecond());
    }

    @Override
    public void close() {
        this.wrapped.close();
    }

    final class BackingIterator<X, E> implements Iterator<X> {

        private final Iterator<E> original;
        private final Function<E, X> mapper;

        BackingIterator(Iterator<E> original, Function<E, X> mapper) {
            this.original = original;
            this.mapper = mapper;
        }

        @Override
        public boolean hasNext() {
            return this.original.hasNext();
        }

        @Override
        public X next() {
            return this.mapper.apply(this.original.next());
        }

        @Override
        public void remove() {
            this.original.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super X> action) {
            this.original.forEachRemaining(mapper::apply);
        }
    }

    final class BackingSpliterator<X, E> implements Spliterator<X> {

        private final Spliterator<E> original;
        private final Function<E, X> mapper;
        private final Function<X, E> inverseMapper;

        BackingSpliterator(Spliterator<E> original, Function<E, X> mapper, Function<X, E> inverseMapper) {
            this.original = original;
            this.mapper = mapper;
            this.inverseMapper = inverseMapper;
        }

        @Override
        public void forEachRemaining(Consumer<? super X> action) {
            this.original.forEachRemaining(mapper::apply);
        }

        @Override
        public long getExactSizeIfKnown() {
            return this.original.getExactSizeIfKnown();
        }

        @Override
        public boolean hasCharacteristics(int characteristics) {
            return this.original.hasCharacteristics(characteristics);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Comparator<? super X> getComparator() {
            Comparator<E> comparator = (Comparator<E>) this.original.getComparator();

            return new BackingComparator<>(comparator, inverseMapper);
        }

        @Override
        public boolean tryAdvance(Consumer<? super X> action) {
            return this.original.tryAdvance(e -> action.accept(this.mapper.apply(e)));
        }

        @Override
        public Spliterator<X> trySplit() {
            return new BackingSpliterator<>(this.original.trySplit(), this.mapper, inverseMapper);
        }

        @Override
        public long estimateSize() {
            return this.original.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.original.characteristics();
        }
    }

    final class BackingComparator<X, E> implements Comparator<X> {

        private final Comparator<E> original;
        private final Function<X, E> mapper;

        BackingComparator(Comparator<E> original, Function<X, E> mapper) {
            this.original = original;
            this.mapper = mapper;
        }

        @Override
        public int compare(X o1, X o2) {
            return this.original.compare(this.mapper.apply(o1), this.mapper.apply(o2));
        }
    }
}
