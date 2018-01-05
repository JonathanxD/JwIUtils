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
package com.github.jonathanxd.iutils.processing;

import java.util.Arrays;

/**
 * Context holder. Holds context instance, enter stack elements exit stack elements.
 */
public class ContextHolder {

    /**
     * Context instance.
     */
    private final Object context;

    /**
     * Contexts on enter
     */
    private final Object[] enterContext;
    /**
     * Enter stack trace.
     */
    private final StackTraceElement[] enterTrace;
    /**
     * Contexts on exit.
     */
    private Object[] exitContext;
    /**
     * Exit stack trace. (May be null)
     */
    private StackTraceElement[] exitTrace;

    public ContextHolder(Object context, StackTraceElement[] enterTrace, StackTraceElement[] exitTrace,
                         Object[] enterContext, Object[] exitContext) {
        this.context = context;
        this.enterTrace = enterTrace.clone();
        this.exitTrace = exitTrace == null ? null : exitTrace.clone();
        this.enterContext = enterContext.clone();
        this.exitContext = exitContext == null ? null : exitContext.clone();
    }

    /**
     * Gets context instance.
     *
     * @return Context instance.
     */
    public Object getContext() {
        return this.context;
    }

    /**
     * Returns true if exited.
     *
     * @return True if exited.
     */
    public boolean isExited() {
        return this.exitTrace != null;
    }

    /**
     * Gets the copy of enter call stack elements.
     *
     * @return Copy of enter call stack elements.
     */
    public StackTraceElement[] getEnterTrace() {
        return this.enterTrace.clone();
    }

    /**
     * Gets the copy of exit call stack elements (may be null).
     *
     * @return Copy of exit call stack elements (may be null).
     */
    public StackTraceElement[] getExitTrace() {
        return this.exitTrace == null ? null : this.exitTrace.clone();
    }

    /**
     * Gets enter context.
     *
     * @return Enter context.
     */
    public Object[] getEnterContext() {
        return this.enterContext.clone();
    }

    /**
     * Gets exit context.
     *
     * @return Exit context.
     */
    public Object[] getExitContext() {
        return this.exitContext == null ? null : this.exitContext.clone();
    }

    /**
     * Exits the context.
     *
     * @param exitTrace Exit call stack trace.
     * @return Same context with exit state.
     */
    ContextHolder exit(StackTraceElement[] exitTrace, Object[] exitContext) {

        if (this.isExited())
            return this;

        this.exitTrace = exitTrace.clone();
        this.exitContext = exitContext.clone();

        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ContextHolder[context=").append(this.getContext());

        stringBuilder.append(", ").append("enterContext=").append(Arrays.toString(this.getEnterContext()));
        stringBuilder.append(", ").append("enterTrace=").append(Arrays.toString(this.getEnterTrace()));

        if (this.isExited()) {
            stringBuilder.append(", ").append("exitContext=").append(Arrays.toString(this.getExitContext()));
            stringBuilder.append(", ").append("exitTrace=").append(Arrays.toString(this.getExitTrace()));
        }

        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    /**
     * Returns a string representation without stacks.
     *
     * @return String representation without stacks.
     */
    public String toSimpleString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ContextHolder[context=").append(this.getContext());

        stringBuilder.append(", ").append("enterContext=").append(Arrays.toString(this.getEnterContext()));

        if (this.isExited()) {
            stringBuilder.append(", ").append("exitContext=").append(Arrays.toString(this.getExitContext()));
        }

        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}
