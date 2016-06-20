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
package com.github.jonathanxd.iutils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.jonathanxd.iutils.arrays.JwArray;
import com.github.jonathanxd.iutils.iterator.BackableIterator;
import com.github.jonathanxd.iutils.iterator.Navigator;
import com.github.jonathanxd.iutils.iterator.SafeBackableIterator;

public class FileReading implements Iterable<String>{
	
	private final File file;
	private final String[] lines;
	
	public FileReading(File file) throws IOException {
		this.file = file;
		
		final JwArray<String> jwArray = new JwArray<String>();
		
		Stream<String> stream = Files.lines(file.toPath());
		stream.forEachOrdered(jwArray::add);
		stream.close();
		this.lines = jwArray.toGenericArray();
	}
	
	
	public File getFile() {
		return file;
	}


	@Override
	public Iterator<String> iterator() {
		return new Iter();
	}
	
	public BackableIterator<String> backableIterator(){
		return new Iter();
	}

	public SafeBackableIterator<String> safeBackableIterator(){
		return new Iter();
	}
	
	
	private class Iter implements SafeBackableIterator<String> {

		int index = -1;
		
		@Override
		public boolean hasNext() {
			return (index + 1) < lines.length;
		}

		@Override
		public String next() {			
			return lines[++index];
		}

		@Override
		public boolean hasBack() {
			return (index -1) > -1;
		}

		@Override
		public String back() {
			return lines[--index];
		}

		@Override
		public int getIndex() {
			return index;
		}

		@Override
		public Navigator<String> safeNavigate() {
			return new Navigator<String>() {
				
				@Override
				public String navigateTo(int index) {
					return lines[index];
				}
				
				@Override
				public boolean has(int index) {
					return index > -1 && index < lines.length;
				}
				
				@Override
				public String currentValue() {
					return lines[index];
				}
				
				@Override
				public int currentIndex() {
					return index;
				}

				@Override
				public List<String> collect(int to) {
					int currentIndex = index;

					List<String> list = new ArrayList<>();
					to = to + currentIndex;
					do {
						list.add(navigateTo(currentIndex));
						++currentIndex;
					}
					while (has(currentIndex) && currentIndex < to);

					return list;
				}

				@Override
				public void goNextWhen(Predicate<String> predicate) {
					int internalIndex = index;
					
					while(has(internalIndex) && predicate.test(navigateTo(internalIndex))){
						++internalIndex;
					}
					
				}
			};
		}
		
	}
	
	

}
