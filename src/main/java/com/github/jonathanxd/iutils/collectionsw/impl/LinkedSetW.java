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
package com.github.jonathanxd.iutils.collectionsw.impl;

import com.github.jonathanxd.iutils.collectionsw.CollectionW;
import com.github.jonathanxd.iutils.collectionsw.EntryW;
import com.github.jonathanxd.iutils.collectionsw.IteratorW;
import com.github.jonathanxd.iutils.collectionsw.MapW;
import com.github.jonathanxd.iutils.collectionsw.SetW;
import com.github.jonathanxd.iutils.collectionsw.impl.builder.SetWBuilder;
import com.github.jonathanxd.iutils.collectionsw.impl.java.JavaWrappedMapW;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaCollection;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaIterable;
import com.github.jonathanxd.iutils.collectionsw.impl.java.WBackedJavaSet;
import com.github.jonathanxd.iutils.collectionsw.impl.mutable.JavaBackedMutSetW;
import com.github.jonathanxd.iutils.object.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A {@link SetW} backed by a {@link MapW} backed by a {@link LinkedHashMap}.
 *
 * @param <E> Element type.
 */
public class LinkedSetW<E> implements SetW<E> {

    private static final Object DEF = new Object();
    private final MapW<E, Object> map;

    /**
     * Creates an empty hash set.
     */
    public LinkedSetW() {
        this.map = new JavaWrappedMapW<>(new LinkedHashMap<>(), LinkedHashMap::new);
    }

    /**
     * Creates a hash set with only one element.
     *
     * @param element Element.
     */
    public LinkedSetW(E element) {
        LinkedHashMap<E, Object> map = new LinkedHashMap<>();
        map.put(element, DEF);
        this.map = new JavaWrappedMapW<>(map, LinkedHashMap::new);
    }

    /**
     * Creates a hash set with {@code elements} as initial elements.
     *
     * @param elements Elements.
     */
    public LinkedSetW(SetW<E> elements) {
        LinkedHashMap<E, Object> map = new LinkedHashMap<>();
        elements.forEach(e -> map.put(e, DEF));
        this.map = new JavaWrappedMapW<>(map, LinkedHashMap::new);
    }

    /**
     * Creates a hash set with {@code elements} as initial elements.
     *
     * @param elements Elements.
     */
    @SuppressWarnings("unchecked")
    public LinkedSetW(E[] elements) {
        LinkedHashMap<E, Object> map = new LinkedHashMap<>();
        for (E element : elements) {
            map.put(element, DEF);
        }

        this.map = new JavaWrappedMapW<>(map, LinkedHashMap::new);
    }

    /**
     * Creates a hash set with {@code elements} as initial elements.
     *
     * @param elements Elements.
     */
    @SuppressWarnings("unchecked")
    public LinkedSetW(E[] elements, int size) {
        this((E[]) Arrays.stream(elements)
                .limit(size)
                .toArray(Object[]::new));
    }

    protected LinkedSetW(MapW<E, Object> map) {
        this.map = map;
    }


    public static <E> SetW<E> fromJavaCollection(Set<E> set) {
        return new LinkedSetW<>(new JavaBackedMutSetW<>(set));
    }

    private static <K> MapW<K, Object> createFromKeys(CollectionW<K> collectionW) {
        Map<K, Object> hashMap = new HashMap<>();
        MapW<K, Object> map = new JavaWrappedMapW<>(hashMap, HashMap::new);

        collectionW.forEach(k -> hashMap.put(k, DEF));

        return map;
    }

    private static <K, V> MapW<K, V> create(CollectionW<EntryW<K, V>> collectionW) {
        Map<K, V> hashMap = new HashMap<>();
        MapW<K, V> map = new JavaWrappedMapW<>(hashMap, HashMap::new);

        collectionW.forEach(k -> hashMap.put(k.getKey(), k.getValue()));

        return map;
    }

    @Override
    public Iterable<E> asJavaIterable() {
        return new WBackedJavaIterable<>(this);
    }

    @Override
    public Set<E> asJavaSet() {
        return new WBackedJavaSet<>(this);
    }

    @Override
    public Collection<E> asJavaCollection() {
        return new WBackedJavaCollection<>(this);
    }

    @Override
    public IteratorW<E> iterator() {
        return this.map.keys().iterator();
    }

    @Override
    public E first() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return this.iterator().next();
    }

    @Override
    public SetW<E> head() {
        return this.isEmpty() ? new LinkedSetW<>() : new LinkedSetW<>(this.first());
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();

        IteratorW<E> iterator = this.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (!iterator.hasNext())
                return next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public SetW<E> tail() {
        return this.isEmpty() ? new LinkedSetW<>() : new LinkedSetW<>(this.last());
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
    public boolean contains(E o) {
        return this.map.containsKey(o);
    }

    @Override
    public SetW<E> prepend(E e) {
        return new LinkedSetW<>(this.map.prepend(e, DEF));
    }

    @Override
    public SetW<E> prepend(CollectionW<? extends E> es) {
        return new LinkedSetW<>(this.map.prepend(LinkedSetW.create(es.map(o -> new EntryW.Immutable<>(o, DEF)))));
    }

    @Override
    public SetW<E> append(E e) {
        return new LinkedSetW<>(this.map.append(e, DEF));
    }

    @Override
    public SetW<E> append(CollectionW<? extends E> es) {
        return new LinkedSetW<>(this.map.append(LinkedSetW.create(es.map(o -> new EntryW.Immutable<>(o, DEF)))));
    }

    @Override
    public SetW<E> add(E e) {
        return new LinkedSetW<>(this.map.append(e, DEF));
    }

    @Override
    public SetW<E> remove(E e) {
        return new LinkedSetW<>(this.map.remove(e));
    }

    @Override
    public boolean containsAll(CollectionW<? extends E> c) {
        return c.iterator().allOfRemaining(this.map::containsKey);
    }

    @Override
    public SetW<E> addAll(CollectionW<? extends E> c) {
        return this.append(c);
    }

    @Override
    public SetW<E> removeAll(CollectionW<? extends E> c) {
        return new LinkedSetW<>(this.map.removeAll(LinkedSetW.create(c.map(o -> new EntryW.Immutable<>(o, DEF)))));
    }

    @Override
    public <R> SetW<R> map(Function<? super E, ? extends R> mapper) {
        return new LinkedSetW<>(this.map.map((e, o) -> Pair.of(mapper.apply(e), DEF)));
    }

    @Override
    public <R> SetW<R> flatMap(Function<? super E, ? extends CollectionW<? extends R>> mapper) {
        return new LinkedSetW<>(this.map
                .flatMap((e, o) ->
                        create(mapper.apply(e)
                                .map(x -> new EntryW.Immutable<>(x, DEF)))
                ));
    }

    @Override
    public SetW<E> filter(Predicate<? super E> filter) {
        return new LinkedSetW<>(this.map.filter((e, o) -> filter.test(e)));
    }

    @Override
    public SetW<E> copy() {
        return new LinkedSetW<>(this.map.copy());
    }

    @Override
    public Builder<E> builder() {
        return SetWBuilder.builder(this);
    }
}
