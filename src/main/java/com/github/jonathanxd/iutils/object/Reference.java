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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * Created by jonathan on 13/02/16.
 */
public class Reference<T> implements Comparable<Reference> {

    /**
     * Accessed and modified via reflection in {@link AbstractReference}
     */
    private final Class<? extends T> aClass;

    /**
     * Accessed and modified via reflection in {@link AbstractReference} & {@link DynamicReference}
     */
    private final Reference[] related;

    /**
     * Accessed and modified via reflection in {@link AbstractReference}
     */
    private final Object hold;

    protected Reference() {
        this.aClass = null;
        this.related = null;
        this.hold = null;
    }


    protected Reference(Reference<T> reference) {
        this.hold = reference.hold;
        this.related = reference.related.clone();
        this.aClass = reference.aClass;
    }

    Reference(Class<? extends T> aClass, Reference[] related, Object hold) {
        this.hold = hold;
        this.aClass = Objects.requireNonNull(aClass);
        this.related = related != null ? related : new Reference[0];
    }

    @NotNull
    public static String toString(Reference reference, Function<Class<?>, String> classToString) {

        StringBuilder sb = new StringBuilder();
        String shortName = classToString.apply(reference.getAClass());
        sb.append(shortName);

        if (reference.getRelated().length != 0) {
            sb.append("<");
            StringJoiner sj = new StringJoiner(", ");

            for (Reference loopRef : reference.getRelated()) {
                sj.add(toString(loopRef, classToString));
            }

            String processResult = sj.toString();
            sb.append(processResult);
            sb.append(">");
        }

        return sb.toString();
    }


    @NotNull
    public static String toFullString(Reference reference) {

        return toString(reference, Class::getName);
    }

    //java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>

    // < |-> Create
    // , |-> Add
    // > |-> Set as Child

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static List<Reference<?>> fromFullString(String fullString) throws ClassNotFoundException {
        ReferenceBuilder<?> main = new ReferenceBuilder<>();
        ReferenceBuilder<?> current = main;

        List<Reference<?>> referenceList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = fullString.toCharArray();

        boolean lastIsSeparator = false;

        for (int x = 0; x < chars.length; ++x) {

            boolean hasNext = (x + 1 < chars.length);

            char c = chars[x];

            if (lastIsSeparator) {
                lastIsSeparator = false;
                if (c == ' ') {
                    continue;
                }
            }

            if (c != '<' && c != '>' && c != ',') {
                stringBuilder.append(c);
            }

            if (c == '<' || c == ',' || c == '>' || !hasNext) {

                if (stringBuilder.length() == 0) {
                    if (main.getaClass() != null) {
                        referenceList.add(main.build());
                        main = new ReferenceBuilder<>();
                        current = main;
                    }

                    if (c == ',')
                        lastIsSeparator = true;

                    continue;
                }

                String classString = stringBuilder.toString();

                Class clazz = Class.forName(classString);

                if (c == '<' || !hasNext) {

                    if (main.getaClass() == null) {
                        main.a(clazz);
                    } else {
                        ReferenceBuilder<?> referenceBuilder = new ReferenceBuilder<>().a(clazz);
                        current.of(referenceBuilder);
                        current = referenceBuilder;
                    }
                }

                if (c == ',' && hasNext) {
                    lastIsSeparator = true;

                    if (main.getaClass() != null) {
                        current.of(new ReferenceBuilder().a(clazz));
                    }
                }

                if (c == '>' && hasNext) {
                    if (current.getaClass() == null) {
                        current.a(clazz);
                    } else {
                        current.of(clazz);
                    }
                }

                stringBuilder.setLength(0);
            }
        }

        if (main.getaClass() != null) {
            referenceList.add(main.build());
        }

        return referenceList;

    }

    private static Class getCl(StringBuilder stringBuilder) throws ClassNotFoundException {
        String classString = stringBuilder.toString();

        Class clazz = Class.forName(classString);

        stringBuilder.setLength(0);
        return clazz;
    }

    public static <T> ReferenceBuilder<T> to() {
        return referenceTo();
    }

    public static <T> ReferenceBuilder<T> referenceTo() {
        return new ReferenceBuilder<>();
    }

    public static <T> ReferenceBuilder<T> a(Class<T> aClass) {
        return Reference.<T>referenceTo().a(aClass);
    }

    public static <T> Reference<T> aEnd(Class<T> aClass) {
        return Reference.<T>referenceTo().a(aClass).build();
    }

    @SuppressWarnings("unchecked")
    public static <T> ReferenceBuilder<T> but(Reference reference) {
        return Reference.<T>referenceTo().a(reference.getAClass()).ofArray(reference.getRelated());
    }

    public ReferenceBuilder<? extends T> but() {
        return Reference.but(this);
    }

    public Class<? extends T> getAClass() {
        return aClass;
    }

    public Object get() {
        return hold;
    }

    public Reference[] getRelated() {
        return related;
    }

    @Override
    public String toString() {
        return toString(this, Class::getSimpleName);
    }

    public String toFullString() {
        return toFullString(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aClass, Arrays.deepHashCode(related));
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Reference))
            return false;

        Reference other = (Reference) obj;

        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(@NotNull Reference compareTo) {

        if (getAClass() == compareTo.getAClass()) {

            if (Arrays.deepEquals(getRelated(), compareTo.getRelated())) {
                return 0;
            }

            return 1;
        }

        return -1;
    }

    public int compareToAssignable(@NotNull Reference compareTo) {

        if (getAClass().isAssignableFrom(compareTo.getAClass())) {

            if (getRelated().length != compareTo.getRelated().length)
                return -1;

            for (int x = 0; x < getRelated().length; ++x) {
                Reference<?> mainRef = getRelated()[x];
                Reference<?> compareRef = compareTo.getRelated()[x];

                if (!mainRef.getAClass().isAssignableFrom(compareRef.getAClass())) {
                    if (compareRef.getAClass().isAssignableFrom(mainRef.getAClass())) {
                        return 1;
                    }
                    return -1;
                }
            }
            return 0;
        }

        return -1;
    }

}
