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
package com.github.jonathanxd.iutils.description;

import com.github.jonathanxd.iutils.exceptions.RethrowException;

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 10/08/16.
 */
public class DescriptionUtil {

    /**
     * Convert binary class name to internal.
     *
     * @param binary Binary class name.
     * @return Internal class name
     */
    public static String binaryToInternal(String binary) {
        Objects.requireNonNull(binary, "Binary class name cannot be null");

        switch (binary) {
            case "int":
                return "I";
            case "void":
                return "V";
            case "boolean":
                return "Z";
            case "long":
                return "J";
            case "char":
                return "C";
            case "byte":
                return "B";
            case "short":
                return "S";
            case "float":
                return "F";
            case "double":
                return "D";
        }

        return "L" + binary + ";";
    }

    /**
     * Convert binary class names to internal.
     *
     * @param binaries Binary classes names.
     * @return Internal classes names.
     */
    public static String[] binariesToInternal(String[] binaries) {
        Objects.requireNonNull(binaries, "Binaries name cannot be null");

        return Arrays.stream(binaries).map(DescriptionUtil::binaryToInternal).toArray(String[]::new);
    }

    public static MethodType toMethodType(Description description, ClassLoader classLoader) {
        Objects.requireNonNull(description, "Description cannot be null");
        Objects.requireNonNull(classLoader, "Class loader cannot be null");

        Class<?> returnType = DescriptionUtil.resolveUnsafe(description.getReturnType(), classLoader);

        Class[] parameterTypes = DescriptionUtil.parametersToClass(description, classLoader);

        return MethodType.methodType(returnType, parameterTypes);
    }

    public static Class<?>[] parametersToClass(Description description, ClassLoader classLoader) {
        Objects.requireNonNull(description, "Description cannot be null");
        Objects.requireNonNull(classLoader, "Class loader cannot be null");

        return Arrays.stream(description.getParameterTypes())
                .map(s -> DescriptionUtil.resolveUnsafe(DescriptionUtil.binaryToInternal(s), classLoader))
                .toArray(Class[]::new);
    }

    public static Class<?> resolveUnsafe(String name, ClassLoader classLoader) {
        try {
            return DescriptionUtil.resolve(name, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RethrowException(e);
        }
    }

    public static Class<?>[] resolveUnsafe(String[] names, ClassLoader classLoader) {

        try {
            return DescriptionUtil.resolve(names, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RethrowException(e);
        }
    }


    public static String internalToName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");

        if (name.length() == 1) {
            switch (name.charAt(0)) {
                case 'V':
                    return "void";
                case 'Z':
                    return "boolean";
                case 'C':
                    return "char";
                case 'B':
                    return "byte";
                case 'S':
                    return "short";
                case 'I':
                    return "int";
                case 'F':
                    return "float";
                case 'J':
                    return "long";
                case 'D':
                    return "double";
            }
        }


        String typeName = name;

        if (name.startsWith("L") && name.endsWith(";")) {
            typeName = name.substring(1, name.length() - 1);
        }

        typeName = typeName.replace("/", ".");

        return typeName;
    }

    public static Class<?> resolve(String name, ClassLoader classLoader) throws ClassNotFoundException {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(classLoader, "Class loader cannot be null");

        if (name.length() == 1) {
            switch (name.charAt(0)) {
                case 'V':
                    return Void.TYPE;
                case 'Z':
                    return Boolean.TYPE;
                case 'C':
                    return Character.TYPE;
                case 'B':
                    return Byte.TYPE;
                case 'S':
                    return Short.TYPE;
                case 'I':
                    return Integer.TYPE;
                case 'F':
                    return Float.TYPE;
                case 'J':
                    return Long.TYPE;
                case 'D':
                    return Double.TYPE;
            }
        }


        String typeName = name;

        if (name.startsWith("L") && name.endsWith(";")) {
            typeName = name.substring(1, name.length() - 1);
        }

        typeName = typeName.replace("/", ".");

        return classLoader.loadClass(typeName);
    }

    public static Class<?>[] resolve(String[] names, ClassLoader classLoader) throws ClassNotFoundException {

        Objects.requireNonNull(classLoader, "ClassLoader cannot be null");

        if(names == null || names.length == 0)
            return new Class[0];

        Class<?>[] classes = new Class[names.length];

        for (int i = 0; i < classes.length; i++) {
            classes[i] = DescriptionUtil.resolve(names[i], classLoader);
        }

        return classes;
    }

    public static String toJavaSpec(Class<?> theClass) {
        Objects.requireNonNull(theClass, "Class cannot be null");

        return binaryToInternal(theClass.getName());
    }

    public static Description from(Method method) {
        Objects.requireNonNull(method, "Method cannot be null");

        String desc = toJavaSpec(method.getDeclaringClass())
                + ":"
                + method.getName()
                + Arrays.stream(method.getParameterTypes()).map(DescriptionUtil::toJavaSpec).collect(Collectors.joining("", "(", ")"))
                + toJavaSpec(method.getReturnType());

        return DescriptionUtil.parseDescription(desc);
    }

    public static Description parseDescription(String description) {
        Objects.requireNonNull(description, "Description cannot be null");

        return new Description(
                description,
                DescriptionUtil.getBinaryClassName(description),
                DescriptionUtil.getMethodName(description),
                DescriptionUtil.getParameterTypes(description),
                DescriptionUtil.getReturnType(description));
    }

    public static String getBinaryClassName(String desc) {
        Objects.requireNonNull(desc, "Description cannot be null");

        if (desc.length() == 1) {
            switch (desc.charAt(0)) {
                case 'V':
                    return "void";
                case 'Z':
                    return "boolean";
                case 'C':
                    return "char";
                case 'B':
                    return "byte";
                case 'S':
                    return "short";
                case 'I':
                    return "int";
                case 'F':
                    return "float";
                case 'J':
                    return "long";
                case 'D':
                    return "double";
            }

            throw new IllegalArgumentException("Illegal description: " + desc);
        }

        return DescriptionUtil.parseBinaryClassName(desc.substring(0, desc.indexOf(';') + 1));
    }

    public static String getMethodName(String desc) {
        Objects.requireNonNull(desc, "Description cannot be null");

        return desc.substring(desc.indexOf(':') + 1, desc.indexOf('('));
    }

    public static String[] getParameterTypes(String desc) {
        Objects.requireNonNull(desc, "Description cannot be null");

        String parameters = desc.substring(desc.indexOf('(') + 1, desc.indexOf(')'));

        return DescriptionUtil.parametersToString(parameters);
    }

    public static String getReturnType(String desc) {
        Objects.requireNonNull(desc, "Description cannot be null");

        return DescriptionUtil.parseBinaryClassName(desc.substring(desc.indexOf(')') + 1));
    }

    private static String[] parametersToString(String parameters) {
        if(parameters == null)
            return new String[0];

        // Parameter list
        List<String> parameterNames = new ArrayList<>();

        // String characters
        char[] chars = parameters.toCharArray();

        // Builder of class names
        StringBuilder stringBuilder = new StringBuilder();


        for (char aChar : chars) {
            // If char is 'L' and string builder is empty
            if (aChar == 'L' && stringBuilder.length() == 0) {
                // Append 'L' to stringBuilder
                stringBuilder.append(aChar);
            } else if (aChar == ';') { // If char is ';'
                // If builder is empty
                if (stringBuilder.length() == 0)
                    throw new RuntimeException("Invalid parameter description: '" + parameters + "'!");

                // Append character ';'
                stringBuilder.append(';');

                // Add builder text to list
                parameterNames.add(stringBuilder.toString());

                // Set content of builder to ""
                stringBuilder.setLength(0);
            } else { // If character isn't 'L' or ';'
                // If builder is not empty
                if (stringBuilder.length() != 0) {
                    // Append character to builder
                    stringBuilder.append(aChar);
                } else { // If builder is empty
                    // Add character to parameterTypes
                    parameterNames.add(String.valueOf(aChar));
                }
            }
        }

        // If Builder is empty
        if (stringBuilder.length() != 0)
            throw new RuntimeException("Invalid description. ';' expected. Description = '" + parameters + "'!");

        // Return array of parameterTypes
        return parameterNames.stream().map(DescriptionUtil::parseBinaryClassName).toArray(String[]::new);
    }

    private static void checkValidDescription(String desc) {
        if (desc == null
                || !desc.startsWith("L")
                || desc.indexOf(';') == -1
                || desc.indexOf(':') == -1
                || desc.indexOf('(') == -1
                || desc.indexOf(')') == -1) {
            throw new IllegalArgumentException("Invalid description: '" + desc + "'. See " + Description.class.getCanonicalName() + " javadoc.");
        }
    }

    /**
     * Parse binary class name from constant class info (L[BINARY_CLASS_NAME];)
     *
     * @param name Constant class info
     * @return Binary Class Name
     */
    public static String parseBinaryClassName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");

        if (name.startsWith("L") && name.endsWith(";"))
            return name.substring(1, name.indexOf(';'));

        return name;
    }

    public static String binaryToName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");

        if (name.startsWith("L") && name.endsWith(";"))
            return name.substring(1, name.indexOf(';')).replace('/', '.');

        return name;
    }

}
