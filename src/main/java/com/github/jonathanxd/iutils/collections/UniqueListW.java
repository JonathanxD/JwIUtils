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
 * A List which all elements are unique and does not support repetitions. This list remains
 * accepting {@code null}, but only one null reference is allowed.
 *
 * @param <E> Type of elements.
 */
public interface UniqueListW<E> extends ListW<E> {

    @Override
    UniqueListW<E> head();

    @Override
    UniqueListW<E> tail();

    @Override
    UniqueListW<E> prepend(E e);

    @Override
    UniqueListW<E> prepend(CollectionW<? extends E> es);

    @Override
    UniqueListW<E> append(E e);

    @Override
    UniqueListW<E> append(CollectionW<? extends E> es);

    @Override
    UniqueListW<E> add(E e);

    @Override
    UniqueListW<E> remove(E e);

    @Override
    UniqueListW<E> addAll(CollectionW<? extends E> c);

    @Override
    UniqueListW<E> removeAll(CollectionW<? extends E> c);

    @Override
    <R> UniqueListW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> UniqueListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    UniqueListW<E> filter(Predicate<? super E> filter);

    @Override
    default UniqueListW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    UniqueListW<E> addAll(int index, CollectionW<? extends E> c);

    @Override
    UniqueListW<E> getEntry(int index);

    @Override
    UniqueListW<E> set(int index, E element);

    @Override
    UniqueListW<E> add(int index, E element);

    @Override
    UniqueListW<E> remove(int index);

    @Override
    UniqueListW<E> subList(int fromIndex, int toIndex);

    @Override
    UniqueListW<E> replaceAll(UnaryOperator<E> operator);

    @Override
    UniqueListW<E> sorted(Comparator<? super E> c);

    @Override
    <R> UniqueListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper);

    @Override
    <R> UniqueListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    UniqueListW<E> filterIndexed(IntObjBiPredicate<? super E> filter);

    @Override
    default UniqueListW<E> filterNotIndexed(IntObjBiPredicate<? super E> filter) {
        return this.filterIndexed(filter.negate());
    }

    @Override
    UniqueListW<E> copy();

}
