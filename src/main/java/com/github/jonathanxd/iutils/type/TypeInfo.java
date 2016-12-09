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
package com.github.jonathanxd.iutils.type;

import com.github.jonathanxd.iutils.annotation.NotNull;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

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
     * Sub types info
     */
    private final TypeInfo<?>[] subTypesInfo;

    /**
     * Marking it as unique will make this to use default implementation of {@link #hashCode()} and
     * {@link #equals(Object)} from {@link Object}
     */
    private final boolean isUnique;

    protected TypeInfo() {
        this.aClass = null;
        this.related = null;
        this.isUnique = false;
        this.subTypesInfo = TypeInfo.createSubTypeInfos(this);
    }


    protected TypeInfo(TypeInfo<T> typeInfo) {
        this.related = typeInfo.related.clone();
        this.aClass = typeInfo.aClass;
        this.isUnique = typeInfo.isUnique;
        this.subTypesInfo = TypeInfo.createSubTypeInfos(this);
    }

    TypeInfo(Class<? extends T> aClass, TypeInfo[] related, boolean isUnique) {
        this.aClass = Objects.requireNonNull(aClass);
        this.related = related != null ? related : new TypeInfo[0];
        this.isUnique = isUnique;
        this.subTypesInfo = TypeInfo.createSubTypeInfos(this);
    }

    @NotNull
    public static String toString(TypeInfo typeInfo, Function<Class<?>, String> classToString) {

        StringBuilder sb = new StringBuilder();
        String shortName = classToString.apply(typeInfo.getAClass());
        sb.append(shortName);

        if (typeInfo.fastGetRelated().length != 0) {
            sb.append("<");
            StringJoiner sj = new StringJoiner(", ");

            for (TypeInfo loopRef : typeInfo.fastGetRelated()) {
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
        return TypeInfo.<T>representationOf().a(typeInfo.getAClass()).ofArray(typeInfo.fastGetRelated());
    }

    public static TypeInfo[] fromProvider(TypeProvider provider) {
        Type[] types = provider.getTypes();
        TypeInfo[] typeInfos = new TypeInfo[types.length];

        for (int i = 0; i < types.length; i++) {
            typeInfos[i] = TypeUtil.toReference(types[i]);
        }

        return typeInfos;
    }

    public static void insertTypes(TypeInfo<?> resolvedType, Map<String, TypeInfo<?>> typeMap) {

        Class<?> aClass = resolvedType.getAClass();
        TypeInfo[] related = resolvedType.fastGetRelated();
        TypeVariable<? extends Class<?>>[] typeParameters = aClass.getTypeParameters();

        if (related.length != typeParameters.length)
            throw new IllegalArgumentException("Types not fully resolved!");

        for (int i = 0; i < typeParameters.length; i++) {
            TypeVariable<? extends Class<?>> typeParameter = typeParameters[i];
            TypeInfo typeInfo = related[i];

            String name = typeParameter.getName();

            typeMap.put(name, typeInfo);
        }

    }

    protected static <T> TypeInfo<?>[] createSubTypeInfos(TypeInfo<T> receiver) {
        return TypeInfo.getSubTypeInfos(receiver, new HashMap<>());
    }

    protected static <T> TypeInfo<?>[] getSubTypeInfos(TypeInfo<T> receiver, Map<String, TypeInfo<?>> names) {
        Class<? extends T> aClass = receiver.getAClass();

        if (aClass == null)
            return new TypeInfo[0];

        List<TypeInfo<?>> subInfos = new ArrayList<>();

        Type genericSuperclass = aClass.getGenericSuperclass();

        if (genericSuperclass != null) {
            TypeInfo<?> typeInfo = TypeUtil.toReference(genericSuperclass, names);
            TypeInfo.insertTypes(typeInfo, names);
            subInfos.add(typeInfo);
        }

        Type[] genericInterfaces = aClass.getGenericInterfaces();

        for (Type genericInterface : genericInterfaces) {
            TypeInfo<?> typeInfo = TypeUtil.toReference(genericInterface, names);
            TypeInfo.insertTypes(typeInfo, names);

            subInfos.add(typeInfo);
        }


        TypeInfo[] typeInfos = subInfos.stream().toArray(TypeInfo[]::new);

        for (TypeInfo info : typeInfos) {
            @SuppressWarnings("unchecked") TypeInfo<?>[] subTypeInfos = TypeInfo.getSubTypeInfos(info, names);

            if (subTypeInfos.length > 0) {
                Collections.addAll(subInfos, subTypeInfos);
            }
        }

        // bump
        return subInfos.toArray(new TypeInfo[subInfos.size()]);
    }

    @SuppressWarnings("unchecked")
    public TypeInfo<T> toUnique() {
        return this.isUnique() ? this : (TypeInfo<T>) this.but().setUnique(true).build();
    }

    public TypeInfoBuilder<? extends T> but() {
        return TypeInfo.but(this);
    }

    public Class<? extends T> getAClass() {
        return this.aClass;
    }

    public boolean isUnique() {
        return this.isUnique;
    }

    public TypeInfo[] getRelated() {
        return this.related.clone();
    }

    /**
     * Gets the array without cloning.
     *
     * @return Original Array.
     */
    protected TypeInfo[] fastGetRelated() {
        return this.related;
    }

    public TypeInfo<?>[] getSubTypeInfos() {
        return this.subTypesInfo.clone();
    }

    /**
     * Gets the array without cloning.
     *
     * @return Original Array.
     */
    protected TypeInfo<?>[] fastGetSubTypeInfos() {
        return this.subTypesInfo;
    }

    public boolean isAssignableFrom(TypeInfo<?> other) {
        if (this.compareTypeAndRelatedTo(other) == 0)
            return true;

        TypeInfo<?>[] otherSubTypeInfos = other.fastGetSubTypeInfos();

        if (otherSubTypeInfos.length == 1)
            return this.isAssignableFrom(otherSubTypeInfos[0]);

        for (TypeInfo<?> otherSubTypeInfo : otherSubTypeInfos) {
            if (this.isAssignableFrom(otherSubTypeInfo)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Cast this {@link TypeInfo} type to another.
     *
     * This method doesn't effectively cast the types, it only let the Type System to treat this
     * {@link TypeInfo} as a info of type {@link U}.
     *
     * @param <U> New Type.
     * @return Casted {@code this}.
     */
    @SuppressWarnings("unchecked")
    public <U> TypeInfo<U> cast() {
        return (TypeInfo<U>) this;
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

        if (isUnique)
            return super.hashCode();

        return Objects.hash(aClass, Arrays.deepHashCode(related));
    }

    @Override
    public boolean equals(Object obj) {

        if (isUnique)
            return super.equals(obj);

        if (!(obj instanceof TypeInfo))
            return false;

        TypeInfo other = (TypeInfo) obj;

        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(@NotNull TypeInfo compareTo) {

        if (this.getAClass() == compareTo.getAClass()) {

            if (Arrays.deepEquals(this.fastGetRelated(), compareTo.fastGetRelated())) {
                return 0;
            }

            return 1;
        }

        return -1;
    }

    /**
     * @deprecated Don't work correctly for sub-types, use: {@link #isAssignableFrom(TypeInfo)}
     */
    @Deprecated
    public int compareToAssignable(@NotNull TypeInfo compareTo) {

        if (this.getAClass().isAssignableFrom(compareTo.getAClass())) {

            TypeInfo[] thisRelated = this.fastGetRelated();
            TypeInfo[] compareToRelated = compareTo.fastGetRelated();

            if (thisRelated.length != compareToRelated.length)
                return -1;

            for (int x = 0; x < thisRelated.length; ++x) {
                TypeInfo<?> mainRef = thisRelated[x];
                TypeInfo<?> compareRef = compareToRelated[x];

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

    /**
     * Restrictively compare current type and related types to types and related types of another
     * {@link TypeInfo}.
     *
     * @param compareTo Element to compare
     * @return 0 if comparison succeed, positive or negative number if not.
     */
    public int compareTypeAndRelatedTo(@NotNull TypeInfo compareTo) {

        if (this.getAClass().isAssignableFrom(compareTo.getAClass())) {
            TypeInfo[] thisRelated = this.fastGetRelated();
            TypeInfo[] otherRelated = compareTo.fastGetRelated();

            if (thisRelated.length != otherRelated.length)
                return -1;

            for (int x = 0; x < thisRelated.length; ++x) {
                Class<?> mainRefClass = thisRelated[x].getAClass();
                Class<?> compareRefClass = otherRelated[x].getAClass();

                if (!mainRefClass.isAssignableFrom(compareRefClass)) {

                    if (compareRefClass.isAssignableFrom(mainRefClass)) {
                        return 1;
                    }

                    return -1;
                }
            }

            return 0;
        }

        return -1;
    }

    /**
     * Same as {@link #compareTo(TypeInfo)}
     */
    public static class ExactlyComparator implements Comparator<TypeInfo<?>> {

        @Override
        public int compare(TypeInfo<?> o1, TypeInfo<?> o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Compare if {@link TypeInfo typeInfo1} is assignable to {@link TypeInfo typeInfo2}.
     */
    public static class AssignableComparator implements Comparator<TypeInfo<?>> {

        @Override
        public int compare(TypeInfo<?> o1, TypeInfo<?> o2) {
            return o1.isAssignableFrom(o2) ? 0 : -1;
        }
    }

}
