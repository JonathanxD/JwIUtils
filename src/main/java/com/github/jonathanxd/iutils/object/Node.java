/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.container.BaseContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

        if(entries == null)
            return Collections.emptyList();

        Collection<Node<K, V>> collection = new ArrayList<>();

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

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Node) {
            return ((Node) obj).key.equals(this.key) && ((Node) obj).value.equals(this.value);
        }

        return super.equals(obj);
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
