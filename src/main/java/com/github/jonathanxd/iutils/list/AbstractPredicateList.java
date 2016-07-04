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

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Created by jonathan on 12/05/16.
 */
public abstract class AbstractPredicateList<E> extends ArrayList<E> implements IPredicateList<E> {

    public AbstractPredicateList(int initialCapacity) {
        super(initialCapacity);
    }

    public AbstractPredicateList() {
        super();
    }

    public AbstractPredicateList(Collection<? extends E> c) {
        super(c);
    }

    @Override
    public boolean add(E e) {
        if (!this.test(e)) {
            reject(e);
        }else {
            return super.add(e);
        }

        return false;
    }

    @Override
    public void add(int index, E element) {
        if (!this.test(element)) {
            reject(element);
        } else {
            super.add(index, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        Collection<E> toAdd = new ArrayList<>();

        for (E e : c) {
            if (this.test(e)) {
                toAdd.add(e);
            } else {
                reject(e);
            }
        }


        return super.addAll(toAdd);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Collection<E> toAdd = new ArrayList<>();

        for (E e : c) {
            if (this.test(e)) {
                toAdd.add(e);
            } else {
                reject(e);
            }
        }


        return super.addAll(index, toAdd);
    }

    @Override
    public E set(int index, E element) {
        if (!this.test(element)) {
            reject(element);
        } else {
            super.set(index, element);
        }

        return null;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        super.replaceAll(new PredicateUnaryOperator<>(this::test, operator));
    }

    private static class PredicateUnaryOperator<T> implements UnaryOperator<T> {

        private final Predicate<T> predicate;
        private final UnaryOperator<T> unaryOperator;

        private PredicateUnaryOperator(Predicate<T> predicate, UnaryOperator<T> unaryOperator) {
            this.predicate = predicate;
            this.unaryOperator = unaryOperator;
        }


        @Override
        public T apply(T t) {

            T result = unaryOperator.apply(t);

            if (!predicate.test(result))
                throw new IllegalArgumentException("Cannot accept element '" + result + "' (original element '" + t + "')");

            return result;
        }
    }

}
