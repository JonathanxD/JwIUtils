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
package com.github.jonathanxd.iutils.collections.impl;

import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.EntryW;
import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.MapW;
import com.github.jonathanxd.iutils.collections.SetW;
import com.github.jonathanxd.iutils.collections.impl.java.JavaBackedIteratorW;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaCollection;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterable;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaIterator;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaMap;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaMapEntry;
import com.github.jonathanxd.iutils.collections.impl.java.WBackedJavaSet;
import com.github.jonathanxd.iutils.function.function.PairFunction;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A linked {@link MapW}.
 *
 * {@link java.util.LinkedHashMap}
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class LinkedMapW<K, V> implements MapW<K, V> {

    private final int size;
    LinkedEntry<K, V> first;
    LinkedEntry<K, V> last;


    LinkedMapW() {
        this.size = 0;
    }

    private LinkedMapW(int size, LinkedEntry<K, V> first, LinkedEntry<K, V> last) {
        this.size = size;
        this.first = first;
        this.last = last;
    }

    LinkedMapW(K key, V value) {
        this.size = 1;
        this.first = new LinkedEntry<>(key, value, null);
        this.last = this.first;
    }

    public static <K, V> LinkedMapW<K, V> empty() {
        return new LinkedMapW<>();
    }

    public static <K, V> LinkedMapW<K, V> single(K key, V value) {
        return new LinkedMapW<>(key, value);
    }

    @SafeVarargs
    public static <K, V> LinkedMapW<K, V> fromEntries(EntryW<K, V>... elements) {
        JavaBackedIteratorW<EntryW<K, V>> iteratorW = new JavaBackedIteratorW<>(IteratorUtil.ofArray(elements));

        return copy(elements.length, iteratorW);
    }

    public static <K, V> LinkedMapW<K, V> fromCollection(CollectionW<EntryW<K, V>> elements) {
        IteratorW<EntryW<K, V>> iterator = elements.iterator();

        return copy(elements.size(), iterator);
    }

    private static <K, V> LinkedMapW<K, V> append(LinkedMapW<K, V> head, MapW<? extends K, ? extends V> tail) {
        return prependMap(head, tail);
    }

    private static <K, V> LinkedMapW<K, V> append(LinkedMapW<K, V> head, K lastKey, V lastValue) {
        return prependMap(head, new LinkedMapW<>(lastKey, lastValue));
    }

    public static <K, V> LinkedMapW<K, V> fromMap(MapW<? extends K, ? extends V> elements) {
        return prependMap(elements, null);
    }

    private static <K, V> LinkedMapW<K, V> prepend(K firstKey, V firstValue, LinkedMapW<K, V> tail) {
        int size = tail.size() + 1;
        LinkedEntry<K, V> first = new LinkedEntry<>(firstKey, firstValue, tail.first);
        LinkedEntry<K, V> last = tail.last;

        return new LinkedMapW<>(size, first, last);
    }

    @SuppressWarnings("unchecked")
    private static <K, V> LinkedMapW<K, V> prependMap(MapW<? extends K, ? extends V> headMap,
                                                      MapW<? extends K, ? extends V> tailMap) {
        boolean tailIsLinked = tailMap != null && tailMap instanceof LinkedMapW<?, ?>;

        int size = headMap.size() + (tailMap != null ? tailMap.size() : 0);

        IteratorW<EntryW<K, V>> iterator = (IteratorW<EntryW<K, V>>) (IteratorW) headMap.iterator();

        if (!tailIsLinked && tailMap != null) {
            iterator = new BiIteratorW<>(iterator, (IteratorW<EntryW<K, V>>) (IteratorW) tailMap.iterator());
        }

        LinkedMapW<K, V> copy = copy(size, iterator);

        if (tailIsLinked) {

            LinkedMapW<K, V> tailLinked = (LinkedMapW<K, V>) tailMap;

            if (copy.last == null) {
                copy.first = tailLinked.first;
                copy.last = tailLinked.last;
            } else {
                copy.last.setNext(tailLinked.first);
                copy.last = tailLinked.first;
            }
        }

        return copy;
    }

    private static <K, V> LinkedMapW<K, V> copy(int size, IteratorW<EntryW<K, V>> iterator) {
        LinkedEntry<K, V> first = null;
        LinkedEntry<K, V> current = null;
        LinkedEntry<K, V> last = null;

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            LinkedEntry<K, V> node = new LinkedEntry<>(next.getKey(), next.getValue(), null);

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

        return new LinkedMapW<>(size, first, last);
    }

    @Override
    public Iterable<EntryW<K, V>> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public Map<K, V> asJavaMap() {
        return new WBackedJavaMap<>(this);
    }

    @Override
    public EntryW<K, V> first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.first;
    }

    @Override
    public MapW<K, V> head() {
        LinkedEntry<K, V> copy = this.first.copy();

        copy.setNext(null);

        return new LinkedMapW<>(0, copy, null);
    }

    @Override
    public EntryW<K, V> last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.last;
    }

    @Override
    public MapW<K, V> tail() {

        if (this.isEmpty() || this.size() == 1)
            return empty();

        return new LinkedMapW<>(this.size() - 1, this.first.getNext(), this.last);
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
    public boolean containsKey(K key) {
        for (LinkedEntry<K, V> entry = first; entry != null; entry = entry.getNext()) {
            if (entry.getKey().equals(key))
                return true;
        }

        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (LinkedEntry<K, V> entry = first; entry != null; entry = entry.getNext()) {
            if (entry.getValue().equals(value))
                return true;
        }

        return false;
    }

    @Override
    public boolean contains(K key, V value) {
        for (LinkedEntry<K, V> entry = first; entry != null; entry = entry.getNext()) {
            if (entry.getKey().equals(key) && entry.getValue().equals(value))
                return true;
        }

        return false;
    }

    @Override
    public MapW<K, V> prepend(K key, V value) {
        return prepend(key, value, this);
    }

    @Override
    public MapW<K, V> prepend(MapW<? extends K, ? extends V> mapW) {
        return prependMap(mapW, this);
    }

    @Override
    public MapW<K, V> append(K key, V value) {
        return append(this, key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapW<K, V> append(MapW<? extends K, ? extends V> map) {
        if (map instanceof LinkedMapW) {
            return ((LinkedMapW) map).prepend(this);
        }

        return append(this, map);
    }


    @Override
    public MapW<K, V> add(K key, V value) {
        return this.append(key, value);
    }

    @Override
    public MapW<K, V> addAll(MapW<? extends K, ? extends V> map) {
        return this.append(map);
    }

    @Override
    public MapW<K, V> remove(K key) {
        return this.remove(key, null, false);
    }

    @Override
    public MapW<K, V> remove(K key, V value) {
        return this.remove(key, value, true);
    }

    private MapW<K, V> remove(K key, V value, boolean checkValue) {

        if (this.first == null)
            return this;

        if (this.first.getKey().equals(key)
                && (!checkValue || this.first.getValue().equals(value))) {

            if (this.size == 1)
                return empty();

            return new LinkedMapW<>(this.size() - 1, this.first.getNext(), this.last);
        }

        if (this.last.getKey().equals(key)
                && (!checkValue || this.first.getValue().equals(value))) {

            if (this.size == 1)
                return empty();

            LinkedMapW<K, V> w = new LinkedMapW<>(this.size() - 1, this.first, null);

            LinkedEntry<K, V> n = this.first;

            while (n.getNext() != this.last) {
                n = n.getNext();
            }

            w.last = n;

            return w;
        }

        LinkedEntry<K, V> first = null;
        LinkedEntry<K, V> current = null;
        LinkedEntry<K, V> last = null;
        boolean found = false;

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();
            boolean eq = next.getKey().equals(key) && (!checkValue || next.getValue().equals(value));

            if (!eq || found) {
                LinkedEntry<K, V> node = new LinkedEntry<>(next.getKey(), next.getValue(), null);

                if (first == null) {
                    first = node;
                    current = first;
                } else {
                    current.setNext(node);
                    current = node;
                }

                if (!iterator.hasNext())
                    last = current;

            } else {
                found = true;
            }
        }

        return new LinkedMapW<>(found ? this.size - 1 : this.size, first, last);
    }

    @Override
    public boolean containsAll(MapW<? extends K, ? extends V> map) {
        return map.iterator().allOfRemaining(entryW -> this.contains(entryW.getKey(), entryW.getValue()));
    }

    @Override
    public boolean containsAllKeys(MapW<? extends K, ? extends V> map) {
        return map.iterator().allOfRemaining(entryW -> this.containsKey(entryW.getKey()));
    }

    @Override
    public boolean containsAllValues(MapW<? extends K, ? extends V> map) {
        return map.iterator().allOfRemaining(entryW -> this.containsValue(entryW.getValue()));
    }

    @Override
    public boolean containsAllKeys(CollectionW<? extends K> collectionW) {
        return collectionW.iterator().allOfRemaining(this::containsKey);
    }

    @Override
    public boolean containsAllValues(CollectionW<? extends V> collectionW) {
        return collectionW.iterator().allOfRemaining(this::containsValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapW<K, V> removeAll(MapW<? extends K, ? extends V> map) {
        return this.filter((k, v) -> !((MapW<K, V>) map).contains(k, v));
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapW<K, V> removeAllKeys(MapW<? extends K, ? extends V> map) {
        return this.filter((k, v) -> !((MapW<K, V>) map).containsKey(k));
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapW<K, V> removeAllKeys(CollectionW<? extends K> collection) {
        return this.filter((k, v) -> !((CollectionW<K>) collection).contains(k));
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapW<K, V> removeAllValues(MapW<? extends K, ? extends V> map) {
        return this.filter((k, v) -> !((MapW<K, V>) map).containsValue(v));
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapW<K, V> removeAllValues(CollectionW<? extends V> collection) {
        return this.filter((k, v) -> !((CollectionW<V>) collection).contains(v));
    }

    @Override
    public SetW<K> keys() {
        return new KeySet();
    }

    @Override
    public CollectionW<V> values() {
        return new ValueCollection();
    }

    @Override
    public SetW<EntryW<K, V>> entries() {
        return new EntrySet();
    }

    @Override
    public MapW<K, V> clear() {
        return new LinkedMapW<>();
    }

    @Override
    public <RK, RV> MapW<RK, RV> map(PairFunction<? super K, ? super V, ? extends RK, ? extends RV> mapper) {
        LinkedEntry<RK, RV> first = null;
        LinkedEntry<RK, RV> current = null;
        LinkedEntry<RK, RV> last = null;

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> n = iterator.next();

            Pair<? extends RK, ? extends RV> apply = mapper.apply(n.getKey(), n.getValue());

            LinkedEntry<RK, RV> node = new LinkedEntry<>(apply.getFirst(), apply.getSecond(), null);

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

        return new LinkedMapW<>(this.size, first, last);
    }

    @Override
    public <RK, RV> MapW<RK, RV> flatMap(BiFunction<? super K, ? super V, ? extends MapW<? extends RK, ? extends RV>> mapper) {
        MapW<RK, RV> left = empty();

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            MapW<? extends RK, ? extends RV> right = mapper.apply(next.getKey(), next.getValue());

            left = left.addAll(right);

        }

        return left;
    }

    @Override
    public MapW<K, V> filter(BiPredicate<? super K, ? super V> filter) {
        int removed = 0;
        LinkedEntry<K, V> first = null;
        LinkedEntry<K, V> current = null;
        LinkedEntry<K, V> last = null;

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (filter.test(next.getKey(), next.getValue())) {
                LinkedEntry<K, V> node = new LinkedEntry<>(next.getKey(), next.getValue(), null);

                if (first == null) {
                    first = node;
                    current = first;
                } else {
                    current.setNext(node);
                    current = node;
                }

                if (!iterator.hasNext())
                    last = current;

            } else {
                ++removed;
            }
        }

        return new LinkedMapW<>(this.size - removed, first, last);
    }

    @Override
    public IteratorW<EntryW<K, V>> iterator() {
        return new LinkedIterator<>(this.first);
    }

    @Override
    public V get(K key) {

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (next.getKey().equals(key))
                return next.getValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SetW<K> getKeys(V value) {
        Object[] keys = new Object[this.size()]; // Yes, I will copy values to this array
        int size = 0;

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (next.getValue().equals(value)) {
                keys[size] = next.getKey();
                ++size;
            }
        }

        return new LinkedSetW<>((K[]) keys, size);
    }

    @Override
    public OptObject<EntryW<K, V>> getEntry(K key) {
        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (next.getKey().equals(key))
                return OptObject.optObject(next);
        }

        return OptObject.none();
    }

    @SuppressWarnings("unchecked")
    @Override
    public SetW<EntryW<K, V>> getEntries(V value) {
        EntryW<K, V>[] keys = new EntryW[this.size()]; // Yes, I will copy values to this array
        int size = 0;

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (next.getValue().equals(value)) {
                keys[size] = next;
                ++size;
            }
        }

        return new LinkedSetW<>(keys, size);
    }

    @Override
    public OptObject<V> getOpt(K key) {
        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (next.getKey().equals(key))
                return OptObject.optObject(next.getValue());
        }

        return OptObject.none();
    }

    @Override
    public MapW<K, V> copy() {
        return append(this, null);
    }

    @Override
    public String toString() {
        IteratorW<EntryW<K, V>> it = this.iterator();

        if (!it.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');

        for (; ; ) {

            EntryW<K, V> e = it.next();

            sb.append(e.getKey() == this ? "(this Map)" : e.getKey());
            sb.append('=');
            sb.append(e.getValue() == this ? "(this Map)" : e.getValue());

            if (!it.hasNext()) {
                return sb.append('}').toString();
            }

            sb.append(',').append(' ');
        }
    }

    static class LinkedEntry<K, V> implements EntryW<K, V> {
        private final K key;
        private final V value;
        private LinkedEntry<K, V> next;

        LinkedEntry(K key, V value, LinkedEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public Map.Entry<K, V> asJavaEntry() {
            return new WBackedJavaMapEntry<>(this);
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        public LinkedEntry<K, V> getNext() {
            return this.next;
        }

        void setNext(LinkedEntry<K, V> next) {
            this.next = next;
        }

        @Override
        public LinkedEntry<K, V> copy() {
            return new LinkedEntry<>(this.getKey(), this.getValue(), this.getNext());
        }
    }

    static class Holder<K, V> {
        private Holder<K, V> prev;
        private LinkedEntry<K, V> next;

        Holder(Holder<K, V> prev, LinkedEntry<K, V> next) {
            this.prev = prev;
            this.next = next;
        }

        public LinkedEntry<K, V> getNext() {
            return this.next;
        }

        void setNext(LinkedEntry<K, V> next) {
            this.next = next;
        }

        public Holder<K, V> getPrev() {
            return this.prev;
        }

        void setPrev(Holder<K, V> prev) {
            this.prev = prev;
        }

    }

    static class LinkedIterator<K, V> implements IteratorW<EntryW<K, V>> {

        private LinkedEntry<K, V> next;

        LinkedIterator(LinkedEntry<K, V> node) {
            this.next = node;
        }

        @Override
        public Iterator<EntryW<K, V>> asJavaIterator() {
            return new WBackedJavaIterator<>(this);
        }

        @Override
        public boolean hasNext() {
            return this.next.getNext() != null;
        }

        @Override
        public EntryW<K, V> next() {
            if (!this.hasNext())
                throw new NoSuchElementException();

            LinkedEntry<K, V> entry = this.next;

            this.next = entry.getNext();

            return entry;
        }

        @Override
        public IteratorW<EntryW<K, V>> copy() {
            return new LinkedIterator<>(this.next);
        }

    }

    class KeySet implements SetW<K> {

        @Override
        public Iterable<K> asJavaIterable() {
            return new WBackedJavaIterable<>(this);
        }

        @Override
        public Set<K> asJavaSet() {
            return new WBackedJavaSet<>(this);
        }

        @Override
        public Collection<K> asJavaCollection() {
            return new WBackedJavaCollection<>(this);
        }

        @Override
        public IteratorW<K> iterator() {
            return new MappingIteratorW<>(LinkedMapW.this.iterator(), EntryW::getKey);
        }

        @Override
        public K first() {
            return LinkedMapW.this.first.getKey();
        }

        @Override
        public SetW<K> head() {
            return new LinkedSetW<>(this.first());
        }

        @Override
        public K last() {
            return LinkedMapW.this.last.getKey();
        }

        @Override
        public SetW<K> tail() {
            return LinkedMapW.this.tail().keys();
        }

        @Override
        public int size() {
            return LinkedMapW.this.size();
        }

        @Override
        public boolean isEmpty() {
            return LinkedMapW.this.isEmpty();
        }

        @Override
        public boolean contains(K o) {
            return LinkedMapW.this.containsKey(o);
        }

        @Override
        public SetW<K> prepend(K k) {
            return new LinkedSetW<>(this).prepend(k);
        }

        @Override
        public SetW<K> prepend(CollectionW<? extends K> es) {
            return new LinkedSetW<>(this).prepend(es);
        }

        @Override
        public SetW<K> append(K k) {
            return new LinkedSetW<>(this).append(k);
        }

        @Override
        public SetW<K> append(CollectionW<? extends K> es) {
            return new LinkedSetW<>(this).append(es);
        }

        @Override
        public SetW<K> add(K k) {
            return this.append(k);
        }

        @Override
        public SetW<K> remove(K k) {
            return new LinkedSetW<>(this).remove(k);
        }

        @Override
        public boolean containsAll(CollectionW<? extends K> c) {
            return LinkedMapW.this.containsAllKeys(c);
        }

        @Override
        public SetW<K> addAll(CollectionW<? extends K> c) {
            return this.append(c);
        }

        @Override
        public SetW<K> removeAll(CollectionW<? extends K> c) {
            return LinkedMapW.this.removeAllKeys(c).keys();
        }

        @Override
        public <R> SetW<R> map(Function<? super K, ? extends R> mapper) {
            return new LinkedSetW<>(this).map(mapper);
        }

        @Override
        public <R> SetW<R> flatMap(Function<? super K, ? extends CollectionW<? extends R>> mapper) {
            return new LinkedSetW<>(this).flatMap(mapper);
        }

        @Override
        public SetW<K> filter(Predicate<? super K> filter) {
            return new LinkedSetW<>(this).filter(filter);
        }

        @Override
        public SetW<K> copy() {
            return new KeySet();
        }
    }

    class ValueCollection implements CollectionW<V> {
        @Override
        public Iterable<V> asJavaIterable() {
            return new WBackedJavaIterable<>(this);
        }

        @Override
        public Collection<V> asJavaCollection() {
            return new WBackedJavaCollection<>(this);
        }

        @Override
        public IteratorW<V> iterator() {
            return new MappingIteratorW<>(LinkedMapW.this.iterator(), EntryW::getValue);
        }

        @Override
        public V first() {
            return LinkedMapW.this.first.getValue();
        }

        @Override
        public CollectionW<V> head() {
            return new LinkedSetW<>(this.first());
        }

        @Override
        public V last() {
            return LinkedMapW.this.last.getValue();
        }

        @Override
        public CollectionW<V> tail() {
            return LinkedMapW.this.tail().values();
        }

        @Override
        public int size() {
            return LinkedMapW.this.size();
        }

        @Override
        public boolean isEmpty() {
            return LinkedMapW.this.isEmpty();
        }

        @Override
        public boolean contains(V o) {
            return LinkedMapW.this.containsValue(o);
        }

        @Override
        public CollectionW<V> prepend(V v) {
            return LinkedListW.fromCollection(this).prepend(v);
        }

        @Override
        public CollectionW<V> prepend(CollectionW<? extends V> es) {
            return LinkedListW.fromCollection(this).prepend(es);
        }

        @Override
        public CollectionW<V> append(V v) {
            return LinkedListW.fromCollection(this).append(v);
        }

        @Override
        public CollectionW<V> append(CollectionW<? extends V> es) {
            return LinkedListW.fromCollection(this).append(es);
        }

        @Override
        public CollectionW<V> add(V v) {
            return this.append(v);
        }

        @Override
        public CollectionW<V> remove(V v) {
            return LinkedListW.fromCollection(this).remove(v);
        }

        @Override
        public boolean containsAll(CollectionW<? extends V> c) {
            return LinkedMapW.this.containsAllValues(c);
        }

        @Override
        public CollectionW<V> addAll(CollectionW<? extends V> c) {
            return this.append(c);
        }

        @Override
        public CollectionW<V> removeAll(CollectionW<? extends V> c) {
            return LinkedMapW.this.removeAllValues(c).values();
        }

        @Override
        public <R> CollectionW<R> map(Function<? super V, ? extends R> mapper) {
            return LinkedListW.fromCollection(this).map(mapper);
        }

        @Override
        public <R> CollectionW<R> flatMap(Function<? super V, ? extends CollectionW<? extends R>> mapper) {
            return LinkedListW.fromCollection(this).flatMap(mapper);
        }

        @Override
        public CollectionW<V> filter(Predicate<? super V> filter) {
            return LinkedListW.fromCollection(this).filter(filter);
        }

        @Override
        public CollectionW<V> copy() {
            return new ValueCollection();
        }
    }

    class EntrySet implements SetW<EntryW<K, V>> {
        @Override
        public Iterable<EntryW<K, V>> asJavaIterable() {
            return new WBackedJavaIterable<>(this);
        }

        @Override
        public Set<EntryW<K, V>> asJavaSet() {
            return new WBackedJavaSet<>(this);
        }

        @Override
        public Collection<EntryW<K, V>> asJavaCollection() {
            return new WBackedJavaCollection<>(this);
        }

        @Override
        public IteratorW<EntryW<K, V>> iterator() {
            return LinkedMapW.this.iterator();
        }

        @Override
        public EntryW<K, V> first() {
            return LinkedMapW.this.first;
        }

        @Override
        public SetW<EntryW<K, V>> head() {
            return new LinkedSetW<>(this.first());
        }

        @Override
        public EntryW<K, V> last() {
            return LinkedMapW.this.last;
        }

        @Override
        public SetW<EntryW<K, V>> tail() {
            return LinkedMapW.this.tail().entries();
        }

        @Override
        public int size() {
            return LinkedMapW.this.size();
        }

        @Override
        public boolean isEmpty() {
            return LinkedMapW.this.isEmpty();
        }

        @Override
        public boolean contains(EntryW<K, V> o) {
            return LinkedMapW.this.contains(o.getKey(), o.getValue());
        }

        @Override
        public SetW<EntryW<K, V>> prepend(EntryW<K, V> e) {
            return new LinkedSetW<>(this).prepend(e);
        }

        @Override
        public SetW<EntryW<K, V>> prepend(CollectionW<? extends EntryW<K, V>> es) {
            return new LinkedSetW<>(this).prepend(es);
        }

        @Override
        public SetW<EntryW<K, V>> append(EntryW<K, V> v) {
            return new LinkedSetW<>(this).append(v);
        }

        @Override
        public SetW<EntryW<K, V>> append(CollectionW<? extends EntryW<K, V>> es) {
            return new LinkedSetW<>(this).append(es);
        }

        @Override
        public SetW<EntryW<K, V>> add(EntryW<K, V> v) {
            return this.append(v);
        }

        @Override
        public SetW<EntryW<K, V>> remove(EntryW<K, V> v) {
            return new LinkedSetW<>(this).remove(v);
        }

        @Override
        public boolean containsAll(CollectionW<? extends EntryW<K, V>> c) {
            return c.iterator().allOfRemaining(o -> LinkedMapW.this.contains(o.getKey(), o.getValue()));
        }

        @Override
        public SetW<EntryW<K, V>> addAll(CollectionW<? extends EntryW<K, V>> c) {
            return this.append(c);
        }

        @Override
        public SetW<EntryW<K, V>> removeAll(CollectionW<? extends EntryW<K, V>> c) {
            return new LinkedSetW<>(this).removeAll(c);
        }

        @Override
        public <R> SetW<R> map(Function<? super EntryW<K, V>, ? extends R> mapper) {
            return new LinkedSetW<>(this).map(mapper);
        }

        @Override
        public <R> SetW<R> flatMap(Function<? super EntryW<K, V>, ? extends CollectionW<? extends R>> mapper) {
            return new LinkedSetW<>(this).flatMap(mapper);
        }

        @Override
        public SetW<EntryW<K, V>> filter(Predicate<? super EntryW<K, V>> filter) {
            return new LinkedSetW<>(this).filter(filter);
        }

        @Override
        public SetW<EntryW<K, V>> copy() {
            return new EntrySet();
        }
    }

}
