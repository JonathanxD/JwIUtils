/**
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.jonathanxd.iutils.type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * Enhanced type resolution utilities.
 *
 * @author Jonathan Halterman
 */
public class TypeToolsMethods {
    private static final Method GET_POOL_METHOD;
    private static final Class<?> CONSTANT_POOL_CLASS;

    static {

        Method tmp;

        try {
            tmp = Class.class.getDeclaredMethod("getConstantPool");
            tmp.setAccessible(true);
        } catch (NoSuchMethodException e) {
            tmp = null;
        }

        GET_POOL_METHOD = tmp;

        Class<?> tmp2;

        try {
            tmp2 = Class.forName("sun.reflect.ConstantPool");
        } catch (ClassNotFoundException e) {
            tmp2 = null;
        }
        CONSTANT_POOL_CLASS = tmp2;
    }

    private static String[] getMethodRefInfo(Object constantPool) {
        String[] returnValue = null;

        int size = -1;

        try {
            size = (int) CONSTANT_POOL_CLASS.getMethod("getSize").invoke(constantPool);
        } catch (Throwable ignored) {
        }
        for (int i = size - 1; i >= 0; i--) {
            try {
                //constantPool.getMemberRefInfoAt(i)
                String[] methodRefInfo = (String[]) CONSTANT_POOL_CLASS.getMethod("getMemberRefInfoAt", Integer.TYPE).invoke(constantPool, i);
                String methodName = methodRefInfo[1];

                if (methodName.equals("<init>")) {
                    continue;
                }

                returnValue = methodRefInfo;
                if (!methodName.equals("valueOf")) {
                    break;
                }
            } catch (IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) {
            }
        }


        return returnValue;
    }

    /**
     * Adaptation
     *
     * by: JonathanScripter
     */
    public static void fromConstantPool(Class<?> lambdaClass, Class<?> functionalInterfaceClass, Map<String, Class<?>> types) {
        if (GET_POOL_METHOD != null) {
            for (Method m : functionalInterfaceClass.getDeclaredMethods()) {
                if (!m.isDefault() && !Modifier.isStatic(m.getModifiers()) && m.getDeclaringClass() != Object.class) {

                    Object constantPool;

                    try {
                        constantPool = GET_POOL_METHOD.invoke(lambdaClass);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }

                    String[] methodRefInfo = getMethodRefInfo(constantPool);

                    if (methodRefInfo == null)
                        continue;

                    ASMType returnType = ASMType.getReturnType(methodRefInfo[2]);
                    ASMType[] parameterTypes = ASMType.getArgumentTypes(methodRefInfo[2]);

                    Type genericReturnType = m.getGenericReturnType();
                    Type[] genericParameters = m.getGenericParameterTypes();

                    if (genericReturnType instanceof TypeVariable) {
                        try {
                            Class<?> returnTypeClass = lambdaClass.getClassLoader().loadClass(returnType.getClassName());
                            types.put(((TypeVariable) genericReturnType).getName(), returnTypeClass);
                        } catch (ClassNotFoundException e) {
                        }

                    }

                    for (int i = 0; i < genericParameters.length; i++) {
                        Type param = genericParameters[i];

                        if (param instanceof TypeVariable) {
                            ASMType asmType = parameterTypes[i];

                            try {
                                Class<?> paramTypeClass = lambdaClass.getClassLoader().loadClass(asmType.getClassName());
                                types.put(((TypeVariable) param).getName(), paramTypeClass);
                            } catch (ClassNotFoundException e) {
                            }
                        }
                    }
                }
            }
        }
    }
}
