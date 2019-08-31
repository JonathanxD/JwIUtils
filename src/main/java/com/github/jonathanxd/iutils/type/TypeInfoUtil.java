/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;

import javax.lang.model.type.TypeMirror;

/**
 * Type info utilities.
 */
public final class TypeInfoUtil {

    private TypeInfoUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a String representation from {@code typeInfo}.
     *
     * @param typeInfo      Type info.
     * @param classToString Class to string function.
     * @return String representation from {@code typeInfo}.
     */
    public static String toString(TypeInfo typeInfo, Function<Class<?>, String> classToString) {

        StringBuilder sb = new StringBuilder();

        try {
            String shortName = classToString.apply(typeInfo.getTypeClass());
            sb.append(shortName);
        } catch (Exception e) {
            sb.append(typeInfo.getClassLiteral());
        }

        if (typeInfo.getTypeParameters().size() != 0) {
            sb.append("<");
            StringJoiner sj = new StringJoiner(", ");

            for (TypeInfo<?> loopRef : ((TypeInfo<?>) typeInfo).getTypeParameters()) {
                sj.add(TypeInfoUtil.toString(loopRef, classToString));
            }

            String processResult = sj.toString();
            sb.append(processResult);
            sb.append(">");
        }

        return sb.toString();
    }

    /**
     * Parse {@code fullString} and returns {@link TypeInfo TypeInfo list} that {@code fullString}
     * represents.
     *
     * This method will not resolve the types, it will provide them as class literal, to resolve and
     * load types use: {@link TypeInfo#loadTypes(Function)}.
     *
     * This method can parse {@link TypeMirror#toString() TypeMirror type representation}.
     *
     * @param fullString String representation of an {@link TypeInfo}.
     * @return {@link TypeInfo TypeInfo list} that {@code fullString} represents.
     */
    @SuppressWarnings("Duplicates")
    public static List<TypeInfo<?>> fromFullString(String fullString) {
        return TypeInfoUtil.fromFullString(fullString, it -> null);
    }

    /**
     * Parse {@code fullString} and returns {@link TypeInfo TypeInfo list} that {@code fullString}
     * represents.
     *
     * This method can resolve types through {@code classResolver}, if the resolver returns null,
     * the class will not be resolved, the {@link TypeInfo} will be constructed with type literals
     * that can be resolved later using {@link TypeInfo#loadTypes(Function)}.
     *
     * This method can parse {@link TypeMirror#toString() TypeMirror type representation}.
     *
     * @param fullString    String representation of an {@link TypeInfo}.
     * @param classResolver Resolver of string to class.
     * @return {@link TypeInfo TypeInfo list} that {@code fullString} represents.
     */
    public static List<TypeInfo<?>> fromFullString(String fullString, Function<String, Class<?>> classResolver) {
        Deque<TypeInfoBuilder<?>> builders = new ArrayDeque<>();

        List<TypeInfo<?>> typeInfoList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = fullString.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];

            if (current == '<') {

                String classLiteral = TypeInfoUtil.getClLiteral(stringBuilder);
                TypeInfoBuilder<?> a = TypeInfoUtil.createBuilder(classLiteral, classResolver);

                if (builders.isEmpty()) {
                    builders.offer(a);
                } else {
                    TypeInfoBuilder<?> peek = builders.peekLast();
                    peek.of(a);
                    builders.offer(a);
                }
            } else if (current == ',') {
                if (stringBuilder.length() > 0) {
                    String classLiteral = TypeInfoUtil.getClLiteral(stringBuilder);
                    TypeInfoBuilder<?> a = TypeInfoUtil.createBuilder(classLiteral, classResolver);

                    builders.peekLast().of(a);
                    //builders.offer(a); @Bug
                }
                ++i; //Jump Space after comma
            } else if (current == '>') {
                if (stringBuilder.length() != 0) {
                    String classLiteral = TypeInfoUtil.getClLiteral(stringBuilder);
                    TypeInfoBuilder<?> a = TypeInfoUtil.createBuilder(classLiteral, classResolver);

                    builders.peekLast().of(a);
                }

                TypeInfoBuilder<?> poll = builders.pollLast();

                if (!builders.isEmpty()) {
                    TypeInfoBuilder<?> typeInfoBuilder = builders.peekLast();

                    if (!typeInfoBuilder.getTypeParameters().contains(poll)) {
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

                    if (!typeInfoBuilder.getTypeParameters().contains(poll)) {
                        typeInfoList.add(poll.build());
                    }
                } else {
                    typeInfoList.add(poll.build());
                }
            } while (!builders.isEmpty());

        }

        if (stringBuilder.length() > 0) {
            String classLiteral = TypeInfoUtil.getClLiteral(stringBuilder);
            typeInfoList.add(TypeInfoUtil.createBuilder(classLiteral, classResolver).build());
        }

        return typeInfoList;
    }

    @SuppressWarnings("unchecked")
    private static TypeInfoBuilder<?> createBuilder(String classLiteral, Function<String, Class<?>> resolver) {
        Class<?> r;
        if ((r = resolver.apply(classLiteral)) != null)
            return new TypeInfoBuilder<>().a((Class) r);

        return new TypeInfoBuilder<>().a(classLiteral);
    }


    private static String getClLiteral(StringBuilder stringBuilder) {
        String classString = stringBuilder.toString();
        stringBuilder.setLength(0);
        return classString;
    }

    /**
     * Returns a consistent generic string representation of {@code typeInfo}.
     *
     * @param typeInfo Type information.
     * @return Consistent generic string representation of {@code typeInfo}.
     */
    public static String toFullString(TypeInfo typeInfo) {
        return TypeInfoUtil.toString(typeInfo, Class::getName);
    }

    /**
     * Creates a {@link TypeInfo} from {@code provider}.
     *
     * @param provider Provider.
     * @return {@link TypeInfo} from {@code provider}.
     */
    public static TypeInfo[] fromProvider(TypeProvider provider) {
        Type[] types = provider.getTypes();
        TypeInfo[] typeInfos = new TypeInfo[types.length];

        for (int i = 0; i < types.length; i++) {
            typeInfos[i] = TypeUtil.toTypeInfo(types[i]);
        }

        return typeInfos;
    }

    /**
     * Insert types inferred in {@code resolvedType} in {@code typeMap}.
     *
     * @param resolvedType Inferred types.
     * @param typeMap      Mutable Map to insert inferred types.
     */
    public static void insertTypes(TypeInfo<?> resolvedType, Map<String, TypeInfo<?>> typeMap) {

        Class<?> aClass = resolvedType.getTypeClass();
        List<TypeInfo<?>> infoTypeParameters = resolvedType.getTypeParameters();
        TypeVariable<? extends Class<?>>[] typeParameters = aClass.getTypeParameters();

        if (infoTypeParameters.size() != typeParameters.length)
            throw new IllegalArgumentException("Types not fully resolved!");

        for (int i = 0; i < typeParameters.length; i++) {
            TypeVariable<? extends Class<?>> typeParameter = typeParameters[i];
            TypeInfo typeInfo = infoTypeParameters.get(i);

            String name = typeParameter.getName();

            typeMap.put(name, typeInfo);
        }

    }

    /**
     * Creates sub types information of a {@code receiver} {@link TypeInfo}.
     *
     * @param receiver Receiver.
     * @param <T>      Type.
     * @return Sub types information of {@code receiver}.
     */
    static <T> List<TypeInfo<?>> createSubTypeInfos(TypeInfo<T> receiver) {
        return TypeInfoUtil.getSubTypeInfos(receiver, new HashMap<>());
    }

    private static <T> List<TypeInfo<?>> getSubTypeInfos(TypeInfo<T> receiver, Map<String, TypeInfo<?>> names) {
        Class<? extends T> aClass = receiver.getTypeClass();

        if (aClass == null)
            return Collections.emptyList();

        List<TypeInfo<?>> subInfos = new ArrayList<>();

        Type genericSuperclass = aClass.getGenericSuperclass();

        if (genericSuperclass != null) {
            TypeInfo<?> typeInfo = TypeUtil.toTypeInfo(genericSuperclass, names);
            TypeInfoUtil.insertTypes(typeInfo, names);
            subInfos.add(typeInfo);
        }

        Type[] genericInterfaces = aClass.getGenericInterfaces();

        for (Type genericInterface : genericInterfaces) {
            TypeInfo<?> typeInfo = TypeUtil.toTypeInfo(genericInterface, names);
            TypeInfoUtil.insertTypes(typeInfo, names);

            subInfos.add(typeInfo);
        }


        List<TypeInfo<?>> typeInfos = new ArrayList<>(subInfos);

        for (TypeInfo info : typeInfos) {
            @SuppressWarnings("unchecked") List<TypeInfo<?>> subTypeInfos = TypeInfoUtil.getSubTypeInfos(info, names);

            if (!subTypeInfos.isEmpty()) {
                subInfos.addAll(subTypeInfos);
            }
        }

        // bump
        return subInfos;
    }

    /**
     * Checks if normalized primitive {@code typeInfo} is equal normalized primitive {@code other}. This is used
     * to compare primitive {@link TypeInfo} with a primitive boxed {@link TypeInfo}. Example, this returns {@code true} for
     * {@code isNormalizedEquals(TypeInfo<Boolean>, TypeInfo<boolean>)} (and vice-versa, or with both primitives) but returns false for
     * {@code isNormalizedEquals(TypeInfo<boolean>, TypeInfo<int>)}. If {@code typeInfo} and {@code other} is not primitive, this
     * method fallback to default equals logic. ({@link TypeInfo#equals(Object)}).
     *
     * This method uses {@link Primitive#typeEquals(Class, Class)} to compare primitive type and boxed primitive type.
     *
     * This method also requires both {@code typeInfo} and {@code other} to be resolved.
     *
     * @param typeInfo Primitive type info.
     * @param other    Other primitive type info.
     * @return Whether normalized primitive {@code typeInfo} is equal to normalized primitive {@code other}.
     */
    public static boolean isNormalizedEquals(TypeInfo<?> typeInfo, TypeInfo<?> other) {
        if (typeInfo == null || other == null)
            return typeInfo == other;

        if (!typeInfo.getTypeParameters().isEmpty() || !other.getTypeParameters().isEmpty())
            return typeInfo.equals(other);

        Class<?> typeClass = typeInfo.getTypeClass();
        Class<?> otherTypeClass = other.getTypeClass();

        if (!typeClass.isPrimitive() && !otherTypeClass.isPrimitive())
            return typeInfo.equals(other);

        return Primitive.typeEquals(typeClass, otherTypeClass);
    }

    /**
     * A class resolver that use a list of class loaders to resolve classes. Resolution operation
     * always return {@code null} when none class loader resolves the class.
     */
    public static class ClassLoaderClassResolver implements Function<String, Class<?>> {

        private final List<ClassLoader> classLoaders = new ArrayList<>();

        public ClassLoaderClassResolver() {
        }

        /**
         * Constructs the resolver with {@code defaultLoaders} in list.
         *
         * @param defaultLoaders Default class loaders.
         */
        public ClassLoaderClassResolver(List<ClassLoader> defaultLoaders) {
            this.classLoaders.addAll(defaultLoaders);
        }

        /**
         * Gets the mutable list of class loaders.
         *
         * @return Mutable list of class loaders.
         */
        public List<ClassLoader> getClassLoadersList() {
            return this.classLoaders;
        }

        @Override
        public Class<?> apply(String s) {

            for (ClassLoader classLoader : this.classLoaders) {
                try {
                    return classLoader.loadClass(s);
                } catch (ClassNotFoundException ignored) {
                }
            }

            return null;
        }
    }
}
