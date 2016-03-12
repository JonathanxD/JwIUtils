/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.object.Reference;
import com.github.jonathanxd.iutils.object.ReferenceBuilder;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jonathan on 13/02/16.
 */
public class ReferenceData extends BaseData<Reference<?>> implements Cloneable {

    public static Reference<?> toReference(ParameterizedType param) {
        ReferenceBuilder referenceBuilder = Reference.a(Reflection.from(param.getRawType()));
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

        for (Reference<?> data : getDataSet()) {

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

        for (Reference<?> data : getDataSet()) {

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
            Reference<?> ref = toReference(parameterizedType);
            return getData(ref);
        } else {
            return this.getData((Class<? extends X>) parameter.getType());
        }
    }

    /**
     * Find a data in Set based on Class
     *
     * @param reference Reference of data
     * @return True if data exists in DataSet
     */
    public <T> boolean findData(Reference<T> reference) {
        return getData(reference).isPresent();
    }

    /**
     * Get data <br> Comparator: Parameter 1 = Class of Data in Set. Parameter 2 = Data Class
     * parameter <br> Expression: if(Comparator.compare(DataObject class, Data Class parameter)
     * equalsTo 0) return (Type Cast) DataObject
     *
     * @param reference Reference to class
     * @param <X>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(Reference<?> reference) {

        for (Reference<?> data : getDataSet()) {

            // Prevent ClassCannotCastException
            if (data.equals(reference)) {
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
