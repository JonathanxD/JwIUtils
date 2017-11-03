/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link TypeInfo} that have dynamic type parameters.
 *
 * @param <T> Type.
 */
public final class DynamicTypeInfo<T> extends TypeInfo<T> {

    private final List<TypeInfo<?>> typeParameters = new ArrayList<>();

    DynamicTypeInfo(Class<T> aClass, List<TypeInfo<?>> typeParameters) {
        super(aClass, typeParameters);

        this.typeParameters.addAll(typeParameters);
    }

    DynamicTypeInfo(String classLiteral, List<TypeInfo<?>> typeParameters) {
        super(classLiteral, typeParameters);

        this.typeParameters.addAll(typeParameters);
    }

    public void addRelated(TypeInfo<?> typeInfo) {
        this.typeParameters.add(typeInfo);
    }

    public TypeInfo<T> toTypeInfo() {
        if (this.isResolved())
            return new TypeInfo<>(this.getTypeClass(), this.getTypeParameters());

        return new TypeInfo<>(this.getClassLiteral(), this.getTypeParameters());
    }

    @Override
    public List<TypeInfo<?>> getTypeParameters() {
        return this.typeParameters;
    }
}
