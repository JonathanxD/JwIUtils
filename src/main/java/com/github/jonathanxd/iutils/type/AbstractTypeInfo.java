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
package com.github.jonathanxd.iutils.type;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Abstract type info.
 *
 * @param <T> Type.
 * @deprecated This strategy creates two instances of {@link TypeInfo} (this, and of object to be
 * wrapped) and prevents the {@link AbstractTypeInfo} instance to be collected by the garbage
 * collector. Use {@link TypeParameterProvider#createTypeInfo()}.
 */
public abstract class AbstractTypeInfo<T> extends TypeInfo<T> {

    private final TypeInfo<T> wrapped;

    @SuppressWarnings("unchecked")
    public AbstractTypeInfo() {
        super();

        this.wrapped = (TypeInfo<T>)
                TypeUtil.toTypeInfo(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public String getClassLiteral() {
        return this.getWrapped().getClassLiteral();
    }

    @Override
    public List<TypeInfo<?>> getTypeParameters() {
        return this.getWrapped().getTypeParameters();
    }

    protected TypeInfo<T> getWrapped() {
        return this.wrapped;
    }
}
