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
package com.github.jonathanxd.iutils.map;

import com.github.jonathanxd.iutils.collection.view.ViewCollections;
import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.iterator.IteratorUtil;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.Pairs;
import com.github.jonathanxd.iutils.type.TypeInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An {@link TempTypedMap} implementation backed to a {@link Map provided map}, all value types are
 * stored together with values using a {@link Value}.
 */
public final class BackedTempTypedMap<K, V> implements TempTypedMap<K, V> {

    private final Map<K, Value<? extends V>> backingMap;
    private final Lazy<UnmodTypedMap<K, V>> unmodAccess = Lazy.lazy(() -> new UnmodTypedMap<>(this));

    public BackedTempTypedMap() {
        this.backingMap = new HashMap<>();
    }

    /**
     * See {@link HashMap#HashMap(int, float)}
     *
     * @param initialCapacity See {@link HashMap#HashMap(int, float)}
     * @param loadFactor      See {@link HashMap#HashMap(int, float)}
     * @see HashMap#HashMap(int, float)
     */
    public BackedTempTypedMap(int initialCapacity, float loadFactor) {
        this.backingMap = new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * See {@link HashMap#HashMap(int)}
     *
     * @param initialCapacity See {@link HashMap#HashMap(int)}
     * @see HashMap#HashMap(int)
     */
    public BackedTempTypedMap(int initialCapacity) {
        this.backingMap = new HashMap<>(initialCapacity);
    }

    /**
     * Constructs a HashTypedMap with all values of {@link Map map} {@code m}.
     *
     * @param m Map to get values.
     */
    public BackedTempTypedMap(Map<? extends K, ? extends Value<V>> m) {
        this.backingMap = new HashMap<>(m);
    }

    /**
     * Constructs a HashTypedMap from another map.
     *
     * @param m       Map to get values.
     * @param backing If true, delegates operations to {@code m}, if false, only copy all values
     *                from {@code m}.
     */
    public BackedTempTypedMap(Map<K, Value<? extends V>> m, boolean backing) {
        if (backing)
            this.backingMap = m;
        else
            this.backingMap = new HashMap<>(m);
    }

    @Override
    public TypedMap<K, V> createUnmodifiable() {
        return this.unmodAccess.get();
    }

    @Override
    public <B extends V> Pair<? extends V, TypeInfo<? extends V>> putTypedTemporary(K key, B value, TypeInfo<B> type) {
        Value<? extends V> replaced = this.backingMap.put(key, Value.create(value, type, true));

        if (replaced == null)
            return Pair.nullPair();

        return Pair.of(replaced.getValue(), replaced.getType());
    }

    @Override
    public <B extends V> Pair<? extends V, TypeInfo<? extends V>> putTyped(K key, B value, TypeInfo<B> type) {
        Value<? extends V> replaced = this.backingMap.put(key, Value.create(value, type));

        if (replaced == null)
            return Pair.nullPair();

        return Pair.of(replaced.getValue(), replaced.getType());
    }

    @Override
    public Pair<? extends V, TypeInfo<? extends V>> getTyped(K key) {
        Value<? extends V> get = this.backingMap.get(key);

        if (get == null)
            return Pair.nullPair();

        if (get.isTemporary())
            this.backingMap.remove(key, get);

        return Pair.of(get.getValue(), get.getType());
    }

    @Override
    public <B extends V> List<Pair<? extends K, TypeInfo<? extends B>>> getValueTyped(B value) {
        List<Pair<? extends K, TypeInfo<? extends B>>> list = new ArrayList<>();

        Iterator<Entry<K, Value<? extends V>>> iterator = this.backingMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<K, Value<? extends V>> kValueEntry = iterator.next();

            K key = kValueEntry.getKey();
            Value<? extends V> valueHolder = kValueEntry.getValue();

            if (valueHolder.getValue().equals(value)) {
                if (valueHolder.isTemporary())
                    iterator.remove();

                list.add(Pair.of(key, valueHolder.getType().cast()));
            }
        }


        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends V> B getTyped(K key, TypeInfo<B> type) {
        Pair<? extends V, TypeInfo<? extends V>> pair = this.getTyped(key);

        if(pair.isNullPair())
            return null;

        if (pair.getSecond().equals(type))
            return (B) pair.getFirst();

        return null;
    }

    @Override
    public Pair<? extends V, TypeInfo<? extends V>> removeTyped(K key) {
        Value<? extends V> removed = this.backingMap.remove(key);

        if (removed == null)
            return Pairs.nullPair();

        return Pair.of(removed.getValue(), removed.getType());
    }

    @Override
    public <B extends V> boolean removeTyped(K key, B value, TypeInfo<B> type) {
        return this.backingMap.remove(key, Pair.of(value, type));
    }

    @Override
    public boolean containsTyped(K key, TypeInfo<?> type) {

        for (TypedEntry<K, ? extends V> kTypedEntry : this.typedEntrySet()) {
            if (Objects.equals(kTypedEntry.getKey(), key)
                    && Objects.equals(kTypedEntry.getType(), type))
                return true;

        }

        return false;
    }

    @Override
    public Set<TypedEntry<K, ? extends V>> typedEntrySet() {
        Set<Entry<K, Value<? extends V>>> set = this.backingMap.entrySet();

        return ViewCollections.setMapped(set,
                (kPairEntry, entryIterator) -> IteratorUtil.mapped(kPairEntry, entryIterator, kPairEntry1 ->
                        new TypedEntry<>(kPairEntry1.getKey(),
                                kPairEntry1.getValue().getValue(),
                                kPairEntry1.getValue().getType().cast())
                ),
                y -> {
                    throw new UnsupportedOperationException();
                },
                kTypedEntry -> set.removeIf(kPairEntry ->
                        Objects.equals(kPairEntry.getKey(), kTypedEntry.getValue())
                                && Objects.equals(kPairEntry.getValue().getValue(), kTypedEntry.getValue())
                                && Objects.equals(kPairEntry.getValue().getType(), kTypedEntry.getType())
                ));
    }

    @Override
    public int size() {
        return this.backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.backingMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return BiStreams.mapStream(this.backingMap)
                .filter((k, typeInfoPair) -> typeInfoPair.getValue().equals(value))
                .count() > 0;
    }

    @Override
    public V get(Object key) {
        Value<? extends V> get = this.backingMap.get(key);

        if (get != null)
            return get.getValue();

        return null;
    }

    @Override
    public V put(K key, V value) {
        Value<? extends V> put = this.backingMap.put(key,
                Value.create(value, TypeInfo.of(value.getClass()).cast()));

        if (put != null)
            return put.getValue();

        return null;
    }

    @Override
    public V remove(Object key) {
        Value<? extends V> remove = this.backingMap.remove(key);

        if (remove != null)
            return remove.getValue();

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        this.backingMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.backingMap.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<Value<? extends V>> values = this.backingMap.values();

        return ViewCollections.collectionMapped(values,
                (typeInfoPair, pairIterator) -> IteratorUtil.mapped(typeInfoPair, pairIterator, Value::getValue),
                y -> {
                    throw new UnsupportedOperationException();
                },
                o -> values.removeIf(typeInfoPair -> Objects.equals(typeInfoPair.getValue(), o))

        );
    }

    @Override
    public String toString() {
        return this.backingMap.toString();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, Value<? extends V>>> entries = this.backingMap.entrySet();

        return ViewCollections.setMapped(entries,
                (e, eIterator) -> IteratorUtil.mapped(e, eIterator, kPairEntry ->
                        new BackedTypedMap.MappedEntryWrapper<>(kPairEntry, Value::getValue, o -> Value.create(o, kPairEntry.getValue().getType().cast()))
                ),
                y -> {
                    throw new UnsupportedOperationException();
                },
                e -> entries.removeIf(kPairEntry ->
                        Objects.equals(kPairEntry.getKey(), e.getKey())
                                && Objects.equals(kPairEntry.getValue().getValue(), e.getValue())
                )
        );
    }

}
