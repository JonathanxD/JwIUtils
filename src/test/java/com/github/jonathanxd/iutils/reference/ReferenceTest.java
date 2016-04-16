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
package com.github.jonathanxd.iutils.reference;

import com.github.jonathanxd.iutils.object.AbstractReference;
import com.github.jonathanxd.iutils.object.Reference;
import com.github.jonathanxd.iutils.object.TypeProvider;
import com.github.jonathanxd.iutils.object.TypeUtil;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by jonathan on 02/04/16.
 */
public class ReferenceTest {

    @Test
    public void testRef() {
        Reference<Map> reference = Reference.a(Map.class).of(String.class).and(Reference.a(List.class).of(Reference.a(Map.class).of(String.class).and(Integer[].class))).build();

        String fullString = reference.toFullString();

        System.out.println(fullString);

        try {
            System.out.println(Reference.fromFullString(fullString));
            System.out.println(Reference.fromFullString("java.util.Map"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Assert.assertEquals(Reference.fromFullString("java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>, java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>").toString(), "[Map<String, List<Map<String, Integer[]>>>, Map<String, List<Map<String, Integer[]>>>]");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Reference<String> reference1 = new AbstractReference<String>(){};

        System.out.println(reference1);


        Reference<List<String>> reference2 = new AbstractReference<List<String>>(){};

        System.out.println(reference2);

        Reference<List<?>> reference3 = new AbstractReference<List<?>>(){};

        System.out.println(reference3);

        TV<String> stringTV = new TV<String>(){};

        System.out.println(stringTV.getReference());

        Reference<Class<List<String>>[][]> reference4 = Data.A.getReference();

        System.out.println(reference4);

        String toFullString = reference4.toFullString();

        System.out.println(toFullString);

        try {
            System.out.println(Reference.fromFullString(toFullString));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static abstract class Data<T> implements TypeProvider<T> {

        public static final Data<Class<List<String>>[][]> A = new Data<Class<List<String>>[][]>() {
        };

        public static final Data<Class<?>[]> IMPLEMENTATIONS = new Data<Class<?>[]>() {
        };
        public static final Data<Class<?>> SUPER = new Data<Class<?>>() {
        };
    }

    private abstract static class TV<T> implements TypeProvider<T> {
        private final Reference<T> reference;

        public TV() {
            reference = new AbstractReference<T>(this) {};

            Reference<?> reef = TypeUtil.toReference((ParameterizedType) getClass().getGenericSuperclass());

            System.out.println(reef);
        }

    }

}
