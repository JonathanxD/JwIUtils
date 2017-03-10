/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

/**
 * Abstract implementation of {@link PredicateList}.
 *
 * @param <E> Element type.
 */
public abstract class AbstractPredicateList<E> extends ArrayList<E> implements PredicateList<E> {

    public AbstractPredicateList(int initialCapacity) {
        super(initialCapacity);
    }

    public AbstractPredicateList() {
        super();
    }

    public AbstractPredicateList(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e);
        }
    }

    @Override
    public boolean add(E e) {
        if (!this.isAcceptable(e)) {
            onReject(e);
        } else {
            return super.add(e);
        }

        return false;
    }

    @Override
    public void add(int index, E element) {
        if (!this.isAcceptable(element)) {
            onReject(element);
        } else {
            super.add(index, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        Collection<E> toAdd = new ArrayList<>();

        for (E e : c) {
            if (this.isAcceptable(e)) {
                toAdd.add(e);
            } else {
                onReject(e);
            }
        }


        return super.addAll(toAdd);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Collection<E> toAdd = new ArrayList<>();

        for (E e : c) {
            if (this.isAcceptable(e)) {
                toAdd.add(e);
            } else {
                onReject(e);
            }
        }


        return super.addAll(index, toAdd);
    }

    @Override
    public E set(int index, E element) {
        if (!this.isAcceptable(element)) {
            onReject(element);
        } else {
            super.set(index, element);
        }

        return null;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        super.replaceAll(new PredicateListUnaryOperator<>(this, operator));
    }

    @Override
    public ListIterator<E> listIterator() {
        return new PredicateWrappedListIterator<>(this, super.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new PredicateWrappedListIterator<>(this, super.listIterator(index));
    }

}
