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

import com.github.jonathanxd.iutils.object.Lazy;

import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SuppliedWrapperListIterator<T> implements ListIterator<T> {

    private final Lazy<ListIterator<T>> lazy;

    public SuppliedWrapperListIterator(Supplier<ListIterator<T>> supplier) {
        this.lazy = Lazy.lazy(supplier);
    }

    private Lazy<ListIterator<T>> getLazy() {
        return this.lazy;
    }

    private ListIterator<T> getIterator() {
        return this.getLazy().get();
    }

    @Override
    public boolean hasNext() {
        return this.getIterator().hasNext();
    }

    @Override
    public T next() {
        return this.getIterator().next();
    }

    @Override
    public void remove() {
        this.getIterator().remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        this.getIterator().forEachRemaining(action);
    }

    @Override
    public boolean hasPrevious() {
        return this.getIterator().hasPrevious();
    }

    @Override
    public T previous() {
        return this.getIterator().previous();
    }

    @Override
    public int nextIndex() {
        return this.getIterator().nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.getIterator().previousIndex();
    }

    @Override
    public void set(T t) {
        this.getIterator().set(t);
    }

    @Override
    public void add(T t) {
        this.getIterator().add(t);
    }
}
