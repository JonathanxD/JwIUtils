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
package com.github.jonathanxd.iutils.arrays;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.github.jonathanxd.iutils.extra.MutableContainer;

public class ArrayCalculator {
	
	public static Short calculateShort(Arrays<Short> arrays){
		final MutableContainer<Short> val = new MutableContainer<Short>((short) 0);
		
		arrays.forEach(v -> val.set((short) (val.get() + v)));
		
		return val.get();
	}
	
	public static Integer calculateInt(Arrays<Integer> arrays){
		final AtomicInteger val = new AtomicInteger(0);

        arrays.forEach(val::addAndGet);
		
		return val.get();
	}
	
	public static Long calculateLong(Arrays<Long> arrays){
		final AtomicLong val = new AtomicLong(0);
		
		arrays.forEach(val::addAndGet);
		
		return val.get();
	}
	
	public static Float calculateFloat(Arrays<Float> arrays){
		final MutableContainer<Float> val = new MutableContainer<>(0f);
		
		arrays.forEach(v -> val.set(val.get()+v));
		
		return val.get();
	}

	public static Double calculateDouble(Arrays<Double> arrays){
		final MutableContainer<Double> val = new MutableContainer<Double>(0d);
		
		arrays.forEach(v -> val.set(val.get()+v));
		
		return val.get();
	}

}
