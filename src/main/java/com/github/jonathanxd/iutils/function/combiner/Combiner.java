package com.github.jonathanxd.iutils.function.combiner;

import org.jetbrains.annotations.Contract;

public interface Combiner<F, S, C> {

    /**
     * Combines {@code f} and {@code s} into an object of type {@link C}.
     *
     * @param f First object to combine.
     * @param s Second object to combine.
     * @return Combined instance of {@code f} and {@code s}.
     */
    @Contract("null, null -> null; _, null -> fail; null, _ -> fail; !null, !null -> !null")
    C combine(F f, S s);

    /**
     * Combines {@code f} into an object of type {@link C}.
     *
     * @param f Object to combine.
     * @return Combined instance of {@code f}.
     */
    @Contract("null -> null; !null -> !null")
    C combineFirst(F f);

    /**
     * Combines {@code s} into an object of type {@link C}.
     *
     * @param s Object to combine.
     * @return Combined instance of {@code s}.
     */
    @Contract("null -> null; !null -> !null")
    C combineSecond(S s);
}
