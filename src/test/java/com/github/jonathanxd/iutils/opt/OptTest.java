/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.opt;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.object.Lazy;
import com.github.jonathanxd.iutils.opt.specialized.OptBoolean;
import com.github.jonathanxd.iutils.opt.specialized.OptByte;
import com.github.jonathanxd.iutils.opt.specialized.OptDouble;
import com.github.jonathanxd.iutils.opt.specialized.OptLazy;
import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class OptTest {

    @Test
    public void optTest() {
        Opt<OptBoolean> abcd = op("aabbc")
                .$or(() -> op("ccddaa"));

        OptObject<String> s = abcd.cast()
                .flatMapTo(value -> value ? OptObject.optObjectNotNull("TRUE") : OptObject.optObjectNotNull("FALSE"), OptObject::none);


        Assert.assertTrue(abcd.isPresent());
        Assert.assertTrue(s.isPresent());
    }

    @Test
    public void optTest2() {
        OptObject.optObjectNotNull("Here")
                .ifPresent(s -> Assert.assertEquals("Here", s), () -> {
                    throw new AssertionError();
                });
    }

    @Test
    public void optTest3() {
        OptObject.optObjectNotNull("Here")
                .filter(s -> s.length() > 5)
                .ifPresent(s -> {
                    throw new AssertionError();
                }, () -> {
                });

        OptObject.optObjectNotNull("Here")
                .filter(s -> s.length() > 3)
                .ifPresent(s -> Assert.assertEquals("Here", s), () -> {
                    throw new AssertionError();
                });
    }

    @Test
    public void optTest4() {
        OptObject<String> test = test(4)
                .or(() -> test(99))
                .or(() -> test(110))
                .or(() -> test(250));

        Assert.assertEquals("110", test.getValue());

        test.flatMap(s -> OptObject.$try(() -> Integer.parseInt(s)));
    }

    @Test
    public void optTest5() {
        OptDouble test = testDouble(16.9)
                .or(() -> testDouble(50.0))
                .or(() -> testDouble(99.9999999999)
                .or(() -> testDouble(100.0)));

        Assert.assertTrue(test.equals(OptDouble.optDouble(100.0)));
        Assert.assertEquals(100.0, test.getValue(), 0D);
    }

    private OptDouble testDouble(double doub) {
        return doub >= 100.0 ? OptDouble.optDouble(doub) : OptDouble.none();
    }

    @Test
    public void optTestLazy() {

        OptLazy<List<String>> laz = measure("Lazie", () -> OptLazy.optLazy(this.getText(1))
                .or(() -> OptLazy.optLazy(getText(2)))
                .or(() -> OptLazy.optLazy(getText(3)))
        );

        OptLazy<List<String>> lazie = laz.map(strings -> {
            strings.add("^EOF");
            return strings;
        });

        Assert.assertFalse(lazie.isEvaluated());
        System.out.println(lazie.getValue()); // Evaluated here
        Assert.assertTrue(lazie.isEvaluated());

    }

    public OptObject<String> test(int i) {
        return i > 100 ? OptObject.optObjectNotNull(String.valueOf(i)) : OptObject.none();
    }

    private <T> T measure(String name, Supplier<T> supplier) {
        Instant instant = Instant.now();
        T get = supplier.get();
        System.out.println("[" + name + "] took: " + Duration.between(instant, Instant.now()).toMillis() + "ms");
        return get;
    }

    public Lazy<List<String>> getText(int v) {
        switch (v) {
            case 1: {
                return Lazy.lazy(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }

                    return Collections3.listOf("0", "1");
                });
            }
            case 2: {
                return Lazy.lazy(() -> {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }

                    return Collections3.listOf("0", "1", "2");
                });
            }
            case 3: {
                return Lazy.lazy(() -> {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }

                    return Collections3.listOf("0", "1", "2", "3");
                });
            }
            default:
                return Lazy.evaluated(Collections.emptyList());
        }

    }


    public Opt<OptBoolean> op(String s) {
        char[] charArray = s.toCharArray();

        for (int i = 0; i < charArray.length; i += 2) {
            if (i + 1 >= charArray.length)
                return OptBoolean.none();

            if (charArray[i] != charArray[i + 1])
                return OptBoolean.falseOpt();
        }

        return OptBoolean.trueOpt();

    }
}
