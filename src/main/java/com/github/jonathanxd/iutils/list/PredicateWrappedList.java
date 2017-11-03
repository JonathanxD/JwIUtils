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
package com.github.jonathanxd.iutils.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A {@link PredicateList} backed by a {@link List}.
 *
 * @param <E> Element type.
 */
public class PredicateWrappedList<E> extends AbstractPredicateWrappedList<E> implements PredicateList<E> {

    /**
     * Predicate to test elements to add.
     */
    private final Predicate<E> predicate;

    /**
     * Creates a predicate array list.
     *
     * @param list      List to add values.
     * @param predicate Predicate to test elements to add.
     */
    public PredicateWrappedList(List<E> list, Predicate<E> predicate) {
        super(list);
        this.predicate = predicate;
    }

    /**
     * Creates {@link PredicateWrappedList} backing to a new {@link java.util.ArrayList}.
     *
     * @param predicate Predicate to test elements to add.
     */
    public PredicateWrappedList(Predicate<E> predicate) {
        this(new ArrayList<>(), predicate);
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new PredicateWrappedList<>(this.getWrappedList().subList(fromIndex, toIndex), this.getPredicate());
    }

    @Override
    public boolean isAcceptable(E e) {
        return this.getPredicate().test(e);
    }

    @Override
    public void onReject(E e) {
    }

    protected Predicate<E> getPredicate() {
        return this.predicate;
    }
}
