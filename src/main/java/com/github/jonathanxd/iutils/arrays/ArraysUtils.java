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

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Created by jonathan on 30/04/16.
 */
public class ArraysUtils {

    private static final Arrays<?> EMPTY = new ImmutableArrays<>();

    public static <T> Arrays<T> empty() {
        return (Arrays<T>) EMPTY;
    }

    public static <E> boolean equalsArray(E element, E[] array) {
        for (E elem : array) {
            if (element.equals(elem)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removeElement(E[] array, int index) {
        int len = array.length - 1;

        E[] arrayCp = (E[]) Array.newInstance(array.getClass().getComponentType(), len);

        int pointer = 0;
        for (int x = 0; x < array.length; ++x) {
            if (x == index) {
                continue;
            }

            arrayCp[pointer] = array[x];

            ++pointer;

        }

        return arrayCp;
    }

    public static <E> int find(E[] array, E element) {
        Objects.requireNonNull(array);
        Objects.requireNonNull(element);
        int found = 0;
        for (E e : array) {
            if (e.equals(element)) {
                ++found;
            }
        }
        return found;
    }

    public static <E> E[] addToArrayWA(E[] array, E element) {
        return Arrays.of(array).add(element).toGenericArray();
    }

    public static <E> E[] addToArray(E[] array, E element) {
        Objects.requireNonNull(array);
        final int len = array.length;

        array = java.util.Arrays.copyOf(array, len + 1);

        array[len] = element;

        return array;
    }

    public static <E> E[] expandArray(E[] array, int size) {
        Objects.requireNonNull(array);
        final int len = array.length;

        array = java.util.Arrays.copyOf(array, len + size);

        return array;
    }


    public static <E> E[] removeElement(E[] array, E element, boolean recursive) {
        int len = (recursive ?
                array.length - find(array, element)
                : array.length - 1);


        @SuppressWarnings("unchecked")
        E[] arrayCp = (E[]) Array.newInstance(array.getClass().getComponentType(), len);

        int l = 0;
        boolean found = false;

        for (int x = 0; x < array.length; ++x) {
            E e = array[x];

            if (!e.equals(element) || (!recursive && found)) {
                arrayCp[l] = e;
                ++l;
            } else {
                found = true;
            }
        }

        return arrayCp;
    }

    public static <E> E[] removeFirstElement(E[] array, E element) {
        return removeElement(array, element, false);
    }

    public static <E> E[] removeElement(E[] array, E element) {
        return removeElement(array, element, true);
    }


}
