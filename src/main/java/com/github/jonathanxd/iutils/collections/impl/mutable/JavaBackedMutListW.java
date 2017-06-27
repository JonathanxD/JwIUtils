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
package com.github.jonathanxd.iutils.collections.impl.mutable;

import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.mutable.BiDiIndexedMutIteratorW;
import com.github.jonathanxd.iutils.collections.mutable.MutableListW;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Java backed {@link MutableListW}.
 *
 * @param <E> Element type.
 */
public final class JavaBackedMutListW<E> implements MutableListW<E> {
    private final List<E> list;
    private final Function<Collection<?>, List<?>> factory;

    public JavaBackedMutListW(List<E> list) {
        this(list, ArrayList::new);
    }

    public JavaBackedMutListW(List<E> list, Function<Collection<?>, List<?>> factory) {
        this.list = list;
        this.factory = factory;
    }

    @Override
    public List<E> asJavaList() {
        return this.list;
    }

    @Override
    public MutableListW<E> head() {
        return new JavaBackedMutListW<>(this.list.subList(0, 0));
    }

    @Override
    public E last() {
        if (this.list.isEmpty())
            throw new NoSuchElementException();

        return this.list.get(this.list.size() - 1);
    }

    @Override
    public MutableListW<E> tail() {
        if (this.list.isEmpty())
            return new JavaBackedMutListW<>(this.list.subList(0, 0));

        return new JavaBackedMutListW<>(this.list.subList(1, this.list.size()));
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return this.list.contains(o);
    }

    @Override
    public MutableListW<E> prepend(E e) {
        this.list.add(0, e);
        return this;
    }

    @Override
    public MutableListW<E> prepend(CollectionW<? extends E> es) {
        int index = 0;

        IteratorW<? extends E> iterator = es.iterator();

        while (iterator.hasNext()) {
            this.list.add(index, iterator.next());
            ++index;
        }

        return this;
    }

    @Override
    public MutableListW<E> append(E e) {
        this.list.add(e);
        return this;
    }

    @Override
    public MutableListW<E> append(CollectionW<? extends E> es) {
        es.forEach(this.list::add);
        return this;
    }

    @Override
    public MutableListW<E> add(E e) {
        return this.append(e);
    }

    @Override
    public MutableListW<E> remove(E e) {
        this.list.remove(e);
        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.all(this::contains);
    }

    @Override
    public MutableListW<E> addAll(CollectionW<? extends E> c) {
        return this.append(c);
    }

    @Override
    public MutableListW<E> removeAll(CollectionW<? extends E> c) {
        c.forEach(this::remove);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableListW<R> map(Function<? super E, ? extends R> mapper) {
        List<R> list = (List<R>) factory.apply(Collections.emptyList());

        for (E e : this.list) {
            list.add(mapper.apply(e));
        }

        return new JavaBackedMutListW<>(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        MutableListW<R> left = new JavaBackedMutListW<>((List<R>) factory.apply(Collections.emptyList()));

        for (E e : this.list) {
            left.addAll(mapper.apply(e));
        }

        return left;
    }

    @Override
    public MutableListW<E> filter(Predicate<? super E> filter) {
        for (E e : this.list) {
            if (!filter.test(e))
                this.remove(e);
        }

        return this;
    }

    @Override
    public Collection<E> asJavaCollection() {
        return this.list;
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return this.list;
    }

    @Override
    public BiDiIndexedMutIteratorW<E> iterator() {
        return new JavaBackedBiDiIndMutIterator<>(this.list.listIterator());
    }

    @Override
    public E first() {
        if (this.list.isEmpty())
            throw new NoSuchElementException();

        return this.list.get(0);
    }

    @Override
    public BiDiIndexedMutIteratorW<E> iterator(int index) {
        return new JavaBackedBiDiIndMutIterator<>(this.list.listIterator(index));
    }

    @Override
    public MutableListW<E> addAll(int index, CollectionW<? extends E> c) {
        int cindex = index;

        IteratorW<? extends E> iterator = c.iterator();

        while (iterator.hasNext()) {
            this.list.add(cindex, iterator.next());
            ++cindex;
        }

        return this;
    }

    @Override
    public E get(int index) {
        return this.list.get(index);
    }

    @Override
    public MutableListW<E> getEntry(int index) {
        return new JavaBackedMutListW<>(this.list.subList(index, index));
    }

    @Override
    public MutableListW<E> set(int index, E element) {
        this.list.set(index, element);
        return this;
    }

    @Override
    public MutableListW<E> add(int index, E element) {
        this.list.add(index, element);
        return this;
    }

    @Override
    public MutableListW<E> remove(int index) {
        this.list.remove(index);
        return this;
    }

    @Override
    public int indexOf(E o) {
        return this.list.indexOf(o);
    }

    @Override
    public int lastIndexOf(E o) {
        return this.list.lastIndexOf(o);
    }

    @Override
    public MutableListW<E> subList(int fromIndex, int toIndex) {
        return new JavaBackedMutListW<>(this.list.subList(fromIndex, toIndex));
    }

    @Override
    public MutableListW<E> replaceAll(UnaryOperator<E> operator) {
        this.list.replaceAll(operator);
        return this;
    }

    @Override
    public MutableListW<E> sorted(Comparator<? super E> c) {
        this.list.sort(c);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper) {
        List<R> list = (List<R>) factory.apply(Collections.emptyList());

        int index = 0;
        for (E e : this.list) {
            list.add(mapper.apply(index, e));
            ++index;
        }

        return new JavaBackedMutListW<>(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> MutableListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper) {
        MutableListW<R> left = new JavaBackedMutListW<>((List<R>) factory.apply(Collections.emptyList()));

        int index = 0;

        for (E e : this.list) {
            left.addAll(mapper.apply(index, e));
            ++index;
        }

        return left;
    }

    @Override
    public MutableListW<E> filterIndexed(IntObjBiPredicate<? super E> filter) {
        int index = 0;

        for (E e : this.list) {
            if (!filter.test(index, e))
                this.remove(e);
            ++index;
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MutableListW<E> copy() {
        return new JavaBackedMutListW<>((List<E>) factory.apply(this.list));
    }
}
