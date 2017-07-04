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
package com.github.jonathanxd.iutils.collectionsw;

import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Collection;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A Collection of elements of type {@link E}, collections does not take care about order of the
 * elements.
 *
 * All operations which remains on equality depends on implementation, the general contract is that
 * {@link Object#equals(Object)} and {@link Object#hashCode()} should be used to compare elements.
 *
 * All operations which returns a {@code new} {@link CollectionW} should return a collection of the
 * same type, for mutable collections, the same collection should be returned.
 */
public interface CollectionW<E> extends IterableW<E> {

    /**
     * Returns a java collection version of this collection.
     *
     * Modifications are not allowed for immutable collections.
     *
     * @return Java collection version of this collection.
     */
    Collection<E> asJavaCollection();

    @Override
    IteratorW<E> iterator();

    /**
     * Returns the first element of collection.
     *
     * If the {@link #size()} of collection is {@code 1} then the returned element is the same as
     * {@link #last()} element.
     *
     * @return First element of collection.
     * @throws java.util.NoSuchElementException If collection is empty.
     */
    E first();

    /**
     * Returns the head of collection or empty collection if there is no head.
     *
     * @return Head of collection or empty collection if there is no head.
     */
    CollectionW<E> head();

    /**
     * Returns the last element of collection.
     *
     * If the {@link #size()} of collection is {@code 1} then the returned element is the same as
     * {@link #first()} element.
     *
     * @return The last element of collection.
     * @throws java.util.NoSuchElementException If collection is empty.
     */
    E last();

    /**
     * Returns the tail of collection or empty collection if there is no tail.
     *
     * @return Tail of collection or empty collection if there is no tail.
     */
    CollectionW<E> tail();

    /**
     * Size of this collection.
     *
     * @return Size of this collection.
     */
    int size();

    /**
     * Returns true if this collection is empty.
     *
     * @return True if this collection is empty.
     */
    boolean isEmpty();

    // Query

    /**
     * Returns true if this collection contains any object that is equal to {@code o}. The
     * comparison depends on implementation, but commonly it will use either {@link
     * Object#equals(Object)} and {@link Object#hashCode()} to compare objects.
     *
     * @param o Object to compare.
     * @return True if this collection contains any object equals to {@code o}.
     */
    boolean contains(E o);

    /**
     * Returns true if this collection contains any element that matches the {@code predicate}, the
     * implementation should stop looping through collection at the first matching element and
     * return {@code true}.
     *
     * @param predicate Predicate to use to check elements.
     * @return True if this collection contains any element that matches the {@code predicate}.
     */
    default boolean contains(Predicate<E> predicate) {
        IteratorW<E> iterator = this.iterator();

        while (!iterator.hasNext()) {
            E next = iterator.next();
            if (predicate.test(next))
                return true;
        }

        return false;
    }

    /**
     * Returns a new collection with same elements and {@code e} prepended to the collection.
     *
     * @param e Element to prepend.
     * @return New collection with same elements and {@code e} prepended to the collection.
     */
    CollectionW<E> prepend(E e);

    /**
     * Returns a new collection with same elements and elements of {@code es} prepended to the
     * collection.
     *
     * @param es Elements to prepend.
     * @return New collection with same elements and elements of {@code es} prepended to the
     * collection.
     */
    CollectionW<E> prepend(CollectionW<? extends E> es);

    /**
     * Returns a new collection with same elements and {@code e} appended to the collection.
     *
     * @param e Element to prepend.
     * @return New collection with same elements and {@code e} appended to the collection.
     */
    CollectionW<E> append(E e);

    /**
     * Returns a new collection with same elements and elements of {@code es} appended to the
     * collection.
     *
     * @param es Elements to prepend.
     * @return New collection with same elements and elements of {@code es} appended to the
     * collection.
     */
    CollectionW<E> append(CollectionW<? extends E> es);

    /**
     * Creates a new collection of the same type which contains same elements and {@code e}.
     *
     * @param e Element to append to new collection.
     * @return A collection which contains the same elements and {@code e}.
     */
    CollectionW<E> add(E e);

    /**
     * Returns a new collection without element {@code e}.
     *
     * @param e Element to remove from new collection.
     * @return A collection without element {@code e}.
     */
    CollectionW<E> remove(E e);

    /**
     * Returns true if this collection contains all elements present in {@code c}.
     *
     * @param c Collection.
     * @return True if this collection contains all elements present in {@code c}.
     */
    boolean containsAll(CollectionW<? extends E> c);

    /**
     * Creates a new collection with same elements and elements of {@code c}.
     *
     * @param c Collection with elements.
     * @return New collection with same elements and elements of {@code c}.
     */
    CollectionW<E> addAll(CollectionW<? extends E> c);

    /**
     * Returns a new collection without all elements of {@code c}.
     *
     * @param c Collection of elements to remove.
     * @return New collection without all elements of {@code c}.
     */
    CollectionW<E> removeAll(CollectionW<? extends E> c);

    /**
     * Returns true if this collection equals to another collection of the same type.
     *
     * @param o Other object to test.
     * @return True if this collection equals to another collection of the same type.
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns the hash of this collection.
     *
     * @return Hash of this collection.
     */
    int hashCode();

    /**
     * Creates a splitaretor from this collection.
     *
     * @return Splitaretor from this collection.
     */
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.asJavaCollection(), 0);
    }

    /**
     * Creates a stream from the spliterator of this collection.
     *
     * @return Stream from the spliterator of this collection.
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Creates a parallel stream from the spliterator of this collection.
     *
     * @return Parallel stream from the spliterator of this collection.
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    @Override
    CollectionW<E> copy();

    // Ops

    /**
     * Maps all values of this collection using {@code mapper} and return a new collection of same
     * type with mapped elements.
     *
     * @param mapper Mapper function.
     * @param <R>    Map type.
     * @return Mapped collection.
     */
    <R> CollectionW<R> map(Function<? super E, ? extends R> mapper);

    /**
     * Flat maps all values of this collection using {@code mapper} and return a new collection of
     * the same type with mapped elements.
     *
     * @param mapper Flat mapping function.
     * @param <R>    Map type.
     * @return Flat mapped collection.
     */
    <R> CollectionW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    /**
     * Filters all elements of this collections that matches {@code filter} and returns a new
     * collection with filtered elements.
     *
     * @param filter Predicate to filter elements.
     * @return Collection of filtered elements.
     */
    CollectionW<E> filter(Predicate<? super E> filter);

    /**
     * Filters all elements of this collections that does not matches {@code filter} and returns a
     * new collection with filtered elements.
     *
     * @param filter Predicate to filter elements.
     * @return Collection of elements that does not matches {@code filter}.
     */
    default CollectionW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    /**
     * Returns first element that matches {@code predicate}.
     *
     * @param predicate Predicate to test element.
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of first element that matches {@code
     * predicate} or {@link com.github.jonathanxd.iutils.opt.None} if there is no one element
     * matches {@code predicate}.
     */
    default OptObject<E> first(Predicate<? super E> predicate) {
        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(next))
                return OptObject.optObjectNullable(next);
        }

        return OptObject.none();
    }

    /**
     * Returns last element that matches {@code predicate}.
     *
     * @param predicate Predicate to test element.
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of last element that matches {@code
     * predicate} or {@link com.github.jonathanxd.iutils.opt.None} if there is no one element
     * matches {@code predicate}.
     */
    default OptObject<E> last(Predicate<? super E> predicate) {
        IteratorW<E> iterator = this.iterator();

        OptObject<E> obj = OptObject.none();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(next))
                obj = OptObject.optObjectNullable(next);
        }

        return obj;
    }

    /**
     * Returns the lowest element of this collection according to the {@code comparator}.
     *
     * @param comparator Comparator to compare elements  (may receive nulls, make sure that
     *                   comparator will not fail if receive null values).
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of lowest element of this collection
     * according to the {@code comparator}, or {@link com.github.jonathanxd.iutils.opt.None} if
     * collection is empty.
     */
    default OptObject<E> min(Comparator<? super E> comparator) {
        IteratorW<E> iterator = this.iterator();

        OptObject<E> lowest = OptObject.none();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!lowest.isPresent()) {
                lowest = Opt.some(next);
            } else {
                // if (next < lowest)
                if (comparator.compare(next, lowest.getValue()) < 0) {
                    lowest = Opt.some(next);
                }
            }
        }

        return lowest;
    }

    /**
     * Returns the highest element of this collection according to the {@code comparator}.
     *
     * @param comparator Comparator to compare elements (may receive nulls, make sure that
     *                   comparator will not fail if receive null values).
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of highest element of this collection
     * according to the {@code comparator}, or {@link com.github.jonathanxd.iutils.opt.None} if
     * collection is empty.
     */
    default OptObject<E> max(Comparator<? super E> comparator) {
        return this.min(comparator.reversed());
    }

    /**
     * Returns {@code true} if any element matches {@code predicate}.
     *
     * Always {@code false} for empty collections.
     *
     * @param predicate Predicate to match.
     * @return {@code true} if any element matches {@code predicate}.
     */
    default boolean any(Predicate<? super E> predicate) {
        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(next))
                return true;
        }

        return false;
    }

    /**
     * Returns {@code true} if all elements matches {@code predicate}.
     *
     * Always {@code true} for empty collections.
     *
     * @param predicate Predicate to match.
     * @return {@code true} if all elements matches {@code predicate}.
     */
    default boolean all(Predicate<? super E> predicate) {
        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!predicate.test(next))
                return false;
        }

        return true;
    }

    /**
     * Returns {@code true} if no one element matches {@code predicate}.
     *
     * Always {@code true} for empty collections.
     *
     * @param predicate Predicate to match.
     * @return {@code true} if no one element matches {@code predicate}.
     */
    default boolean none(Predicate<? super E> predicate) {
        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(next))
                return false;
        }

        return true;
    }

    /**
     * For each all elements of this collection and pass to consumer.
     *
     * @param consumer Consumer of elements.
     */
    default void forEach(Consumer<? super E> consumer) {
        this.iterator().forEachRemaining(consumer);
    }

    /**
     * Returns a builder with elements of {@code this} collection.
     *
     * @return Builder.
     */
    Builder<E> builder();

    /**
     * Builder interface which allows collections to be built. All default implementations are
     * implemented using a backing {@link java.util.LinkedList} and elements remains sorted by
     * insertion order until construction.
     *
     * You can also retrieve a stream of all current elements in builder using {@link #elements()}
     *
     * There is no guarantee that a collection of same type will be returned.
     *
     * @param <E> Element type.
     */
    interface Builder<E> {

        /**
         * Returns a stream of all elements inside the builder.
         *
         * @return Stream of all elements inside the builder.
         */
        Stream<E> elements();

        /**
         * Returns the amount of elements in this builder.
         *
         * @return Amount of elements.
         */
        int size();

        /**
         * Adds an element to builder.
         *
         * @param element Element to add.
         * @return {@code this}.
         */
        Builder<E> add(E element);

        /**
         * Removes an element from builder.
         *
         * @param element Element to remove.
         * @return {@code this}.
         */
        Builder<E> remove(E element);

        /**
         * Remove all builder elements.
         *
         * @return {@code this}.
         */
        Builder<E> clear();

        /**
         * Build collection.
         *
         * @return Built collection.
         */
        CollectionW<E> build();

    }
}
