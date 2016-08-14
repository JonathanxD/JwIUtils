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
public class TypeInfo<T> implements Comparable<TypeInfo> {

    /**
     * Accessed and modified via reflection in {@link AbstractTypeInfo}
     */
    private final Class<? extends T> aClass;

    /**
     * Accessed and modified via reflection in {@link AbstractTypeInfo} & {@link DynamicTypeInfo}
     */
    private final TypeInfo[] related;

    /**
     * Accessed and modified via reflection in {@link AbstractTypeInfo}
     * @deprecated
     */
    //private final Object hold;

    /**
     * Unique GenericRepresentation uses default {@link #equals(Object)} and {@link #hashCode()}, and identique
     */
    private final boolean isUnique;

    protected TypeInfo() {
        this.aClass = null;
        this.related = null;
        //this.hold = null;
        this.isUnique = false;
    }


    protected TypeInfo(TypeInfo<T> typeInfo) {
        this.related = typeInfo.related.clone();
        this.aClass = typeInfo.aClass;
        this.isUnique = typeInfo.isUnique;
    }

    TypeInfo(Class<? extends T> aClass, TypeInfo[] related, boolean isUnique) {
        this.aClass = Objects.requireNonNull(aClass);
        this.related = related != null ? related : new TypeInfo[0];
        this.isUnique = isUnique;
    }

    @NotNull
    public static String toString(TypeInfo typeInfo, Function<Class<?>, String> classToString) {

        StringBuilder sb = new StringBuilder();
        String shortName = classToString.apply(typeInfo.getAClass());
        sb.append(shortName);

        if (typeInfo.getRelated().length != 0) {
            sb.append("<");
            StringJoiner sj = new StringJoiner(", ");

            for (TypeInfo loopRef : typeInfo.getRelated()) {
                sj.add(toString(loopRef, classToString));
            }

            String processResult = sj.toString();
            sb.append(processResult);
            sb.append(">");
        }

        return sb.toString();
    }


    @NotNull
    public static String toFullString(TypeInfo typeInfo) {

        return toString(typeInfo, Class::getName);
    }

    //java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>

    // < |-> Create
    // , |-> Add
    // > |-> Set as Child

    public static List<TypeInfo<?>> fromFullString(String fullString) throws ClassNotFoundException {
        Deque<TypeInfoBuilder<?>> builders = new ArrayDeque<>();

        List<TypeInfo<?>> typeInfoList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = fullString.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];

            if (current == '<') {
                Class<?> s = getCl(stringBuilder);

                TypeInfoBuilder<?> a = new TypeInfoBuilder<>().a(s);

                if (builders.isEmpty()) {
                    builders.offer(a);
                } else {
                    TypeInfoBuilder<?> peek = builders.peekLast();
                    peek.of(a);
                    builders.offer(a);
                }
            } else if (current == ',') {
                if (stringBuilder.length() > 0) {
                    Class<?> s = getCl(stringBuilder);
                    TypeInfoBuilder<?> a = new TypeInfoBuilder<>().a(s);

                    builders.peekLast().of(a);
                    //builders.offer(a); @Bug
                }
                ++i; //Jump Space after comma
            } else if (current == '>') {
                if (stringBuilder.length() != 0) {
                    Class<?> s = getCl(stringBuilder);
                    TypeInfoBuilder<?> a = new TypeInfoBuilder<>().a(s);

                    builders.peekLast().of(a);
                }

                TypeInfoBuilder<?> poll = builders.pollLast();

                if (!builders.isEmpty()) {
                    TypeInfoBuilder<?> typeInfoBuilder = builders.peekLast();

                    if (!typeInfoBuilder.getRelated().contains(poll)) {
                        typeInfoList.add(poll.build());
                    }
                } else {
                    typeInfoList.add(poll.build());
                }
            } else {
                stringBuilder.append(current);
            }

        }

        if (!builders.isEmpty()) {
            do {
                TypeInfoBuilder<?> poll = builders.pollLast();

                if (!builders.isEmpty()) {
                    TypeInfoBuilder<?> typeInfoBuilder = builders.peekLast();

                    if (!typeInfoBuilder.getRelated().contains(poll)) {
                        typeInfoList.add(poll.build());
                    }
                } else {
                    typeInfoList.add(poll.build());
                }
            } while (!builders.isEmpty());

        }

        if (stringBuilder.length() > 0) {
            Class cl = getCl(stringBuilder);
            typeInfoList.add(TypeInfo.aEnd(cl));
        }

        return typeInfoList;
    }

    private static void tryCreate(Deque<TypeInfoBuilder<?>> builders, List<TypeInfo<?>> typeInfoList) {
        TypeInfoBuilder<?> poll = builders.pollLast();

        if (!builders.isEmpty()) {
            TypeInfoBuilder<?> typeInfoBuilder = builders.peekLast();

            if (!typeInfoBuilder.getRelated().contains(poll)) {
                typeInfoList.add(poll.build());
            }
        } else {
            typeInfoList.add(poll.build());
        }
    }

    private static Class getCl(StringBuilder stringBuilder) throws ClassNotFoundException {
        String classString = stringBuilder.toString();

        Class clazz = Class.forName(classString);

        stringBuilder.setLength(0);
        return clazz;
    }

    public static <T> TypeInfoBuilder<T> to() {
        return referenceTo();
    }

    public static <T> TypeInfoBuilder<T> representationOf() {
        return new TypeInfoBuilder<>();
    }

    public static <T> TypeInfoBuilder<T> representationOfUnique() {
        return new TypeInfoBuilder<T>().setUnique(true);
    }

    @Deprecated
    public static <T> TypeInfoBuilder<T> referenceTo() {
        return new TypeInfoBuilder<>();
    }

    public static <T> TypeInfoBuilder<T> of(Class<T> aClass) {
        return TypeInfo.<T>representationOf().a(aClass);
    }

    public static <T> TypeInfoBuilder<T> builderOfUnique(Class<T> aClass) {
        return TypeInfo.<T>representationOf().a(aClass).setUnique(true);
    }

    public static <T> TypeInfoBuilder<T> a(Class<T> aClass) {
        return TypeInfo.<T>representationOf().a(aClass);
    }

    public static <T> TypeInfo<T> ofEnd(Class<T> aClass) {
        return TypeInfo.<T>representationOf().a(aClass).build();
    }

    public static <T> TypeInfo<T> aEnd(Class<T> aClass) {
        return TypeInfo.<T>representationOf().a(aClass).build();
    }

    public static <T> TypeInfo<T> ofUnique(Class<T> aClass) {
        return TypeInfo.<T>representationOf().a(aClass).setUnique(true).build();
    }

    public static <T> TypeInfo<T> aUnique(Class<T> aClass) {
        return TypeInfo.<T>representationOf().a(aClass).setUnique(true).build();
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeInfoBuilder<T> but(TypeInfo typeInfo) {
        return TypeInfo.<T>representationOf().a(typeInfo.getAClass()).ofArray(typeInfo.getRelated());
    }

    public static TypeInfo[] fromProvider(TypeProvider provider) {
        Type[] types = provider.getTypes();
        TypeInfo[] typeInfos = new TypeInfo[types.length];

        for (int i = 0; i < types.length; i++) {
            typeInfos[i] = TypeUtil.toReference(types[i]);
        }

        return typeInfos;
    }

    public TypeInfoBuilder<? extends T> but() {
        return TypeInfo.but(this);
    }

    public Class<? extends T> getAClass() {
        return aClass;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public TypeInfo[] getRelated() {
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

        if (!(obj instanceof TypeInfo))
            return false;

        TypeInfo other = (TypeInfo) obj;

        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(@NotNull TypeInfo compareTo) {

        if (getAClass() == compareTo.getAClass()) {

            if (Arrays.deepEquals(getRelated(), compareTo.getRelated())) {
                return 0;
            }

            return 1;
        }

        return -1;
    }

    public int compareToAssignable(@NotNull TypeInfo compareTo) {

        if (getAClass().isAssignableFrom(compareTo.getAClass())) {

            if (getRelated().length != compareTo.getRelated().length)
                return -1;

            for (int x = 0; x < getRelated().length; ++x) {
                TypeInfo<?> mainRef = getRelated()[x];
                TypeInfo<?> compareRef = compareTo.getRelated()[x];

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
