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
package com.github.jonathanxd.iutils.collectionsw.impl.java;

import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.collectionsw.IterableW;
import com.github.jonathanxd.iutils.collectionsw.IteratorW;
import com.github.jonathanxd.iutils.collectionsw.impl.builder.MutableListWBuilder;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaSubCollection;
import com.github.jonathanxd.iutils.object.Lazy;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Java collection backed implementation of {@link CollectionW}.
 *
 * @param <E> Type of elements.
 */
public class JavaWrappedCollectionW<E> implements CollectionW<E> {

    protected final Collection<E> wrapped;
    private final Lazy<Collection<E>> unmodColl;
    private final IterableW<E> iterable;

    private final Function<Collection<?>, ? extends Collection<?>> collectionFactory;

    /**
     * Constructs a Java wrapped collection from {@code mapped} and use {@code collectionFactory} to
     * create new collections.
     *
     * @param wrapped           Wrapped collection.
     * @param collectionFactory Factory of collection, make sure that the factory returns a new
     *                          collection, otherwise this collection will be mutated, which is a
     *                          unexpected behavior. This class may throw an exception if it can
     *                          detect that the {@code collectionFactory} returned {@code wrapped}.
     *                          This factory receives a collection as argument to create a new
     *                          collection with same elements as received collection, but it is not
     *                          guaranteed that the {@code wrapped} collection will be passed or not
     *                          as an argument nor that the passed collection is of the same type.
     *                          Returned collection should be generic and not type checked because
     *                          some operations return a collection of another type.
     */
    public JavaWrappedCollectionW(Collection<E> wrapped,
                                  Function<Collection<?>, ? extends Collection<?>> collectionFactory) {
        this.wrapped = wrapped;
        this.collectionFactory = collectionFactory;
        this.unmodColl = Lazy.lazy(() -> Collections.unmodifiableCollection(this.wrapped));
        this.iterable = new JavaBackedIterableW<>(this.wrapped);
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return this.iterable.asJavaIterable();
    }

    @Override
    public Collection<E> asJavaCollection() {
        return this.unmodColl.get();
    }

    @Override
    public IteratorW<E> iterator() {
        return this.iterable.iterator();
    }

    @Override
    public E first() {
        this.checkNotEmpty();

        return this.iterator().next();
    }

    @Override
    public CollectionW<E> head() {
        if (this.isEmpty())
            return new JavaWrappedCollectionW<>(Collections.emptyList(), this.collectionFactory);

        return new JavaWrappedCollectionW<>(Collections.singleton(this.first()), this.collectionFactory);
    }

    @Override
    public E last() {
        this.checkNotEmpty();

        IteratorW<E> iterator = this.iterator();

        E last = null;

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!iterator.hasNext())
                last = next;
        }

        return last;
    }

    @Override
    public CollectionW<E> tail() {
        if (this.isEmpty())
            return new JavaWrappedCollectionW<>(Collections.emptyList(), this.collectionFactory);

        return new JavaWrappedCollectionW<>(new JavaSubCollection<>(this.wrapped, 1, this.size()), this.collectionFactory);
    }

    @Override
    public int size() {
        return this.wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return this.wrapped.contains(o);
    }

    @Override
    public CollectionW<E> prepend(E e) {
        Collection<E> es = this.newCollection(Collections.emptyList());

        es.add(e);

        es.addAll(this.wrapped);

        return this.newFrom(es);
    }

    @Override
    public CollectionW<E> append(E e) {
        return this.add(e);
    }

    @Override
    public CollectionW<E> prepend(CollectionW<? extends E> es) {
        Collection<E> es2 = this.newCollection(Collections.emptyList());

        es.iterator().forEachRemaining(es2::add);
        es2.addAll(this.wrapped);

        return this.newFrom(es2);
    }

    @Override
    public CollectionW<E> append(CollectionW<? extends E> es) {
        return this.addAll(es);
    }

    @Override
    public CollectionW<E> add(E e) {
        Collection<E> es = this.newCollection(this.wrapped);

        es.add(e);

        return this.newFrom(es);
    }

    @Override
    public CollectionW<E> remove(E e) {
        Collection<E> es = this.newCollection(this.wrapped);

        es.remove(e);

        return this.newFrom(es);
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.iterator().allOfRemaining(this::contains);
    }

    @Override
    public CollectionW<E> addAll(CollectionW<? extends E> c) {
        Collection<E> es = this.newCollection(this.wrapped);

        c.iterator().forEachRemaining(es::add);

        return this.newFrom(es);
    }

    @Override
    public CollectionW<E> removeAll(CollectionW<? extends E> c) {
        Collection<E> es = this.newCollection(this.wrapped);

        c.iterator().forEachRemaining(es::remove);

        return this.newFrom(es);
    }

    @Override
    public <R> CollectionW<R> map(Function<? super E, ? extends R> mapper) {

        Collection<R> es = this.newCollection(Collections.emptyList());

        for (E e : this.wrapped) {
            es.add(mapper.apply(e));
        }

        return this.newFrom(es);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> CollectionW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        Collection<R> es = this.newCollection(Collections.emptyList());

        for (E e : this.wrapped) {
            es.addAll((Collection<? extends R>) mapper.apply(e));
        }

        return this.newFrom(es);
    }

    @Override
    public CollectionW<E> filter(Predicate<? super E> filter) {
        Collection<E> es = this.newCollection(this.wrapped);

        es.removeIf(filter.negate());

        return this.newFrom(es);
    }

    private void checkNotEmpty() {
        if (this.wrapped.isEmpty())
            throw new NoSuchElementException();
    }

    @Override
    public CollectionW<E> copy() {
        return this.newFrom(newCollection(this.wrapped));
    }

    protected <R> CollectionW<R> newFrom(Collection<R> collection) {
        return new JavaWrappedCollectionW<>(collection, this.collectionFactory);
    }

    @SuppressWarnings("unchecked")
    protected <R> Collection<R> newCollection(Collection<R> collection) {
        return (Collection<R>) this.collectionFactory.apply(collection);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof CollectionW<?>) {
            CollectionW<?> w = (CollectionW<?>) obj;

            if (w.size() != this.size())
                return false;

            try {
                return this.containsAll((CollectionW<? extends E>) w);
            } catch (Throwable e) {
                return false;
            }
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public String toString() {
        return this.wrapped.toString();
    }

    @Override
    public Builder<E> builder() {
        return MutableListWBuilder.builder(this);
    }
}
