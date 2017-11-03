/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.data.Data;
import com.github.jonathanxd.iutils.data.DataReflect;
import com.github.jonathanxd.iutils.data.TypedData;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.TypedKey;
import com.github.jonathanxd.iutils.type.TypeInfo;

import org.junit.Assert;
import org.junit.Test;

public class DataTest {

    @Test
    public void dataTest1() {
        Data data = new Data();

        data.set("name", "Cup");
        data.set("amount", 10);

        int get = data.getOrSet("amount", 5);
        int get0 = data.getOrSet("am0unt", 5);

        Assert.assertEquals(10, get);
        Assert.assertEquals(5, get0);
    }

    static class Product {
        private final String name;

        Product(String name) {
            this.name = name;
        }
    }

    public static class Stock {
        private final Product product;
        private final int amount;

        public Stock(Product product, int amount) {
            this.product = product;
            this.amount = amount;
        }
    }

    @Test
    public void dataTest2() {
        Data data = new Data();

        data.set("product", new Product("Cup"));
        data.set("amount", 10);

        Stock construct = (Stock) DataReflect.construct(Stock.class, data);


        Assert.assertEquals("Cup", construct.product.name);
        Assert.assertEquals(10, construct.amount);
    }

    @Test
    public void dataTest() {
        Data data = new Data();

        data.set("a", 9);
        data.set("b", 5.5D);
        data.set("c", "DataTest");

        Mrs construct = (Mrs) DataReflect.construct(Mrs.class, data);

        Assert.assertEquals(9, construct.getTag());
        Assert.assertEquals(5.5D, construct.getAmount(), 0);
        Assert.assertEquals("DataTest", construct.getName());

    }

    @Test
    public void typedDataTest() {
        TypedData typedData = new TypedData();

        typedData.set("number", 9, TypeInfo.of(Integer.class));

        Mx construct = (Mx) DataReflect.construct(Mx.class, typedData);

        Assert.assertEquals(9, construct.getNumber());
        Assert.assertEquals(9, typedData.getOrNull("number", TypeInfo.of(Integer.class)).intValue());

    }

    @Test
    public void typedDataKeyTest() {
        TypedData data = new TypedData();

        TypedKey<Integer> typedKey = new TypedKey<>("number", TypeInfo.of(Integer.class));

        typedKey.set(data, 8);

        Assert.assertTrue(typedKey.contains(data));
        Assert.assertEquals(8, typedKey.getOrNull(data).intValue());

        Pair<?, TypeInfo<?>> number = new TypedKey<>("number", TypeInfo.of(Long.class)).removeAny(data);

        Assert.assertEquals(8, number.getFirst());
        Assert.assertEquals(TypeInfo.of(Integer.class), number.getSecond());
    }

    @Test
    public void tempDataTest() {
        TypedData data = new TypedData();

        TypedKey<Integer> temporary = new TypedKey<>("number", TypeInfo.of(Integer.class));

        temporary.set(data, 9, true);

        Assert.assertEquals(Integer.valueOf(9), temporary.getOrNull(data));
        Assert.assertEquals(null, temporary.getOrNull(data));
    }

    public static class Mx {
        private final int number;

        public Mx(int number) {
            this.number = number;
        }

        public int getNumber() {
            return this.number;
        }
    }

    public static class Mrs {
        private final int tag;
        private final double amount;
        private final String name;

        public Mrs(int tag, double amount, String name) {
            this.tag = tag;
            this.amount = amount;
            this.name = name;
        }

        public int getTag() {
            return this.tag;
        }

        public double getAmount() {
            return this.amount;
        }

        public String getName() {
            return this.name;
        }
    }

}
