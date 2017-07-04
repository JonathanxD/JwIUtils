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
package com.github.jonathanxd.iutils.collections.impl.mutable;

import com.github.jonathanxd.iutils.collections.BiDiIndexedIteratorW;
import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.ListW;
import com.github.jonathanxd.iutils.collections.impl.BiIteratorW;
import com.github.jonathanxd.iutils.collections.impl.BiSwitchingIteratorW;
import com.github.jonathanxd.iutils.collections.impl.java.JavaBackedIteratorW;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaCollection;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterable;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterator;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaList;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaListIterator;
import com.github.jonathanxd.iutils.collections.impl.predef.IteratorW1;
import com.github.jonathanxd.iutils.collections.mutable.BiDiIndexedMutIteratorW;
import com.github.jonathanxd.iutils.collections.mutable.MutableListW;
import com.github.jonathanxd.iutils.function.function.IntObjBiFunction;
import com.github.jonathanxd.iutils.function.predicate.IntObjBiPredicate;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * An linked implementation of {@link MutableListW}.
 *
 */
public class LinkedMutableListW<E> implements MutableListW<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;

    private LinkedMutableListW() {
        this.size = 0;
    }

    private LinkedMutableListW(int size, Node<E> first, Node<E> last) {
        this.size = size;
        this.first = first;
        this.last = last;
    }

    private LinkedMutableListW(E element) {
        this.size = 1;
        this.first = new Node<>(element, null);
        this.last = this.first;
    }

    public static <E> LinkedMutableListW<E> empty() {
        return new LinkedMutableListW<>();
    }

    public static <E> LinkedMutableListW<E> single(E element) {
        return new LinkedMutableListW<>(element);
    }

    public static <E> LinkedMutableListW<E> fromArray(E[] element) {
        JavaBackedIteratorW<E> iteratorW = new JavaBackedIteratorW<>(IteratorUtil.ofArray(element));

        return LinkedMutableListW.copy(element.length, iteratorW);
    }

    private static <E> LinkedMutableListW<E> append(LinkedMutableListW<E> head, CollectionW<? extends E> tail) {

        IteratorW<? extends E> iterator = tail.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            Node<E> node = new Node<>(next, null);

            if (head.last == null) {
                head.first = node;
                head.last = node;
            } else {
                head.last.next = node;
                head.last = node;
            }

            head.size++;
        }

        return head;
    }

    private static <E> LinkedMutableListW<E> append(LinkedMutableListW<E> head, E last) {

        Node<E> node = new Node<>(last, null);

        if (head.last == null) {
            head.first = node;
            head.last = node;
        } else {
            head.last.next = node;
            head.last = node;
        }

        head.size++;

        return head;
    }

    public static <E> LinkedMutableListW<E> fromCollection(CollectionW<? extends E> elements) {

        int size = elements.size();

        IteratorW<? extends E> iterator = elements.iterator();

        return LinkedMutableListW.copy(size, iterator);
    }

    private static <E> LinkedMutableListW<E> prepend(E firstElement, LinkedMutableListW<E> tail) {
        tail.size++;

        tail.first = new Node<>(firstElement, tail.first);

        return tail;
    }

    private static <E> LinkedMutableListW<E> copy(int size, IteratorW<? extends E> iterator) {
        Node<E> first = null;
        Node<E> current = null;
        Node<E> last = null;

        while (iterator.hasNext()) {
            E next = iterator.next();

            Node<E> node = new Node<>(next, null);

            if (first == null) {
                first = node;
                current = first;
            } else {
                current.setNext(node);
                current = node;
            }

            if (!iterator.hasNext())
                last = current;
        }

        return new LinkedMutableListW<>(size, first, last);
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public List<E> asJavaList() {
        return new WBackedJavaList<>(this);
    }

    @Override
    public Collection<E> asJavaCollection() {
        return new WBackedJavaCollection<>(this);
    }

    @Override
    public E first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.first.getValue();
    }

    @Override
    public MutableListW<E> head() {
        Node<E> copy = this.first;

        return new LinkedMutableListW<>(1, copy, null);
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.last.getValue();
    }

    @Override
    public MutableListW<E> tail() {

        if (this.isEmpty() || this.size() == 1)
            return LinkedMutableListW.empty();

        return subList(1, this.size());
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(E o) {
        return this.iterator().anyOfRemaining(o::equals);
    }

    @Override
    public MutableListW<E> prepend(E e) {
        return LinkedMutableListW.prepend(e, this);
    }

    @Override
    public MutableListW<E> prepend(CollectionW<? extends E> es) {
        es.forEach(this::prepend);

        return this;
    }

    @Override
    public MutableListW<E> append(E e) {
        return LinkedMutableListW.append(this, e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MutableListW<E> append(CollectionW<? extends E> es) {

        if (es instanceof LinkedMutableListW) {
            return ((LinkedMutableListW) es).prepend(this);
        }

        return LinkedMutableListW.append(this, es);
    }

    @Override
    public MutableListW<E> add(E e) {
        return LinkedMutableListW.append(this, e);
    }

    @Override
    public MutableListW<E> remove(E e) {

        if (this.first == null)
            return this;

        if (Objects.equals(this.first.getValue(), e)) {

            if (this.size == 1) {
                this.first = null;
                this.last = null;
                return this;
            }

            this.first = this.first.getNext();

            return this;
        }

        if (Objects.equals(this.last.getValue(), e)) {

            if (this.size == 1) {
                this.first = null;
                this.last = null;

                return this;
            }

            Node<E> n = this.first;

            while (n.next != this.last) {
                n = n.next;
            }

            this.last = n;

            return this;
        }

        Node<E> prev = null;
        Node<E> current = this.first;

        while (current != null) {

            if (prev != null) {
                if (Objects.equals(current.value, e)) {
                    prev.next = current.next;
                    return this;
                }
            }

            prev = current;
            current = current.next;
        }

        return this;
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.iterator().allOfRemaining(this::contains);
    }

    @Override
    public MutableListW<E> addAll(CollectionW<? extends E> c) {
        return LinkedMutableListW.append(this, c);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MutableListW<E> removeAll(CollectionW<? extends E> c) {
        return this.filter(e -> !((CollectionW<E>) c).contains(e));
    }

    @Override
    public <R> MutableListW<R> map(Function<? super E, ? extends R> mapper) {
        return this.mapIndexed((i, value) -> mapper.apply(value));
    }

    @Override
    public <R> MutableListW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        return this.flatMapIndexed((i, value) -> mapper.apply(value));
    }

    @Override
    public MutableListW<E> filter(Predicate<? super E> filter) {
        return this.filterIndexed((i, value) -> filter.test(value));
    }

    @Override
    public void clear() {
        this.first = null;
        this.last = null;
    }

    @Override
    public BiDiIndexedMutIteratorW<E> iterator() {
        return new LinkedIterator<>(this.first);
    }

    @Override
    public BiDiIndexedMutIteratorW<E> iterator(int index) {
        if (index > this.size() || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Size: " + this.size() + ".");

        return new LinkedIterator<>(this.first, index);
    }

    @Override
    public MutableListW<E> addAll(int index, CollectionW<? extends E> c) {
        if (index == this.size())
            return this.append(c);

        if (index > this.size() || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Size: " + this.size() + ".");

        int cIndex = index;

        IteratorW<? extends E> iterator = c.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            this.add(cIndex, next);
            ++cIndex;
        }

        return this;
    }

    @Override
    public E get(int index) {
        if (index >= this.size() || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Size: " + this.size() + ".");

        BiDiIndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E elem = iterator.next();

            if (iterator.index() == index)
                return elem;
        }

        throw new IndexOutOfBoundsException("Size: " + this.size() + ". Index: " + index + ".");
    }

    @Override
    public MutableListW<E> getEntry(int index) {
        if (index >= this.size() || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Size: " + this.size() + ".");

        return this.subList(index, index);
    }

    @Override
    public MutableListW<E> set(int index, E element) {
        if (index >= this.size() || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Size: " + this.size() + ".");

        return this.mapIndexed((i, value) -> i == index ? element : value);
    }

    @Override
    public MutableListW<E> add(int index, E element) {
        if (index == this.size())
            return this.append(element);

        if (index > this.size() || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Size: " + this.size() + ".");

        int pos = 0;
        Node<E> prev = null;
        Node<E> current = this.first;

        while (current != null) {

            if (pos == index) {
                Node<E> next = current.next;

                if (prev != null) {
                    prev.next = new Node<>(element, next);
                } else {
                    this.first = new Node<>(element, next);
                }

                return this;

            }

            ++pos;
            prev = current;
            current = current.next;
        }

        return this;
    }

    @Override
    public MutableListW<E> remove(int index) {
        return this.filterIndexed((i, e) -> i != index);
    }

    @Override
    public int indexOf(E o) {
        BiDiIndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E elem = iterator.next();

            if (elem.equals(o))
                return iterator.index();
        }

        return -1;
    }

    @Override
    public int lastIndexOf(E o) {
        BiDiIndexedIteratorW<E> iterator = this.iterator();
        int index = -1;

        while (iterator.hasNext()) {
            E elem = iterator.next();

            if (elem.equals(o))
                index = iterator.index();
        }

        return index;
    }

    @Override
    public MutableListW<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > this.size())
            throw new IndexOutOfBoundsException("From Index: " + fromIndex + ". Size: " + this.size() + ".");

        if (toIndex < 0 || toIndex > this.size())
            throw new IndexOutOfBoundsException("From Index: " + fromIndex + ". Size: " + this.size() + ".");

        int pos = 0;
        Node<E> first = null;
        Node<E> current = this.first;
        Node<E> last = null;

        while (current != null) {

            if (pos < fromIndex) {
                ++pos;
                continue;
            }

            if (first == null) {
                first = current;
            }

            if (pos + 1 == toIndex) {
                last = current;
                break;
            }

            current = current.next;
            ++pos;
        }

        if (last == null)
            last = this.last;

        return new LinkedMutableListW<>(toIndex - fromIndex, first, last);
    }

    @Override
    public MutableListW<E> replaceAll(UnaryOperator<E> operator) {
        return this.map(operator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MutableListW<E> sorted(Comparator<? super E> c) {
        Object[] array = new Object[this.size()];

        this.forEachIndexed((i, e) -> array[i] = e);

        Arrays.sort(array);

        this.clear();

        LinkedMutableListW<E> w = (LinkedMutableListW<E>) LinkedMutableListW.fromArray(array);

        return this.addAll(w);
    }

    @Override
    public <R> MutableListW<R> mapIndexed(IntObjBiFunction<? super E, ? extends R> mapper) {

        Node<R> first = null;
        Node<R> current = null;
        Node<R> last = null;

        BiDiIndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            R next = mapper.apply(iterator.index(), iterator.next());

            Node<R> node = new Node<>(next, null);

            if (first == null) {
                first = node;
                current = first;
            } else {
                current.setNext(node);
                current = node;
            }

            if (!iterator.hasNext())
                last = current;

        }

        return new LinkedMutableListW<>(this.size, first, last);
    }

    @Override
    public <R> MutableListW<R> flatMapIndexed(IntObjBiFunction<? super E, ? extends CollectionW<? extends R>> mapper) {
        MutableListW<R> left = LinkedMutableListW.empty();

        BiDiIndexedIteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            CollectionW<? extends R> right = mapper.apply(iterator.index(), iterator.next());
            left = left.addAll(right);

        }

        return left;
    }

    @Override
    public MutableListW<E> filterIndexed(IntObjBiPredicate<? super E> filter) {
        int index = 0;

        Node<E> prev = null;
        Node<E> current = this.first;

        while (current != null) {

                if (!filter.test(index, current.getValue())) {
                    if (prev != null) {
                        prev.next = current.next;
                    } else {
                        first = current.next;
                    }
                }

            prev = current;
            current = current.next;
            ++index;
        }


        return this;
    }

    @Override
    public MutableListW<E> copy() {
        return copy(this.size(), this.iterator());
    }

    @Override
    public String toString() {
        IteratorW<E> it = this.iterator();

        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (; ; ) {

            E e = it.next();

            sb.append(e == this ? "(this Collection)" : e);

            if (!it.hasNext()) {
                return sb.append(']').toString();
            }

            sb.append(',').append(' ');
        }
    }

    static class Node<E> {
        E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }

        public E getValue() {
            return this.value;
        }

        public Node<E> getNext() {
            return this.next;
        }

        void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> copy() {
            return new Node<>(this.getValue(), this.getNext());
        }
    }

    static class Holder<E> {
        private Holder<E> prev;
        private Node<E> next;

        Holder(Holder<E> prev, Node<E> next) {
            this.prev = prev;
            this.next = next;
        }

        public Node<E> getNext() {
            return this.next;
        }

        void setNext(Node<E> next) {
            this.next = next;
        }

        public Holder<E> getPrev() {
            return this.prev;
        }

        void setPrev(Holder<E> prev) {
            this.prev = prev;
        }

    }

    static class LinkedIterator<E> implements BiDiIndexedMutIteratorW<E> {

        private int index = -1;
        private Holder<E> holder;

        LinkedIterator(Node<E> node) {
            this(node, 0);
        }

        LinkedIterator(Node<E> node, int index) {
            this(new Holder<>(null, node), index);
        }

        LinkedIterator(Holder<E> node, int index) {
            this.holder = node;

            for (int i = 0; i < index; ++i)
                this.next();
        }

        @Override
        public boolean hasPrevious() {
            return this.holder.getPrev() != null;
        }

        @Override
        public E previous() {
            if (!this.hasPrevious())
                throw new NoSuchElementException();

            this.index--;

            Holder<E> prevHolder = this.holder.getPrev();

            Node<E> prev = prevHolder.getNext();

            this.holder = new Holder<>(prevHolder.getPrev(), prev);

            return prev.getValue();
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
        public boolean hasNext() {
            return this.holder.getNext() != null;
        }

        @Override
        public E next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            this.index++;

            Node<E> next = this.holder.getNext();

            this.holder = new Holder<>(this.holder, next.getNext());

            return next.getValue();
        }

        @Override
        public BiDiIndexedMutIteratorW<E> copy() {
            return new LinkedIterator<>(this.holder, this.index);
        }

        @Override
        public void remove() {
            holder.prev.next.next = holder.next;
        }

        @Override
        public int index() {
            return this.index;
        }
    }

}
