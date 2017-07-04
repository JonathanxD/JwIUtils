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
package com.github.jonathanxd.iutils.collectionsw.impl.mutable;

import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.collectionsw.IteratorW;
import com.github.jonathanxd.iutils.collectionsw.impl.builder.SizedListWBuilder;
import com.github.jonathanxd.iutils.collectionsw.mutable.BiDiBoundedMutIteratorW;
import com.github.jonathanxd.iutils.collectionsw.mutable.BiDiIndexedMutIteratorW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableListW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSizedListW;
import com.github.jonathanxd.iutils.exception.LimitExceededException;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.list.DynamicSizedJavaList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * The design choice of this implemented was explained {@link DynamicSizedJavaList here}.
 *
 * @param <E> Element type.
 */
public class WBackedDynamicSizedListW<E> implements MutableSizedListW<E> {
    private final MutableListW<E> listW;
    private final IntSupplier maxSizeSupplier;
    private final List<E> sized;
    private final Supplier<MutableListW<?>> factory;

    protected WBackedDynamicSizedListW(MutableListW<E> listW, IntSupplier maxSizeSupplier) {
        this(listW, maxSizeSupplier, () -> new JavaBackedMutListW<E>(new ArrayList<>()));
    }

    protected WBackedDynamicSizedListW(MutableListW<E> listW, IntSupplier maxSizeSupplier,
                                       Supplier<MutableListW<?>> factory) {

        int limit = maxSizeSupplier.getAsInt();

        if (listW.size() > limit)
            throw new LimitExceededException("This list only accepts " + limit + " elements and " + listW.size() + " elements was provided as initial elements and backing list.");

        this.listW = listW;
        this.maxSizeSupplier = maxSizeSupplier;
        this.sized = new DynamicSizedJavaList<E>(listW.asJavaList(), maxSizeSupplier) {
        };
        this.factory = factory;
    }

    @Override
    public int maxSize() {
        return this.maxSizeSupplier.getAsInt();
    }

    @Override
    public List<E> asJavaList() {
        return this.sized;
    }

    @Override
    public E first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.iterator().next();
    }

    @Override
    public MutableSizedListW<E> head() {
        return new WBackedDynamicSizedListW<>(listW.head(),
                () -> (this.maxSize() - this.size())
        );
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!iterator.hasNext())
                return next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public MutableSizedListW<E> tail() {
        MutableListW<E> tail = listW.tail();
        return new WBackedDynamicSizedListW<>(tail,
                () -> (this.maxSize() - this.size())
        );
    }

    @Override
    public int size() {
        return this.listW.size();
    }

    @Override
    public boolean isEmpty() {
        return this.listW.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return this.listW.contains(o);
    }

    @Override
    public MutableSizedListW<E> prepend(E e) {
        this.checkSize(1);
        this.listW.prepend(e);
        return this;
    }

    @Override
    public MutableSizedListW<E> prepend(CollectionW<? extends E> es) {
        this.checkSize(es.size());
        this.listW.prepend(es);
        return this;
    }

    @Override
    public MutableSizedListW<E> append(E e) {
        this.checkSize(1);
        this.listW.append(e);
        return this;
    }

    @Override
    public MutableSizedListW<E> append(CollectionW<? extends E> es) {
        this.checkSize(es.size());
        this.listW.append(es);
        return this;
    }

    @Override
    public MutableSizedListW<E> add(E e) {
        return this.append(e);
    }

    @Override
    public MutableSizedListW<E> remove(E e) {
        this.listW.remove(e);
        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return this.listW.containsAll(c);
    }

    @Override
    public MutableSizedListW<E> addAll(CollectionW<? extends E> c) {
        this.append(c);
        return this;
    }

    @Override
    public MutableSizedListW<E> removeAll(CollectionW<? extends E> c) {
        this.listW.removeAll(c);
        return this;
    }

    @Override
    public <R> MutableSizedListW<R> map(Function<? super E, ? extends R> mapper) {
        int size = this.maxSizeSupplier.getAsInt();
        return createNew(this.listW.map(mapper), () -> size);
    }

    @Override
    public <R> MutableSizedListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        MutableListW<R> mutableListW = this.listW.flatMap(mapper);
        int size = mutableListW.size();
        return createNew(mutableListW, () -> size);
    }

    @Override
    public MutableSizedListW<E> filter(Predicate<? super E> filter) {
        this.listW.filter(filter);

        return this;
    }

    @Override
    public void clear() {
        this.listW.clear();
    }

    @Override
    public Collection<E> asJavaCollection() {
        return this.asJavaList();
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return this.asJavaList();
    }

    @Override
    public BiDiBoundedMutIteratorW<E> iterator() {
        return new BoundedIter(this.listW.iterator());
    }

    @Override
    public BiDiBoundedMutIteratorW<E> iterator(int index) {
        return new BoundedIter(this.listW.iterator(index));
    }

    @Override
    public MutableSizedListW<E> addAll(int index, CollectionW<? extends E> c) {
        this.checkSize(c.size());
        this.listW.addAll(index, c);
        return this;
    }

    @Override
    public E get(int index) {
        return this.listW.get(index);
    }

    @Override
    public MutableSizedListW<E> getEntry(int index) {
        return new WBackedDynamicSizedListW<>(listW.getEntry(index),
                () -> this.maxSize() - this.size()
        );
    }

    @Override
    public MutableSizedListW<E> set(int index, E element) {
        this.listW.set(index, element);
        return this;
    }

    @Override
    public MutableSizedListW<E> add(int index, E element) {
        this.checkSize(1);
        this.listW.add(index, element);
        return this;
    }

    @Override
    public MutableSizedListW<E> remove(int index) {
        this.listW.remove(index);
        return this;
    }

    @Override
    public int indexOf(E o) {
        return this.listW.indexOf(o);
    }

    @Override
    public int lastIndexOf(E o) {
        return this.listW.lastIndexOf(o);
    }

    @Override
    public MutableSizedListW<E> subList(int fromIndex, int toIndex) {
        return new WBackedDynamicSizedListW<>(this.listW.subList(fromIndex, toIndex),
                () -> this.maxSize() - this.size()
        );
    }

    @Override
    public MutableSizedListW<E> replaceAll(UnaryOperator<E> operator) {
        this.listW.replaceAll(operator);
        return this;
    }

    @Override
    public MutableSizedListW<E> sorted(Comparator<? super E> c) {
        this.listW.sorted(c);
        return this;
    }

    @Override
    public <R> MutableSizedListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper) {
        int size = this.maxSizeSupplier.getAsInt();
        return createNew(this.listW.mapIndexed(mapper), () -> size);
    }

    @Override
    public <R> MutableSizedListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper) {
        MutableListW<R> mutableListW = this.listW.flatMapIndexed(mapper);
        int size = mutableListW.size();
        return createNew(mutableListW, () -> size);
    }

    @Override
    public MutableSizedListW<E> filterIndexed(IntObjBiPredicate<? super E> filter) {
        this.listW.filterIndexed(filter);
        return this;
    }

    @Override
    public MutableSizedListW<E> copy() {
        return new WBackedDynamicSizedListW<>(this.listW.copy(), this.maxSizeSupplier);
    }

    @Override
    public MutableSizedListW<E> newWithSize(int newSize) {
        return new WBackedDynamicSizedListW<>(this.listW.copy(), () -> newSize);
    }


    @SuppressWarnings("unchecked")
    public <R> MutableListW<R> createNew() {
        return (MutableListW<R>) this.factory.get();
    }

    @SuppressWarnings("unchecked")
    public <R> WBackedDynamicSizedListW<R> createNew(MutableListW<R> listW, IntSupplier maxSizeSupplier) {
        return new WBackedDynamicSizedListW<>(listW, maxSizeSupplier, this.factory);
    }

    private void checkSize(int elements) throws LimitExceededException {
        if (this.size() + elements >= this.maxSize())
            throw new LimitExceededException("Can't add " + elements + " elements to SizedList. Limit: " + this.maxSize() + ". Size: " + this.size() + ". Size before addition of elements: " + this.size() + elements + ".");
    }

    @Override
    public Builder<E> builder() {
        return SizedListWBuilder.builder(this.maxSize(), this);
    }

    final class BoundedIter implements BiDiBoundedMutIteratorW<E> {

        private final BiDiIndexedMutIteratorW<E> iter;

        BoundedIter(BiDiIndexedMutIteratorW<E> iter) {
            this.iter = iter;
        }

        @Override
        public ListIterator<E> asJavaListIterator() {
            return IteratorUtil.addCheckListIterator(this.iter.asJavaListIterator(),
                    () -> WBackedDynamicSizedListW.this.checkSize(1));
        }

        @Override
        public int end() {
            return WBackedDynamicSizedListW.this.maxSize() - 1;
        }

        @Override
        public int index() {
            return this.iter.index();
        }

        @Override
        public boolean hasPrevious() {
            return this.iter.hasPrevious();
        }

        @Override
        public E previous() {
            return this.iter.previous();
        }

        @Override
        public void remove() {
            this.iter.remove();
        }

        @Override
        public Iterator<E> asJavaIterator() {
            return this.asJavaListIterator();
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public E next() {
            return this.iter.next();
        }

        @Override
        public BiDiBoundedMutIteratorW<E> copy() {
            return new BoundedIter(this.iter.copy());
        }
    }
}
