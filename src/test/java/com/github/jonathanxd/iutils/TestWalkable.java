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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.stream.walkable.WalkableNodeBiStream;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestWalkable {

    @Test
    public void test() {

        Map<String, Integer> map = new HashMap<>();

        map.put("foo", 595);
        map.put("bar", 304);
        map.put("wiz", 480);
        map.put("viz", 755);
        map.put("diz", 32);
        map.put("niz", 895);

        WalkableNodeBiStream<String, Integer> biStream = new WalkableNodeBiStream<>(Walkable.fromList(map));

        biStream.sorted((key, value, key2, value2) -> Integer.compare(value, value2)).forEach((key, value) -> {
            System.out.println(key+" = "+value);
        });

        int amount = biStream.reduceSecond("", 0, (value, key, value2, key2) -> value + value2);

        Assert.assertEquals(3061, amount);

        WalkableNodeBiStream<String, Integer> biStream2 = new WalkableNodeBiStream<>(Walkable.fromList(map));

        StringBuilder firstE = biStream2.collectKey(StringBuilder::new, StringBuilder::append);

        Assert.assertEquals("bardizvizfoowizniz", firstE.toString());

        WalkableNodeBiStream<String, Integer> walkableBistream = new WalkableNodeBiStream<>(Walkable.fromList(map));
        LinkedHashMap<String, Integer> otherMap = walkableBistream.collect(BiCollectors.toMap(LinkedHashMap::new));

        Assert.assertEquals(map, otherMap);


    }

}
