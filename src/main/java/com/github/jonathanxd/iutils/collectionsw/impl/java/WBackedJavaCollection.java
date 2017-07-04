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
import com.github.jonathanxd.iutils.collectionsw.impl.MutationOperationOnImmutableData;

import java.util.Collection;
import java.util.Iterator;

public final class WBackedJavaCollection<E> implements Collection<E> {

    private final CollectionW<E> wrapped;

    public WBackedJavaCollection(CollectionW<E> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int size() {
        return this.wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return this.wrapped.contains((E) o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.wrapped.asJavaIterable().iterator();
    }

    @Override
    public Object[] toArray() {
        Object[] o = new Object[this.size()];

        int i = 0;

        for (E e : this) {
            o[i] = e;
            i++;
        }

        return o;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {

        int i = 0;

        for (E e : this) {
            a[i] = (T) e;
            i++;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        throw new MutationOperationOnImmutableData();
    }

    @Override
    public boolean remove(Object o) {
        throw new MutationOperationOnImmutableData();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if(!this.contains(o))
                return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new MutationOperationOnImmutableData();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new MutationOperationOnImmutableData();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new MutationOperationOnImmutableData();
    }

    @Override
    public void clear() {
        throw new MutationOperationOnImmutableData();
    }
}
