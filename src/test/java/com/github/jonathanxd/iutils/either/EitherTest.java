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
package com.github.jonathanxd.iutils.either;

import com.github.jonathanxd.iutils.object.BaseEither;
import com.github.jonathanxd.iutils.object.Either;
import com.github.jonathanxd.iutils.object.Try;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class EitherTest {

    private static <T> void assertLeft(BaseEither either, Consumer<T> test) {
        Assert.assertFalse(either.isRight());
        Assert.assertTrue(either.isLeft());
        assert_(either, test);
    }

    private static <T> void assertRight(BaseEither either, Consumer<T> test) {
        Assert.assertTrue(either.isRight());
        Assert.assertFalse(either.isLeft());
        assert_(either, test);
    }

    @SuppressWarnings("unchecked")
    private static <T> void assert_(BaseEither either,
                                    Consumer<T> test) {
        try {

            String nName;
            String fName;

            if (either.isLeft()) {
                nName = "getLeft";
                fName = "getRight";
            } else if (either.isRight()) {
                nName = "getRight";
                fName = "getLeft";
            } else {
                throw new IllegalStateException();
            }


            Method func = either.getClass().getEnclosingClass().getDeclaredMethod(nName);
            Method func2 = either.getClass().getEnclosingClass().getDeclaredMethod(fName);

            test.accept((T) func.invoke(either));

            Either<Throwable, Object> aTry =
                    Try.Try(() -> {
                        try {
                            return func2.invoke(either);
                        } catch (InvocationTargetException e) {
                            throw e.getCause();
                        }
                    });

            Assert.assertTrue(aTry.isLeft());

            if (!(aTry.getLeft() instanceof NoSuchElementException))
                throw new RuntimeException(aTry.getLeft());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testObjEither() {

        Either<Exception, String> either =
                Try.TryEx(() -> "Y");

        Either<Exception, String> either2 =
                Try.TryEx(() -> {
                    throw new RuntimeException();
                });


        assertRight(either, o -> Assert.assertEquals("Y", o));

        assertLeft(either2, o -> Assert.assertTrue(o instanceof RuntimeException));
    }


    @Test
    public void testTry() {
        Either<Throwable, String> aTry = Try.Try(() -> {
            throw new IllegalStateException();
        });

        Either<Throwable, String> throwableStringEither = aTry.mapLeftToRight(x -> "x");

        Assert.assertTrue(throwableStringEither.isRight());
        Assert.assertEquals("x", throwableStringEither.getRight());
    }
}
