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
package com.github.jonathanxd.iutils.extra.primitivecontainers;

import com.github.jonathanxd.iutils.extra.Container;

public class IntContainer extends Container<Integer>{

	public IntContainer(int i) {
		super(i);
	}
	
	public void add(int i){
		super.set(super.get() + i);
	}
	
	public void add(){
		this.add(1);
	}

	public void remove(int i){
		this.add(-i);
	}
	
	public void remove(){
		this.remove(1);
	}
	
	public void multiply(int i){
		super.set(super.get() * i);
	}

	public void divide(int i){
		super.set(super.get() / i);
	}
	
	public static IntContainer of(int i){
		return new IntContainer(i);
	}
}
