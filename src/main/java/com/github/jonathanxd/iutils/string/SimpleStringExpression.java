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
package com.github.jonathanxd.iutils.string;

import com.github.jonathanxd.iutils.condition.Conditions;
import com.github.jonathanxd.iutils.exception.MaxRecursiveParseException;
import com.github.jonathanxd.iutils.exception.RethrowException;
import com.github.jonathanxd.iutils.type.Primitive;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String expression evaluator.
 */
public class SimpleStringExpression {

    public static final String METHOD_INVOKE_SYMBOL = ".";
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();
    private static final Pattern ARGUMENT_EXTRACTOR = Pattern.compile("\\b[^()]+\\((.*)\\)(;)?$");
    private static final Pattern ARGUMENT_MATCHER = Pattern.compile("([^,]+\\(.+?\\))|([^,]+)");

    /**
     * Evaluates the expression string {@code string} and returns the resulting object.
     *
     * @param string    String expression.
     * @param variables Provided variables.
     * @return Evaluated Object.
     */
    public static Object evaluateExpression(String string, Map<String, Object> variables) {
        return SimpleStringExpression.evaluateExpression(string, variables, -1, new ArrayList<>());
    }

    private static Object evaluateExpression(String string, Map<String, Object> variables, int resultN, List<Object> results) {
        if (resultN >= 256)
            throw new MaxRecursiveParseException("Exceeded limit of 256 local variables.");

        int indexOfMethod = string.indexOf(METHOD_INVOKE_SYMBOL);

        String varName = string;

        if (resultN < -1) {
            throw new IllegalArgumentException("Illegal resultNumber: resultN: '" + resultN + "' ('resultN' need to be >= -1)!");
        }

        if (indexOfMethod != -1) {

            varName = string.substring(0, indexOfMethod).trim();

            Object varValue;

            if (resultN == -1) {
                varValue = Objects.requireNonNull(variables.get(varName), "Cannot find variable '" + varName + "'");
            } else if (resultN > -1) {
                varValue = Objects.requireNonNull(results.get(resultN), "Null local value at index '" + resultN + "'!");
            } else {
                throw new IllegalArgumentException("Illegal resultNumber: resultN: '" + resultN + "' ('resultN' need to be >= -1)!");
            }

            final Class<?> aClass;

            if (varValue instanceof Class) {
                aClass = (Class<?>) varValue;
                varValue = null;
            } else {
                aClass = varValue.getClass();
            }

            int range = parseMethodRange(string, 1);

            if (range != -1) {

                int methodNameStart = indexOfMethod + METHOD_INVOKE_SYMBOL.length();

                String argumentsStr = string.substring(0, range + 1);


                String[] arguments = parseArguments(argumentsStr);

                return SimpleStringExpression.executeMethodOfObject(varValue, aClass, string, arguments, methodNameStart, range, variables, resultN, results);
            } else {
                int pos = indexOfMethod + METHOD_INVOKE_SYMBOL.length();
                int end = string.indexOf(METHOD_INVOKE_SYMBOL, pos);

                end = end != -1 ? end : string.length();

                String fieldName = string.substring(pos, end).trim();

                try {
                    Field field = aClass.getField(fieldName);

                    Object v = field.get(varValue);

                    String rangeRes = string.substring(pos + fieldName.length());

                    if (rangeRes.length() == 0 || rangeRes.equals(";"))
                        return v;

                    if (rangeRes.startsWith(".")) {
                        rangeRes = rangeRes.substring(1);
                    }


                    List<Object> results2 = new ArrayList<>(results);

                    results2.add(v);

                    return SimpleStringExpression.evaluateExpression(rangeRes, variables, resultN + 1, results2);


                } catch (NoSuchFieldException | IllegalAccessException e) {
                    RuntimeException ex = new RuntimeException("Cannot access field '" + fieldName + "' of class '" + aClass.getCanonicalName() + "'", e);

                    ex.setStackTrace(new StackTraceElement[]{ex.getStackTrace()[0]});

                    throw ex;
                }
            }

        } else {
            Object parseValue;
            if ((parseValue = parseValue(varName)) != null) {
                return parseValue;
            } else {
                if (resultN == -1) {
                    if (variables.containsKey(varName)) {
                        return variables.get(varName);
                    } else {
                        throw new RuntimeException("Cannot find variable '" + varName + "'");
                    }
                } else {

                    Object value = Objects.requireNonNull(results.get(resultN), "Null local value at index '" + resultN + "'!");

                    int range = SimpleStringExpression.parseMethodRange(string, 0);

                    if (range != -1) {
                        int methodNameStart = indexOfMethod + METHOD_INVOKE_SYMBOL.length();

                        String argumentsStr = string.substring(0, range + 1);

                        String[] arguments = parseArguments(argumentsStr);

                        return SimpleStringExpression.executeMethodOfObject(value, value.getClass(), string, arguments, methodNameStart, range, variables, resultN, results);
                    } else {

                        try {
                            Field field = value.getClass().getField(varName);

                            field.setAccessible(true);

                            return field.get(value);

                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw new RethrowException(e);
                        }
                    }
                }
            }
        }
    }

    private static Object executeMethodOfObject(Object varValue, Class<?> aClass, String methodString, String[] arguments, int methodNameStart, int range, Map<String, Object> variables, int resultN, List<Object> results) {
        String methodName = methodString.substring(methodNameStart, methodString.indexOf('(', methodNameStart)).trim();

        Object[] filledArguments = fillArguments(arguments, variables);

        MethodHandle method = SimpleStringExpression.findMethod(aClass, methodName, varValue, filledArguments);

        if (varValue != null)
            method = method.bindTo(varValue);

        Object resultL;

        try {
            resultL = method.invokeWithArguments(filledArguments);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

        String afterClose = methodString.substring(range + 1);

        if (afterClose.length() == 0 || afterClose.equals(";"))
            return resultL;

        if (afterClose.startsWith(".")) {
            afterClose = afterClose.substring(1);
        }

        List<Object> results2 = new ArrayList<>(results);

        results2.add(resultL);

        return SimpleStringExpression.evaluateExpression(afterClose, variables, resultN + 1, results2);
    }

    private static Object parseValue(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ignored) {
        }

        try {
            return Long.parseLong(s);
        } catch (NumberFormatException ignored) {
        }

        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ignored) {
        }

        if (s.length() > 1 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }

        return null;
    }

    private static MethodHandle findMethod(Class<?> clazz, String name, Object variable, Object[] parameters) {

        try {
            Method reflectGetMethod = reflectGetMethod(variable == null, clazz, name, parameters);

            Conditions.checkNotNull(reflectGetMethod, "Cannot find method '" + name + "' with parameters: '" + Arrays.toString(parameters) + "'!");

            return LOOKUP.unreflect(reflectGetMethod);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method reflectGetMethod(boolean isStatic, Class<?> clazz, String methodName, Object[] parameters) {
        Class<?>[] parameterTypes = Arrays.stream(parameters).map(Object::getClass).toArray(Class<?>[]::new);

        for (Method method : clazz.getMethods()) {

            if (isStatic == Modifier.isStatic(method.getModifiers())) {
                if (method.getName().equals(methodName)) {
                    if (checkArguments(parameterTypes, method.getParameterTypes())) {
                        return method;
                    }
                }
            }
        }

        return null;
    }

    private static boolean checkArguments(Class<?>[] current, Class<?>[] expected) {
        if (current.length != expected.length)
            return false;

        for (int i = 0; i < current.length; i++) {
            Class<?> currentClass = current[i];
            Class<?> expectedClass = expected[i];

            if (Primitive.typeEquals(currentClass, expectedClass)) {
                if (currentClass.isPrimitive() != expectedClass.isPrimitive()) {
                    currentClass = Primitive.normalize(currentClass, expectedClass);
                }
            }

            if (!expectedClass.isAssignableFrom(currentClass)) {
                return false;
            }
        }

        return true;
    }

    private static Object[] fillArguments(String[] arguments, Map<String, Object> variables) {

        Object[] objects = new Object[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            objects[i] = evaluateExpression(arguments[i], variables);
        }

        return objects;
    }

    private static String[] parseArguments(String string) {

        List<String> args = new ArrayList<>();

        Matcher matcher = ARGUMENT_EXTRACTOR.matcher(string);

        if (matcher.matches()) {
            String arguments = matcher.group(1);

            Matcher argumentMatcher = ARGUMENT_MATCHER.matcher(arguments);

            while (argumentMatcher.find()) {
                String arg = argumentMatcher.group(2);
                args.add(arg);

            }
        }

        return args.stream().toArray(String[]::new);
    }

    private static int parseMethodRange(String string, int ignore) {
        int openTags = 0;
        int closeTags = 0;

        boolean lastIsEscape = false;

        char[] chars = string.toCharArray();

        for (int x = 0; x < chars.length; ++x) {

            char c = chars[x];

            if (!lastIsEscape) {
                if (c == '\\') {
                    lastIsEscape = true;
                } else if (c == '"') {
                    if (openTags <= closeTags)
                        ++openTags;
                    else
                        ++closeTags;
                } else if (c == ')' && openTags == closeTags) {
                    return x;
                } else if (c == '.' && openTags == closeTags) {
                    if (ignore == 0)
                        return -1;
                    else
                        --ignore;
                }

            } else {
                lastIsEscape = false;
            }


        }

        return -1;
    }

}
