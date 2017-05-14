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
package com.github.jonathanxd.iutils.collection.view;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

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
    public static <E, Y> Iterator<Y> iterator(Iterable<E> i, BiFunction<E, Iterator<E>, Iterator<Y>> mapper) {
        return new IndexedIterator<>(i, mapper);
    }

    /**
     * Creates a iterator which maps elements of {@code i} to an {@link Iterator} and provide these
     * elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     *               @param start Start index of list.
     * @param <E>    Element type.
     * @return Mapping inner iterator.
     */
    public static <E, Y> ListIterator<Y> listIterator(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper, int start) {
        return new IndexedListIterator<>(i, mapper, start);
    }

    /**
     * Creates a {@link Iterable} which holds a {@link #iterator(Iterable, BiFunction)} instance.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link Iterable} which holds a {@link #iterator(Iterable, BiFunction)} instance.
     */
    public static <E, Y> Iterable<Y> iterable(Iterable<E> i, BiFunction<E, Iterator<E>, Iterator<Y>> mapper) {
        return () -> iterator(i, mapper);
    }

    /**
     * Creates a {@link Iterable} which holds a {@link #iterator(Iterable, BiFunction)} instance.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link Iterable} which holds a {@link #iterator(Iterable, BiFunction)} instance.
     */
    public static <E, Y> ListIterable<Y> listIterable(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper) {
        return index -> listIterator(i, mapper, index-1);
    }

    public interface ListIterable<Y> extends Iterable<Y> {

        @Override
        default ListIterator<Y> iterator() {
            return this.iterator(-1);
        }

        ListIterator<Y> iterator(int index);
    }

    static abstract class AbstractIndexedIterator<E, Y> implements Iterator<Y> {

        protected abstract void setCurrent(Iterator<Y> iter);
        protected abstract Iterable<E> getIterable();
        protected abstract Iterator<Y> getCurrent();
        protected abstract Iterator<E> getMain();
        protected abstract Iterator<Y> map(E element, Iterator<E> iter);

        @SuppressWarnings("unchecked")
        @Override
        public boolean hasNext() {
            // Checks if current is null
            boolean isNull = this.getCurrent() == null;

            // Check if current iterator is null or does not have next element
            if (isNull || !this.getCurrent().hasNext()) {
                // Check if main iterator does not have more elements
                if (!this.getMain().hasNext())
                    return false;

                // Sets current iterator to result of mapping the next element of main iterator to a new iterator using 'mapper'
                this.setCurrent(map(this.getMain().next(), this.getMain()));

                // Hacky code to fix some problems...
                if(this.getCurrent() == this.getMain() && isNull)

                    while(this.getMain().hasNext())
                        this.getMain().next();


                    this.setCurrent((Iterator<Y>) this.getIterable().iterator());
            }

            // Check if current iterator is not null and has next element.
            return this.getCurrent() != null && this.getCurrent().hasNext();
        }

        @Override
        public Y next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            return this.getCurrent().next();
        }

        @Override
        public void remove() {
            this.defineCurrent();

            if (this.getCurrent() == null)
                throw new NoSuchElementException();

            this.getCurrent().remove();
        }

        /**
         * Defines the current iterator if it is not defined yet.
         */
        private void defineCurrent() {
            // Checks if current iterator is not defined and main iterator has elements.
            if (this.getCurrent() == null && this.getMain().hasNext()) {

                // Sets current iterator to result of mapping the next element of main iterator to a new iterator using 'mapper'
                this.setCurrent(map(this.getMain().next(), this.getMain()));
            }
        }
    }

    static class IndexedIterator<E, Y> extends AbstractIndexedIterator<E, Y> {
        private final Iterable<E> i;
        private final BiFunction<E, Iterator<E>, Iterator<Y>> mapper;

        /**
         * Main iterator
         */
        private final Iterator<E> main;

        /**
         * Current iterator
         */
        private Iterator<Y> current = null;

        public IndexedIterator(Iterable<E> i, BiFunction<E, Iterator<E>, Iterator<Y>> mapper) {
            this.i = i;
            this.mapper = mapper;
            this.main = i.iterator();
        }

        @Override
        public Iterator<E> getMain() {
            return this.main;
        }

        @Override
        protected Iterator<Y> map(E element, Iterator<E> iter) {
            return this.mapper.apply(element, iter);
        }

        @Override
        protected void setCurrent(Iterator<Y> iter) {
            this.current = iter;
        }

        @Override
        protected Iterator<Y> getCurrent() {
            return this.current;
        }

        @Override
        protected Iterable<E> getIterable() {
            return this.i;
        }
    }

    static class IndexedListIterator<E, Y> extends AbstractIndexedIterator<E, Y> implements ListIterator<Y> {

        private final List<E> i;
        private final BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper;

        /**
         * Main iterator
         */
        private final ListIterator<E> main;

        /**
         * Current index.
         */
        private int elemIndex = -1;

        /**
         * Current iterator
         */
        private ListIterator<Y> current = null;

        public IndexedListIterator(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper, int startIndex) {
            this.i = i;
            this.mapper = mapper;
            this.main = i.listIterator();

            if(startIndex != 0) {
                while(this.hasNext() && this.elemIndex != startIndex) {
                    this.next();
                }
            }
        }

        @Override
        public boolean hasPrevious() {

            boolean isNull = this.current == null;

            // Check if current iterator is null or does not have previous element
            if(isNull || !this.current.hasPrevious()) {
                // Check if main iterator does not have previous elements
                if(!this.main.hasPrevious())
                    return false;

                // Gets next element.
                E previous = this.main.previous();

                // Sets current iterator to result of mapping the previous element of main iterator to a new iterator using 'mapper'
                this.current = mapper.apply(previous, this.main);

                // Another try to fix some problems, this is better because list iterators allows previous and next navigation.
                if(this.current == this.main && isNull) {
                    this.main.next();
                }
            }

            // Check if current iterator is not null and has previous element.
            return this.current != null && this.current.hasPrevious();
        }

        @Override
        public Y next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            Y next = this.current.next();

            ++elemIndex;

            return next;
        }

        @Override
        public Y previous() {
            if (!this.hasPrevious())
                throw new NoSuchElementException();

            Y previous = this.current.previous();

            --this.elemIndex;

            return previous;
        }

        @Override
        public int nextIndex() {
            return this.elemIndex + 1;
        }

        @Override
        public void set(Y y) {
            super.defineCurrent();
            this.current.set(y);
        }

        @Override
        public void add(Y y) {
            super.defineCurrent();
            this.current.add(y);
        }

        @Override
        public int previousIndex() {
            return this.elemIndex - 1;
        }

        @Override
        public void remove() {
            super.defineCurrent();

            if (this.current == null)
                throw new NoSuchElementException();

            this.current.remove();
        }

        @Override
        protected void setCurrent(Iterator<Y> iter) {
            this.current = (ListIterator<Y>) iter;
        }

        @Override
        protected Iterator<Y> getCurrent() {
            return this.current;
        }

        @Override
        protected Iterator<E> getMain() {
            return this.main;
        }

        @Override
        protected Iterator<Y> map(E element, Iterator<E> iter) {
            return this.mapper.apply(element, (ListIterator<E>) iter);
        }

        @Override
        protected Iterable<E> getIterable() {
            return this.i;
        }

    }
}
