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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.data.Data;
import com.github.jonathanxd.iutils.data.DataReflect;

import org.junit.Assert;
import org.junit.Test;

public class DataTest {

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
