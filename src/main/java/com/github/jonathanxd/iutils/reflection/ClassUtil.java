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
package com.github.jonathanxd.iutils.reflection;

import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.list.UniqueList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ClassUtil {

    private ClassUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Compares inheritance of {@code a} and {@code b}.
     *
     * @param a First class to compare.
     * @param b Second class to compare.
     * @return {@code -1} if neither {@code a} is assignable to {@code b} and {@code b} is not
     * assignable to a, or have lower inheritance. Returns {@code 1} if {@code a} have higher
     * inheritance. Returns {@code 0} if both are equals or have same inheritance level.
     */
    public static int compare(Class<?> a, Class<?> b) {
        if (!ClassUtil.isCompatible(a, b))
            return -1;

        if (a.equals(b))
            return 0;

        return Integer.compare(ClassUtil.getInheritLevel(a), ClassUtil.getInheritLevel(b));
    }

    /**
     * Returns whether {@code a} is compatible to {@code b}. (Either {@code a} is assignable from
     * {@code b} or {@code b} is assignable from {@code a}).
     *
     * @param a First class.
     * @param b Second class.
     * @return Whether {@code a} is compatible to {@code b}. (Either {@code a} is assignable from
     * {@code b} or {@code b} is assignable from {@code a}).
     */
    public static boolean isCompatible(Class<?> a, Class<?> b) {
        return a.isAssignableFrom(b) || b.isAssignableFrom(a);
    }

    /**
     * Gets inheritance level of {@code a}.
     *
     * @param a Class to get inheritance level.
     * @return Inheritance level of {@code a}.
     */
    public static int getInheritLevel(Class<?> a) {
        return ClassUtil.getSortedSuperTypes(a).size();
    }

    /**
     * Create a sorted list representing the class hierarchy.
     *
     * @param aClass Class.
     * @return Sorted list representing the class hierarchy.
     */
    public static List<Class<?>> getSortedSuperTypes(Class<?> aClass) {
        return ClassUtil.getSuperTypesLeveled(aClass).stream().map(Leveled::getValue).collect(Collectors.toList());
    }

    /**
     * Creates a list representing the class hierarchy by level.
     *
     * @param aClass Class.
     * @return List representing the class hierarchy by level.
     */
    public static List<Leveled<Class<?>>> getSuperTypesLeveled(Class<?> aClass) {
        List<Leveled<Class<?>>> list = new UniqueList<>();

        ClassUtil.getSuperTypesLeveled(aClass, list, 1);

        list = ClassUtil.fixLeveled(list);

        list.sort((o1, o2) -> {

            int compare = Integer.compare(o1.getLevel(), o2.getLevel());

            if (compare != 0) {
                return compare;
            } else {
                if (!o1.getValue().isInterface()) { // Classes always first
                    return -1;
                } else {
                    return compare + 1;
                }
            }
        });

        return list;
    }

    private static <E, T extends Leveled<E>> List<Leveled<E>> fixLeveled(List<T> list) {

        Map<E, Integer> map = new HashMap<>();

        for (T t : list) {
            if (map.containsKey(t.getValue())) {
                Integer integer = map.get(t.getValue());
                if (integer >= t.getLevel()) {
                    continue;
                }
            }

            map.put(t.getValue(), t.getLevel());

        }

        return BiStreams.mapStream(map).streamMap((e, integer) -> new Leveled<>(integer, e)).collect(Collectors.toList());
    }

    private static void getSuperTypesLeveled(Class<?> aClass, List<Leveled<Class<?>>> list, int level) {
        Class<?> superclass = aClass.getSuperclass();
        Class<?>[] interfaces = aClass.getInterfaces();

        boolean hasSuperClass = !aClass.isInterface()
                && superclass != null && superclass != Object.class;

        if (hasSuperClass) {
            list.add(new Leveled<>(level, superclass));
        }

        for (Class<?> anInterface : interfaces) {
            list.add(new Leveled<>(level, anInterface));
        }

        if (hasSuperClass) {
            ClassUtil.getSuperTypesLeveled(superclass, list, level + 1);
        }

        for (Class<?> anInterface : interfaces) {
            ClassUtil.getSuperTypesLeveled(anInterface, list, level + 1);
        }
    }

    /**
     * Level holder.
     *
     * @param <T> Element type.
     */
    public static final class Leveled<T> {

        /**
         * Level of this element.
         */
        private final int level;

        /**
         * Element.
         */
        private final T value;

        Leveled(int level, T value) {
            this.level = level;
            this.value = value;
        }

        /**
         * Gets the level of element.
         *
         * @return Level of element.
         */
        public int getLevel() {
            return this.level;
        }

        /**
         * Gets the element.
         *
         * @return Element.
         */
        public T getValue() {
            return this.value;
        }

        @Override
        public boolean equals(Object obj) {

            if (obj instanceof Leveled) {
                Leveled obj0 = (Leveled) obj;

                return Objects.equals(this.getValue(), obj0.getValue())
                        && this.getLevel() == obj0.getLevel();
            }

            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getLevel(), this.getValue());
        }

        @Override
        public String toString() {
            return "Leveled[level=" + this.getLevel() + ", value=" + this.getValue() + "]";
        }
    }
}
