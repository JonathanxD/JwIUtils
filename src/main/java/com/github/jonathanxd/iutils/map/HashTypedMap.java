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
package com.github.jonathanxd.iutils.map;

import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An {@link TypedMap} implementation backed to a {@link HashMap}, all value types are stored
 * together with values using a {@link Pair}.
 */
public final class HashTypedMap<K, V> implements TypedMap<K, V> {

    private final HashMap<K, Pair<? extends V, TypeInfo<? extends V>>> hashMap;

    public HashTypedMap() {
        this.hashMap = new HashMap<>();
    }

    /**
     * See {@link HashMap#HashMap(int, float)}
     *
     * @param initialCapacity See {@link HashMap#HashMap(int, float)}
     * @param loadFactor      See {@link HashMap#HashMap(int, float)}
     * @see HashMap#HashMap(int, float)
     */
    public HashTypedMap(int initialCapacity, float loadFactor) {
        this.hashMap = new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * See {@link HashMap#HashMap(int)}
     *
     * @param initialCapacity See {@link HashMap#HashMap(int)}
     * @see HashMap#HashMap(int)
     */
    public HashTypedMap(int initialCapacity) {
        this.hashMap = new HashMap<>(initialCapacity);
    }

    /**
     * Constructs a HashTypedMap with all values of {@link Map map} {@code m}.
     *
     * @param m Map to get values.
     */
    public HashTypedMap(Map<? extends K, ? extends Pair<? extends V, TypeInfo<? extends V>>> m) {
        this.hashMap = new HashMap<>(m);
    }


    @Override
    public <B extends V> Pair<? extends V, TypeInfo<? extends V>> putTyped(K key, B value, TypeInfo<B> type) {
        return this.hashMap.put(key, Pair.of(value, type));
    }

    @Override
    public Pair<? extends V, TypeInfo<? extends V>> getTyped(K key) {
        return this.hashMap.get(key);
    }

    @Override
    public <B extends V> List<Pair<? extends K, TypeInfo<? extends B>>> getValueTyped(B value) {
        List<Pair<? extends K, TypeInfo<? extends B>>> list = new ArrayList<>();

        for (Entry<K, Pair<? extends V, TypeInfo<? extends V>>> kPairEntry : this.hashMap.entrySet()) {
            K key = kPairEntry.getKey();
            Pair<? extends V, TypeInfo<? extends V>> entryValue = kPairEntry.getValue();

            V first = entryValue.getFirst();

            if (first.equals(value)) {
                list.add(Pair.of(key, entryValue.getSecond().cast()));
            }
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends V> B getTyped(K key, TypeInfo<B> type) {
        Pair<? extends V, TypeInfo<? extends V>> pair = this.getTyped(key);

        if (pair.getSecond().equals(type))
            return (B) pair.getFirst();

        return null;
    }

    @Override
    public Set<TypedEntry<K, ? extends V>> typedEntrySet() {
        return this.hashMap.entrySet().stream()
                .map(kPairEntry ->
                        new TypedEntry<>(kPairEntry.getKey(),
                                kPairEntry.getValue().getFirst(),
                                kPairEntry.getValue().getSecond().cast())
                )
                .collect(Collectors.toSet());
    }

    @Override
    public int size() {
        return this.hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.hashMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.hashMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return BiStreams.mapStream(this.hashMap)
                .filter((k, typeInfoPair) -> typeInfoPair.getFirst().equals(value))
                .count() > 0;
    }

    @Override
    public V get(Object key) {
        Pair<? extends V, TypeInfo<? extends V>> get = this.hashMap.get(key);

        if (get != null)
            return get.getFirst();

        return null;
    }

    @Override
    public V put(K key, V value) {
        Pair<? extends V, TypeInfo<? extends V>> put = this.hashMap.put(key, Pair.of(value, TypeInfo.of(value.getClass()).cast()));

        if (put != null)
            return put.getFirst();

        return null;
    }

    @Override
    public V remove(Object key) {
        Pair<? extends V, TypeInfo<? extends V>> remove = this.hashMap.remove(key);

        if (remove != null)
            return remove.getFirst();

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        this.hashMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.hashMap.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<Pair<? extends V, TypeInfo<? extends V>>> values = this.hashMap.values();

        return ViewCollections.collectionMapped(values,
                (typeInfoPair, pairIterator) -> IteratorUtil.mapped(typeInfoPair, pairIterator, Pair::getFirst),
                y -> values.add(Pair.ofSupplier(() -> y, () -> {
                    throw new UnsupportedOperationException();
                })),
                o -> values.removeIf(typeInfoPair -> Objects.equals(typeInfoPair.getFirst(), o))

        );
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, Pair<? extends V, TypeInfo<? extends V>>>> entries = this.hashMap.entrySet();

        return ViewCollections.setMapped(entries,
                (e, eIterator) -> IteratorUtil.mapped(e, eIterator, kPairEntry ->
                        new MappedEntryWrapper<>(kPairEntry, Pair::getFirst, o -> Pair.of(o, kPairEntry.getValue().getSecond()))
                ),
                y -> {
                    throw new UnsupportedOperationException();
                },
                e -> entries.removeIf(kPairEntry ->
                        Objects.equals(kPairEntry.getKey(), e.getKey())
                                && Objects.equals(kPairEntry.getValue().getFirst(), e.getValue())
                )
        );
    }

    static final class MappedEntryWrapper<K, V, X> implements Map.Entry<K, X> {

        private final Map.Entry<K, V> wrapper;
        private final Function<V, X> mapper;
        private final Function<X, V> unmapper;

        MappedEntryWrapper(Entry<K, V> wrapper, Function<V, X> mapper, Function<X, V> unmapper) {
            this.wrapper = wrapper;
            this.mapper = mapper;
            this.unmapper = unmapper;
        }

        @Override
        public K getKey() {
            return this.wrapper.getKey();
        }

        @Override
        public X getValue() {
            return this.mapper.apply(this.wrapper.getValue());
        }

        @Override
        public X setValue(X value) {
            return this.mapper.apply(this.wrapper.setValue(this.unmapper.apply(value)));
        }
    }
}
