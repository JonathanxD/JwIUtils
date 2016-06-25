package com.github.jonathanxd.iutils.bistream;

import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.stream.MapStream;
import com.github.jonathanxd.iutils.map.MapUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by jonathan on 25/06/16.
 */
public class BiStreamTest {

    @Test
    public void biStream() {


        Map<String, Integer> myMap = MapUtils.mapOf("A:1", 1, "B:2", 2, "C:1", 1, "D:3", 3);

        Map<String, Integer> collect = MapStream.of(myMap)
                .filter((s, integer) -> integer == 1)
                .collect(BiCollectors.toMap());

        System.out.println(collect);

        Assert.assertEquals(2, collect.size());
    }
}
