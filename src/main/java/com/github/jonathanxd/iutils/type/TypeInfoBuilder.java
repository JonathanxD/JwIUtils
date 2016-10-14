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
package com.github.jonathanxd.iutils.type;

import com.github.jonathanxd.iutils.optional.OptionalCall;
import com.github.jonathanxd.iutils.optional.RequiredCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jonathan on 13/02/16.
 */
public final class TypeInfoBuilder<T> {
    private Class<? extends T> aClass = null;
    private List<TypeInfoBuilder<?>> related = new ArrayList<>();
    //private Object hold = null;
    private boolean isUnique = false;

    TypeInfoBuilder() {
    }

    public static <T> TypeInfoBuilder<T> from(TypeInfo<T> typeInfo) {
        TypeInfoBuilder<T> typeInfoBuilder = new TypeInfoBuilder<>();
        typeInfoBuilder.a(typeInfo.getAClass());

        for (TypeInfo<?> otherTypeInfo : typeInfo.getRelated()) {
            typeInfoBuilder.related.add(from(otherTypeInfo));
        }

        return typeInfoBuilder;
    }

    public static <T> TypeInfo<T> to(TypeInfoBuilder<T> typeInfoBuilder) {

        DynamicTypeInfo<T> dynamicReference = new DynamicTypeInfo<>(typeInfoBuilder.aClass, new TypeInfo[]{}, typeInfoBuilder.isUnique());

        if (typeInfoBuilder.related.size() > 0) {

            List<TypeInfo<?>> typeInfoList = new ArrayList<>();

            for (TypeInfoBuilder<?> otherTypeInfoBuilder : typeInfoBuilder.related) {
                typeInfoList.add(to(otherTypeInfoBuilder));
            }

            for (TypeInfo<?> typeInfo : typeInfoList) {
                dynamicReference.addRelated(typeInfo);
            }
        }

        return dynamicReference.toReference();
    }

    public Class<? extends T> getaClass() {
        return aClass;
    }

    public List<TypeInfoBuilder<?>> getRelated() {
        return related;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public TypeInfoBuilder<T> setUnique(boolean unique) {
        this.isUnique = unique;
        return this;
    }

    @RequiredCall
    public TypeInfoBuilder<T> a(Class<? extends T> aClass) {
        this.aClass = aClass;
        return this;
    }

    // Of
    @OptionalCall
    public TypeInfoBuilder<T> of(List<TypeInfo<?>> related) {

        for (TypeInfo<?> typeInfo : related) {
            this.related.add(from(typeInfo));
        }

        return this;
    }

    @OptionalCall
    public <E> TypeInfoBuilder<T> ofE(List<TypeInfo<E>> related) {

        for (TypeInfo<E> typeInfo : related) {
            this.related.add(from(typeInfo));
        }

        return this;
    }

    @OptionalCall
    public final <E> TypeInfoBuilder<T> of(TypeInfo<?>... related) {
        this.of(Arrays.asList(related));
        return this;
    }

    @OptionalCall
    public final <E> TypeInfoBuilder<T> ofArray(TypeInfo<?>[] related) {
        this.of(Arrays.asList(related));
        return this;
    }

    @OptionalCall
    public TypeInfoBuilder<T> of(TypeInfoBuilder... builders) {

        for (TypeInfoBuilder builder : builders) {
            this.related.add(builder);
        }
        return this;
    }

    @SafeVarargs
    @OptionalCall
    public final <E> TypeInfoBuilder<T> of(Class<? extends E>... classes) {

        List<TypeInfo<E>> typeInfos = new ArrayList<>();

        for (Class<? extends E> classz : classes) {
            typeInfos.add(new TypeInfoBuilder<E>().a(classz).build());
        }

        ofE(typeInfos);

        return this;
    }

    // AND OF
    @OptionalCall
    public <E> TypeInfoBuilder<T> and(List<TypeInfo<E>> related) {
        andCheck();
        ofE(related);
        return this;
    }

    @SafeVarargs
    @OptionalCall
    public final <E> TypeInfoBuilder<T> and(TypeInfo<E>... related) {
        andCheck();
        of(related);
        return this;
    }

    @OptionalCall
    public TypeInfoBuilder<T> and(TypeInfoBuilder... builders) {
        andCheck();
        of(builders);
        return this;
    }

    @OptionalCall
    public TypeInfoBuilder<T> and(Class<?>... classes) {
        andCheck();
        of(classes);
        return this;
    }

    private void andCheck() {
        if (related.size() == 0)
            throw new IllegalStateException("'and' cannot be used here! Usage ex: referenceTo().a(Object.class).of(String.class).and(Class.class)");
    }

    public TypeInfo<T> build() {
        return to(this);
    }

    @SuppressWarnings("unchecked")
    public <U> TypeInfo<U> buildGeneric() {
        return (TypeInfo<U>) this.build();
    }
}
