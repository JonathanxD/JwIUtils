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
package com.github.jonathanxd.iutils.list;

import com.github.jonathanxd.iutils.container.BaseContainer;
import com.github.jonathanxd.iutils.container.MutableContainer;
import com.github.jonathanxd.iutils.container.list.IndexedListContainer;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;

/**
 * A list with a predefined size. This size is constant and cannot be changed. This List only holds
 * this amount of elements.
 *
 * @param <T> Type of elements.
 */
public class StaticList<T> implements IndexedListContainer<T>, Iterable<T> {

    /**
     * Size of the list.
     */
    private final int size;

    /**
     * Value array with all values.
     */
    private final T[] values;

    /**
     * Type of elements.
     */
    private final Class<? extends T> clazz;

    /**
     * Cache the empty slots for a fast-add function.
     */
    private int slotCache = 0;


    @SuppressWarnings("unchecked")
    StaticList(Class<? extends T> clazz, int size) {
        this.size = size;
        this.clazz = clazz;
        this.values = (T[]) Array.newInstance(clazz, this.size);
    }

    /**
     * Creates a {@link StaticList} with predefined size.
     *
     * @param elementType Type of elements of the list.
     * @param size        Predefined size of the list.
     * @param <T>         Type of elements.
     * @return {@link StaticList}.
     */
    public static <T> StaticList<T> createStaticListOf(final Class<T> elementType, final int size) {
        if (size <= 0) {
            throw new RuntimeException("Cannot create a static list with 0 or negative size!");
        }
        return new StaticList<>(Objects.requireNonNull(elementType), size);
    }

    /**
     * Gets an array with all values in this {@link StaticList}.
     *
     * @return Array with all values in this {@link StaticList}.
     */
    @SuppressWarnings("unchecked")
    public T[] getValues() {
        T[] dest = (T[]) Array.newInstance(clazz, this.size());
        System.arraycopy(values, 0, dest, 0, this.size());
        return dest;
    }

    @Override
    public boolean add(T element) {
        int caching = this.nextEmptySlot();

        if (caching != -1) {
            this.values[caching] = element;
            this.nextEmptySlot();
            return true;
        }

        return false;
    }

    @Override
    public BaseContainer<T> holdAndAdd(T element) {
        int caching = this.nextEmptySlot();

        if (caching == -1) {
            caching = this.size() - 1;
        }

        T oldValue = this.values[caching];
        this.values[caching] = element;
        this.nextEmptySlot();
        return oldValue != null ? MutableContainer.of(oldValue) : MutableContainer.empty();
    }

    @Override
    public boolean remove(T element) {
        for (int x = 0; x < this.size(); ++x) {
            if (this.values[x] != null && values[x].equals(element)) {
                this.values[x] = null;
                this.slotCache = x;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.emptySlots() == this.values.length;
    }

    @Override
    public int emptySlots() {
        int empty = 0;
        for (int x = 0; x < size; ++x) {
            if (this.values[x] == null) {
                ++empty;
            }
        }
        return empty;
    }

    @Override
    public boolean isFull() {
        return this.nextEmptySlot() == -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean add(int index, T element) {
        this.checkIndex(index);

        if (this.values[index] == null) {
            this.values[index] = element;
            this.nextEmptySlot();
            return true;
        }
        return false;
    }

    @Override
    public BaseContainer<T> holdAndAdd(int index, T element) {
        this.checkIndex(index);

        T old = this.values[index];
        this.values[index] = element;
        this.nextEmptySlot();

        return (old == null ? MutableContainer.empty() : MutableContainer.of(old));
    }

    @Override
    public boolean remove(int index) {
        this.checkIndex(index);

        if (this.values[index] == null) {
            return false;
        }
        this.values[index] = null;
        this.slotCache = index;
        return true;
    }

    @Override
    public BaseContainer<T> holdAndRemove(int index) {
        this.checkIndex(index);

        if (this.values[index] == null) {
            return MutableContainer.empty();
        }

        T old = values[index];
        this.values[index] = null;
        this.slotCache = index;
        return MutableContainer.of(old);
    }

    @Override
    public boolean hasEmptySlot() {
        return this.nextEmptySlot() != -1;
    }

    @Override
    public int nextEmptySlot() {
        if (this.slotCache > -1 && this.slotCache < this.values.length) {
            if (this.values[this.slotCache] == null) {
                return this.slotCache;
            }
        }

        for (int x = 0; x < this.values.length; ++x) {
            if (this.values[x] == null) {
                this.slotCache = x;
                return x;
            }
        }

        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    public String toString() {
        Iterator<T> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (; ; ) {

            T e = it.next();

            sb.append(e == this ? "(this Collection)" : e);

            if (!it.hasNext()) {
                sb.append(']');
                sb.append(". Data: [Size: ").append(this.size()).append(", Empty Slots: ").append(this.emptySlots()).append("]");
                return sb.toString();
            }

            sb.append(',').append(' ');
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException("Element[" + this.toString() + "]. Suggested index: " + index);
        }
    }

    @Override
    public boolean contains(T element) {
        for (int x = 0; x < this.size(); ++x) {
            if (this.values[x] != null && this.values[x].equals(element)) {
                return true;
            } else if (this.values[x] == null) {
                this.nextEmptySlot();
            }
        }
        return false;
    }

    /**
     * Indexer
     */
    @Override
    public BaseContainer<T> get(int index) {
        checkIndex(index);

        if (this.values[index] == null) {
            updateCache(index);
            return MutableContainer.empty();
        }
        return MutableContainer.of(this.values[index]);
    }

    private void updateCache(int index) {
        if (this.values[slotCache] != null && this.values[index] == null) {
            this.slotCache = index;
        }
    }

    public int getSlotCache() {
        return this.slotCache;
    }

    private final class Iter implements Iterator<T> {

        private int index = -1;

        @Override
        public boolean hasNext() {
            return (index + 1) < StaticList.this.size();
        }

        @Override
        public T next() {
            return StaticList.this.values[++index];
        }

    }
}
