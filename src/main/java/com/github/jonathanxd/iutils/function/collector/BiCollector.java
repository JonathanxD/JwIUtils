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
package com.github.jonathanxd.iutils.function.collector;

import com.github.jonathanxd.iutils.function.consumer.TriConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by jonathan on 05/03/16.
 */
public interface BiCollector<T, U, A, R> {

    static <T, U, R> BiCollector<T, U, R, R> of(Supplier<R> supplier, TriConsumer<R, T, U> accumulator, BinaryOperator<R> combiner) {
        return new BiCollectors.CommonBiCollector<>(supplier, accumulator, combiner);
    }

    static <T, U, A, R> BiCollector<T, U, A, R> of(Supplier<A> supplier, TriConsumer<A, T, U> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher) {
        return new BiCollectors.CommonBiCollector<>(supplier, accumulator, combiner, finisher);
    }

    Supplier<A> supplier();

    TriConsumer<A, T, U> accumulator();

    BinaryOperator<A> combiner();

    Function<A, R> finisher();


}
