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

        if (typeInfo.fastGetRelated().length != 0) {
            sb.append("<");
            StringJoiner sj = new StringJoiner(", ");

            for (TypeInfo loopRef : typeInfo.fastGetRelated()) {
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
     * This method may parse {@link TypeMirror#toString() TypeMirror type representation}.
     *
     * @param fullString String representation of an {@link TypeInfo}.
     * @return {@link TypeInfo TypeInfo list} that {@code fullString} represents.
     */
    @SuppressWarnings("Duplicates")
    public static List<TypeInfo<?>> fromFullString(String fullString) {
        Deque<TypeInfoBuilder<?>> builders = new ArrayDeque<>();

        List<TypeInfo<?>> typeInfoList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = fullString.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];

            if (current == '<') {

                String classLiteral = getClLiteral(stringBuilder);

                TypeInfoBuilder<?> a = new TypeInfoBuilder<>().a(classLiteral);

                if (builders.isEmpty()) {
                    builders.offer(a);
                } else {
                    TypeInfoBuilder<?> peek = builders.peekLast();
                    peek.of(a);
                    builders.offer(a);
                }
            } else if (current == ',') {
                if (stringBuilder.length() > 0) {
                    String classLiteral = getClLiteral(stringBuilder);
                    TypeInfoBuilder<?> a = new TypeInfoBuilder<>().a(classLiteral);

                    builders.peekLast().of(a);
                    //builders.offer(a); @Bug
                }
                ++i; //Jump Space after comma
            } else if (current == '>') {
                if (stringBuilder.length() != 0) {
                    String classLiteral = getClLiteral(stringBuilder);
                    TypeInfoBuilder<?> a = new TypeInfoBuilder<>().a(classLiteral);

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
            String classLiteral = getClLiteral(stringBuilder);
            typeInfoList.add(TypeInfo.of(classLiteral));
        }

        return typeInfoList;
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

    /**
     * Creates sub types information of a {@code receiver} {@link TypeInfo}.
     *
     * @param receiver Receiver.
     * @param <T>      Type.
     * @return Sub types information of {@code receiver}.
     */
    static <T> TypeInfo<?>[] createSubTypeInfos(TypeInfo<T> receiver) {
        return TypeInfoUtil.getSubTypeInfos(receiver, new HashMap<>());
    }

    private static <T> TypeInfo<?>[] getSubTypeInfos(TypeInfo<T> receiver, Map<String, TypeInfo<?>> names) {
        Class<? extends T> aClass = receiver.getTypeClass();

        if (aClass == null)
            return new TypeInfo[0];

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


        TypeInfo[] typeInfos = subInfos.stream().toArray(TypeInfo[]::new);

        for (TypeInfo info : typeInfos) {
            @SuppressWarnings("unchecked") TypeInfo<?>[] subTypeInfos = TypeInfoUtil.getSubTypeInfos(info, names);

            if (subTypeInfos.length > 0) {
                Collections.addAll(subInfos, subTypeInfos);
            }
        }

        // bump
        return subInfos.toArray(new TypeInfo[subInfos.size()]);
    }
}
