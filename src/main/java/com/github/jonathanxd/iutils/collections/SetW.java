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

import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A set is a collection of unique non-null elements.
 * @param <E> Elements type.
 */
public interface SetW<E> extends CollectionW<E> {

    /**
     * Returns a Java version of this set.
     *
     * @return Java version of this set.
     */
    Set<E> asJavaSet();

    @Override
    IteratorW<E> iterator();

    /**
     * As sets are unordered, the head may vary.
     *
     * @return Head of this set.
     */
    @Override
    SetW<E> head();

    /**
     * As sets are unordered, the tail may vary.
     *
     * @return Tail of this set.
     */
    @Override
    SetW<E> tail();

    @Override
    SetW<E> prepend(E e);

    @Override
    SetW<E> prepend(CollectionW<? extends E> es);

    @Override
    SetW<E> append(E e);

    @Override
    SetW<E> append(CollectionW<? extends E> es);

    @Override
    SetW<E> add(E e);

    @Override
    SetW<E> remove(E e);

    @Override
    SetW<E> addAll(CollectionW<? extends E> c);

    @Override
    SetW<E> removeAll(CollectionW<? extends E> c);

    @Override
    <R> SetW<R> map(Function<? super E, ? extends R> mapper);

    @Override
    <R> SetW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper);

    @Override
    SetW<E> filter(Predicate<? super E> filter);

    @Override
    default SetW<E> filterNot(Predicate<? super E> filter) {
        return this.filter(filter.negate());
    }

    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.asJavaSet(), Spliterator.DISTINCT);
    }

    @Override
    SetW<E> copy();
}
