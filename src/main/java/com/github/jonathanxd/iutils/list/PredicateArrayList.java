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
package com.github.jonathanxd.iutils.list;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * A predicate {@link java.util.ArrayList}.
 *
 * @param <E> Element type.
 */
public class PredicateArrayList<E> extends AbstractPredicateList<E> implements PredicateList<E> {

    /**
     * Predicate to test elements to add.
     */
    private final Predicate<E> predicate;

    /**
     * Creates a predicate array list.
     *
     * @param initialCapacity Initial capacity of list.
     * @param predicate       Predicate to test elements to add.
     */
    public PredicateArrayList(int initialCapacity, Predicate<E> predicate) {
        super(initialCapacity);
        this.predicate = predicate;
    }

    /**
     * Creates a predicate array list.
     *
     * @param predicate Predicate to test elements to add.
     */
    public PredicateArrayList(Predicate<E> predicate) {
        super();
        this.predicate = predicate;
    }

    /**
     * Creates a predicate array list.
     *
     * @param c         Initial elements.
     * @param predicate Predicate to test elements to add.
     */
    public PredicateArrayList(Collection<? extends E> c, Predicate<E> predicate) {
        super(c);
        this.predicate = predicate;
    }

    @Override
    public boolean isAcceptable(E e) {
        return predicate.test(e);
    }

    @Override
    public void onReject(E e) {
        throw new IllegalArgumentException("Cannot accept element '" + e + "'");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new PredicateArrayList<>(super.subList(fromIndex, toIndex), this.predicate);
    }

}
