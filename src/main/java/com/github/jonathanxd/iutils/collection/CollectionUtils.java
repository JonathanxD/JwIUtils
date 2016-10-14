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
package com.github.jonathanxd.iutils.collection;

import com.github.jonathanxd.iutils.exception.BiException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by jonathan on 05/03/16.
 */
public class CollectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> same(Collection<?> collection) {
        try {
            return (Collection<T>) collection.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            try {
                return collection.getClass().getConstructor(Collection.class).newInstance(Collections.emptyList());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> sameFilled(Collection<T> collection) {
        try {
            return collection.getClass().getConstructor(Collection.class).newInstance(collection);
        } catch (Throwable t) {
            try {
                Collection rmk = collection.getClass().newInstance();

                rmk.addAll(collection);

                return (Collection<T>) rmk;
            } catch (InstantiationException | IllegalAccessException e1) {
                throw new BiException(t, e1);
            }
        }

    }

}
