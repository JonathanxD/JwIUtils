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

import com.github.jonathanxd.iutils.exceptions.JwIUtilsRuntimeException;

public class StringUtils {
	
	private String string;
	
	public StringUtils(String string) {
		this.string = string;
	}
	
	public StringUtils capitalizeWords(){
		StringBuilder sb = new StringBuilder();
		
		for(String current : string.split(" ")){
			if(current.trim().isEmpty()){
				sb.append(current).append(" ");
			}else{
				
				StringBuilder capitalize = new StringBuilder(current);
				for(int x = 0; x < current.length(); ++x){
					char c;
					if(x == 0){
						c = Character.toUpperCase(current.charAt(x));
					}else{
						c = Character.toLowerCase(current.charAt(x));
					}
					capitalize.setCharAt(x, c);
				}
				
				sb.append(capitalize).append(" ");				
			}
		}	
		this.string = sb.toString().endsWith(" ") ? sb.toString().substring(0, sb.length()-1) : sb.toString();
		return this;
	}
	
	public StringUtils firstWordUnderlineRemove(){
		StringBuilder sb = new StringBuilder(this.string);
		
		CharSequenceProcessor processor = CharSequenceProcessor.createCharSequenceProcessor(this.string);
		
		boolean underline = false;
		while(processor.hasNext()){
			Character character = processor.next();
			if(character.equals('_') && !underline){
				underline = true;
				sb.setCharAt(processor.getCurrent(), ' ');
			}else{
				underline = false;
			}
		}
		
		this.string = sb.toString();
		return this;
	}
	
	@Override
	public String toString() {		
		return this.string;
	}
	
	/**
	 * {@link StringUtils#toString()}
	 * @return
	 */
	public String getString() {
		return this.toString();
	}
	
	public static String sCapitalizeWords(String string){
		return new StringUtils(string).capitalizeWords().getString();
	}

	public static String sFirstWordUnderlineRemover(String s){
		return new StringUtils(s).capitalizeWords().getString();
	}
	public static String reverseSubstring(String input, int length){
		if(input.length() - length < 0){
			throw new JwIUtilsRuntimeException(StringUtils.class, "Invalid length "+length+" for input string [length="+input.length()+"]");
		}
		
		return input.substring(input.length() - length);
	}	
}
