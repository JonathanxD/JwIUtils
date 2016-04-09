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
package com.github.jonathanxd.extend;

public class AOrResult<T1, T2> {
	
	private final Object value1;
	private Class<?> type1 = null;
	private Class<?> type2 = null;
	
	AOrResult(Object value) {
		this.value1 = value;
	}
	
	public static <T1, T2> AOrResult<T1, T2> of(T1 value1){
		AOrResult<T1, T2> result = new AOrResult<T1, T2>(value1);
		result.type1 = value1.getClass();
		return result;
	}

	public static <T1, T2> AOrResult<T1, T2> of2(T2 value1){
		AOrResult<T1, T2> result = new AOrResult<T1, T2>(value1);
		result.type2 = value1.getClass();
		return result;
	}
	
	public boolean is(Class<?> clazz){
		return clazz.isInstance(this.value1);
	}
	
	public boolean is1(){
		return type1 != null;
	}

	public boolean is2(){
		return type2 != null;
	}
	
	/**
	 * Try to get as value type 1 or value type 2
	 * Return null if fails
	 * @return
	 */
	public Object getValue(){
		
		if(is1()) return get1();
		if(is2()) return get2();
		
		return null;
	}
	
	/**
	 * Return value, or null if isn't a value type 1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T1 get1(){
		if(is1()){
			return (T1) this.value1;
		}
		return null;
	}

	/**
	 * Return value, or null if isn't a value type 2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T2 get2(){
		if(is2()){
			return (T2) this.value1;
		}
		return null;
	}
}
