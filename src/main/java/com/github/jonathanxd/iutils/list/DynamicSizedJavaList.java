/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.exception.LimitExceededException;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.IntSupplier;

/**
 * A Dynamic size limited implementation of {@link List}.
 *
 * Obs: Changing input list will break the size limit constraint, make sure to not modify the input
 * list.
 *
 * This version has an dynamic max size, which can be reduced or increased.
 *
 * Dynamically increasing or decreasing the size will not cause elements to be lost, but be careful,
 * this may result in inconsistent behaviors. The purpose of dynamic sized list is to support
 * sub-lists without having issues. For example:
 *
 * If you have a Sized List with max of {@code 10} elements, and 5 elements inside it, and then you
 * sub-list it {@code fromIndex} {@code 2} {@code toIndex} 5, you will have a list of 3 elements
 * with a max of {@code 5} elements because the wrapped sized supports more {@code 5} elements from
 * the moment as you created the sub-list, if you remove an element from original list, without a
 * dynamic sized list, the sub-list remains allowing {@code 5} elements instead of {@code 6}, with
 * dynamic sized list, the size will be dynamically changed as the original list changes.
 *
 * As the original list will be changed when sub-list is changed, this problem does not occurs in
 * original list, so, this list should only be used in very specific cases.
 *
 * As this is a special type of list which should be only used with care, the constructor is
 * protected.
 *
 * @param <E> Element type.
 */
public class DynamicSizedJavaList<E> implements List<E> {

    private final List<E> wrapped;
    private final IntSupplier maxSizeSupplier;

    protected DynamicSizedJavaList(List<E> wrapped, IntSupplier maxSizeSupplier) {
        int limit = maxSizeSupplier.getAsInt();

        if (wrapped.size() > limit)
            throw new LimitExceededException("This list only accepts " + limit + " elements and " + wrapped.size() + " elements was provided as initial elements and backing list.");

        this.wrapped = wrapped;
        this.maxSizeSupplier = maxSizeSupplier;
    }

    /**
     * Returns true if this sized list is full.
     *
     * @return True if this sized list is full.
     */
    public boolean isFull() {
        return this.maxSize() == this.size();
    }

    /**
     * Gets the max size of this list.
     *
     * @return Max size of this list.
     */
    public int maxSize() {
        return this.maxSizeSupplier.getAsInt();
    }

    @Override
    public int size() {
        return this.wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.wrapped.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.wrapped.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.wrapped.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.wrapped.toArray(a);
    }

    @Override
    public boolean add(E e) {
        this.checkSize(1);

        return this.wrapped.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.wrapped.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.wrapped.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        this.checkSize(c.size());

        return this.wrapped.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        this.checkSize(c.size());

        return this.wrapped.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.wrapped.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.wrapped.retainAll(c);
    }

    @Override
    public void clear() {
        this.wrapped.clear();
    }

    @Override
    public E get(int index) {
        return this.wrapped.get(index);
    }

    @Override
    public E set(int index, E element) {
        return this.wrapped.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        this.checkSize(1);
        this.wrapped.add(index, element);
    }

    @Override
    public E remove(int index) {
        return this.wrapped.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.wrapped.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.wrapped.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return IteratorUtil.addCheckListIterator(this.wrapped.listIterator(), () -> this.checkSize(1));
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return IteratorUtil.addCheckListIterator(this.wrapped.listIterator(index), () -> this.checkSize(1));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new DynamicSizedJavaList<>(this.wrapped.subList(fromIndex, toIndex),
                () -> this.maxSize() - this.size()
        );
    }

    private void checkSize(int elements) throws LimitExceededException {
        if (this.size() + elements >= this.maxSize())
            throw new LimitExceededException("Can't add " + elements + " elements to SizedList. Limit: " + this.maxSize() + ". Size: " + this.size() + ". Size before addition of elements: " + this.size() + elements + ".");
    }
}
