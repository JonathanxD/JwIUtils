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
package com.github.jonathanxd.iutils.loop;

public abstract class IndexLoopWay<E> {	

	public final void loopIn(int fullLength){
		int size = fullLength;
		
		
		int x1 = 0, x2;
		int y1 = size-1, y2;
		int pointStop;
		int loops = 1;
		
		boolean point = false;
		
		if(size % 2 == 0){
			pointStop = size/2;
			x2 = pointStop;
			y2 = x2-1;
		}else{
			pointStop = size/2;
			x2 = pointStop;
			y2 = x2-1;
		}
		
		while(loops <= size){
			if(!point && x1 != x2){				
				indexLoop(x1);
				loops++;
				++x1;
			}else if(y1 != y2){
				indexLoop(y1);
				loops++;
				--y1;
			}
			point = !point;
		}
		
	}
	
	public abstract void indexLoop(int index);
}
