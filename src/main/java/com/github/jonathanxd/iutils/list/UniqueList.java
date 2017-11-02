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
import java.util.List;

/**
 * An {@link AbstractPredicateWrappedList} that does not allow duplicated elements and does not
 * throw exceptions when trying to add duplicated elements.
 *
 * @param <E> Element type.
 */
public class UniqueList<E> extends AbstractPredicateWrappedList<E> {

    public UniqueList() {
        this(new ArrayList<>());
    }

    public UniqueList(List<E> list) {
        super(list);
    }

    @Override
    public boolean isAcceptable(E e) {
        return !this.getWrappedList().contains(e);
    }

    @Override
    public void onReject(E e) {
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new UniqueList<>(this.getWrappedList().subList(fromIndex, toIndex));
    }
}
