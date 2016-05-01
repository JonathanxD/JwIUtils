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
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.object.GenericRepresentation;
import com.github.jonathanxd.iutils.object.ReferenceBuilder;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Created by jonathan on 13/02/16.
 */
public class ReferenceData extends BaseData<GenericRepresentation<?>> implements Cloneable {

    public static GenericRepresentation<?> toReference(ParameterizedType param) {
        ReferenceBuilder referenceBuilder = GenericRepresentation.a(Reflection.from(param.getRawType()));
        for (Type type : param.getActualTypeArguments()) {
            if (!(type instanceof ParameterizedType)) {
                referenceBuilder.of(Reflection.from(type));
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                referenceBuilder.of(toReference(parameterizedType));
            }
        }
        return referenceBuilder.build();
    }

    @SuppressWarnings("unchecked")
    public <X> Optional<X> getDataAssignable(Class<? extends X> dataClass) {

        for (GenericRepresentation<?> data : getDataSet()) {

            // Prevent ClassCannotCastException
            Object r;
            if ((r = data.get()).getClass().isAssignableFrom(dataClass)) {
                return Optional.of((X) r);
            }

        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(Class<? extends X> dataClass) {

        for (GenericRepresentation<?> data : getDataSet()) {

            // Prevent ClassCannotCastException
            Object r;
            if ((r = data.get()).getClass() == dataClass) {
                return Optional.of((X) r);
            }

        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(Parameter parameter) {

        AnnotatedType type = null;
        try {
            type = parameter.getAnnotatedType();
        } catch (Exception e) {
        }

        if (type != null && type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            GenericRepresentation<?> ref = toReference(parameterizedType);
            return getData(ref);
        } else {
            return this.getData((Class<? extends X>) parameter.getType());
        }
    }

    /**
     * Find a data in Set based on Class
     *
     * @param genericRepresentation GenericRepresentation of data
     * @return True if data exists in DataSet
     */
    public <T> boolean findData(GenericRepresentation<T> genericRepresentation) {
        return getData(genericRepresentation).isPresent();
    }

    /**
     * Get data <br> Comparator: Parameter 1 = Class of Data in Set. Parameter 2 = Data Class
     * parameter <br> Expression: if(Comparator.compare(DataObject class, Data Class parameter)
     * equalsTo 0) return (Type Cast) DataObject
     *
     * @param genericRepresentation GenericRepresentation to class
     * @param <X>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(GenericRepresentation<?> genericRepresentation) {

        for (GenericRepresentation<?> data : getDataSet()) {

            // Prevent ClassCannotCastException
            if (data.equals(genericRepresentation)) {
                return Optional.of((X) data.get());
            }

        }

        return Optional.empty();
    }

    public void migrateFrom(ReferenceData data) {
        this.getDataSet().addAll(data.getDataSet());
    }

    public void migrateTo(ReferenceData data) {
        data.getDataSet().addAll(this.getDataSet());
    }

    @Override
    public ReferenceData clone() {
        ReferenceData data = new ReferenceData();
        data.getDataSet().addAll(this.getDataSet());
        return data;
    }


}
