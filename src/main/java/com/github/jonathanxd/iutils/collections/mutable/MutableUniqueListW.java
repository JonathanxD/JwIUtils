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
import com.github.jonathanxd.iutils.collections.ListW;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface MutableUniqueListW<E> extends MutableCollectionW<E>, ListW<E> {

    @Override
    BiDiIndexedMutIteratorW<E> iterator();

    @Override
    BiDiIndexedMutIteratorW<E> iterator(int index);

    @Override
    MutableUniqueListW<E> addAll(int index, CollectionW<? extends E> c);

    @Override
    E get(int index);

    @Override
    MutableUniqueListW<E> getEntry(int index);

    @Override
    MutableUniqueListW<E> set(int index, E element);

    @Override
    MutableUniqueListW<E> add(int index, E element);

    @Override
    MutableUniqueListW<E> remove(int index);

    @Override
    MutableUniqueListW<E> head();

    @Override
    MutableUniqueListW<E> tail();

    @Override
    MutableUniqueListW<E> prepend(E e);

    @Override
    MutableUniqueListW<E> prepend(CollectionW<? extends E> es);

    @Override
    MutableUniqueListW<E> append(E e);

    @Override
    MutableUniqueListW<E> append(CollectionW<? extends E> es);

    @Override
    MutableUniqueListW<E> add(E e);

    @Override
    MutableUniqueListW<E> remove(E e);

    @Override
    MutableUniqueListW<E> addAll(CollectionW<? extends E> c);

    @Override
    MutableUniqueListW<E> removeAll(CollectionW<? extends E> c);

    @Override
    MutableUniqueListW<E> copy();

    @Override
    <R> MutableUniqueListW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> MutableUniqueListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableUniqueListW<E> filter(Predicate<? super E> filter);

    @Override
    default MutableUniqueListW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    MutableUniqueListW<E> subList(int fromIndex, int toIndex);

    @Override
    MutableUniqueListW<E> replaceAll(UnaryOperator<E> operator);

    @Override
    MutableUniqueListW<E> sorted(Comparator<? super E> c);

    @Override
    <R> MutableUniqueListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper);

    @Override
    <R> MutableUniqueListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableUniqueListW<E> filterIndexed(IntObjBiPredicate<? super E> filter);

    @Override
    default MutableUniqueListW<E> filterNotIndexed(IntObjBiPredicate<? super E> filter) {
        return this.filterIndexed(filter.negate());
    }
}
