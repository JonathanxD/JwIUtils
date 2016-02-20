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
package com.github.jonathanxd.extend.listinteraction.integer;

import com.github.jonathanxd.extend.list.IAListInteraction;
import com.github.jonathanxd.extend.list.data.IAData;
import com.github.jonathanxd.iutils.exceptions.JwIUtilsRuntimeException;

public class IntegerDivision implements IAListInteraction<Integer> {
	
	@Override
	public Integer and(Integer currentValue, IAData<Integer> inputValue) {
		if(inputValue.get() == 0){
			throw new JwIUtilsRuntimeException(IntegerMultiplier.class, "Invalid input value, input value cannot be 0.", -2);
		}
		return currentValue / inputValue.get();
	}

}
