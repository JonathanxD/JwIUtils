/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
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
package com.github.jonathanxd.iutils.container.list;

import com.github.jonathanxd.iutils.container.BaseContainer;

/**
 * Standard specification of JwIUtils {@link com.github.jonathanxd.iutils.list.StaticList
 * StaticLists}.
 *
 * @param <T> Type of elements.
 */
public interface ListContainer<T> {

    /**
     * Adds a element.
     *
     * @param element Element to add.
     * @return True if successfully added {@code element}, false if list is full.
     */
    boolean add(T element);

    /**
     * Holds element to be replaced and replace with the {@code element}.
     *
     * This method will replace last element in the list if it is full, other methods like {@link
     * #add(Object)} will return false if list is full.
     *
     * @param element Element to add.
     * @return Container with replaced value (or empty container if there is not value to replace).
     */
    BaseContainer<T> holdAndAdd(T element);

    /**
     * Removes the element.
     *
     * @param element Element to remove.
     * @return True if successfully removed {@code element}.
     */
    boolean remove(T element);

    /**
     * Returns true if the list is empty.
     *
     * @return True if is empty.
     */
    boolean isEmpty();

    /**
     * Returns true if the list is full.
     *
     * @return True if the list is full.
     */
    boolean isFull();

    /**
     * Returns true if the list contains the element {@code element}.
     *
     * @param element Element.
     * @return True if the list contains the element {@code element}.
     */
    boolean contains(T element);

    /**
     * Returns the size of list.
     *
     * @return Size of list.
     */
    int size();

}
