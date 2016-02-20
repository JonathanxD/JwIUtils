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
package com.github.jonathanxd.iutils.loop;

public abstract class ElementLoopWay<E> {		
	
	boolean continue_ = true;
	
	public final void loopIn(E[] elements){
		int size = elements.length;
		int x1 = 0, x2;
		int y1 = size-1, y2;
		int pointStop;
		int loops = 1;
		continue_ = true;
		
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
		
		
		while(loops <= size && continue_){
			if(!point && x1 != x2){
				elementLoop(elements[x1], x1);
				++loops;
				++x1;
			}else if(y1 != y2){
				elementLoop(elements[y1], y1);
				++loops;
				--y1;
			}
			point = !point;
		}		
	}
	
	public final void breakLoop(){
		continue_ = false;
	}
	
	public abstract void elementLoop(E element, int index);
}
