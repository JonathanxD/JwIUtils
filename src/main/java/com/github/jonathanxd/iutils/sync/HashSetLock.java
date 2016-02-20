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
package com.github.jonathanxd.iutils.sync;

import com.github.jonathanxd.iutils.exceptions.ElementLockedException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Spliterator;

public class HashSetLock<E> extends HashSet<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8630709456520761213L;
	HashSet<E> queueQueue = new HashSet<>();

	private boolean isLocked = false;
	
	public void doLock(){
		lock();
	}
	
	void lock(){
		if(isLocked()){
			throw new ElementLockedException(this);
		}
		isLocked = true;
	}
	
	public void doUnlock(){
		unlock();
		transfer();
	}
	
	void unlock(){
		isLocked = false;
	}
	
	public boolean isLocked(){
		return this.isLocked;
	}
	
	@Override
	public boolean add(E e) {
		return add(e, true);
	}
	
	public boolean add(E e, boolean addIfLocked) {
		if(isLocked()){
			if(addIfLocked)return queueQueue.add(e);
			else return false;
		}else{
			return super.add(e);
		}
	}
	
	@Override
	public boolean remove(Object o) {
		if(isLocked()){
			return queueQueue.remove(o);
		}
		return super.remove(o);
	}
	
	@Override
	public void clear() {
		if(isLocked()){
			throw new ElementLockedException(this);
		}
		super.clear();
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if(isLocked()){
			return queueQueue.addAll(c);
		}
		return super.addAll(c);
	}
	
	@Override
	public Iterator<E> iterator() {
		return super.iterator();
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		if(isLocked()){
			throw new ElementLockedException(this);
		}
		return super.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		if(isLocked()){
			throw new ElementLockedException(this);
		}
		return super.retainAll(c);
	}
	@Override
	public Spliterator<E> spliterator() {
		if(isLocked()){
			throw new ElementLockedException(this);
		}
		return super.spliterator();
	}
	
	/**
	 * 
	 * @return If any element has transfered
	 */
	protected boolean transfer() {
		boolean empty = !queueQueue.isEmpty();
		super.addAll(queueQueue);
		queueQueue.clear();
		return empty;
	}
}
