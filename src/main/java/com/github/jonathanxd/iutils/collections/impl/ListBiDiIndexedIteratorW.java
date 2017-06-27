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
package com.github.jonathanxd.iutils.collections.impl;

import com.github.jonathanxd.iutils.collections.BiDiIndexedIteratorW;
import com.github.jonathanxd.iutils.collections.ListW;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterator;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaListIterator;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListBiDiIndexedIteratorW<E> implements BiDiIndexedIteratorW<E> {
    private final ListW<E> listW;
    private int index = -1;

    public ListBiDiIndexedIteratorW(ListW<E> listW) {
        this(listW, 0);
    }

    public ListBiDiIndexedIteratorW(ListW<E> listW, int index) {
        this.listW = listW;
        this.index = index - 1;
    }

    @Override
    public Iterator<E> asJavaIterator() {
        return new WBackedJavaIterator<>(this);
    }

    @Override
    public boolean hasNext() {
        return this.index + 1 < this.listW.size();
    }

    @Override
    public E next() {
        if (!this.hasNext())
            throw new NoSuchElementException();

        return this.listW.get(++this.index);
    }

    @Override
    public boolean hasPrevious() {
        return this.index > -1;
    }

    @Override
    public E previous() {
        if (!this.hasPrevious())
            throw new NoSuchElementException();

        return this.listW.get(this.index--);
    }

    @Override
    public ListIterator<E> asJavaListIterator() {
        return new WBackedJavaListIterator<>(this);
    }

    @Override
    public int index() {
        return this.index;
    }

    @Override
    public BiDiIndexedIteratorW<E> copy() {

        ListBiDiIndexedIteratorW<E> w = new ListBiDiIndexedIteratorW<>(this.listW);

        w.index = this.index;

        return w;
    }
}
