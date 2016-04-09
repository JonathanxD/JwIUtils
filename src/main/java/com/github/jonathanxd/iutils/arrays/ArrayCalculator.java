/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
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
