/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.array;

import com.github.jonathanxd.iutils.annotation.Generated;
import com.github.jonathanxd.iutils.function.predicate.BooleanPredicate;
import com.github.jonathanxd.iutils.function.predicate.BytePredicate;
import com.github.jonathanxd.iutils.function.predicate.CharPredicate;
import com.github.jonathanxd.iutils.function.predicate.FloatPredicate;
import com.github.jonathanxd.iutils.function.predicate.ShortPredicate;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * Array utilities.
 */
public class ArrayUtils {

    /**
     * Checks if the {@code array} contains specified {@code element}.
     *
     * @param array   Array
     * @param element Element to find
     * @param <E>     Element Type
     * @return True if {@link Array} contains {@code element}
     */
    public static <E> boolean arrayContains(E[] array, E element) {
        for (E elem : array) {
            if (element.equals(elem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes element from {@code array} in the specified {@code index}.
     *
     * @param array Array.
     * @param index Index.
     * @param <E>   Element type.
     * @return New {@link Array} without the removed element.
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] removeElementAtIndex(E[] array, int index) {
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

    /**
     * Remove elements from {@code array} in the specified {@code indexes}.
     *
     * @param array   Array.
     * @param indexes Indexes.
     * @param <E>     Element type.
     * @return New {@link Array} without removed elements.
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] removeElementAtIndexes(E[] array, int[] indexes) {
        int len = array.length - indexes.length;

        E[] arrayCp = (E[]) Array.newInstance(array.getClass().getComponentType(), len);

        int pointer = 0;
        for (int x = 0; x < array.length; ++x) {
            if (Primitive.count(indexes, x) > 0) {
                continue;
            }

            arrayCp[pointer] = array[x];

            ++pointer;

        }

        return arrayCp;
    }

    /**
     * Count occurrences of specified {@code element} in {@code array}.
     *
     * @param array   Array
     * @param element element
     * @param <E>     Element type
     * @return Number of occurrences of specified {@code element}
     */
    public static <E> int count(E[] array, E element) {
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

    /**
     * Count occurrences of all {@code elements} in {@code array}
     *
     * @param array    Array
     * @param elements Elements to count
     * @param <E>      Element type
     * @return Number of occurrences of all {@code elements}
     */
    public static <E> int count(E[] array, E[] elements) {
        Objects.requireNonNull(array);
        Objects.requireNonNull(elements);

        int found = 0;
        for (E e : array) {
            for (E element : elements) {
                if (e.equals(element)) {
                    ++found;
                }
            }
        }
        return found;
    }

    /**
     * Adds a {@code element} to specified {@code array}
     *
     * @param array   Array to add the element.
     * @param element Element to add.
     * @param <E>     Element type.
     * @return {@link Array} with added {@code element}.
     */
    public static <E> E[] addToArray(E[] array, E element) {
        Objects.requireNonNull(array);
        final int len = array.length;

        array = java.util.Arrays.copyOf(array, len + 1);

        array[len] = element;

        return array;
    }

    /**
     * Adds all {@code elements} to specified {@code array}
     *
     * @param array    Array to add all {@code elements}.
     * @param elements Elements to add.
     * @param <E>      Element type.
     * @return {@link Array} with added {@code elements}.
     * @since 2.2
     */
    @SuppressWarnings("Duplicates")
    public static <E> E[] addAllToArray(E[] array, E[] elements) {
        Objects.requireNonNull(array);
        Objects.requireNonNull(elements);
        final int len = array.length;

        array = java.util.Arrays.copyOf(array, len + elements.length);

        for (int x = len; x < array.length; ++x) {
            array[x] = elements[x - len];
        }

        return array;
    }

    /**
     * Expands the {@code array} to a new {@code size}.
     *
     * @param array Array to expand.
     * @param size  Size to increment (negative size to decrement).
     * @param <E>   Array Type.
     * @return Resized {@link Array}.
     */
    public static <E> E[] expandArray(E[] array, int size) {
        Objects.requireNonNull(array);
        final int len = array.length;

        array = java.util.Arrays.copyOf(array, len + size);

        return array;
    }

    /**
     * Removes {@code element} from {@code array}
     *
     * @param array     Array.
     * @param element   Element to remove.
     * @param recursive Recursive remove (remove all elements, false to remove first occurrence).
     * @param <E>       Element Type
     * @return New {@link Array} without specified {@code element}.
     */
    public static <E> E[] removeElement(E[] array, E element, boolean recursive) {
        int len = (recursive ?
                array.length - ArrayUtils.count(array, element)
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

    /**
     * Recursively removes all {@code elements} from {@code array}.
     *
     * @param array    Array.
     * @param elements Elements to remove.
     * @param <E>      Element Type.
     * @return {@link Array} without {@code elements}
     * @since 2.2
     */
    public static <E> E[] removeElements(E[] array, E[] elements) {
        int len = array.length - count(array, elements);


        @SuppressWarnings("unchecked")
        E[] arrayCp = (E[]) Array.newInstance(array.getClass().getComponentType(), len);

        int l = 0;

        for (int x = 0; x < array.length; ++x) {
            E e = array[x];

            if (!arrayContains(elements, e)) {
                arrayCp[l] = e;
                ++l;
            }

        }

        return arrayCp;
    }

    /**
     * Remove first occurrence of {@code element} from {@link Array}
     *
     * @param array   Array.
     * @param element Element.
     * @param <E>     Element Type.
     * @return New {@link Array} without {@code element}.
     */
    public static <E> E[] removeFirstElement(E[] array, E element) {
        return ArrayUtils.removeElement(array, element, false);
    }

    /**
     * Recursively removes a {@code element} from {@link Array}.
     *
     * @param array   Array.
     * @param element Element.
     * @param <E>     Element Type.
     * @return New {@link Array} without specified {@code element}.
     */
    public static <E> E[] removeElement(E[] array, E element) {
        return removeElement(array, element, true);
    }

    /**
     * Copy an array to a Array of Objects (works with primitive arrays).
     *
     * @param array Array to convert.
     * @return Array of objects.
     */
    public static Object[] toObjectArray(Object array) {
        int length = Array.getLength(array);
        Object[] objectArray = new Object[length];

        for (int i = 0; i < objectArray.length; i++) {
            objectArray[i] = Array.get(array, i);
        }

        return objectArray;
    }

    /**
     * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
     * empty).
     *
     * @param array     Array to test.
     * @param predicate Predicate.
     * @param <T>       Type.
     * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
     * empty).
     * @since 4.9.17
     */
    public static <T> boolean all(T[] array, Predicate<T> predicate) {
        for (T t : array) {
            if (!predicate.test(t))
                return false;
        }

        return true;
    }

    /**
     * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
     * array).
     *
     * @param array     Array to test.
     * @param predicate Predicate.
     * @param <T>       Type.
     * @return True if any element of {@code array} matches {@code predicate}. (false for empty
     * array).
     * @since 4.9.17
     */
    public static <T> boolean any(T[] array, Predicate<T> predicate) {
        for (T t : array) {
            if (predicate.test(t))
                return true;
        }

        return false;
    }

    @SuppressWarnings("Duplicates")
    public static class Primitive {

        // Find methods

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(byte[] array, byte element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (byte e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(short[] array, short element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (short e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(int[] array, int element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (int e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(long[] array, long element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (long e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(float[] array, float element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (float e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(double[] array, double element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (double e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(boolean[] array, boolean element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (boolean e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int count(char[] array, char element) {
            Objects.requireNonNull(array);

            int found = 0;
            for (char e : array) {
                if (e == element) {
                    ++found;
                }
            }
            return found;
        }

        // Find methods since 2.2

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(byte[] array, byte[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (byte e : array) {
                for (byte element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(short[] array, short[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (short e : array) {
                for (short element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(int[] array, int[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (int e : array) {
                for (int element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(long[] array, long[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (long e : array) {
                for (long element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(float[] array, float[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (float e : array) {
                for (float element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(double[] array, double[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (double e : array) {
                for (double element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(boolean[] array, boolean[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (boolean e : array) {
                for (boolean element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        /**
         * Primitive version of method {@link #count(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int count(char[] array, char[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            int found = 0;
            for (char e : array) {
                for (char element : elements) {
                    if (e == element) {
                        ++found;
                    }
                }
            }
            return found;
        }

        // Remove element methods

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static byte[] removeElement(byte[] array, byte element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") byte[] arrayCp = (byte[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                byte e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static short[] removeElement(short[] array, short element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") short[] arrayCp = (short[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                short e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static int[] removeElement(int[] array, int element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") int[] arrayCp = (int[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                int e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static long[] removeElement(long[] array, long element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") long[] arrayCp = (long[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                long e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static float[] removeElement(float[] array, float element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") float[] arrayCp = (float[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                float e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static double[] removeElement(double[] array, double element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") double[] arrayCp = (double[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                double e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean[] removeElement(boolean[] array, boolean element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") boolean[] arrayCp = (boolean[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                boolean e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElement(Object[], Object, boolean)}
         *
         * @since 2.2
         */
        @Generated
        public static char[] removeElement(char[] array, char element, boolean recursive) {
            int len = (recursive ? array.length - count(array, element) : array.length - 1);
            @SuppressWarnings("unchecked") char[] arrayCp = (char[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            boolean found = false;
            for (int x = 0; x < array.length; ++x) {
                char e = array[x];
                if (e != element || (!recursive && found)) {
                    arrayCp[l] = e;
                    ++l;
                } else {
                    found = true;
                }
            }
            return arrayCp;
        }


        // Remove methods since 2.2

        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static byte[] removeElements(byte[] array, byte[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") byte[] arrayCp = (byte[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                byte e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static short[] removeElements(short[] array, short[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") short[] arrayCp = (short[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                short e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int[] removeElements(int[] array, int[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") int[] arrayCp = (int[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                int e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static long[] removeElements(long[] array, long[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") long[] arrayCp = (long[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                long e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static float[] removeElements(float[] array, float[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") float[] arrayCp = (float[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                float e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }


        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static double[] removeElements(double[] array, double[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") double[] arrayCp = (double[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                double e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static boolean[] removeElements(boolean[] array, boolean[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") boolean[] arrayCp = (boolean[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                boolean e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElements(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static char[] removeElements(char[] array, char[] elements) {
            int len = array.length - count(array, elements);
            @SuppressWarnings("unchecked") char[] arrayCp = (char[]) Array.newInstance(array.getClass().getComponentType(), len);
            int l = 0;
            for (int x = 0; x < array.length; ++x) {
                char e = array[x];
                if (!arrayContains(elements, e)) {
                    arrayCp[l] = e;
                    ++l;
                }
            }
            return arrayCp;
        }

        // Array contains methods

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(byte[] array, byte element) {
            for (byte elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(short[] array, short element) {
            for (short elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(int[] array, int element) {
            for (int elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(long[] array, long element) {
            for (long elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(float[] array, float element) {
            for (float elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(double[] array, double element) {
            for (double elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(boolean[] array, boolean element) {
            for (boolean elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Primitive version of method {@link #arrayContains(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean arrayContains(char[] array, char element) {
            for (char elem : array) {
                if (element == elem) {
                    return true;
                }
            }
            return false;
        }

        // Remove element methods

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static byte[] removeElementAtIndex(byte[] array, int index) {
            int len = array.length - 1;
            byte[] arrayCp = new byte[len];
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

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static short[] removeElementAtIndex(short[] array, int index) {
            int len = array.length - 1;
            short[] arrayCp = new short[len];
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

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static int[] removeElementAtIndex(int[] array, int index) {
            int len = array.length - 1;
            int[] arrayCp = new int[len];
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

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static long[] removeElementAtIndex(long[] array, int index) {
            int len = array.length - 1;
            long[] arrayCp = new long[len];
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

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static float[] removeElementAtIndex(float[] array, int index) {
            int len = array.length - 1;
            float[] arrayCp = new float[len];
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

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static double[] removeElementAtIndex(double[] array, int index) {
            int len = array.length - 1;
            double[] arrayCp = new double[len];
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

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static boolean[] removeElementAtIndex(boolean[] array, int index) {
            int len = array.length - 1;
            boolean[] arrayCp = new boolean[len];
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

        /**
         * Primitive version of method {@link #removeElementAtIndex(Object[], int)}
         *
         * @since 2.2
         */
        @SuppressWarnings("unchecked")
        @Generated
        public static char[] removeElementAtIndex(char[] array, int index) {
            int len = array.length - 1;
            char[] arrayCp = new char[len];
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

        // Remove at indexes methods

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static byte[] removeElementAtIndexes(byte[] array, int[] indexes) {
            int len = array.length - indexes.length;
            byte[] arrayCp = new byte[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static short[] removeElementAtIndexes(short[] array, int[] indexes) {
            int len = array.length - indexes.length;
            short[] arrayCp = new short[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static int[] removeElementAtIndexes(int[] array, int[] indexes) {
            int len = array.length - indexes.length;
            int[] arrayCp = new int[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static long[] removeElementAtIndexes(long[] array, int[] indexes) {
            int len = array.length - indexes.length;
            long[] arrayCp = new long[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static float[] removeElementAtIndexes(float[] array, int[] indexes) {
            int len = array.length - indexes.length;
            float[] arrayCp = new float[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static double[] removeElementAtIndexes(double[] array, int[] indexes) {
            int len = array.length - indexes.length;
            double[] arrayCp = new double[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static boolean[] removeElementAtIndexes(boolean[] array, int[] indexes) {
            int len = array.length - indexes.length;
            boolean[] arrayCp = new boolean[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        /**
         * Primitive version of method {@link #removeElementAtIndexes(Object[], int[])}
         *
         * @since 2.2
         */
        @Generated
        public static char[] removeElementAtIndexes(char[] array, int[] indexes) {
            int len = array.length - indexes.length;
            char[] arrayCp = new char[len];
            int pointer = 0;
            for (int x = 0; x < array.length; ++x) {
                if (Primitive.count(indexes, x) > 0) {
                    continue;
                }
                arrayCp[pointer] = array[x];
                ++pointer;
            }
            return arrayCp;
        }

        // Add to array methods

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static byte[] addToArray(byte[] array, byte element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static short[] addToArray(short[] array, short element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static int[] addToArray(int[] array, int element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static long[] addToArray(long[] array, long element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static float[] addToArray(float[] array, float element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static double[] addToArray(double[] array, double element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean[] addToArray(boolean[] array, boolean element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        /**
         * Primitive version of method {@link #addToArray(Object[], Object)}
         *
         * @since 2.2
         */
        @Generated
        public static char[] addToArray(char[] array, char element) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + 1);
            array[len] = element;
            return array;
        }

        // Add All to Array Methods

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static byte[] addAllToArray(byte[] array, byte[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static short[] addAllToArray(short[] array, short[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static int[] addAllToArray(int[] array, int[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static long[] addAllToArray(long[] array, long[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static float[] addAllToArray(float[] array, float[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static double[] addAllToArray(double[] array, double[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);

            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static boolean[] addAllToArray(boolean[] array, boolean[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }

        /**
         * Primitive version of method {@link #addAllToArray(Object[], Object[])}
         *
         * @since 2.2
         */
        @Generated
        public static char[] addAllToArray(char[] array, char[] elements) {
            Objects.requireNonNull(array);
            Objects.requireNonNull(elements);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + elements.length);
            for (int x = len; x < array.length; ++x) {
                array[x] = elements[x - len];
            }
            return array;
        }


        // Expand array methods

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static byte[] expandArray(byte[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static short[] expandArray(short[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static int[] expandArray(int[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static long[] expandArray(long[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static float[] expandArray(float[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static double[] expandArray(double[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static boolean[] expandArray(boolean[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Primitive version of method {@link #expandArray(Object[], int)}
         *
         * @since 2.2
         */
        @Generated
        public static char[] expandArray(char[] array, int size) {
            Objects.requireNonNull(array);
            final int len = array.length;
            array = java.util.Arrays.copyOf(array, len + size);
            return array;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(char[] array, CharPredicate predicate) {
            for (char t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(byte[] array, BytePredicate predicate) {
            for (byte t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(short[] array, ShortPredicate predicate) {
            for (short t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(int[] array, IntPredicate predicate) {
            for (int t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(long[] array, LongPredicate predicate) {
            for (long t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(float[] array, FloatPredicate predicate) {
            for (float t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(double[] array, DoublePredicate predicate) {
            for (double t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if all elements of {@code array} matches {@code predicate}. (or if array is
         * empty).
         * @since 4.9.17
         */
        public static boolean all(boolean[] array, BooleanPredicate predicate) {
            for (boolean t : array) {
                if (!predicate.test(t))
                    return false;
            }

            return true;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(char[] array, CharPredicate predicate) {
            for (char t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(byte[] array, BytePredicate predicate) {
            for (byte t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(short[] array, ShortPredicate predicate) {
            for (short t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(int[] array, IntPredicate predicate) {
            for (int t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(long[] array, LongPredicate predicate) {
            for (long t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(float[] array, FloatPredicate predicate) {
            for (float t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(double[] array, DoublePredicate predicate) {
            for (double t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }

        /**
         * Returns true if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         *
         * Hand written :P
         *
         * @param array     Array to test.
         * @param predicate Predicate.
         * @return True if any element of {@code array} matches {@code predicate}. (false for empty
         * array).
         * @since 4.9.17
         */
        public static boolean any(boolean[] array, BooleanPredicate predicate) {
            for (boolean t : array) {
                if (predicate.test(t))
                    return true;
            }

            return false;
        }
    }
}
