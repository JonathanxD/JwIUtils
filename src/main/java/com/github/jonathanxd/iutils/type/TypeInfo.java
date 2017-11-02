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

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * Accessed and modified via reflection in {@link AbstractTypeInfo} Class literal.
     */
    private final String classLiteral;

    /**
     * Contains information of type parameters.
     */
    private final List<TypeInfo<?>> typeParameters;

    /**
     * Cached class instance.
     */
    private Class<T> cachedAClass;

    /**
     * Cached type variables.
     */
    private List<TypeVariable<?>> typeVariableCache;

    /**
     * Sub types info
     */
    private List<TypeInfo<?>> subTypesInfo;

    protected TypeInfo() {
        this.classLiteral = null;
        this.typeParameters = Collections.emptyList();
    }

    protected TypeInfo(TypeInfo<T> typeInfo) {
        this.typeParameters = Collections.unmodifiableList(new ArrayList<>(typeInfo.getTypeParameters()));
        this.classLiteral = typeInfo.getClassLiteral();
    }

    @SuppressWarnings("unchecked")
    TypeInfo(Class<T> aClass, List<TypeInfo<?>> typeParameters) {
        this.classLiteral = TypeUtil.fixName(aClass.getName());
        this.cachedAClass = aClass;
        this.typeParameters = Collections.unmodifiableList(new ArrayList<>(typeParameters));
    }

    @SuppressWarnings("unchecked")
    TypeInfo(String classLiteral, List<TypeInfo<?>> typeParameters) {
        this.classLiteral = TypeUtil.fixName(classLiteral);
        this.typeParameters = Collections.unmodifiableList(new ArrayList<>(typeParameters));
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
     * Creates a builder from {@code this} {@link TypeInfo}.
     *
     * @return Builder from {@code this} {@link TypeInfo}.
     */
    public TypeInfoBuilder<? extends T> but() {
        if (this.isResolved())
            return TypeInfo.<T>builder().a(this.getTypeClass()).of(this.getTypeParameters());
        else
            return TypeInfo.<T>builder().a(this.getClassLiteral()).of(this.getTypeParameters());
    }

    /**
     * This method will load and cache all types, including types of {@link #typeParameters} type
     * info.
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
                this.cachedAClass = (Class<T>) classLoader.loadClass(classLiteral);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to resolve class '" + classLiteral + "' using class loader: '" + classLiteral + "'!");
            }
        }

        for (TypeInfo typeInfo : this.getTypeParameters()) {
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
    public Class<T> getTypeClass() {

        if (this.cachedAClass != null)
            return this.cachedAClass;
        else {
            this.cachedAClass = TypeUtil.resolveClass(this.getClassLiteral());
            return this.cachedAClass;
        }
    }

    /**
     * Gets {@link #getTypeClass() type class} {@link Class#getTypeParameters() type parameters}.
     *
     * @return {@link #getTypeClass() type class} {@link Class#getTypeParameters() type parameters}.
     */
    private List<TypeVariable<?>> getTypeClassTypeVariables() {
        if (this.typeVariableCache != null) {
            return this.typeVariableCache;
        } else {
            this.typeVariableCache =
                    Collections.unmodifiableList(new ArrayList<>(Arrays.asList(this.getTypeClass().getTypeParameters())));

            return this.typeVariableCache;
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
     * Gets type parameters type information.
     *
     * @return Type parameters information.
     */
    public List<TypeInfo<?>> getTypeParameters() {
        return this.typeParameters;
    }

    /**
     * Gets information of type parameter in position {@code pos}.
     *
     * Example, for {@code Map<String, Integer>}, {@code getTypeParameter(1)} returns {@code
     * Integer} type info.
     *
     * @param pos Position.
     * @return Information of type parameter in position {@code pos}.
     * @throws IllegalArgumentException  If {@code pos} is negative.
     * @throws IndexOutOfBoundsException If {@code pos} exceeds the amount of type parameters in
     *                                   {@link #getTypeClass() type class}.
     */
    public TypeInfo<?> getTypeParameter(int pos) throws IllegalArgumentException, IndexOutOfBoundsException {
        this.validate();
        this.validatePos(pos);

        return this.getTypeParameters().get(pos);
    }

    /**
     * Gets the type parameter information by {@code name} of type variable of {@link
     * #getTypeClass() type class}.
     *
     * Example, for {@code Map<String, Integer>}, which is declared as {@code Map<K, V>}, {@code
     * getTypeParameter("V")} return {@code Integer} type info.
     *
     * @param name Name of type variable of {@link #getTypeClass() type class}.
     * @return Type parameter information corresponding to type variable with specified {@code
     * name}.
     * @throws IllegalArgumentException If there is no type variable with specified name in {@link
     *                                  #getTypeClass() type class}.
     */
    public TypeInfo<?> getTypeParameter(String name) throws IllegalArgumentException {
        this.validate();
        this.validateName(name);

        List<TypeVariable<?>> typeClassTypeVariables = this.getTypeClassTypeVariables();

        for (int i = 0; i < typeClassTypeVariables.size(); i++) {
            TypeVariable<?> typeVariable = typeClassTypeVariables.get(i);

            if (typeVariable.getName().equals(name))
                return this.getTypeParameter(i);
        }

        throw new IllegalStateException();
    }

    /**
     * Gets information of sub types of {@code this} {@link TypeInfo}.
     *
     * @return Information of sub types of {@code this} {@link TypeInfo}.
     */
    public List<TypeInfo<?>> getSubTypeInfoList() {
        if (this.subTypesInfo == null)
            this.subTypesInfo = Collections.unmodifiableList(new ArrayList<>(TypeInfoUtil.createSubTypeInfos(this)));

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

        List<TypeInfo<?>> otherSubTypeInfos = info.getSubTypeInfoList();

        if (otherSubTypeInfos.size() == 1)
            return this.isAssignableFrom(otherSubTypeInfos.get(0));

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

    /**
     * Validates type info. This method checks if this {@link TypeInfo} specifies type parameters
     * information for all type parameters of {@link #getTypeClass()}.
     */
    private void validate() {
        Class<?> typeClass = this.getTypeClass();
        int typeClassSize = this.getTypeClassTypeVariables().size();
        int thisSpecSize = this.getTypeParameters().size();

        if (thisSpecSize != typeClassSize)
            throw new IllegalStateException("Mismatch type parameters." +
                    " Type class parameters: '" + typeClassSize + "'." +
                    " This info parameters: '" + thisSpecSize + "'." +
                    " Type class: '" + typeClass.getCanonicalName() + "'." +
                    " This info: '" + this + "'.");
    }

    /**
     * Validate access position of type variables.
     *
     * @param pos Position to validate.
     */
    private void validatePos(int pos) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (pos < 0)
            throw new IllegalArgumentException("Negative position provided: '" + pos + "'!");

        int max = this.getTypeClassTypeVariables().size();

        if (pos >= max)
            throw new IndexOutOfBoundsException("Position: '" + pos + "'. Max: '" + max + "'!");
    }

    /**
     * Validates name to access type variables.
     *
     * @param name Name to validate.
     */
    private void validateName(String name) throws IllegalArgumentException {
        List<String> typeClassTypeVariables =
                this.getTypeClassTypeVariables().stream()
                        .map(TypeVariable::getName)
                        .collect(Collectors.toList());

        if (!typeClassTypeVariables.contains(name)) {
            throw new IllegalArgumentException("Cannot find type variable with name: '" + name + "'" +
                    " in type variable list: '" + typeClassTypeVariables + "' of class" +
                    " '" + this.getTypeClass().getCanonicalName() + "'!");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClassLiteral(), this.getTypeParameters().hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TypeInfo))
            return false;

        TypeInfo other = (TypeInfo) obj;

        return this.compareTo(other) == 0;
    }


    @Override
    public int compareTo(TypeInfo compareTo) {
        Objects.requireNonNull(compareTo);

        if (this.getClassLiteral().equals(compareTo.getClassLiteral())) {

            if (this.getTypeParameters().equals(compareTo.getTypeParameters())) {
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
    @SuppressWarnings("unchecked")
    public int compareTypeAndRelatedTo(TypeInfo compareTo) {

        Objects.requireNonNull(compareTo);

        if (this.getTypeClass().isAssignableFrom(compareTo.getTypeClass())) {
            List<TypeInfo<?>> thisRelated = this.getTypeParameters();
            List<TypeInfo<?>> otherRelated = compareTo.getTypeParameters();

            if (thisRelated.size() != otherRelated.size())
                return -1;

            for (int x = 0; x < thisRelated.size(); ++x) {
                Class<?> mainRefClass = thisRelated.get(x).getTypeClass();
                Class<?> compareRefClass = otherRelated.get(x).getTypeClass();

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
