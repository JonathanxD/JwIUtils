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

/**
 * Provides a Simple Data Injection on Members.
 * @param <T> Type of Input
 */
public abstract class BaseData<T> {

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

    public abstract void addData(T data, Object o);

    public abstract void removeData(T data);


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

    @Override
    public abstract BaseData clone();
}
