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

public class Array2DToTable {
	
	
	public static void printTable(String[][] content){
		
		int maxLength = getMaxLength(content)+3;
		StringBuilder indexes = new StringBuilder("|Index|");
		
		int max = maxElement(content) +1;
		
		for(int x = 0; x < max; ++x){
			indexes.append(String.format("%"+maxLength+"d|", x));
		}
		System.out.println(indexes.toString());
		for(int x = 0; x < content.length; ++x){
			
			String[] dimension1 = content[x];

			StringBuffer sb = new StringBuffer(String.format("|%5d|", x));
			
			for(int y = 0; y < dimension1.length; ++y){
				String value = dimension1[y];
				sb.append(String.format("%"+maxLength+"s|", value));
			}
			System.out.println(sb.toString());

		}
		
	}

	public static String[] getAsTable(String[][] content){
		
		Arrays<String> arr = new Arrays<String>();
		
		int maxLength = getMaxLength(content)+3;
		StringBuilder indexes = new StringBuilder("|Index|");
		
		int max = maxElement(content) +1;
		
		for(int x = 0; x < max; ++x){
			indexes.append(String.format("%"+maxLength+"d|", x));
		}
		arr.add(indexes.toString());
		for(int x = 0; x < content.length; ++x){
			
			String[] dimension1 = content[x];

			StringBuffer sb = new StringBuffer(String.format("|%5d|", x));
			
			for(int y = 0; y < dimension1.length; ++y){
				String value = dimension1[y];
				arr.add(String.format("%"+maxLength+"s|", value));
			}
			System.out.println(sb.toString());

		}
		return arr.toGenericArray();
		
	}

	
	public static int maxElement(String[][] content){
		int maxElement = 0;
		
		for(int x = 0; x < content.length; ++x){
			String[] dimension1 = content[x];
			for(int y = 0; y < dimension1.length; ++y){
				if(y > maxElement){
					maxElement = y;
				}
			}
		}
		return maxElement;

	}
	
	public static int getMaxLength(String[][] content){
		int maxLength = 0;
		
		for(int x = 0; x < content.length; ++x){
			String[] dimension1 = content[x];
			for(int y = 0; y < dimension1.length; ++y){
				String value = dimension1[y];
				if(value.length() > maxLength){
					maxLength = value.length();
				}
			}
		}
		return maxLength;
	}
	
}