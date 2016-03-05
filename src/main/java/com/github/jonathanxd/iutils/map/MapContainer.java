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

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class MapContainer<K, V> extends AbstractMap<K, V> implements Cloneable, Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5274944497358783305L;
	
	/**
	 * Contains Generic Key
	 * @param key generic key
	 * @return true if contains | false if don't contains
	 */
	protected abstract boolean containsGenKey(K key);
	
	/**
	 * Get node reference from <K> key
	 * @param key
	 * @return
	 */
	protected abstract Node<K, V> getNode(int hash, K key);
	
	protected abstract V putInit(Object key, Object value);
	
	protected abstract boolean removeNode(int hash, K key, V value);
	
	protected abstract V removef(K key);
	/**
	 * Hash
	 * @author Copy of {@link java.util.HashMap#hash(Object)}
	 *
	 * @param <K>
	 * @param <V>
	 */
	protected static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
	
	/**
	 * JwMap node
	 * @author Copy of {@link java.util.HashMap.Node}
	 *
	 * @param <K>
	 * @param <V>
	 */
	protected static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }
	
	
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}
}
