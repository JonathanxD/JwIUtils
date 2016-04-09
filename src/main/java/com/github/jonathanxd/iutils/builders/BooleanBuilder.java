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
package com.github.jonathanxd.iutils.builders;

import java.io.Serializable;
import java.util.Arrays;

import com.github.jonathanxd.iutils.builders.abstracts.AbstractBooleanBuilder;


public class BooleanBuilder extends AbstractBooleanBuilder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2956071802768088685L;

	public BooleanBuilder() {
		super(16);
	}

	public BooleanBuilder(int capacity) {
		super(capacity);
	}
	
	public BooleanBuilder(Boolean[] value){
		super(value.length + 16);
		append(value);
	}
	
	@Override
	public String toString() {
		return Arrays.asList(subboolean(0)).toString();
	}
	
	public Boolean[] toBooleanArray(){
		return subboolean(0);
	}
}
