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
package com.github.jonathanxd.iutils.bistream;

import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.function.stream.MapStream;
import com.github.jonathanxd.iutils.map.MapUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class BiStreamTest {

    @Test
    public void biStream() {


        Map<String, Integer> myMap = MapUtils.mapOf("A:1", 1, "B:2", 2, "C:1", 1, "D:3", 3);

        Map<String, Integer> collect = BiStreams.mapStream(myMap)
                .filter((s, integer) -> integer == 1)
                .collect(BiCollectors.toMap());

        Assert.assertEquals(2, collect.size());

        Assert.assertTrue(BiStreams.mapStream(Collections.emptyMap()).count() == 0);

        Map<String, Integer> collect1 = BiStreams.mapStream(myMap).filter((s, integer) -> integer < 0).collect(BiCollectors.toMap());

        Assert.assertTrue(collect1.isEmpty());
    }
}
