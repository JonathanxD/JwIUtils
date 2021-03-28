/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.exception.InitializationException;
import com.github.jonathanxd.iutils.object.LateInit;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.object.MutableLateInit;

import org.junit.Assert;
import org.junit.Test;

public class LateTest {
    @Test(expected = InitializationException.class)
    public void initFail() {
        LateInit.lateRef("testA").getValue();
    }

    @Test(expected = InitializationException.class)
    public void reinitFail() {
        LateInit.Ref<Object> testA = LateInit.lateRef("testA");

        testA.init("A");
        testA.init("B");
    }

    @Test
    public void initNormal() {
        LateInit.Ref<Object> testA = LateInit.lateRef("testA");

        Assert.assertFalse(testA.isInitialized());
        testA.init("A");
        Assert.assertTrue(testA.isInitialized());
        Assert.assertEquals("A", testA.getValue());
    }

    @Test
    public void initLong() {
        LateInit.Long l = LateInit.lateLong("testA");

        Assert.assertFalse(l.isInitialized());
        l.init(569L);
        Assert.assertTrue(l.isInitialized());
        Assert.assertEquals(569L, l.getValue());
    }

    @Test
    public void initLazy() {
        Lazy<String> myLazy = Lazy.lazy(() -> "Hello");
        LateInit.LLazy<String> llazy = LateInit.lateLazy("testA");

        Assert.assertFalse(llazy.isInitialized());
        Assert.assertFalse(myLazy.isEvaluated());
        llazy.init(myLazy);
        Assert.assertTrue(llazy.isInitialized());
        Assert.assertFalse(myLazy.isEvaluated());
        Assert.assertEquals("Hello", llazy.getValue());
        Assert.assertTrue(myLazy.isEvaluated());
    }


    @Test
    public void mutableReInitFail() {
        MutableLateInit.Ref<Object> hello = MutableLateInit.lateRef("hello");

        hello.init("A");
        Assert.assertEquals("A", hello.getValue());
        hello.init("B");
        Assert.assertEquals("B", hello.getValue());
    }
}
