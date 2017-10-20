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
package com.github.jonathanxd.iutils.reflection;

import com.github.jonathanxd.iutils.annotation.Singleton;

import org.junit.Assert;
import org.junit.Test;

public class TestSingleton {

    @Test
    public void testSingleton() {
        Obj instance = Reflection.getInstance(Obj.class);

        Assert.assertEquals(Obj.INSTANCE, instance);
    }

    @Test
    public void testSingleton2() {
        Obj2 instance = Reflection.getInstance(Obj2.class);

        Assert.assertEquals(Obj2.DEFAULT, instance);
    }

    public static class Obj {
        public static final Obj INSTANCE = new Obj();

        private Obj() {
        }
    }

    @Singleton("DEFAULT")
    public static class Obj2 {
        public static final Obj2 DEFAULT = new Obj2();

        private Obj2() {
        }
    }

}
