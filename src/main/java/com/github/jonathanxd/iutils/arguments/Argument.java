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
package com.github.jonathanxd.iutils.arguments;

import com.github.jonathanxd.iutils.javatypes.JavaTypes;

public class Argument<T> {
	
	private static final Argument<JavaTypes> nullArgument = new Argument<JavaTypes>(JavaTypes.Null);
	private final T value;
	
	public Argument(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public static <T> Argument<T> of(T object){
		return new Argument<T>(object);
	}
	
	public static Argument<JavaTypes> getNullArgument(){
		return nullArgument;
	}
	
}
