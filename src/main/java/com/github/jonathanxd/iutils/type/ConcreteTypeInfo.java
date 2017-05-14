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

/**
 * Creates a concrete type information (from a generic type variable).
 *
 * @param <T> Type.
 */
public abstract class ConcreteTypeInfo<T> extends TypeInfo<T> {

    private final TypeInfo<T> wrapped;
    private final boolean isUnique;

    @SuppressWarnings("unchecked")
    public ConcreteTypeInfo(boolean isUnique) {
        super();

        this.wrapped = (TypeInfo<T>) TypeUtil.toTypeInfo(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        this.isUnique = isUnique;
    }

    public ConcreteTypeInfo() {
        this(false);
    }

    @Override
    public TypeInfo[] getRelated() {
        return this.getWrapped().getRelated();
    }

    @Override
    protected TypeInfo[] fastGetRelated() {
        return this.getWrapped().fastGetRelated();
    }

    @Override
    public String getClassLiteral() {
        return this.getWrapped().getClassLiteral();
    }

    @Override
    public TypeInfo<?>[] getSubTypeInfos() {
        return this.getWrapped().getSubTypeInfos();
    }

    @Override
    protected TypeInfo<?>[] fastGetSubTypeInfos() {
        return this.getWrapped().fastGetSubTypeInfos();
    }

    @Override
    public boolean isUnique() {
        return this.isUnique;
    }

    protected TypeInfo<T> getWrapped() {
        return this.wrapped;
    }

}
