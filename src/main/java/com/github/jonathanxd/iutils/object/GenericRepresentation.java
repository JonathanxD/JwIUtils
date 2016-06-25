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

import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * Created by jonathan on 13/02/16.
 */
@SuppressWarnings("Duplicates")
public class GenericRepresentation<T> implements Comparable<GenericRepresentation> {

    /**
     * Accessed and modified via reflection in {@link AbstractGenericRepresentation}
     */
    private final Class<? extends T> aClass;

    /**
     * Accessed and modified via reflection in {@link AbstractGenericRepresentation} & {@link DynamicGenericRepresentation}
     */
    private final GenericRepresentation[] related;

    /**
     * Accessed and modified via reflection in {@link AbstractGenericRepresentation}
     * @deprecated
     */
    //private final Object hold;

    /**
     * Unique GenericRepresentation uses default {@link #equals(Object)} and {@link #hashCode()}, and identique
     */
    private final boolean isUnique;

    protected GenericRepresentation() {
        this.aClass = null;
        this.related = null;
        //this.hold = null;
        this.isUnique = false;
    }


    protected GenericRepresentation(GenericRepresentation<T> genericRepresentation) {
        this.related = genericRepresentation.related.clone();
        this.aClass = genericRepresentation.aClass;
        this.isUnique = genericRepresentation.isUnique;
    }

    GenericRepresentation(Class<? extends T> aClass, GenericRepresentation[] related, boolean isUnique) {
        this.aClass = Objects.requireNonNull(aClass);
        this.related = related != null ? related : new GenericRepresentation[0];
        this.isUnique = isUnique;
    }

    @NotNull
    public static String toString(GenericRepresentation genericRepresentation, Function<Class<?>, String> classToString) {

        StringBuilder sb = new StringBuilder();
        String shortName = classToString.apply(genericRepresentation.getAClass());
        sb.append(shortName);

        if (genericRepresentation.getRelated().length != 0) {
            sb.append("<");
            StringJoiner sj = new StringJoiner(", ");

            for (GenericRepresentation loopRef : genericRepresentation.getRelated()) {
                sj.add(toString(loopRef, classToString));
            }

            String processResult = sj.toString();
            sb.append(processResult);
            sb.append(">");
        }

        return sb.toString();
    }


    @NotNull
    public static String toFullString(GenericRepresentation genericRepresentation) {

        return toString(genericRepresentation, Class::getName);
    }

    //java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>

    // < |-> Create
    // , |-> Add
    // > |-> Set as Child

    public static List<GenericRepresentation<?>> fromFullString(String fullString) throws ClassNotFoundException {
        Deque<RepresentationBuilder<?>> builders = new ArrayDeque<>();

        List<GenericRepresentation<?>> genericRepresentationList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = fullString.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];

            if (current == '<') {
                Class<?> s = getCl(stringBuilder);

                RepresentationBuilder<?> a = new RepresentationBuilder<>().a(s);

                if (builders.isEmpty()) {
                    builders.offer(a);
                } else {
                    RepresentationBuilder<?> peek = builders.peekLast();
                    peek.of(a);
                    builders.offer(a);
                }
            } else if (current == ',') {
                if (stringBuilder.length() > 0) {
                    Class<?> s = getCl(stringBuilder);
                    RepresentationBuilder<?> a = new RepresentationBuilder<>().a(s);

                    builders.peekLast().of(a);
                    //builders.offer(a); @Bug
                }
                ++i; //Jump Space after comma
            } else if (current == '>') {
                if (stringBuilder.length() != 0) {
                    Class<?> s = getCl(stringBuilder);
                    RepresentationBuilder<?> a = new RepresentationBuilder<>().a(s);

                    builders.peekLast().of(a);
                }

                RepresentationBuilder<?> poll = builders.pollLast();

                if (!builders.isEmpty()) {
                    RepresentationBuilder<?> representationBuilder = builders.peekLast();

                    if (!representationBuilder.getRelated().contains(poll)) {
                        genericRepresentationList.add(poll.build());
                    }
                } else {
                    genericRepresentationList.add(poll.build());
                }
            } else {
                stringBuilder.append(current);
            }

        }

        if (!builders.isEmpty()) {
            do {
                RepresentationBuilder<?> poll = builders.pollLast();

                if (!builders.isEmpty()) {
                    RepresentationBuilder<?> representationBuilder = builders.peekLast();

                    if (!representationBuilder.getRelated().contains(poll)) {
                        genericRepresentationList.add(poll.build());
                    }
                } else {
                    genericRepresentationList.add(poll.build());
                }
            } while (!builders.isEmpty());

        }

        if (stringBuilder.length() > 0) {
            Class cl = getCl(stringBuilder);
            genericRepresentationList.add(GenericRepresentation.aEnd(cl));
        }

        return genericRepresentationList;
    }

    private static void tryCreate(Deque<RepresentationBuilder<?>> builders, List<GenericRepresentation<?>> genericRepresentationList) {
        RepresentationBuilder<?> poll = builders.pollLast();

        if (!builders.isEmpty()) {
            RepresentationBuilder<?> representationBuilder = builders.peekLast();

            if (!representationBuilder.getRelated().contains(poll)) {
                genericRepresentationList.add(poll.build());
            }
        } else {
            genericRepresentationList.add(poll.build());
        }
    }

    private static Class getCl(StringBuilder stringBuilder) throws ClassNotFoundException {
        String classString = stringBuilder.toString();

        Class clazz = Class.forName(classString);

        stringBuilder.setLength(0);
        return clazz;
    }

    public static <T> RepresentationBuilder<T> to() {
        return referenceTo();
    }

    public static <T> RepresentationBuilder<T> representationOf() {
        return new RepresentationBuilder<>();
    }

    @Deprecated
    public static <T> RepresentationBuilder<T> referenceTo() {
        return new RepresentationBuilder<>();
    }

    public static <T> RepresentationBuilder<T> of(Class<T> aClass) {
        return GenericRepresentation.<T>representationOf().a(aClass);
    }

    public static <T> RepresentationBuilder<T> a(Class<T> aClass) {
        return GenericRepresentation.<T>representationOf().a(aClass);
    }

    public static <T> GenericRepresentation<T> ofEnd(Class<T> aClass) {
        return GenericRepresentation.<T>representationOf().a(aClass).build();
    }

    public static <T> GenericRepresentation<T> aEnd(Class<T> aClass) {
        return GenericRepresentation.<T>representationOf().a(aClass).build();
    }

    @SuppressWarnings("unchecked")
    public static <T> RepresentationBuilder<T> but(GenericRepresentation genericRepresentation) {
        return GenericRepresentation.<T>representationOf().a(genericRepresentation.getAClass()).ofArray(genericRepresentation.getRelated());
    }

    public static GenericRepresentation[] fromProvider(TypeProvider provider) {
        Type[] types = provider.getTypes();
        GenericRepresentation[] genericRepresentations = new GenericRepresentation[types.length];

        for (int i = 0; i < types.length; i++) {
            genericRepresentations[i] = TypeUtil.toReference(types[i]);
        }

        return genericRepresentations;
    }

    public RepresentationBuilder<? extends T> but() {
        return GenericRepresentation.but(this);
    }

    public Class<? extends T> getAClass() {
        return aClass;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public GenericRepresentation[] getRelated() {
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

        if(isUnique)
            return super.hashCode();

        return Objects.hash(aClass, Arrays.deepHashCode(related));
    }

    @Override
    public boolean equals(Object obj) {

        if(isUnique)
            return super.equals(obj);

        if (!(obj instanceof GenericRepresentation))
            return false;

        GenericRepresentation other = (GenericRepresentation) obj;

        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(@NotNull GenericRepresentation compareTo) {

        if (getAClass() == compareTo.getAClass()) {

            if (Arrays.deepEquals(getRelated(), compareTo.getRelated())) {
                return 0;
            }

            return 1;
        }

        return -1;
    }

    public int compareToAssignable(@NotNull GenericRepresentation compareTo) {

        if (getAClass().isAssignableFrom(compareTo.getAClass())) {

            if (getRelated().length != compareTo.getRelated().length)
                return -1;

            for (int x = 0; x < getRelated().length; ++x) {
                GenericRepresentation<?> mainRef = getRelated()[x];
                GenericRepresentation<?> compareRef = compareTo.getRelated()[x];

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
