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
package com.github.jonathanxd.iutils.function.collector;

import com.github.jonathanxd.iutils.collection.Collections3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Collectors3 {

    /**
     * Creates a split collector. Collect stream elements with {@code max} of elements per list.
     *
     * @param amount Max amount of elements of each list.
     * @param <T>    Type of elements.
     * @return Batch collector.
     */
    @SuppressWarnings("Convert2MethodRef")
    public static <T> Collector<T, ?, List<List<T>>> split(int amount) {
        return Collectors3.split(
                () -> new ArrayList<List<T>>(),
                ArrayList::new,
                amount
        );
    }

    /**
     * Creates a split collector. Collect stream elements with {@code max} of elements per list.
     *
     * @param listOfListFactory Factory of list of lists.
     * @param listFactory       Factory list, input {@code int} is the {@code max} value.
     * @param max               Max of elements of each list.
     * @param <T>               Type of elements.
     * @return Batch collector.
     */
    public static <T> Collector<T, ?, List<List<T>>> split(Supplier<List<List<T>>> listOfListFactory,
                                                           IntFunction<List<T>> listFactory,
                                                           int max) {
        return new SplitCollector<>(listOfListFactory, listFactory, max);
    }


    private static final class SplitCollector<T> implements Collector<T, List<List<T>>, List<List<T>>> {

        private final Supplier<List<List<T>>> listOfListFactory;
        private final Supplier<List<T>> factory;
        private final int size;

        private SplitCollector(Supplier<List<List<T>>> listOfListFactory, IntFunction<List<T>> factory, int size) {
            this.listOfListFactory = listOfListFactory;
            this.factory = () -> factory.apply(size);
            this.size = size;
        }

        @Override
        public Supplier<List<List<T>>> supplier() {
            return listOfListFactory;
        }

        @Override
        public BiConsumer<List<List<T>>, T> accumulator() {
            return (l, v) -> {
                if (l.isEmpty() || l.get(l.size() - 1).size() == size) {
                    l.add(this.factory.get());
                }

                List<T> last = l.get(l.size() - 1);

                last.add(v);
            };
        }

        @Override
        public BinaryOperator<List<List<T>>> combiner() {
            return (f1, f2) -> {
                f1.addAll(f2);
                return f1;
            };
        }

        @Override
        public Function<List<List<T>>, List<List<T>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections3.setOf(Characteristics.IDENTITY_FINISH);
        }
    }
}
