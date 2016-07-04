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

import com.github.jonathanxd.iutils.exceptions.RethrowException;
import com.github.jonathanxd.iutils.object.AbstractGenericRepresentation;
import com.github.jonathanxd.iutils.object.GenericRepresentation;
import com.github.jonathanxd.iutils.object.TypeProvider;
import com.github.jonathanxd.iutils.object.TypeUtil;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * Created by jonathan on 02/04/16.
 */
public class GenericRepresentationTest {


    @org.junit.Test
    public void testRef() {

        GenericRepresentation<Dict<? extends CharSequence, ? super Number>> ref = new AbstractGenericRepresentation<Dict<? extends CharSequence, ? super Number>>() {};

        System.out.println("Ref: "+ref);

        Test<? extends CharSequence> test = new Test<>();

        GenericRepresentation[] myRefs = test.getClassReferences();

        System.out.println("Ref: "+ Arrays.toString(myRefs));

        GenericRepresentation<Dict<List<String>, Integer>> dictGenericRepresentation = new AbstractGenericRepresentation<Dict<List<String>, Integer>>() {};
        String toFullString1 = dictGenericRepresentation.toFullString();

        System.out.println("Dict: "+ dictGenericRepresentation);

        System.out.println("Dict: "+ toFullString1);

        try {
            System.out.println("Dict: "+ GenericRepresentation.fromFullString(toFullString1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        GenericRepresentation<Map> genericRepresentation = GenericRepresentation.a(Map.class).of(String.class).and(GenericRepresentation.a(List.class).of(GenericRepresentation.a(Map.class).of(String.class).and(Integer[].class))).build();

        String fullString = genericRepresentation.toFullString();

        System.out.println(fullString);

        try {
            System.out.println(GenericRepresentation.fromFullString(fullString));
            System.out.println(GenericRepresentation.fromFullString("java.util.Map"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Assert.assertEquals("[Map<String, List<Map<String, Integer[]>>>, Map<String, List<Map<String, Integer[]>>>]", GenericRepresentation.fromFullString("java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>, java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>").toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        GenericRepresentation<String> genericRepresentation1 = new AbstractGenericRepresentation<String>(){};

        System.out.println(genericRepresentation1);


        GenericRepresentation<List<String>> genericRepresentation2 = new AbstractGenericRepresentation<List<String>>(){};

        System.out.println(genericRepresentation2);

        GenericRepresentation<List<?>> genericRepresentation3 = new AbstractGenericRepresentation<List<?>>(){};

        System.out.println(genericRepresentation3);

        TV<String> stringTV = new TV<String>(){};

        System.out.println("stringTV -> "+Arrays.toString(stringTV.getReferences()));

        GenericRepresentation<Class<List<String>>[][]>[] genericRepresentation4 = Data.A.getReferences();

        System.out.println("Reference4 -> "+Arrays.toString(genericRepresentation4));

        String toFullString = genericRepresentation4[0].toFullString();

        System.out.println(toFullString);

        try {
            System.out.println(GenericRepresentation.fromFullString(toFullString));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        GenericRepresentation<?> listGenericRepresentation = TypeUtil.resolve(MyList.class, List.class);

        System.out.println("GenericRepresentation => "+ listGenericRepresentation);

        Function<String, Integer> stringToInteger = Integer::valueOf;

        GenericRepresentation<?> functionGenerics = TypeUtil.resolve(stringToInteger.getClass(), Function.class);

        System.out.println("GenericRepresentations of Function<String, Integer> => "+ functionGenerics);

        GenericRepresentation<?> genericRepresentation5 = TypeUtil.lambdaTypes(stringToInteger.getClass(), Function.class);

        System.out.println("GenericRepresentations of Function<String, Integer> => "+ genericRepresentation5);


        GenericRepresentation<?> representationN =
                GenericRepresentation.of(List.class)
                .of(String.class)
                .and(GenericRepresentation.of(List.class).of(Integer.class))
                .and(GenericRepresentation.of(List.class).of(String.class))
                .build();

        System.out.println("MultiRepresentation of List<String, List<Integer>, List<String>> => "+ representationN);

        String toFullString2 = representationN.toFullString();

        System.out.println("MultiRepresentation of List<String, List<Integer>, List<String>> Full: => "+ toFullString2);

        try {
            System.out.println("MultiRepresentation of List<String, List<Integer>, List<String>> From Full: => "+ GenericRepresentation.fromFullString(toFullString2));
        } catch (ClassNotFoundException e) {
            throw new RethrowException(e);
        }


    }

    public static class MyList extends ArrayList<String> {

    }

    public static class Dict<K, V> {

    }

    public static abstract class Data<T> implements TypeProvider {

        public static final Data<Class<List<String>>[][]> A = new Data<Class<List<String>>[][]>() {
        };

        public static final Data<Class<?>[]> IMPLEMENTATIONS = new Data<Class<?>[]>() {
        };
        public static final Data<Class<?>> SUPER = new Data<Class<?>>() {
        };
    }

    private static class Test<T extends CharSequence & Serializable> implements TypeProvider {

    }

    private abstract static class TV<T> implements TypeProvider {
        private final GenericRepresentation<T> genericRepresentation;

        @SuppressWarnings("unchecked")
        public TV() {
            genericRepresentation = getReferences()[0];

            GenericRepresentation<?> reef = TypeUtil.toReference((ParameterizedType) getClass().getGenericSuperclass());

            System.out.println(reef);
        }

    }

}
