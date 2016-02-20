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
