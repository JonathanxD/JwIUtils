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
package com.github.jonathanxd.iutils.type;

import com.github.jonathanxd.iutils.condition.Conditions;

/**
 * Primitive boxing and unboxing utility.
 */
public final class Primitive {

    private Primitive() {
        throw new UnsupportedOperationException();
    }

    /**
     * Box Type (return boxed type. (primitive) -&gt; Wrapper)
     *
     * @param type Type to Box
     * @return Boxed Type or null if cannot box the {@code type}
     */
    public static Class<?> box(Class<?> type) {
        if (int.class == type) {
            return Integer.class;
        } else if (byte.class == type) {
            return Byte.class;
        } else if (boolean.class == type) {
            return Boolean.class;
        } else if (short.class == type) {
            return Short.class;
        } else if (long.class == type) {
            return Long.class;
        } else if (float.class == type) {
            return Float.class;
        } else if (double.class == type) {
            return Double.class;
        } else if (char.class == type) {
            return Character.class;
        } else if (void.class == type) {
            return Void.class;
        } else {
            return null;
        }
    }

    /**
     * Unbox Type (return primitive type. (Wrapper) -&gt; primitive)
     *
     * @param type Type to Unbox
     * @return Primitive Type or null if type cannot be translated to primitive.
     */
    public static Class<?> unbox(Class<?> type) {
        if (Integer.class == type) {
            return int.class;
        } else if (Byte.class == type) {
            return byte.class;
        } else if (Boolean.class == type) {
            return boolean.class;
        } else if (Short.class == type) {
            return short.class;
        } else if (Long.class == type) {
            return long.class;
        } else if (Float.class == type) {
            return float.class;
        } else if (Double.class == type) {
            return double.class;
        } else if (Character.class == type) {
            return char.class;
        } else if (Void.class == type) {
            return void.class;
        } else {
            return null;
        }
    }

    /**
     * Check if type equals boxing and unboxing types if necessary.
     *
     * Example, if {@code type1} is primitive and {@code type2} is not, then type2 will be
     * translated to primitive type
     *
     * @param type1 Type 1
     * @param type2 Type 2
     * @return True if types equals.
     */
    public static boolean typeEquals(Class<?> type1, Class<?> type2) {

        if (type1.isPrimitive() != type2.isPrimitive()) {
            if (type1.isPrimitive()) {
                Class<?> unbox = unbox(type2);

                if (unbox == null)
                    return false; // Not primitive, not equal

                type2 = unbox;
            } else if (type2.isPrimitive()) {
                Class<?> unbox = unbox(type1);

                if (unbox == null)
                    return false; // Not primitive, not equal

                type1 = unbox;
            }
        }

        return type1.equals(type2);
    }

    /**
     * Normalize {@code toNormalize} to {@code expected}
     *
     * If {@link #typeEquals(Class, Class)} returns false, {@code throws} {@link
     * IllegalArgumentException}
     *
     * @param toNormalize Type to normalize
     * @param expected    Expected normalization
     * @return Normalized class.
     * @throws IllegalArgumentException if {@link #typeEquals(Class, Class)} returns false
     */
    public static Class<?> normalize(Class<?> toNormalize, Class<?> expected) throws IllegalArgumentException {
        Conditions.require(typeEquals(toNormalize, expected), "Cannot normalize type '" + toNormalize.getCanonicalName() + "' to '" + expected + "'. Different types!");

        if (expected.isPrimitive() == toNormalize.isPrimitive())
            return toNormalize;

        if (expected.isPrimitive() && !toNormalize.isPrimitive())
            return unbox(toNormalize);

        if (!expected.isPrimitive() && toNormalize.isPrimitive())
            return box(toNormalize);

        return toNormalize;
    }
}
