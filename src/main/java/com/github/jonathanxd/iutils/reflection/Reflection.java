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
package com.github.jonathanxd.iutils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Reflection {

    public static Object callDeclaredMethod(RClass rclass, String methodName, Object[] args, Class<?>... arguments) throws Exception {
        Class<?> classRef = rclass.getClassRef();
        Object obj = rclass.getObjectRef();

        Method m = classRef.getDeclaredMethod(methodName, arguments);

        if (!m.isAccessible()) {
            m.setAccessible(!m.isAccessible());
        }

        if (args == null) {
            return m.invoke(obj != null ? obj : null);
        } else {
            return m.invoke(obj != null ? obj : null, args);
        }
    }

    public static Object callNonDeclaredMethod(RClass rclass, String methodName, Object[] args, Class<?>... arguments) throws Exception {
        Class<?> classRef = rclass.getClassRef();
        Object obj = rclass.getObjectRef();

        Method m = classRef.getMethod(methodName, arguments);

        if (!m.isAccessible()) {
            m.setAccessible(!m.isAccessible());
        }

        if (args == null) {
            return m.invoke(obj != null ? obj : null);
        } else {
            return m.invoke(obj != null ? obj : null, args);
        }
    }

    public static void changeFinalField(RClass rclass, String fieldName, Object newValue) throws Exception {
        Class<?> classRef = rclass.getClassRef();
        Object obj = rclass.getObjectRef();
        Field field;
        try {
            field = classRef.getField(fieldName);
        } catch (Exception e) {
            field = classRef.getDeclaredField(fieldName);
        }

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        if (Modifier.isFinal(field.getModifiers())) {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }

        field.set(obj, newValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T constructEmpty(Class<? super T> clazz) throws ReflectiveOperationException {
        try {
            return (T) clazz.newInstance(); //No loops for default constructor
        } catch (Exception e) {
        }
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Modifier.isPublic(constructor.getModifiers())) {
                if (constructor.getParameterCount() == 0) {
                    return (T) constructor.newInstance();
                }
            }
        }
        return null;
    }

    public static boolean isOnClassInit(Class<?> classI) {
        StackTraceElement[] elem = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : elem) {
            if (e.getClassName().equals(classI.getName())) {
                if (e.getMethodName().equals("<init>")) {
                    return true;
                }
            }
        }

        return false;

    }

    public static StackTraceElement getCallInformations(Class<?> classI) {
        StackTraceElement[] elem = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : elem) {
            if (e.getClassName().equals(classI.getName())) {
                return e;
            }
        }

        return null;

    }

    public static boolean methodExists(MethodSpecification method, Class<?> clazz) {
        return findMethod(method, clazz) != null;

    }

    public static Method findDeclaredMethods(MethodSpecification method, Class<?> clazz) {
        return findMethodsArray(method, clazz.getDeclaredMethods());
    }

    public static Method findMethod(MethodSpecification method, Class<?> clazz) {
        return findMethodsArray(method, clazz.getMethods());
    }

    private static Method findMethodsArray(MethodSpecification method, Method[] methods) {
        for (Method m : methods) {
            if (method.match(m)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Try to translate via reflection using parse* method
     *
     * @param <T> Type
     * @return Translated value
     */
    public static <T> T tryTranslate(Class<T> tClass, Object value) {
        return discovery(tClass, value, tClass.getDeclaredMethods());
    }

    @SuppressWarnings("unchecked")
    private static <T> T discovery(Class<T> tClass, Object value, Method[] methods) {
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                if (method.getName().startsWith("parse")
                        || method.getName().startsWith("valueOf")) {
                    for (Class<?> type : method.getParameterTypes()) {
                        if (type.isAssignableFrom(value.getClass())) {
                            try {
                                Object invokeResult = method.invoke(null, value);
                                if (invokeResult != null) {
                                    System.out.println(invokeResult);
                                    return (T) invokeResult;
                                }
                            } catch (IllegalAccessException | InvocationTargetException ignored) {
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T tryClone(T objectToClone) {
        Class<?> classToClone = objectToClone.getClass();
        try {
            Method method = classToClone.getMethod("clone");
            if (Modifier.isPublic(method.getModifiers())) {
                return (T) method.invoke(objectToClone);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }

        return objectToClone;
    }

    @SuppressWarnings("unchecked")
    public static <T> T findStaticField(Class<?> classToFind, String fieldName) {
        try {
            Field field = classToFind.getDeclaredField(fieldName);
            return (T) field.get(null);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Recursive get annotation
     *
     * @param aClass          Class with annotations (analyze subclasses and interfaces)
     * @param annotationClass Annotation
     * @param <T>             Type of Annotation
     * @return Annotation Array
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T[] getAnnotation(Class<?> aClass, Class<T> annotationClass) {
        List<T> list = new ArrayList<>();

        Class<?> superClass = aClass.getSuperclass();
        if (superClass != null) {

            T[] annotations = getAnnotation(superClass, annotationClass);

            if (annotations.length > 0) {
                addAll(list, annotations);
            }
        }

        Class<?>[] interfaces = aClass.getInterfaces();

        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> sub : aClass.getInterfaces()) {
                T[] annotations = getAnnotation(sub, annotationClass);

                if (annotations.length > 0) {
                    addAll(list, annotations);
                }
            }
        }


        addAll(list, aClass.getDeclaredAnnotationsByType(annotationClass));

        T[] t = (T[]) Array.newInstance(annotationClass, list.size());

        return list.toArray(t);
    }

    private static <T extends Annotation> void addAll(List<T> list, T[] array) {
        if (array == null || array.length <= 0)
            return;

        for (T t : array) {
            if (!list.contains(t))
                list.add(t);
        }
    }

    public static Collection<Field> fieldCollection(Object object, boolean semiRecursive, boolean statics, boolean transients) {
        Class<?> clazz = object.getClass();

        Set<Field> fields = new HashSet<>();

        fields.addAll(toCollection(clazz.getDeclaredFields(), statics, transients));

        if (semiRecursive)
            fields.addAll(toCollection(clazz.getFields(), statics, transients));


        return fields;
    }

    public static Collection<Field> toCollection(Field[] fields, boolean statics, boolean transients) {
        Set<Field> fieldSet = new HashSet<>();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) && !statics) {
                continue;
            }
            if (Modifier.isTransient(field.getModifiers()) && !transients) {
                continue;
            }

            fieldSet.add(field);
        }

        return fieldSet;
    }

    public static Object create(String fullClassName, Object... parameters) {
        return create(fullClassName, false, parameters);
    }

    public static Object create(String fullClassName, boolean force, Object... parameters) {
        try {
            Class<?> clazz = Class.forName(fullClassName);

            if (parameters.length == 0) {
                Constructor constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            }

            for (Constructor constructor : clazz.getDeclaredConstructors()) {
                if (constructor.getParameterCount() != parameters.length) continue;
                if (!force && constructor.isAccessible()) continue;

                boolean ok = true;

                for (int x = 0; x < parameters.length; ++x) {
                    Object parameter = parameters[x];
                    Class<?> parameterType = parameter.getClass();
                    Class<?> constructorExpectedParameter = constructor.getParameterTypes()[x];

                    if (constructorExpectedParameter.isAssignableFrom(parameterType)) {
                        ok = false;
                    }
                }
                if (ok) {
                    if (force)
                        constructor.setAccessible(true);
                    return constructor.newInstance(parameters);
                }
            }

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Cannot determine the constructor!");
    }

    public static Method getMethod(Class<?> clazz, MethodSpecification specification) {
        try {

            Method m;
            if ((m = process(specification, clazz.getDeclaredMethods())) != null) {
                return m;
            }
            return process(specification, clazz.getMethods());
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getMethodByParam(Class<?> clazz, MethodSpecification specification, Object... params) {
        try {

            Method m;
            if ((m = process(specification, clazz.getDeclaredMethods(), params)) != null) {
                return m;
            }
            return process(specification, clazz.getMethods(), params);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private static Method process(MethodSpecification specification, Method[] methods, Object... params) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        for (Method method : methods) {
            if (specification.match(method)) {
                if(params == null) {
                    return method;
                }else{
                    if(paramCheck(method, params)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }

    private static boolean paramCheck(Executable executable, Object... parameters) {
        boolean ok = true;

        for (int x = 0; x < parameters.length; ++x) {
            Object parameter = parameters[x];
            Class<?> parameterType = parameter.getClass();
            Class<?> expectedParameter = executable.getParameterTypes()[x];

            if (!expectedParameter.isAssignableFrom(parameterType)) {
                ok = false;
            }
        }

        return ok;
    }

    public static Object invoke(Class<?> clazz, Object instance, MethodSpecification mSpec, boolean force, boolean ignoreNull, Object... parameters) {
        Object inst = invoke(instance, mSpec, force, clazz.getDeclaredMethods(), parameters);

        if (inst != null || ignoreNull)
            return inst;

        inst = invoke(instance, mSpec, force, clazz.getMethods(), parameters);

        return Objects.requireNonNull(inst, "Failed to get D:");
    }

    private static Object invoke(Object instance, MethodSpecification mSpec, boolean force, Method[] methods, Object... parameters) {

        try {

            for (Method method : methods) {
                if(mSpec != null && !mSpec.match(method)) continue;
                if (method.getParameterCount() != parameters.length) continue;
                if (!force && method.isAccessible()) continue;

                boolean ok = paramCheck(method, parameters);

                if (ok) {
                    if (force)
                        method.setAccessible(true);
                    return method.invoke(instance, parameters);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Cannot determine the method");
    }

    public static Class<?> from(Type t) {
        try {
            return Class.forName(t.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
