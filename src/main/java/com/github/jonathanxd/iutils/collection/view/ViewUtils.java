/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        return new IndexedIterator<>(i::iterator, mapper);
    }

    /**
     * Creates a iterator which maps elements of iterator supplied by {@code i} to an {@link
     * Iterator} and provide these elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterator supplier
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return Mapping inner iterator.
     */
    public static <E, Y> Iterator<Y> iterator(Supplier<Iterator<E>> i, BiFunction<E, Iterator<E>, Iterator<Y>> mapper) {
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
     * @param start  Start index of list.
     * @param <E>    Element type.
     * @return Mapping inner iterator.
     */
    public static <E, Y> ListIterator<Y> listIterator(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper, int start) {
        return new IndexedListIterator<>(i::listIterator, mapper, start, false);
    }

    /**
     * Creates a iterator which maps elements of {@code i} to an {@link Iterator} and provide these
     * elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterator supplier.
     * @param mapper Mapper function.
     * @param start  Start index of list.
     * @param <E>    Element type.
     * @return Mapping inner iterator.
     */
    public static <E, Y> ListIterator<Y> listIterator(Supplier<ListIterator<E>> i,
                                                      BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper,
                                                      int start) {
        return new IndexedListIterator<>(i, mapper, start, false);
    }

    /**
     * Creates a reverse iterator which maps elements of {@code i} to an {@link Iterator} and
     * provide these elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param start  Start index of list.
     * @param <E>    Element type.
     * @return Reverse mapping inner iterator.
     */
    public static <E, Y> ListIterator<Y> reverseListIterator(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper, int start) {
        return new IndexedListIterator<>(() -> new ReverseListIterator<>(i::listIterator, i.size()), mapper, start, false);
    }

    /**
     * Creates a reverse iterator which maps elements of {@code i} to an {@link Iterator} and
     * provide these elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterator supplier.
     * @param size   Size of list.
     * @param mapper Mapper function.
     * @param start  Start index of list.
     * @param <E>    Element type.
     * @return Reverse mapping inner iterator.
     */
    public static <E, Y> ListIterator<Y> reverseListIterator(IntFunction<ListIterator<E>> i, int size, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper, int start) {
        return new IndexedListIterator<>(() -> new ReverseListIterator<>(i, size), mapper, start, false);
    }

    /**
     * Creates a read only iterator which maps elements of {@code i} to an {@link Iterator} and
     * provide these elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param start  Start index of list.
     * @param <E>    Element type.
     * @return Read only mapping inner iterator.
     */
    public static <E, Y> ListIterator<Y> readOnlyListIterator(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper, int start) {
        return new IndexedListIterator<>(() -> new ReverseListIterator<>(i::listIterator, i.size()), mapper, start, true);
    }

    /**
     * Creates a read only iterator which maps elements of {@code i} to an {@link Iterator} and
     * provide these elements for iteration.
     *
     * If you did not understand this doc, see the class, it is not hard to understand what happens
     * inside the iterator.
     *
     * @param i      Iterator supplier.
     * @param mapper Mapper function.
     * @param start  Start index of list.
     * @param <E>    Element type.
     * @return Read only mapping inner iterator.
     */
    public static <E, Y> ListIterator<Y> readOnlyListIterator(Supplier<ListIterator<E>> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper, int start) {
        return new IndexedListIterator<>(i, mapper, start, true);
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
     * @param i      Iterator supplier.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link Iterable} which holds a {@link #iterator(Iterable, BiFunction)} instance.
     */
    public static <E, Y> Iterable<Y> iterable(Supplier<Iterator<E>> i, BiFunction<E, Iterator<E>, Iterator<Y>> mapper) {
        return () -> iterator(i, mapper);
    }

    /**
     * Creates a {@link ListIterable} which holds a {@link #reverseListIterator(List, BiFunction,
     * int)} instance.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link ListIterable} which holds a {@link #reverseListIterator(List, BiFunction,
     * int)} instance.
     */
    public static <E, Y> ListIterable<Y> reverseListIterable(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper) {
        return index -> reverseListIterator(i, mapper, index);
    }

    /**
     * Creates a {@link ListIterable} which holds a {@link #reverseListIterator(List, BiFunction,
     * int)} instance.
     *
     * @param i      Iterator provider.
     * @param size   Size of list.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link ListIterable} which holds a {@link #reverseListIterator(List, BiFunction,
     * int)} instance.
     */
    public static <E, Y> ListIterable<Y> reverseListIterable(IntFunction<ListIterator<E>> i, int size, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper) {
        return index -> reverseListIterator(i, size, mapper, index);
    }

    /**
     * Creates a {@link ListIterable} which holds a {@link #readOnlyListIterator(List, BiFunction,
     * int)} instance.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link ListIterable} which holds a {@link #readOnlyListIterator(List, BiFunction,
     * int)} instance.
     */
    public static <E, Y> ListIterable<Y> readOnlyListIterable(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper) {
        return index -> readOnlyListIterator(i, mapper, index);
    }

    /**
     * Creates a {@link ListIterable} which holds a {@link #readOnlyListIterator(List, BiFunction,
     * int)} instance.
     *
     * @param i      Iterator supplier.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link ListIterable} which holds a {@link #readOnlyListIterator(List, BiFunction,
     * int)} instance.
     */
    public static <E, Y> ListIterable<Y> readOnlyListIterable(Supplier<ListIterator<E>> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper) {
        return index -> readOnlyListIterator(i, mapper, index);
    }

    /**
     * Creates a {@link ListIterable} which holds a {@link #listIterator(List, BiFunction, int)}
     * instance.
     *
     * @param i      Iterable to map.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link ListIterable} which holds a {@link #listIterator(List, BiFunction, int)}
     * instance.
     */
    public static <E, Y> ListIterable<Y> listIterable(List<E> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper) {
        return index -> listIterator(i, mapper, index);
    }

    /**
     * Creates a {@link ListIterable} which holds a {@link #listIterator(List, BiFunction, int)}
     * instance.
     *
     * @param i      Iterator supplier.
     * @param mapper Mapper function.
     * @param <E>    Element type.
     * @return {@link ListIterable} which holds a {@link #listIterator(List, BiFunction, int)}
     * instance.
     */
    public static <E, Y> ListIterable<Y> listIterable(Supplier<ListIterator<E>> i, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper) {
        return index -> listIterator(i, mapper, index);
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
     * Returns a predicate that throws a read only collection exception.
     *
     * @param <X> Value type.
     * @return Predicate that throws a read only collection exception.
     */
    public static <X> Predicate<X> unmodifiable() {
        return x -> {
            throw new UnsupportedOperationException("Read-only collection.");
        };
    }

    /**
     * Returns a predicate that throws a {@link UnsupportedOperationException} exception.
     *
     * @param message Message of exception.
     * @param <X>     Value type.
     * @return Predicate that throws a {@link UnsupportedOperationException} exception.
     */
    public static <X> Predicate<X> unmodifiable(String message) {
        return x -> {
            throw new UnsupportedOperationException(message);
        };
    }

    public interface ListIterable<Y> extends Iterable<Y> {

        @Override
        default ListIterator<Y> iterator() {
            return this.iterator(0);
        }

        ListIterator<Y> iterator(int index);
    }

    static abstract class AbstractIndexedIterator<E, Y> implements Iterator<Y> {

        protected abstract Iterator<Y> getCurrent();

        protected abstract void setCurrent(Iterator<Y> iter);

        protected abstract Iterator<E> getMain();

        protected abstract boolean isMapperPresent();

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

                // Checks if mapper is present.
                if (!this.isMapperPresent()) {
                    // If mapper is not present, uses main iterator.
                    this.setCurrent((Iterator<Y>) this.getMain());
                } else {
                    // Sets current iterator to result of mapping the next element of main iterator to a new iterator using 'mapper'
                    this.setCurrent(this.map(this.getMain().next(), this.getMain()));
                }
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
        @SuppressWarnings("unchecked")
        void defineCurrent() {
            // Checks if current iterator is not defined and main iterator has elements.
            if (this.getCurrent() == null && this.getMain().hasNext()) {

                // Checks if mapper is present.
                if (!this.isMapperPresent()) {
                    // If mapper is not present, uses main iterator.
                    this.setCurrent((Iterator<Y>) this.getMain());
                } else {
                    // Sets current iterator to result of mapping the next element of main iterator to a new iterator using 'mapper'
                    this.setCurrent(map(this.getMain().next(), this.getMain()));
                }
            }
        }
    }

    static class IndexedIterator<E, Y> extends AbstractIndexedIterator<E, Y> {
        private final BiFunction<E, Iterator<E>, Iterator<Y>> mapper;

        /**
         * Main iterator
         */
        private final Iterator<E> main;

        /**
         * Current iterator
         */
        private Iterator<Y> current = null;

        public IndexedIterator(Supplier<Iterator<E>> i, BiFunction<E, Iterator<E>, Iterator<Y>> mapper) {
            this.mapper = mapper;
            this.main = i.get();
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
        protected Iterator<Y> getCurrent() {
            return this.current;
        }

        @Override
        protected void setCurrent(Iterator<Y> iter) {
            this.current = iter;
        }

        @Override
        protected boolean isMapperPresent() {
            return this.mapper != null;
        }
    }

    static class IndexedListIterator<E, Y> extends AbstractIndexedIterator<E, Y> implements ListIterator<Y> {

        private final boolean isReadOnly;

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

        public IndexedListIterator(Supplier<ListIterator<E>> i,
                                   BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper,
                                   int startIndex,
                                   boolean isReadOnly) {
            this.mapper = mapper;
            this.main = i.get();
            this.isReadOnly = isReadOnly;

            if (startIndex != 0) {
                while (this.hasNext() && this.elemIndex != startIndex - 1) {
                    this.next();
                }
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean hasPrevious() {

            boolean isNull = this.current == null;

            // Check if current iterator is null or does not have previous element
            if (isNull || !this.current.hasPrevious()) {
                // Check if main iterator does not have previous elements
                if (!this.main.hasPrevious())
                    return false;

                // Checks if mapper is present.
                if (!this.isMapperPresent()) {
                    // If mapper is not present, uses main iterator.
                    this.setCurrent((Iterator<Y>) this.getMain());
                } else {
                    // Sets current iterator to result of mapping the previous element of main iterator to a new iterator using 'mapper'
                    this.current = mapper.apply(this.main.previous(), this.main);
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
            if (this.isReadOnly)
                throw new UnsupportedOperationException("Read only!");
            super.defineCurrent();
            this.current.set(y);
        }

        @Override
        public void add(Y y) {
            if (this.isReadOnly)
                throw new UnsupportedOperationException("Read only!");

            super.defineCurrent();
            this.current.add(y);
        }

        @Override
        public int previousIndex() {
            return this.elemIndex;
        }

        @Override
        public void remove() {
            if (this.isReadOnly)
                throw new UnsupportedOperationException("Read only!");

            super.defineCurrent();

            if (this.current == null)
                throw new NoSuchElementException();

            this.current.remove();
        }

        @Override
        protected Iterator<Y> getCurrent() {
            return this.current;
        }

        @Override
        protected void setCurrent(Iterator<Y> iter) {
            this.current = (ListIterator<Y>) iter;
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
        protected boolean isMapperPresent() {
            return this.mapper != null;
        }


    }

    static final class ReverseListIterator<E> implements ListIterator<E> {

        private final ListIterator<E> listIterator;

        ReverseListIterator(IntFunction<ListIterator<E>> i, int size) {
            this.listIterator = i.apply(size);
        }

        @Override
        public boolean hasNext() {
            return this.listIterator.hasPrevious();
        }

        @Override
        public E next() {
            return this.listIterator.previous();
        }

        @Override
        public boolean hasPrevious() {
            return this.listIterator.hasNext();
        }

        @Override
        public E previous() {
            return this.listIterator.next();
        }

        @Override
        public int nextIndex() {
            return this.listIterator.previousIndex();
        }

        @Override
        public int previousIndex() {
            return this.listIterator.nextIndex();
        }

        @Override
        public void remove() {
            this.listIterator.remove();
        }

        @Override
        public void set(E e) {
            this.listIterator.set(e);
        }

        @Override
        public void add(E e) {
            this.listIterator.add(e);
        }
    }

    static final class ReadOnlyListIterator<E> implements ListIterator<E> {

        private final ListIterator<E> listIterator;

        ReadOnlyListIterator(ListIterator<E> listIterator) {
            this.listIterator = listIterator;
        }

        @Override
        public boolean hasNext() {
            return this.listIterator.hasNext();
        }

        @Override
        public E next() {
            return this.listIterator.next();
        }

        @Override
        public boolean hasPrevious() {
            return this.listIterator.hasPrevious();
        }

        @Override
        public E previous() {
            return this.listIterator.previous();
        }

        @Override
        public int nextIndex() {
            return this.listIterator.nextIndex();
        }

        @Override
        public int previousIndex() {
            return this.listIterator.previousIndex();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Read only!");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Read only!");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Read only!");
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

}
