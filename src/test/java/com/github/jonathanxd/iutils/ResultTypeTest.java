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

import com.github.jonathanxd.iutils.function.combiner.Combiners;
import com.github.jonathanxd.iutils.object.Pair;
import com.github.jonathanxd.iutils.object.result.Result;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Objects;

public class ResultTypeTest {

    @Test
    public void resultTypeTest() {
        Result<byte[], Fail> readFile = readFile("hello.txt");

        Assert.assertTrue(readFile instanceof Result.Err<?, ?>);

        Result<byte[], Fail> recover = readFile.recover(() -> new byte[0]);

        Assert.assertTrue(recover instanceof Result.Ok<?, ?>);
        Assert.assertTrue(Objects.deepEquals(new byte[0], recover.successOrNull()));

        Result<String, Fail> map = recover.map(bytes -> new String(bytes, Charset.forName("UTF-8")), e -> e);

        Assert.assertEquals("", map.successOrNull());
    }

    private Result<byte[], Fail> readFile(String file) {
        return Result.Err(new PermissionDenied());
    }

    @Test
    public void combineSuccessAndError_Success() {
        Result<String, Throwable> result = Result.ok("Hello");
        Result<String, Throwable> result2 = Result.ok("Hello 2");

        Object combineSuccess = result.combineSuccess(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Object combineError = result.combineError(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(Pair.of("Hello", "Hello 2"), combineSuccess);
        Assert.assertEquals("Hello", combineError);
    }

    @Test
    public void combineSuccessAndError_SuccessAndError() {
        RuntimeException runtimeException = new RuntimeException();
        Result<String, Throwable> result = Result.ok("Hello");
        Result<String, Throwable> result2 = Result.error(runtimeException);

        Object combineSuccess = result.combineSuccess(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Object combineError = result.combineError(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(Pair.of("Hello", null), combineSuccess);
        Assert.assertEquals(Pair.of(null, runtimeException), combineError);
    }

    @Test
    public void combineSuccessAndError_ErrorAndError() {
        RuntimeException runtimeException = new RuntimeException();
        Result<String, Throwable> result = Result.error(runtimeException);
        Result<String, Throwable> result2 = Result.error(runtimeException);

        Object combineSuccess = result.combineSuccess(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Object combineError = result.combineError(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(runtimeException, combineSuccess);
        Assert.assertEquals(Pair.of(runtimeException, runtimeException), combineError);
    }

    @Test
    public void combineSuccessAndError_ErrorAndSuccess() {
        RuntimeException runtimeException = new RuntimeException();
        Result<String, Throwable> result = Result.error(runtimeException);
        Result<String, Throwable> result2 = Result.ok("Hello 2");

        Object combineSuccess = result.combineSuccess(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Object combineError = result.combineError(result2, Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(Pair.of(null, "Hello 2"), combineSuccess);
        Assert.assertEquals(Pair.of(runtimeException, null), combineError);
    }

    @Test
    public void combine_Success() {
        Result<String, Throwable> result = Result.ok("Hello");
        Result<String, Throwable> result2 = Result.ok("Hello 2");

        Object combine = result.combine(result2, Combiners.pair(), Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(Pair.of("Hello", "Hello 2"), combine);
    }

    @Test
    public void combine_SuccessAndError() {
        Result<String, Throwable> result = Result.ok("Hello");
        Result<String, Throwable> result2 = Result.error(new RuntimeException());

        Object combine = result.combine(result2, Combiners.pair(), Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(Pair.of("Hello", null), combine);
    }

    @Test
    public void combine_ErrorAndSuccess() {
        Result<String, Throwable> result = Result.error(new RuntimeException());
        Result<String, Throwable> result2 = Result.ok("Hello 2");

        Object combine = result.combine(result2, Combiners.pair(), Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(Pair.of(null, "Hello 2"), combine);
    }

    @Test
    public void combine_ErrorAndError() {
        RuntimeException runtimeException = new RuntimeException();
        Result<String, Throwable> result = Result.error(runtimeException);
        Result<String, Throwable> result2 = Result.error(runtimeException);

        Object combine = result.combine(result2, Combiners.pair(), Combiners.pair())
                .getSuccessOrError(f -> f, f -> f);

        Assert.assertEquals(Pair.of(runtimeException, runtimeException), combine);
    }

    static class Fail {
    }

    static class PermissionDenied extends Fail {
    }
}
