/*
 *      JwIUtils-specializations - Specializations of JwIUtils types <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.test.opt;

import com.github.jonathanxd.iutils.opt.Opt;
import com.github.jonathanxd.iutils.opt.OptObject;
import com.github.jonathanxd.iutils.opt.specialized.OptBoolean;
import com.github.jonathanxd.iutils.opt.specialized.OptDouble;

import org.junit.Assert;
import org.junit.Test;

public class SpecializedOptTest {

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
    public void optTest5() {
        OptDouble test = testDouble(16.9)
                .or(() -> testDouble(50.0))
                .or(() -> testDouble(99.9999999999)
                        .or(() -> testDouble(100.0)));

        Assert.assertEquals(OptDouble.optDouble(100.0), test);
        Assert.assertEquals(100.0, test.getValue(), 0D);
    }

    private OptDouble testDouble(double doub) {
        return doub >= 100.0 ? OptDouble.optDouble(doub) : OptDouble.none();
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
