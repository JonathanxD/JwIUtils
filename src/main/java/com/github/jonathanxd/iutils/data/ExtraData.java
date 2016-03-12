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

import java.lang.reflect.Parameter;
import java.util.Comparator;
import java.util.Optional;

/**
 * Created by jonathan on 13/02/16.
 */
public class ExtraData extends BaseData<Object> implements Cloneable {

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
    public ExtraData clone() {
        ExtraData data = new ExtraData();
        data.getDataSet().addAll(this.getDataSet());
        return data;
    }
}
