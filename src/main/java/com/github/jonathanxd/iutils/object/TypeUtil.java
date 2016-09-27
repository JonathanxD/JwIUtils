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

import com.github.jonathanxd.iutils.reflection.RClass;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by jonathan on 28/02/16.
 */
public class TypeUtil {



    public static Class<?>[] typesAsClass(Type[] parTypes) throws ClassNotFoundException {
        Class<?>[] typesClass = new Class<?>[parTypes.length];

        for (int x = 0; x < parTypes.length; ++x) {
            typesClass[x] = Class.forName(parTypes[x].getTypeName());
        }

        return typesClass;
    }

    public static <T> void deepTypes(Type[] types, Predicate<Type> continueIf, Function<Type, T> converter, Consumer<T> typeConsumer) {
        for (Type type : types) {
            if (continueIf.test(type)) {
                T converted = converter.apply(type);
                typeConsumer.accept(converted);
            }
        }
    }

    public static <S, E extends S> TypeInfo<?> resolve(Class<E> classWithTypeVariable, Class<S> subClass) {
        return TypeUtil.resolve(classWithTypeVariable, subClass, new HashMap<>());
    }

    public static <S, E extends S> TypeInfo<?> resolve(Class<E> classWithTypeVariable, Class<S> subClass, Map<String, TypeInfo<?>> names) {
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

    private static TypeInfo<?> from(Type type, Class<?> actual, Class<?> expected) {
        return TypeUtil.from(type, actual, expected, new HashMap<>());
    }

    private static TypeInfo<?> from(Type type, Class<?> actual, Class<?> expected, Map<String, TypeInfo<?>> names) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            if (expected.isAssignableFrom(actual)) {
                return TypeUtil.toReference(parameterizedType, names);
            }

        } else if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length > 0) {
                return TypeUtil.toReference(bounds[0], names);
            }
        }

        return null;
    }

    public static TypeInfo<?>[] genericRepOfVariableTypes(Class<?> classWithTypeVariable) {
        return TypeUtil.genericRepOfVariableTypes(classWithTypeVariable, new HashMap<>());
    }

    public static TypeInfo<?>[] genericRepOfVariableTypes(Class<?> classWithTypeVariable, Map<String, TypeInfo<?>> names) {

        return TypeUtil.toReferences(TypeUtil.getTypeVariableTypes(classWithTypeVariable, names), names);
    }

    public static Type[] getTypeVariableTypes(Class<?> classWithTypeVariable) {
        return TypeUtil.getTypeVariableTypes(classWithTypeVariable, new HashMap<>());
    }

    public static Type[] getTypeVariableTypes(Class<?> classWithTypeVariable, Map<String, TypeInfo<?>> names) {

        TypeVariable<? extends Class<?>>[] typeParameters = classWithTypeVariable.getTypeParameters();

        if (typeParameters.length == 0) {
            throw new IllegalStateException("No Type Variables");
        }

        Type[] types = new Type[typeParameters.length];

        for (int i = 0; i < typeParameters.length; i++) {
            TypeVariable<? extends Class<?>> typeParameter = typeParameters[i];

            if(names.containsKey(typeParameter.getName())) {
                types[i] = names.get(typeParameter.getName()).getAClass();
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

    public static TypeInfo<?>[] toReferences(Type[] param) {
        return TypeUtil.toReferences(param, new HashMap<>());
    }

    public static TypeInfo<?>[] toReferences(Type[] param, Map<String, TypeInfo<?>> names) {
        TypeInfo<?>[] typeInfos = new TypeInfo[param.length];

        for (int i = 0; i < param.length; i++) {
            typeInfos[i] = TypeUtil.toReference(param[i], names);
        }

        return typeInfos;
    }

    public static TypeInfo<?> toReference(Type param) {
        return TypeUtil.toReference(param, new HashMap<>());
    }

    public static TypeInfo<?> toReference(Type param, Map<String, TypeInfo<?>> names) {
        if (param instanceof ParameterizedType) {
            return TypeUtil.toReference((ParameterizedType) param, names);
        }

        if (param instanceof GenericArrayType) {

            GenericArrayType genericArrayType = (GenericArrayType) param;
            TypeInfo<?> typeInfo = TypeUtil.toReference(genericArrayType.getGenericComponentType(), names);

            String pr = param.toString();

            int start = pr.indexOf('<');
            int end = pr.lastIndexOf('>') + 1;

            String name = pr.substring(0, start);

            String arrays = pr.substring(end, pr.length()).replace("]", "");

            String fullName = (arrays + "L" + name) + ";";

            try {
                Reflection.changeFinalField(RClass.getRClass(typeInfo), "aClass", Class.forName(fullName));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return typeInfo;

        }

        if(param instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) param;

            if(names.containsKey(typeVariable.getName())) {
                return names.get(typeVariable.getName());
            }

        }

        return TypeInfo.aEnd(TypeUtil.from(param, names));
    }

    public static <T, E extends T> TypeInfo<?> lambdaTypes(Class<E> lambdaClass, Class<T> functionalInterfaceClass) {

        Map<String, Class<?>> types = new HashMap<>();

        TypeToolsMethods.fromConstantPool(lambdaClass, functionalInterfaceClass, types);

        TypeInfoBuilder<?> typeInfoBuilder = new TypeInfoBuilder<>().a(functionalInterfaceClass);

        TypeVariable<Class<T>>[] typeParameters = functionalInterfaceClass.getTypeParameters();

        for (TypeVariable<Class<T>> typeParameter : typeParameters) {
            String name = typeParameter.getName();
            if (types.containsKey(name)) {
                Class<?> aClass = types.get(name);
                typeInfoBuilder.of(aClass);
            }
        }

        return typeInfoBuilder.build();
    }

    public static TypeInfo<?> toReference(ParameterizedType param) {
        return TypeUtil.toReference(param, new HashMap<>());
    }

    public static TypeInfo<?> toReference(ParameterizedType param, Map<String, TypeInfo<?>> names) {
        TypeInfoBuilder<?> typeInfoBuilder = TypeInfo.a(TypeUtil.from(param.getRawType(), names));
        for (Type type : param.getActualTypeArguments()) {
            if (!(type instanceof ParameterizedType)) {

                if(type instanceof TypeVariable) {
                    TypeVariable typeVar = (TypeVariable) type;

                    if(names.containsKey(typeVar.getName())) {
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
                typeInfoBuilder.of(TypeUtil.toReference(parameterizedType, names));
            }
        }
        return typeInfoBuilder.build();
    }

    public static Class<?> from(Type t) {
        return TypeUtil.from(t, new HashMap<>());
    }

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

            if(names.containsKey(name)) {
                return names.get(name).getAClass();
            }

            return Object.class;
        }

        try {
            return Class.forName(t.getTypeName());
        } catch (Exception e) {
            return null; // To fix some problems
        }
    }


    public static ParameterizedType findParameterizedClass(Class<?> in, Class<?> parameterizedType) {
        Class<?> superClass = in.getSuperclass();

        if(superClass.equals(parameterizedType)) {
            if(in.getGenericSuperclass() instanceof ParameterizedType) {
               return (ParameterizedType) in.getGenericSuperclass();
            }
        }

        Class[] types = in.getInterfaces();
        Type[] genericInterfaces = in.getGenericInterfaces();

        for (int i = 0; i < types.length; i++) {
            if(types[i].equals(parameterizedType)) {
                if(genericInterfaces[i] instanceof ParameterizedType) {
                    return (ParameterizedType) genericInterfaces[i];
                }
            }
        }

        if(!superClass.equals(Object.class)) {
            ParameterizedType find = findParameterizedClass(superClass, parameterizedType);
            if(find != null) {
                return find;
            }
        }

        if(parameterizedType.isInterface()) {
            for (Class type : types) {
                ParameterizedType find = findParameterizedClass(type, parameterizedType);

                if(find != null) {
                    return find;
                }
            }
        }

        return null;
    }
}
