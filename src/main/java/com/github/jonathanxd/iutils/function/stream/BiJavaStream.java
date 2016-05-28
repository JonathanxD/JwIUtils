/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
import com.github.jonathanxd.iutils.function.function.ToBiFunction;
import com.github.jonathanxd.iutils.function.stream.walkable.WalkableNodeBiStream;
import com.github.jonathanxd.iutils.object.Bi;
import com.github.jonathanxd.iutils.object.Node;

import java.util.stream.Stream;

/**
 * Created by jonathan on 28/05/16.
 */

/**
 *
 * @param <O> Original Type
 * @param <T> New Type[1]
 * @param <U> New Type[2]
 */
public class BiJavaStream<O, T, U> extends WalkableNodeBiStream<T, U> {

    private BiJavaStream(Stream<O> stream, ToBiFunction<O, T, U> toBiFunction) {
        super(Walkable.fromStream(stream).map(o -> {
            Bi<T, U> apply = toBiFunction.apply(o);

            return new Node<>(apply._1(), apply._2());
        }));
    }

    public static <T, R1, R2> BiJavaStream<T, R1, R2> fromJavaStream(Stream<T> stream, ToBiFunction<T, R1, R2> toBiFunction) {
        return new BiJavaStream<>(stream, toBiFunction);
    }

}
