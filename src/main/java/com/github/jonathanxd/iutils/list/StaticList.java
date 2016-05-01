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
package com.github.jonathanxd.iutils.list;

import com.github.jonathanxd.iutils.containers.list.IndexedListContainer;
import com.github.jonathanxd.iutils.containers.Container;
import com.github.jonathanxd.iutils.iterator.BackableIterator;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;

/**
 * Static list is an max-index-defined list, and elements can be set to certain index, works like a java array.
 * 
 * @author jonathan
 *
 */
public class StaticList<T> implements IndexedListContainer<T>, Iterable<T>{
	
	private final int size;
	private final T[] values;
	private final Class<? extends T> clazz;
	
	/**
	 * Cache the empty slots for a fast-add function.
	 */
	private int slotCache = 0;
	
	
	// ? SUPER = QUALQUER CLASSE PAI DE T
	// ? EXTENDS = QUALQUER CLASSE FILHA DE T
	@SuppressWarnings("unchecked")
	StaticList(Class<? extends T> clazz, int size) {
		this.size = size;
		this.clazz = clazz;
		this.values = (T[]) Array.newInstance(clazz, this.size);
	}
	
	public static <T> StaticList<T> createStaticListOf(final Class<? extends T> listType, final int listSize){
		if(listSize <= 0){			
			throw new RuntimeException("Cannot create a static list with 0 or negative size!");
		}
		return new StaticList<>(Objects.requireNonNull(listType), listSize);
	}
	
	public T[] getValues() {
		@SuppressWarnings("unchecked")
		T[] dest = (T[]) Array.newInstance(clazz, this.size);
		System.arraycopy(values, 0, dest, 0, size);
		return dest;
	}
	
	public T[] __unsecureGetValues() {
		return values;
	}
	/**
	 * Indexer
	 */
	@Override
	public boolean add(T element) {
		int caching = nextEmptySlot();
		if(caching != -1){
			values[caching] = element;
			nextEmptySlot();
			return true;
		}
		return false;
	}

	/**
	 * {Indexer}
	 * Add element to first empty index, or to last index.
	 */
	@Override
	public Container<T> addAndHold(T element) {
		int caching = nextEmptySlot();
		
		if(caching == -1){
			caching = size-1;
		}
		
		T oldValue = values[caching];
		values[caching] = element;
		nextEmptySlot();
		return oldValue != null ? Container.of(oldValue) : Container.empty();
	}

	/**
	 * Forced Indexer
	 */
	@Override
	public boolean remove(T element) {
		for(int x = 0; x < size; ++x){
			if(values[x] != null && values[x].equals(element)){
				values[x] = null;
				slotCache = x;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isEmpty() {		
		return emptySlots() == values.length;
	}

	@Override
	public int emptySlots() {
		int empty = 0;
		for(int x = 0; x < size; ++x){
			if(values[x] == null){
				++empty;
			}
		}
		return empty;
	}

	@Override
	public boolean isFull() {
		return nextEmptySlot() == -1;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Indexer
	 */
	@Override
	public boolean add(int index, T element) {
		checkIndex(index);
		
		if(values[index] == null){
			values[index] = element;
			nextEmptySlot();
			return true;
		}		
		return false;
	}

	/**
	 * Indexer
	 */
	@Override
	public Container<T> addAndHold(int index, T element) {
		checkIndex(index);
		
		T old = values[index];
		values[index] = element;
		nextEmptySlot();
		
		return (old == null ? Container.empty() : Container.of(old));
	}

	/**
	 * Forced Indexer
	 */
	@Override
	public boolean remove(int index) {
		checkIndex(index);
		
		if(values[index] == null){
			return false;
		}
		values[index] = null;
		slotCache = index;
		return true;
	}
	
	/**
	 * Forced Indexer
	 */
	@Override
	public Container<T> removeAndHold(int index) {
		checkIndex(index);
		
		if(values[index] == null){
			return Container.empty();
		}
		T old = values[index];
		values[index] = null;
		slotCache = index;
		return Container.of(old);
	}

	/**
	 * Adds a element if has an empty slot.
	 * @return
	 */
	@Override
	public boolean hasEmptySlot(){
		return nextEmptySlot() != -1;
	}

	/**
	 * Indexer
	 */
	@Override
	public int nextEmptySlot() {
		if(slotCache > -1 && slotCache < values.length){
			if(values[slotCache] == null){
				return slotCache;
			}
		}
		for(int x = 0; x < values.length; ++x){
			if(values[x] == null){
				slotCache = x;
				return x;
			}			
		}
		return -1;
	}

	@Override
	public Iterator<T> iterator() {
		
		return new Iter();
	}
	
	private final class Iter implements BackableIterator<T> {

		private int index = -1;
		
		@Override
		public boolean hasNext() {			
			return (index + 1) < size;
		}

		@Override
		public T next() {
			return values[++index];
		}

		@Override
		public boolean hasBack() {
			return (index - 1) > -1;
		}

		@Override
		public T back() {
			return values[--index];
		}

		@Override
		public int getIndex() {
			return index;
		}

	}
	
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext()){
            	sb.append(']');
            	sb.append(". Data: [Size: "+size+", Empty Slots: "+emptySlots()+"]");
                return sb.toString();
            }
            sb.append(',').append(' ');
        }        
    }
	
	//************************************//
	
	private void checkIndex(int index) {
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException("Element["+this.toString()+"]. Suggested index: "+index);
		}
		
	}

	/**
	 * Indexer
	 */
	@Override
	public boolean contains(T element) {
		for(int x = 0; x < size; ++x){
			if(values[x] != null && values[x].equals(element)){
				return true;
			}else if(values[x] == null){
				nextEmptySlot();
			}
		}
		return false;
	}

	/**
	 * Indexer
	 */
	@Override
	public Container<T> get(int index) {
		checkIndex(index);
		
		if(values[index] == null){
			updateCache(index);
			return Container.empty();
		}
		return Container.of(values[index]);
	}

	private void updateCache(int index){
		if(values[slotCache] != null && values[index] == null){
			slotCache = index;
		}
	}
	
	public int getSlotCache() {
		return slotCache;
	}
}
