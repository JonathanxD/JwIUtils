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

public class DoubleContainer extends Container<Double>{

	public DoubleContainer(double d) {
		super(d);
	}
	
	public void add(double d){
		super.set(super.get() + d);
	}
	
	public void add(){
		this.add(1);
	}

	public void remove(double d){
		this.add(-d);
	}
	
	public void remove(){
		this.remove(1);
	}
	
	public void multiply(double d){
		super.set(super.get() * d);
	}

	public void divide(double d){
		super.set(super.get() / d);
	}
	
	public static DoubleContainer of(double d){
		return new DoubleContainer(d);
	}
}
