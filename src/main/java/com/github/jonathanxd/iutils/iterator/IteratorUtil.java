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

import com.github.jonathanxd.iutils.function.checked.CRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

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
     * Creates an {@link Iterator} which has only one element to iterate.
     *
     * @param e   Supplier of elements.
     * @param <E> Element type.
     * @return {@link Iterator} which has only one element to iterate.
     */
    public static <E> Iterator<E> singleSupplied(final Supplier<E> e) {
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

                return e.get();
            }
        };
    }

    /**
     * Creates a {@link ListIterator} which has only one element to iterate (does not support add).
     *
     * @param e   Element.
     * @param <E> Element type.
     * @return {@link Iterator} which has only one element to iterate.
     */
    public static <E> ListIterator<E> singleListIterator(final E e) {
        return new ListIterator<E>() {

            private E value = e;

            private boolean removed = false;
            private boolean hasNext = true;

            private void checkRemoved() {
                if (this.removed)
                    throw new NoSuchElementException();
            }

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public boolean hasPrevious() {
                return !this.hasNext;
            }

            @Override
            public E previous() {
                if (!this.hasPrevious())
                    throw new NoSuchElementException();

                this.hasNext = true;

                return value;
            }

            @Override
            public int nextIndex() {
                this.checkRemoved();

                if (!this.hasNext())
                    throw new NoSuchElementException();

                return 0;
            }

            @Override
            public int previousIndex() {
                this.checkRemoved();

                if (!this.hasPrevious())
                    throw new NoSuchElementException();

                return 0;
            }

            @Override
            public void remove() {
                this.checkRemoved();
                this.removed = true;
            }

            @Override
            public void set(E e) {
                this.removed = false;
                this.value = e;
            }

            @Override
            public void add(E e) {
                throw new UnsupportedOperationException("Cannot add values to single iterator.");
            }

            @Override
            public E next() {
                this.checkRemoved();

                if (!this.hasNext())
                    throw new NoSuchElementException();

                this.hasNext = false;

                return this.value;
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
                return this.index + 1 < args.length;
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

    /**
     * Creates a iterator to map values.
     *
     * This iterator should only be used in view collections. The behavior is uncertain outside of a
     * view collection.
     *
     * @param value    Original value.
     * @param original Original iterator.
     * @param mapper   Value mapper.
     * @param <E>      Input type.
     * @param <Y>      Output type.
     * @return Iterator mapping {@code value} and delegating remove operations to original iterator.
     */
    public static <E, Y> Iterator<Y> mapped(E value, Iterator<E> original, Function<E, Y> mapper) {
        return new Iterator<Y>() {

            private boolean readed = false;

            @Override
            public boolean hasNext() {

                return !this.readed;
            }

            @Override
            public Y next() {

                if (!this.hasNext())
                    throw new NoSuchElementException();

                this.readed = true;

                return mapper.apply(value);
            }

            @Override
            public void remove() {
                if (!this.readed)
                    throw new IllegalStateException();

                original.remove();
            }
        };
    }

    /**
     * Creates a iterator to map values.
     *
     * This iterator should only be used in view collections. The behavior is uncertain outside of a
     * view collection.
     *
     * @param value    Original value.
     * @param original Original iterator.
     * @param mapper   Value mapper.
     * @param unmapper Remap value to original type.
     * @param <E>      Input type.
     * @param <Y>      Output type.
     * @return Iterator mapping {@code value} and delegating operations to original iterator.
     */
    public static <E, Y> ListIterator<Y> mapped(E value, ListIterator<E> original, Function<E, Y> mapper, Function<Y, E> unmapper) {
        return new ListIterator<Y>() {

            private final int index = original.nextIndex();
            private boolean readed = false;

            @Override
            public boolean hasNext() {
                return !this.readed;
            }

            @Override
            public Y next() {
                if (!this.hasNext())
                    throw new NoSuchElementException();

                this.readed = true;
                return mapper.apply(value);
            }

            @Override
            public boolean hasPrevious() {
                return this.readed;
            }

            @Override
            public Y previous() {
                if (!this.hasPrevious())
                    throw new NoSuchElementException();

                this.readed = false;

                return mapper.apply(value);
            }

            @Override
            public int nextIndex() {

                if (!this.readed)
                    return this.index;

                return this.index + 1;
            }

            @Override
            public int previousIndex() {

                if (!this.readed)
                    return this.index - 1;

                return this.index;
            }

            @Override
            public void remove() {
                if (!this.readed)
                    throw new IllegalStateException();

                original.remove();
            }

            @Override
            public void set(Y y) {
                if (!this.readed)
                    throw new IllegalStateException();

                original.set(unmapper.apply(y));
            }

            @Override
            public void add(Y y) {
                if (!this.readed)
                    throw new IllegalStateException();
                original.add(unmapper.apply(y));
            }
        };
    }

    /**
     * Creates an iterator which maps values provided by {@code source} using {@code mapper}.
     *
     * @param source Source iterator.
     * @param mapper Mapper of values.
     * @param <E>    Element type.
     * @param <R>    Target type.
     * @return Iterator which maps values of {@code source}.
     */
    public static <E, R> Iterator<R> mappedIterator(Iterator<E> source, Function<E, R> mapper) {
        return new Iterator<R>() {
            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public R next() {
                return mapper.apply(source.next());
            }

            @Override
            public void remove() {
                source.remove();
            }
        };
    }

    /**
     * Creates an list iterator which maps values provided by {@code source} using {@code mapper}
     * and unmaps values for {@link ListIterator#set(Object)} and {@link ListIterator#add(Object)}
     * operations using {@code unmapper}.
     *
     * @param source   Source iterator.
     * @param mapper   Mapper of values.
     * @param unmapper Unmapper of values.
     * @param <E>      Element type.
     * @param <R>      Target type.
     * @return List iterator which maps values of {@code source}.
     */
    public static <E, R> ListIterator<R> mappedIterator(ListIterator<E> source,
                                                        Function<E, R> mapper,
                                                        Function<R, E> unmapper) {
        return new ListIterator<R>() {
            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public R next() {
                return mapper.apply(source.next());
            }

            @Override
            public boolean hasPrevious() {
                return source.hasNext();
            }

            @Override
            public R previous() {
                return mapper.apply(source.previous());
            }

            @Override
            public int nextIndex() {
                return source.nextIndex();
            }

            @Override
            public int previousIndex() {
                return source.previousIndex();
            }

            @Override
            public void remove() {
                source.remove();
            }

            @Override
            public void set(R r) {
                source.set(unmapper.apply(r));
            }

            @Override
            public void add(R r) {
                source.add(unmapper.apply(r));
            }


        };
    }

    /**
     * Wraps a {@code listIterator} into another and call {@code addCheck} every time {@link
     * ListIterator#add(Object)} is called.
     *
     * @param listIterator List iterator to wrap.
     * @param addCheck     Checker runnable. (Should throw exception if cannot add element).
     * @param <E>          Element type.
     * @return Wrapped list iterator.
     */
    public static <E> ListIterator<E> addCheckListIterator(ListIterator<E> listIterator, CRunnable addCheck) {
        return new ListIterator<E>() {
            @Override
            public boolean hasNext() {
                return listIterator.hasNext();
            }

            @Override
            public E next() {
                return listIterator.next();
            }

            @Override
            public boolean hasPrevious() {
                return listIterator.hasPrevious();
            }

            @Override
            public E previous() {
                return listIterator.previous();
            }

            @Override
            public int nextIndex() {
                return listIterator.nextIndex();
            }

            @Override
            public int previousIndex() {
                return listIterator.previousIndex();
            }

            @Override
            public void remove() {
                listIterator.remove();
            }

            @Override
            public void set(E e) {
                listIterator.set(e);
            }

            @Override
            public void add(E e) {
                addCheck.run();
                listIterator.add(e);
            }
        };
    }
}
