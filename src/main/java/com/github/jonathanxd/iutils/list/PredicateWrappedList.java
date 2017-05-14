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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A {@link PredicateList} backed by a {@link List}.
 *
 * @param <E> Element type.
 */
public class PredicateWrappedList<E> implements PredicateList<E> {

    private final List<E> list;

    /**
     * Predicate to test elements to add.
     */
    private final Predicate<E> predicate;

    /**
     * Creates a predicate array list.
     *
     * @param list      List to add values.
     * @param predicate Predicate to test elements to add.
     */
    public PredicateWrappedList(List<E> list, Predicate<E> predicate) {
        this.list = list;
        this.predicate = predicate;
    }

    /**
     * Creates {@link PredicateWrappedList} backing to a new {@link java.util.ArrayList}.
     *
     * @param predicate Predicate to test elements to add.
     */
    public PredicateWrappedList(Predicate<E> predicate) {
        this(new ArrayList<>(), predicate);
    }

    @Override
    public int size() {
        return this.getWrappedList().size();
    }

    @Override
    public boolean isEmpty() {
        return this.getWrappedList().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.getWrappedList().contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.getWrappedList().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.getWrappedList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.getWrappedList().toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (!this.isAcceptable(e)) {
            this.onReject(e);
        } else {
            return this.getWrappedList().add(e);
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {
        return this.getWrappedList().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.getWrappedList().containsAll(c);
    }

    @Override
    public void add(int index, E element) {
        if (!this.isAcceptable(element)) {
            this.onReject(element);
        } else {
            this.getWrappedList().add(index, element);
        }
    }

    @Override
    public E remove(int index) {
        return this.getWrappedList().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.getWrappedList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.getWrappedList().lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new PredicateWrappedListIterator<>(this, this.getWrappedList().listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new PredicateWrappedListIterator<>(this, this.getWrappedList().listIterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new PredicateArrayList<>(this.getWrappedList().subList(fromIndex, toIndex), this.predicate);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        Collection<E> toAdd = new ArrayList<>();

        for (E e : c) {
            if (this.isAcceptable(e)) {
                toAdd.add(e);
            } else {
                this.onReject(e);
            }
        }


        return this.getWrappedList().addAll(toAdd);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Collection<E> toAdd = new ArrayList<>();

        for (E e : c) {
            if (this.isAcceptable(e)) {
                toAdd.add(e);
            } else {
                this.onReject(e);
            }
        }


        return this.getWrappedList().addAll(index, toAdd);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.getWrappedList().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.getWrappedList().retainAll(c);
    }

    @Override
    public E set(int index, E element) {
        if (!this.isAcceptable(element)) {
            this.onReject(element);
        } else {
            this.getWrappedList().set(index, element);
        }

        return null;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        this.getWrappedList().replaceAll(new PredicateListUnaryOperator<>(this, operator));
    }

    @Override
    public void clear() {
        this.getWrappedList().clear();
    }

    @Override
    public E get(int index) {
        return this.getWrappedList().get(index);
    }


    protected List<E> getWrappedList() {
        return this.list;
    }

    @Override
    public boolean isAcceptable(E e) {
        return predicate.test(e);
    }

    @Override
    public void onReject(E e) {
        throw new IllegalArgumentException("Cannot accept element '" + e + "'");
    }

}
