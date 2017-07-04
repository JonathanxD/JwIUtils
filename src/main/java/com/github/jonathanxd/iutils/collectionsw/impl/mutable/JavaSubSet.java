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
package com.github.jonathanxd.iutils.collectionsw.impl.mutable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class JavaSubSet<E> implements Set<E> {

    private final Set<E> parent;
    private final int fromIndex;
    private int size;

    public JavaSubSet(Set<E> parent, int fromIndex, int toIndex) {
        this.parent = parent;
        this.fromIndex = fromIndex;
        this.size = toIndex - fromIndex;
    }

    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E e : this) {
            if (Objects.equals(e, o))
                return true;
        }

        return false;
    }

    @Override
    public boolean add(E e) {
        boolean r = this.parent.add(e);

        if (r)
            this.size++;

        return r;
    }

    public boolean remove(Object o) {
        boolean result = this.parent.remove(o);
        if (result)
            this.size--;
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o))
                return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int oldSize = this.parent.size();
        boolean r = this.parent.addAll(c);

        if (r) {
            int newSize = this.parent.size();
            this.size += newSize - oldSize;
        }

        return r;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.removeIf(next -> !c.contains(next));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.removeIf(c::contains);
    }

    @Override
    public void clear() {
        Iterator<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    public Iterator<E> iterator() {
        SubSetIterator iter = new SubSetIterator(this.parent.iterator());
        iter.skip(this.fromIndex);
        return iter;
    }

    @Override
    public Object[] toArray() {
        Object[] o = new Object[this.size()];

        int index = 0;
        for (E e : this) {
            o[index] = e;
            ++index;
        }

        return o;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {

        int index = 0;
        for (E e : this) {
            a[index] = (T) e;
            ++index;
        }

        return a;
    }

    private class SubSetIterator implements Iterator<E> {

        private final Iterator<E> iterator;
        private int at = 0;

        private SubSetIterator(Iterator<E> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return this.at != JavaSubSet.this.size;
        }

        @Override
        public E next() {
            return this.iterator.next();
        }

        @Override
        public void remove() {
            this.iterator.remove();
            JavaSubSet.this.size--;
        }

        void skip(int n) {
            int pos = 0;

            while (pos != n) {
                this.iterator.next();
            }
        }
    }


}
