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
package com.github.jonathanxd.iutils.collections.impl.java;

import com.github.jonathanxd.iutils.collections.ListW;
import com.github.jonathanxd.iutils.collections.impl.MutationOperationOnImmutableData;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class WBackedJavaList<E> extends WBackedAbstractJavaCollection<E> implements List<E> {

    private final ListW<E> wrapped;

    public WBackedJavaList(ListW<E> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ListW<E> getWrapped() {
        return this.wrapped;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new MutationOperationOnImmutableData();
    }

    @Override
    public E get(int index) {
        return this.wrapped.get(index);
    }

    @Override
    public E set(int index, E element) {
        throw new MutationOperationOnImmutableData();
    }

    @Override
    public void add(int index, E element) {
        throw new MutationOperationOnImmutableData();
    }

    @Override
    public E remove(int index) {
        throw new MutationOperationOnImmutableData();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int indexOf(Object o) {
        return this.wrapped.indexOf((E) o);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int lastIndexOf(Object o) {
        return this.wrapped.lastIndexOf((E) o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new WBackedJavaListIterator<>(this.wrapped.iterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new WBackedJavaListIterator<>(this.wrapped.iterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new WBackedJavaList<>(this.wrapped.subList(fromIndex, toIndex));
    }
}
