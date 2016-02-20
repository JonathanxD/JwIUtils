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
package com.github.jonathanxd.iutils.builders;

import java.io.Serializable;
import java.util.Arrays;

import com.github.jonathanxd.iutils.builders.abstracts.AbstractIntegerBuilder;


public class IntegerBuilder extends AbstractIntegerBuilder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6366715270349626407L;

	public IntegerBuilder() {
		super(16);
	}

	public IntegerBuilder(int capacity) {
		super(capacity);
	}
	
	public IntegerBuilder(Integer[] value){
		super(value.length + 16);
		append(value);
	}
	
	@Override
	public String toString() {
		return Arrays.asList(subinteger(0)).toString();
	}
	
	public Integer[] toIntegerArray() {
		return subinteger(0);
	}
}
