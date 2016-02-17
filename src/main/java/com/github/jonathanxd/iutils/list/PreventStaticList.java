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
package com.github.jonathanxd.iutils.list;

import java.util.Objects;

import com.github.jonathanxd.iutils.extra.Container;

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
