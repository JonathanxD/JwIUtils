/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.collection.CollectionUtils;
import com.github.jonathanxd.iutils.extra.BaseContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Created by jonathan on 14/02/16.
 */
public class Node<K, V> {

    private final K key;
    private final V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Node(Map.Entry<K, V> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public Node(BaseContainer<K> container1, BaseContainer<V> container2) {
        this.key = container1.orElseThrow(() -> new NullPointerException("Empty container 1!"));
        this.value = container2.orElseThrow(() -> new NullPointerException("Empty container 2!"));
    }

    public Node(Optional<K> container1, Optional<V> container2) {
        this.key = container1.orElseThrow(() -> new NullPointerException("Empty optional 1!"));
        this.value = container2.orElseThrow(() -> new NullPointerException("Empty optional 2!"));
    }

    public static <K, V> Node<? extends K, ? extends V> fromEntry(Map.Entry<? extends K, ? extends V> entry) {
        return new Node<>(entry.getKey(), entry.getValue());
    }

    public static <K, V> Node<K, V> fromFlatEntry(Map.Entry<K, V> entry) {
        return new Node<>(entry.getKey(), entry.getValue());
    }

    public static <K, V> Collection<Node<K, V>> fromEntryCollection(Collection<Map.Entry<K, V>> entries) {
        Collection<Node<K, V>> sameCollection = CollectionUtils.same(entries);

        Collection<Node<K, V>> collection = sameCollection != null ? sameCollection : new ArrayList<>();

        entries.forEach(e -> collection.add(fromFlatEntry(e)));

        return collection;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Node["+getKey()+"="+getValue()+"]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    public void consume(BiConsumer<? super K, ? super V> biConsumer) {
        biConsumer.accept(this.getKey(), this.getValue());
    }

    public Node<K, V> withNewKey(K key) {
        return new Node<>(key, this.getValue());
    }

    public Node<K, V> withNewValue(V value) {
        return new Node<>(this.getKey(), value);
    }
}
