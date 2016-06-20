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
package com.github.jonathanxd.iutils.string;

import com.github.jonathanxd.iutils.arrays.JwArray;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jonathan on 27/05/16.
 */
public class SimpleStringExpression {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();
    public static final String METHOD_INVOKE_SYMBOL = ".";

    private static final Pattern ARGUMENT_EXTRACTOR = Pattern.compile("\\b[^()]+\\((.*)\\)(;)?$");
    private static final Pattern ARGUMENT_MATCHER = Pattern.compile("([^,]+\\(.+?\\))|([^,]+)");

    public static Object executeExpression(String string, Map<String, Object> variables) {

        int indexOfMethod = string.indexOf(METHOD_INVOKE_SYMBOL);

        String varName = string;


        if (indexOfMethod != -1) {

            varName = string.substring(0, indexOfMethod).trim();

            Object varValue = Objects.requireNonNull(variables.get(varName), "Cannot find variable '" + varName + "'");

            final Class<?> aClass;

            if (varValue instanceof Class) {
                aClass = (Class<?>) varValue;
                varValue = null;
            } else {
                aClass = varValue.getClass();
            }

            if(string.endsWith(")") || string.endsWith(");")) {
                String methodName = string.substring(indexOfMethod + METHOD_INVOKE_SYMBOL.length(), string.indexOf('(')).trim();
                String[] arguments = parseArguments(string);
                Object[] filledArguments = fillArguments(arguments, variables);

                MethodHandle method = findMethod(aClass, methodName, varValue, filledArguments);

                if(varValue != null)
                    method = method.bindTo(varValue);

                try {
                    return method.invokeWithArguments(filledArguments);
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            } else {
                String fieldName = string.substring(indexOfMethod + METHOD_INVOKE_SYMBOL.length(), string.length()).trim();

                try {
                    Field field = aClass.getField(fieldName);

                    return field.get(varValue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    RuntimeException ex = new RuntimeException("Cannot access field '"+fieldName+"' of class '"+aClass.getCanonicalName()+"'", e);

                    ex.setStackTrace(new StackTraceElement[]{ex.getStackTrace()[0]});

                    throw ex;
                }


            }


        } else {
            if (variables.containsKey(varName)) {
                return variables.get(varName);
            } else {
                throw new RuntimeException("Cannot find variable '" + varName + "'");
            }
        }

    }



    private static MethodHandle findMethod(Class<?> clazz, String name, Object variable, Object[] parameters) {

        Class<?>[] classes = java.util.Arrays.stream(parameters).map(Object::getClass).toArray(Class<?>[]::new);

        MethodType methodType = MethodType.methodType(reflectGetReturnType(clazz, name, classes), classes);

        try {
            if (variable == null) {
                return LOOKUP.findStatic(clazz, name, methodType);
            } else {
                return LOOKUP.findVirtual(clazz, name, methodType);
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> reflectGetReturnType(Class<?> clazz, String methodName, Class<?>[] argTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, argTypes).getReturnType();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object[] fillArguments(String[] arguments, Map<String, Object> variables) {

        Object[] objects = new Object[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            objects[i] = executeExpression(arguments[i], variables);
        }

        return objects;
    }

    private static String[] parseArguments(String string) {

        JwArray<String> args = new JwArray<>();

        Matcher matcher = ARGUMENT_EXTRACTOR.matcher(string);

        if (matcher.matches()) {
            String arguments = matcher.group(1);

            Matcher argumentMatcher = ARGUMENT_MATCHER.matcher(arguments);

            while (argumentMatcher.find()) {
                String arg = argumentMatcher.group(2);
                args.add(arg);

            }
        }

        return args.toGenericArray(String[].class);
    }

}
