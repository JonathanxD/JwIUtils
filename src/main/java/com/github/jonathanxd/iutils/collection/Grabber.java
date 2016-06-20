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
package com.github.jonathanxd.iutils.collection;

import com.github.jonathanxd.iutils.arrays.JwArray;
import com.github.jonathanxd.iutils.exceptions.CannotCollectElementsException;
import com.github.jonathanxd.iutils.exceptions.ExcludedElementIndexException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.ObjIntConsumer;


/**
 * Grabber of elements, when you grab an element, your index is marked as {@code excluded}.
 *
 * @param <T> Elements type
 */
public interface Grabber<T> {

    /**
     * Grab an element at {@code current non-excluded index + 1} and mark element index as
     * "excluded"
     *
     * @return Element at {@code current non-excluded index + 1}
     * @throws IndexOutOfBoundsException if has no elements to grab.
     */
    T grab() throws IndexOutOfBoundsException;

    /**
     * Grab element at a {@code index} and mark index as excluded
     *
     * @param index Element index
     * @return Element at a {@code index}
     * @throws ExcludedElementIndexException if element index is excluded
     */
    T grab(int index) throws ExcludedElementIndexException;

    /**
     * Return true if specified {@code index} is excluded
     *
     * @param index Index
     * @return True if specified {@code index} is excluded
     */
    boolean isExcluded(int index);

    /**
     * Collect all remaining elements (non-excluded index elements)
     *
     * @return All remaining
     */
    T[] collectRemainingToArray(IntFunction<T[]> generator);


    /**
     * Collect all remaining elements (non-excluded index elements)
     *
     * @return All remaining
     */
    List<T> collectRemainingToList();

    /**
     * Collect a {@code amount} of remaining elements
     *
     * @return A {@code amount} of remaining elements
     */
    T[] collectRemainingToArray(int amount, IntFunction<T[]> generator);

    /**
     * Collect a {@code amount} of remaining elements
     *
     * @return a {@code amount} of remaining elements
     */
    List<T> collectRemainingToList(int amount);

    /**
     * Foreach all remaining elements (non-excluded index elements)
     */
    void foreachRemaining(Consumer<T> consumer);

    /**
     * Foreach all remaining elements and indexes (non-excluded index elements)
     */
    void foreachRemaining(ObjIntConsumer<T> consumer);

    /**
     * Foreach a {@code amount} of remaining elements (non-excluded index elements)
     *
     * @throws CannotCollectElementsException if cannot collect all elements
     */
    void foreachRemaining(int amount, Consumer<T> consumer) throws CannotCollectElementsException;

    /**
     * Foreach a {@code amount} of remaining elements and indexes (non-excluded index elements)
     *
     * @throws CannotCollectElementsException if cannot collect all elements
     */
    void foreachRemaining(int amount, ObjIntConsumer<T> consumer) throws CannotCollectElementsException;

    /**
     * Calculate number of {@code non-excluded elements}
     *
     * Use {@link #hasIncludedElements()} instead of this if you want to know if has included
     * elements (best performance).
     *
     * @return Number of {@code non-excluded elements}
     */
    int calculateIncludedElements();

    /**
     * Calculate number of {@code excluded elements}
     *
     * Use {@link #hasIncludedElements()} instead of this if you want to know if hasn't included
     * elements (best performance).
     *
     * @return Number of {@code excluded elements}
     */
    int calculateExcludedElements();

    /**
     * Return number of all elements (included and excluded)
     *
     * @return Number of all elements (included and excluded)
     */
    int allElementsSize();

    /**
     * Return true if has included elements.
     *
     * @return True if has included elements.
     */
    boolean hasIncludedElements();

    /**
     * All elements
     *
     * @return All Elements
     */
    JwArray<T> allElements();

    /**
     * Non-Excluded elements
     *
     * @return Non-Excluded elements
     */
    JwArray<T> includedElements();

    /**
     * Excluded elements
     *
     * @return Excluded elements
     */
    JwArray<T> excludedElements();

}