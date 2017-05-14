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

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link TypeInfo} that have dyamic related elements.
 *
 * @param <T> Type.
 */
public final class DynamicTypeInfo<T> extends TypeInfo<T> {

    private final List<TypeInfo<?>> related = new ArrayList<>();

    DynamicTypeInfo(Class<? extends T> aClass, TypeInfo[] related, boolean isUnique) {
        super(aClass, related, isUnique);

        for (TypeInfo typeInfo : related) {
            this.related.add(typeInfo);
        }
    }

    DynamicTypeInfo(String classLiteral, TypeInfo[] related, boolean isUnique) {
        super(classLiteral, related, isUnique);

        for (TypeInfo typeInfo : related) {
            this.related.add(typeInfo);
        }
    }

    public void addRelated(TypeInfo<?> typeInfo) {
        this.related.add(typeInfo);
    }

    public TypeInfo<T> toReference() {
        if(this.isResolved())
            return new TypeInfo<>(this.getTypeClass(), this.getRelated(), this.isUnique());

        return new TypeInfo<>(this.getClassLiteral(), this.getRelated(), this.isUnique());
    }

    @Override
    public TypeInfo[] getRelated() {
        return this.related.toArray(new TypeInfo[this.related.size()]);
    }

    @Override
    protected TypeInfo[] fastGetRelated() {
        return this.getRelated();
    }
}
