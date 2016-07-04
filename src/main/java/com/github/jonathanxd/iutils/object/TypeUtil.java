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

    public static <S, E extends S> GenericRepresentation<?> resolve(Class<E> classWithTypeVariable, Class<S> subClass) {
        Type genericSuperclass = classWithTypeVariable.getGenericSuperclass();
        Class<?> superClass = classWithTypeVariable.getSuperclass();

        GenericRepresentation<?> from = from(genericSuperclass, superClass, subClass);

        if (from == null) {
            Type[] genericInterfaces = classWithTypeVariable.getGenericInterfaces();
            Class<?>[] interfacesClasses = classWithTypeVariable.getInterfaces();

            for (int i = 0; i < interfacesClasses.length; i++) {
                Type genericInterface = genericInterfaces[i];
                Class<?> interfaceClass = interfacesClasses[i];

                from = from(genericInterface, interfaceClass, subClass);
                if (from != null)
                    break;
            }
        }

        if (from != null) {
            return from;
        }

        return null;
    }

    private static GenericRepresentation<?> from(Type type, Class<?> actual, Class<?> expected) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            if (expected.isAssignableFrom(actual)) {
                return toReference(parameterizedType);
            }

        } else if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length > 0) {
                return toReference(bounds[0]);
            }
        }

        return null;
    }

    public static GenericRepresentation<?>[] genericRepOfVariableTypes(Class<?> classWithTypeVariable) {

        return toReferences(getTypeVariableTypes(classWithTypeVariable));
    }

    public static Type[] getTypeVariableTypes(Class<?> classWithTypeVariable) {

        TypeVariable<? extends Class<?>>[] typeParameters = classWithTypeVariable.getTypeParameters();

        if (typeParameters.length == 0) {
            throw new IllegalStateException("No Type Variables");
        }

        Type[] types = new Type[typeParameters.length];

        for (int i = 0; i < typeParameters.length; i++) {
            Type[] bounds = typeParameters[i].getBounds();

            if (bounds.length > 0) {
                types[i] = bounds[0];
            } else {
                types[i] = Object.class;
            }
        }

        return types;
    }

    public static GenericRepresentation<?>[] toReferences(Type[] param) {
        GenericRepresentation<?>[] genericRepresentations = new GenericRepresentation[param.length];

        for (int i = 0; i < param.length; i++) {
            genericRepresentations[i] = toReference(param[i]);
        }

        return genericRepresentations;
    }

    public static GenericRepresentation<?> toReference(Type param) {
        if (param instanceof ParameterizedType) {
            return toReference((ParameterizedType) param);
        }

        if (param instanceof GenericArrayType) {

            GenericArrayType genericArrayType = (GenericArrayType) param;
            GenericRepresentation<?> genericRepresentation = toReference(genericArrayType.getGenericComponentType());

            String pr = param.toString();

            int start = pr.indexOf('<');
            int end = pr.lastIndexOf('>') + 1;

            String name = pr.substring(0, start);

            String arrays = pr.substring(end, pr.length()).replace("]", "");

            String fullName = (arrays + "L" + name) + ";";

            try {
                Reflection.changeFinalField(RClass.getRClass(genericRepresentation), "aClass", Class.forName(fullName));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return genericRepresentation;

        }

        return GenericRepresentation.aEnd(from(param));
    }

    public static <T, E extends T> GenericRepresentation<?> lambdaTypes(Class<E> lambdaClass, Class<T> functionalInterfaceClass) {

        Map<String, Class<?>> types = new HashMap<>();

        TypeToolsMethods.fromConstantPool(lambdaClass, functionalInterfaceClass, types);

        RepresentationBuilder<?> representationBuilder = new RepresentationBuilder<>().a(functionalInterfaceClass);

        TypeVariable<Class<T>>[] typeParameters = functionalInterfaceClass.getTypeParameters();

        for (TypeVariable<Class<T>> typeParameter : typeParameters) {
            String name = typeParameter.getName();
            if (types.containsKey(name)) {
                Class<?> aClass = types.get(name);
                representationBuilder.of(aClass);
            }
        }

        return representationBuilder.build();
    }

    public static GenericRepresentation<?> toReference(ParameterizedType param) {
        RepresentationBuilder representationBuilder = GenericRepresentation.a(from(param.getRawType()));
        for (Type type : param.getActualTypeArguments()) {
            if (!(type instanceof ParameterizedType)) {

                Class<?> from = from(type);

                if (from != null) {
                    representationBuilder.of(from);
                } else {
                    break;
                }
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                representationBuilder.of(toReference(parameterizedType));
            }
        }
        return representationBuilder.build();
    }

    public static Class<?> from(Type t) {
        if (t instanceof Class) {
            return (Class<?>) t;
        }

        if (t instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) t;
            Type[] upperBounds = wildcardType.getUpperBounds();

            if (upperBounds.length > 0) {
                return from(upperBounds[0]);
            } else {
                return Object.class;
            }
        }

        if (t instanceof TypeVariable) {
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
