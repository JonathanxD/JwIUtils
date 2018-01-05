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
package com.github.jonathanxd.iutils.collection;

import com.github.jonathanxd.iutils.function.Predicates;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Node;
import com.github.jonathanxd.iutils.sort.SortingResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link Walkable} is a unification of {@link Collection} and {@link java.util.Iterator} to be used
 * by {@link com.github.jonathanxd.iutils.function.stream.BiStream BiStreams}.
 *
 * Walkable is used by {@link com.github.jonathanxd.iutils.function.stream.BiStream} to work with
 * elements.
 *
 * @param <T> Type of elements.
 */
public interface Walkable<T> {

    Walkable<?> EMPTY = new EmptyWalkable();

    /**
     * Create a map walkable.
     *
     * @param map Map.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Walkable of map entries (as {@link Node}).
     */
    static <K, V> Walkable<Node<K, V>> fromList(Map<K, V> map) {

        if (map == null) {
            return Walkable.empty();
        }

        return Walkable.fromCollection(Node.fromEntryCollection(map.entrySet()));
    }

    /**
     * Create a walkable from a list of nodes.
     *
     * @param nodes List of nodes.
     * @param <K>   Key type.
     * @param <V>   Value type.
     * @return Walkable of list nodes.
     */
    static <K, V> Walkable<Node<K, V>> fromList(List<Node<K, V>> nodes) {
        if (nodes == null)
            return empty();

        return fromCollection(nodes);
    }

    /**
     * Creates a {@link Walkable} from another {@link Walkable}.
     *
     * This method will also consume the elements of {@code walkable}.
     *
     * @param walkable Walkable.
     * @param <T>      Value type.
     * @return Walkable from another {@code walkable}.
     */
    static <T> Walkable<T> fromWalkable(Walkable<T> walkable) {

        if (walkable == null)
            return Walkable.empty();

        List<T> list = new ArrayList<>();

        while (walkable.hasNext())
            list.add(walkable.next());

        return Walkable.fromCollection(list);
    }

    /**
     * Creates a {@link Walkable} from a {@link Collection}.
     *
     * @param collection Collection.
     * @param <T>        Value type.
     * @return {@link Walkable} from {@code collection}.
     */
    static <T> Walkable<T> fromCollection(Collection<T> collection) {
        if (collection == null)
            return Walkable.empty();

        return new WalkableList<>(new ArrayList<>(collection));
    }

    /**
     * Creates a {@link Walkable} from elements of a {@link Stream}. This method calls a terminal
     * operation of {@code stream}.
     *
     * @param stream Stream.
     * @param <T>    Value type.
     * @return A {@link Walkable} from elements of {@code stream}.
     */
    static <T> Walkable<T> fromStream(Stream<T> stream) {
        if (stream == null)
            return Walkable.empty();

        List<T> ts = IteratorUtil.toList(stream.iterator());
        return Walkable.fromCollection(ts);
    }

    /**
     * Creates a empty {@link Walkable}.
     *
     * @param <T> Type of values.
     * @return Empty {@link Walkable}.
     */
    @SuppressWarnings("unchecked")
    static <T> Walkable<T> empty() {
        return (Walkable<T>) EMPTY;
    }

    /**
     * Go to next element.
     *
     * @return Next element.
     * @throws java.util.NoSuchElementException if there is not element.
     */
    T next();

    /**
     * Returns true if has next element.
     *
     * @return True if has next element.
     */
    boolean hasNext();

    /**
     * Remove current element.
     */
    void remove();

    /**
     * Sorts current walkable using comparator {@code c}.
     *
     * @param c Comparator.
     */
    void sort(Comparator<? super T> c);

    /**
     * Walk to end.
     */
    void walkToEnd();

    /**
     * Creates a new {@link Walkable} with default state.
     *
     * @return new {@link Walkable} instance with default state.
     */
    Walkable<T> newWithoutState();

    /**
     * Consume all elements.
     *
     * @param consumer Consumer.
     */
    void forEach(Consumer<? super T> consumer);

    /**
     * Consumer all elements until predicate {@code until} returns true.
     *
     * @param consumer Consumer.
     * @param until    Predicate.
     */
    void forEach(Consumer<? super T> consumer, Predicate<? super T> until);

    /**
     * Create a array from this walkable elements.
     *
     * Consume elements.
     *
     * @return Array from this walkable elements.
     */
    T[] toArray();

    /**
     * Gets the amount of elements.
     *
     * @return Amount of elements.
     */
    int size();

    /**
     * Gets the amount of remaining elements.
     *
     * @return Amount of remaining elements.
     */
    int getRemainingElementsAmount();

    /**
     * Returns true if there is a current element available.
     *
     * @return True if there is a current element available.
     */
    boolean hasCurrent();

    /**
     * Gets the current element.
     *
     * @return Current element.
     */
    T getCurrent();

    /**
     * Creates a clone of {@code this} {@link Walkable} with the same state.
     *
     * @return Clone of {@code this} {@link Walkable} with the same state.
     */
    Walkable<T> clone();

    /**
     * Resets the index of current element.
     */
    void resetIndex();

    /**
     * Creates a new {@link Walkable} with distinct elements (compared using {@link
     * Object#hashCode()} and {@link Object#equals(Object)}).
     *
     * @return New {@link Walkable} with distinct elements.
     */
    Walkable<T> distinct();

    /**
     * Distinct elements of current walkable (and resets the walkable).
     *
     * @see #distinct()
     */
    void distinctInternal();

    /**
     * Creates a new {@link Walkable} with distinct elements (compared using {@link
     * Object#hashCode()} and {@link Object#equals(Object)}).
     *
     * @param function Function to map elements before equality comparison.
     * @param <R>      Type to map.
     * @return New {@link Walkable} with distinct elements.
     */
    <R> Walkable<T> distinct(Function<T, R> function);

    /**
     * Distinct elements of current walkable (and resets the walkable).
     *
     * @param function Function to map elements before equality comparison.
     * @param <R>      Type to map.
     * @see #distinct(Function)
     */
    <R> void distinctInternal(Function<T, R> function);

    /**
     * Returns true if this {@link Walkable} contains the element {@code t}.
     *
     * @param t Element.
     * @return True if this {@link Walkable} contains the element {@code t}.
     */
    boolean contains(T t);

    /**
     * Maps current {@link Walkable} using {@code map}.
     *
     * This method will not consume objects.
     *
     * @param map Mapper function.
     * @param <R> Return type.
     * @return Returns mapped {@link Walkable}.
     */
    <R> Walkable<R> map(Function<T, R> map);

    /**
     * Creates a {@link List} of remaining elements of {@code this} walkable.
     *
     * @return {@link List} of remaining elements of {@code this} walkable.
     */
    default List<T> toList() {
        List<T> list = new ArrayList<>();
        this.clone().forEach(list::add);
        return list;
    }

    /**
     * Creates a {@link List} of all elements of {@code this} walkable.
     *
     * @return {@link List} of all elements of {@code this} walkable.
     */
    default List<T> allElementsToList() {
        List<T> list = new ArrayList<>();
        this.newWithoutState().forEach(list::add);
        return list;
    }

    /**
     * Compares all elements of this walkable and returns a list sorted list boxed in {@link
     * SortingResult}.
     *
     * @param comparator Comparator.
     * @return Comparison of elements.
     */
    default SortingResult<T> compareToComparison(Comparator<? super T> comparator) {
        List<T> sorted = this.allElementsToList().stream().sorted(comparator).collect(Collectors.toList());

        return new SortingResult<>(sorted);
    }

    /**
     * Compares all elements of this walkable and returns a sorted list.
     *
     * @param comparator Comparator.
     * @return Sorted list of compared elements.
     */
    default List<T> compareToList(Comparator<? super T> comparator) {
        return this.allElementsToList().stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * A {@link Walkable} backed by a {@link List}.
     *
     * @param <T> Type of element.
     */
    final class WalkableList<T> implements Walkable<T> {

        /**
         * List
         */
        private final List<T> list;

        /**
         * Index of current element.
         */
        int index = -1;

        WalkableList(List<T> list) {
            this.list = list;
        }

        @Override
        public T next() {
            return list.get(++index);
        }

        @Override
        public boolean hasNext() {
            return index + 1 < list.size();
        }

        @Override
        public void remove() {
            this.list.remove(this.index);
            --this.index;
        }

        @Override
        public void sort(Comparator<? super T> c) {
            this.list.sort(c);
        }

        @Override
        public void walkToEnd() {
            while (this.hasNext())
                this.next();
        }

        @Override
        public Walkable<T> newWithoutState() {
            return new WalkableList<>(new ArrayList<>(this.list));
        }

        @Override
        public void forEach(Consumer<? super T> consumer) {
            this.forEach(consumer, Predicates.acceptAll());
        }

        @Override
        public void forEach(Consumer<? super T> consumer, Predicate<? super T> until) {
            Objects.requireNonNull(consumer);
            Objects.requireNonNull(until);

            Walkable<T> walkable = clone();

            while (walkable.hasNext()) {
                T next = walkable.next();
                if (until.test(next))
                    consumer.accept(next);
                else
                    break;
            }
        }

        @Override
        public boolean hasCurrent() {
            return this.index > -1 && this.index < this.list.size();
        }

        @Override
        public T getCurrent() {
            return this.list.get(this.index);
        }

        @SuppressWarnings("unchecked")
        @Override
        public T[] toArray() {
            T[] array;

            if (hasCurrent())
                array = (T[]) Array.newInstance(this.getCurrent().getClass(), this.getRemainingElementsAmount());
            else
                array = (T[]) new Object[0];

            WalkableList<T> walkable = this.clone();

            if (walkable.hasCurrent()) {
                int x = -1;
                do {
                    array[++x] = walkable.getCurrent();
                }
                while (walkable.hasNext() && walkable.next() != null);
            }

            return array;
        }

        @Override
        public int size() {
            return this.list.size();
        }

        @Override
        public int getRemainingElementsAmount() {
            return this.size() - this.index;
        }

        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        public WalkableList<T> clone() {
            WalkableList<T> walkable = new WalkableList<>(this.list);
            walkable.index = this.index;
            return walkable;
        }

        @Override
        public void resetIndex() {
            this.index = -1;
        }

        @Override
        public Walkable<T> distinct() {
            List<T> list = this.distinctToList();

            return new WalkableList<>(list);
        }

        @Override
        public void distinctInternal() {
            List<T> list = distinctToList();

            this.list.clear();
            this.list.addAll(list);

            this.resetIndex();
        }

        @Override
        public <R> Walkable<T> distinct(Function<T, R> function) {
            List<T> list = this.distinctToList(function);

            return new WalkableList<>(list);
        }

        @Override
        public <R> void distinctInternal(Function<T, R> function) {
            List<T> list = this.distinctToList(function);

            this.list.clear();
            this.list.addAll(list);
        }

        private List<T> distinctToList() {
            List<T> list = new ArrayList<>();

            this.clone().forEach(t -> {
                boolean any = false;

                for (T t1 : list) {
                    if (t.hashCode() == t1.hashCode() && t.equals(t1)) {
                        any = true;
                    }
                }

                if (!any)
                    list.add(t);
            });

            return list;
        }

        private <R> List<T> distinctToList(Function<T, R> function) {
            List<T> list = new ArrayList<>();


            this.clone().forEach(t -> {
                boolean any = false;

                R r = function.apply(t);

                for (T t1 : list) {

                    R r1 = function.apply(t1);

                    if (r.hashCode() == r1.hashCode() && r.equals(r1)) {
                        any = true;
                    }
                }

                if (!any)
                    list.add(t);
            });

            return list;
        }

        @Override
        public boolean contains(T t) {
            return this.list.contains(t);
        }

        @Override
        public <R> Walkable<R> map(Function<T, R> map) {

            List<R> rs = new ArrayList<>();

            Walkable<T> clone = this.newWithoutState();

            while (clone.hasNext()) {
                T next = clone.next();
                rs.add(map.apply(next));
            }

            return new WalkableList<>(rs);
        }

    }
}
