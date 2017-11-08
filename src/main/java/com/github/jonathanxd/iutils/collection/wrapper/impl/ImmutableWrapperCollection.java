/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.collection.wrapper.impl;

import com.github.jonathanxd.iutils.collection.immutable.ImmutableCollection;
import com.github.jonathanxd.iutils.collection.wrapper.WrapperCollection;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import java.util.Collection;
import java.util.Iterator;

public abstract class ImmutableWrapperCollection<E> implements ImmutableCollection<E>, WrapperCollection<E> {

    @Override
    public int size() {
        return this.getWrapped().size();
    }

    @Override
    public boolean isEmpty() {
        return this.getWrapped().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.getWrapped().contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return IteratorUtil.immutableIterator(this.getWrapped().iterator());
    }

    @Override
    public Object[] toArray() {
        return this.getWrapped().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.getWrapped().toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.getWrapped().containsAll(c);
    }

    protected abstract Collection<E> getWrapped();
}
