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
package com.github.jonathanxd.iutils.typeinfo;

import com.github.jonathanxd.iutils.type.TypeInfo;
import com.github.jonathanxd.iutils.type.TypeInfoUtil;
import com.github.jonathanxd.iutils.type.TypeParameterProvider;
import com.github.jonathanxd.iutils.type.TypeProvider;
import com.github.jonathanxd.iutils.type.TypeUtil;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeInfoTest {


    private final TypeInfo<A> aInfo = new TypeParameterProvider<A>() {
    }.createTypeInfo();
    private final TypeInfo<B<List<?>>> bInfo = new TypeParameterProvider<B<List<?>>>() {
    }.createTypeInfo();

    @org.junit.Test
    public void testRef() {

        TypeInfo<Dict<? extends CharSequence, ? super Number>> ref = new TypeParameterProvider<Dict<? extends CharSequence, ? super Number>>() {
        }.createTypeInfo();

        Assert.assertEquals("Dict<CharSequence, Object>", ref.toString());

        Test<? extends CharSequence> test = new Test<>();

        TypeInfo[] myRefs = test.getClassReferences();

        Assert.assertEquals("CharSequence", myRefs[0].toString());

        TypeInfo<Dict<List<String>, Integer>> dictTypeInfo = new TypeParameterProvider<Dict<List<String>, Integer>>() {
        }.createTypeInfo();
        String toFullString1 = dictTypeInfo.toFullString();

        Assert.assertEquals("Dict<List<String>, Integer>", dictTypeInfo.toString());

        Assert.assertEquals("com.github.jonathanxd.iutils.typeinfo.TypeInfoTest$Dict<java.util.List<java.lang.String>, java.lang.Integer>", toFullString1);

        Assert.assertEquals(dictTypeInfo, TypeInfoUtil.fromFullString(toFullString1).get(0));

        TypeInfo<Map> typeInfo = TypeInfo.builderOf(Map.class).of(String.class).of(TypeInfo.builderOf(List.class).of(TypeInfo.builderOf(Map.class).of(String.class).of(Integer[].class))).build();

        String fullString = typeInfo.toFullString();

        Assert.assertEquals("java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>", fullString);

        Assert.assertEquals("[Map<String, List<Map<String, Integer[]>>>, Map<String, List<Map<String, Integer[]>>>]", TypeInfoUtil.fromFullString("java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>, java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>").toString());

        TypeInfo<String> typeInfo1 = new TypeParameterProvider<String>() {
        }.createTypeInfo();

        Assert.assertEquals(TypeInfo.of(String.class), typeInfo1);

        TypeInfo<List<String>> typeInfo2 = new TypeParameterProvider<List<String>>() {
        }.createTypeInfo();

        Assert.assertEquals(TypeInfo.builderOf(List.class).of(String.class).build(), typeInfo2);

        TypeInfo<List<?>> typeInfo3 = new TypeParameterProvider<List<?>>() {
        }.createTypeInfo();

        Assert.assertEquals(TypeInfo.builderOf(List.class).of(Object.class).build(), typeInfo3);

        TV<String> stringTV = new TV<String>() {
        };

        Assert.assertEquals(TypeInfo.builderOf(String.class).build(), stringTV.getReferences()[0]);

        TypeInfo<Class<List<String>>[][]>[] typeInfo4 = Data.A.getReferences();

        String toFullString = typeInfo4[0].toFullString();

        Assert.assertEquals("[[Ljava.lang.Class;<java.util.List<java.lang.String>>", toFullString);

        TypeInfo<?> listTypeInfo = TypeUtil.resolve(MyList.class, List.class);

        Assert.assertEquals(TypeInfo.builderOf(ArrayList.class).of(String.class).build(), listTypeInfo);

        TypeInfo<?> representationN =
                TypeInfo.builderOf(List.class)
                        .of(String.class)
                        .of(TypeInfo.builderOf(List.class).of(Integer.class))
                        .of(TypeInfo.builderOf(List.class).of(String.class))
                        .build();

        Assert.assertEquals("java.util.List<java.lang.String, java.util.List<java.lang.Integer>, java.util.List<java.lang.String>>", representationN.toFullString());

        TypeInfo<List<Object>> typeInfo6 = new TypeParameterProvider<List<Object>>() {
        }.createTypeInfo();
        TypeInfo<List<String>> typeInfo7 = new TypeParameterProvider<List<String>>() {
        }.createTypeInfo();
        //noinspection deprecation
        Assert.assertTrue(typeInfo6.isAssignableFrom(typeInfo7));

    }

    @org.junit.Test
    public void testSub() {

        bInfo.isAssignableFrom(aInfo);

        System.out.println(bInfo.isAssignableFrom(aInfo));


        Assert.assertTrue(bInfo.isAssignableFrom(aInfo));

    }

    @org.junit.Test
    public void testNew() {
        TypeInfo<List<Map<Integer, A>>> expected =
                TypeInfo.builderOf(List.class).of(TypeInfo.builderOf(Map.class).of(Integer.class).of(A.class)).buildGeneric();
        TypeInfo<List<Map<Integer, A>>> typeInfo = new TypeParameterProvider<List<Map<Integer, A>>>() {}.createTypeInfo();

        TypeInfo<Map<Integer, A>> expected2 =
                TypeInfo.builderOf(Map.class).of(Integer.class).of(A.class).buildGeneric();

        Assert.assertEquals(expected, typeInfo);
        Assert.assertEquals(expected2, typeInfo.getTypeParameter("E"));
        Assert.assertEquals(expected2, typeInfo.getTypeParameter(0));
        Assert.assertEquals(TypeInfo.of(Integer.class), typeInfo.getTypeParameter("E").getTypeParameter("K"));
        Assert.assertEquals(TypeInfo.of(A.class), typeInfo.getTypeParameter("E").getTypeParameter("V"));
    }

    public static class A extends X<List<?>> {
    }

    public static class X<E> extends B<E> { /*...*/
    }

    public static class B<E> { /*...*/
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
        @SuppressWarnings("unchecked")
        public TV() {
        }

    }

}
