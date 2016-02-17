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
