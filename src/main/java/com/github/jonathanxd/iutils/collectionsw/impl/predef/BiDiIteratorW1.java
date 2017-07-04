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
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaIterator;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaListIterator;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BiDiIteratorW1<E> extends IteratorW1<E> implements BiDiIndexedIteratorW<E> {

    public BiDiIteratorW1(E element) {
        this(element, 0);
    }

    public BiDiIteratorW1(E element, int index) {
        super(element);

        if (index > 0)
            this.hasNext = false;
    }

    @Override
    public ListIterator<E> asJavaListIterator() {
        return new WBackedJavaListIterator<>(this);
    }

    @Override
    public Iterator<E> asJavaIterator() {
        return new WBackedJavaIterator<>(this);
    }

    @Override
    public int index() {
        return this.hasNext ? -1 : 0;
    }

    @Override
    public boolean hasPrevious() {
        return !this.hasNext();
    }

    @Override
    public E previous() {
        if (!this.hasPrevious())
            throw new NoSuchElementException();

        this.hasNext = true;

        return this.element;
    }

    @Override
    public BiDiIndexedIteratorW<E> copy() {
        BiDiIteratorW1<E> iteratorW1 = new BiDiIteratorW1<>(this.element);

        iteratorW1.hasNext = this.hasNext;

        return iteratorW1;
    }
}
