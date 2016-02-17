/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Optimized Version of {@link Map}, the method get is faster than {@link HashMap#get(Object)}
 * and the put method is faster than {@link Map#put(Object, Object)}, but more slowly than {@link HashMap#put(Object, Object)}
 * 
 * @author jonathan
 *
 * @param <K>
 * @param <V>
 * 
 * @since 1.1
 */
public class FastMap<K, V> extends MapContainer<K, V>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8307678606769422541L;
		
	
	protected final Arrays<Node<K, V>> nodes = new Arrays<Node<K,V>>();
	protected Arrays<SimpleNodeOff<K, V>> nodeOff = new Arrays<SimpleNodeOff<K,V>>();
	protected boolean wMod = false;
	
	public FastMap() {
		
	}
	public FastMap(MapRegistry register) {
		register.init(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized V putInit(Object key, Object value) {
		if(Reflection.isOnClassInit(FastMap.class)){
			update();
			return put((K) key, (V) value);
		}else{
			throw new RuntimeException("Cannot put objects out of initialization.");
		}
	}
	
	@Override
	public synchronized V put(K key, V value) {
		Node<K, V> node;
		int hash = hash(key);
		
		if(nodes.length() > 0){
			node = nodes.getFirst();
			wMod = true;
			if(node.hash == hash && node.key.equals(key)){
				V oldValue = node.value;
				node.setValue(value);
				return oldValue;
			}
			while(node.next != null){
				node = node.next;
				if(node.hash == hash && node.key.equals(key)){
					V oldValue = node.value;
					node.setValue(value);
					return oldValue;
				}
			}
			node.next = new Node<K, V>(hash, key, value, null);
		}else{
			nodes.add(new Node<K, V>(hash, key, value, null)); // Create first element;
			wMod = true;			
		}
		return null;
	}
	
	@Override
	public synchronized boolean containsGenKey(K key){
		return containsKey(key);
	}
	
	@Override
	public synchronized boolean containsKey(Object key) {

		return containsKey(hash(key), key);
	}
	
	public synchronized boolean containsKey(int hash, Object key){
		Node<K, V> node;
		if(nodes.length() > 0){
			node = nodes.getFirst();
			if(node.key.equals(key)){
				return true;
			}
			while(node.next != null){
				node = node.next;
				if(node.key.equals(key)){
					return true;
				}
			}
		}
		return false;		
	}
	
	@Override
	public synchronized V get(Object key) {
		
		Node<K, V> node;
		int hash = hash(key);
		
		if(nodes.length() > 0){
			node = nodes.getFirst();
			if(node.hash == hash && node.key.equals(key)){
				return node.value;
			}
			while(node.next != null){
				node = node.next;
				if(node.hash == hash && node.key.equals(key)){
					return node.value;
				}
			}
		}
		return null;
	}
	
	@Override
	public synchronized int size() {
		return nodes.length();
	}
	
	public synchronized Arrays<SimpleNodeOff<K, V>> getNodesOff(){
		if(wMod == true){
			Node<K, V> node = nodes.getFirst();
			nodeOff.add(new SimpleNodeOff<K, V>(node.getKey(), node.getValue()));
			while((node = node.next) != null){
				nodeOff.add(new SimpleNodeOff<K, V>(node.getKey(), node.getValue()));
			}
			wMod = false;
		}
		return nodeOff; 
	}
	
	@Override
	public synchronized Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException("Use getNodesOff instead");
	}
	
	@Override
	protected synchronized MapContainer.Node<K, V> getNode(int hash, K key) {
		Node<K, V> fNode = nodes.getFirst();		
		if(fNode != null && ((fNode.hash == hash && fNode.key.equals(key)) || ( (fNode = nodes.getLast()).key.equals(key) && fNode.hash == hash ))){
			return fNode;
		}else if(fNode != null){
			while((fNode = fNode.next) != null){
				if(fNode.hash == hash && fNode.getKey().equals(key)){
					return fNode;
				}
			}
		}else if(fNode == null){
			return null;
		}
		
		return null;
	}
	
	@Override
	protected synchronized V removef(K key) {
		int hash = hash(key);
		V val = getNode(hash, key).getValue();
		
		removeNode(hash, key, null);
		
		return val;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized V remove(Object key) {
		return removef((K) key);
	}
	
	@Override
	public synchronized void forEach(BiConsumer<? super K, ? super V> action) {				
		for(SimpleNodeOff<K, V> sno : getNodesOff()){
			action.accept(sno.getKey(), sno.getValue());
		}
	}

	@Override
	protected synchronized boolean removeNode(int hash, K key, V value) {
		
		Node<K, V> fNode = nodes.getFirst();		
		if(fNode != null && ((fNode.hash == hash && fNode.key.equals(key) && (value == null || fNode.value.equals(value))) || ( (fNode = nodes.getLast()).key.equals(key) && fNode.hash == hash && (value == null || fNode.value.equals(value)) ))){
			nodes.remove(fNode);
		}else if(fNode != null){
			while(fNode.next != null){
				if(fNode.next.hash == hash && (fNode.next.key.equals(key) && (value == null || fNode.next.value.equals(value)))){
					fNode.next = null;
					return true;
				}
				fNode = fNode.next;
			}
		}else if(fNode == null){
			return false;
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
		for (;;) {
			SimpleNodeOff<K, V> e = i.next();
			K key = e.getKey();
			V value = e.getValue();
			sb.append(key == this ? "(this Map)" : key);
			sb.append('=');
			sb.append(value == this ? "(this Map)" : value);
			if (!i.hasNext())
				return sb.append('}').toString();
			sb.append(',').append(' ');
		}
	}

}