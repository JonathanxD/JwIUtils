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
package com.github.jonathanxd.iutils.collections;

import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A list with a constant size.
 *
 * @param <E> Type of elements.
 */
public interface SizedListW<E> extends ListW<E> {

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

    @Override
    SizedListW<E> head();

    @Override
    SizedListW<E> tail();

    @Override
    SizedListW<E> prepend(E e);

    @Override
    SizedListW<E> prepend(CollectionW<? extends E> es);

    @Override
    SizedListW<E> append(E e);

    @Override
    SizedListW<E> append(CollectionW<? extends E> es);

    @Override
    SizedListW<E> add(E e);

    @Override
    SizedListW<E> remove(E e);

    @Override
    SizedListW<E> addAll(CollectionW<? extends E> c);

    @Override
    SizedListW<E> removeAll(CollectionW<? extends E> c);

    @Override
    <R> SizedListW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> SizedListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    SizedListW<E> filter(Predicate<? super E> filter);

    @Override
    default SizedListW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    BiDiBoundedIteratorW<E> iterator();

    @Override
    BiDiBoundedIteratorW<E> iterator(int index);

    @Override
    SizedListW<E> addAll(int index, CollectionW<? extends E> c);

    @Override
    SizedListW<E> getEntry(int index);

    @Override
    SizedListW<E> set(int index, E element);

    @Override
    SizedListW<E> add(int index, E element);

    @Override
    SizedListW<E> remove(int index);

    @Override
    SizedListW<E> subList(int fromIndex, int toIndex);

    @Override
    SizedListW<E> replaceAll(UnaryOperator<E> operator);

    @Override
    SizedListW<E> sorted(Comparator<? super E> c);

    @Override
    <R> SizedListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper);

    @Override
    <R> SizedListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    SizedListW<E> filterIndexed(IntObjBiPredicate<? super E> filter);

    @Override
    default SizedListW<E> filterNotIndexed(IntObjBiPredicate<? super E> filter) {
        return this.filterIndexed(filter.negate());
    }

    @Override
    SizedListW<E> copy();
}
