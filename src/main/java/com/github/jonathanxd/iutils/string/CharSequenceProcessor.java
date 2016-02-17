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
package com.github.jonathanxd.iutils.string;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharSequenceProcessor implements Iterator<Character>, Comparable<CharSequence>{
	
	private final CharSequence charSequence;
	private Character currentChar;
	private int current = -1;
	
	CharSequenceProcessor(CharSequence charSequence) {
		this.charSequence = charSequence;
	}
		
	CharSequenceProcessor() {
		this.charSequence = "";
	}
	
	@Override
	public synchronized boolean hasNext(){
		return current+1 < charSequence.length();
	}
	
	@Override
	public synchronized Character next(){		
		if(!hasNext()){
			throw new NoSuchElementException();
		}
		return currentChar = charSequence.charAt(++current);
	}
	
	public Character getCurrentChar() {
		return currentChar;
	}

	@Override
	public int compareTo(CharSequence charSequence) {
		int len1 = this.charSequence.length();
        int len2 = charSequence.length();
        int lim = Math.min(len1, len2);
        char v1[] = this.charSequence.toString().toCharArray();
        char v2[] = charSequence.toString().toCharArray();

        int k = 0;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
	}

	public CharSequence getCharSequence() {
		return charSequence;
	}
	
	public int getCurrent() {
		return current;
	}
	
	@Override
	public String toString() {
		return this.charSequence.toString();
	}
	
	public static CharSequenceProcessor createCharSequenceProcessor(String string){
		return new CharSequenceProcessor(string);
	}
}
