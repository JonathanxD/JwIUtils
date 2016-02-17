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

import java.util.List;

public class ListUtils {

	public static <E> E listNotContains(List<E> list, E[] values){
		for(E current2 : values){
			if(!list.contains(current2)){
				return current2;
			}
		}
		return null;
	}
	
	public static <E> E listNotContains(List<E> list, E[] values, List<E> exclude){
		for(E current2 : values){
			if(!list.contains(current2) && !exclude.contains(current2)){
				return current2;
			}
		}
		return null;
	}
	
}
