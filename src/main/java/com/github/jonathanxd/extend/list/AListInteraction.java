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
package com.github.jonathanxd.extend.list;

import com.github.jonathanxd.extend.AResult;
import com.github.jonathanxd.extend.Task;
import com.github.jonathanxd.extend.list.data.AType;
import com.github.jonathanxd.extend.list.data.IAData;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.containers.MutableContainer;

//TODO FIX THE FAIL IDENTICAL VALUE PROBLEM
public class AListInteraction<T> {
	private final IAListInteraction<T> interactor;
	private final AList<T> arraysInstance;

	Map<MutableContainer<T>, Task> vals = new HashMap<>();

	private boolean ends = false;

	AListInteraction(AList<T> arraysInstance, IAListInteraction<T> interactor, T value) {
		
		this.interactor = Objects.requireNonNull(interactor);
		this.arraysInstance = Objects.requireNonNull(arraysInstance);
		vals.put(MutableContainer.of(Objects.requireNonNull(value)), Task.NOTHING);
	}
	
	public AListInteraction<T> fireFor(Function<IAData<T>, T> func) {
		checkClose();

		Map<MutableContainer<T>, Task> vals2 = new HashMap<>();

		Iterator<Map.Entry<MutableContainer<T>, Task>> ite = vals.entrySet().iterator();



		boolean first = true;
		while (ite.hasNext()) {
			Map.Entry<MutableContainer<T>, Task> node = ite.next();
			
			IAData<T> data;
			if(first){
				data = new Data<>(AType.FIRST_ELEMENT, node.getKey().getValue());
				first = false;
			}else if(ite.hasNext()){
				data = new Data<>(AType.OTHER, node.getKey().getValue());
			}else{
				data = new Data<>(AType.LAST_ELEMENT, node.getKey().getValue());
			}
			
			vals2.put(MutableContainer.of(func.apply(data)), node.getValue());
		}
		vals = vals2;
		preClose();
		return this;
	}

	public AListInteraction<T> and(int index) {
		checkAllClose();
		vals.put(MutableContainer.of(arraysInstance.toGenericArray()[index]), Task.AND);
		return this;
	}
	
	public AListInteraction<T> andAll(int start){
		checkAllClose();
		for(int x = start; x < arraysInstance.length(); ++x){
			and(x);				
		}
		return this;
	}
	
	public AListInteraction<T> andAllIf(int start, Predicate<T> predicate){
		checkAllClose();
		for(int x = start; x < arraysInstance.length(); ++x){
			if(predicate.test(arraysInstance.toGenericArray()[x])){
				and(x);				
			}
		}
		return this;		
	}


	/**
	 * Close the interactor and return the result.
	 * 
	 * @return
	 */
	public T build() {
		checkClose();
		return finish().getValue2();
	}

	/**
	 * Close the interactor and return list of selected values, and result.
	 * 
	 * @return
	 */
	public AResult<Arrays<T>, T> finish() {
		checkClose();
		T value = null;

		Arrays<T> processed = new Arrays<T>();
		Iterator<Map.Entry<MutableContainer<T>, Task>> ite = vals.entrySet().iterator();
		boolean first = true;
		while (ite.hasNext()) {
			
			Map.Entry<MutableContainer<T>, Task> node = ite.next();
			
			AType type;
			
			if(first){
				type = AType.FIRST_ELEMENT;
				first = false;
			}else if(ite.hasNext()){
				type = AType.OTHER;
			}else{
				type = AType.LAST_ELEMENT;
			}
			IAData<T> cData = new Data<T>(type, node.getKey().getValue());
			if(!interactor.filter(value, cData)){
				continue;
			}
			
			switch (node.getValue()) {
				case NOTHING: {
					if (value == null) {
						value = node.getKey().getValue();
					} else {
						throw new RuntimeException("Error, invalid map received for build, is it closed?");
					}
					break;
				}
				case AND: {
					value = interactor.and(value, cData);
					break;
				}
			}
			processed.add(node.getKey().getValue());
		}
		
		close();
		return new AResult<Arrays<T>, T>(processed, value);
	}
	
	private void checkPreClose(){
		if(ends){
			throw new RuntimeException("Pre-Closed interactor (only close and pre-close operators is available)!");
		}
	}
	
	private void checkAllClose(){
		checkClose();
		checkPreClose();
	}
	
	private void checkClose(){
		if(interactor == null || arraysInstance == null || (vals == null || vals.isEmpty())){
			throw new RuntimeException("Closed interactor!");
		}
	}
	
	private boolean bcheckClose(){
		if(interactor == null || arraysInstance == null || (vals == null || vals.isEmpty())){
			return true;
		}
		return false;
	}
	
	private void close() {
		if (!bcheckClose()) {
			ends = true;
			try {
				Field field = this.getClass().getDeclaredField("vals");
				field.setAccessible(true);

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

				field.set(this, null);
			} catch (Exception e) {
			}
			try {
				Field field = this.getClass().getDeclaredField("arraysInstance");
				field.setAccessible(true);

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

				field.set(this, null);
			} catch (Exception e) {
			}
			try {
				Field field = this.getClass().getDeclaredField("interactor");
				field.setAccessible(true);

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

				field.set(this, null);
			} catch (Exception e) {
			}
		}else{
			throw new RuntimeException("Cannot close already closed interactor!");
		}
	}

	private void preClose() {
		if(!ends){
			ends = true;
		}else{
			throw new RuntimeException("Cannot pre-close already pre-closed interactor!");
		}
	}

	
	protected static class Data<T> implements IAData<T>{

		private final T value;
		private final AType atype;
		
		public Data(AType atype, T value) {
			this.atype = atype;
			this.value = value;
		}

		@Override
		public AType getType() {
			return this.atype;
		}

		@Override
		public T get() {
			return this.value;
		}
		
		
	}
	
}
