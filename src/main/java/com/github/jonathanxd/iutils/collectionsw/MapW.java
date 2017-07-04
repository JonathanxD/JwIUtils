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
package com.github.jonathanxd.iutils.collectionsw;

import com.github.jonathanxd.iutils.collectionsw.mutable.MutableMapW;
import com.github.jonathanxd.iutils.function.comparators.BiComparator;
import com.github.jonathanxd.iutils.function.function.PairFunction;
import com.github.jonathanxd.iutils.function.stream.BiStream;
import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A Map of keys of type {@link K} associated to values of type {@link V}.
 *
 * All operations which remains on equality depends on implementation, the general contract is that
 * {@link Object#equals(Object)} and {@link Object#hashCode()} should be used to compare entries.
 *
 * All operations which returns a {@code new} {@link MapW} should return a map of the same type, for
 * mutable maps, the same map instance should be returned. And operations applied to maps returned
 * by this map such as {@link #head()}, {@link #tail()} etc, should be reflected on original map.
 */
public interface MapW<K, V> extends IterableW<EntryW<K, V>> {

    /**
     * Returns a java map version of this map.
     *
     * Modifications are not allowed for immutable map.
     *
     * @return Java map version of this map.
     */
    Map<K, V> asJavaMap();

    @Override
    IteratorW<EntryW<K, V>> iterator();

    /**
     * Returns the first element entry of map.
     *
     * If the {@link #size()} of map is {@code 1} then the returned element is the same as {@link
     * #last()} element.
     *
     * This method may return different values for different calls because maps are naturally
     * unordered.
     *
     * @return First element of map.
     * @throws java.util.NoSuchElementException If map is empty.
     */
    EntryW<K, V> first();

    /**
     * Returns the head of map or empty map if there is no head.
     *
     * This method may return different values for different calls because maps are naturally
     * unordered.
     *
     * @return Head of map or empty map if there is no head.
     */
    MapW<K, V> head();

    /**
     * Returns the last element of map.
     *
     * If the {@link #size()} of map is {@code 1} then the returned element is the same as {@link
     * #first()} element.
     *
     * @return The last element of map.
     * @throws java.util.NoSuchElementException If map is empty.
     */
    EntryW<K, V> last();

    /**
     * Returns the tail of map or empty map if there is no tail.
     *
     * This method may return different values for different calls because maps are naturally
     * unordered.
     *
     * @return Tail of map or empty map if there is no tail.
     */
    MapW<K, V> tail();

    /**
     * Size of this map.
     *
     * @return Size of this map.
     */
    int size();

    /**
     * Returns true if this map is empty.
     *
     * @return True if this map is empty.
     */
    boolean isEmpty();

    // Query

    /**
     * Gets {@code value} associated to {@code key}.
     *
     * @param key Key to get associated value.
     * @return {@code value} associated to {@code key}.
     */
    V get(K key);

    /**
     * Gets all {@code keys} associated to {@code value}.
     *
     * @param value Value to get associated key.
     * @return {@code keys} associated to {@code value}.
     */
    SetW<K> getKeys(V value);

    /**
     * Gets {@code value} associated to {@code key}.
     *
     * @param key Key to get associated value.
     * @return Optional of {@link com.github.jonathanxd.iutils.opt.Some} {@code value} associated to
     * {@code key} if present, or {@link com.github.jonathanxd.iutils.opt.None} if not.
     */
    OptObject<V> getOpt(K key);

    /**
     * Gets entry which holds {@code key} and associated {@code value}.
     *
     * @param key Key to get entry.
     * @return Optional of {@link com.github.jonathanxd.iutils.opt.Some} entry associated to {@code
     * key}, or {@link com.github.jonathanxd.iutils.opt.None} if there is no entry which holds the
     * {@code key}.
     */
    OptObject<EntryW<K, V>> getEntry(K key);

    /**
     * Gets entry which holds {@code value} and associated {@code key}.
     *
     * @param value Value to get entris.
     * @return Set with all enetries with {@code value} associated to it.
     */
    SetW<EntryW<K, V>> getEntries(V value);

    /**
     * Returns true if this map contains any key that is equal to {@code key}. The comparison
     * depends on implementation, but commonly it will use either {@link Object#equals(Object)} and
     * {@link Object#hashCode()} to compare objects.
     *
     * @param key Key to compare.
     * @return True if this map contains any key equals to {@code key}.
     */
    boolean containsKey(K key);

    /**
     * Returns true if this map contains any value (associated to a key) that is equal to {@code
     * value}. The comparison depends on implementation, but commonly it will use either {@link
     * Object#equals(Object)} and {@link Object#hashCode()} to compare objects.
     *
     * @param value Value to compare.
     * @return True if this map contains any value equals to {@code value}.
     */
    boolean containsValue(V value);

    /**
     * Returns true if this map contains {@code key} and the {@code key} is associated to {@code
     * value}.
     *
     * @param key   Key to find.
     * @param value Value to find.
     * @return True if this map contains {@code key} and the {@code key} is associated to {@code
     * value}.
     */
    boolean contains(K key, V value);

    /**
     * Returns true if this map contains any entry that matches the {@code predicate}, the
     * implementation should stop looping through map at the first matching element and return
     * {@code true}.
     *
     * @param predicate Predicate to use to check entries.
     * @return True if this map contains any entry that matches the {@code predicate}.
     */
    default boolean contains(BiPredicate<K, V> predicate) {
        IteratorW<? extends EntryW<K, V>> iterator = this.iterator();

        while (!iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();
            if (predicate.test(next.getKey(), next.getValue()))
                return true;
        }

        return false;
    }

    // Mod

    /**
     * Returns a new map with same entries and {@code key} associated to {@code value} prepended to
     * the map.
     *
     * @param key   Key to associate to {@code value}.
     * @param value Value to associated to {@code key}.
     * @return New map with same entries and {@code key} associated to {@code value} prepended to
     * the map.
     */
    MapW<K, V> prepend(K key, V value);

    /**
     * Returns a new map with same entries and entries of {@code map} prepended to the map.
     *
     * @param map Map with entries to prepend.
     * @return New map with same entries and entries of {@code map} prepended to the map.
     */
    MapW<K, V> prepend(MapW<? extends K, ? extends V> map);

    /**
     * Returns a new map with same entries and {@code key} associated to {@code value} appended to
     * the map.
     *
     * @param key   Key to associate to {@code value}.
     * @param value Value to associated to {@code key}.
     * @return New map with same entries and {@code key} associated to {@code value} appended to the
     * map.
     */
    MapW<K, V> append(K key, V value);

    /**
     * Returns a new map with same entries and entries of {@code map} appended to the map.
     *
     * @param map Map with entries to prepend.
     * @return New map with same entries and entries of {@code map} appended to the map.
     */
    MapW<K, V> append(MapW<? extends K, ? extends V> map);

    /**
     * Creates a map of the same type which contains same entries and {@code key} associated to
     * {@code value}.
     *
     * Same as {@link #put(Object, Object)}
     *
     * @param key   Key to associated to {@code value}.
     * @param value Value to associated to {@code key}.
     * @return A map of the same type which contains same entries and {@code key} associated to
     * {@code value}.
     */
    MapW<K, V> add(K key, V value);

    /**
     * Creates a map of the same type which contains same entries and {@code key} associated to
     * {@code value}.
     *
     * @param key   Key to associated to {@code value}.
     * @param value Value to associated to {@code key}.
     * @return A map of the same type which contains same entries and {@code key} associated to
     * {@code value}.
     */
    default MapW<K, V> put(K key, V value) {
        return this.add(key, value);
    }

    /**
     * Returns a new map without {@code key} and value associated to {@code key}.
     *
     * @param key Key to remove associated value belong its reference.
     * @return A map without {@code key} and value associated to {@code key}.
     */
    MapW<K, V> remove(K key);

    /**
     * Returns a new map without pair of {@code key} and {@code value}.
     *
     * The pair of {@code key} and {@code value} is removed if, and only if, the associated value is
     * same as {@code value}.
     *
     * @param key   Key to remove.
     * @param value Value to remove.
     * @return A new map without pair of {@code key} and {@code value}.
     */
    MapW<K, V> remove(K key, V value);

    /**
     * Returns true if this map contains all pairs of key and value present in {@code map}.
     *
     * @param map Map.
     * @return True if this map contains all pairs of key and value present in {@code map}.
     */
    boolean containsAll(MapW<? extends K, ? extends V> map);

    /**
     * Returns true if this map contains all keys present in {@code map}.
     *
     * @param map Map.
     * @return True if this map contains all keys present in {@code map}.
     */
    boolean containsAllKeys(MapW<? extends K, ? extends V> map);

    /**
     * Returns true if this map contains all values present in {@code map}.
     *
     * @param map Map.
     * @return True if this map contains all values present in {@code map}.
     */
    boolean containsAllValues(MapW<? extends K, ? extends V> map);

    /**
     * Returns true if this map contains all keys present in {@code collection}.
     *
     * @param collection Collection.
     * @return True if this map contains all keys present in {@code collection}.
     */
    boolean containsAllKeys(CollectionW<? extends K> collection);

    /**
     * Returns true if this map contains all values present in {@code collection}.
     *
     * @param collection Collection.
     * @return True if this map contains all values present in {@code collection}.
     */
    boolean containsAllValues(CollectionW<? extends V> collection);

    /**
     * Creates a new map with same entries and entries of {@code map}.
     *
     * Same as {@link #putAll(MapW)}
     *
     * @param map Map with entries to add to new map.
     * @return New map with same entries and entries of {@code map}.
     */
    MapW<K, V> addAll(MapW<? extends K, ? extends V> map);

    /**
     * Creates a new map with same entries and entries of {@code map}.
     *
     * @param map Map with entries to add to new map.
     * @return New map with same entries and entries of {@code map}.
     */
    default MapW<K, V> putAll(MapW<? extends K, ? extends V> map) {
        return this.addAll(map);
    }

    /**
     * Returns a new map without all key and value pairs of {@code map}.
     *
     * Keys will be removed if, and only if, the pair of key and value is equals (not only the key,
     * for keys only use {@link #removeAllKeys(MapW)}).
     *
     * @param map Map with key and value pairs to remove.
     * @return New map without all key and value pairs of {@code map}.
     */
    MapW<K, V> removeAll(MapW<? extends K, ? extends V> map);

    /**
     * Returns a new map without all keys of {@code map} regardless the associated value.
     *
     * @param map Map with keys to remove.
     * @return New map without all keys of {@code map} regardless the associated value.
     */
    MapW<K, V> removeAllKeys(MapW<? extends K, ? extends V> map);

    /**
     * Returns a new map without all keys of {@code collection}.
     *
     * @param collection Collection with keys to remove.
     * @return New map without all keys of {@code collection}.
     */
    MapW<K, V> removeAllKeys(CollectionW<? extends K> collection);

    /**
     * Returns a new map without all values which {@code map} contains regardless the associated
     * key.
     *
     * @param map Map with values to remove.
     * @return New map without all values which {@code map} contains regardless the associated key.
     */
    MapW<K, V> removeAllValues(MapW<? extends K, ? extends V> map);

    /**
     * Returns a new map without all values of {@code collection}.
     *
     * @param collection Collection with values to remove.
     * @return New map without all values of {@code collection}.
     */
    MapW<K, V> removeAllValues(CollectionW<? extends V> collection);

    /**
     * Returns a set of keys which this map contains.
     *
     * @return Set of keys which this map contains.
     */
    SetW<K> keys();

    /**
     * Returns a set of values which this map contains.
     *
     * @return Set of values which this map contains.
     */
    CollectionW<V> values();

    /**
     * Returns a set of entries of this map.
     *
     * @return Set of entries of this map.
     */
    SetW<EntryW<K, V>> entries();

    /**
     * Returns a new empty map of same type.
     *
     * @return New empty map of same type.
     * @see MutableMapW#clear()
     */
    MapW<K, V> clear();

    /**
     * Returns true if this map equals to another map of the same type.
     *
     * @param o Other object to test.
     * @return True if this map equals to another map of the same type.
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns the hash of this map.
     *
     * @return Hash of this map.
     */
    int hashCode();

    /**
     * Creates a splitaretor from this map.
     *
     * @return Splitaretor from this map.
     */
    default Spliterator<EntryW<K, V>> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator().asJavaIterator(), Spliterator.DISTINCT | Spliterator.IMMUTABLE);
    }

    /**
     * Creates a stream from the spliterator of this map.
     *
     * @return Stream from the spliterator of this map.
     */
    default Stream<EntryW<K, V>> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Creates a parallel stream from the spliterator of this map.
     *
     * @return Parallel stream from the spliterator of this map.
     */
    default Stream<EntryW<K, V>> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    /**
     * Creates a non-parallel capable {@link BiStream} ({@link BiStream} is not same as java {@link
     * Stream}, and it is not a real stream) of this map.
     *
     * @return non-parallel capable {@link BiStream}of this map.
     */
    default BiStream<K, V> biStream() {
        return BiStreams.mapWStream(this);
    }

    @Override
    MapW<K, V> copy();

    // Ops

    /**
     * Maps all values of this map using {@code mapper} and return a new map of same type with
     * mapped entries.
     *
     * @param mapper Mapper function.
     * @param <RK>   Target key type.
     * @param <RV>   Target value type.
     * @return Mapped map.
     */
    <RK, RV> MapW<RK, RV> map(PairFunction<? super K, ? super V, ? extends RK, ? extends RV> mapper);

    /**
     * Flat maps all values of this map using {@code mapper} and return a new map of the same type
     * with mapped entries.
     *
     * @param mapper Flat mapping function.
     * @param <RK>   Target key type.
     * @param <RV>   Target value type.
     * @return Flat mapped map.
     */
    <RK, RV> MapW<RK, RV> flatMap(BiFunction<? super K, ? super V, ? extends MapW<? extends RK, ? extends RV>> mapper);

    /**
     * Filters all entries of this map that matches {@code filter} and returns a new map with
     * filtered entries.
     *
     * @param filter Predicate to filter entries.
     * @return Map of filtered entries.
     */
    MapW<K, V> filter(BiPredicate<? super K, ? super V> filter);

    /**
     * Filters all entries of this map that does not matches {@code filter} and returns a new map
     * with filtered entries.
     *
     * @param filter Predicate to filter entries.
     * @return Map of entries that does not matches {@code filter}.
     */
    default MapW<K, V> filterNot(BiPredicate<? super K, ? super V> filter) {
        return this.filter(filter.negate());
    }

    /**
     * Returns first entry that matches {@code predicate}.
     *
     * @param predicate Predicate to test element.
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of first entry that matches {@code
     * predicate} or {@link com.github.jonathanxd.iutils.opt.None} if there is no one entry matches
     * {@code predicate}.
     */
    default OptObject<EntryW<K, V>> first(BiPredicate<? super K, ? super V> predicate) {
        IteratorW<? extends EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (predicate.test(next.getKey(), next.getValue()))
                return OptObject.optObjectNullable(next);
        }

        return OptObject.none();
    }

    /**
     * Returns last element that matches {@code predicate}.
     *
     * @param predicate Predicate to test element.
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of last element that matches {@code
     * predicate} or {@link com.github.jonathanxd.iutils.opt.None} if there is no one element
     * matches {@code predicate}.
     */
    default OptObject<EntryW<K, V>> last(BiPredicate<? super K, ? super V> predicate) {
        IteratorW<? extends EntryW<K, V>> iterator = this.iterator();

        OptObject<EntryW<K, V>> obj = OptObject.none();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (predicate.test(next.getKey(), next.getValue()))
                obj = OptObject.optObjectNullable(next);
        }

        return obj;
    }

    /**
     * Returns the lowest entry of this map according to the {@code comparator}.
     *
     * @param comparator Comparator to compare entries.
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of lowest entry of this map according
     * to the {@code comparator}, or {@link com.github.jonathanxd.iutils.opt.None} if map is empty.
     */
    default OptObject<EntryW<K, V>> min(BiComparator<? super K, ? super V> comparator) {
        IteratorW<? extends EntryW<K, V>> iterator = this.iterator();

        OptObject<EntryW<K, V>> lowest = OptObject.none();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (!lowest.isPresent()) {
                lowest = Opt.someNotNull(next);
            } else {
                // if (next < lowest)
                if (comparator.compare(next.getKey(), next.getValue(),
                        lowest.getValue().getKey(), lowest.getValue().getValue()) < 0) {
                    lowest = Opt.someNotNull(next);
                }
            }
        }

        return lowest;
    }

    /**
     * Returns the highest entry of this map according to the {@code comparator}.
     *
     * @param comparator Comparator to compare entries.
     * @return {@link com.github.jonathanxd.iutils.opt.Some} of highest entry of this map according
     * to the {@code comparator}, or {@link com.github.jonathanxd.iutils.opt.None} if map is empty.
     */
    default OptObject<EntryW<K, V>> max(BiComparator<? super K, ? super V> comparator) {
        return this.min(comparator.reversed());
    }

    /**
     * Returns {@code true} if any entry matches {@code predicate}.
     *
     * Always {@code false} for empty map.
     *
     * @param predicate Predicate to match.
     * @return {@code true} if any entry matches {@code predicate}.
     */
    default boolean any(BiPredicate<? super K, ? super V> predicate) {
        IteratorW<? extends EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (predicate.test(next.getKey(), next.getValue()))
                return true;
        }

        return false;
    }

    /**
     * Returns {@code true} if all entries matches {@code predicate}.
     *
     * Always {@code true} for empty map.
     *
     * @param predicate Predicate to match.
     * @return {@code true} if all entries matches {@code predicate}.
     */
    default boolean all(BiPredicate<? super K, ? super V> predicate) {
        IteratorW<? extends EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (!predicate.test(next.getKey(), next.getValue()))
                return false;
        }

        return true;
    }

    /**
     * Returns {@code true} if no one entry matches {@code predicate}.
     *
     * Always {@code true} for empty map.
     *
     * @param predicate Predicate to match.
     * @return {@code true} if no one entry matches {@code predicate}.
     */
    default boolean none(BiPredicate<? super K, ? super V> predicate) {
        IteratorW<? extends EntryW<K, V>> iterator = this.iterator();

        while (iterator.hasNext()) {
            EntryW<K, V> next = iterator.next();

            if (predicate.test(next.getKey(), next.getValue()))
                return false;
        }

        return true;
    }

    /**
     * For each all entries of this map and pass to consumer.
     *
     * @param consumer Consumer of entries.
     */
    default void forEach(BiConsumer<? super K, ? super V> consumer) {
        this.iterator().forEachRemaining(kvEntryW -> consumer.accept(kvEntryW.getKey(), kvEntryW.getValue()));
    }

    /**
     * Returns a builder with elements of {@code this} map.
     *
     * @return Builder.
     */
    Builder<K, V> builder();

    /**
     * Builder interface which allows map to be built. All default implementations are implemented
     * using a backing {@link java.util.HashMap}. Elements are sorted according to {@link
     * java.util.HashMap}.
     *
     * You can also retrieve a stream of all current elements in builder using {@link #elements()}
     *
     * There is no guarantee that a collection of same type will be returned.
     *
     * @param <K> Key type.
     * @param <V> Value type.
     */
    interface Builder<K, V> {

        /**
         * Returns a stream of entries inside the builder.
         *
         * @return Stream of entries inside the builder.
         */
        Stream<EntryW<K, V>> elements();

        /**
         * Returns the amount of entries in this builder.
         *
         * @return Amount of entries.
         */
        int size();

        /**
         * Associates a key to a value in builder.
         *
         * @param key   Key to associate.
         * @param value Value to associate.
         * @return {@code this}.
         */
        Builder<K, V> put(K key, V value);

        /**
         * Remove association of a key to a value in builder.
         *
         * @param key   Key to remove association.
         * @param value Value to remove association.
         * @return {@code this}.
         */
        Builder<K, V> remove(K key, V value);

        /**
         * Remove all builder entries.
         *
         * @return {@code this}.
         */
        Builder<K, V> clear();

        /**
         * Build map.
         *
         * @return Built map.
         */
        MapW<K, V> build();

    }

}
