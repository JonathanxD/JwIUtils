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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Objects;

/**
 * Created by jonathan on 16/04/16.
 */

/**
 * Provides a Type for {@link AbstractTypeInfo}s
 */
public interface TypeProvider {

    default Type[] getTypes() {

        Class<?> superclass = getClass().getSuperclass();

        Type genericClass = null;

        if(superclass.equals(Object.class) || !(getClass().getGenericSuperclass() instanceof ParameterizedType)) {
            for(Type interface_ : getClass().getGenericInterfaces()) {
                if(interface_ instanceof ParameterizedType) {
                    genericClass = interface_;
                }
            }
        } else {
            genericClass = getClass().getGenericSuperclass();
        }

        if(genericClass == null) {
            throw new IllegalStateException("Cannot find generic super type");
        }


        return getTypes(genericClass);
    }

    default Type[] getClassTypes() {

        return TypeUtil.getTypeVariableTypes(this.getClass());

    }

    default Type[] getTypes(Class<?> classToDetermine) {

        ParameterizedType parameterizedClass = TypeUtil.findParameterizedClass(this.getClass(), classToDetermine);

        return parameterizedClass != null ? parameterizedClass.getActualTypeArguments() : null;

    }

    default Type[] getTypes(Type type) {

        if(type == null)
            return null;

        if (!(type instanceof ParameterizedType)) {

            TypeVariable<? extends Class<? extends TypeProvider>>[] typeParameters = getClass().getTypeParameters();

            if(typeParameters.length == 0) {
                throw new IllegalStateException("Not a generic class");
            }

            Type[] types = new Type[typeParameters.length];

            for (int i = 0; i < typeParameters.length; i++) {
                Type[] bounds = typeParameters[i].getBounds();

                if(bounds.length > 0) {
                    types[i] = bounds[0];
                } else {
                    types[i] = Object.class;
                }
            }



            return types;
        }


        return ((ParameterizedType) type).getActualTypeArguments();

    }

    @SuppressWarnings("unchecked")
    default TypeInfo[] getReferences() {
        return TypeUtil.toReferences(Objects.requireNonNull(getTypes(), "Null Type!"));
    }

    default TypeInfo[] getClassReferences() {
        return TypeUtil.toReferences(Objects.requireNonNull(getClassTypes(), "Null Type!"));
    }
}
