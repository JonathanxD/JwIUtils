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

public class BooleanContainer extends Container<Boolean>{

	public BooleanContainer(boolean b) {
		super(b);
	}
	
	public void toogle(){
		super.set(!super.get());
	}
	
	public void toFalse(){
		super.set(Boolean.FALSE);
	}

	public void toTrue(){
		super.set(Boolean.TRUE);
	}
	
	public boolean toBoolean(){
		return super.get().booleanValue();
	}
}
