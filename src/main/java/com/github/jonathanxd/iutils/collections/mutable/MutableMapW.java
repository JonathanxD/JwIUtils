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
package com.github.jonathanxd.iutils.collections.mutable;

import com.github.jonathanxd.iutils.collections.EntryW;
import com.github.jonathanxd.iutils.collections.IteratorW;
import com.github.jonathanxd.iutils.collections.MapW;
import com.github.jonathanxd.iutils.function.comparators.BiComparator;
import com.github.jonathanxd.iutils.function.function.PairFunction;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A mutable {@link MapW}. Implementation must returns mutable values for all operations,
 * modifications to mutable values must be reflected in this map. (it includes all values, even
 * those returned by {@link #head()} and {@link #tail()}).
 */
public interface MutableMapW<K, V> extends MapW<K, V> {

    @Override
    IteratorW<EntryW<K, V>> iterator();

    IteratorW<EntryW.Mut<K, V>> iteratorMutEntry();

    @Override
    MutableMapW<K, V> clear();

    @Override
    EntryW.Mut<K, V> first();

    @Override
    MutableMapW<K, V> head();

    @Override
    EntryW.Mut<K, V> last();

    @Override
    MutableMapW<K, V> tail();

    @Override
    MutableMapW<K, V> prepend(K key, V value);

    @Override
    MutableMapW<K, V> prepend(MapW<? extends K, ? extends V> map);

    @Override
    MutableMapW<K, V> append(K key, V value);

    @Override
    MutableMapW<K, V> append(MapW<? extends K, ? extends V> map);

    @Override
    MutableMapW<K, V> add(K key, V value);

    @Override
    default MutableMapW<K, V> put(K key, V value) {
        return this.add(key, value);
    }

    @Override
    MutableMapW<K, V> remove(K key);

    @Override
    MutableMapW<K, V> remove(K key, V value);

    @Override
    MutableMapW<K, V> addAll(MapW<? extends K, ? extends V> map);

    @Override
    default MutableMapW<K, V> putAll(MapW<? extends K, ? extends V> map) {
        return this.addAll(map);
    }

    @Override
    MutableMapW<K, V> removeAll(MapW<? extends K, ? extends V> map);

    @Override
    MutableMapW<K, V> removeAllKeys(MapW<? extends K, ? extends V> map);

    @Override
    default Spliterator<EntryW<K, V>> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator().asJavaIterator(), Spliterator.DISTINCT);
    }


    /**
     * Creates a spliterator with mutable entries.
     *
     * @return Spliterator with mutable entries.
     */
    default Spliterator<EntryW.Mut<K, V>> spliteratorMutEntries() {
        return Spliterators.spliteratorUnknownSize(this.iteratorMutEntry().asJavaIterator(), Spliterator.DISTINCT);
    }

    /**
     * Creates a Stream with mutable entries.
     *
     * @return Stream with mutable entries.
     */
    default Stream<EntryW.Mut<K, V>> streamMutEntries() {
        return StreamSupport.stream(this.spliteratorMutEntries(), false);
    }

    /**
     * Creates a Parallel Stream with mutable entries.
     *
     * @return Parallel Stream with mutable entries.
     */
    default Stream<EntryW.Mut<K, V>> parallelStreamMutEntries() {
        return StreamSupport.stream(this.spliteratorMutEntries(), true);
    }

    @Override
    MutableMapW<K, V> copy();

    @Override
    <RK, RV> MutableMapW<RK, RV> map(PairFunction<? super K, ? super V, ? extends RK, ? extends RV> mapper);

    @Override
    <RK, RV> MutableMapW<RK, RV> flatMap(BiFunction<? super K, ? super V, ? extends MapW<? extends RK, ? extends RV>> mapper);

    @Override
    MutableMapW<K, V> filter(BiPredicate<? super K, ? super V> filter);

    @Override
    default MutableMapW<K, V> filterNot(BiPredicate<? super K, ? super V> filter) {
        return this.filter(filter.negate());
    }

    /**
     * Returns first mutable entry that matches {@code predicate}.
     *
     * This calls the super operation {@link #first(BiPredicate)} and unsafe cast the result.
     *
     * @param predicate Predicate.
     * @return First mutable entry that matches {@code predicate}.
     * @see #first(BiPredicate)
     */
    default OptObject<EntryW.Mut<K, V>> firstMutEntry(BiPredicate<? super K, ? super V> predicate) {
        return MapW.super.first(predicate).map(kvEntryW -> (EntryW.Mut<K, V>) kvEntryW);
    }

    /**
     * Returns last mutable entry that matches {@code predicate}.
     *
     * This calls the super operation {@link #last(BiPredicate)} and unsafe cast the result.
     *
     * @param predicate Predicate.
     * @return Last mutable entry that matches {@code predicate}.
     * @see #last(BiPredicate)
     */
    default OptObject<EntryW.Mut<K, V>> lastMutEntry(BiPredicate<? super K, ? super V> predicate) {
        return MapW.super.last(predicate).map(kvEntryW -> (EntryW.Mut<K, V>) kvEntryW);
    }

    /**
     * Returns min mutable entry according to {@code comparator}.
     *
     * This calls the super operation {@link #min(BiComparator)} and unsafe cast the result.
     *
     * @param comparator Comparator.
     * @return Min mutable entry according to {@code comparator}.
     * @see #min(BiComparator)
     */
    default OptObject<EntryW.Mut<K, V>> minMutEntry(BiComparator<? super K, ? super V> comparator) {
        return MapW.super.min(comparator).map(kvEntryW -> (EntryW.Mut<K, V>) kvEntryW);
    }

    /**
     * Returns max mutable entry according to {@code comparator}.
     *
     * This calls the super operation {@link #max(BiComparator)} and unsafe cast the result.
     *
     * @param comparator Comparator.
     * @return Max mutable entry according to {@code comparator}.
     * @see #max(BiComparator)
     */
    default OptObject<EntryW.Mut<K, V>> maxMutEntry(BiComparator<? super K, ? super V> comparator) {
        return MapW.super.max(comparator).map(kvEntryW -> (EntryW.Mut<K, V>) kvEntryW);
    }
}
