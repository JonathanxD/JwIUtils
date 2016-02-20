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
package com.github.jonathanxd.iutils.sequence;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public interface DoubleSequence {
	
	int length();
	
	Double doubleAt(int index);
	
	DoubleSequence subSequence(int start, int end);
	
	public String toString();
	
	public default DoubleStream ints() {
        class DoubleIterator implements PrimitiveIterator.OfDouble {
            int cur = 0;

            public boolean hasNext() {
                return cur < length();
            }

            public double nextDouble() {
                if (hasNext()) {
                    return doubleAt(cur++);
                } else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void forEachRemaining(DoubleConsumer block) {
                for (; cur < length(); cur++) {
                    block.accept(doubleAt(cur));
                }
            }
        }

        return StreamSupport.doubleStream(() ->
                Spliterators.spliterator(
                        new DoubleIterator(),
                        length(),
                        Spliterator.ORDERED),
                Spliterator.SUBSIZED | Spliterator.SIZED | Spliterator.ORDERED,
                false);
    }
	
	
}
