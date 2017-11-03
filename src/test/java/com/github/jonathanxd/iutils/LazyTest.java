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

import com.github.jonathanxd.iutils.container.primitivecontainers.BooleanContainer;
import com.github.jonathanxd.iutils.object.Lazy;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Executors;

public class LazyTest {

    @Test
    public void testLazy() {
        BooleanContainer container = BooleanContainer.of(false);

        Lazy<String> lazy = Lazy.lazy(() -> {
            container.set(true);
            return "Lazy";
        });

        Assert.assertFalse(container.get());
        Assert.assertEquals("Lazy", lazy.get());

    }

    @Test
    public void testEvaluated() {
        Lazy<String> lazy = Lazy.evaluated("Lazy");

        Assert.assertEquals("Lazy", lazy.get());
    }

    @Test
    public void testAsync() {
        Lazy.Async<String> lazy = Lazy.async(Lazy.lazy(this::expensiveOperation));

        lazy.evaluateAsync(Executors.newSingleThreadExecutor());

        Assert.assertEquals("AsyncLazy", lazy.get());
    }

    private String expensiveOperation() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "AsyncLazy";
    }

}
