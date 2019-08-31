/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.recursion;

import com.github.jonathanxd.iutils.object.Pair;

/**
 * Represents a sequence of consumable elements.
 *
 * @param <E> Element type.
 */
public class Elements<E> {
    /**
     * First element of sequence.
     */
    public Element<E> first;

    /**
     * Inserts an element immediately after the current element (the element returned by the last
     * call to {@link #nextElement()}, in other words, {@link #nextElement() next element} will be
     * the inserted element. The element to insert cannot point to a next element, if you want to
     * insert more than one element, use {@link #insert(Element, Element)}.
     *
     * @param eElement Element to insert.
     */
    public void insert(Element<E> eElement) {
        this.insert(eElement, eElement);
    }

    /**
     * Inserts an element immediately after the current element (the element returned by the last
     * call to {@link #nextElement()}, in other words, {@link #nextElement() next element} will be
     * the inserted element.
     *
     * This method allows multiple elements to be added, but a pointer to last element is required,
     * the last element cannot have a pointer to a next element.
     *
     * @param eElement First element to insert after current element.
     * @param end      Last element that {@code eElement} points to (directly or indirectly).
     */
    public void insert(Element<E> eElement, Element<E> end) {
        if (end.next != null)
            throw new IllegalArgumentException("Element to insert has a next element");

        if (first == null) {
            first = eElement;
        } else {
            Element<E> f = first;
            first = eElement;
            end.next = f;
        }
    }

    /**
     * Same as {@link #insert(Element, Element)}, but extract {@code first} and {@code last} from a
     * {@link Pair pair}.
     *
     * @param firstAndLastPair Pair with {@code first} and {@code last} element to insert.
     * @see #insert(Element, Element)
     */
    public void insertFromPair(Pair<Element<E>, Element<E>> firstAndLastPair) {
        this.insert(firstAndLastPair.getFirst(), firstAndLastPair.getSecond());
    }

    /**
     * Consumes next element and return it. After consumed, the element will not exist anymore in
     * the sequence, unless another element points to it. GC may collect the consumed element (and
     * this is the intention), so do not keep any strong reference to it. Having a strong reference
     * to element will not allow it to be collected, and elements that it points to, will not be
     * collected too, breaking the intended behavior of this class.
     *
     * @return Next element.
     */
    public Element<E> nextElement() {
        if (first != null) {
            Element<E> elem = first;
            this.first = first.next;
            return elem;
        }
        return null;
    }
}
