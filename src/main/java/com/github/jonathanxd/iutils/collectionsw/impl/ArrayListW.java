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
package com.github.jonathanxd.iutils.collectionsw.impl;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.collectionsw.BiDiIndexedIteratorW;
import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.collectionsw.ListW;
import com.github.jonathanxd.iutils.collectionsw.impl.builder.ListWBuilder;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaIterable;
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
 * This is an {@link ListW} backing to an Array, this implementation wraps an {@link ArrayList} and
 * delegate operations.
 */
public class ArrayListW<E> implements ListW<E> {

    private final List<E> arrayList;
    private final List<E> unmod;
    private final Iterable<E> iterable;

    private ArrayListW(List<E> arrayList) {
        this.arrayList = arrayList;
        this.iterable = new WBackedJavaIterable<>(this);
        this.unmod = Collections.unmodifiableList(arrayList);
    }

    public ArrayListW() {
        this(new ArrayList<>());
    }

    public ArrayListW(int capacity) {
        this(new ArrayList<>(capacity));
    }

    public ArrayListW(CollectionW<? extends E> elements) {
        this(elements.size());
        elements.forEach(this.arrayList::add);
    }


    @Override
    public Iterable<E> asJavaIterable() {
        return this.iterable;
    }

    @Override
    public List<E> asJavaList() {
        return this.unmod;
    }

    @Override
    public Collection<E> asJavaCollection() {
        return this.unmod;
    }

    @Override
    public E first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.get(0);
    }

    @Override
    public ListW<E> head() {
        return this.isEmpty() ? new ArrayListW<>() : new ArrayListW<>(Collections3.listOf(this.first()));
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.get(this.size() - 1);
    }

    @Override
    public ListW<E> tail() {
        return this.isEmpty() ? new ArrayListW<>() : new ArrayListW<>(this.subList(1, this.size()));
    }

    @Override
    public int size() {
        return this.arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.arrayList.isEmpty();
    }

    @Override
    public boolean contains(E o) {
        return this.arrayList.contains(o);
    }

    @Override
    public ListW<E> prepend(E e) {
        ArrayList<E> list = new ArrayList<>(1 + this.size());

        list.add(e);
        list.addAll(this.arrayList);

        return new ArrayListW<>(list);
    }

    @Override
    public ListW<E> prepend(CollectionW<? extends E> es) {
        ArrayList<E> list = new ArrayList<>(es.size() + this.size());

        es.forEach(list::add);
        list.addAll(this.arrayList);

        return new ArrayListW<>(list);
    }

    @Override
    public ListW<E> append(E e) {
        ArrayList<E> list = new ArrayList<>(this.size() + 1);

        list.addAll(this.arrayList);
        list.add(e);

        return new ArrayListW<>(list);
    }

    @Override
    public ListW<E> append(CollectionW<? extends E> es) {
        ArrayList<E> list = new ArrayList<>(this.size() + es.size());

        list.addAll(this.arrayList);
        es.forEach(list::add);

        return new ArrayListW<>(list);
    }

    @Override
    public ListW<E> add(E e) {
        return this.append(e);
    }

    @Override
    public ListW<E> remove(E e) {
        ArrayList<E> es = new ArrayList<>(arrayList);

        es.remove(e);

        return new ArrayListW<>(es);
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.all(this.arrayList::contains);
    }

    @Override
    public ListW<E> addAll(CollectionW<? extends E> c) {
        return this.append(c);
    }

    @Override
    public ListW<E> removeAll(CollectionW<? extends E> c) {
        ArrayList<E> es = new ArrayList<>(this.arrayList);

        c.forEach(es::remove);

        return new ArrayListW<>(es);
    }

    @Override
    public <R> ListW<R> map(Function<? super E, ? extends R> mapper) {
        ArrayList<R> es = new ArrayList<>(this.arrayList.size());

        for (E e : this.arrayList) {
            es.add(mapper.apply(e));
        }

        return new ArrayListW<>(es);
    }

    @Override
    public <R> ListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        ArrayList<R> es = new ArrayList<>(this.arrayList.size());

        for (E e : this.arrayList) {
            mapper.apply(e).forEach(es::add);
        }

        return new ArrayListW<>(es);
    }

    @Override
    public ListW<E> filter(Predicate<? super E> filter) {
        ArrayList<E> es = new ArrayList<>(this.arrayList.size());

        for (E e : this.arrayList) {
            if (filter.test(e))
                es.add(e);
        }

        return new ArrayListW<>(es);
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator() {
        return new ListBiDiIndexedIteratorW<>(this);
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator(int index) {
        if (index < 0 || index > this.size())
            throw new IndexOutOfBoundsException("Size: " + this.size() + ". Index: " + index + ".");

        return new ListBiDiIndexedIteratorW<>(this, index);
    }

    @Override
    public ListW<E> addAll(int index, CollectionW<? extends E> c) {
        ArrayList<E> es = new ArrayList<>(this.arrayList.size() + c.size());

        es.addAll(this.arrayList);
        es.addAll(index, c.asJavaCollection());

        return new ArrayListW<>(es);
    }

    @Override
    public E get(int index) {
        return this.arrayList.get(index);
    }

    @Override
    public ListW<E> getEntry(int index) {
        return new ArrayListW<>(Collections3.listOf(this.get(index)));
    }

    @Override
    public ListW<E> set(int index, E element) {
        ArrayList<E> es = new ArrayList<>(this.arrayList);

        es.set(index, element);

        return new ArrayListW<>(es);
    }

    @Override
    public ListW<E> add(int index, E element) {
        ArrayList<E> es = new ArrayList<>(this.arrayList);

        es.add(index, element);

        return new ArrayListW<>(es);
    }

    @Override
    public ListW<E> remove(int index) {
        ArrayList<E> es = new ArrayList<>(this.arrayList);

        es.remove(index);

        return new ArrayListW<>(es);
    }

    @Override
    public int indexOf(E o) {
        return this.arrayList.indexOf(o);
    }

    @Override
    public int lastIndexOf(E o) {
        return this.arrayList.lastIndexOf(o);
    }

    @Override
    public ListW<E> subList(int fromIndex, int toIndex) {
        return new ArrayListW<>(this.arrayList.subList(fromIndex, toIndex));
    }

    @Override
    public ListW<E> replaceAll(UnaryOperator<E> operator) {
        ArrayList<E> es = new ArrayList<>(this.arrayList);

        es.replaceAll(operator);

        return new ArrayListW<>(es);
    }

    @Override
    public ListW<E> sorted(Comparator<? super E> c) {
        ArrayList<E> es = new ArrayList<>(this.arrayList);

        es.sort(c);

        return new ArrayListW<>(es);
    }

    @Override
    public <R> ListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper) {
        ArrayList<R> es = new ArrayList<>(this.arrayList.size());

        int index = 0;
        for (E e : this.arrayList) {
            es.add(mapper.apply(index, e));
            ++index;
        }

        return new ArrayListW<>(es);
    }

    @Override
    public <R> ListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper) {
        ArrayList<R> es = new ArrayList<>(this.arrayList.size());

        int index = 0;
        for (E e : this.arrayList) {
            mapper.apply(index, e).forEach(es::add);
            ++index;
        }

        return new ArrayListW<>(es);
    }

    @Override
    public ListW<E> filterIndexed(IntObjBiPredicate<? super E> filter) {
        ArrayList<E> es = new ArrayList<>(this.arrayList.size());

        int index = 0;

        for (E e : this.arrayList) {
            if (filter.test(index, e))
                es.add(e);
            ++index;
        }

        return new ArrayListW<>(es);
    }

    @Override
    public ListW<E> copy() {
        return new ArrayListW<>(new ArrayList<>(this.arrayList));
    }

    @Override
    public Builder<E> builder() {
        return ListWBuilder.builder(this);
    }
}
