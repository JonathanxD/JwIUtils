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
package com.github.jonathanxd.iutils.conditions;

import com.github.jonathanxd.iutils.exceptions.ArraySizeException;
import com.github.jonathanxd.iutils.string.JString;

/**
 * Created by jonathan on 27/05/16.
 */
public class Conditions {

    public static void require(boolean bool) {
        require(bool, "False required condition!");
    }

    public static void require(boolean bool, String message) {
        require(bool, message, Def.INSTANCE);
    }

    public static void require(boolean bool, String message, RuntimeExceptionFunction function) {
        if (!bool) {
            throw function.apply(message);
        }
    }

    public static <T> T checkNotNull(T o) {
        return checkNotNull(o, "Null object value!", Def.INSTANCE);
    }

    public static <T> T checkNotNull(T o, String message) {
        return checkNotNull(o, message, Def.INSTANCE);
    }

    public static <T> T checkNotNull(T o, String message, RuntimeExceptionFunction function) {
        if (o == null) {
            throw function.apply(message);
        }

        return o;
    }

    public static <T> T checkNull(T o) {
        return checkNull(o, "Not null object!", Def.INSTANCE);
    }

    public static <T> T checkNull(T o, String message) {
        return checkNull(o, message, Def.INSTANCE);
    }

    public static <T> T checkNull(Object o, String message, RuntimeExceptionFunction function) {
        if (o != null) {
            throw function.apply(message);
        }

        return null;
    }

    public static <T> T[] checkSize(T[] array, int minimumSize, int maximumSize) {
        return checkSize(array, minimumSize, maximumSize, JString.of("Array size doesn't match requirements. Minimum size: $min. Maximum size: $max. Current: ${length}",
                "min", minimumSize,
                "max", maximumSize,
                "length", array.length)
                .toString());
    }

    public static <T> T[] checkSize(T[] array, int minimumSize, int maximumSize, String message) {
        return checkSize(array, minimumSize, maximumSize, message, ArraySizeException::new);
    }

    public static <T> T[] checkSize(T[] array, int minimumSize, int maximumSize, String message, RuntimeExceptionFunction function) {
        if (array.length < minimumSize || array.length > maximumSize)
            throw function.apply(message);

        return array;
    }


    private static class Def implements RuntimeExceptionFunction {

        static final Def INSTANCE = new Def();

        private Def() {
        }

        @Override
        public RuntimeException apply(String message) {
            return new RuntimeException(message);
        }
    }
}
