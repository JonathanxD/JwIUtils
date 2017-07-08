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
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Iterator utility.
 */
public class IteratorUtil {

    private static final Iterator<?> EMPTY_ITER = new EmptyIterator();
    private static final Iterator<?> EMPTY_LIST_ITER = new EmptyListIterator();

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
        return new SingleIterator<>(e);
    }

    /**
     * Creates an {@link Iterator} which has only one element to iterate.
     *
     * @param e   Supplier of elements.
     * @param <E> Element type.
     * @return {@link Iterator} which has only one element to iterate.
     */
    public static <E> Iterator<E> singleSupplied(final Supplier<E> e) {
        return new SingleSuppliedIterator<>(e);
    }

    /**
     * Creates an {@link Iterator} which has {@code amount} of elements to iterator, which elements
     * are provided by supplier {@code e}.
     *
     * @param amount Amount of provided elements.
     * @param e      Supplier of elements.
     * @param <E>    Element type.
     * @return {@link Iterator} which has {@code amount} of elements to iterator, which elements
     */
    public static <E> Iterator<E> amountSupplied(final int amount, final Supplier<E> e) {
        return new AmountSuppliedIterator<>(amount, e);
    }

    /**
     * Creates an {@link Iterator} which has {@code amount} of elements to iterator, which elements
     * are provided by supplier {@code e}.
     *
     * @param hasNext Function to delegate {@link Iterator#hasNext()} function.
     * @param e       Supplier of elements.
     * @param <E>     Element type.
     * @return {@link Iterator} which has {@code amount} of elements to iterator, which elements
     */
    public static <E> Iterator<E> nextDelegatedSuppliedIterator(final BooleanSupplier hasNext, final Supplier<E> e) {
        return new NextDelegatedSuppliedIterator<>(hasNext, e);
    }

    /**
     * Creates a {@link ListIterator} which has only one element to iterate (does not support add).
     *
     * @param e   Element.
     * @param <E> Element type.
     * @return {@link Iterator} which has only one element to iterate.
     */
    public static <E> ListIterator<E> singleListIterator(final E e) {
        return new SingleListIterator<>(e);
    }

    /**
     * Creates an {@link ListIterator} which has only one element to iterate.
     *
     * @param e   Supplier of element.
     * @param <E> Element type.
     * @return {@link Iterator} which has only one element to iterate.
     */
    public static <E> ListIterator<E> singleSuppliedListIterator(final Supplier<E> e) {
        return new SingleSuppliedListIterator<>(e);
    }

    /**
     * Creates a {@link ListIterator} which has {@code amount} of elements which is supplied by
     * {@code e}.
     *
     * Reversed iteration (using {@link ListIterator#hasPrevious()} and {@link
     * ListIterator#previous()}) are implemented by an array list, and forwarding before backing
     * will returns next element in list. A full supplied is only possible through wrapping a {@link
     * #amountSupplied(int, Supplier) Supplied iterator} into a {@link #listIterator(Iterator) List
     * Iterator}
     *
     * @param amount Amount of supplied elements
     * @param e      Element supplier.
     * @param <E>    Element type.
     * @return {@link Iterator} which has {@code amount} of elements which is supplied by {@code e}.
     */
    public static <E> ListIterator<E> suppliedListIterator(final int amount, final Supplier<E> e) {
        return new SuppliedListIterator<>(amount, e);
    }

    /**
     * Creates a {@link ListIterator} which has delegates {@link ListIterator#hasNext()} to {@code
     * hasNext} function, this iterator also lazy gets element through a {@code supplier} and fully
     * supports reverse-iteration, which is implemented using a {@link List}.
     *
     * A full supplied is only possible through wrapping a {@link #nextDelegatedSuppliedIterator(BooleanSupplier,
     * Supplier) Supplied iterator} into a {@link #listIterator(Iterator) List Iterator}, but
     * reverse-iteration is not supported.
     *
     * @param hasNext  Function to delegate hasNext.
     * @param supplier Element supplier.
     * @param <E>      Element type.
     * @return {@link Iterator} which has {@code amount} of elements which is supplied by {@code
     * supplier}.
     */
    public static <E> ListIterator<E> nextDelegatedSuppliedListIterator(final BooleanSupplier hasNext, final Supplier<E> supplier) {
        return new NextDelegatedSuppliedListIterator<>(hasNext, supplier);
    }

    /**
     * Creates an wrapper which wraps a {@link Iterator} into a {@link ListIterator}, returned list
     * iterator does not support back-iterating, an back iterating is only possible via .
     *
     * @param iterator Iterator to back.
     * @param <E>      Element type.
     * @return Wrapper {@link ListIterator}.
     */
    public static <E> ListIterator<E> listIterator(final Iterator<E> iterator) {
        return new BackedListIterator<>(iterator);
    }

    /**
     * Creates an {@link Iterator} of an array of type {@link E}.
     *
     * {@link Iterator#remove()} is not supported.
     *
     * @param args Elements.
     * @param <E>  Element type.
     * @return {@link Iterator} of an array of type {@link E}.
     */
    @SafeVarargs
    public static <E> Iterator<E> ofArray(final E... args) {
        return new ArrayIterator<>(args);
    }

    /**
     * Creates an {@link ListIterator} of an array of type {@link E}.
     *
     * {@link ListIterator#remove()} and {@link ListIterator#add(Object)} are not supported.
     *
     * @param args Elements.
     * @param <E>  Element type.
     * @return {@link ListIterator} of an array of type {@link E}.
     */
    @SafeVarargs
    public static <E> ListIterator<E> listIteratorOfArray(final E... args) {
        return new ArrayListIterator<>(args);
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
        return new ViewMappedIterator<>(mapper, value, original);
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
        return new ViewMappedListIterator<>(original, mapper, value, unmapper);
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
        return new MappedIterator<>(source, mapper);
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
        return new MappedListIterator<>(source, mapper, unmapper);
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
        return new AddCheckListIterator<>(listIterator, addCheck);
    }

    /**
     * Returns an {@link Iterator} which does not iterate over any element.
     *
     * @return {@link Iterator} which does not iterate over any element.
     */
    @SuppressWarnings("unchecked")
    public static <E> Iterator<E> emptyIterator() {
        return (Iterator<E>) EMPTY_ITER;
    }

    /**
     * Returns an {@link ListIterator} which does not iterate over any element.
     *
     * @return {@link ListIterator} which does not iterate over any element.
     */
    @SuppressWarnings("unchecked")
    public static <E> ListIterator<E> emptyListIterator() {
        return (ListIterator<E>) EMPTY_LIST_ITER;
    }

    private static class BackedListIterator<E> implements ListIterator<E> {
        private final Iterator<E> iterator;

        public BackedListIterator(Iterator<E> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next();
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    private static class SingleIterator<E> implements Iterator<E> {

        private final E e;
        private boolean hasNext;

        public SingleIterator(E e) {
            this.e = e;
            hasNext = true;
        }

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
    }

    private static class SingleSuppliedIterator<E> implements Iterator<E> {

        private final Supplier<E> e;
        private boolean hasNext;

        public SingleSuppliedIterator(Supplier<E> e) {
            this.e = e;
            hasNext = true;
        }

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
    }

    private static class SingleSuppliedListIterator<E> implements ListIterator<E> {

        private final Supplier<E> e;
        private E element = null;
        private boolean hasNext;

        public SingleSuppliedListIterator(Supplier<E> e) {
            this.e = e;
            this.hasNext = true;
        }

        @Override
        public boolean hasNext() {
            return this.hasNext;
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            this.hasNext = false;

            if(this.element != null)
                return this.element;

            return this.element = e.get();
        }

        @Override
        public boolean hasPrevious() {
            return !this.hasNext();
        }

        @Override
        public E previous() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            this.hasNext = true;

            return this.element;
        }

        @Override
        public int nextIndex() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            return 0;
        }

        @Override
        public int previousIndex() {
            if (!this.hasPrevious())
                throw new NoSuchElementException();

            return 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    private static class AmountSuppliedIterator<E> implements Iterator<E> {

        private final int amount;
        private final Supplier<E> e;
        private int current = 0;

        public AmountSuppliedIterator(int amount, Supplier<E> e) {
            this.amount = amount;
            this.e = e;
        }

        @Override
        public boolean hasNext() {
            return this.current < this.amount;
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            this.current++;

            return e.get();
        }
    }

    private static class NextDelegatedSuppliedIterator<E> implements Iterator<E> {

        private final BooleanSupplier hasNext;
        private final Supplier<E> e;

        public NextDelegatedSuppliedIterator(BooleanSupplier hasNext, Supplier<E> e) {
            this.hasNext = hasNext;
            this.e = e;
        }

        @Override
        public boolean hasNext() {
            return this.hasNext.getAsBoolean();
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            return this.e.get();
        }
    }

    private static class SingleListIterator<E> implements ListIterator<E> {

        private E value;

        private boolean removed;
        private boolean hasNext;

        public SingleListIterator(E e) {
            this.value = e;
            this.removed = false;
            this.hasNext = true;
        }

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
    }

    private static class SuppliedListIterator<E> implements ListIterator<E> {

        private final List<E> elements;
        private final int amount;
        private final Supplier<E> e;
        private int current;

        public SuppliedListIterator(int amount, Supplier<E> e) {
            this.amount = amount;
            this.e = e;
            elements = new ArrayList<>();
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return this.current < amount;
        }

        @Override
        public boolean hasPrevious() {
            return this.current > 0;
        }

        @Override
        public E previous() {
            if (!this.hasPrevious())
                throw new NoSuchElementException();

            --current;

            return this.elements.get(current);
        }

        @Override
        public int nextIndex() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            return this.current;
        }

        @Override
        public int previousIndex() {
            return this.current - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove values from supplied iterator.");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Cannot add values to supplied iterator.");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Cannot add values to supplied iterator.");
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            if (this.elements.size() < this.current + 1) {
                this.elements.add(e.get());
            }

            return this.elements.get(this.current++);
        }
    }

    private static class NextDelegatedSuppliedListIterator<E> implements ListIterator<E> {

        private final List<E> elements = new ArrayList<>();
        private final BooleanSupplier hasNext;
        private final Supplier<E> e;
        private int current = 0;

        public NextDelegatedSuppliedListIterator(BooleanSupplier hasNext, Supplier<E> e) {
            this.hasNext = hasNext;
            this.e = e;
        }

        private void checkRemoved() {
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            return this.current < elements.size() || this.hasNext.getAsBoolean();
        }

        @Override
        public boolean hasPrevious() {
            return this.current > 0;
        }

        @Override
        public E previous() {
            if (!this.hasPrevious())
                throw new NoSuchElementException();

            --current;

            return this.elements.get(current);
        }

        @Override
        public int nextIndex() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            return this.current;
        }

        @Override
        public int previousIndex() {
            return this.current - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove values from supplied iterator.");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Cannot add values to supplied iterator.");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Cannot add values to supplied iterator.");
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            if (this.elements.size() < this.current + 1) {
                this.elements.add(e.get());
            }

            return this.elements.get(this.current++);
        }
    }

    private static class ArrayIterator<E> implements Iterator<E> {
        private final E[] args;
        private int index;

        public ArrayIterator(E[] args) {
            this.args = args;
            index = -1;
        }

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
    }

    private static class ArrayListIterator<E> implements ListIterator<E> {
        private final E[] args;
        private int index;

        public ArrayListIterator(E[] args) {
            this.args = args;
            index = -1;
        }

        @Override
        public int nextIndex() {
            return this.index + 1;
        }

        @Override
        public int previousIndex() {
            return this.index - 1;
        }

        @Override
        public boolean hasNext() {
            return this.index + 1 < args.length;
        }

        @Override
        public boolean hasPrevious() {
            return this.index - 1 > -1;
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            return args[++index];
        }

        @Override
        public E previous() {
            if (!this.hasPrevious())
                throw new NoSuchElementException();

            return this.args[--index];
        }

        @Override
        public void set(E e) {
            this.args[index] = e;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class ViewMappedIterator<E, Y> implements Iterator<Y> {

        private final Function<E, Y> mapper;
        private final E value;
        private final Iterator<E> original;
        private boolean readed;

        public ViewMappedIterator(Function<E, Y> mapper, E value, Iterator<E> original) {
            this.mapper = mapper;
            this.value = value;
            this.original = original;
            this.readed = false;
        }

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
    }

    private static class ViewMappedListIterator<E, Y> implements ListIterator<Y> {

        private final int index;
        private final ListIterator<E> original;
        private final Function<E, Y> mapper;
        private final E value;
        private final Function<Y, E> unmapper;
        private boolean readed;

        public ViewMappedListIterator(ListIterator<E> original, Function<E, Y> mapper, E value, Function<Y, E> unmapper) {
            this.original = original;
            this.mapper = mapper;
            this.value = value;
            this.unmapper = unmapper;
            this.index = original.nextIndex() - 1;
            this.readed = false;
        }

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
    }

    private static class MappedIterator<E, R> implements Iterator<R> {
        private final Iterator<E> source;
        private final Function<E, R> mapper;

        public MappedIterator(Iterator<E> source, Function<E, R> mapper) {
            this.source = source;
            this.mapper = mapper;
        }

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
    }

    private static class MappedListIterator<E, R> implements ListIterator<R> {
        private final ListIterator<E> source;
        private final Function<E, R> mapper;
        private final Function<R, E> unmapper;

        public MappedListIterator(ListIterator<E> source, Function<E, R> mapper, Function<R, E> unmapper) {
            this.source = source;
            this.mapper = mapper;
            this.unmapper = unmapper;
        }

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


    }

    private static class AddCheckListIterator<E> implements ListIterator<E> {
        private final ListIterator<E> listIterator;
        private final CRunnable addCheck;

        public AddCheckListIterator(ListIterator<E> listIterator, CRunnable addCheck) {
            this.listIterator = listIterator;
            this.addCheck = addCheck;
        }

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
    }

    private static class EmptyIterator<E> implements Iterator<E> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            throw new NoSuchElementException();
        }
    }

    private static class EmptyListIterator<E> implements ListIterator<E> {
        @Override
        public int nextIndex() {
            return -1;
        }

        @Override
        public int previousIndex() {
            return -1;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E next() {
            throw new NoSuchElementException();
        }

        @Override
        public E previous() {
            throw new NoSuchElementException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
