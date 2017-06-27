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
package com.github.jonathanxd.iutils.matching;

import com.github.jonathanxd.iutils.opt.specialized.OptObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Simple "functional pattern matching" system.
 *
 * @param <K> Type of input value.
 */
public interface When<K> {
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
     * Tests {@code cases} against {@code value} and return first result.
     *
     * @param value Value to test against case clauses.
     * @param cases Case clauses.
     * @param <K>   Value type.
     * @param <R>   Result type.
     * @return First result of first matching {@code case} clause.
     */
    @SafeVarargs
    static <K, R> OptObject<R> When(K value, Case<K, R>... cases) {
        return new Impl<>(value, cases).evaluate();
    }

    /**
     * Tests {@code cases} against {@code value} and return first result.
     *
     * @param mode  Mode of matching.
     * @param value Value to test against case clauses.
     * @param cases Case clauses.
     * @param <K>   Value type.
     * @param <R>   Result type.
     * @return First result of first matching {@code case} clause.
     */
    @SafeVarargs
    static <K, R> OptObject<R> When(int mode, K value, Case<K, R>... cases) {
        return new Impl<>(value, cases, mode).evaluate();
    }

    /**
     * Calls {@code ifMatches} with {@code receiver} as parameter if {@code receiver} matches
     * {@code predicate}.
     *
     * @param predicate Predicate to call.
     * @param ifMatches Function to call with receiver.
     */
    static <K, R> Case<K, R> Matches(Predicate<K> predicate, Function<K, R> ifMatches) {
        return new MatchesCase<>(predicate, ifMatches);
    }

    /**
     * Calls {@code ifMatches} with {@code receiver} as parameter if {@code receiver} matches
     * {@code obj}.
     *
     * @param obj       Object to compare.
     * @param ifMatches Function to call with receiver.
     */
    static <K, R> Case<K, R> EqualsTo(K obj, Function<K, R> ifMatches) {
        return new EqualsCase<>(obj, ifMatches);
    }

    /**
     * Calls {@code ifMatches} with {@code receiver} as parameter if {@code receiver} is
     * instance of {@code type}.
     *
     * @param type      Type to check instance.
     * @param ifMatches Function to call with receiver.
     */
    static <K, R> Case<K, R> InstanceOf(Class<?> type, Function<K, R> ifMatches) {
        return new InstanceOfCase<>(type, ifMatches);
    }

    /**
     * Calls {@code ifMatches} with {@code receiver} as parameter if no one case was matched in
     * {@link When} context.
     *
     * @param caseElse Function to call with receiver.
     */
    static <K, R> Case<K, R> Else(Function<K, R> caseElse) {
        return new ElseCase<>(caseElse);
    }

    /**
     * Gets the value to check if matches predicates.
     *
     * @return value to check if matches predicates.
     */
    K getValue();

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

    final class Impl<K, R> implements When<K> {


        private final K value;
        private final List<Case<K, R>> cases;
        private final int mode;
        private Case<K, R> elseCase;

        Impl(K value, Case<K, R>[] cases) {
            this(value, cases, SHORT_CIRCUIT);
        }

        Impl(K value, Case<K, R>[] cases, int mode) {
            this.value = value;
            this.cases = Arrays.stream(cases)
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

        @Override
        public K getValue() {
            return this.value;
        }

        public OptObject<R> evaluate() {
            OptObject<R> result = OptObject.none();

            for (Case<K, R> aCase : this.cases) {
                OptObject<R> evaluate = aCase.evaluate(this.value);
                if (evaluate.isPresent()
                        && (!result.isPresent() || this.mode == LAST)) {
                    result = evaluate;

                    if (this.mode == SHORT_CIRCUIT)
                        return result;
                }
            }

            if (!result.isPresent() && this.elseCase != null)
                return this.elseCase.evaluate(this.value);

            return result;
        }
    }

}

