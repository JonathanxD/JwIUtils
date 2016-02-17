/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
            throw new ClassCastException("Cannot cast class '" + o.getClass() + "' to " + clazz.getClass());
        }
        throw new ClassCastException("Cannot cast class '" + o.getClass() + "' to " + clazz.getClass());
    }

    public E cast(Object o) {
        return cast(o, persistantClass);
    }

    @SuppressWarnings("unchecked")
    public E simpleCast(Object o) {
        return (E) o;
    }
}
