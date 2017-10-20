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

import com.github.jonathanxd.iutils.annotation.Singleton;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class Reflection {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();

    private Reflection() {
        throw new UnsupportedOperationException();
    }


    /**
     * Gets the singleton instance of a {@code type}, this requires that the class have a {@code
     * public static} {@code INSTANCE} field of the same type as enclosing type ({@code type} in
     * this case) or is annotated with {@link Singleton} that specifies the name of singleton
     * field.
     *
     * @param type Type to get singleton instance.
     * @param <T>  Class type.
     * @return Singleton instance of {@code type}.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> type) {
        Singleton singleton = type.getDeclaredAnnotation(Singleton.class);
        String name = singleton == null ? "INSTANCE" : singleton.value();

        try {
            return (T) LOOKUP.findStaticGetter(type, name, type).invokeWithArguments();
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Cannot find public static singleton field with name '" +
                    name + "' in " + type.getCanonicalName(), e);

        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot access public static singleton field with name '" +
                    name + "' in " + type.getCanonicalName(), e);

        } catch (Throwable throwable) {
            throw new IllegalStateException("Failed to invoke field getter.", throwable);
        }
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
