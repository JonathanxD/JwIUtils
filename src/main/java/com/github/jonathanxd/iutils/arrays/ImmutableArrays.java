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
package com.github.jonathanxd.iutils.arrays;

import java.util.Collection;
import java.util.Enumeration;

/**
 * Arrays IS NOT A COLLECTION, Arrays is an Array representation
 */
public class ImmutableArrays<E> extends Arrays<E> {

    public ImmutableArrays() {
        super();
    }

    public ImmutableArrays(E... values) {
        super(values);
    }

    public ImmutableArrays(Collection<? extends E> collection) {
        super(collection);
    }

    public ImmutableArrays(Iterable<? extends E> iterable) {
        super(iterable);
    }

    public ImmutableArrays(Enumeration<? extends E> enume) {
        super(enume);
    }

    public static <E> ImmutableArrays<E> empty() {
        return new ImmutableArrays<>();
    }

    @Override
    public Arrays<E> add(E value) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }

    @Override
    public Arrays<E> addAll(E[] value) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }

    @Override
    public Arrays<E> remove(E value) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }

    @Override
    public Arrays<E> set(E value, int index) {
        throw new UnsupportedOperationException("Immutable Arrays Object");
    }


}
