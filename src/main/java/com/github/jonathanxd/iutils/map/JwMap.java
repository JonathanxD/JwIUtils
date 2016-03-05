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