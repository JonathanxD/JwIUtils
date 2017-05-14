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
package com.github.jonathanxd.iutils.collection.view;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ViewList<E, Y> extends AbstractViewCollection<E, Y> implements List<Y> {

    private final Predicate<Y> add;
    private final Predicate<Object> remove;

    /**
     * Synthetic list iterable to emulate a iterable of element of type {@link Y}.
     */
    private final ViewUtils.ListIterable<Y> syntheticIterable;

    /**
     * Constructs a list view.
     *
     * @param list   List to wrap.
     * @param mapper Mapper.
     * @param add    Add operation handler. First argument is index (negative is undefined), the
     *               second is the value.
     * @param remove Add operation handler. First argument is index (negative is undefined), the
     *               second is the value (null for undefined).
     */
    public ViewList(List<E> list, BiFunction<E, ListIterator<E>, ListIterator<Y>> mapper,
                    Predicate<Y> add,
                    Predicate<Object> remove) {
        super(list);

        Objects.requireNonNull(add);
        Objects.requireNonNull(remove);

        this.add = add;
        this.remove = remove;
        this.syntheticIterable = ViewUtils.listIterable(list, mapper);
    }


    @Override
    public boolean addAll(int index, Collection<? extends Y> c) {
        int offset = 0;

        for (Y y : c) {
            int realIndex = index + offset;

            this.add(realIndex, y);

            offset++;
        }

        return false;
    }

    @Override
    public Y get(int index) {

        int loc = 0;

        for (Y y : this.getSyntheticIterable()) {
            if (loc == index)
                return y;
            loc++;
        }

        return null;
    }

    @Override
    public Y set(int index, Y element) {
        ListIterator<Y> iter = this.getSyntheticIterable().iterator(index);
        Y old = iter.next();
        iter.set(element);
        return old;
    }

    @Override
    public void add(int index, Y element) {
        this.getSyntheticIterable().iterator(index).add(element);
    }

    @Override
    public Y remove(int index) {
        ListIterator<Y> iterator = this.getSyntheticIterable().iterator(index);

        Y old = iterator.next();

        iterator.remove();

        return old;
    }

    @Override
    public int indexOf(Object o) {
        int loc = 0;

        for (Y y : this.getSyntheticIterable()) {
            if (y.equals(o))
                return loc;
            loc++;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int loc = 0;
        int last = -1;

        for (Y y : this.getSyntheticIterable()) {
            if (y.equals(o))
                last = loc;
            loc++;
        }

        return last;
    }

    @Override
    public ListIterator<Y> listIterator() {
        return this.getSyntheticIterable().iterator();
    }

    @Override
    public ListIterator<Y> listIterator(int index) {
        return this.getSyntheticIterable().iterator(index);
    }

    @Override
    public List<Y> subList(int fromIndex, int toIndex) {
        return new SubList(this, 0, fromIndex, toIndex);
    }

    @Override
    public boolean add(Y y) {
        return this.add.test(y);
    }

    @Override
    public boolean remove(Object o) {
        return this.remove.test(o);
    }

    @Override
    public ViewUtils.ListIterable<Y> getSyntheticIterable() {
        return this.syntheticIterable;
    }

    /**
     * From {@link ArrayList.SubList}
     */
    class SubList extends AbstractList<Y> {
        private final List<Y> parent;
        private final int fromIndex;
        private final int offset;
        private int size;

        SubList(List<Y> parent, int offset, int fromIndex, int toIndex) {
            this.parent = parent;
            this.fromIndex = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
        }

        public Y set(int index, Y e) {

            return this.parent.set(offset + index, e);
        }

        public Y get(int index) {
            return this.parent.get(offset + index);
        }

        public int size() {
            return this.size;
        }

        public void add(int index, Y e) {
            this.parent.add(fromIndex + index, e);
            this.size++;
        }

        public Y remove(int index) {
            Y result = this.parent.remove(fromIndex + index);
            this.size--;
            return result;
        }

        public boolean addAll(Collection<? extends Y> c) {
            return this.addAll(this.size, c);
        }

        public boolean addAll(int index, Collection<? extends Y> c) {
            int sz = c.size();

            if (sz == 0)
                return false;

            this.parent.addAll(fromIndex + index, c);

            this.size += sz;

            return true;
        }

        public Iterator<Y> iterator() {
            return listIterator();
        }

        public ListIterator<Y> listIterator(final int index) {

            final int offset = this.offset;

            return new ListIterator<Y>() {
                int at = index;
                int lastRet = -1;

                public boolean hasNext() {
                    return this.at != SubList.this.size;
                }

                public Y next() {
                    int i = this.at;

                    if (i >= SubList.this.size)
                        throw new NoSuchElementException();

                    at = i + 1;

                    return SubList.this.parent.get(offset + (lastRet = i));
                }

                public boolean hasPrevious() {
                    return at != 0;
                }

                public Y previous() {

                    int i = at - 1;

                    if (i < 0)
                        throw new NoSuchElementException();

                    at = i;

                    return SubList.this.parent.get(offset + (lastRet = i));
                }

                public void forEachRemaining(Consumer<? super Y> consumer) {

                    final int size = SubList.this.size;

                    int i = at;

                    if (i >= size) {
                        return;
                    }

                    while (i != size) {
                        consumer.accept(SubList.this.parent.get(offset + (i++)));
                    }

                    lastRet = at = i;
                }

                public int nextIndex() {
                    return at;
                }

                public int previousIndex() {
                    return at - 1;
                }

                public void remove() {
                    if (lastRet < 0)
                        throw new IllegalStateException();

                    SubList.this.remove(lastRet);
                    at = lastRet;
                    lastRet = -1;
                }

                public void set(Y e) {

                    if (lastRet < 0)
                        throw new IllegalStateException();

                    SubList.this.parent.set(offset + lastRet, e);

                }

                public void add(Y e) {
                    int i = at;

                    SubList.this.add(i, e);

                    at = i + 1;

                    lastRet = -1;
                }

            };
        }

        public List<Y> subList(int fromIndex, int toIndex) {
            return new SubList(this, offset, fromIndex, toIndex);
        }

    }

}
