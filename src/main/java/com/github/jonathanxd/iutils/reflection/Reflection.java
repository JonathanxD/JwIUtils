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
package com.github.jonathanxd.iutils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class Reflection {

    private Reflection() {
        throw new UnsupportedOperationException();
    }

    /**
     * Changes a final (or static final) field of a class.
     *
     * @param aClass    Class.
     * @param instance  Class object instance.
     * @param fieldName Field name.
     * @param newValue  New value.
     * @throws Exception If an exception occurs when trying to change the value.
     */
    public static void changeFinalField(Class<?> aClass, Object instance, String fieldName, Object newValue) throws Exception {
        Field field;

        try {
            field = aClass.getField(fieldName);
        } catch (Exception e) {
            field = aClass.getDeclaredField(fieldName);
        }

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        if (Modifier.isFinal(field.getModifiers())) {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }

        field.set(instance, newValue);
    }

    /**
     * Tries to gets a instance of {@code tClass} using a valueOf and parse method.
     *
     * @param value Value to pass to valueOf and parse method.
     * @param <T>   Type.
     * @return Value, or null if cannot find a {@code valueOf} or a {@code parse} method.
     */
    public static <T> T value(Class<T> tClass, Object value) {
        return Reflection.findValueMethod(value, tClass.getDeclaredMethods());
    }

    @SuppressWarnings("unchecked")
    private static <T> T findValueMethod(Object value, Method[] methods) {
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                if (method.getName().startsWith("parse")
                        || method.getName().startsWith("valueOf")) {
                    for (Class<?> type : method.getParameterTypes()) {
                        if (type.isAssignableFrom(value.getClass())) {
                            try {
                                Object invokeResult = method.invoke(null, value);
                                if (invokeResult != null) {
                                    return (T) invokeResult;
                                }
                            } catch (IllegalAccessException | InvocationTargetException ignored) {
                            }
                        }
                    }
                }
            }
        }

        return null;
    }


}
