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
package com.github.jonathanxd.iutils.collections.impl.predef;

import com.github.jonathanxd.iutils.collection.CollectionUtils;
import com.github.jonathanxd.iutils.collections.BiDiIndexedIteratorW;
import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.ListW;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterable;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ListW2<E> implements ListW<E> {

    private final E element1;
    private final E element2;

    public ListW2(E element1, E element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public Collection<E> asJavaCollection() {
        return Collections.unmodifiableCollection(CollectionUtils.listOf(this.element1, this.element2));
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator() {
        return new BiDiIteratorW2<>(this.element1, this.element2);
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator(int index) {
        return new BiDiIteratorW2<>(this.element1, this.element2, index);
    }

    @Override
    public ListW<E> addAll(int index, CollectionW<? extends E> c) {
        return null; // TODO
    }

    @Override
    public E get(int index) {
        this.checkIndex(index);

        if (index == 0)
            return this.element1;

        return this.element2;
    }

    @Override
    public ListW<E> getEntry(int index) {
        this.checkIndex(index);

        if (index == 0)
            return new ListW1<>(this.element1);

        return new ListW1<>(this.element2);
    }

    @Override
    public ListW<E> set(int index, E element) {
        this.checkIndex(index);

        if (index == 0)
            return new ListW2<>(element, this.element2);

        return new ListW2<>(this.element1, element);
    }

    @Override
    public ListW<E> add(int index, E element) {
        if (index < 0 && index > 2)
            throw new IndexOutOfBoundsException("Index must be 0, 1 or 2, provided: " + index + ".");

        // if(index == 0) new ListW2<>(element, this.element)
        // if(index == 1) new ListW2<>(this.element, element)

        return null; // new ListW2(this.element, element);
    }

    @Override
    public ListW<E> remove(int index) {
        this.checkIndex(index);

        if (index == 1)
            return new ListW1<>(this.element1);

        return new ListW1<>(this.element2);
    }

    @Override
    public int indexOf(E o) {
        if (o.equals(this.element1))
            return 0;

        if (o.equals(this.element2))
            return 1;

        return -1;
    }

    @Override
    public int lastIndexOf(E o) {
        int index = -1;

        if (o.equals(this.element1))
            index = 0;

        if (o.equals(this.element2))
            index = 1;

        return index;
    }

    @Override
    public ListW<E> subList(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == 2)
            return this;

        if (fromIndex == 0 && toIndex == 1)
            return new ListW1<>(this.element1);

        if (fromIndex == 1 && toIndex == 2)
            return new ListW1<>(this.element2);

        if (fromIndex == 0 && toIndex == 0)
            return ListW0.empty();

        throw new IndexOutOfBoundsException(
                String.format("Index must be between 0 (inclusive) and 2 (exclusive), Provided: (%d, %d).", fromIndex, toIndex));
    }

    @Override
    public ListW<E> replaceAll(UnaryOperator<E> operator) {
        return new ListW2<>(operator.apply(this.element1), operator.apply(this.element2));
    }

    @Override
    public ListW<E> sorted(Comparator<? super E> c) {
        if (c.compare(this.element1, this.element2) < 0)
            return new ListW2<>(this.element2, this.element1);

        return this;
    }

    @Override
    public <R> ListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper) {
        return new ListW2<>(mapper.apply(0, this.element1), mapper.apply(1, this.element2));
    }

    @Override
    public <R> ListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper) {
        return null; // TODO
    }

    @Override
    public ListW<E> filterIndexed(IntObjBiPredicate<? super E> filter) {
        boolean filter1 = filter.test(0, this.element1);
        boolean filter2 = filter.test(1, this.element2);

        if (filter1 && !filter2)
            return new ListW1<>(this.element1);

        if (!filter1 && filter2)
            return new ListW1<>(this.element2);

        if (filter1 /*&& filter2*/)
            return this;

        return ListW0.empty();
    }

    @Override
    public E first() {
        return this.element1;
    }

    @Override
    public List<E> asJavaList() {
        return Collections.unmodifiableList(CollectionUtils.listOf(this.element1, this.element2));
    }

    @Override
    public ListW<E> head() {
        return new ListW1<>(this.element1);
    }

    @Override
    public E last() {
        return this.element2;
    }

    @Override
    public ListW<E> tail() {
        return new ListW1<>(this.element2);
    }

    @Override
    public ListW<E> prepend(E e) {
        return null; // TODO
    }

    @Override
    public ListW<E> prepend(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public ListW<E> append(E e) {
        return null; // TODO
    }

    @Override
    public ListW<E> append(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(E e) {
        return this.element1.equals(e) || this.element2.equals(e);
    }

    @Override
    public ListW<E> add(E e) {
        return null;// new CollectionW1<>(e); -> ListW1(e)
    }

    @Override
    public ListW<E> remove(E e) {
        boolean e1Eq = this.element1.equals(e);
        boolean e2Eq = this.element2.equals(e);

        if (!e1Eq && e2Eq)
            return new ListW1<>(this.element1);

        if (e1Eq && !e2Eq)
            return new ListW1<>(this.element2);

        if (e1Eq /*&& e2Eq*/)
            return this;

        return ListW0.empty();
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.all(o -> this.element1.equals(o) || this.element2.equals(o));
    }

    @Override
    public ListW<E> addAll(CollectionW<? extends E> c) {
        return null; // TODO
    }

    @Override
    public ListW<E> removeAll(CollectionW<? extends E> c) {
        boolean contains1 = false;
        boolean contains2 = false;

        IteratorW<? extends E> iterator = c.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (element1.equals(next))
                contains1 = true;

            if (element2.equals(next))
                contains2 = true;
        }

        if (!contains1 && contains2)
            return new ListW1<>(this.element1);

        if (contains1 && !contains2)
            return new ListW1<>(this.element2);

        if (contains1 /*&& contains2*/)
            return this;

        return ListW0.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> ListW<R> map(Function<? super E, ? extends R> mapper) {
        return new ListW2<>(mapper.apply(this.element1), mapper.apply(this.element2));
    }

    @Override
    public <R> ListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        return null; // TODO
    }

    @Override
    public ListW<E> filter(Predicate<? super E> filter) {
        boolean filter1 = filter.test(this.element1);
        boolean filter2 = filter.test(this.element2);

        if (filter1 && !filter2)
            return new ListW1<>(this.element1);

        if (!filter1 && filter2)
            return new ListW1<>(this.element2);

        if (filter1 /*&& filter2*/)
            return this;

        return ListW0.empty();
    }

    @Override
    public ListW<E> copy() {
        return new ListW2<>(this.element1, this.element2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListW<?>)
            return ((ListW) obj).isEmpty();

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // Singleton
    }

    @Override
    public String toString() {
        return "[" + this.element1 + ", " + this.element2 + "]";
    }

    private void checkIndex(int index) {
        if (index < 0 || index > 2)
            throw new NoSuchElementException("Size: 2. Index: " + index);
    }
}
