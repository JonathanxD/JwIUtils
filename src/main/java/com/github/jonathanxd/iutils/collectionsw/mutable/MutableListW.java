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
import com.github.jonathanxd.iutils.collectionsw.ListW;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Mutable list.
 *
 * Map operations always returns a new list. Filter operations modifies current list.
 *
 * @param <E> Element type.
 */
public interface MutableListW<E> extends MutableCollectionW<E>, ListW<E> {

    @Override
    BiDiIndexedMutIteratorW<E> iterator();

    @Override
    BiDiIndexedMutIteratorW<E> iterator(int index);

    @Override
    MutableListW<E> addAll(int index, CollectionW<? extends E> c);

    @Override
    E get(int index);

    @Override
    MutableListW<E> getEntry(int index);

    @Override
    MutableListW<E> set(int index, E element);

    @Override
    MutableListW<E> add(int index, E element);

    @Override
    MutableListW<E> remove(int index);

    @Override
    MutableListW<E> head();

    @Override
    MutableListW<E> tail();

    @Override
    MutableListW<E> prepend(E e);

    @Override
    MutableListW<E> prepend(CollectionW<? extends E> es);

    @Override
    MutableListW<E> append(E e);

    @Override
    MutableListW<E> append(CollectionW<? extends E> es);

    @Override
    MutableListW<E> add(E e);

    @Override
    MutableListW<E> remove(E e);

    @Override
    MutableListW<E> addAll(CollectionW<? extends E> c);

    @Override
    MutableListW<E> removeAll(CollectionW<? extends E> c);

    @Override
    MutableListW<E> copy();

    @Override
    <R> MutableListW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> MutableListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableListW<E> filter(Predicate<? super E> filter);

    @Override
    default MutableListW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    MutableListW<E> subList(int fromIndex, int toIndex);

    @Override
    MutableListW<E> replaceAll(UnaryOperator<E> operator);

    @Override
    MutableListW<E> sorted(Comparator<? super E> c);

    @Override
    <R> MutableListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper);

    @Override
    <R> MutableListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableListW<E> filterIndexed(IntObjBiPredicate<? super E> filter);

    @Override
    default MutableListW<E> filterNotIndexed(IntObjBiPredicate<? super E> filter) {
        return this.filterIndexed(filter.negate());
    }

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.asJavaList(), Spliterator.ORDERED);
    }

    @Override
    Builder<E> builder();

    /**
     * Mutable list builder interface.
     *
     * There is no guarantee that a collection of same type will be returned.
     *
     * @param <E> Element type.
     */
    interface Builder<E> extends ListW.Builder<E>, MutableCollectionW.Builder<E> {

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
        MutableListW<E> build();

    }
}
