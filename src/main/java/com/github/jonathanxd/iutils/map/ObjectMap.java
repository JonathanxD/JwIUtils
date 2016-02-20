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

import com.github.jonathanxd.iutils.extra.SetOf;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.extra.Container;

/**
 * Define multiple keys
 * @author jonathan
 *
 */
public class ObjectMap implements java.util.Map<Object, Object>{

	Arrays<Node<Object, Object>> nodes = new Arrays<Node<Object, Object>>();
	
	@Override
	public int size() {
		return nodes.length();
	}

	@Override
	public boolean isEmpty() {
		return nodes.length() > 0;
	}

	@Override
	public boolean containsKey(Object key) {		
		return getNode(hash(key), key).isPresent();
		
	}

	@Override
	public boolean containsValue(Object value) {
		Optional<Node<Object, Object>> opt = nodes.stream().filter(entry -> entry.value == value).findFirst();
		return opt.isPresent();
	}

	/**
	 * Get first element
	 * @param key
	 * @return
	 */
	@Override
	public Object get(Object key) {		
		return getNode(hash(key), key).getValue();
	}

	/**
	 * Add net element
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Object put(Object key, Object value) {
		nodes.add(new Node<Object, Object>(hash(key), key, value, null));
		return key;
	}

	@Override
	public Object remove(Object key) {
				
		Container<Node<Object, Object>> node = getNode(hash(key), key);
		if(!node.isPresent())
			return null;
		
		nodes.remove(node.get());
		return node.get().value;
	}

	@Override
	public void putAll(Map<? extends Object, ? extends Object> m) {
		m.forEach((k, v) ->{
			put(k, v);
		});
		
	}

	@Override
	public void clear() {
		nodes = new Arrays<>();
		
	}

	@Override
	public Set<Object> keySet() {
		final HashSet<Object> set = new HashSet<>();
		
		nodes.forEach((node) -> {
			set.add(node.key);
		});
		
		return set;
	}

	@Override
	public Collection<Object> values() {
		final Arrays<Object> arr = new Arrays<>();
		
		nodes.forEach((node) -> {
			arr.add(node.value);
		});
		
		return arr.toCollection();
	}

	@Override
	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return SetOf.setOf(nodes.toGenericArray());
	}
	
	public Container<Node<Object, Object>> getNode(int hash, Object key){
		
		final Container<Node<Object, Object>> result = Container.empty();
		Optional<Node<Object, Object>> opt = nodes.stream().filter(entry -> entry.hash == hash && entry.key.equals(key)).findFirst();
		if(opt.isPresent()){
			result.set(opt.get());				
		}
		return result;

	}
	
	protected static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
	
	/**
	 * Map node
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
}
