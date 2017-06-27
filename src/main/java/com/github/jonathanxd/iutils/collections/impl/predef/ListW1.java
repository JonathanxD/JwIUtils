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

import com.github.jonathanxd.iutils.collections.BiDiIndexedIteratorW;
import com.github.jonathanxd.iutils.collections.CollectionW;
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

public class ListW1<E> implements ListW<E> {

    private final E element;

    public ListW1(E element) {
        this.element = element;
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public Collection<E> asJavaCollection() {
        return Collections.singleton(this.element);
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator() {
        return new BiDiIteratorW1<>(this.element);
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator(int index) {
        return new BiDiIteratorW1<>(this.element, index);
    }

    @Override
    public ListW<E> addAll(int index, CollectionW<? extends E> c) {
        return null; // TODO
    }

    @Override
    public E get(int index) {
        this.checkIndex(index);

        return this.element;
    }

    @Override
    public ListW<E> getEntry(int index) {
        this.checkIndex(index);

        return new ListW1<>(this.element);
    }

    @Override
    public ListW<E> set(int index, E element) {
        this.checkIndex(index);

        return new ListW1<>(element);
    }

    @Override
    public ListW<E> add(int index, E element) {
        if (index != 0 && index != 1)
            throw new IndexOutOfBoundsException("Index must be 0 or 1, provided: " + index + ".");

        if (index == 0) new ListW2<>(element, this.element);

        return new ListW2<>(this.element, element);
    }

    @Override
    public ListW<E> remove(int index) {
        this.checkIndex(index);

        return ListW0.empty();
    }

    @Override
    public int indexOf(E o) {
        if (o.equals(this.element))
            return 0;

        return -1;
    }

    @Override
    public int lastIndexOf(E o) {
        if (o.equals(this.element))
            return 0;

        return -1;
    }

    @Override
    public ListW<E> subList(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == 1)
            return this;

        if (fromIndex == 0 && toIndex == 0)
            return ListW0.empty();

        throw new IndexOutOfBoundsException(
                String.format("Index must be between 0 (inclusive) and 1 (exclusive), Provided: (%d, %d).", fromIndex, toIndex));
    }

    @Override
    public ListW<E> replaceAll(UnaryOperator<E> operator) {
        return new ListW1<>(operator.apply(this.element));
    }

    @Override
    public ListW<E> sorted(Comparator<? super E> c) {
        return this;
    }

    @Override
    public <R> ListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper) {
        return new ListW1<>(mapper.apply(0, this.element));
    }

    @Override
    public <R> ListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper) {
        return null; // TODO
    }

    @Override
    public ListW<E> filterIndexed(IntObjBiPredicate<? super E> filter) {
        if (filter.test(0, this.element))
            return ListW0.empty();

        return this;
    }

    @Override
    public E first() {
        return this.element;
    }

    @Override
    public List<E> asJavaList() {
        return Collections.singletonList(this.element);
    }

    @Override
    public ListW<E> head() {
        return this;
    }

    @Override
    public E last() {
        return this.element;
    }

    @Override
    public ListW<E> tail() {
        return this;
    }

    @Override
    public ListW<E> prepend(E e) {
        return new ListW2<>(e, this.element);
    }

    @Override
    public ListW<E> prepend(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public ListW<E> append(E e) {
        return new ListW2<>(this.element, e);
    }

    @Override
    public ListW<E> append(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(E e) {
        return this.element.equals(e);
    }

    @Override
    public ListW<E> add(E e) {
        return new ListW1<>(e);
    }

    @Override
    public ListW<E> remove(E e) {
        if (this.element.equals(e))
            return ListW0.empty();

        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.all(this.element::equals);
    }

    @Override
    public ListW<E> addAll(CollectionW<? extends E> c) {
        return null; // TODO
    }

    @Override
    public ListW<E> removeAll(CollectionW<? extends E> c) {
        if (c.any(this.element::equals))
            return ListW0.empty();

        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> ListW<R> map(Function<? super E, ? extends R> mapper) {
        return new ListW1<>(mapper.apply(this.element));
    }

    @Override
    public <R> ListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        return null; // TODO
    }

    @Override
    public ListW<E> filter(Predicate<? super E> filter) {
        if (filter.test(this.element))
            return ListW0.empty();

        return this;
    }

    @Override
    public ListW<E> copy() {
        return this;
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
        return "[" + this.element + "]";
    }

    private void checkIndex(int index) {
        if (index != 0)
            throw new NoSuchElementException("Size: 1. Index: " + index);
    }
}
