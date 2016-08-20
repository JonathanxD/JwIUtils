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

import java.lang.reflect.Parameter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jonathan on 13/02/16.
 */
public class ExtraData extends BaseData<Object> implements Cloneable {

    private final Set<Object> dataSet = new HashSet<>();

    @Override
    public void removeData(Object data) {
        this.dataSet.remove(data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(Object data) {
        return getDataSet().contains(data) ? Optional.of((X) data) : Optional.empty();
    }

    /**
     * Get data <br> Expression: if(Class equalsTo (==) Data Class) return (Type Cast) DataObject
     *
     * @param dataClass Class of data
     * @param <T>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    @Override
    public <T> Optional<T> getData(Class<? extends T> dataClass) {

        return getData(dataClass, (o1, o2) -> o1 == o2 ? 0 : -1);

    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Optional<X> getData(Parameter parameter) {
        return getData((Class<? extends X>) parameter.getType());
    }

    /**
     * Get data <br> Expression: if(Class isAssignableFrom Data Class) return (Type Cast)
     * DataObject
     *
     * @param dataClass Class assignable from Data
     * @param <T>       Type of Data
     * @return Optional of Data or {@link Optional#empty()}
     */
    @Override
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

        for (Object data : getDataSet()) {

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

    @Override
    public void addData(Object data, Object o) {
        this.dataSet.add(o);
    }

    protected Set<Object> getDataSet() {
        return dataSet;
    }

    @Override
    public ExtraData clone() {
        ExtraData data = new ExtraData();
        data.getDataSet().addAll(this.getDataSet());
        return data;
    }
}
