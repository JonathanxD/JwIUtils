/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.function.exception.FuncTry;
import com.github.jonathanxd.iutils.object.result.Result;

import org.junit.Assert;
import org.junit.Test;

public class FuncTryTest {
    @Test
    public void funcTryTest() {
        Result<Integer, Throwable> sa = FuncTry
                .Try(() -> Integer.parseInt("sa"))
                .Catch(NumberFormatException.class)
                .evaluate();

        Assert.assertTrue(sa instanceof Result.Err<?, ?>);
        Assert.assertTrue(((Result.Err<?, ?>) sa).error() instanceof NumberFormatException);

        Result<Integer, Throwable> success = FuncTry
                .Try(() -> Integer.parseInt("19"))
                .Catch(NumberFormatException.class)
                .evaluate();

        Assert.assertTrue(success instanceof Result.Ok<?, ?>);
        Assert.assertEquals(19, (int) ((Result.Ok<Integer, ?>) success).success());

        try {
            FuncTry
                    .Try(() -> Integer.parseInt("19a"))
                    .Catch(NullPointerException.class)
                    .evaluate();

            throw new AssertionError();
        } catch (NumberFormatException ignored) {
        }
    }
}
