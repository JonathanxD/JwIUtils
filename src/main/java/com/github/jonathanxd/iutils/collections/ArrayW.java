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

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Java array version which implements a {@link ListW}.
 *
 * Implementations should wrap original array and delegate operations to them.
 *
 * @param <E> Element.
 */
public interface ArrayW<E> extends ListW<E> {

    /**
     * Returns Java array version of this array.
     *
     * Obs: Returned array is a copy of original array.
     *
     * @return Java array version of this array.
     */
    E[] asJavaArray();

    @Override
    BiDiBoundedIteratorW<E> iterator();

    @Override
    BiDiBoundedIteratorW<E> iterator(int index);

    @Override
    ArrayW<E> head();

    @Override
    ArrayW<E> tail();

    @Override
    ArrayW<E> prepend(E e);

    @Override
    ArrayW<E> prepend(CollectionW<? extends E> es);

    @Override
    ArrayW<E> append(E e);

    @Override
    ArrayW<E> append(CollectionW<? extends E> es);

    @Override
    ArrayW<E> add(E e);

    @Override
    ArrayW<E> remove(E e);

    @Override
    ArrayW<E> addAll(CollectionW<? extends E> c);

    @Override
    ArrayW<E> removeAll(CollectionW<? extends E> c);

    @Override
    <R> ArrayW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> ArrayW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    ArrayW<E> filter(Predicate<? super E> filter);

    @Override
    default ArrayW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    ArrayW<E> addAll(int index, CollectionW<? extends E> c);

    @Override
    ArrayW<E> getEntry(int index);

    @Override
    ArrayW<E> set(int index, E element);

    @Override
    ArrayW<E> add(int index, E element);

    @Override
    ArrayW<E> remove(int index);

    @Override
    ArrayW<E> subList(int fromIndex, int toIndex);

    @Override
    ArrayW<E> replaceAll(UnaryOperator<E> operator);

    @Override
    ArrayW<E> sorted(Comparator<? super E> c);

    @Override
    <R> ArrayW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper);

    @Override
    <R> ArrayW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    ArrayW<E> filterIndexed(IntObjBiPredicate<? super E> filter);

    @Override
    default ArrayW<E> filterNotIndexed(IntObjBiPredicate<? super E> filter) {
        return this.filterIndexed(filter.negate());
    }

    @Override
    ArrayW<E> copy();
}
