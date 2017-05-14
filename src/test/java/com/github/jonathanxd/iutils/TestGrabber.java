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

import com.github.jonathanxd.iutils.array.PrimitiveArrayConverter;
import com.github.jonathanxd.iutils.collection.CollectionUtils;
import com.github.jonathanxd.iutils.grabber.Grabber;
import com.github.jonathanxd.iutils.grabber.ListGrabber;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGrabber {

    static List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

    @Test
    public void testGrabber() {
        Grabber<Integer> grabber = new ListGrabber<>(integers);

        grabber.foreachRemaining(5, System.out::println);

        Grabber<Integer> g = grabber.createClone();

        Grabber<String> map = g.mapAll(String::valueOf);

        Assert.assertEquals("8", map.grab(7));

        Assert.assertEquals("[-(1, 2, 3, 4, 5), 6, 7, -(8), 9, 10]", map.toString());

        Grabber<Integer> cloned = grabber.createClone();

        List<Integer> integers = grabber.collectRemainingToList();

        Assert.assertEquals(CollectionUtils.listOf(6, 7, 8, 9, 10), integers);

        int[] array = PrimitiveArrayConverter.toPrimitive(cloned.collectRemainingToArray(Integer[]::new));

        Assert.assertArrayEquals(new int[]{6, 7, 8, 9, 10}, array);


    }


}
