/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2019 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.exception;

/**
 * Rethrow exception. Does not hold stack trace of this instance.
 */
public class RethrowException extends RuntimeException {

    public RethrowException(Throwable cause) {
        super(cause.toString(), cause.getCause());

        for (Throwable throwable : cause.getSuppressed()) {
            this.addSuppressed(throwable);
        }

        this.setStackTrace(cause.getStackTrace());
    }

    public RethrowException(Throwable cause, Throwable rootCause) {
        super(cause.toString(), rootCause);

        for (Throwable throwable : cause.getSuppressed()) {
            this.addSuppressed(throwable);
        }

        this.setStackTrace(cause.getStackTrace());

    }


    /**
     * Rethrow exception {@code t} as unchecked.
     */
    @SuppressWarnings("unchecked")
    public static <X extends Throwable> RuntimeException rethrow(Throwable t) throws X {
        throw (X) t;
    }

    /**
     * Rethrow cause exception {@code t} as unchecked. If the cause exception is {@code null}, rethrow the exception.
     */
    @SuppressWarnings("unchecked")
    public static <X extends Throwable> RuntimeException rethrowCause(Throwable t) throws X {
        Throwable cause = t.getCause();

        if (cause != null)
            throw (X) cause;

        throw (X) t;
    }

}