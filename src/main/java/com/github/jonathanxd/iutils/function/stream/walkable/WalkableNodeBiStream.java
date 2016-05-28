/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.collection.AddSupport;
import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.comparator.Compared;
import com.github.jonathanxd.iutils.containers.IMutableContainer;
import com.github.jonathanxd.iutils.containers.MutableContainer;
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
import com.github.jonathanxd.iutils.function.stream.BiStream;
import com.github.jonathanxd.iutils.object.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by jonathan on 05/03/16.
 */
public class WalkableNodeBiStream<T, U> extends WalkableBiStream<T, U, Walkable<Node<T, U>>> {

    //int index = 0;

    public WalkableNodeBiStream(Walkable<Node<T, U>> entries) {
        super(entries);
    }

    protected WalkableNodeBiStream(List<Node<T, U>> entries) {
        super(Walkable.asWithoutStateList(entries));
    }


    protected WalkableNodeBiStream(Walkable<Node<T, U>> walkable, Runnable closeRunnable) {
        super(walkable, closeRunnable);
    }

    @Override
    public BiStream<T, U> filter(BiPredicate<? super T, ? super U> predicate) {

        WalkableNodeBiStream<T, U> biStream = newBi();

        Walkable<Node<T, U>> iterator = biStream.unsafeIter();

        while (iterator.hasNext()) {
            Node<T, U> entry = iterator.next();

            if (!predicate.test(entry.getKey(), entry.getValue()))
                iterator.remove();
        }

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
    public <RK, RV> BiStream<RK, RV> map(NodeFunction<? super T, ? super U, ? extends RK, ? extends RV> mapper) {

        Map<RK, RV> newMap = new HashMap<>();

        loop(entry -> {
            Node<? extends RK, ? extends RV> node = mapper.apply(entry.getKey(), entry.getValue());
            newMap.put(node.getKey(), node.getValue());
        });

        return new WalkableNodeBiStream<>(Walkable.asList(newMap));
    }

    @Override
    public <R> Stream<R> streamMap(BiFunction<? super T, ? super U, ? extends R> mapper) {
        List<R> rList = new ArrayList<>();

        loop(entry -> {
            R ret = mapper.apply(entry.getKey(), entry.getValue());
            rList.add(ret);
        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamKeyMap(Function<? super T, ? extends R> mapper) {
        List<R> rList = new ArrayList<>();

        loop(entry -> {
            R ret = mapper.apply(entry.getKey());
            rList.add(ret);
        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamValueMap(Function<? super U, ? extends R> mapper) {
        List<R> rList = new ArrayList<>();

        loop(entry -> {
            R ret = mapper.apply(entry.getValue());
            rList.add(ret);
        });

        return rList.stream();
    }

    @Override
    public IntStream mapToInt(BiToIntFunction<? super T, ? super U> mapper) {

        IntStream.Builder builder = IntStream.builder();

        loop(entry -> builder.add(mapper.applyAsInt(entry.getKey(), entry.getValue())));

        return builder.build();
    }

    @Override
    public LongStream mapToLong(BiToLongFunction<? super T, ? super U> mapper) {

        LongStream.Builder builder = LongStream.builder();

        loop(entry -> builder.add(mapper.applyAsLong(entry.getKey(), entry.getValue())));

        return builder.build();
    }

    @Override
    public DoubleStream mapToDouble(BiToDoubleFunction<? super T, ? super U> mapper) {
        DoubleStream.Builder builder = DoubleStream.builder();

        loop(entry -> builder.add(mapper.applyAsDouble(entry.getKey(), entry.getValue())));

        return builder.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R, V> BiStream<R, V> flatMap(BiFunction<? super T, ? super U, ? extends BiStream<? extends R, ? extends V>> mapper) {

        List<Node> nodes = new ArrayList<>();

        loop(e -> {
            BiStream<? extends R, ? extends V> bi = mapper.apply(e.getKey(), e.getValue());
            bi.iterator().forEachRemaining(nodes::add);
        });

        return new WalkableNodeBiStream<>((List<Node<R, V>>) Walkable.asList(nodes));

        /*WalkableNodeBiStream<R, V> mapBiStream = new WalkableNodeBiStream<>(Walkable.asList(new HashMap<>()));

        Bridge<R, V> bridge = new Bridge<>(mapBiStream);

        loop(e -> {

            BiStream<? extends R, ? extends V> bi = mapper.apply(e.getKey(), e.getValue());
            bridge.up(bi);

        });

        return mapBiStream;*/

    }

    @Override
    public <R> Stream<R> streamFlatMap(BiFunction<? super T, ? super U, ? extends Stream<? extends R>> mapper) {

        List<R> rList = new ArrayList<>();

        loop(e -> {

            Stream<? extends R> streamA = mapper.apply(e.getKey(), e.getValue());
            streamA.forEach(rList::add);

        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamKeyFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        List<R> rList = new ArrayList<>();

        loop(e -> {

            Stream<? extends R> streamA = mapper.apply(e.getKey());
            streamA.forEach(rList::add);

        });

        return rList.stream();
    }

    @Override
    public <R> Stream<R> streamValueFlatMap(Function<? super U, ? extends Stream<? extends R>> mapper) {
        List<R> rList = new ArrayList<>();

        loop(e -> {

            Stream<? extends R> streamA = mapper.apply(e.getValue());
            streamA.forEach(rList::add);

        });

        return rList.stream();
    }

    @Override
    public IntStream flatMapToInt(BiFunction<? super T, ? super U, ? extends IntStream> mapper) {
        IntStream.Builder builder = IntStream.builder();

        loop(e -> {

            IntStream is = mapper.apply(e.getKey(), e.getValue());
            is.forEach(builder::add);

        });

        return builder.build();
    }

    @Override
    public LongStream flatMapToLong(BiFunction<? super T, ? super U, ? extends LongStream> mapper) {
        LongStream.Builder builder = LongStream.builder();

        loop(e -> {

            LongStream ls = mapper.apply(e.getKey(), e.getValue());
            ls.forEach(builder::add);

        });

        return builder.build();
    }

    @Override
    public DoubleStream flatMapToDouble(BiFunction<? super T, ? super U, ? extends DoubleStream> mapper) {
        DoubleStream.Builder builder = DoubleStream.builder();

        loop(e -> {

            DoubleStream ds = mapper.apply(e.getKey(), e.getValue());
            ds.forEach(builder::add);

        });

        return builder.build();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public BiStream<T, U> distinctTwo() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        List<Integer> hashCodes = new ArrayList<>();

        Walkable<Node<T, U>> iterator = biStream.unsafeIter();

        while (iterator.hasNext()) {
            Node<T, U> entry = iterator.next();

            int keyHash = entry.getKey().hashCode();
            int valueHash = entry.getValue().hashCode();

            if (hashCodes.contains(keyHash) || hashCodes.contains(valueHash))
                iterator.remove();
            else {
                hashCodes.add(keyHash);
                hashCodes.add(valueHash);
            }
        }

        return biStream;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public BiStream<T, U> distinctFirst() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        List<Integer> hashCodes = new ArrayList<>();

        Walkable<Node<T, U>> iterator = biStream.unsafeIter();

        while (iterator.hasNext()) {
            Node<T, U> entry = iterator.next();

            int keyHash = entry.getKey().hashCode();

            if (hashCodes.contains(keyHash))
                iterator.remove();
            else {
                hashCodes.add(keyHash);
            }
        }

        return biStream;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public BiStream<T, U> distinctSecond() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        List<Integer> hashCodes = new ArrayList<>();

        Walkable<Node<T, U>> iterator = biStream.unsafeIter();

        while (iterator.hasNext()) {
            Node<T, U> entry = iterator.next();

            int valueHash = entry.getValue().hashCode();

            if (hashCodes.contains(valueHash))
                iterator.remove();
            else {
                hashCodes.add(valueHash);
            }
        }

        return biStream;
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    @Override
    public BiStream<T, U> sortedTwo() {
        WalkableNodeBiStream<T, U> biStream = newBi();
        biStream.getWalkable().sort((e, e2) -> {

            Comparable<T> comparableE1T = (Comparable<T>) e.getKey();
            Comparable<U> comparableE1U = (Comparable<U>) e.getValue();

            T e2T = e2.getKey();
            U e2U = e2.getValue();

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

            Comparable<T> comparableE1T = (Comparable<T>) e.getKey();

            T e2T = e2.getKey();

            return comparableE1T.compareTo(e2T);
        });

        return biStream;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BiStream<T, U> sortedSecond() {
        WalkableNodeBiStream<T, U> biStream = newBi();

        biStream.getWalkable().sort((e, e2) -> {

            Comparable<U> comparableE1U = (Comparable<U>) e.getValue();

            U e2U = e2.getValue();

            return comparableE1U.compareTo(e2U);
        });

        return biStream;
    }

    @Override
    public BiStream<T, U> sorted(BiComparator<? super T, ? super U> comparator) {
        WalkableNodeBiStream<T, U> biStream = newBi();
        biStream.getWalkable().sort((e, e2) -> comparator.compare(e.getKey(), e.getValue(), e2.getKey(), e2.getValue()));
        return biStream;
    }

    @Override
    public BiStream<T, U> peek(BiConsumer<? super T, ? super U> action) {
        WalkableNodeBiStream<T, U> biStream = newBi();
        biStream.consume(e -> action.accept(e.getKey(), e.getValue()));
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
        if(!getWalkable().hasNext())
            return;

        consume(e -> e.consume(action));
    }

    @Override
    public void forEachOrdered(BiConsumer<? super T, ? super U> action) {
        if(!getWalkable().hasNext())
            return;

        consume(e -> e.consume(action));
    }

    @Override
    public Node<T, U>[] toArray() {
        return getWalkable().toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A, V> Node<A, V>[] toArray(IntFunction<Node<A, V>[]> generator) {
        return toArray(new BackPortIntFunc<>(generator));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A, V> Node<A, V>[] toArray(NodeArrayIntFunction<A, V> generator) {
        Node<T, U>[] nodeArray = toArray();

        Node<A, V>[] avNode = generator.apply(nodeArray.length);

        for (int x = 0; x < nodeArray.length; ++x) {
            Node<T, U> node = nodeArray[x];
            avNode[x] = new Node<>((A) node.getKey(), (V) node.getValue());
        }

        return avNode;
    }

    @Override
    public Node<T, U> reduceTwo(T identity, U identify2, NodeBiBinaryOperator<T, U> accumulator) {

        IMutableContainer<Node<T, U>> nodeContainer = new MutableContainer<>(new Node<>(identity, identify2));

        consume(n -> nodeContainer.set(
                accumulator.apply(nodeContainer.get().getKey(), nodeContainer.get().getValue(),
                        n.getKey(), n.getValue())
                )
        );

        return nodeContainer.get();
    }

    @Override
    public Node<List<T>, U> reduceMixed(List<T> init, U identify, StackBiBinaryOperator<List<T>, T, U> accumulator) {

        IMutableContainer<Node<List<T>, U>> nodeContainer = new MutableContainer<>(new Node<>(init, identify));

        consume(n -> nodeContainer.set(
                current -> current.withNewValue(
                        accumulator.apply(nodeContainer.get().getKey(), nodeContainer.get().getValue(), n.getKey(), n.getValue())
                )
                )
        );

        return nodeContainer.get();
    }

    @Override
    public T reduceFirst(T identify, U identify2, BiBinaryOperator<T, U> accumulator) {

        IMutableContainer<Node<T, U>> nodeContainer = new MutableContainer<>(new Node<>(identify, identify2));

        consume(n -> nodeContainer.set(
                (current) -> current.withNewKey(
                        accumulator.apply(current.getKey(), current.getValue(), n.getKey(), n.getValue())
                )
                )
        );
        return nodeContainer.get().getKey();
    }

    @Override
    public U reduceSecond(T identify, U identify2, BiBinaryOperator<U, T> accumulator) {
        IMutableContainer<Node<T, U>> nodeContainer = new MutableContainer<>(new Node<>(identify, identify2));

        consume(n -> nodeContainer.set(
                (current) -> current.withNewValue(
                        accumulator.apply(current.getValue(), current.getKey(), n.getValue(), n.getKey())
                )
                )
        );

        return nodeContainer.get().getValue();
    }

    @Override
    public Optional<Node<T, U>> reduceTwo(NodeBiBinaryOperator<T, U> accumulator) {
        IMutableContainer<Node<T, U>> nodeContainer = new MutableContainer<>(null);

        consume(n -> {
            if (!nodeContainer.isPresent())
                nodeContainer.set(n);
            else
                nodeContainer.set(current -> accumulator.apply(current.getKey(), current.getValue(), n.getKey(), n.getValue()));
        });

        return !nodeContainer.isPresent() ? Optional.empty() : Optional.of(nodeContainer.get());
    }

    @Override
    public Optional<T> reduceFirst(BiBinaryOperator<T, U> accumulator) {
        IMutableContainer<Node<T, U>> nodeContainer = new MutableContainer<>(null);

        consume(n -> {
                    if (!nodeContainer.isPresent())
                        nodeContainer.set(n);
                    else
                        nodeContainer.set(
                                (current) -> current.withNewKey(
                                        accumulator.apply(current.getKey(), current.getValue(), n.getKey(), n.getValue())
                                )
                        );
                }
        );

        return !nodeContainer.isPresent() ? Optional.empty() : Optional.of(nodeContainer.get().getKey());
    }

    @Override
    public Optional<U> reduceSecond(BiBinaryOperator<U, T> accumulator) {
        IMutableContainer<Node<T, U>> nodeContainer = new MutableContainer<>(null);

        consume(n -> {
                    if (!nodeContainer.isPresent())
                        nodeContainer.set(n);
                    else
                        nodeContainer.set((current) -> current.withNewValue(
                                accumulator.apply(current.getValue(), current.getKey(), n.getValue(), n.getKey())
                        ));
                }
        );

        return !nodeContainer.isPresent() ? Optional.empty() : Optional.of(nodeContainer.get().getValue());
    }

    @Override
    public <R> R reduce(R identity, TriFunction<R, ? super T, ? super U, R> accumulator, BinaryOperator<R> combiner) {

        IMutableContainer<R> ret = new MutableContainer<>(identity);

        consume(n -> ret.set(current -> combiner.apply(current, accumulator.apply(identity, n.getKey(), n.getValue()))));

        return ret.get();
    }

    @Override
    public <R> R collectFirst(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return collectTwo(supplier, (ret, key, value) -> accumulator.accept(ret, key), combiner);
    }

    @Override
    public <R> R collectSecond(Supplier<R> supplier, BiConsumer<R, ? super U> accumulator, BiConsumer<R, R> combiner) {

        return collectTwo(supplier, (ret, key, value) -> accumulator.accept(ret, value), combiner);
    }

    @Override
    public <R> R collectTwo(Supplier<R> supplier, TriConsumer<R, ? super T, ? super U> accumulator, BiConsumer<R, R> combiner) {
        IMutableContainer<R> ret = new MutableContainer<>();

        consume(n -> {

            R retVal = ret.get();
            if (ret.isPresent())
                retVal = ret.get();
            else
                retVal = null;

            R newV = supplier.get();
            accumulator.accept(newV, n.getKey(), n.getValue());

            if (retVal == null) {
                ret.set(newV);
            } else {
                combiner.accept(retVal, newV);
            }
        });

        return ret.get();
    }

    @Override
    public <R, A> R collect(BiCollector<? super T, ? super U, A, R> collector) {

        IMutableContainer<A> ret = new MutableContainer<>();

        Supplier<A> supplier = collector.supplier();
        TriConsumer<A, ? super T, ? super U> accumulator = collector.accumulator();
        BinaryOperator<A> combiner = collector.combiner();
        Function<A, R> finisher = collector.finisher();

        consume(n -> {

            A retVal = ret.get();
            if (ret.isPresent())
                retVal = ret.get();
            else
                retVal = null;

            A newV = supplier.get();
            accumulator.accept(newV, n.getKey(), n.getValue());

            if (retVal == null) {
                ret.set(newV);
            } else {
                ret.set(combiner.apply(retVal, newV));
            }
        });

        return finisher.apply(ret.get());

    }

    /**
     * terminal
     *
     * @param comparator t
     * @return t
     */
    @Override
    public Optional<Node<T, U>> min(BiComparator<? super T, ? super U> comparator) {
        Compared<Node<T, U>> compared = getWalkable().compare((c, c2) -> comparator.compare(c.getKey(), c.getValue(), c2.getKey(), c2.getValue()));

        return compared.min();
    }

    /**
     * terminal
     *
     * @param comparator t
     * @return t
     */
    @Override
    public Optional<Node<T, U>> max(BiComparator<? super T, ? super U> comparator) {
        Compared<Node<T, U>> compared = getWalkable().compare((c, c2) -> comparator.compare(c.getKey(), c.getValue(), c2.getKey(), c2.getValue()));

        return compared.max();
    }

    @Override
    public long count() {
        return getWalkable().size();
    }

    @Override
    public boolean anyMatch(BiPredicate<? super T, ? super U> predicate) {

        Walkable<Node<T, U>> iter = unsafeIter();

        while (iter.hasNext()) {
            Node<T, U> node = iter.next();

            if (predicate.test(node.getKey(), node.getValue())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean allMatch(BiPredicate<? super T, ? super U> predicate) {

        Walkable<Node<T, U>> iter = unsafeIter();

        while (iter.hasNext()) {
            Node<T, U> node = iter.next();

            if (!predicate.test(node.getKey(), node.getValue())) {
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
    public Optional<Node<T, U>> findFirst() {

        List<Node<T, U>> currentList = getWalkable().currentAsList();

        return currentList == null || currentList.isEmpty() ? Optional.empty() : Optional.of(currentList.get(0));
    }

    @Override
    public Optional<Node<T, U>> findAny() {
        return !getWalkable().hasCurrent() ? Optional.empty() : Optional.of(getWalkable().getCurrent());
    }

    @Override
    public Iterator<Node<T, U>> iterator() {

        return nodeList().iterator();
    }

    @Override
    public Spliterator<Node<T, U>> spliterator() {
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
    private Walkable<Node<T, U>> iter() {
        return getWalkable().newWithoutState();
    }

    private Walkable<Node<T, U>> safeIter() {
        return getWalkable().newWithoutState();
    }

    private Walkable<Node<T, U>> unsafeIter() {

        if (!this.getWalkable().hasNext())
            throw new NoSuchElementException("End of stream!");

        return getWalkable();
    }

    private void loop(Consumer<Node<T, U>> consumer) {
        getWalkable().forEach(consumer::accept);
        updateState();
    }

    private void consume(Consumer<Node<T, U>> consumer) {

        if (!this.getWalkable().hasNext())
            throw new NoSuchElementException("End of stream!");

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
        Walkable<Node<T, U>> iter = safeIter();

        List<Node<T, U>> entries = new ArrayList<>();

        long current = 0;
        while (iter.hasNext() && current < n) {
            entries.add(iter.next());
            ++current;
        }

        WalkableNodeBiStream<T, U> walkableNodeBiStream = new WalkableNodeBiStream<>(entries);

        walkableNodeBiStream.updateState();

        return walkableNodeBiStream;
    }

    private List<Node<T, U>> nodeList() {
        List<Node<T, U>> nodeList = new ArrayList<>();

        loop(nodeList::add);

        return nodeList;
    }


    private static class Bridge<T, U> {

        private final WalkableNodeBiStream<T, U> stream;

        private Bridge(WalkableNodeBiStream<T, U> stream) {
            this.stream = stream;
        }

        private void up(BiStream<? extends T, ? extends U> node) {
            node.iterator().forEachRemaining(this::up);
        }

        @SuppressWarnings("unchecked")
        private void up(Node<? extends T, ? extends U> node) {

            if (stream.getWalkable() instanceof AddSupport) {
                ((AddSupport<Node<? extends T, ? extends U>>) stream.getWalkable()).add(node);
            } else {
                throw new UnsupportedOperationException("Cannot add node '" + node + "' to Stream '" + stream + "', that Stream doesn't support add() operation!");
            }


        }

    }

    private static class BackPortIntFunc<T, E> implements NodeArrayIntFunction<T, E> {

        private final IntFunction<Node<T, E>[]> intFunction;

        private BackPortIntFunc(IntFunction<Node<T, E>[]> intFunction) {
            this.intFunction = intFunction;
        }

        @Override
        public Node<T, E>[] apply(int i) {
            return intFunction.apply(i);
        }
    }

}
