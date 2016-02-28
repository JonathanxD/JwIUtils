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
package com.github.jonathanxd.iutils.function;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by jonathan on 28/02/16.
 */
public class FunctionUtil {

    @SuppressWarnings("unchecked")
    public static <T, R> R[] as(T[] array, Function<T, R> translator) {

        List<R> list = new ArrayList<>();

        Class<?> type = null;
        for(T t : array) {
            R o =translator.apply(t);
            list.add(o);
            if(type == null)
                type = o.getClass();
        }

        if(type != null) {
            return list.toArray((R[]) Array.newInstance(type, list.size()));
        }else{
            return (R[]) new Object[]{};
        }
    }

    public static <T> String[] asString(T[] array) {
        return as(array, Object::toString);
    }

}
