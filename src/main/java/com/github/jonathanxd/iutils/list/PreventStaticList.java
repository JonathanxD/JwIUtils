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

import java.util.Objects;

import com.github.jonathanxd.iutils.container.Container;

public class PreventStaticList<T> extends StaticList<T>{

	PreventStaticList(Class<? extends T> clazz, int size) {
		super(clazz, size);
	}

	@Override
	public boolean add(int index, T element) {
		if(!contains(element)){
			return super.add(index, element);
		}
		return false;
	}
	
	@Override
	public boolean add(T element) {
		if(!contains(element)){
			return super.add(element);
		}
		return false;
	}
	
	@Override
	public Container<T> addAndHold(int index, T element) {
		if(!contains(element)){
			return super.addAndHold(index, element);			
		}
		return Container.of(element);
	}
	
	@Override
	public Container<T> addAndHold(T element) {
		if(!contains(element)){
			return super.addAndHold(element);
		}
		return Container.of(element);		
	}

	public static <T> PreventStaticList<T> createStaticListOf(final Class<? extends T> listType, final int listSize){
		if(listSize <= 0){			
			throw new RuntimeException("Cannot create a static list with 0 or negative size!");
		}
		return new PreventStaticList<>(Objects.requireNonNull(listType), listSize);
	}

}
