/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.collection.view;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class ViewUtils {

    /**
     * Creates a iterator which maps elements of {@code i} to an {@link Iterator} and provide these
     * elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return Mapping inner iterator.
     */
    public static <E, Y> BackingIterator<E, Y> iterator(Iterable<E> i, Function<E, Iterable<Y>> mapper) {
        return new BackingIterator<>(i, mapper);
    }

    /**
     * Creates a {@link Iterable} which holds a {@link #iterator(Iterable, Function)} instance.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link Iterable} which holds a {@link #iterator(Iterable, Function)} instance.
     */
    public static <E, Y> FakeCachedIterable<E, Y> iterable(Iterable<E> i, Function<E, Iterable<Y>> mapper) {
        return new FakeCachedIterable<>(i, mapper);
    }

    /**
     * Better name? Which?
     */
    public static class FakeCachedIterable<E, Y> implements Iterable<Y> {

        /**
         * Main iterable.
         */
        private final Iterable<E> mainIterable;

        /**
         * Mapper.
         */
        private final Function<E, Iterable<Y>> mapper;

        /**
         * Fake cached iterator, will not be reused.
         */
        private BackingIterator<E, Y> cached = null;

        public FakeCachedIterable(Iterable<E> mainIterable, Function<E, Iterable<Y>> mapper) {
            this.mainIterable = mainIterable;
            this.mapper = mapper;
        }

        @Override
        public Iterator<Y> iterator() {
            return this.cached = new BackingIterator<>(this.mainIterable, this.mapper);
        }

        /**
         * Gets cached backing iterator.
         *
         * @return Cached backing iterator.
         */
        public BackingIterator<E, Y> getBackingIterator() {

            if(this.cached == null)
                this.iterator();

            return this.cached;
        }
    }

    public static class BackingIterator<E, Y> implements Iterator<Y> {

        /**
         * Main iterable.
         */
        private final Iterable<E> mainIterable;

        /**
         * Mapper.
         */
        private final Function<E, Iterable<Y>> mapper;

        /**
         * Main iterator
         */
        private final Iterator<E> main;

        /**
         * Current iterable
         */
        private Iterable<Y> currentIterable = null;

        /**
         * Current iterator
         */
        private Iterator<Y> current = null;

        public BackingIterator(Iterable<E> mainIterable, Function<E, Iterable<Y>> mapper) {
            this.mainIterable = mainIterable;
            this.main = mainIterable.iterator();
            this.mapper = mapper;
        }

        @Override
        public boolean hasNext() {
            // Check if current iterator is null or does not have next element
            if (this.current == null || !this.current.hasNext()) {
                // Check if main iterator does not have more elements
                if (!this.main.hasNext())
                    return false;

                // Sets current iterable to result of mapping the next element of main iterator to a new iterator using 'mapper'
                this.currentIterable = this.mapper.apply(this.main.next());

                // Creates iterator from current iterable.
                this.current = this.currentIterable.iterator();
            }

            // Check if current iterator is not null and has next element.
            return this.current != null && this.current.hasNext();
        }

        @Override
        public Y next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            return this.current.next();
        }

        @Override
        public void remove() {
            this.defineCurrent();

            if (this.current == null)
                throw new NoSuchElementException();

            this.current.remove();
        }

        /**
         * Defines the current iterator if it is not defined yet.
         */
        private void defineCurrent() {
            // Checks if current iterator is not defined and main iterator has elements.
            if (this.currentIterable == null && this.main.hasNext()) {
                // Sets current iterator to result of mapping the next element of main iterator to a new iterator using 'mapper'
                this.currentIterable = this.mapper.apply(this.main.next());

                // Creates iterator from current iterable.
                this.current = this.currentIterable.iterator();
            }
        }

        /**
         * Gets main iterable.
         *
         * @return Main iterable.
         */
        public Iterable<E> getMainIterable() {
            return this.mainIterable;
        }

        /**
         * Gets current iterator.
         *
         * @return Current iterator.
         */
        public Iterator<Y> getCurrentIterator() {
            this.defineCurrent();
            return this.current;
        }

        /**
         * Gets current iterable.
         *
         * @return Current iterable.
         */
        public Iterable<Y> getCurrentIterable() {
            this.defineCurrent();
            return this.currentIterable;
        }
    }
}
