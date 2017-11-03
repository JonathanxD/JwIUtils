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
package com.github.jonathanxd.iutils.array;

/**
 * Converts primitive array values to boxed version and vice-versa.
 */
@SuppressWarnings("UnnecessaryBoxing")
public class PrimitiveArrayConverter {

    public static Byte[] fromPrimitive(byte[] primitive) {
        Byte[] boxed = new Byte[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Byte.valueOf(primitive[i]);
        }

        return boxed;
    }

    public static Short[] fromPrimitive(short[] primitive) {
        Short[] boxed = new Short[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Short.valueOf(primitive[i]);
        }

        return boxed;
    }

    public static Integer[] fromPrimitive(int[] primitive) {
        Integer[] boxed = new Integer[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Integer.valueOf(primitive[i]);
        }

        return boxed;
    }

    public static Long[] fromPrimitive(long[] primitive) {
        Long[] boxed = new Long[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Long.valueOf(primitive[i]);
        }

        return boxed;
    }

    public static Float[] fromPrimitive(float[] primitive) {
        Float[] boxed = new Float[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Float.valueOf(primitive[i]);
        }

        return boxed;
    }

    public static Double[] fromPrimitive(double[] primitive) {
        Double[] boxed = new Double[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Double.valueOf(primitive[i]);
        }

        return boxed;

    }

    public static Boolean[] fromPrimitive(boolean[] primitive) {
        Boolean[] boxed = new Boolean[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Boolean.valueOf(primitive[i]);
        }

        return boxed;
    }

    public static Character[] fromPrimitive(char[] primitive) {
        Character[] boxed = new Character[primitive.length];


        for (int i = 0; i < primitive.length; i++) {
            boxed[i] = Character.valueOf(primitive[i]);
        }

        return boxed;
    }

    public static byte[] toPrimitive(Byte[] noPrimitive) {
        byte[] a2 = new byte[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }

    public static short[] toPrimitive(Short[] noPrimitive) {
        short[] a2 = new short[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }

    public static int[] toPrimitive(Integer[] noPrimitive) {
        int[] a2 = new int[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }

    public static long[] toPrimitive(Long[] noPrimitive) {
        long[] a2 = new long[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }

    public static float[] toPrimitive(Float[] noPrimitive) {
        float[] a2 = new float[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }

    public static double[] toPrimitive(Double[] noPrimitive) {
        double[] a2 = new double[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }

    public static boolean[] toPrimitive(Boolean[] noPrimitive) {
        boolean[] a2 = new boolean[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }


    public static char[] toPrimitive(Character[] noPrimitive) {
        char[] a2 = new char[noPrimitive.length];

        for (int x = 0; x < noPrimitive.length; ++x) {
            a2[x] = noPrimitive[x];
        }
        return a2;
    }

}
