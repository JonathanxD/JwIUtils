package com.github.jonathanxd.iutils.function.combiner;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.object.Pair;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class Combiners {

    public static <F, S, C> Combiner<F, S, C> biFunctionAsCombiner(BiFunction<@Nullable F, @Nullable S, C> successCombiner) {
        return new Combiner<F, S, C>() {
            @Override
            public C combine(F f, S s) {
                return successCombiner.apply(f, s);
            }

            @Override
            public C combineFirst(F f) {
                return successCombiner.apply(f, null);
            }

            @Override
            public C combineSecond(S s) {
                return successCombiner.apply(null, s);
            }
        };
    }

    public static <F, S> Combiner<F, S, Pair<@Nullable F, @Nullable S>> pair() {
        return new CombinerImpl<>(Pair::of, f -> Pair.of(f, null), s -> Pair.of(null, s));
    }

    public static <F extends B, S extends B, B> Combiner<F, S, List<B>> list() {
        return new CombinerImpl<>((f, s) -> Collections3.listOf(f, s), Collections::singletonList, Collections::singletonList);
    }

}
