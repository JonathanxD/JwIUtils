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

import org.junit.Assert;
import org.junit.Test;

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

    }

}
