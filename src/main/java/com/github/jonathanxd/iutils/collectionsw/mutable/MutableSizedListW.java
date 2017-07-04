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
package com.github.jonathanxd.iutils.collectionsw.mutable;

import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A list with a constant size.
 *
 * @param <E> Type of elements.
 */
public interface MutableSizedListW<E> extends MutableListW<E> {

    /**
     * Max size of list.
     *
     * @return Max size of list.
     */
    int maxSize();

    /**
     * Returns true if list is full.
     *
     * @return True if list is full.
     */
    default boolean isFull() {
        return this.size() == this.maxSize();
    }

    /**
     * Returns java version of this list.
     *
     * This will not cause this list to lost its limit property, returned list will be always a
     * {@link com.github.jonathanxd.iutils.list.SizedJavaList}.
     *
     * @return Java version of this list.
     */
    @Override
    List<E> asJavaList();

    @Override
    MutableSizedListW<E> head();

    @Override
    MutableSizedListW<E> tail();

    @Override
    MutableSizedListW<E> prepend(E e);

    @Override
    MutableSizedListW<E> prepend(CollectionW<? extends E> es);

    @Override
    MutableSizedListW<E> append(E e);

    @Override
    MutableSizedListW<E> append(CollectionW<? extends E> es);

    @Override
    MutableSizedListW<E> add(E e);

    @Override
    MutableSizedListW<E> remove(E e);

    @Override
    MutableSizedListW<E> addAll(CollectionW<? extends E> c);

    @Override
    MutableSizedListW<E> removeAll(CollectionW<? extends E> c);

    @Override
    <R> MutableSizedListW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> MutableSizedListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableSizedListW<E> filter(Predicate<? super E> filter);

    @Override
    default MutableSizedListW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    BiDiBoundedMutIteratorW<E> iterator();

    @Override
    BiDiBoundedMutIteratorW<E> iterator(int index);

    @Override
    MutableSizedListW<E> addAll(int index, CollectionW<? extends E> c);

    @Override
    MutableSizedListW<E> getEntry(int index);

    @Override
    MutableSizedListW<E> set(int index, E element);

    @Override
    MutableSizedListW<E> add(int index, E element);

    @Override
    MutableSizedListW<E> remove(int index);

    @Override
    MutableSizedListW<E> subList(int fromIndex, int toIndex);

    @Override
    MutableSizedListW<E> replaceAll(UnaryOperator<E> operator);

    @Override
    MutableSizedListW<E> sorted(Comparator<? super E> c);

    @Override
    <R> MutableSizedListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper);

    @Override
    <R> MutableSizedListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableSizedListW<E> filterIndexed(IntObjBiPredicate<? super E> filter);

    @Override
    default MutableSizedListW<E> filterNotIndexed(IntObjBiPredicate<? super E> filter) {
        return this.filterIndexed(filter.negate());
    }

    @Override
    MutableSizedListW<E> copy();

    /**
     * Creates a new {@link MutableSizedListW} with same elements as {@code this} and with a new
     * size limit.
     *
     * If the {@code newSize} limit is less than current {@link #maxSize()}, then elements after
     * {@code newSize - 1} will not be added to new list.
     *
     * @param newSize New size.
     * @return {@link MutableSizedListW} with same elements as {@code this} and with a new size
     * limit.
     */
    MutableSizedListW<E> newWithSize(int newSize);

    @Override
    Builder<E> builder();

    /**
     * Mutable size list builder interface.
     *
     * There is no guarantee that a collection of same type will be returned.
     *
     * @param <E> Element type.
     */
    interface Builder<E> extends MutableListW.Builder<E> {

        @Override
        Stream<E> elements();

        @Override
        int size();

        /**
         * Sets the limit of elements.
         *
         * @param limit Limit of elements.
         * @return {@code this}.
         */
        Builder<E> setLimit(int limit);

        @Override
        Builder<E> add(E element);

        @Override
        Builder<E> remove(E element);

        @Override
        Builder<E> clear();

        @Override
        MutableSizedListW<E> build();

    }

}
