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

import java.util.function.Predicate;

import com.github.jonathanxd.iutils.arrays.Arrays;

public class AList<E> extends Arrays<E>{
	
	
	private final IAListInteraction<E> interactor;

	@SafeVarargs
	public AList(IAListInteraction<E> interactor, E... values) {
		super(values);
		this.interactor = interactor;
	}
	
	/**
	 * Return a list interaction if has match elements, else, return null.
	 * @param predicate
	 * @return
	 */
	public AListInteraction<E> getAllIf(Predicate<E> predicate) {
		
		E pass = null;
		int pos = -1;
		
		for(int x = 0; x < toGenericArray().length; ++x){
			E value = toGenericArray()[x];
			if(predicate.test(value)){
				pass = value;
				pos = x;
				break;
			}
		}
		
		if(pass == null)
			return null;
		
		AListInteraction<E> ali = new AListInteraction<E>(this, interactor, pass);
		
		if(++pos < toGenericArray().length){
			ali.andAllIf(pos, predicate);			
		}
		
		return ali;		
	}
	
	public AListInteraction<E> getAll() {		
		return getAllIf(v -> true);
	}
	
	public AListInteraction<E> get(int index) {
		if(index >= length()){
			throw new IndexOutOfBoundsException(String.valueOf(index)+" out of index in array "+toString()+" [length="+length()+"]");
		}
		
		return new AListInteraction<E>(this, interactor, toGenericArray()[index]);
	}
	
}
