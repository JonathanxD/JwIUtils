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

import com.github.jonathanxd.iutils.function.comparators.IndexedComparator;
import com.github.jonathanxd.iutils.function.consumer.IntObjConsumer;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;
import com.github.jonathanxd.iutils.object.IntNode;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Indexed ordered collection.
 *
 * @param <E> Element type.
 */
public interface ListW<E> extends CollectionW<E> {

    /**
     * Returns Java version of this list.
     *
     * @return Java version of this list.
     */
    List<E> asJavaList();

    @Override
    ListW<E> head();

    @Override
    ListW<E> tail();

    @Override
    ListW<E> prepend(E e);

    @Override
    ListW<E> prepend(CollectionW<? extends E> es);

    @Override
    ListW<E> append(E e);

    @Override
    ListW<E> append(CollectionW<? extends E> es);

    @Override
    ListW<E> add(E e);

    @Override
    ListW<E> remove(E e);

    @Override
    ListW<E> addAll(CollectionW<? extends E> c);

    @Override
    ListW<E> removeAll(CollectionW<? extends E> c);

    @Override
    <R> ListW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> ListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    ListW<E> filter(Predicate<? super E> filter);

    @Override
    default ListW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    /**
     * Creates indexed iterator to traverse this list.
     *
     * @return Indexed iterator to traverse this list.
     */
    BiDiIndexedIteratorW<E> iterator();

    /**
     * Creates indexed iterator to traverse this list.
     *
     * @param index Initial index of pointer (0 to first element).
     * @return Indexed iterator to traverse this list.
     */
    BiDiIndexedIteratorW<E> iterator(int index);

    /**
     * Returns a new list with same elements and elements of {@code c} appended at {@code index}.
     *
     * @param index Indexed to append elements to list.
     * @param c     Elements to append.
     * @return New list with same elements and elements of {@code c} appended at {@code index}.
     */
    ListW<E> addAll(int index, CollectionW<? extends E> c);

    /**
     * Gets element at {@code index}.
     *
     * @param index Index of element.
     * @return Element at {@code index}.
     * @throws IndexOutOfBoundsException If the index is out of the range of list (in other words,
     *                                   that is not between 0 and {@link #size()}).
     */
    E get(int index);

    /**
     * Gets collection of element at {@code index}.
     *
     * @param index Index of element.
     * @return Collection of element at {@code index}.
     * @throws IndexOutOfBoundsException If the index is out of the range of list (in other words,
     *                                   that is not between 0 and {@link #size()}).
     */
    ListW<E> getEntry(int index);

    /**
     * Returns a new list with element replaced at {@code index} by {@code element}.
     *
     * @param index   Index of element.
     * @param element Element to replace.
     * @throws IndexOutOfBoundsException If the index is out of the range of list (in other words,
     *                                   that is not between 0 and {@link #size()}).
     */
    ListW<E> set(int index, E element);

    /**
     * Returns a new list with same elements and an {@code element} appended at {@code index}.
     *
     * If the index is equal to {@link #size()}, then the element is appended to the {@link #last()}
     * of the list.
     *
     * @param index   Index to append element.
     * @param element Element to append.
     * @return New list with same elements and an {@code element} appended at {@code index}.
     * @throws IndexOutOfBoundsException If the index is out of the range of list (in other words,
     *                                   that is not between 0 and {@link #size()} {@code + 1}).
     */
    ListW<E> add(int index, E element);

    /**
     * Returns a new list without element at {@code index}.
     *
     * @param index Index of element to filter.
     * @return New list without element at {@code index}.
     */
    ListW<E> remove(int index);

    /**
     * Returns the index of element {@code o} in this list or {@code -1} if element is not present
     * in list
     *
     * @param o Element.
     * @return Index of element {@code o} in this list or {@code -1} if element is not present in
     * list.
     */
    int indexOf(E o);

    /**
     * Returns the last index of element {@code o} in this list or {@code -1} if element is not
     * present in list
     *
     * @param o Element.
     * @return Last index of element {@code o} in this list or {@code -1} if element is not present
     * in list.
     */
    int lastIndexOf(E o);

    /**
     * Creates a sub list of this list which only elements where is between {@code fromIndex} and
     * {@code toIndex} is visible.
     *
     * @param fromIndex Inclusive starting index.
     * @param toIndex   Exclusive ending index.
     * @return A view list of elements where is between {@code fromIndex} and {@code toIndex}.
     */
    ListW<E> subList(int fromIndex, int toIndex);

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.asJavaList(), Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a new list with all elements replaced by elements provided by {@code operator}.
     *
     * @param operator Operator to replace elements.
     * @return New list with all elements replaced by elements provided by {@code operator}.
     */
    ListW<E> replaceAll(UnaryOperator<E> operator);

    /**
     * Returns a new list sorted by {@code c}.
     *
     * @param c Comparator to sort element.
     * @return New list sorted by {@code c}.
     */
    ListW<E> sorted(Comparator<? super E> c);

    // Ops

    /**
     * Maps all values of this collection using {@code mapper} and return a new collection of same
     * type with mapped elements.
     *
     * @param mapper Mapper function.
     * @param <R>    Map type.
     * @return Mapped collection.
     */
    <R> ListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper);

    /**
     * Flat maps all values of this collection using {@code mapper} and return a new collection of
     * the same type with mapped elements.
     *
     * @param mapper Flat mapping function.
     * @param <R>    Map type.
     * @return Flat mapped collection.
     */
    <R> ListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper);

    /**
     * Filters all elements of this collections that matches {@code filter} and returns a new
     * collection with filtered elements.
     *
     * @param filter Predicate to filter elements.
     * @return Collection of filtered elements.
     */
    ListW<E> filterIndexed(IntObjBiPredicate<? super E> filter);

    /**
     * Filters all elements of this collections that does not matches {@code filter} and returns a
     * new collection with filtered elements.
     *
     * @param filter Predicate to filter elements.
     * @return Collection of elements that does not matches {@code filter}.
     */
    default ListW<E> filterNotIndexed(IntObjBiPredicate<? super E> filter) {
        return this.filterIndexed(filter.negate());
    }

    @Override
    ListW<E> copy();

    /**
     * Returns first element that matches {@code predicate}.
     *
     * @param predicate Predicate to test element.
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of first element that matches {@code
     * predicate} or {@link com.github.jonathanxd.iutils.opt.None} if there is no one element
     * matches {@code predicate}.
     */
    default OptObject<E> firstIndexed(IntObjBiPredicate<? super E> predicate) {
        IndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(iterator.index(), next))
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
    default OptObject<E> lastIndexed(IntObjBiPredicate<? super E> predicate) {
        IndexedIteratorW<E> iterator = this.iterator();

        OptObject<E> obj = OptObject.none();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(iterator.index(), next))
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
    default OptObject<E> minIndexed(IndexedComparator<? super E> comparator) {
        IndexedIteratorW<E> iterator = this.iterator();

        OptObject<IntNode<E>> lowest = OptObject.none();

        while (iterator.hasNext()) {
            E next = iterator.next();
            IntNode<E> nextNode = IntNode.intNode(iterator.index(), next);

            if (!lowest.isPresent()) {
                lowest = Opt.some(nextNode);
            } else {
                IntNode<E> lowestNode = lowest.getValue();
                // if (next < lowest)
                if (comparator.compare(iterator.index(), next, lowestNode.getKey(), lowestNode.getValue()) < 0) {
                    lowest = Opt.some(nextNode);
                }
            }
        }

        return lowest.map(IntNode::getValue);
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
    default OptObject<E> maxIndexed(IndexedComparator<? super E> comparator) {
        return this.minIndexed(comparator.reversed());
    }

    /**
     * Returns {@code true} if any element matches {@code predicate}.
     *
     * Always {@code false} for empty collections.
     *
     * @param predicate Predicate to match.
     * @return {@code true} if any element matches {@code predicate}.
     */
    default boolean anyIndexed(IntObjBiPredicate<? super E> predicate) {
        IndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(iterator.index(), next))
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
    default boolean allIndexed(IntObjBiPredicate<? super E> predicate) {
        IndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!predicate.test(iterator.index(), next))
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
    default boolean noneIndexed(IntObjBiPredicate<? super E> predicate) {
        IndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (predicate.test(iterator.index(), next))
                return false;
        }

        return true;
    }


    /**
     * For each all elements of this list and pass to consumer alongside of its index.
     *
     * @param consumer Consumer of elements.
     */
    default void forEachIndexed(IntObjConsumer<? super E> consumer) {
        BiDiIndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();
            consumer.accept(iterator.index(), next);
        }
    }

    @Override
    Builder<E> builder();

    /**
     * List builder interface.
     *
     * There is no guarantee that a collection of same type will be returned.
     *
     * @param <E> Element type.
     */
    interface Builder<E> extends CollectionW.Builder<E> {

        @Override
        Stream<E> elements();

        @Override
        int size();

        @Override
        Builder<E> add(E element);

        @Override
        Builder<E> remove(E element);

        @Override
        Builder<E> clear();

        @Override
        ListW<E> build();

    }

}
