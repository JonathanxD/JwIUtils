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
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.map.TypedMap;
import com.github.jonathanxd.iutils.type.Primitive;
import com.github.jonathanxd.iutils.type.TypeInfo;
import com.github.jonathanxd.iutils.type.TypeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Construction and invocation from data utility.
 */
public class DataReflect {

    /**
     * Construct {@code type} using values provided by {@link Data data}.
     *
     * @param type Type to construct.
     * @param data Data.
     * @return Constructed object or null if object cannot be constructed from {@code data}.
     */
    public static Object construct(Class<?> type, DataBase data) {
        return DataReflect.construct(type, data, e -> true);
    }

    /**
     * Invokes a method of {@code object} using {@code data} values.
     *
     * @param object Object instance.
     * @param data   Data.
     * @return Result of invocation (or null if cannot invoke or the invoked method return null).
     */
    public static Object invoke(Object object, DataBase data) {
        return DataReflect.invoke(object, data, e -> true);
    }

    /**
     * Construct {@code type} using values provided by {@link Data data}.
     *
     * @param type Type to construct.
     * @param data Data.
     * @param test Constructor selector (select which constructor will be used to construct the
     *             object of type {@code type}).
     * @return Constructed object or null if object cannot be constructed from {@code data}.
     */
    public static Object construct(Class<?> type, DataBase data, Predicate<Constructor<?>> test) {

        return create(data, type, type::getDeclaredConstructors, (e, args) -> {
            try {
                return e.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e1) {
                e1.printStackTrace();
            }
            return null;
        }, test);
    }

    /**
     * Invokes a method of {@code object} using {@code data} values.
     *
     * @param object          Object instance.
     * @param data            Data.
     * @param methodPredicate Method selector (selects which method will be invoked).
     * @return Result of invocation (or null if cannot invoke or the invoked method return null).
     */
    public static Object invoke(Object object, DataBase data, Predicate<Method> methodPredicate) {

        return create(data, object.getClass(), object.getClass()::getDeclaredMethods, (e, args) -> {
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

    private static <E extends Executable> Object create(DataBase baseData, Class<?> dataClass, Supplier<E[]> supplyElements, BiFunction<E, Object[], Object> function, Predicate<E> accept) {

        List<String> errorMessages = new ArrayList<>();

        List<Object> parameterList = new ArrayList<>();

        E valid = null;

        E[] array = supplyElements.get();

        TypedMap<Object, Object> dataMap = baseData.getTypedDataMap();


        for (E element : array) {

            if (!accept.test(element))
                continue;

            boolean fail = false;

            for (Type parameterTypeType : element.getGenericParameterTypes()) {

                TypeInfo<?> parameterType = TypeUtil.toTypeInfo(parameterTypeType);

                Optional<Object> objOpt = Optional.empty();

                TypeInfo<?> type = parameterType.getTypeClass().isPrimitive()
                        ? TypeInfo.of(Primitive.box(parameterType.getTypeClass()))
                        : parameterType;

                for (TypedMap.TypedEntry<Object, ?> objectTypedEntry : dataMap.typedEntrySet()) {
                    Object value = objectTypedEntry.getValue();

                    TypeInfo<?> tmp = objectTypedEntry.getType();

                    TypeInfo<?> valueType = tmp.getTypeClass().isPrimitive()
                            ? TypeInfo.of(Primitive.box(tmp.getTypeClass()))
                            : tmp;

                    if (type.isAssignableFrom(valueType)) {
                        objOpt = Optional.of(value);
                        break;
                    }
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
            errorMessages.forEach(DataReflect::invokeError);
        } else {
            Object[] args = parameterList.toArray(new Object[parameterList.size()]);
            return function.apply(valid, args);
        }

        return null;
    }
}
