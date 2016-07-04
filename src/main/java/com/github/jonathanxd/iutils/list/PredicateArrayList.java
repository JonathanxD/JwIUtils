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
package com.github.jonathanxd.iutils.list;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by jonathan on 12/05/16.
 */
public class PredicateArrayList<E> extends AbstractPredicateList<E> implements IPredicateList<E> {

    private final Predicate<E> predicate;

    public PredicateArrayList(int initialCapacity, Predicate<E> predicate) {
        super(initialCapacity);
        this.predicate = predicate;
    }

    public PredicateArrayList(Predicate<E> predicate) {
        super();
        this.predicate = predicate;
    }

    public PredicateArrayList(Collection<? extends E> c, Predicate<E> predicate) {
        super(c);
        this.predicate = predicate;
    }

    @Override
    public boolean test(E e) {
        return predicate.test(e);
    }

    @Override
    public void reject(E e) {
        throw new IllegalArgumentException("Cannot accept element '"+e+"'");
    }
}
