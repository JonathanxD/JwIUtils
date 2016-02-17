/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jonathan on 13/02/16.
 */
public class ExtraData {

    private final Set<Object> dataSet = new HashSet<>();

    public static Object construct(ExtraData extraData, Class<?> dataClass) {

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
            errorMessages.forEach(ExtraData::constructError);
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

    public Object construct(Class<?> dataClass) {
        return ExtraData.construct(this, dataClass);
    }

    public void registerData(Object data) {
        if (!findData(data.getClass()))
            dataSet.add(data);
    }

    /**
     * Find a data in Set based on Class
     *
     * @param dataClass Class of data
     * @return True if data exists in DataSet
     */
    public boolean findData(Class<?> dataClass) {
        return getData(dataClass).isPresent();
    }

    /**
     * Get data <br> Expression: if(Class equalsTo (==) Data Class) return (Type Cast) DataObject
     *
     * @param dataClass Class of data
     * @param <T>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    public <T> Optional<T> getData(Class<? extends T> dataClass) {

        return getData(dataClass, (o1, o2) -> o1 == o2 ? 0 : -1);

    }

    /**
     * Get data <br> Expression: if(Class isAssignableFrom Data Class) return (Type Cast)
     * DataObject
     *
     * @param dataClass Class assignable from Data
     * @param <T>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    public <T> Optional<T> getDataAssignable(Class<? extends T> dataClass) {

        return getData(dataClass, (o1, o2) -> o2.isAssignableFrom(o1) ? 0 : -1);

    }

    /**
     * Get data <br> Comparator: Parameter 1 = Class of Data in Set. Parameter 2 = Data Class
     * parameter <br> Expression: if(Comparator.compare(DataObject class, Data Class parameter)
     * equalsTo 0) return (Type Cast) DataObject
     *
     * @param dataClass  Data Class
     * @param comparator Comparator of DataObject Class and Data Class parameter
     * @param <T>        Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getData(Class<? extends T> dataClass, Comparator<Class<?>> comparator) {

        for (Object data : dataSet) {

            // Prevent ClassCannotCastException
            if (!dataClass.isAssignableFrom(data.getClass())) {
                continue;
            }

            if (comparator.compare(data.getClass(), dataClass) == 0) {
                return Optional.of((T) data);
            }
        }

        return Optional.empty();
    }

}
