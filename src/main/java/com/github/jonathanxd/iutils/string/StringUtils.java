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
