/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents sorted elements.
 *
 * @param <T> Type of sorted.
 */
public final class SortingResult<T> {

    /**
     * Sorted elements.
     */
    private final List<T> sortedElements;

    /**
     * Creates a sorting result.
     *
     * @param elements Sorted list of elements.
     */
    public SortingResult(List<T> elements) {
        this.sortedElements = Collections.unmodifiableList(elements);
    }

    /**
     * Gets the last (minimum) element of sorted list.
     *
     * @return {@link Optional} of last (minimum) element of sorted list, or a empty optional if
     * there is no element in the list.
     */
    public Optional<T> min() {

        if (this.sortedElements.isEmpty())
            return Optional.empty();

        return Optional.of(sortedElements.get(sortedElements.size() - 1));
    }

    /**
     * Gets the first (maximum) element of sorted list.
     *
     * @return {@link Optional} of first (maximum) element of sorted list, or a empty optional if
     * there is no element in the list.
     */
    public Optional<T> max() {
        if (this.sortedElements.isEmpty())
            return Optional.empty();

        return Optional.of(this.sortedElements.get(0));
    }

    /**
     * Gets the immutable list of sorted elements.
     *
     * @return Immutable list of sorted elements.
     */
    public List<T> getSortedElements() {
        return this.sortedElements;
    }
}
