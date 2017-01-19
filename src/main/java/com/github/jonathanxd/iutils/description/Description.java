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

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * Description format for classes: [CLASS_NAME]
 *
 * Description format for fields: [CLASS_NAME]:[FIELD_NAME]:[TYPE]
 *
 * Description format for methods: [CLASS_NAME]:[METHOD_NAME]([PARAMETERS])[RETURN_TYPE]
 *
 * [CLASS_NAME] = L[<a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2.1">BINARY_CLASS_NAME</a>];
 *
 * [FIELD_NAME] = PLAIN_STRING
 *
 * [METHOD_NAME] = PLAIN_STRING
 *
 * [PARAMETERS] = [CLASS_NAME]...
 *
 * [TYPE] = [CLASS_NAME]
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
    private final String elementName;

    /**
     * Method parameter types binary class name
     */
    private final String[] parameterTypes;

    /**
     * Method return type binary name
     */
    private final String type;

    /**
     * Element type
     */
    private final ElementType elementType;

    /**
     * Plain description.
     *
     * @param description     Description
     * @param binaryClassName Binary name of method declaring class
     * @param elementName     Method name
     * @param parameterTypes  Method parameter types binary name
     * @param type      Return type binary name
     * @param elementType     Element type.
     */
    public Description(String description, String binaryClassName, String elementName, String[] parameterTypes, String type, ElementType elementType) {
        this.description = description == null ? create(binaryClassName, elementName, parameterTypes, type, elementType) : description;
        this.binaryClassName = binaryClassName;
        this.elementName = elementName;
        this.parameterTypes = parameterTypes;
        this.type = type;
        this.elementType = elementType;
    }

    /**
     * Plain description of a method.
     *
     * @param description     Description
     * @param binaryClassName Binary name of method declaring class
     * @param elementName     Method name
     * @param parameterTypes  Method parameter types binary name
     * @param type      Return type binary name
     */
    @Deprecated
    public Description(String description, String binaryClassName, String elementName, String[] parameterTypes, String type) {
        this(description, binaryClassName, elementName, parameterTypes, type, ElementType.METHOD);
    }

    /**
     * Plain description
     *
     * @param binaryClassName Binary name of method declaring class
     * @param elementName     Method name
     * @param parameterTypes  Method parameter types binary name
     * @param type      Return type binary name
     */
    public Description(String binaryClassName, String elementName, String[] parameterTypes, String type, ElementType elementType) {
        this(null, binaryClassName, elementName, parameterTypes, type, elementType);
    }

    /**
     * Plain description
     *
     * @param binaryClassName Binary name of method declaring class
     * @param elementName     Method name
     * @param parameterTypes  Method parameter types binary name
     * @param type      Return type binary name
     */
    @Deprecated
    public Description(String binaryClassName, String elementName, String[] parameterTypes, String type) {
        this(null, binaryClassName, elementName, parameterTypes, type);
    }

    /**
     * Create description string from specified elements.
     *
     * @param binaryClassName Binary name of declaring class of method
     * @param elementName     Element name
     * @param parameterTypes  Parameter types
     * @param returnType      Return Type
     * @return Description
     */
    private static String create(String binaryClassName, String elementName, String[] parameterTypes, String returnType, ElementType elementType) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(binaryClassName);

        if(elementType == ElementType.CLASS)
            return stringBuilder.toString();


        stringBuilder.append(':');

        stringBuilder.append(elementName);

        if(elementType == ElementType.FIELD) {
            stringBuilder.append(':');
            stringBuilder.append(returnType);
            return stringBuilder.toString();
        }

        stringBuilder.append('(');

        stringBuilder.append(Arrays.stream(parameterTypes).collect(Collectors.joining("")));

        stringBuilder.append(')');

        stringBuilder.append(returnType);

        return stringBuilder.toString();
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
    @Deprecated
    public String getMethodName() {
        return this.elementName;
    }

    /**
     * Gets the element name.
     *
     * @return Element name.
     */
    public String getElementName() {
        return this.elementName;
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
    @Deprecated
    public String getReturnType() {
        return this.type;
    }

    /**
     * Gets binary class of element type.
     *
     * @return Binary class name of element type.
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2.1">Binary
     * Class and Interface Names: jvms 4.2.1</a>
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the element name.
     *
     * @return Element name.
     */
    public ElementType getElementType() {
        return this.elementType;
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
                    && this.getElementName().equals(other.getElementName())
                    && Arrays.equals(this.getParameterTypes(), other.getParameterTypes())
                    && this.getType().equals(other.getType())
                    && this.getElementType().equals(other.getElementType());

        }

        return super.equals(obj);
    }
}
