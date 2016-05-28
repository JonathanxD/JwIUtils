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
package com.github.jonathanxd.iutils.iterator;

import java.util.Iterator;
import java.util.List;

/**
 * Created by jonathan on 28/05/16.
 */
public class ListIterator<T> implements Iterator<T>, Cloneable {
    private final List<T> list;
    private int index;

    public ListIterator(List<T> list) {
        this.list = list;

    }

    @Override
    public T next() {
        return list.get(++index);
    }

    @Override
    public boolean hasNext() {
        return index + 1 < list.size();
    }

    @Override
    public void remove() {
        list.remove(index);
        --index;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public ListIterator<T> clone() {
        ListIterator<T> iterator = new ListIterator<>(list);
        iterator.index = this.index;
        return iterator;
    }

}
