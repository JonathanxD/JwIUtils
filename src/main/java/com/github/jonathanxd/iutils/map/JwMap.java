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
package com.github.jonathanxd.iutils.map;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author jonathan
 * @since 1.1
 */
public class JwMap<K, V> extends MapContainer<K, V> {

    /**
     *
     */
    private static final long serialVersionUID = -8307678606769422541L;

    protected final Arrays<Node<K, V>> nodes = new Arrays<Node<K, V>>();
    protected Arrays<SimpleNodeOff<K, V>> nodeOff = new Arrays<SimpleNodeOff<K, V>>();
    protected boolean wMod = false;

    public JwMap() {

    }

    public JwMap(MapRegistry register) {
        register.init(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized V putInit(Object key, Object value) {
        if (Reflection.isOnClassInit(JwMap.class)) {
            update();
            return put((K) key, (V) value);
        } else {
            throw new RuntimeException("Cannot put objects out of initialization.");
        }
    }

    @Override
    public synchronized V put(K key, V value) {
        Node<K, V> node;
        int hash = hash(key);
        if (nodes.length() != 0 && ((node = getNode(hash, key)) != null)) {
            node.setValue(value);
            wMod = true;
            return node.value;
        } else {
            nodes.add(new Node<K, V>(hash, key, value, null));
            wMod = true;
            return null;
        }

    }

    @Override
    public synchronized boolean containsGenKey(K key) {
        return containsKey(key);
    }

    @Override
    public synchronized boolean containsKey(Object key) {

        return containsKey(hash(key), key);
    }

    public synchronized boolean containsKey(int hash, Object key) {
        return get(key) != null;
    }

    @Override
    public synchronized V get(Object key) {
        int hash = hash(key);

        Node<K, V> fNode = nodes.getFirst();
        if (fNode != null && ((fNode.hash == hash && fNode.key.equals(key)) || ((fNode = nodes.getLast()).key.equals(key) && fNode.hash == hash))) {
            return fNode.value;
        } else if (fNode == null) {
            return null;
        }

        for (Node<K, V> node : nodes) {
            if (node.hash == hash && node.getKey().equals(key))
                return node.getValue();
        }
        return null;
    }

    @Override
    public synchronized int size() {
        return nodes.length();
    }

    public synchronized Arrays<SimpleNodeOff<K, V>> getNodesOff() {
        if (wMod == true) {
            for (Node<K, V> node : nodes) {
                nodeOff.add(new SimpleNodeOff<K, V>(node.getKey(), node.getValue()));
            }
            wMod = false;
        }
        return nodeOff;
    }

    @Override
    public synchronized Set<java.util.Map.Entry<K, V>> entrySet() {

        Set<Map.Entry<K, V>> entrySet = new HashSet<>();
        getNodesOff().forEach(n -> entrySet.add(new SimpleEntry<K, V>(n.getKey(), n.getValue())));
        return entrySet;
    }

    @Override
    protected synchronized MapContainer.Node<K, V> getNode(int hash, K key) {
        Node<K, V> fNode = nodes.getFirst();
        if (fNode != null && (fNode.equals(key) || (fNode = nodes.getLast()).equals(key))) {
            return fNode;
        } else if (fNode == null) {
            return null;
        }

        for (Node<K, V> node : nodes) {
            if (node.hash == hash && node.getKey().equals(key))
                return node;
        }
        return null;
    }

    @Override
    protected synchronized V removef(K key) {
        V val = getNode(hash(key), key).getValue();

        removeNode(hash(key), key, null);

        return val;
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized V remove(Object key) {
        return removef((K) key);
    }

    @Override
    public synchronized void forEach(BiConsumer<? super K, ? super V> action) {
        for (SimpleNodeOff<K, V> sno : getNodesOff()) {
            action.accept(sno.getKey(), sno.getValue());
        }
    }

    @Override
    protected synchronized boolean removeNode(int hash, K key, V value) {
        for (Node<K, V> node : nodes) {
            if (node.hash == hash && node.getKey().equals(key) && (value == null || node.getValue().equals(value))) {
                nodes.remove(node);
                update();
            }
            return true;
        }
        return false;
    }

    private void update() {
        wMod = true;
    }

    @Override
    public String toString() {
        Iterator<SimpleNodeOff<K, V>> i = getNodesOff().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            SimpleNodeOff<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key == this ? "(this JwMap)" : key);
            sb.append('=');
            sb.append(value == this ? "(this JwMap)" : value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}