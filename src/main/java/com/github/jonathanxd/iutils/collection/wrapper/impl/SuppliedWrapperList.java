/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.collection.wrapper.impl;

import com.github.jonathanxd.iutils.collection.wrapper.WrapperList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;

public class SuppliedWrapperList<T> implements WrapperList<T> {

    private final Supplier<List<T>> supplier;

    public SuppliedWrapperList(Supplier<List<T>> supplier) {
        this.supplier = supplier;
    }

    private Supplier<List<T>> getSupplier() {
        return this.supplier;
    }

    private List<T> getList() {
        return this.getSupplier().get();
    }

    @Override
    public int size() {
        return this.getList().size();
    }

    @Override
    public boolean isEmpty() {
        return this.getList().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.getList().contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new SuppliedWrapperIterator<>(this.getList()::iterator);
    }

    @Override
    public Object[] toArray() {
        return this.getList().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return this.getList().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return this.getList().add(t);
    }

    @Override
    public boolean remove(Object o) {
        return this.getList().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.getList().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.getList().addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.getList().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.getList().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.getList().retainAll(c);
    }

    @Override
    public void clear() {
        this.getList().clear();
    }

    @Override
    public T get(int index) {
        return this.getList().get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.getList().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.getList().add(index, element);
    }

    @Override
    public T remove(int index) {
        return this.getList().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.getList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.getList().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new SuppliedWrapperListIterator<>(this.getList()::listIterator);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new SuppliedWrapperListIterator<>(() -> this.getList().listIterator(index));
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.getList().subList(fromIndex, toIndex);
    }

    @Override
    public int hashCode() {
        return this.getList().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getList().equals(obj);
    }

    @Override
    public String toString() {
        return this.getList().toString();
    }
}
