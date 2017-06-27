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
package com.github.jonathanxd.iutils.collections.impl.java;

import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.collections.CollectionW;
import com.github.jonathanxd.iutils.collections.EntryW;
import com.github.jonathanxd.iutils.collections.IterableW;
import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.MapW;
import com.github.jonathanxd.iutils.collections.SetW;
import com.github.jonathanxd.iutils.collections.mutable.MutableMapW;
import com.github.jonathanxd.iutils.function.function.PairFunction;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link MapW} backed to a Java map.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class JavaWrappedMapW<K, V> implements MapW<K, V> {

    private final Map<K, V> wrapped;
    private final Lazy<Map<K, V>> unmod;
    private final Function<Map<?, ?>, Map<?, ?>> mapFactory;
    private final IterableW<EntryW<K, V>> iterable;

    /**
     * Creates a {@link MapW} backed to a java {@code map}.
     *
     * @param wrapped    Map to back.
     * @param mapFactory Factory of map, already explained in {@link JavaWrappedCollectionW}. Rules
     *                   are the same, behavior are the same.
     */
    public JavaWrappedMapW(Map<K, V> wrapped, Function<Map<?, ?>, Map<?, ?>> mapFactory) {
        this.wrapped = wrapped;
        this.unmod = Lazy.lazy(() -> Collections.unmodifiableMap(this.wrapped));
        this.mapFactory = mapFactory;
        this.iterable = new JavaBackedMapIterableW<>(this.wrapped);
    }

    @Override
    public Iterable<EntryW<K, V>> asJavaIterable() {
        return this.iterable.asJavaIterable();
    }

    @Override
    public Map<K, V> asJavaMap() {
        return this.unmod.get();
    }

    @Override
    public IteratorW<EntryW<K, V>> iterator() {
        return this.iterable.iterator();
    }

    @Override
    public EntryW<K, V> first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.iterator().next();
    }

    @Override
    public MapW<K, V> head() {
        if (this.isEmpty())
            return this.createNew();

        MapW<K, V> aNew = this.createNew();
        EntryW<K, V> first = this.first();

        return aNew.add(first.getKey(), first.getValue());
    }

    @Override
    public EntryW<K, V> last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        IteratorW<EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (!iterator.hasNext())
                return next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public MapW<K, V> tail() {
        if (this.isEmpty())
            return this.createNew();

        MapW<K, V> aNew = this.createNew();
        EntryW<K, V> last = this.last();

        return aNew.add(last.getKey(), last.getValue());
    }

    @Override
    public int size() {
        return this.wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override
    public V get(K key) {
        return this.wrapped.get(key);
    }

    @Override
    public SetW<K> getKeys(V value) {
        return new JavaWrappedSetW<>(this.wrapped.entrySet().stream()
                .filter(kvEntry -> kvEntry.getValue().equals(value))
                .map(kvEntry -> kvEntry.getKey())
                .collect(Collectors.toSet()), HashSet::new);
    }

    @Override
    public OptObject<V> getOpt(K key) {
        if (!this.containsKey(key))
            return OptObject.none();

        return OptObject.optObject(this.get(key));
    }

    @Override
    public OptObject<EntryW<K, V>> getEntry(K key) {
        if (!this.containsKey(key))
            return OptObject.none();

        V v = this.get(key);

        EntryW<K, V> entry;

        if (this.wrapped instanceof MutableMapW<?, ?>) {
            entry = new JavaMapBackedMutEntry<>(this.wrapped, key, v);
        } else {
            entry = new EntryW.Immutable<>(key, v);
        }

        if (!this.containsKey(key))
            return OptObject.none();

        return OptObject.optObject(entry);
    }

    @Override
    public SetW<EntryW<K, V>> getEntries(V value) {
        return new JavaWrappedSetW<>(this.wrapped.entrySet().stream()
                .filter(kvEntry -> kvEntry.getValue().equals(value))
                .map(kvEntry -> new EntryW.Immutable<>(kvEntry.getKey(), kvEntry.getValue()))
                .collect(Collectors.toSet()), HashSet::new);
    }

    @Override
    public boolean containsKey(K key) {
        return this.wrapped.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return this.wrapped.containsValue(value);
    }

    @Override
    public boolean contains(K key, V value) {
        return this.wrapped.containsKey(key) && Objects.equals(this.wrapped.get(key), value);

    }

    @Override
    public MapW<K, V> prepend(K key, V value) {

        Map<K, V> aNew = createNewJavaMap();

        aNew.put(key, value);
        aNew.putAll(this.wrapped);

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> prepend(MapW<? extends K, ? extends V> map) {

        Map<K, V> aNew = createNewJavaMap();

        map.forEach(aNew::put);

        aNew.putAll(this.wrapped);

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> append(K key, V value) {
        Map<K, V> aNew = createNewJavaMap(this.wrapped);

        aNew.put(key, value);

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> append(MapW<? extends K, ? extends V> map) {
        Map<K, V> aNew = createNewJavaMap(this.wrapped);

        map.forEach(aNew::put);

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> add(K key, V value) {
        return this.append(key, value);
    }

    @Override
    public MapW<K, V> remove(K key) {
        Map<K, V> aNew = createNewJavaMap(this.wrapped);

        aNew.remove(key);

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> remove(K key, V value) {
        Map<K, V> aNew = createNewJavaMap(this.wrapped);

        aNew.remove(key, value);

        return this.createNew(aNew);
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
        return collection.all(o -> this.containsKey(o));
    }

    @Override
    public boolean containsAllValues(CollectionW<? extends V> collection) {
        return collection.all(o -> this.containsValue(o));
    }

    @Override
    public MapW<K, V> addAll(MapW<? extends K, ? extends V> map) {
        return this.append(map);
    }

    @Override
    public MapW<K, V> removeAll(MapW<? extends K, ? extends V> map) {
        Map<K, V> aNew = createNewJavaMap(this.wrapped);

        map.forEach(aNew::remove);

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> removeAllKeys(MapW<? extends K, ? extends V> map) {
        Map<K, V> aNew = createNewJavaMap(this.wrapped);

        map.forEach((o, o2) -> aNew.remove(o));

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> removeAllKeys(CollectionW<? extends K> collection) {
        return null; // TODO
    }

    @Override
    public MapW<K, V> removeAllValues(MapW<? extends K, ? extends V> map) {
        return null; // TODO
    }

    @Override
    public MapW<K, V> removeAllValues(CollectionW<? extends V> collection) {
        return null; // TODO
    }

    @Override
    public SetW<K> keys() {
        return new JavaWrappedSetW<>(this.wrapped.keySet(), HashSet::new);
    }

    @Override
    public CollectionW<V> values() {
        return new JavaWrappedCollectionW<>(this.wrapped.values(), ArrayList::new);
    }

    @Override
    public SetW<EntryW<K, V>> entries() {
        return new JavaWrappedSetW<>(ViewCollections.setMapped(this.wrapped.entrySet(), (e, eIterator) ->
                        IteratorUtil.mapped(e, eIterator, kvEntry -> new EntryW.Immutable<>(kvEntry.getKey(), kvEntry.getValue())),
                y -> {
                    throw new RuntimeException();
                },
                l -> {
                    throw new RuntimeException();
                }), HashSet::new);
    }

    @Override
    public MapW<K, V> clear() {
        return null;
    }

    @Override
    public MapW<K, V> copy() {
        return this.createNew(this.wrapped);
    }

    @Override
    public <RK, RV> MapW<RK, RV> map(PairFunction<? super K, ? super V, ? extends RK, ? extends RV> mapper) {
        Map<RK, RV> aNew = createNewJavaMap();

        this.wrapped.forEach((k, v) -> {
            Pair<? extends RK, ? extends RV> apply = mapper.apply(k, v);
            aNew.put(apply.getFirst(), apply.getSecond());
        });

        return this.createNew(aNew);
    }

    @Override
    public <RK, RV> MapW<RK, RV> flatMap(BiFunction<? super K, ? super V, ? extends MapW<? extends RK, ? extends RV>> mapper) {
        Map<RK, RV> aNew = createNewJavaMap();

        this.wrapped.forEach((k, v) -> {
            MapW<? extends RK, ? extends RV> apply = mapper.apply(k, v);
            apply.forEach(aNew::put);
        });

        return this.createNew(aNew);
    }

    @Override
    public MapW<K, V> filter(BiPredicate<? super K, ? super V> filter) {
        Map<K, V> aNew = createNewJavaMap();

        this.wrapped.forEach((k, v) -> {
            if (filter.test(k, v))
                aNew.put(k, v);
        });

        return this.createNew(aNew);
    }

    @SuppressWarnings("unchecked")
    private <X, Y> MapW<X, Y> createNew(Map<X, Y> map) {
        return new JavaWrappedMapW<>(this.createNewJavaMap(map), this.mapFactory);
    }

    @SuppressWarnings("unchecked")
    private <X, Y> MapW<X, Y> createNew() {
        return new JavaWrappedMapW<>(this.createNewJavaMap(), this.mapFactory);
    }

    @SuppressWarnings("unchecked")
    private <X, Y> Map<X, Y> createNewJavaMap(Map<X, Y> map) {
        return (Map<X, Y>) this.mapFactory.apply(map);
    }

    @SuppressWarnings("unchecked")
    private <X, Y> Map<X, Y> createNewJavaMap() {
        return (Map<X, Y>) this.mapFactory.apply(Collections.emptyMap());
    }
}
