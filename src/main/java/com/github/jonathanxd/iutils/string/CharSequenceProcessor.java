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
