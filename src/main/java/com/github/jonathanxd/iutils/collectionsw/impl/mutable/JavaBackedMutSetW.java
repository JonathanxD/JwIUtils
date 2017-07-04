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
import com.github.jonathanxd.iutils.collectionsw.impl.builder.MutableSetWBuilder;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableIteratorW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSetW;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Java backed {@link MutableSetW}.
 *
 * @param <E> Element type.
 */
public final class JavaBackedMutSetW<E> implements MutableSetW<E> {
    private final Set<E> set;
    private final Function<Collection<?>, Set<?>> factory;

    public JavaBackedMutSetW(Set<E> set) {
        this(set, HashSet::new);
    }

    public JavaBackedMutSetW(Set<E> set, Function<Collection<?>, Set<?>> factory) {
        this.set = set;
        this.factory = factory;
    }

    @Override
    public Set<E> asJavaSet() {
        return this.set;
    }

    @Override
    public MutableSetW<E> head() {
        return new JavaBackedMutSetW<>(new JavaSubSet<>(this.set, 0, 0));
    }


    @Override
    public E first() {
        if (this.set.isEmpty())
            throw new NoSuchElementException();

        return this.set.iterator().next();
    }

    @Override
    public E last() {
        if (this.set.isEmpty())
            throw new NoSuchElementException();

        Iterator<E> iterator = this.set.iterator();

        while(iterator.hasNext()) {
            E next = iterator.next();

            if(!iterator.hasNext())
                return next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public MutableSetW<E> tail() {
        if (this.set.isEmpty())
            return new JavaBackedMutSetW<>(new JavaSubSet<>(this.set, 0, 0));

        return new JavaBackedMutSetW<>(new JavaSubSet<>(this.set, 1, this.set.size()));
    }

    @Override
    public int size() {
        return this.set.size();
    }

    @Override
    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return this.set.contains(o);
    }

    @Override
    public MutableSetW<E> prepend(E e) {
        this.set.add(e);
        return this;
    }

    @Override
    public MutableSetW<E> prepend(CollectionW<? extends E> es) {
        IteratorW<? extends E> iterator = es.iterator();

        while (iterator.hasNext()) {
            this.set.add(iterator.next());
        }

        return this;
    }

    @Override
    public MutableSetW<E> append(E e) {
        this.set.add(e);
        return this;
    }

    @Override
    public MutableSetW<E> append(CollectionW<? extends E> es) {
        es.forEach(this.set::add);
        return this;
    }

    @Override
    public MutableSetW<E> add(E e) {
        return this.append(e);
    }

    @Override
    public MutableSetW<E> remove(E e) {
        this.set.remove(e);
        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.all(this::contains);
    }

    @Override
    public MutableSetW<E> addAll(CollectionW<? extends E> c) {
        return this.append(c);
    }

    @Override
    public MutableSetW<E> removeAll(CollectionW<? extends E> c) {
        c.forEach(this::remove);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableSetW<R> map(Function<? super E, ? extends R> mapper) {
        Set<R> set = (Set<R>) factory.apply(Collections.emptyList());

        for (E e : this.set) {
            set.add(mapper.apply(e));
        }

        return new JavaBackedMutSetW<>(set);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableSetW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        MutableSetW<R> left = new JavaBackedMutSetW<>((Set<R>) factory.apply(Collections.emptyList()));

        for (E e : this.set) {
            left.addAll(mapper.apply(e));
        }

        return left;
    }

    @Override
    public MutableSetW<E> filter(Predicate<? super E> filter) {
        this.set.removeIf(filter.negate());
        return this;
    }

    @Override
    public Collection<E> asJavaCollection() {
        return this.set;
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return this.set;
    }

    @Override
    public MutableIteratorW<E> iterator() {
        return new JavaBackedMutIteratorW<>(this.set.iterator());
    }


    @SuppressWarnings("unchecked")
    @Override
    public MutableSetW<E> copy() {
        return new JavaBackedMutSetW<>((Set<E>) factory.apply(this.set));
    }

    @Override
    public void clear() {
        this.set.clear();
    }

    @Override
    public String toString() {
        return this.set.toString();
    }

    @Override
    public Builder<E> builder() {
        return MutableSetWBuilder.builder(this);
    }
}
