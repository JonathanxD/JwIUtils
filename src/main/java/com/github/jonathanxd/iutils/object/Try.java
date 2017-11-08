/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.condition.Conditions;
import com.github.jonathanxd.iutils.function.checked.CRunnable;
import com.github.jonathanxd.iutils.function.checked.ERunnable;
import com.github.jonathanxd.iutils.function.checked.function.CFunction;
import com.github.jonathanxd.iutils.function.checked.function.EFunction;
import com.github.jonathanxd.iutils.function.checked.supplier.CSupplier;
import com.github.jonathanxd.iutils.function.checked.supplier.ESupplier;

/**
 * Try utility.
 */
public final class Try {
    private Try() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Try to run function {@code f}.
     *
     * @param f   Function.
     * @param <R> Return type.
     * @return {@link Either} of thrown exception or {@link Either} of result if {@code f} succeed
     * without exceptions.
     */
    public static <R> Either<Throwable, R> Try(CSupplier<R> f) {
        try {
            return Either.right(f.getChecked());
        } catch (Throwable t) {
            return Either.left(t);
        }
    }

    /**
     * Try to run function {@code f} on {@code value}.
     *
     * @param value Value to apply.
     * @param f     Function.
     * @param <R>   Return type.
     * @return {@link Either} of thrown exception or {@link Either} of result if {@code f} succeed
     * without exceptions.
     */
    public static <T, R> Either<Throwable, R> TryOn(T value, CFunction<T, R> f) {
        try {
            return Either.right(f.applyChecked(value));
        } catch (Throwable t) {
            return Either.left(t);
        }
    }

    /**
     * Try to run function {@code f}.
     *
     * @param f Function.
     * @return {@link Either} of thrown exception or {@link Either} of {@code null}.
     */
    public static Either<Throwable, Void> Try(CRunnable f) {
        try {
            f.runChecked();
            return Either.right(null);
        } catch (Throwable t) {
            return Either.left(t);
        }
    }

    /**
     * Try to run function {@code f}.
     *
     * @param f   Function.
     * @param <R> Return type.
     * @return {@link Either} of thrown {@link Exception} or {@link Either} of result if {@code f}
     * succeed without exceptions.
     */
    public static <R> Either<Exception, R> TryEx(ESupplier<R> f) {
        try {
            return Either.right(f.getChecked());
        } catch (Exception t) {
            return Either.left(t);
        }
    }

    /**
     * Try to run function {@code f} on {@code value}.
     *
     * @param value Value to apply.
     * @param f     Function.
     * @param <R>   Return type.
     * @return {@link Either} of thrown {@link Exception} or {@link Either} of result if {@code f}
     * succeed without exceptions.
     */
    public static <T, R> Either<Exception, R> TryOn(T value, EFunction<T, R> f) {
        try {
            return Either.right(f.applyChecked(value));
        } catch (Exception t) {
            return Either.left(t);
        }
    }

    /**
     * Try to run function {@code f}.
     *
     * @param f Function.
     * @return {@link Either} of thrown {@link Exception} or {@link Either} of {@code null}.
     */
    public static Either<Exception, Void> TryEx(ERunnable f) {
        try {
            f.runChecked();
            return Either.right(null);
        } catch (Exception t) {
            return Either.left(t);
        }
    }


    /**
     * Try all suppliers and return first successful, or either left with all exceptions (suppressed).
     *
     * @param suppliers Suppliers.
     * @param <R>       Return type.
     * @return Either of suppressed exceptions or first valid result.
     */
    @SafeVarargs
    public static <R> Either<Exception, R> TryAll(ESupplier<R>... suppliers) {
        Conditions.require(suppliers.length > 0, "Supplier array cannot be empty.");

        Exception e = null;
        for (ESupplier<R> supplier : suppliers) {
            try {
                return Either.right(supplier.getChecked());
            } catch (Exception x) {

                if (e == null)
                    e = x;
                else
                    e.addSuppressed(x);
            }
        }

        return Either.left(e);
    }
}
