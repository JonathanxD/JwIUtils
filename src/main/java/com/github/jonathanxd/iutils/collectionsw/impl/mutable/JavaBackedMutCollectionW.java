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
import com.github.jonathanxd.iutils.collectionsw.impl.builder.MutableListWBuilder;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableCollectionW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableIteratorW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Java backed {@link MutableCollectionW}.
 *
 * @param <E> Element type.
 */
public final class JavaBackedMutCollectionW<E> implements MutableCollectionW<E> {
    private final Collection<E> collection;
    private final Function<Collection<?>, Collection<?>> factory;

    public JavaBackedMutCollectionW(Collection<E> collection) {
        this(collection, ArrayList::new);
    }

    public JavaBackedMutCollectionW(Collection<E> collection, Function<Collection<?>, Collection<?>> factory) {
        this.collection = collection;
        this.factory = factory;
    }

    @Override
    public Collection<E> asJavaCollection() {
        return this.collection;
    }

    @Override
    public MutableCollectionW<E> head() {
        return new JavaBackedMutCollectionW<>(new JavaSubCollection<>(this.collection, 0, 0));
    }


    @Override
    public E first() {
        if (this.collection.isEmpty())
            throw new NoSuchElementException();

        return this.collection.iterator().next();
    }

    @Override
    public E last() {
        if (this.collection.isEmpty())
            throw new NoSuchElementException();

        Iterator<E> iterator = this.collection.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!iterator.hasNext())
                return next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public MutableCollectionW<E> tail() {
        if (this.collection.isEmpty())
            return new JavaBackedMutCollectionW<>(new JavaSubCollection<>(this.collection, 0, 0));

        return new JavaBackedMutCollectionW<>(new JavaSubCollection<>(this.collection, 1, this.collection.size()));
    }

    @Override
    public int size() {
        return this.collection.size();
    }

    @Override
    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return this.collection.contains(o);
    }

    @Override
    public MutableCollectionW<E> prepend(E e) {
        this.collection.add(e);
        return this;
    }

    @Override
    public MutableCollectionW<E> prepend(CollectionW<? extends E> es) {
        IteratorW<? extends E> iterator = es.iterator();

        while (iterator.hasNext()) {
            this.collection.add(iterator.next());
        }

        return this;
    }

    @Override
    public MutableCollectionW<E> append(E e) {
        this.collection.add(e);
        return this;
    }

    @Override
    public MutableCollectionW<E> append(CollectionW<? extends E> es) {
        es.forEach(this.collection::add);
        return this;
    }

    @Override
    public MutableCollectionW<E> add(E e) {
        return this.append(e);
    }

    @Override
    public MutableCollectionW<E> remove(E e) {
        this.collection.remove(e);
        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.all(this::contains);
    }

    @Override
    public MutableCollectionW<E> addAll(CollectionW<? extends E> c) {
        return this.append(c);
    }

    @Override
    public MutableCollectionW<E> removeAll(CollectionW<? extends E> c) {
        c.forEach(this::remove);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableCollectionW<R> map(Function<? super E, ? extends R> mapper) {
        Collection<R> collection = (Collection<R>) factory.apply(Collections.emptyList());

        for (E e : this.collection) {
            collection.add(mapper.apply(e));
        }

        return new JavaBackedMutCollectionW<>(collection);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableCollectionW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        MutableCollectionW<R> left = new JavaBackedMutCollectionW<>((Collection<R>) factory.apply(Collections.emptyList()));

        for (E e : this.collection) {
            left.addAll(mapper.apply(e));
        }

        return left;
    }

    @Override
    public MutableCollectionW<E> filter(Predicate<? super E> filter) {
        this.collection.removeIf(filter.negate());
        return this;
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return this.collection;
    }

    @Override
    public MutableIteratorW<E> iterator() {
        return new JavaBackedMutIteratorW<>(this.collection.iterator());
    }


    @SuppressWarnings("unchecked")
    @Override
    public MutableCollectionW<E> copy() {
        return new JavaBackedMutCollectionW<>((Collection<E>) factory.apply(this.collection));
    }

    @Override
    public void clear() {
        this.collection.clear();
    }

    @Override
    public String toString() {
        return this.collection.toString();
    }

    @Override
    public Builder<E> builder() {
        return MutableListWBuilder.builder(this);
    }
}
