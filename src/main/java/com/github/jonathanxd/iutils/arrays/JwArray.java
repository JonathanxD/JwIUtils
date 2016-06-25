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
package com.github.jonathanxd.iutils.arrays;

import com.github.jonathanxd.iutils.iterator.BackableIterator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * JwArray IS NOT A LIST or COLLECTION, JwArray is an Array representation
 */
public class JwArray<E> implements Iterable<E>, Comparable<E[]>, Cloneable {

    /**
     * Max Array Size
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Initial Array Size
     */
    private static int INITIAL_SIZE = 12;

    /**
     * Array values
     */
    private E[] values;

    /**
     * Array Size
     */
    private int arraySize = 0;

    /**
     * Cached Iterator
     */
    private Iter ix = null;

    /**
     * Create Empty Generic Array
     */
    @SuppressWarnings("unchecked")
    public JwArray() {
        this.values = (E[]) new Object[INITIAL_SIZE];
    }

    /**
     * Create Empty Array of Specified Type.
     */
    @SuppressWarnings("unchecked")
    public JwArray(Class<?> clazz) {
        this.values = (E[]) Array.newInstance(clazz, INITIAL_SIZE);
    }

    /**
     * Create array of
     * @param values
     */
    @SafeVarargs
    public JwArray(E... values) {
        this(values[0].getClass());
        for (E value : values) {
            this.addInternal(value);
        }
    }

    public JwArray(Collection<? extends E> collection) {
        super();
        collection.forEach(this::addInternal);
    }

    public JwArray(List<? extends E> list) {
        super();
        list.forEach(this::addInternal);
    }

    public JwArray(Iterable<? extends E> iterable) {
        super();
        iterable.forEach(this::addInternal);
    }

    public JwArray(Enumeration<? extends E> enume) {
        super();
        while (enume.hasMoreElements()) {
            this.addInternal(enume.nextElement());
        }
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    public static <E> JwArray<E> ofG(E[] values) {
        Objects.requireNonNull(values);
        return new JwArray<>(values);
    }

    @SafeVarargs
    public static <E> JwArray<E> of(E... values) {
        return new JwArray<>(values);
    }

    @SafeVarargs
    public static <E> E[] genericOf(E... values) {
        return new JwArray<>(values).toGenericArray();
    }

    @SuppressWarnings("unchecked")
    public static <T> JwArray<T> empty() {
        return (JwArray<T>) ImmutableJwArray.EMPTY;
    }

    private void removeElementInternal(E element) {
        removeElementInternal(element, true);
    }

    private void removeElementInternalObject(Object element) {
        removeElementInternalObject(element, true);
    }

    private void removeElementInternal(E element, boolean recursive) {
        int len = (recursive ?
                values.length - findInternal(element)
                : values.length - 1);

        if (len < INITIAL_SIZE) {
            len = values.length;

        }

        @SuppressWarnings("unchecked")
        E[] arrayCp = (E[]) Array.newInstance(values.getClass().getComponentType(), len);

        int l = 0;
        boolean found = false;

        final int currentArraySize = arraySize;

        for (int x = 0; x < currentArraySize; ++x) {
            E e = values[x];

            if (!e.equals(element) || (!recursive && found)) {
                arrayCp[l] = e;
                ++l;
            } else {
                found = true;
                if (!recursive)
                    break;
                --arraySize;
            }
        }

        values = arrayCp;
    }

    private void removeElementInternalObject(Object element, boolean recursive) {
        int len = (recursive ?
                values.length - findInternalObject(element)
                : values.length - 1);

        if (len < INITIAL_SIZE) {
            len = values.length;

        }

        @SuppressWarnings("unchecked")
        E[] arrayCp = (E[]) Array.newInstance(values.getClass().getComponentType(), len);

        int l = 0;
        boolean found = false;

        final int currentArraySize = arraySize;

        for (int x = 0; x < currentArraySize; ++x) {
            E e = values[x];

            if (!e.equals(element) || (!recursive && found)) {
                arrayCp[l] = e;
                ++l;
            } else {
                found = true;
                if (!recursive)
                    break;
                --arraySize;
            }
        }

        values = arrayCp;
    }

    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - values.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int oldCapacity = values.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        values = java.util.Arrays.copyOf(values, newCapacity);
    }

    public int findInternal(E element) {
        Objects.requireNonNull(element);
        int found = 0;

        for (int x = 0; x < arraySize; ++x) {
            if (values[x].equals(element)) {
                ++found;
            }
        }

        return found;
    }

    public int findInternalObject(Object element) {
        Objects.requireNonNull(element);
        int found = 0;

        for (int x = 0; x < arraySize; ++x) {
            if (values[x].equals(element)) {
                ++found;
            }
        }

        return found;
    }

    private void addInternal(E value) {
        ensureExplicitCapacity(arraySize + 1);
        arraySize += 1;
        values[arraySize - 1] = value;
    }

    public JwArray<E> add(E value) {

        addInternal(value);

        return this;
    }

    public JwArray<E> addAll(E[] value) {
        for (E e : value) {
            add(e);
        }
        return this;
    }

    public E get(int index) {
        return values[index];
    }

    public E remove(int index) {
        E element = values[index];
        removeIndexInternal(index);
        return element;
    }

    private void removeIndexInternal(int index) {

        int numMoved = arraySize - index - 1;

        if (numMoved > 0)
            System.arraycopy(values, index + 1, values, index,
                    numMoved);

        values[--arraySize] = null;
    }

    public JwArray<E> remove(E value) {
        Objects.requireNonNull(value);
        removeElementInternal(value);

        for (int x = 0; x < arraySize; x++) {
            if (value.equals(values[x])) {
                remove(x);
            }
        }
        return this;
    }

    public JwArray<E> removeObject(Object value) {
        Objects.requireNonNull(value);
        removeElementInternalObject(value);

        for (int x = 0; x < arraySize; x++) {
            if (value.equals(values[x])) {
                remove(x);
            }
        }
        return this;
    }

    public JwArray<E> set(E value, int index) {
        Objects.requireNonNull(value);
        if (index >= arraySize)
            throw new IndexOutOfBoundsException("Set '" + value.getClass().getName() + "'. Index '" + index + "'. Length '" + arraySize + "'");
        values[index] = value;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <U> JwArray<U> map(Function<E, U> function) {

        U[] array = (U[]) new Object[values.length];

        for (int i = 0; i < values.length; i++) {
            array[i] = function.apply(values[i]);
        }

        return new JwArray<U>(array);
    }

    public Collection<E> toCollection() {
        return new JwArray.ArrayList<>(values);
    }

    public java.util.ArrayList<E> toArrayList() {
        return new java.util.ArrayList<E>(toCollection());
    }

    public List<E> toList() {
        return new JwArray.ArrayList<>(toGenericArray());
    }

    public ArraysAbstractList<E> toArraysList() {
        return new JwArray.ArrayList<>(toGenericArray());
    }

    public E[] toGenericArray() {
        return java.util.Arrays.copyOf(values, arraySize);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toGenericArrayOf(T[] t) {
        if (t.length < arraySize)
            return (T[]) java.util.Arrays.copyOf(values, arraySize, t.getClass());
        System.arraycopy(values, 0, t, 0, arraySize);
        if (t.length > arraySize)
            t[arraySize] = null;
        return t;

    }

    public <T> T[] toGenericArray(Class<? extends T[]> t) {
        return java.util.Arrays.copyOf(values, arraySize, t);
    }

    public E getFirst() {
        return (arraySize > 0 ? values[0] : null);
    }

    public E getLast() {
        return (arraySize > 0 ? values[arraySize - 1] : null);
    }

    public int getFirstEqualsIndex(E elem) {
        for (int x = 0; x < arraySize; ++x) {
            if (values[x].equals(elem))
                return x;
        }
        return -1;
    }

    /**
     * Copy elements from {@code array} to this
     * @param array Array
     * @return This Array
     */
    public JwArray<E> copy(JwArray<E> array) {
        for (E e : array) {
            add(e);
        }

        return this;
    }

    public int length() {
        return this.arraySize;
    }

    public boolean isEmpty() {
        return length() <= 0;
    }

    @Override
    public String toString() {

        StringJoiner sj = new StringJoiner(", ", "[", "]");

        for (E e : this) {
            sj.add(String.valueOf(e));
        }

        return sj.toString();
    }

    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public boolean contains(E o) {
        if (length() < 1) return false;
        return compare(o) == 1;
    }

    @Override
    public BackableIterator<E> iterator() {
        if (ix != null) {
            ix.reset();
            return ix;
        }
        return ix = new Iter();
    }

    public int compare(E o) {
        int v = arraySize - 1;

        if (v > -1 && values[v].equals(o)) {
            return 1;
        }

        int k = v / 2;

        if (k > -1 && k != v && values[k].equals(o)) {
            return 1;
        }

        for (int x = 0; x < v; ++x) {
            if (values[x].equals(o)) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * Returns -1 if the arrays size not equals Returns 0  if the arrays not equals Returns 1  if
     * arrays equals
     */
    @Override
    public int compareTo(E[] o) {
        if (o.length != arraySize) {
            return -1;
        }

        int v = arraySize - 1;

        if (v > -1 && values[v].equals(o[v])) {
            return 1;
        }

        int k = v / 2;

        if (k > -1 && k != v && values[k].equals(o[k])) {
            return 1;
        }

        for (int x = 0; x < v; ++x) {
            if (values[x].equals(o[x])) {
                return 1;
            }
        }

        return 0;
    }

    @Override
    public JwArray<E> clone() {
        JwArray<E> jwArray = new JwArray<>();
        jwArray.addAll(this.values);
        return jwArray;
    }

    private static class ArrayList<E> extends ArraysAbstractList<E> implements RandomAccess, java.io.Serializable {
        private static final long serialVersionUID = -2764017481108945198L;
        private final E[] a;

        @SafeVarargs
        ArrayList(E... array) {
            a = Objects.requireNonNull(array);
        }

        @Override
        public int size() {
            return a.length;
        }

        @Override
        public Object[] toArray() {
            return a.clone();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            int size = size();
            if (a.length < size)
                return java.util.Arrays.copyOf(this.a, size, (Class<? extends T[]>) a.getClass());
            System.arraycopy(this.a, 0, a, 0, size);
            if (a.length > size)
                a[size] = null;
            return a;
        }

        @Override
        public E get(int index) {
            return a[index];
        }

        @Override
        public E set(int index, E element) {
            E oldValue = a[index];
            a[index] = element;
            return oldValue;
        }

        @Override
        public int indexOf(Object o) {
            E[] a = this.a;
            if (o == null) {
                for (int i = 0; i < a.length; i++)
                    if (a[i] == null)
                        return i;
            } else {
                for (int i = 0; i < a.length; i++)
                    if (o.equals(a[i]))
                        return i;
            }
            return -1;
        }

        @Override
        public boolean contains(Object o) {
            return indexOf(o) != -1;
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.spliterator(a, Spliterator.ORDERED);
        }

        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            for (E e : a) {
                action.accept(e);
            }
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
            E[] a = this.a;
            for (int i = 0; i < a.length; i++) {
                a[i] = operator.apply(a[i]);
            }
        }

        @Override
        public void sort(Comparator<? super E> c) {
            java.util.Arrays.sort(a, c);
        }

        @Override
        public JwArray<E> toArrays() {
            return new JwArray<>(a);
        }
    }

    private class Iter implements BackableIterator<E> {

        int current = -1;

        @Override
        public boolean hasNext() {
            return (current + 1 < arraySize);
        }

        @Override
        public E next() {
            ++current;
            return values[current];
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            while (hasNext())
                action.accept(next());
        }

        protected final void reset() {
            current = -1;
        }

        @Override
        public boolean hasBack() {
            return current - 1 > -1;
        }

        @Override
        public E back() {
            --current;
            return values[current];
        }

        @Override
        public int getIndex() {
            return current;
        }
    }

}
