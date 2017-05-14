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

import com.github.jonathanxd.iutils.container.BaseContainer;
import com.github.jonathanxd.iutils.container.MutableContainer;

import java.util.Objects;

/**
 * A {@link java.util.Set} like version of {@link StaticList}.
 *
 * This list checks if {@link StaticList#contains(Object)} before adding elements to prevent
 * duplicated elements.
 *
 * @param <T> Element type.
 */
public class StaticListSet<T> extends StaticList<T> {

    private StaticListSet(Class<? extends T> clazz, int size) {
        super(clazz, size);
    }

    /**
     * Creates a {@link StaticListSet} with a predefined size.
     *
     * @param elementType Type of elements.
     * @param size        Size of the list.
     * @param <T>         Type of elements.
     * @return A {@link StaticListSet} with predefined {@code size}.
     */
    public static <T> StaticListSet<T> createStaticListSetOf(final Class<T> elementType, final int size) {
        if (size <= 0) {
            throw new RuntimeException("Cannot create a static list with 0 or negative size!");
        }
        return new StaticListSet<>(Objects.requireNonNull(elementType), size);
    }

    @Override
    public boolean add(int index, T element) {
        return !this.contains(element) && super.add(index, element);
    }

    @Override
    public boolean add(T element) {
        return !this.contains(element) && super.add(element);
    }

    @Override
    public BaseContainer<T> holdAndAdd(int index, T element) {
        if (!this.contains(element)) {
            return super.holdAndAdd(index, element);
        }

        return MutableContainer.empty();
    }

    @Override
    public BaseContainer<T> holdAndAdd(T element) {
        if (!this.contains(element)) {
            return super.holdAndAdd(element);
        }

        return MutableContainer.empty();
    }

}
