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
package com.github.jonathanxd.iutils.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Node is a representation of association of a key to a value.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public final class Node<K, V> {

    /**
     * Key.
     */
    private final K key;

    /**
     * Value.
     */
    private final V value;

    /**
     * Creates a node.
     *
     * @param key   Node key.
     * @param value Node value.
     */
    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Creates a node from a java map entry.
     *
     * @param entry Map entry.
     * @return Node from java map entry values.
     */
    public static <K, V> Node<K, V> fromEntry(Map.Entry<? extends K, ? extends V> entry) {
        return new Node<>(entry.getKey(), entry.getValue());
    }

    /**
     * Creates a collection of nodes from a collection of map entries.
     *
     * @param entries Map entries.
     * @param <K>     Key type.
     * @param <V>     Value type.
     * @return Collection of nodes from a collection of map entries.
     */
    public static <K, V> Collection<Node<K, V>> fromEntryCollection(Collection<Map.Entry<K, V>> entries) {

        if (entries == null)
            return Collections.emptyList();

        Collection<Node<K, V>> collection = new ArrayList<>();

        entries.forEach(e -> collection.add(fromEntry(e)));

        return collection;
    }

    /**
     * Gets the key of this node.
     *
     * @return Key of this node.
     */
    public K getKey() {
        return this.key;
    }

    /**
     * Gets the value of this node.
     *
     * @return Value of this node.
     */
    public V getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Node[" + getKey() + "=" + getValue() + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Node) {
            return ((Node) obj).key.equals(this.key) && ((Node) obj).value.equals(this.value);
        }

        return super.equals(obj);
    }

    public void consume(BiConsumer<? super K, ? super V> biConsumer) {
        biConsumer.accept(this.getKey(), this.getValue());
    }

    /**
     * Creates a new instance of current node with a new {@link #key}.
     *
     * @param key new key.
     * @return New instance of current node with a new {@link #key}.
     */
    public Node<K, V> withNewKey(K key) {
        return new Node<>(key, this.getValue());
    }

    /**
     * Creates a new instance of current node with a new {@link #value}.
     *
     * @param value new value.
     * @return New instance of current node with a new {@link #value}.
     */
    public Node<K, V> withNewValue(V value) {
        return new Node<>(this.getKey(), value);
    }
}
