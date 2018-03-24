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
package com.github.jonathanxd.iutils.function.exception;

import com.github.jonathanxd.iutils.collection.Collections3;
import com.github.jonathanxd.iutils.exception.RethrowException;
import com.github.jonathanxd.iutils.function.checked.supplier.CSupplier;
import com.github.jonathanxd.iutils.object.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Try-catch to be used with {@link Result}. This class provides utility to catch specific
 * exceptions, non-caught exceptions are thrown.
 *
 * Operation is executed only when {@link #evaluate()} is called.
 *
 * Example of use:
 *
 * <pre>
 *     {@code
 *     Result<MyValue, String> result = FuncTry.Try(() -> MyValueParser.parse("5:4"))
 *            .Catch(NullPointerException.class, r -> Result.Err("Provided value is null"))
 *            .Catch(MalformedInputStringException.class, r -> Result.Err("Malformed input string"))
 *            .evaluate()
 *     }
 * </pre>
 *
 * @param <R> Result value type.
 */
public class FuncTry<R, C extends Catch> {

    private final CSupplier<R> supplier;
    private final List<Catch<?, ?>> catchList;

    private FuncTry(CSupplier<R> supplier, List<Catch<?, ?>> catchList) {
        this.supplier = supplier;
        this.catchList = catchList;
    }

    /**
     * Creates an instance of {@link FuncTry} that will try to get value supplied by {@code
     * supplier}.
     *
     * @param supplier Supplier of result value.
     * @param <R>      Result value type.
     */
    public static <R> FuncTry<R, Catch> Try(CSupplier<R> supplier) {
        return new FuncTry<>(supplier, Collections.emptyList());
    }

    /**
     * Catch the {@code exception}.
     *
     * @param exception Exception to catch.
     * @param <E>       Exception type.
     * @return A new {@link FuncTry} that catches {@code exception}.
     */
    @SuppressWarnings("unchecked")
    public <E extends Throwable> FuncTry<R, Catch<E, C>> Catch(Class<E> exception) {
        return this.Catch(exception, r -> Result.error(((Result.Err) r).error()));
    }

    /**
     * Catch the {@code exception}.
     *
     * @param exception   Exception to catch.
     * @param transformer Transformer of {@link Result.Err error result} to another {@link Result
     *                    result}.
     * @param <E>         Exception type.
     * @return A new {@link FuncTry} that catches {@code exception}.
     */
    @SuppressWarnings("unchecked")
    public <E extends Throwable> FuncTry<R, Catch<E, C>> Catch(Class<E> exception,
                                                               Function<Result<R, E>, Result<R, ?>> transformer) {
        return new FuncTry<>(this.supplier, Collections3.append(new Catch(exception, transformer), this.catchList));
    }

    /**
     * Evaluates this {@code Try-Catch} function.
     *
     * @param <E> Expected type of exception.
     * @return Result of evaluation (either success result or error result with one of caught exception).
     */
    @SuppressWarnings("unchecked")
    public <E> Result<R, E> evaluate() {
        try {
            return Result.ok(supplier.getChecked());
        } catch (Throwable t) {
            for (Catch<?, ?> aCatch : this.catchList) {
                if (aCatch.getException().isInstance(t)) {
                    return (Result<R, E>) ((Catch) aCatch).getTransformer().apply(Result.error(t));
                }
            }

            throw RethrowException.rethrow(t);
        }
    }
}
