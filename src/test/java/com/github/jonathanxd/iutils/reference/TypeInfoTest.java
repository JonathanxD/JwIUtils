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
import com.github.jonathanxd.iutils.object.AbstractTypeInfo;
import com.github.jonathanxd.iutils.object.ConcreteTypeInfo;
import com.github.jonathanxd.iutils.object.TypeInfo;
import com.github.jonathanxd.iutils.object.TypeProvider;
import com.github.jonathanxd.iutils.object.TypeUtil;

import org.junit.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by jonathan on 02/04/16.
 */
public class TypeInfoTest {


    @org.junit.Test
    public <V> void testRef() {

        TypeInfo<V> typeInf0 = new ConcreteTypeInfo<V>() {};
        TypeInfo<Dict<? extends CharSequence, ? super Number>> ref = new AbstractTypeInfo<Dict<? extends CharSequence, ? super Number>>() {};

        System.out.println("Ref: "+ref);

        Test<? extends CharSequence> test = new Test<>();

        TypeInfo[] myRefs = test.getClassReferences();

        System.out.println("Ref: "+ Arrays.toString(myRefs));

        TypeInfo<Dict<List<String>, Integer>> dictTypeInfo = new AbstractTypeInfo<Dict<List<String>, Integer>>() {};
        String toFullString1 = dictTypeInfo.toFullString();

        System.out.println("Dict: "+ dictTypeInfo);

        System.out.println("Dict: "+ toFullString1);

        try {
            System.out.println("Dict: "+ TypeInfo.fromFullString(toFullString1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        TypeInfo<Map> typeInfo = TypeInfo.a(Map.class).of(String.class).and(TypeInfo.a(List.class).of(TypeInfo.a(Map.class).of(String.class).and(Integer[].class))).build();

        String fullString = typeInfo.toFullString();

        System.out.println(fullString);

        try {
            System.out.println(TypeInfo.fromFullString(fullString));
            System.out.println(TypeInfo.fromFullString("java.util.Map"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Assert.assertEquals("[Map<String, List<Map<String, Integer[]>>>, Map<String, List<Map<String, Integer[]>>>]", TypeInfo.fromFullString("java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>, java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>").toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TypeInfo<String> typeInfo1 = new AbstractTypeInfo<String>(){};

        System.out.println(typeInfo1);


        TypeInfo<List<String>> typeInfo2 = new AbstractTypeInfo<List<String>>(){};

        System.out.println(typeInfo2);

        TypeInfo<List<?>> typeInfo3 = new AbstractTypeInfo<List<?>>(){};

        System.out.println(typeInfo3);

        TV<String> stringTV = new TV<String>(){};

        System.out.println("stringTV -> "+Arrays.toString(stringTV.getReferences()));

        TypeInfo<Class<List<String>>[][]>[] typeInfo4 = Data.A.getReferences();

        System.out.println("Reference4 -> "+Arrays.toString(typeInfo4));

        String toFullString = typeInfo4[0].toFullString();

        System.out.println(toFullString);

        try {
            System.out.println(TypeInfo.fromFullString(toFullString));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TypeInfo<?> listTypeInfo = TypeUtil.resolve(MyList.class, List.class);

        System.out.println("GenericRepresentation => "+ listTypeInfo);

        Function<String, Integer> stringToInteger = Integer::valueOf;

        TypeInfo<?> functionGenerics = TypeUtil.resolve(stringToInteger.getClass(), Function.class);

        System.out.println("GenericRepresentations of Function<String, Integer> => "+ functionGenerics);

        TypeInfo<?> typeInfo5 = TypeUtil.lambdaTypes(stringToInteger.getClass(), Function.class);

        System.out.println("GenericRepresentations of Function<String, Integer> => "+ typeInfo5);


        TypeInfo<?> representationN =
                TypeInfo.of(List.class)
                .of(String.class)
                .and(TypeInfo.of(List.class).of(Integer.class))
                .and(TypeInfo.of(List.class).of(String.class))
                .build();

        System.out.println("MultiRepresentation of List<String, List<Integer>, List<String>> => "+ representationN);

        String toFullString2 = representationN.toFullString();

        System.out.println("MultiRepresentation of List<String, List<Integer>, List<String>> Full: => "+ toFullString2);

        try {
            System.out.println("MultiRepresentation of List<String, List<Integer>, List<String>> From Full: => "+ TypeInfo.fromFullString(toFullString2));
        } catch (ClassNotFoundException e) {
            throw new RethrowException(e);
        }

        TypeInfo<List<Object>> typeInfo6 = new ConcreteTypeInfo<List<Object>>() {};
        TypeInfo<List<String>> typeInfo7 = new ConcreteTypeInfo<List<String>>() {};
        System.out.println(typeInfo6.compareToAssignable(typeInfo7));
    }

    @org.junit.Test
    public void testSub() {

        TypeInfo<A> aInfo = new ConcreteTypeInfo<A>() {};

        TypeInfo<B<List<?>>> bInfo = new ConcreteTypeInfo<B<List<?>>>() {};

        System.out.println(bInfo.compareToAssignable(aInfo));
        System.out.println(bInfo.isAssignableFrom(aInfo));

        Assert.assertTrue(bInfo.isAssignableFrom(aInfo));


    }

    public static class A extends X<List<?>> {}
    public static class X<E> extends B<E> { /*...*/ }
    public static class B<E> { /*...*/ }



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
        private final TypeInfo<T> typeInfo;

        @SuppressWarnings("unchecked")
        public TV() {
            typeInfo = getReferences()[0];

            TypeInfo<?> reef = TypeUtil.toReference((ParameterizedType) getClass().getGenericSuperclass());

            System.out.println(reef);
        }

    }

}
