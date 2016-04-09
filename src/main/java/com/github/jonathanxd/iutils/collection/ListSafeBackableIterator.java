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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.github.jonathanxd.iutils.iterator.Navigator;
import com.github.jonathanxd.iutils.iterator.SafeBackableIterator;

/**
 * Created by jonathan on 08/02/16.
 */
public class ListSafeBackableIterator<E> implements SafeBackableIterator<E> {

    private final List<E> list;
    private int index = -1;

    public ListSafeBackableIterator(List<E> list) {
        this.list = list;
    }

    @Override
    public boolean hasBack() {
        return index - 1 >= 0;
    }

    @Override
    public E back() {
        return list.get(--index);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Navigator<E> safeNavigate() {
        return new ListSafeNavigator<>(this.list);
    }

    @Override
    public boolean hasNext() {
        return index + 1 < list.size();
    }

    @Override
    public E next() {
        return list.get(++index);
    }

    private static class ListSafeNavigator<E> implements Navigator<E> {

        private final List<E> list;
        private int currentIndex;
        private E currentValue;

        private ListSafeNavigator(List<E> list) {
            this.list = list;
        }

        @Override
        public boolean has(int index) {
            return index >= 0 && index < list.size();
        }

        @Override
        public E navigateTo(int index) {
            if (index < 0) {
                currentIndex = 0;
            } else {
                currentIndex = index;
            }
            return currentValue = list.get(currentIndex);
        }

        @Override
        public E currentValue() {
            return currentValue;
        }

        @Override
        public void goNextWhen(Predicate<E> predicate) {
            do {
                ++currentIndex;
            }
            while (has(currentIndex) && predicate.test(navigateTo(currentIndex)));
        }

        @Override
        public int currentIndex() {
            return currentIndex;
        }

        @Override
        public List<E> collect(int to) {
            List<E> list = new ArrayList<>();
            to = to + currentIndex;
            do {
                list.add(navigateTo(currentIndex));
                ++currentIndex;
            }
            while (has(currentIndex) && currentIndex < to);

            return list;
        }
    }

}
