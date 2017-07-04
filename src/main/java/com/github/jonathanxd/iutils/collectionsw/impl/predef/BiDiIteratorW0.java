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
package com.github.jonathanxd.iutils.collectionsw.impl.predef;

import com.github.jonathanxd.iutils.collectionsw.BiDiIndexedIteratorW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaListIterator;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BiDiIteratorW0<E> extends IteratorW0<E> implements BiDiIndexedIteratorW<E> {

    private static final BiDiIteratorW0<?> EMPTY = new BiDiIteratorW0<>();

    protected BiDiIteratorW0() {
    }

    @SuppressWarnings("unchecked")
    public static <E> BiDiIteratorW0<E> empty() {
        return (BiDiIteratorW0<E>) EMPTY;
    }

    @Override
    public ListIterator<E> asJavaListIterator() {
        return new WBackedJavaListIterator<>(this);
    }

    @Override
    public int index() {
        return -1;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public E previous() {
        throw new NoSuchElementException();
    }

    @Override
    public BiDiIndexedIteratorW<E> copy() {
        return this;
    }
}
