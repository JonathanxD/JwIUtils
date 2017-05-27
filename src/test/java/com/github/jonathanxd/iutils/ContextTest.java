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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.processing.Context;
import com.github.jonathanxd.iutils.processing.ContextHolder;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ContextTest {

    @Test
    public void contextTest() {
        Context context = Context.create();

        a(context);

        Assert.assertTrue(context.getContexts().isEmpty());
        Assert.assertTrue(context.getAllContexts().size() == 3);

        List<ContextHolder> lifoAllContexts = context.getLifoAllContexts();

        Assert.assertEquals("c()", lifoAllContexts.get(0).getContext());
        Assert.assertEquals("b()", lifoAllContexts.get(1).getContext());
        Assert.assertEquals("a()", lifoAllContexts.get(2).getContext());
    }

    public void a(Context context) {
        context.enterContext("a()");
        this.b(context);
        context.exitContext("a()");
    }

    public void b(Context context) {
        context.enterContext("b()");
        c(context);
        context.printContext(true, true);
        context.exitContext("b()");
    }

    public void c(Context context) {
        context.enterContext("c()");
        context.exitContext("c()");
    }

}
