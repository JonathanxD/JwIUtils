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
public interface IndexedListContainer<T> extends ListContainer<T> {

    /**
     * Adds a element at {@code index}.
     *
     * @param index   Index.
     * @param element Element to add.
     * @return True if successfully added element.
     */
    boolean add(int index, T element);

    /**
     * Holds old value and add {@code element} at {@code index}.
     *
     * @param index   Index.
     * @param element Element to add.
     * @return {@link BaseContainer} of old value, or a empty {@link BaseContainer} if there is not
     * old value.
     */
    BaseContainer<T> holdAndAdd(int index, T element);

    /**
     * Removes a element at index {@code index}.
     *
     * @param index Index.
     * @return True if successfully removed element.
     */
    boolean remove(int index);

    /**
     * Holds old value and remove.
     *
     * @param index Index of value.
     * @return {@link BaseContainer} of old value, or a empty {@link BaseContainer} if there is not
     * old value.
     */
    BaseContainer<T> holdAndRemove(int index);

    /**
     * Returns true if the list has empty slot(s) (is not full).
     *
     * @return True if the list has empty slot(s).
     */
    boolean hasEmptySlot();

    /**
     * Gets the next empty slot.
     *
     * @return Empty lost.
     */
    int nextEmptySlot();

    /**
     * Returns amount of empty slots.
     *
     * @return Amount of empty slots.
     */
    int emptySlots();

    /**
     * Gets the element at {@code index}.
     *
     * @param index Index.
     * @return Container with element at {@code index}.
     */
    BaseContainer<T> get(int index);
}
