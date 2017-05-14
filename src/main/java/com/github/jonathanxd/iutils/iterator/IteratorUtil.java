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
package com.github.jonathanxd.iutils.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator utility.
 */
public class IteratorUtil {

    /**
     * Adds iterator remaining elements to a list.
     *
     * @param iterator Iterator.
     * @param <E>      Type of elements.
     * @return List of reimaning elements of {@code iterator}.
     */
    public static <E> List<E> toList(Iterator<E> iterator) {
        List<E> eList = new ArrayList<>();

        iterator.forEachRemaining(eList::add);

        return eList;
    }

    /**
     * Creates an {@link Iterator} which has only one element to iterate.
     *
     * @param e   Element.
     * @param <E> Element type.
     * @return {@link Iterator} which has only one element to iterate.
     */
    public static <E> Iterator<E> single(final E e) {
        return new Iterator<E>() {

            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public E next() {
                if (!this.hasNext())
                    throw new NoSuchElementException();

                this.hasNext = false;

                return e;
            }
        };
    }

    /**
     * Creates an {@link Iterator} of an array of type {@link E}.
     *
     * @param args Elements.
     * @param <E>  Element type.
     * @return {@link Iterator} of an array of type {@link E}.
     */
    @SafeVarargs
    public static <E> Iterator<E> ofArray(final E... args) {
        return new Iterator<E>() {
            private int index = -1;

            @Override
            public boolean hasNext() {
                return this.index < args.length;
            }

            @Override
            public E next() {
                if (!this.hasNext())
                    throw new NoSuchElementException();

                return args[++index];
            }
        };
    }

    /**
     * Wraps {@code iterator} into a synthetic {@link Iterable}.
     *
     * @param iterator Iterator to wrap.
     * @param <E>      Type of elements.
     * @return Synthetic {@link Iterable} (lambda).
     */
    public static <E> Iterable<E> wrapIterator(final Iterator<E> iterator) {
        return () -> iterator;
    }
}
