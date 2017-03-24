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
 * {@link TypeInfo} builder.
 *
 * @param <T> Type.
 */
public final class TypeInfoBuilder<T> {

    private String classLiteral = null;
    private Class<? extends T> type = null;
    private List<TypeInfoBuilder<?>> related = new ArrayList<>();
    private boolean isUnique = false;

    TypeInfoBuilder() {
    }

    /**
     * Creates a {@link TypeInfoBuilder} from {@code typeInfo}.
     *
     * @param typeInfo Source.
     * @param <T>      Type.
     * @return {@link TypeInfoBuilder} from {@code typeInfo}.
     */
    public static <T> TypeInfoBuilder<T> from(TypeInfo<T> typeInfo) {
        TypeInfoBuilder<T> typeInfoBuilder = new TypeInfoBuilder<>();

        typeInfoBuilder.classLiteral = typeInfo.getClassLiteral();

        if(typeInfo.isResolved())
            typeInfoBuilder.type = typeInfo.getTypeClass();

        for (TypeInfo<?> otherTypeInfo : typeInfo.getRelated()) {
            typeInfoBuilder.related.add(TypeInfoBuilder.from(otherTypeInfo));
        }

        return typeInfoBuilder;
    }

    /**
     * Creates a {@link TypeInfo} from {@code typeInfoBuilder}.
     *
     * @param typeInfoBuilder Builder.
     * @param <T>             Type.
     * @return {@link TypeInfo} from {@code typeInfoBuilder}.
     */
    public static <T> TypeInfo<T> build(TypeInfoBuilder<T> typeInfoBuilder) {

        DynamicTypeInfo<T> dynamicReference;

        if(typeInfoBuilder.type == null)
            dynamicReference = new DynamicTypeInfo<>(typeInfoBuilder.classLiteral, new TypeInfo[]{}, typeInfoBuilder.isUnique());
        else
            dynamicReference = new DynamicTypeInfo<>(typeInfoBuilder.type, new TypeInfo[]{}, typeInfoBuilder.isUnique());

        if (typeInfoBuilder.related.size() > 0) {

            List<TypeInfo<?>> typeInfoList = new ArrayList<>();

            for (TypeInfoBuilder<?> otherTypeInfoBuilder : typeInfoBuilder.related) {
                typeInfoList.add(TypeInfoBuilder.build(otherTypeInfoBuilder));
            }

            for (TypeInfo<?> typeInfo : typeInfoList) {
                dynamicReference.addRelated(typeInfo);
            }
        }

        return dynamicReference.toReference();
    }

    /**
     * @see TypeInfo#getTypeClass()
     */
    public Class<? extends T> getTypeClass() {

        if(this.type != null)
            return this.type;

        return TypeUtil.resolveClass(this.getClassLiteral());
    }

    /**
     * @see TypeInfo#getClassLiteral()
     */
    public String getClassLiteral() {
        return this.classLiteral;
    }

    /**
     * @see TypeInfo#getRelated()
     */
    public List<TypeInfoBuilder<?>> getRelated() {
        return this.related;
    }

    /**
     * @see TypeInfo#isUnique()
     */
    public boolean isUnique() {
        return this.isUnique;
    }

    /**
     * Marks {@link TypeInfoBuilder} to create a unique {@link TypeInfo} or not.
     *
     * @param unique Is unique.
     * @return {@code this}.
     */
    public TypeInfoBuilder<T> setUnique(boolean unique) {
        this.isUnique = unique;
        return this;
    }

    /**
     * Sets the type.
     *
     * @param aClass Type.
     * @return {@code this}.
     */
    @RequiredCall
    public TypeInfoBuilder<T> a(Class<? extends T> aClass) {
        this.classLiteral = aClass.getName();
        this.type = aClass;
        return this;
    }

    /**
     * Sets the type.
     *
     * @param classLiteral Type.
     * @return {@code this}.
     */
    @RequiredCall
    public TypeInfoBuilder<T> a(String classLiteral) {
        this.classLiteral = classLiteral;
        return this;
    }

    // Of

    /**
     * Adds related types.
     *
     * @param related Related types.
     * @return {@code this}.
     */
    @OptionalCall
    public TypeInfoBuilder<T> of(List<TypeInfo<?>> related) {

        for (TypeInfo<?> typeInfo : related) {
            this.related.add(from(typeInfo));
        }

        return this;
    }

    /**
     * Adds related types of the type {@link E}.
     *
     * @param related Related types.
     * @param <E>     Type.
     * @return {@code this}.
     */
    @OptionalCall
    public <E> TypeInfoBuilder<T> ofE(List<TypeInfo<E>> related) {

        for (TypeInfo<E> typeInfo : related) {
            this.related.add(from(typeInfo));
        }

        return this;
    }

    /**
     * Adds related types.
     *
     * @param related Related types.
     * @return {@code this}.
     */
    @OptionalCall
    public final TypeInfoBuilder<T> of(TypeInfo<?>... related) {
        this.of(Arrays.asList(related));
        return this;
    }

    /**
     * Adds related types.
     *
     * @param related Related types.
     * @return {@code this}.
     */
    @OptionalCall
    public final TypeInfoBuilder<T> ofArray(TypeInfo<?>[] related) {
        this.of(Arrays.asList(related));
        return this;
    }

    /**
     * Adds related types.
     *
     * @param builders Related types builders.
     * @return {@code this}.
     */
    @OptionalCall
    public TypeInfoBuilder<T> of(TypeInfoBuilder... builders) {

        for (TypeInfoBuilder builder : builders) {
            this.related.add(builder);
        }
        return this;
    }

    /**
     * Adds related types.
     *
     * @param classes Related types classes.
     * @return {@code this}.
     */
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

    /**
     * Adds related types.
     *
     * @param classes Related types classes literal.
     * @return {@code this}.
     */
    @OptionalCall
    public final <E> TypeInfoBuilder<T> of(String... classes) {

        List<TypeInfo<E>> typeInfos = new ArrayList<>();

        for (String classz : classes) {
            typeInfos.add(new TypeInfoBuilder<E>().a(classz).build());
        }

        ofE(typeInfos);

        return this;
    }

    /**
     * Builds {@link TypeInfo} from this {@link TypeInfoBuilder}.
     *
     * @return {@link TypeInfo} from this {@link TypeInfoBuilder}.
     */
    public TypeInfo<T> build() {
        return TypeInfoBuilder.build(this);
    }

    /**
     * Builds {@link TypeInfo} of generic type {@link U} from this {@link TypeInfoBuilder}.
     *
     * @return {@link TypeInfo} of generic type {@link U} from this {@link TypeInfoBuilder}.
     */
    @SuppressWarnings("unchecked")
    public <U> TypeInfo<U> buildGeneric() {
        return (TypeInfo<U>) this.build();
    }
}
