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
		return arr.toGenericArray(String[].class);
		
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