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
package com.github.jonathanxd.iutils.matching;

import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Simple "functional pattern matching" system.
 *
 * @param <K> Type of input value.
 */
public interface When<K, R> {
    /**
     * Short circuit case evaluation.
     */
    int SHORT_CIRCUIT = 0;

    /**
     * Standard evaluation (evaluates all but only returns first valid result).
     */
    int STANDARD = 1;

    /**
     * Standard evaluation (evaluates all but only returns last valid result).
     */
    int LAST = 2;

    /**
     * Creates a {@link When pattern matching instance} that test {@code value} against {@code
     * cases}.
     *
     * @param value Value to test.
     * @param cases Case clauses.
     * @param <K>   Value type.
     * @param <R>   Result type.
     * @return {@link When pattern matching instance} that test {@code value} against {@code cases}
     */
    @SafeVarargs
    static <K, R> When<K, R> When(K value, Case<K, R>... cases) {
        return new Impl<>(() -> value, cases);
    }

    /**
     * Creates a {@link When pattern matching instance} that test {@code value} against {@code
     * cases}.
     *
     * @param mode  Mode of matching.
     * @param value Value to test.
     * @param cases Case clauses.
     * @param <K>   Value type.
     * @param <R>   Result type.
     * @return {@link When pattern matching instance} that test {@code value} against {@code cases}
     */
    @SafeVarargs
    static <K, R> When<K, R> When(int mode, K value, Case<K, R>... cases) {
        return new Impl<>(() -> value, cases, mode);
    }

    /**
     * Creates a {@link When pattern matching instance} that test value supplied by {@code
     * valueSupplier} against {@code cases}.
     *
     * @param valueSupplier Supplier of value to test.
     * @param cases         Case clauses.
     * @param <K>           Value type.
     * @param <R>           Result type.
     * @return {@link When pattern matching instance} that test value supplied by {@code
     * valueSupplier} against {@code cases}.
     */
    @SafeVarargs
    static <K, R> When<K, R> WhenSupplied(Supplier<K> valueSupplier, Case<K, R>... cases) {
        return new Impl<>(valueSupplier, cases);
    }

    /**
     * Creates a {@link When pattern matching instance} that test value supplied by {@code
     * valueSupplier} against {@code cases}.
     *
     * @param mode          Mode of matching.
     * @param valueSupplier Supplier of value to test.
     * @param cases         Case clauses.
     * @param <K>           Value type.
     * @param <R>           Result type.
     * @return {@link When pattern matching instance} that test value supplied by {@code
     * valueSupplier} against {@code cases}.
     */
    @SafeVarargs
    static <K, R> When<K, R> WhenSupplied(int mode, Supplier<K> valueSupplier, Case<K, R>... cases) {
        return new Impl<>(valueSupplier, cases, mode);
    }

    /**
     * Static version of {@link When#or(Case[])} to avoid {@code unchecked generics array creation
     * for varargs} warning.
     *
     * @param when  Pattern matching instance.
     * @param cases Conditions.
     * @param <K>   Receiver value type.
     * @param <R>   Result value type.
     * @return New pattern matching instance, see {@link When#or(Case[])}.
     */
    @SafeVarargs
    static <K, R> When<K, R> Or(When<K, R> when, Case<K, R>... cases) {
        return when.or(cases);
    }

    /**
     * Checks if pattern matching receiver matches the {@code predicate}.
     *
     * @param predicate Predicate to test pattern matching receiver.
     * @param ifMatches Function to call if pattern matching receiver matches the {@code
     *                  predicate}.
     */
    static <K, R> Case<K, R> Matches(Predicate<K> predicate, Function<K, R> ifMatches) {
        return new MatchesCase<>(predicate, ifMatches);
    }

    /**
     * Checks if {@code obj} is equal to pattern matching receiver.
     *
     * @param obj       Object to compare.
     * @param ifMatches Function to call if {@code obj} is equal to pattern matching receiver.
     */
    static <K, R> Case<K, R> EqualsTo(K obj, Function<K, R> ifMatches) {
        return new EqualsCase<>(obj, ifMatches);
    }

    /**
     * Checks if pattern matching receiver is instance of {@code type}.
     *
     * @param type      Type to check if value is instance.
     * @param ifMatches Function to be called if pattern matching receiver is instance of {@code
     *                  type}.
     */
    static <K, R> Case<K, R> InstanceOf(Class<?> type, Function<K, R> ifMatches) {
        return new InstanceOfCase<>(type, ifMatches);
    }

    /**
     * Checks if pattern matching receiver does not matches any other case.
     *
     * @param caseElse Function to be called if pattern matching receiver does not matches any other
     *                 case.
     */
    static <K, R> Case<K, R> Else(Function<K, R> caseElse) {
        return new ElseCase<>(caseElse);
    }

    /**
     * Gets the supplier of value to check if matches cases.
     *
     * @return Supplier of value to check if matches cases..
     */
    Supplier<K> getValueSupplier();

    /**
     * Evaluates the pattern matching and returns the result.
     *
     * @return The result of matched pattern case.
     */
    OptObject<R> evaluate();

    /**
     * Returns a new pattern matching with merged {@link Case cases} of current instance and
     * provided {@code cases}.
     *
     * The matching mode still the same, and if an {@link ElseCase else case} is provided, the old
     * will be replaced with the provided.
     *
     * @param cases Cases to merge with {@code this} {@link Case cases}.
     * @return New pattern matching with merged {@link Case cases} of current instance and provided
     * {@code cases}.
     */
    When<K, R> or(Case<K, R>... cases);

    interface Case<K, R> {
        OptObject<R> evaluate(K value);
    }

    class MatchesCase<K, R> implements Case<K, R> {

        private final Predicate<K> predicate;
        private final Function<K, R> ifSuccess;

        public MatchesCase(Predicate<K> predicate, Function<K, R> ifSuccess) {
            this.predicate = predicate;
            this.ifSuccess = ifSuccess;
        }

        @Override
        public OptObject<R> evaluate(K value) {

            if (this.predicate.test(value))
                return OptObject.optObject(this.ifSuccess.apply(value));

            return OptObject.none();
        }
    }

    class ElseCase<K, R> implements Case<K, R> {

        private final Function<K, R> caseElse;

        ElseCase(Function<K, R> caseElse) {
            this.caseElse = caseElse;
        }

        @Override
        public OptObject<R> evaluate(K value) {
            return OptObject.optObject(this.caseElse.apply(value));
        }
    }

    class EqualsCase<K, R> implements Case<K, R> {

        private final K other;
        private final Function<K, R> ifSuccess;

        EqualsCase(K other, Function<K, R> ifSuccess) {
            this.other = other;
            this.ifSuccess = ifSuccess;
        }

        @Override
        public OptObject<R> evaluate(K value) {
            if (value.equals(this.other))
                return OptObject.optObject(this.ifSuccess.apply(value));

            return OptObject.none();
        }
    }

    class InstanceOfCase<K, R> implements Case<K, R> {

        private final Class<?> type;
        private final Function<K, R> ifSuccess;

        InstanceOfCase(Class<?> type, Function<K, R> ifSuccess) {
            this.type = type;
            this.ifSuccess = ifSuccess;
        }

        @Override
        public OptObject<R> evaluate(K value) {
            if (type.isInstance(value))
                return OptObject.optObject(this.ifSuccess.apply(value));

            return OptObject.none();
        }
    }

    final class Impl<K, R> implements When<K, R> {
        private final Supplier<K> valueSupplier;
        private final List<Case<K, R>> cases;
        private final int mode;
        private Case<K, R> elseCase;

        Impl(Supplier<K> valueSupplier, Case<K, R>[] cases) {
            this(valueSupplier, cases, SHORT_CIRCUIT);
        }

        Impl(Supplier<K> valueSupplier, Case<K, R>[] cases, int mode) {
            this(valueSupplier, Arrays.asList(cases), mode);
        }

        Impl(Supplier<K> valueSupplier, List<Case<K, R>> cases, int mode) {
            this.valueSupplier = valueSupplier;
            this.cases = cases.stream()
                    .filter(krCase -> !(krCase instanceof ElseCase))
                    .collect(Collectors.toList());

            for (Case<K, R> aCase : cases) {
                if (aCase instanceof ElseCase) {
                    this.elseCase = aCase;
                    break;
                }
            }

            this.mode = mode;
        }

        @SafeVarargs
        @Override
        public final When<K, R> or(Case<K, R>... cases) {
            List<Case<K, R>> source = new ArrayList<>(this.cases);
            List<Case<K, R>> toMerge = new ArrayList<>(Arrays.asList(cases));
            List<Case<K, R>> merged = new ArrayList<>();

            merged.addAll(source);
            merged.addAll(toMerge);

            return new Impl<>(this.getValueSupplier(), merged, this.mode);
        }

        @Override
        public Supplier<K> getValueSupplier() {
            return this.valueSupplier;
        }

        @Override
        public OptObject<R> evaluate() {
            K value = this.getValueSupplier().get();
            OptObject<R> result = OptObject.none();

            for (Case<K, R> aCase : this.cases) {
                OptObject<R> evaluate = aCase.evaluate(value);
                if (evaluate.isPresent()
                        && (!result.isPresent() || this.mode == LAST)) {
                    result = evaluate;

                    if (this.mode == SHORT_CIRCUIT)
                        return result;
                }
            }

            if (!result.isPresent() && this.elseCase != null)
                return this.elseCase.evaluate(value);

            return result;
        }
    }

}

