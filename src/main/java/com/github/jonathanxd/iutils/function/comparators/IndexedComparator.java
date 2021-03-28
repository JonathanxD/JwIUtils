/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.function.comparators;

/**
 * A comparator which receives two objects to compare and the index of the objects.
 *
 * @param <T> Type of objects.
 */
@FunctionalInterface
public interface IndexedComparator<T> {

    /**
     * Compares {@code o1} and {@code o2} and return a negative int, zero or positive int if {@code
     * o1} is less than {@code o2}, {@code o1} is equal to {@code o2} or {@code o1} is greater than
     * {@code o2} respectively.
     *
     * @param o1Index Index of {@code o1}
     * @param o1      First object to compare.
     * @param o2Index Index of {@code o2}.
     * @param o2      Second object to compare.
     * @return Negative int, zero or positive int if {@code o1} is less than {@code o2}, {@code o1}
     * is equal to {@code o2} or {@code o1} is greater than {@code o2} respectively.
     */
    int compare(int o1Index, T o1, int o2Index, T o2);

    /**
     * Returns reversed comparator.
     *
     * @return Reversed comparator.
     */
    default IndexedComparator<T> reversed() {
        return new IndexedComparator<T>() {
            @Override
            public int compare(int o1Index, T o1, int o2Index, T o2) {
                return IndexedComparator.this.compare(o2Index, o2, o1Index, o1);
            }

            @Override
            public IndexedComparator<T> reversed() {
                return IndexedComparator.this;
            }
        };
    }
}
