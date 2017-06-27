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
package com.github.jonathanxd.iutils.collections.mutable;

import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.SizedListW;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Mutable {@link SizedListW}.
 *
 * @param <E> Type of element.
 */
public interface MutableSizedListW<E> extends SizedListW<E>, MutableListW<E> {

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

}
