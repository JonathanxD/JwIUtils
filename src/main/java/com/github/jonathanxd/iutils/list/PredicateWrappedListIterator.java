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
package com.github.jonathanxd.iutils.list;

import java.util.ListIterator;

public class PredicateWrappedListIterator<E> implements ListIterator<E> {

    private final PredicateList<E> predicateList;
    private final ListIterator<E> wrapped;

    public PredicateWrappedListIterator(PredicateList<E> predicateList, ListIterator<E> wrapped) {
        this.predicateList = predicateList;
        this.wrapped = wrapped;
    }

    @Override
    public boolean hasNext() {
        return this.getWrappedListIterator().hasNext();
    }

    @Override
    public E next() {
        return this.getWrappedListIterator().next();
    }

    @Override
    public boolean hasPrevious() {
        return this.getWrappedListIterator().hasPrevious();
    }

    @Override
    public E previous() {
        return this.getWrappedListIterator().previous();
    }

    @Override
    public int nextIndex() {
        return this.getWrappedListIterator().nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.getWrappedListIterator().previousIndex();
    }

    @Override
    public void remove() {
        this.getWrappedListIterator().remove();
    }

    @Override
    public void set(E e) {
        if (!this.getPredicateList().isAcceptable(e)) {
            this.getPredicateList().onReject(e);
        } else {
            this.getWrappedListIterator().set(e);
        }
    }

    @Override
    public void add(E e) {
        if (!this.getPredicateList().isAcceptable(e)) {
            this.getPredicateList().onReject(e);
        } else {
            this.getWrappedListIterator().add(e);
        }
    }

    protected PredicateList<E> getPredicateList() {
        return this.predicateList;
    }

    protected ListIterator<E> getWrappedListIterator() {
        return wrapped;
    }


}
