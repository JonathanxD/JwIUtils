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
package com.github.jonathanxd.iutils.function.stream;

import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.collections.MapW;
import com.github.jonathanxd.iutils.function.function.ToPairFunction;
import com.github.jonathanxd.iutils.function.stream.walkable.WalkableNodeBiStream;
import com.github.jonathanxd.iutils.object.Node;
import com.github.jonathanxd.iutils.object.Pair;

import java.util.Map;
import java.util.stream.Stream;

public class BiStreams {

    /**
     * Creates a {@link BiJavaStream} from {@code stream}.
     *
     * @param stream Stream to wrap.
     * @param <K>    Key type.
     * @param <V>    Value type.
     * @return {@link BiJavaStream} that delegates operations to {@code stream}.
     */
    public static <K, V> BiStream<K, V> fromJavaStream(Stream<Map.Entry<K, V>> stream) {
        return new BiJavaStream<>(stream);
    }

    /**
     * Creates {@link BiStream} from a {@link Map}.
     *
     * @param map Map.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return {@link BiStream} of {@link Map} nodes.
     */
    public static <K, V> BiStream<K, V> mapStream(Map<K, V> map) {
        return new MapStream<>(map);
    }

    /**
     * Creates a {@link BiStream} from a {@link Stream} values mapped with {@link
     * WalkableNodeBiStream}.
     *
     * @param stream         Stream to map elements.
     * @param toPairFunction Element mapper.
     * @param <O>            Stream value type.
     * @param <K>            Key type.
     * @param <V>            Value type.
     * @return {@link BiStream} from a {@link Stream} values mapped with {@link
     * WalkableNodeBiStream}.
     */
    public static <O, K, V> BiStream<K, V> mapJavaToBiStream(Stream<O> stream, ToPairFunction<O, K, V> toPairFunction) {
        return new WalkableNodeBiStream<>(Walkable.fromStream(stream).map(o -> {
            Pair<K, V> apply = toPairFunction.apply(o);

            return new Node<>(apply.getFirst(), apply.getSecond());
        }));
    }

}
