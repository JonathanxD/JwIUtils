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

import com.github.jonathanxd.iutils.function.consumer.IntObjConsumer;

import java.util.Objects;

/**
 * Node is a representation of association of a int to a value.
 *
 * @param <V> Value type.
 */
public final class IntNode<V> {

    /**
     * Key.
     */
    private final int key;

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
    private IntNode(int key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Creates a new int node from {@code key} and {@code value}.
     *
     * @param key   Key.
     * @param value Value.
     * @param <V>   Value type.
     * @return Int node from {@code key} and {@code value}.
     */
    public static <V> IntNode<V> intNode(int key, V value) {
        return new IntNode<>(key, value);
    }

    /**
     * Gets the key of this node.
     *
     * @return Key of this node.
     */
    public int getKey() {
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

    /**
     * Key value (Kotlin compatibility purpose).
     */
    public final int component1() {
        return this.getKey();
    }

    /**
     * Value (Kotlin compatibility purpose).
     */
    public final V component2() {
        return this.getValue();
    }

    @Override
    public String toString() {
        return "Node[" + this.getKey() + "=" + this.getValue() + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getKey(), this.getValue());
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof IntNode) {
            return ((IntNode) obj).getKey() == this.key && ((IntNode) obj).value.equals(this.value);
        }

        return super.equals(obj);
    }

    /**
     * Consumer the node key and value.
     * @param consumer Consumer.
     */
    public void consume(IntObjConsumer<? super V> consumer) {
        consumer.accept(this.getKey(), this.getValue());
    }

    /**
     * Creates a new instance of current node with a new {@link #key}.
     *
     * @param key new key.
     * @return New instance of current node with a new {@link #key}.
     */
    public IntNode<V> withNewKey(int key) {
        return new IntNode<>(key, this.getValue());
    }

    /**
     * Creates a new instance of current node with a new {@link #value}.
     *
     * @param value new value.
     * @return New instance of current node with a new {@link #value}.
     */
    public IntNode<V> withNewValue(V value) {
        return new IntNode<>(this.getKey(), value);
    }
}
