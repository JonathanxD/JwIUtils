/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.extra.SetOf;
import com.github.jonathanxd.iutils.function.collector.BiCollector;
import com.github.jonathanxd.iutils.function.collector.BiCollectors;
import com.github.jonathanxd.iutils.function.stream.walkable.WalkableNodeBiStream;
import com.github.jonathanxd.iutils.object.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 05/03/16.
 */
public class TestWalkable {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();

        map.put("foo", 595);
        map.put("bar", 304);
        map.put("wiz", 480);
        map.put("viz", 755);
        map.put("diz", 32);
        map.put("niz", 895);

        WalkableNodeBiStream<String, Integer> biStream = new WalkableNodeBiStream<>(Walkable.asList(map));

        biStream.sorted((key, value, key2, value2) -> Integer.compare(value, value2)).forEach((key, value) -> {
            System.out.println(key+" = "+value);
        });
        /*
        biStream.reduceTwo("", 0, (key, value, key2, value2) -> new Node<>(key + "&" + key2, value + value2));

        biStream.reduceMixed(new ArrayList<>(), 0, (value, value2, value3, value4) -> {
            value.add(value3);
            return value2 + value4;
        });*/

        int amount = biStream.reduceSecond("", 0, (value, key, value2, key2) -> value + value2);

        System.out.println(map);

        System.out.println("Sum: "+amount);

        WalkableNodeBiStream<String, Integer> biStream2 = new WalkableNodeBiStream<>(Walkable.asList(map));

        StringBuilder firstE = biStream2.collectFirst(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        System.out.println("first: "+firstE.toString());


        WalkableNodeBiStream<String, Integer> walkableBistream = new WalkableNodeBiStream<>(Walkable.asList(map));
        LinkedHashMap<String, Integer> otherMap = walkableBistream.collect(BiCollectors.toMap(LinkedHashMap::new));

        System.out.println("Other map: "+otherMap);


    }

}
