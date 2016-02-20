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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.jonathanxd.iutils.object.Reference;

/**
 * Created by jonathan on 13/02/16.
 */
public class ReferenceData implements Cloneable {

    private final Set<Reference<?>> dataSet = new HashSet<>();

    public static Object construct(ReferenceData extraData, Class<?> dataClass) {

        List<String> errorMessages = new ArrayList<>();

        List<Object> parameterList = new ArrayList<>();

        Constructor<?> validConstructor = null;

        for (Constructor<?> constructor : dataClass.getDeclaredConstructors()) {

            boolean fail = false;

            for (Class<?> parameterType : constructor.getParameterTypes()) {


                Optional<Object> objOpt = extraData.getData(parameterType);

                if (!objOpt.isPresent()) {
                    objOpt = extraData.getDataAssignable(parameterType);
                }

                if (!objOpt.isPresent()) {
                    errorMessages.add(String.format("Cannot determine instance of %s !", parameterType));
                    fail = true;
                } else {
                    Object object = objOpt.get();
                    if (parameterList.contains(object)) {
                        errorMessages.add(String.format("Argument %s already required!", parameterType));
                        fail = true;
                    } else {
                        parameterList.add(object);
                    }


                }
            }

            if (fail) {
                parameterList.clear();
            } else {
                validConstructor = constructor;
                break;
            }

        }

        if (validConstructor == null) {
            errorMessages.forEach(ReferenceData::constructError);
        } else {
            Object[] args = parameterList.toArray(new Object[parameterList.size()]);

            try {
                return validConstructor.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    private static void constructError(String error) {
        throw new RuntimeException("Cannot construct data! Error: '" + error + "'");
    }

    public void removeData(Reference<?> reference) {
        dataSet.remove(reference);
    }

    public Optional<Object> getDataAssignable(Class<?> dataClass) {

        for (Reference<?> data : dataSet) {

            // Prevent ClassCannotCastException
            Object r;
            if ((r = data.get()).getClass().isAssignableFrom(dataClass)) {
                return Optional.of(r);
            }

        }

        return Optional.empty();
    }

    private Optional<Object> getData(Class<?> dataClass) {

        for (Reference<?> data : dataSet) {

            // Prevent ClassCannotCastException
            Object r;
            if ((r = data.get()).getClass() == dataClass) {
                return Optional.of(r);
            }

        }

        return Optional.empty();
    }

    public Object construct(Class<?> dataClass) {
        return ReferenceData.construct(this, dataClass);
    }

    public <T> void registerData(Reference<T> reference) {
        if (!findData(reference))
            dataSet.add(reference);
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
     * @param <T>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getData(Reference<?> reference) {

        for (Reference<?> data : dataSet) {

            // Prevent ClassCannotCastException
            if (data.equals(reference)) {
                return Optional.of((T) data.get());
            }

        }

        return Optional.empty();
    }

    public void migrateFrom(ReferenceData data) {
        this.dataSet.addAll(data.dataSet);
    }

    public void migrateTo(ReferenceData data) {
        data.dataSet.addAll(this.dataSet);
    }

    @Override
    public ReferenceData clone() throws CloneNotSupportedException {
        super.clone();
        ReferenceData data = new ReferenceData();
        data.dataSet.addAll(this.dataSet);
        return data;
    }

}
