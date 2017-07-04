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

import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.collectionsw.EntryW;
import com.github.jonathanxd.iutils.collectionsw.IterableW;
import com.github.jonathanxd.iutils.collectionsw.IteratorW;
import com.github.jonathanxd.iutils.collectionsw.MapW;
import com.github.jonathanxd.iutils.collectionsw.SetW;
import com.github.jonathanxd.iutils.collectionsw.impl.LinkedSetW;
import com.github.jonathanxd.iutils.collectionsw.impl.builder.MutableMapWBuilder;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaBackedMapIterableW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaBackedMutMapIterableW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaMapBackedMutEntry;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableMapW;
import com.github.jonathanxd.iutils.collectionsw.mutable.MutableSetW;
import com.github.jonathanxd.iutils.function.function.PairFunction;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Java Map backed implementation of {@link MutableMapW}.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class JavaBackedMutMapW<K, V> implements MutableMapW<K, V> {
    private final Map<K, V> map;
    private final IterableW<EntryW<K, V>> iterable;
    private final IterableW<EntryW.Mut<K, V>> mutIterable;
    private final Function<Map<?, ?>, Map<?, ?>> factory;

    public JavaBackedMutMapW(Map<K, V> map, Function<Map<?, ?>, Map<?, ?>> factory) {
        this.map = map;
        this.factory = factory;
        this.iterable = new JavaBackedMapIterableW<>(this.map);
        this.mutIterable = new JavaBackedMutMapIterableW<>(this.map);
    }

    public JavaBackedMutMapW(Map<K, V> map) {
        this(map, HashMap::new);
    }

    @Override
    public Map<K, V> asJavaMap() {
        return this.map;
    }

    @Override
    public Iterable<EntryW<K, V>> asJavaIterable() {
        return this.iterable.asJavaIterable();
    }

    @Override
    public IteratorW<EntryW<K, V>> iterator() {
        return this.iterable.iterator();
    }

    @Override
    public IteratorW<EntryW.Mut<K, V>> iteratorMutEntry() {
        return this.mutIterable.iterator();
    }

    @Override
    public MutableMapW<K, V> clear() {
        this.map.clear();
        return this;
    }

    @Override
    public EntryW.Mut<K, V> first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.iteratorMutEntry().next();
    }

    @Override
    public MutableMapW<K, V> head() {
        return new JavaBackedMutMapW<>(new JavaSubMap<>(this.map, 0, 0), factory);
    }

    @Override
    public EntryW.Mut<K, V> last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        IteratorW<EntryW.Mut<K, V>> mutIteratorW = this.iteratorMutEntry();

        while (mutIteratorW.hasNext()) {
            EntryW.Mut<K, V> next = mutIteratorW.next();

            if (!mutIteratorW.hasNext())
                return next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public MutableMapW<K, V> tail() {
        if (this.isEmpty())
            return new JavaBackedMutMapW<>(new JavaSubMap<>(this.map, 0, 0), factory);

        return new JavaBackedMutMapW<>(new JavaSubMap<>(this.map, 1, this.size()), factory);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public V get(K key) {
        return this.map.get(key);
    }

    @Override
    public SetW<K> getKeys(V value) {
        return new JavaBackedMutSetW<>(this.map.keySet());
    }

    @Override
    public OptObject<V> getOpt(K key) {
        if (!this.containsKey(key))
            return Opt.none();

        return Opt.some(this.get(key));
    }

    @Override
    public OptObject<EntryW<K, V>> getEntry(K key) {
        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (Objects.equals(next.getKey(), key))
                return Opt.some(next);
        }

        return Opt.none();
    }

    @Override
    public SetW<EntryW<K, V>> getEntries(V value) {
        MutableSetW<EntryW<K, V>> mut = new JavaBackedMutSetW<>(new HashSet<>());

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            mut.add(next);
        }

        return new LinkedSetW<>(mut);
    }

    @Override
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return this.map.containsValue(value);
    }

    @Override
    public boolean contains(K key, V value) {
        return this.map.containsKey(key) && Objects.equals(this.map.get(key), value);
    }

    @Override
    public MutableMapW<K, V> prepend(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    @Override
    public MutableMapW<K, V> prepend(MapW<? extends K, ? extends V> map) {
        map.forEach(this::prepend);
        return this;
    }

    @Override
    public MutableMapW<K, V> append(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    @Override
    public MutableMapW<K, V> append(MapW<? extends K, ? extends V> map) {
        map.forEach(this::append);
        return this;
    }

    @Override
    public MutableMapW<K, V> add(K key, V value) {
        return this.append(key, value);
    }

    @Override
    public MutableMapW<K, V> remove(K key) {
        this.map.remove(key);
        return this;
    }

    @Override
    public MutableMapW<K, V> remove(K key, V value) {
        this.map.remove(key, value);
        return this;
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
    public boolean containsAllKeys(CollectionW<? extends K> collection) {
        return collection.iterator().allOfRemaining(this::containsKey);
    }

    @Override
    public boolean containsAllValues(CollectionW<? extends V> collection) {
        return collection.iterator().allOfRemaining(this::containsValue);
    }

    @Override
    public MutableMapW<K, V> addAll(MapW<? extends K, ? extends V> map) {
        map.forEach(this::add);
        return this;
    }

    @Override
    public MutableMapW<K, V> removeAll(MapW<? extends K, ? extends V> map) {
        map.forEach(this::remove);
        return this;
    }

    @Override
    public MutableMapW<K, V> removeAllKeys(MapW<? extends K, ? extends V> map) {
        map.forEach((o, o2) -> this.remove(o));
        return this;
    }

    @Override
    public MapW<K, V> removeAllKeys(CollectionW<? extends K> collection) {
        collection.forEach(this::remove);
        return this;
    }

    @Override
    public MapW<K, V> removeAllValues(MapW<? extends K, ? extends V> map) {
        map.forEach((o, o2) -> {
            if (this.containsValue(o2)) {
                this.map.values().removeIf(v -> Objects.equals(v, o2));
            }
        });
        return this;
    }

    @Override
    public MapW<K, V> removeAllValues(CollectionW<? extends V> collection) {
        collection.forEach(o2 -> {
            if (this.containsValue(o2)) {
                this.map.values().removeIf(v -> Objects.equals(v, o2));
            }
        });
        return this;
    }

    @Override
    public SetW<K> keys() {
        return new JavaBackedMutSetW<>(this.map.keySet());
    }

    @Override
    public CollectionW<V> values() {
        return new JavaBackedMutCollectionW<>(this.map.values());
    }

    @Override
    public SetW<EntryW<K, V>> entries() {
        return new JavaBackedMutSetW<>(this.map.entrySet().stream()
                .map(kvEntry -> new JavaMapBackedMutEntry<>(this.map, kvEntry.getKey(), kvEntry.getValue()))
                .collect(Collectors.toSet()),
                HashSet::new);
    }

    @Override
    public MutableMapW<K, V> copy() {
        return new JavaBackedMutMapW<>(new HashMap<>(this.map), factory);
    }

    @Override
    public <RK, RV> MutableMapW<RK, RV> map(PairFunction<? super K, ? super V, ? extends RK, ? extends RV> mapper) {
        Map<RK, RV> aNew = createNew();

        this.map.forEach((k, v) -> {
            Pair<? extends RK, ? extends RV> apply = mapper.apply(k, v);
            aNew.put(apply.getFirst(), apply.getSecond());
        });

        return new JavaBackedMutMapW<>(aNew);
    }

    @Override
    public <RK, RV> MutableMapW<RK, RV> flatMap(BiFunction<? super K, ? super V, ? extends MapW<? extends RK, ? extends RV>> mapper) {
        Map<RK, RV> aNew = createNew();

        this.map.forEach((k, v) -> {
            MapW<? extends RK, ? extends RV> apply = mapper.apply(k, v);
            apply.forEach(aNew::put);
        });

        return new JavaBackedMutMapW<>(aNew);
    }

    @Override
    public MutableMapW<K, V> filter(BiPredicate<? super K, ? super V> filter) {
        Set<Map.Entry<K, V>> entries = this.map.entrySet();
        entries.removeIf(kvEntry -> filter.test(kvEntry.getKey(), kvEntry.getValue()));

        return this;
    }

    @SuppressWarnings("unchecked")
    private <RK, RV> Map<RK, RV> createNew() {
        return (Map<RK, RV>) factory.apply(Collections.emptyMap());
    }

    @Override
    public Builder<K, V> builder() {
        return MutableMapWBuilder.builder(this);
    }
}
