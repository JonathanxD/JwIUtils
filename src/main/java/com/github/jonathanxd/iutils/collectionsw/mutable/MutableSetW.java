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
import com.github.jonathanxd.iutils.collectionsw.SetW;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MutableSetW<E> extends SetW<E>, MutableCollectionW<E> {
    @Override
    MutableSetW<E> head();

    @Override
    MutableSetW<E> tail();

    @Override
    MutableSetW<E> prepend(E e);

    @Override
    MutableSetW<E> prepend(CollectionW<? extends E> es);

    @Override
    MutableSetW<E> append(E e);

    @Override
    MutableSetW<E> append(CollectionW<? extends E> es);

    @Override
    MutableSetW<E> add(E e);

    @Override
    MutableSetW<E> remove(E e);

    @Override
    MutableSetW<E> addAll(CollectionW<? extends E> c);

    @Override
    MutableSetW<E> removeAll(CollectionW<? extends E> c);

    @Override
    <R> MutableSetW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> MutableSetW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    MutableSetW<E> filter(Predicate<? super E> filter);

    @Override
    default MutableSetW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    MutableSetW<E> copy();

    @Override
    Builder<E> builder();

    /**
     * Mutable set builder interface.
     *
     * There is no guarantee that a collection of same type will be returned.
     *
     * @param <E> Element type.
     */
    interface Builder<E> extends SetW.Builder<E>, MutableCollectionW.Builder<E> {

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
        MutableSetW<E> build();

    }

}
