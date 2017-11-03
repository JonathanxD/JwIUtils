/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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

import com.github.jonathanxd.iutils.exception.TypeResolutionException;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

public class TypeUtil {

    /**
     * Gets the array base type.
     *
     * @param arrayType Array type.
     * @return Pair of array base type and dimensions.
     */
    public static Pair<Class<?>, Integer> getArrayBaseType(Class<?> arrayType) {

        if (arrayType.isArray()) {
            Class<?> component = arrayType;

            int dimensions = 0;

            do {
                ++dimensions;
            } while ((component = component.getComponentType()).isArray());


            return Pair.of(component, dimensions);
        } else {
            return Pair.of(arrayType, 0);
        }
    }

    /**
     * Creates a {@link TypeInfo} from type variables of {@code subClass} using reified types of
     * {@code classWithReifiedTypes}.
     *
     * @param classWithReifiedTypes Class that provides reified types.
     * @param subClass              Base class to create {@link TypeInfo}.
     * @param <S>                   Base class type.
     * @param <E>                   Reified types provider class type.
     * @return {@link TypeInfo}.
     */
    public static <S, E extends S> TypeInfo<?> resolve(Class<E> classWithReifiedTypes, Class<S> subClass) {
        return TypeUtil.resolve(classWithReifiedTypes, subClass, new HashMap<>());
    }

    private static <S, E extends S> TypeInfo<?> resolve(Class<E> classWithTypeVariable, Class<S> subClass, Map<String, TypeInfo<?>> names) {
        Type genericSuperclass = classWithTypeVariable.getGenericSuperclass();
        Class<?> superClass = classWithTypeVariable.getSuperclass();

        TypeInfo<?> from = TypeUtil.from(genericSuperclass, superClass, subClass, names);

        if (from == null) {
            Type[] genericInterfaces = classWithTypeVariable.getGenericInterfaces();
            Class<?>[] interfacesClasses = classWithTypeVariable.getInterfaces();

            for (int i = 0; i < interfacesClasses.length; i++) {
                Type genericInterface = genericInterfaces[i];
                Class<?> interfaceClass = interfacesClasses[i];

                from = TypeUtil.from(genericInterface, interfaceClass, subClass, names);
                if (from != null)
                    break;
            }
        }

        if (from != null) {
            return from;
        }

        return null;
    }

    private static TypeInfo<?> from(Type type, Class<?> actual, Class<?> expected, Map<String, TypeInfo<?>> names) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            if (expected.isAssignableFrom(actual)) {
                return TypeUtil.toTypeInfo(parameterizedType, names);
            }

        } else if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length > 0) {
                return TypeUtil.toTypeInfo(bounds[0], names);
            }
        }

        return null;
    }

    /**
     * Creates a array of {@link TypeInfo} from type variables of {@code classWithTypeVariables}.
     *
     * @param classWithTypeVariables Class with type variables.
     * @return Array of {@link TypeInfo} from type variables of {@code classWithTypeVariables}.
     */
    public static TypeInfo<?>[] infoOfTypeVariables(Class<?> classWithTypeVariables) {
        return TypeUtil.infoOfTypeVariables(classWithTypeVariables, new HashMap<>());
    }

    /**
     * Creates a array of {@link TypeInfo} from type variables of {@code classWithTypeVariables}.
     *
     * @param classWithTypeVariables Class with type variables.
     * @param names                  Map of type variable name to inferred type.
     * @return Array of {@link TypeInfo} from type variables of {@code classWithTypeVariables}.
     */
    public static TypeInfo<?>[] infoOfTypeVariables(Class<?> classWithTypeVariables, Map<String, TypeInfo<?>> names) {
        return TypeUtil.toTypeInfo(TypeUtil.getTypeVariableTypes(classWithTypeVariables, names), names);
    }

    /**
     * Gets array of {@link Type} from {@code classWithTypeVariables}.
     *
     * @param classWithTypeVariables Class with type variables.
     * @return Array of {@link Type} from {@code classWithTypeVariables}.
     */
    public static Type[] getTypeVariableTypes(Class<?> classWithTypeVariables) {
        return TypeUtil.getTypeVariableTypes(classWithTypeVariables, new HashMap<>());
    }

    /**
     * Gets array of {@link Type} from {@code classWithTypeVariables}.
     *
     * @param classWithTypeVariables Class with type variables.
     * @param names                  Map of type variable name to inferred type.
     * @return Array of {@link Type} from {@code classWithTypeVariables}.
     */
    public static Type[] getTypeVariableTypes(Class<?> classWithTypeVariables, Map<String, TypeInfo<?>> names) {

        TypeVariable<? extends Class<?>>[] typeParameters = classWithTypeVariables.getTypeParameters();

        if (typeParameters.length == 0) {
            throw new IllegalStateException("No Type Variables");
        }

        Type[] types = new Type[typeParameters.length];

        for (int i = 0; i < typeParameters.length; i++) {
            TypeVariable<? extends Class<?>> typeParameter = typeParameters[i];

            if (names.containsKey(typeParameter.getName())) {
                types[i] = names.get(typeParameter.getName()).getTypeClass();
            } else {

                Type[] bounds = typeParameter.getBounds();

                if (bounds.length > 0) {
                    types[i] = bounds[0];
                } else {
                    types[i] = Object.class;
                }
            }
        }

        return types;
    }

    /**
     * Creates an array of {@link TypeInfo TypeInfos} from {@link Type type array}.
     *
     * @param param Type array.
     * @return Array of {@link TypeInfo TypeInfos} from {@link Type type array}.
     */
    public static TypeInfo<?>[] toTypeInfo(Type[] param) {
        return TypeUtil.toTypeInfo(param, new HashMap<>());
    }

    /**
     * Creates an array of {@link TypeInfo TypeInfos} from {@link Type type array}.
     *
     * @param param Type array.
     * @param names Map of type variable name to inferred type.
     * @return Array of {@link TypeInfo TypeInfos} from {@link Type type array}.
     */
    public static TypeInfo<?>[] toTypeInfo(Type[] param, Map<String, TypeInfo<?>> names) {
        TypeInfo<?>[] typeInfos = new TypeInfo[param.length];

        for (int i = 0; i < param.length; i++) {
            typeInfos[i] = TypeUtil.toTypeInfo(param[i], names);
        }

        return typeInfos;
    }

    /**
     * Creates a {@link TypeInfo} from {@link Type}.
     *
     * @param param Type.
     * @return {@link TypeInfo} from {@link Type}.
     */
    public static TypeInfo<?> toTypeInfo(Type param) {
        return TypeUtil.toTypeInfo(param, new HashMap<>());
    }

    /**
     * Creates a {@link TypeInfo} from {@link Type}.
     *
     * @param param Type.
     * @param names Map of type variable name to inferred type.
     * @return {@link TypeInfo} from {@link Type}.
     */
    public static TypeInfo<?> toTypeInfo(Type param, Map<String, TypeInfo<?>> names) {
        if (param instanceof ParameterizedType) {
            return TypeUtil.toTypeInfo((ParameterizedType) param, names);
        }

        if (param instanceof GenericArrayType) {

            GenericArrayType genericArrayType = (GenericArrayType) param;
            TypeInfo<?> typeInfo = TypeUtil.toTypeInfo(TypeUtil.getBaseComponent(genericArrayType), names);

            String pr = param.toString();

            int start = pr.indexOf('<');
            int end = pr.lastIndexOf('>') + 1;

            String name = pr.substring(0, start);

            String arrays = pr.substring(end, pr.length()).replace("]", "");

            String fullName = (arrays + "L" + name) + ";";

            try {
                Reflection.changeFinalField(TypeInfo.class, typeInfo, "classLiteral", fullName);

                if(typeInfo.isResolved()) {
                    ClassLoader loader = typeInfo.getTypeClass().getClassLoader();

                    Class<?> arrayCached = loader == null ? Class.forName(fullName) : loader.loadClass(fullName);
                    Reflection.changeFinalField(TypeInfo.class, typeInfo, "cachedAClass", arrayCached);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return typeInfo;

        }

        if (param instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) param;

            if (names.containsKey(typeVariable.getName())) {
                return names.get(typeVariable.getName());
            }

        }

        return TypeInfo.of(TypeUtil.from(param, names));
    }

    /**
     * Creates a {@link TypeInfo} from {@link ParameterizedType}.
     *
     * @param param Parameterized type.
     * @return {@link TypeInfo} from {@link ParameterizedType}.
     */
    public static TypeInfo<?> toTypeInfo(ParameterizedType param) {
        return TypeUtil.toTypeInfo(param, new HashMap<>());
    }

    /**
     * Creates a {@link TypeInfo} from {@link ParameterizedType}.
     *
     * @param param Parameterized type.
     * @param names Map of type variable name to inferred type.
     * @return {@link TypeInfo} from {@link ParameterizedType}.
     */
    public static TypeInfo<?> toTypeInfo(ParameterizedType param, Map<String, TypeInfo<?>> names) {
        TypeInfoBuilder<?> typeInfoBuilder = TypeInfo.builderOf(TypeUtil.from(param.getRawType(), names));
        for (Type type : param.getActualTypeArguments()) {
            if (!(type instanceof ParameterizedType)) {

                if (type instanceof TypeVariable) {
                    TypeVariable typeVar = (TypeVariable) type;

                    if (names.containsKey(typeVar.getName())) {
                        TypeInfo<?> typeInfo = names.get(typeVar.getName());

                        typeInfoBuilder.of(typeInfo);
                        continue;
                    }
                }

                Class<?> from = TypeUtil.from(type, names);

                if (from != null) {
                    typeInfoBuilder.of(from);
                } else {
                    break;
                }
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                typeInfoBuilder.of(TypeUtil.toTypeInfo(parameterizedType, names));
            }
        }
        return typeInfoBuilder.build();
    }

    /**
     * Gets base component of {@code genericArrayType}.
     * @param genericArrayType Generic array type.
     * @return Base component.
     */
    public static Type getBaseComponent(GenericArrayType genericArrayType) {
        Type component = genericArrayType.getGenericComponentType();

        while(component instanceof GenericArrayType) {
            component = ((GenericArrayType) component).getGenericComponentType();
        }

        return component;
    }

    /**
     * Tries to resolve {@link Class} from {@link Type}.
     *
     * @param t Type.
     * @return Resolved type or null if cannot resolve.
     */
    public static Class<?> from(Type t) {
        return TypeUtil.from(t, new HashMap<>());
    }

    /**
     * Tries to resolve {@link Class} from {@link Type}.
     *
     * @param t     Type.
     * @param names Map of type variable name to inferred type.
     * @return Resolved type or null if cannot resolve.
     */
    public static Class<?> from(Type t, Map<String, TypeInfo<?>> names) {
        if (t instanceof Class) {
            return (Class<?>) t;
        }

        if (t instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) t;
            Type[] upperBounds = wildcardType.getUpperBounds();

            if (upperBounds.length > 0) {
                return TypeUtil.from(upperBounds[0], names);
            } else {
                return Object.class;
            }
        }

        if (t instanceof TypeVariable) {
            String name = ((TypeVariable) t).getName();

            if (names.containsKey(name)) {
                return names.get(name).getTypeClass();
            }

            return Object.class;
        }

        try {
            return Class.forName(t.getTypeName());
        } catch (Exception e) {
            return null; // To fix some problems
        }
    }

    /**
     * Finds {@code parameterizedType} generic information provided by {@code classToAnalyze}.
     *
     * @param classToAnalyze    Class to analyze.
     * @param parameterizedType Base type to infer types from generic information provided by {@code
     *                          classToAnalyze}.
     * @return {@link ParameterizedType} of {@code parameterizedType} provided by {@code
     * classToAnalyze}, or null if cannot find.
     */
    public static ParameterizedType findParameterizedClass(Class<?> classToAnalyze, Class<?> parameterizedType) {
        Class<?> superClass = classToAnalyze.getSuperclass();

        if (superClass.equals(parameterizedType)) {
            if (classToAnalyze.getGenericSuperclass() instanceof ParameterizedType) {
                return (ParameterizedType) classToAnalyze.getGenericSuperclass();
            }
        }

        Class[] types = classToAnalyze.getInterfaces();
        Type[] genericInterfaces = classToAnalyze.getGenericInterfaces();

        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(parameterizedType)) {
                if (genericInterfaces[i] instanceof ParameterizedType) {
                    return (ParameterizedType) genericInterfaces[i];
                }
            }
        }

        if (!superClass.equals(Object.class)) {
            ParameterizedType find = findParameterizedClass(superClass, parameterizedType);
            if (find != null) {
                return find;
            }
        }

        if (parameterizedType.isInterface()) {
            for (Class type : types) {
                ParameterizedType find = findParameterizedClass(type, parameterizedType);

                if (find != null) {
                    return find;
                }
            }
        }

        return null;
    }

    /**
     * Gets the {@link Class} instance from {@code str}.
     *
     * @param str Name of the class.
     * @return {@link Class} instance.
     * @throws ClassNotFoundException If class with name {@code str} cannot be found.
     */
    public static Class classForName(String str) throws ClassNotFoundException {
        if (str.equals("byte"))
            return Byte.TYPE;
        if (str.equals("short"))
            return Short.TYPE;
        if (str.equals("char"))
            return Character.TYPE;
        if (str.equals("int"))
            return Integer.TYPE;
        if (str.equals("float"))
            return Float.TYPE;
        if (str.equals("double"))
            return Double.TYPE;
        if (str.equals("long"))
            return Long.TYPE;
        if (str.equals("boolean"))
            return Boolean.TYPE;
        if (str.equals("void"))
            return Void.TYPE;

        return Class.forName(str);
    }

    /**
     * Tries to resolve {@link Class} from {@code classLiteral}.
     *
     * @param classLiteral Class literal.
     * @param <V>          Class type.
     * @return {@link Class} resolved from {@code classLiteral}.
     * @throws TypeResolutionException if class cannot be found.
     */
    @SuppressWarnings("unchecked")
    public static <V> Class<V> resolveClass(String classLiteral) throws TypeResolutionException {

        String fixed = fixName(classLiteral);
        try {
            return (Class<V>) TypeUtil.classForName(fixed);
        } catch (ClassNotFoundException e) {
            throw new TypeResolutionException("Failed to resolve class literal: '" + classLiteral + "' fixed: '" + fixed + "'!", e);
        }
    }

    static String fixName(String classLiteral) {
        StringBuilder stringBuilder = new StringBuilder();

        if (classLiteral.endsWith("[]")) {

            stringBuilder.append("L");

            for (char c : classLiteral.toCharArray()) {
                if (c != '[' && c != ']')
                    stringBuilder.append(c);

                if (c == '[')
                    stringBuilder.insert(0, '[');
            }

            stringBuilder.append(";");
        } else {
            stringBuilder.append(classLiteral);
        }

        return stringBuilder.toString();
    }

}
