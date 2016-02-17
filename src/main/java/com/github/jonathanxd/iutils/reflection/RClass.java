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
package com.github.jonathanxd.iutils.reflection;

public class RClass {
	
	private Class<?> clazz;
	private Object obj = null;
	
	RClass(Class<?> classRef, Object object) {
		this.clazz = classRef;
		this.obj = object;
	}
	
	public Class<?> getClassRef() {
		return clazz;
	}
	
	public Object getObjectRef() {
		return obj;
	}
	
	public static RClass getRClass(Class<?> clazz){
		return new RClass(clazz, null);
	}

	public static RClass getRClass(Object classInstance){
		return new RClass(classInstance.getClass(), classInstance);
	}

	public static RClass getRClass(Class<?> clazz, Object classInstance){
		return new RClass(clazz, classInstance);
	}

}
