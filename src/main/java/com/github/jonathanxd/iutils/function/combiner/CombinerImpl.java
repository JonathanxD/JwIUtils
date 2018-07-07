package com.github.jonathanxd.iutils.function.combiner;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class CombinerImpl<F, S, C> implements Combiner<F, S, C> {

    private final BiFunction<@NotNull F, @NotNull S, @NotNull C> bothCombiner;
    private final Function<@NotNull F, @NotNull C> firstCombiner;
    private final Function<@NotNull S, @NotNull C> secondCombiner;

    public CombinerImpl(BiFunction<@NotNull F, @NotNull S, @NotNull C> bothCombiner,
                        Function<@NotNull F, @NotNull C> firstCombiner,
                        Function<@NotNull S, @NotNull C> secondCombiner) {
        this.bothCombiner = bothCombiner;
        this.firstCombiner = firstCombiner;
        this.secondCombiner = secondCombiner;
    }

    @Override
    public C combine(F f, S s) {
        if ((f == null) != (s == null) || (f == null)) {
            throw new IllegalArgumentException("Both arguments must be either null or not null.");
        } else {
            return bothCombiner.apply(f, s);
        }
    }

    @Override
    public C combineFirst(F f) {
        if (f == null) {
            return null;
        } else {
            return this.firstCombiner.apply(f);
        }
    }

    @Override
    public C combineSecond(S s) {
        if (s == null) {
            return null;
        } else {
            return this.secondCombiner.apply(s);
        }
    }
}
