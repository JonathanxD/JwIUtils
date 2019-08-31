/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.test.either;

import com.github.jonathanxd.iutils.object.BaseEither;
import com.github.jonathanxd.iutils.object.Either;
import com.github.jonathanxd.iutils.object.Try;
import com.github.jonathanxd.iutils.object.specialized.EitherObjInt;
import com.github.jonathanxd.iutils.object.specialized.all.EitherIntDouble;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class SpecializedEitherTest {
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
    public void testObjPrimEither() {
        EitherObjInt<String> e =
                EitherObjInt.right(9);

        EitherObjInt<String> e2 =
                EitherObjInt.left("Y");

        assertRight(e, o -> Assert.assertEquals(9, (int) o));
        assertLeft(e2, o -> Assert.assertEquals("Y", o));
    }

    @Test
    public void testPrimEither() {
        EitherIntDouble e =
                EitherIntDouble.right(9.9);

        EitherIntDouble e2 =
                EitherIntDouble.left(5);

        assertRight(e, o -> Assert.assertEquals(9.9, (double) o, 0.0));
        assertLeft(e2, o -> Assert.assertEquals(5, (int) o));

        EitherIntDouble map = e2.map(v -> v * 9, v -> v * 5.1);
        assertLeft(map, o -> Assert.assertEquals(5 * 9, (int) o));

        EitherIntDouble e3 = map
                .flatMap(value -> value % 2 == 0 ? EitherIntDouble.right(5.0) : EitherIntDouble.left(9),
                        value -> value % 2 == 0 ? EitherIntDouble.left(7) : EitherIntDouble.right(1.0));

        assertLeft(e3, o -> Assert.assertEquals(9, (int) o));

    }

}
