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
package com.github.jonathanxd.iutils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.iterator.BackableIterator;
import com.github.jonathanxd.iutils.iterator.Navigator;
import com.github.jonathanxd.iutils.iterator.SafeBackableIterator;

public class FileReading implements Iterable<String>{
	
	private final File file;
	private final String[] lines;
	
	public FileReading(File file) throws IOException {
		this.file = file;
		
		final Arrays<String> arrays = new Arrays<String>();
		
		Stream<String> stream = Files.lines(file.toPath());
		stream.forEachOrdered(arrays::add);
		stream.close();
		this.lines = arrays.toGenericArray();
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
