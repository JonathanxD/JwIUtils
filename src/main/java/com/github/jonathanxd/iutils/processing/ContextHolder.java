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
package com.github.jonathanxd.iutils.processing;

/**
 * Context holder. Holds context instance, enter stack elements exit stack elements.
 */
public class ContextHolder {

    /**
     * Context instance.
     */
    private final Object context;

    /**
     * Enter stack trace.
     */
    private final StackTraceElement[] enterTrace;

    /**
     * Exit stack trace. (May be null)
     */
    private StackTraceElement[] exitTrace;

    public ContextHolder(Object context, StackTraceElement[] enterTrace, StackTraceElement[] exitTrace) {
        this.context = context;
        this.enterTrace = enterTrace;
        this.exitTrace = exitTrace;
    }

    /**
     * Gets context instance.
     * @return Context instance.
     */
    public Object getContext() {
        return this.context;
    }

    /**
     * Returns true if exited.
     * @return True if exited.
     */
    public boolean isExited() {
        return this.exitTrace != null;
    }

    /**
     * Gets the copy of enter call stack elements.
     * @return Copy of enter call stack elements.
     */
    public StackTraceElement[] getEnterTrace() {
        return this.enterTrace.clone();
    }

    /**
     * Gets the copy of exit call stack elements (may be null).
     * @return Copy of exit call stack elements (may be null).
     */
    public StackTraceElement[] getExitTrace() {
        return this.exitTrace == null ? null : this.exitTrace.clone();
    }

    /**
     * Exits the context.
     * @param exitTrace Exit call stack trace.
     * @return Same context with exit state.
     */
    ContextHolder exit(StackTraceElement[] exitTrace) {

        if (this.isExited())
            return this;

        this.exitTrace = exitTrace;

        return this;
    }
}
