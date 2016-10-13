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

/**
 * Created by jonathan on 10/08/16.
 */

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Description format: [CLASS_NAME]:[METHOD_NAME]([PARAMETERS])[RETURN_TYPE]
 *
 * [CLASS_NAME] = L[<a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2.1">BINARY_CLASS_NAME</a>];
 *
 * [METHOD_NAME] = PLAIN_STRING
 *
 * [PARAMETERS] = [CLASS_NAME]...
 *
 * [RETURN_TYPE] = [CLASS_NAME]
 */
public class Description {
    /**
     * Plain Description
     */
    private final String description;

    /**
     * Binary class name
     */
    private final String binaryClassName;

    /**
     * Method name
     */
    private final String methodName;

    /**
     * Method parameter types binary class name
     */
    private final String[] parameterTypes;

    /**
     * Method return type binary name
     */
    private final String returnType;

    /**
     * Plain description
     *
     * @param description     Description
     * @param binaryClassName Binary name of method declaring class
     * @param methodName      Method name
     * @param parameterTypes  Method parameter types binary name
     * @param returnType      Return type binary name
     */
    public Description(String description, String binaryClassName, String methodName, String[] parameterTypes, String returnType) {
        this.description = description == null ? create(binaryClassName, methodName, parameterTypes, returnType) : description;
        this.binaryClassName = binaryClassName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /**
     * Plain description
     *
     * @param binaryClassName Binary name of method declaring class
     * @param methodName      Method name
     * @param parameterTypes  Method parameter types binary name
     * @param returnType      Return type binary name
     */
    public Description(String binaryClassName, String methodName, String[] parameterTypes, String returnType) {
        this(null, binaryClassName, methodName, parameterTypes, returnType);
    }

    /**
     * Create description string from specified elements.
     *
     * @param binaryClassName Binary name of declaring class of method
     * @param methodName      Method name
     * @param parameterTypes  Parameter types
     * @param returnType      Return Type
     * @return Description
     */
    private static String create(String binaryClassName, String methodName, String[] parameterTypes, String returnType) {
        return binaryClassName + ":" + methodName + "(" + Arrays.stream(parameterTypes).collect(Collectors.joining("")) + ")" + returnType;
    }

    /**
     * Gets binary class name.
     *
     * @return Class Name.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2.1">Binary
     * Class and Interface Names: jvms 4.2.1</a>
     */
    public String getBinaryClassName() {
        return this.binaryClassName;
    }

    /**
     * Gets the method name
     *
     * @return Method name
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * Gets binary class name of method parameters.
     *
     * @return Binary class name of method parameters.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2.1">Binary
     * Class and Interface Names: jvms 4.2.1</a>
     */
    public String[] getParameterTypes() {
        return this.parameterTypes.clone();
    }

    /**
     * Gets binary class of method return type.
     *
     * @return Binary class name of method return type.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2.1">Binary
     * Class and Interface Names: jvms 4.2.1</a>
     */
    public String getReturnType() {
        return this.returnType;
    }

    /**
     * Gets plain description
     *
     * @return Gets plain description
     */
    public String getPlainDescription() {
        return this.description;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Description) {
            Description other = (Description) obj;

            return this.getBinaryClassName().equals(other.getBinaryClassName())
                    && this.getMethodName().equals(other.getMethodName())
                    && Arrays.equals(this.getParameterTypes(), other.getParameterTypes())
                    && this.getReturnType().equals(other.getReturnType());

        }

        return super.equals(obj);
    }
}
