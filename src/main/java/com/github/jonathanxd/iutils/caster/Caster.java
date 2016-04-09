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
package com.github.jonathanxd.iutils.caster;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class Caster<E> {

    private final Class<?> persistantClass;

    public Caster() {
        persistantClass = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @SuppressWarnings("unchecked")
    public static <E> E cast(Object o, Class<?> clazz) {
        Objects.requireNonNull(clazz);
        try {
            o.getClass().asSubclass(clazz);
            return (E) o;
        } catch (Exception ignored) {
        }

        try {
            for (Method m : o.getClass().getDeclaredMethods()) {
                if (!m.isAccessible()) {
                    m.setAccessible(true);
                }
                if (clazz.equals(String.class) && m.getName().equals("toString")) continue;
                if (m.getParameters().length == 0 && !m.getName().equals("toString")) {
                    if (m.getReturnType().equals(Object.class)) {
                        Object returns = m.invoke(o);
                        if (clazz.equals(returns.getClass())) {
                            return (E) returns;
                        }
                    }
                    if (m.getReturnType().equals(clazz)) {
                        return (E) m.invoke(o);
                    }
                }
            }
            for (Method m : o.getClass().getMethods()) {
                if (!m.isAccessible()) {
                    m.setAccessible(true);
                }
                if (clazz.equals(String.class) && m.getName().equals("toString")) continue;
                if (m.getParameters().length == 0) {
                    if (m.getReturnType().equals(Object.class)) {
                        Object returns = m.invoke(o);
                        if (clazz.equals(returns.getClass())) {
                            return (E) returns;
                        }
                    }
                    if (m.getReturnType().equals(clazz)) {
                        return (E) m.invoke(o);
                    }
                }
            }
        } catch (Exception e2) {
            throw new ClassCastException("Cannot cast class '" + o.getClass() + "' to " + clazz);
        }
        throw new ClassCastException("Cannot cast class '" + o.getClass() + "' to " + clazz);
    }

    public E cast(Object o) {
        return cast(o, persistantClass);
    }

    @SuppressWarnings("unchecked")
    public E simpleCast(Object o) {
        return (E) o;
    }
}
