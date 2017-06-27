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

public class ListW0<E> implements ListW<E> {

    private static final ListW0<?> EMPTY = new ListW0<>();

    private ListW0() {
    }

    @SuppressWarnings("unchecked")
    public static <E> ListW0<E> empty() {
        return (ListW0<E>) EMPTY;
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public Collection<E> asJavaCollection() {
        return Collections.emptyList();
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator() {
        return BiDiIteratorW0.empty();
    }

    @Override
    public BiDiIndexedIteratorW<E> iterator(int index) {
        if (index != 0)
            throw new IndexOutOfBoundsException("Empty list. Index: " + index + ".");

        return BiDiIteratorW0.empty();
    }

    @Override
    public ListW<E> addAll(int index, CollectionW<? extends E> c) {
        return null; // TODO
    }

    @Override
    public E get(int index) {
        throw new IndexOutOfBoundsException("Empty list. Index: " + index + ".");
    }

    @Override
    public ListW<E> getEntry(int index) {
        return this;
    }

    @Override
    public ListW<E> set(int index, E element) {
        throw new IndexOutOfBoundsException("Empty list. Index: " + index + ".");
    }

    @Override
    public ListW<E> add(int index, E element) {
        if (index == 0)
            return null; // new ListW1(element);
        return null;
    }

    @Override
    public ListW<E> remove(int index) {
        throw new IndexOutOfBoundsException("Empty list. Index: " + index + ".");
    }

    @Override
    public int indexOf(E o) {
        return -1;
    }

    @Override
    public int lastIndexOf(E o) {
        return -1;
    }

    @Override
    public ListW<E> subList(int fromIndex, int toIndex) {
        throw new IndexOutOfBoundsException("Empty list. From index: " + fromIndex + ". To index: " + toIndex + ".");
    }

    @Override
    public ListW<E> replaceAll(UnaryOperator<E> operator) {
        return this;
    }

    @Override
    public ListW<E> sorted(Comparator<? super E> c) {
        return this;
    }

    @Override
    public <R> ListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper) {
        return ListW0.empty();
    }

    @Override
    public <R> ListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper) {
        return ListW0.empty();
    }

    @Override
    public ListW<E> filterIndexed(IntObjBiPredicate<? super E> filter) {
        return this;
    }

    @Override
    public E first() {
        throw new NoSuchElementException();
    }

    @Override
    public List<E> asJavaList() {
        return Collections.emptyList();
    }

    @Override
    public ListW<E> head() {
        return this;
    }

    @Override
    public E last() {
        throw new NoSuchElementException();
    }

    @Override
    public ListW<E> tail() {
        return this;
    }

    @Override
    public ListW<E> prepend(E e) {
        return new ListW1<>(e);
    }

    @Override
    public ListW<E> prepend(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public ListW<E> append(E e) {
        return new ListW1<>(e);
    }

    @Override
    public ListW<E> append(CollectionW<? extends E> es) {
        return null; // TODO
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(E o) {
        return false;
    }

    @Override
    public ListW<E> add(E e) {
        return new ListW1<>(e);
    }

    @Override
    public ListW<E> remove(E e) {
        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return false;
    }

    @Override
    public ListW<E> addAll(CollectionW<? extends E> c) {
        return null; // TODO
    }

    @Override
    public ListW<E> removeAll(CollectionW<? extends E> c) {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> ListW<R> map(Function<? super E, ? extends R> mapper) {
        return (ListW<R>) this;
    }

    @Override
    public <R> ListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        return ListW0.empty();
    }

    @Override
    public ListW<E> filter(Predicate<? super E> filter) {
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
        return "[]";
    }
}
