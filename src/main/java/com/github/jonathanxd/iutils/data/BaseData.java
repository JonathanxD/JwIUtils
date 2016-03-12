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
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by jonathan on 11/03/16.
 */
public abstract class BaseData<T> {

    private final Set<T> dataSet = new HashSet<>();

    public static <T, E extends Executable> Object match(BaseData<T> baseData, Class<?> dataClass, Supplier<E[]> supplyElements, BiFunction<E, Object[], Object> function, Predicate<E> accept) {

        List<String> errorMessages = new ArrayList<>();

        List<Object> parameterList = new ArrayList<>();

        E valid = null;

        E[] array = supplyElements.get();


        for (E element : array) {

            if (!accept.test(element))
                continue;

            boolean fail = false;

            for (Class<?> parameterType : element.getParameterTypes()) {

                Optional<Object> objOpt = baseData.getData(parameterType);

                if (!objOpt.isPresent()) {
                    objOpt = baseData.getDataAssignable(parameterType);
                }

                if (!objOpt.isPresent()) {
                    errorMessages.add(String.format("Cannot determine instance of %s !", parameterType));
                    fail = true;
                } else {
                    Object object = objOpt.get();
                    if (parameterList.contains(object)) {
                        errorMessages.add(String.format("Argument %s already requested!", parameterType));
                        fail = true;
                    } else {
                        parameterList.add(object);
                    }


                }
            }

            if (fail) {
                parameterList.clear();
            } else {
                valid = element;
                break;
            }

        }

        if (valid == null) {
            errorMessages.forEach(BaseData::invokeError);
        } else {
            Object[] args = parameterList.toArray(new Object[parameterList.size()]);
            return function.apply(valid, args);
        }

        return null;
    }


    public static Object construct(BaseData baseData, Class<?> dataClass, Predicate<Constructor<?>> test) {

        return match(baseData, dataClass, dataClass::getDeclaredConstructors, (e, args) -> {
            try {
                return e.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e1) {
                e1.printStackTrace();
            }
            return null;
        }, test);
    }

    public static Object invoke(BaseData baseData, Object object, Predicate<Method> methodPredicate) {

        return match(baseData, object.getClass(), object.getClass()::getDeclaredMethods, (e, args) -> {
            try {
                return e.invoke(object, args);
            } catch (IllegalAccessException | InvocationTargetException e1) {
                e1.printStackTrace();
            }
            return null;
        }, methodPredicate);
    }

    private static void invokeError(String error) {
        throw new RuntimeException("Cannot invoke target. Error: '" + error + "'");
    }

    public Object construct(Class<?> dataClass) {
        return BaseData.construct(this, dataClass, e -> true);
    }

    public Object invoke(Object object) {
        return BaseData.invoke(this, object, e -> true);
    }

    public Object construct(Class<?> dataClass, Predicate<Constructor<?>> constructorPredicate) {
        return BaseData.construct(this, dataClass, constructorPredicate);
    }

    public Object invoke(Object object, Predicate<Method> methodPredicate) {
        return BaseData.invoke(this, object, methodPredicate);
    }

    /**
     * Register Data
     *
     * @param data Data to register
     */
    public void registerData(T data) {
        if (!findData(data.getClass()))
            dataSet.add(data);
    }

    public void removeData(T data) {
        dataSet.remove(data);
    }


    public abstract <X> Optional<X> getData(T data);

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
     * @param <X>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    public abstract <X> Optional<X> getData(Class<? extends X> dataClass);

    public abstract <X> Optional<X> getData(Parameter parameter);

    /**
     * Get data <br> Expression: if(Class isAssignableFrom Data Class) return (Type Cast)
     * DataObject
     *
     * @param dataClass Class assignable from Data
     * @param <X>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    public abstract <X> Optional<X> getDataAssignable(Class<? extends X> dataClass);

    protected Set<T> getDataSet() {
        return dataSet;
    }

    protected Set<T> dataSet() {
        return Collections.unmodifiableSet(dataSet);
    }

    @Override
    public abstract BaseData clone();
}
