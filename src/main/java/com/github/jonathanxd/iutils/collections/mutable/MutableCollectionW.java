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

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Mutable collection.
 *
 * Map operations always returns a new collection. Filter operations modifies current collection.
 *
 * @param <E> Element type.
 */
public interface MutableCollectionW<E> extends CollectionW<E> {
    @Override
    MutableIteratorW<E> iterator();

    @Override
    MutableCollectionW<E> head();

    @Override
    MutableCollectionW<E> tail();

    @Override
    MutableCollectionW<E> prepend(E e);

    @Override
    MutableCollectionW<E> prepend(CollectionW<? extends E> es);

    @Override
    MutableCollectionW<E> append(E e);

    @Override
    MutableCollectionW<E> append(CollectionW<? extends E> es);

    @Override
    MutableCollectionW<E> add(E e);

    @Override
    MutableCollectionW<E> remove(E e);

    @Override
    MutableCollectionW<E> addAll(CollectionW<? extends E> c);

    @Override
    MutableCollectionW<E> removeAll(CollectionW<? extends E> c);

    @Override
    MutableCollectionW<E> copy();

    @Override
    <R> MutableCollectionW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> MutableCollectionW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableCollectionW<E> filter(Predicate<? super E> filter);

    @Override
    default MutableCollectionW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }
}
