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
package com.github.jonathanxd.iutils.function.stream;

import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.function.stream.walkable.WalkableNodeBiStream;

import java.util.Map;

/**
 * Created by jonathan on 05/03/16.
 */
public class MapStream<K, V> extends WalkableNodeBiStream<K, V> {
    public MapStream(Map<K, V> map) {
        super(Walkable.asList(map));
    }

    public static <K, V> BiStream<K, V> of(Map<K, V> map) {
        return new MapStream<>(map);
    }
}
