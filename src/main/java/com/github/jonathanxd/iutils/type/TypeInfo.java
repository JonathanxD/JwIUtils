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

import com.github.jonathanxd.iutils.exception.TypeResolutionException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/**
 * Holds information about generic types.
 *
 * {@link TypeInfo} does not hold information about wildcard types, and this behavior is
 * intentional.
 *
 * TypeInfo is invariant.
 *
 * @param <T> Type.
 */
@SuppressWarnings("Duplicates")
public class TypeInfo<T> implements Comparable<TypeInfo> {

    /**
     * Accessed and modified via reflection in {@link AbstractTypeInfo}
     * Class literal.
     */
    private final String classLiteral;

    /**
     * Accessed and modified via reflection in {@link AbstractTypeInfo} & {@link DynamicTypeInfo}
     */
    private final TypeInfo[] related;

    /**
     * Marking it as unique will make this to use default implementation of {@link #hashCode()} and
     * {@link #equals(Object)} from {@link Object}
     */
    private final boolean isUnique;

    /**
     * Cached class instance.
     */
    private Class<? extends T> cachedAClass;

    /**
     * Sub types info
     */
    private TypeInfo<?>[] subTypesInfo;

    protected TypeInfo() {
        this.classLiteral = null;
        this.related = null;
        this.isUnique = false;
    }

    protected TypeInfo(TypeInfo<T> typeInfo) {
        this.related = typeInfo.getRelated();
        this.classLiteral = typeInfo.getClassLiteral();
        this.isUnique = typeInfo.isUnique();
    }

    TypeInfo(Class<? extends T> aClass, TypeInfo[] related, boolean isUnique) {
        this.classLiteral = TypeUtil.fixName(aClass.getName());
        this.cachedAClass = aClass;
        this.related = related != null ? related : new TypeInfo[0];
        this.isUnique = isUnique;
    }

    TypeInfo(String classLiteral, TypeInfo[] related, boolean isUnique) {
        this.classLiteral = TypeUtil.fixName(classLiteral);
        this.related = related != null ? related : new TypeInfo[0];
        this.isUnique = isUnique;
    }

    /**
     * Creates a {@link TypeInfoBuilder}.
     *
     * @param <T> Type.
     * @return {@link TypeInfoBuilder}.
     */
    public static <T> TypeInfoBuilder<T> builder() {
        return new TypeInfoBuilder<>();
    }

    /**
     * Creates a {@link TypeInfoBuilder} of a unique {@link TypeInfo}.
     *
     * @param <T> Type.
     * @return {@link TypeInfoBuilder}.
     */
    public static <T> TypeInfoBuilder<T> builderOfUnique() {
        return new TypeInfoBuilder<T>().setUnique(true);
    }

    /**
     * Creates a {@link TypeInfoBuilder} of a {@link TypeInfo} of a {@code aClass}.
     *
     * @param <T> Type.
     * @return {@link TypeInfoBuilder}.
     */
    public static <T> TypeInfoBuilder<T> builderOf(Class<T> aClass) {
        return TypeInfo.<T>builder().a(aClass);
    }

    /**
     * Creates a {@link TypeInfoBuilder} of a {@link TypeInfo} of a {@code classLiteral}.
     *
     * @param <T> Type.
     * @return {@link TypeInfoBuilder}.
     */
    public static <T> TypeInfoBuilder<T> builderOf(String classLiteral) {
        return TypeInfo.<T>builder().a(classLiteral);
    }

    /**
     * Creates a {@link TypeInfoBuilder} of a unique {@link TypeInfo} of a {@code aClass}.
     *
     * @param <T> Type.
     * @return {@link TypeInfoBuilder}.
     */
    public static <T> TypeInfoBuilder<T> builderOfUnique(Class<T> aClass) {
        return TypeInfo.<T>builder().a(aClass).setUnique(true);
    }

    /**
     * Creates a {@link TypeInfoBuilder} of a unique {@link TypeInfo} of a {@code classLiteral}.
     *
     * @param <T> Type.
     * @return {@link TypeInfoBuilder}.
     */
    public static <T> TypeInfoBuilder<T> builderOfUnique(String classLiteral) {
        return TypeInfo.<T>builder().a(classLiteral).setUnique(true);
    }


    /**
     * Creates a {@link TypeInfo} of a {@code aClass}.
     *
     * @param aClass Type.
     * @param <T>    Type.
     * @return {@link TypeInfo} of a {@code aClass}.
     */
    public static <T> TypeInfo<T> of(Class<T> aClass) {
        return TypeInfo.<T>builder().a(aClass).build();
    }

    /**
     * Creates a {@link TypeInfo} of a {@code classLiteral}.
     *
     * @param classLiteral Class Literal.
     * @param <T>          Type.
     * @return {@link TypeInfo} of a {@code classLiteral}.
     */
    public static <T> TypeInfo<T> of(String classLiteral) {
        return TypeInfo.<T>builder().a(classLiteral).build();
    }

    /**
     * Creates a unique {@link TypeInfo} of a {@code aClass}.
     *
     * @param aClass Type.
     * @param <T>    Type.
     * @return {@link TypeInfo} of a {@code aClass}.
     */
    public static <T> TypeInfo<T> ofUnique(Class<T> aClass) {
        return TypeInfo.<T>builder().a(aClass).setUnique(true).build();
    }

    /**
     * Creates a unique {@link TypeInfo} of a {@code classLiteral}.
     *
     * @param classLiteral Class Literal.
     * @param <T>          Type.
     * @return {@link TypeInfo} of a {@code classLiteral}.
     */
    public static <T> TypeInfo<T> ofUnique(String classLiteral) {
        return TypeInfo.<T>builder().a(classLiteral).setUnique(true).build();
    }

    /**
     * Returns a unique instance of {@code this} {@link TypeInfo}.
     *
     * @return Unique instance of {@code this} {@link TypeInfo}.
     */
    @SuppressWarnings("unchecked")
    public TypeInfo<T> asUnique() {
        return this.isUnique() ? this : (TypeInfo<T>) this.but().setUnique(true).build();
    }

    /**
     * Creates a builder from {@code this} {@link TypeInfo}.
     *
     * @return Builder from {@code this} {@link TypeInfo}.
     */
    public TypeInfoBuilder<? extends T> but() {
        return TypeInfo.<T>builder().a(this.getClassLiteral()).ofArray(this.fastGetRelated());
    }

    /**
     * This method will load and cache all types, including types of {@link #related} type info.
     *
     * @param function Function that resolves the class loader of the class by the {@link
     *                 #getClassLiteral() literal name}.
     */
    @SuppressWarnings("unchecked")
    void loadTypes(Function<String, ClassLoader> function) {

        if (this.cachedAClass == null) {
            String classLiteral = this.getClassLiteral();
            ClassLoader classLoader = function.apply(classLiteral);
            try {
                this.cachedAClass = (Class<? extends T>) classLoader.loadClass(classLiteral);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to resolve class '" + classLiteral + "' using class loader: '" + classLiteral + "'!");
            }
        }

        for (TypeInfo typeInfo : this.fastGetRelated()) {
            typeInfo.loadTypes(function);
        }

    }

    /**
     * Returns true if the type is already resolved.
     *
     * @return True if the type is already resolved.
     */
    public boolean isResolved() {
        return this.cachedAClass != null;
    }

    /**
     * Returns true if current type can be resolved.
     *
     * @return True if current type can be resolved.
     */
    public boolean canResolve() {
        try {
            TypeUtil.resolveClass(this.getClassLiteral());
            return true;
        } catch (TypeResolutionException e) {
            return false;
        }
    }

    /**
     * Gets the raw class that this type is representing.
     *
     * @return Raw class that this type is representing.
     */
    public Class<? extends T> getTypeClass() {

        if (this.cachedAClass != null)
            return this.cachedAClass;
        else {
            this.cachedAClass = TypeUtil.resolveClass(this.getClassLiteral());
            return this.cachedAClass;
        }
    }

    /**
     * Gets the class literal of this {@link TypeInfo}.
     *
     * @return Class literal of this {@link TypeInfo}.
     */
    public String getClassLiteral() {
        return this.classLiteral;
    }

    /**
     * Returns whether {@code this} {@link TypeInfo} is unique or not.
     *
     * @return Whether {@code this} {@link TypeInfo} is unique or not.
     */
    public boolean isUnique() {
        return this.isUnique;
    }

    /**
     * Gets related type information.
     *
     * @return Related type information.
     */
    public TypeInfo[] getRelated() {
        return this.related.clone();
    }

    /**
     * Gets related type information without array cloning.
     *
     * @return Related type information.
     */
    protected TypeInfo[] fastGetRelated() {
        return this.related;
    }

    /**
     * Gets information of sub types of {@code this} {@link TypeInfo}.
     *
     * @return Information of sub types of {@code this} {@link TypeInfo}.
     */
    public TypeInfo<?>[] getSubTypeInfos() {
        return this.fastGetSubTypeInfos().clone();
    }

    /**
     * Gets information of sub types of {@code this} {@link TypeInfo} without array cloling.
     *
     * @return Information of sub types of {@code this} {@link TypeInfo}.
     */
    protected TypeInfo<?>[] fastGetSubTypeInfos() {

        if (this.subTypesInfo == null)
            this.subTypesInfo = TypeInfoUtil.createSubTypeInfos(this);

        return this.subTypesInfo;
    }

    /**
     * Returns true if this {@link TypeInfo} is assignable from {@code info}.
     *
     * @return True if this {@link TypeInfo} is assignable from {@code info}.
     * @see Class#isAssignableFrom(Class)
     */
    public boolean isAssignableFrom(TypeInfo<?> info) {
        if (this.compareTypeAndRelatedTo(info) == 0)
            return true;

        TypeInfo<?>[] otherSubTypeInfos = info.fastGetSubTypeInfos();

        if (otherSubTypeInfos.length == 1)
            return this.isAssignableFrom(otherSubTypeInfos[0]);

        for (TypeInfo<?> otherSubTypeInfo : otherSubTypeInfos) {
            if (this.isAssignableFrom(otherSubTypeInfo)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Cast this {@link TypeInfo} type to another {@link TypeInfo}.
     *
     * This method doesn't effectively cast the types, it only let the Type System to treat this
     * {@link TypeInfo} as a {@link TypeInfo} of {@link U}.
     *
     * @param <U> New Type.
     * @return Casted {@code this}.
     */
    @SuppressWarnings("unchecked")
    public <U> TypeInfo<U> cast() {
        return (TypeInfo<U>) this;
    }

    /**
     * Returns a simple string representation of this {@link TypeInfo}.
     *
     * This string may or may not be consistent, available types (cached types) will be represented
     * by the {@link Class#getSimpleName() class simple name}, non-available types will be
     * represented by the {@link #getClassLiteral() class literal}.
     *
     * @return Simple string representation of this {@link TypeInfo}.
     */
    @Override
    public String toString() {
        return TypeInfoUtil.toString(this, Class::getSimpleName);
    }

    /**
     * Returns a consistent generic string representation of this {@link TypeInfo}.
     *
     * @return Consistent generic string representation of this {@link TypeInfo}.
     */
    public String toFullString() {
        return TypeInfoUtil.toFullString(this);
    }

    @Override
    public int hashCode() {

        if (this.isUnique())
            return super.hashCode();

        return Objects.hash(this.getClassLiteral(), Arrays.deepHashCode(this.fastGetRelated()));
    }

    @Override
    public boolean equals(Object obj) {

        if (this.isUnique())
            return super.equals(obj);

        if (!(obj instanceof TypeInfo))
            return false;

        TypeInfo other = (TypeInfo) obj;

        return this.compareTo(other) == 0;
    }


    @Override
    public int compareTo(TypeInfo compareTo) {
        Objects.requireNonNull(compareTo);

        if (this.getClassLiteral().equals(compareTo.getClassLiteral())) {

            if (Arrays.deepEquals(this.fastGetRelated(), compareTo.fastGetRelated())) {
                return 0;
            }

            return 1;
        }

        return -1;
    }

    /**
     * Restrictively compare current {@code type} and {@code related types} to {@code type} and
     * {@code related types} of another {@link TypeInfo}.
     *
     * This method only works for types that are cached or available from the {@link TypeInfo}
     * {@link ClassLoader class loader} context.
     *
     * If types are not available from the class loader, call the {@link #loadTypes(Function)}
     * method to load them.
     *
     * @param compareTo Element to compare
     * @return 0 if comparison succeed, positive or negative number if not.
     */
    public int compareTypeAndRelatedTo(TypeInfo compareTo) {

        Objects.requireNonNull(compareTo);

        if (this.getTypeClass().isAssignableFrom(compareTo.getTypeClass())) {
            TypeInfo[] thisRelated = this.fastGetRelated();
            TypeInfo[] otherRelated = compareTo.fastGetRelated();

            if (thisRelated.length != otherRelated.length)
                return -1;

            for (int x = 0; x < thisRelated.length; ++x) {
                Class<?> mainRefClass = thisRelated[x].getTypeClass();
                Class<?> compareRefClass = otherRelated[x].getTypeClass();

                if (!mainRefClass.isAssignableFrom(compareRefClass)) {

                    if (compareRefClass.isAssignableFrom(mainRefClass)) {
                        return 1;
                    }

                    return -1;
                }
            }

            return 0;
        }

        return -1;
    }

    /**
     * Same as {@link #compareTo(TypeInfo)}
     */
    public static class ExactlyComparator implements Comparator<TypeInfo<?>> {

        @Override
        public int compare(TypeInfo<?> o1, TypeInfo<?> o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Compare if {@link TypeInfo typeInfo1} is assignable to {@link TypeInfo typeInfo2}.
     */
    public static class AssignableComparator implements Comparator<TypeInfo<?>> {

        @Override
        public int compare(TypeInfo<?> o1, TypeInfo<?> o2) {
            return o1.isAssignableFrom(o2) ? 0 : -1;
        }
    }

}
