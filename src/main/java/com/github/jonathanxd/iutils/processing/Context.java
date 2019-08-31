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
package com.github.jonathanxd.iutils.processing;

import com.github.jonathanxd.iutils.collection.view.ViewCollections;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Context tracker.
 *
 * Context can be used to keep track of contexts which caused a element to be processed and enable
 * behaviors depending on context.
 *
 * The context tracker keeps track of where context was created and the current context object and
 * stack trace. This may be used to find bugs in processing context.
 */
public abstract class Context {

    /**
     * Creation stack elements.
     */
    final StackTraceElement[] creationContext;

    /**
     * Creates a context.
     *
     * @param creationContext Creation context (stack elements of methods which caused the creation
     *                        of context).
     */
    Context(StackTraceElement[] creationContext) {
        this.creationContext = creationContext;
    }

    /**
     * Creates a context.
     *
     * @return Context.
     */
    public static Context create() {
        return new Impl(sub(new RuntimeException().getStackTrace()));
    }

    /**
     * Helper method to remove last method from stack trace array.
     *
     * @param stackTraceElements Stack trace array.
     * @return Stack trace array without last method.
     */
    public static StackTraceElement[] sub(StackTraceElement[] stackTraceElements) {
        return Arrays.copyOfRange(stackTraceElements, 1, stackTraceElements.length);
    }

    /**
     * Prints stack trace elements to {@code printer}.
     *
     * @param printer            Printer.
     * @param stackTraceElements Elements to print.
     * @param simplify           Simplify stack trace array (print first element only).
     */
    private static void printStackTraces(Consumer<String> printer, StackTraceElement[] stackTraceElements, boolean simplify) {
        if (simplify) {
            if (stackTraceElements.length > 0)
                printer.accept(String.valueOf(stackTraceElements[0]));
        } else {
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                printer.accept(String.valueOf(stackTraceElement));
            }
        }
    }

    /**
     * Prints contexts.
     *
     * @param contexts Contexts to print.
     * @param printer  Printer.
     * @param simplify Simplify stack trace array (print first element only).
     */
    public static void printContexts(List<ContextHolder> contexts, Consumer<String> printer, boolean simplify) {
        Context.printContexts(contexts, printer, simplify, true);
    }

    /**
     * Prints contexts.
     *
     * @param contexts        Contexts to print.
     * @param printer         Printer.
     * @param simplify        Simplify stack trace array (print first element only).
     * @param printStackTrace Print stack trace. Simplify has not effect if this is {@code false}.
     */
    public static void printContexts(List<ContextHolder> contexts, Consumer<String> printer,
                                     boolean simplify,
                                     boolean printStackTrace) {
        printer.accept("contexts={");

        for (int i = contexts.size() - 1; i >= 0; i--) {
            ContextHolder o = contexts.get(i);
            printer.accept("  [" + o.getContext() + ", enter={");

            printer.accept("    context=" + Arrays.toString(o.getEnterContext()) + "");

            if (printStackTrace)
                Context.printStackTraces(s -> printer.accept("    ".concat(s)), o.getEnterTrace(), simplify);

            if (o.isExited()) {
                printer.accept("  }, exit={");
                printer.accept("    context=" + Arrays.toString(o.getExitContext()) + "");
                if (printStackTrace)
                    Context.printStackTraces(s -> printer.accept("      ".concat(s)), o.getExitTrace(), simplify);
            }
            printer.accept("  }]");
        }

        printer.accept("}");
    }

    /**
     * Enters the context.
     *
     * @param context Context to enter.
     */
    public abstract void enterContext(Object context);

    /**
     * Exits the context.
     *
     * @param context Context to exit.
     */
    public abstract void exitContext(Object context);

    /**
     * Gets current context.
     *
     * @return Current context.
     */
    public abstract Optional<ContextHolder> getCurrentContext();

    /**
     * Gets context based on reverse index, example, backIndex 0 means the last entered context,
     * backIndex 1 means the entered context before the last.
     *
     * @param backIndex Reversed index position.
     * @return Current context.
     */
    public abstract Optional<ContextHolder> getContext(int backIndex);

    /**
     * Gets a view list of current contexts in a LIFO (last in first out) order.
     *
     * @return View list of current contexts in a LIFO (last in first out) order.
     */
    public abstract List<ContextHolder> getLifoContexts();

    /**
     * Gets a view list of all contexts in a LIFO (last in first out) order.
     *
     * @return View list of all contexts in a LIFO (last in first out) order.
     */
    public abstract List<ContextHolder> getLifoAllContexts();

    /**
     * Gets the view list of current contexts.
     *
     * @return View list of current contexts.
     */
    public abstract List<ContextHolder> getContexts();

    /**
     * Gets the view list of all contexts, it includes exited contexts.
     *
     * @return View list of all contexts, it includes exited contexts.
     */
    public abstract List<ContextHolder> getAllContexts();

    /**
     * Creates a unmodifiable context with current context information (enter and exit is not
     * allowed, context list is static and cannot be modified).
     *
     * @return Unmodifiable context with current context information (enter and exit is not allowed,
     * context list is static and cannot be modified).
     */
    public abstract Context current();

    /**
     * Gets the creation location.
     *
     * @return Creation location.
     */
    private StackTraceElement[] getCreationLocation() {
        return this.creationContext;
    }

    /**
     * Gets stack trace elements of location where caused the creation of context.
     *
     * @return Stack trace elements of location where caused the creation of context.
     */
    public StackTraceElement[] getCreationLocationElements() {
        return this.getCreationLocation().clone();
    }

    @Override
    public String toString() {
        return "Context[lifoAllContexts=" +
                this.getLifoAllContexts().stream()
                        .map(ContextHolder::toSimpleString)
                        .collect(Collectors.joining(", ", "[", "]")) +
                "]";
    }

    /**
     * Prints context to {@link System#err}
     */
    public final void printContext() {
        this.printContext(System.err, false, true);
    }

    /**
     * Prints context to {@link System#err}
     *
     * @param simplify Simplify stack trace array (print first element only).
     * @param all      Print all contexts.
     */
    public final void printContext(boolean simplify, boolean all) {
        this.printContext(System.err, simplify, all);
    }

    /**
     * Prints context to {@code printWriter}
     *
     * @param printWriter Writer to print context.
     * @param simplify    Simplify stack trace array (print first element only).
     * @param all         Print all contexts.
     */
    public final void printContext(PrintWriter printWriter, boolean simplify, boolean all) {
        this.printContext(printWriter::println, () -> printWriter, simplify, all);
    }

    /**
     * Prints context to {@code printStream}
     *
     * @param printStream Stream to print context.
     * @param simplify    Simplify stack trace array (print first element only).
     * @param all         Print all contexts.
     */
    public final void printContext(PrintStream printStream, boolean simplify, boolean all) {
        this.printContext(printStream::println, () -> printStream, simplify, all);
    }

    /**
     * Prints context to {@code printer} and locks object provided by {@code lock}.
     *
     * @param printer  Printer.
     * @param lock     Lock provider.
     * @param simplify Simplify stack trace array (print first element only).
     * @param all      Print all contexts.
     */
    public final void printContext(Consumer<String> printer, Supplier<Object> lock, boolean simplify, boolean all) {
        synchronized (lock.get()) {
            this.printContext(printer, simplify, all);
        }
    }

    /**
     * Prints context to {@code printer}.
     *
     * @param printer  Printer.
     * @param simplify Simplify stack trace array (print first element only).
     * @param all      Print all contexts.
     */
    public final void printContext(Consumer<String> printer, boolean simplify, boolean all) {
        this.printContext(printer, simplify, all, true);
    }

    /**
     * Prints context to {@code printer}.
     *
     * @param printer         Printer.
     * @param simplify        Simplify stack trace array (print first element only).
     * @param all             Print all contexts.
     * @param printStackTrace Print stack trace. Simplify has not effect if this is {@code false}.
     */
    public final void printContext(Consumer<String> printer, boolean simplify,
                                   boolean all,
                                   boolean printStackTrace) {
        printer.accept("[Print");

        printer.accept("  Call[");

        if (printStackTrace)
            new RuntimeException().printStackTrace(new PrintStream(new OutputStream() {
                StringBuilder stringBuilder = new StringBuilder();

                @Override
                public void write(int b) throws IOException {
                    if (b == '\n') {
                        printer.accept("    ".concat(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                    } else {
                        stringBuilder.append((char) b);
                    }
                }

                @Override
                public void flush() throws IOException {
                    if (stringBuilder.length() > 0) {
                        printer.accept("    ".concat(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                    }
                }

                @Override
                public void close() throws IOException {
                    if (stringBuilder.length() > 0) {
                        printer.accept("    ".concat(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                    }
                }
            }));

        printer.accept("  ],");

        printer.accept("  Context[");
        printer.accept("    current={");

        if (printStackTrace) {
            printer.accept("      creation={");

            StackTraceElement[] creationLocation = this.getCreationLocation();

            Context.printStackTraces(s -> printer.accept("        ".concat(s)), creationLocation, simplify);

            printer.accept("      },");
        }

        printContexts(this.getContexts(), s -> printer.accept("      ".concat(s)), simplify, printStackTrace);

        if (all) {
            printer.accept("      }, all={");

            printContexts(this.getAllContexts(), s -> printer.accept("      ".concat(s)), simplify, printStackTrace);
        }

        printer.accept("    }");
        printer.accept("  ]");
        printer.accept("]");

    }

    /**
     * Default implementation of context.
     */
    static class Impl extends Context {

        private final List<ContextHolder> contexts = new LinkedList<>();
        private final List<ContextHolder> unmodifiableContexts = Collections.unmodifiableList(this.contexts);

        private final List<ContextHolder> allContexts = new LinkedList<>();
        private final List<ContextHolder> unmodifiableAllContexts = Collections.unmodifiableList(this.allContexts);

        private final List<ContextHolder> reversedContexts =
                ViewCollections.readOnlyList(ViewCollections.reversedList(this.contexts));

        private final List<ContextHolder> reversedAllContexts =
                ViewCollections.readOnlyList(ViewCollections.reversedList(this.allContexts));

        Impl(StackTraceElement[] creationContext) {
            super(creationContext);
        }

        @Override
        public void enterContext(Object context) {
            ContextHolder contextHolder = new ContextHolder(context,
                    sub(new RuntimeException().getStackTrace()),
                    null, this.track(), null);

            this.contexts.add(contextHolder);
            this.allContexts.add(contextHolder);
        }

        @Override
        public void exitContext(Object context) {

            if (this.contexts.isEmpty())
                throw new MismatchContextException("Expected non empty context, but empty context was found!");

            int index = this.contexts.size() - 1;
            ContextHolder o = this.contexts.get(index);

            if (!o.getContext().equals(context))
                throw new MismatchContextException("Mismatch top context value. Expected: '" + context + "' but found '" + o + "'!");

            this.contexts.remove(index);
            o.exit(sub(new RuntimeException().getStackTrace()), this.track());
        }

        private Object[] track() {
            return this.getLifoContexts().stream().map(ContextHolder::getContext).toArray();
        }

        @Override
        public Optional<ContextHolder> getCurrentContext() {
            return this.getContext(0);
        }

        @Override
        public Optional<ContextHolder> getContext(int backIndex) {
            if (this.contexts.isEmpty())
                return Optional.empty();

            int index = (this.contexts.size() - 1) - backIndex;

            if (index < 0)
                throw new IndexOutOfBoundsException("Back index '" + backIndex + "' exceeds max value '" + (this.contexts.size() - 1));

            return Optional.of(this.contexts.get(index));
        }

        @Override
        public List<ContextHolder> getLifoContexts() {
            return this.reversedContexts;
        }

        @Override
        public List<ContextHolder> getLifoAllContexts() {
            return this.reversedAllContexts;
        }

        @Override
        public List<ContextHolder> getContexts() {
            return this.unmodifiableContexts;
        }

        @Override
        public List<ContextHolder> getAllContexts() {
            return this.unmodifiableAllContexts;
        }

        @Override
        public Context current() {
            return new CurrentContext(super.creationContext, this.contexts, this.allContexts);
        }

    }

    /**
     * Current unmodifiable context.
     */
    static class CurrentContext extends Context {

        private final List<ContextHolder> contexts;
        private final List<ContextHolder> allContexts;
        private final List<ContextHolder> reversedContexts;
        private final List<ContextHolder> reversedAllContexts;


        CurrentContext(StackTraceElement[] creationContext, List<ContextHolder> contexts, List<ContextHolder> allContexts) {
            super(creationContext);
            this.contexts = Collections.unmodifiableList(new ArrayList<>(contexts));
            this.allContexts = Collections.unmodifiableList(new ArrayList<>(allContexts));
            this.reversedContexts = ViewCollections.readOnlyList(ViewCollections.reversedList(this.contexts));
            this.reversedAllContexts = ViewCollections.readOnlyList(ViewCollections.reversedList(this.allContexts));
        }

        @Override
        public void enterContext(Object context) {
            throw new UnsupportedOperationException("Cannot mutate current context");
        }

        @Override
        public void exitContext(Object context) {
            throw new UnsupportedOperationException("Cannot mutate current context");
        }

        @Override
        public Optional<ContextHolder> getCurrentContext() {
            return this.getContext(0);
        }

        @Override
        public Optional<ContextHolder> getContext(int backIndex) {
            if (this.contexts.isEmpty())
                return Optional.empty();

            int index = (this.contexts.size() - 1) - backIndex;

            if (index < 0)
                throw new IndexOutOfBoundsException("Back index '" + backIndex + "' exceeds max value '" + (this.contexts.size() - 1) + "'!");

            return Optional.of(this.contexts.get(index));
        }

        @Override
        public List<ContextHolder> getLifoContexts() {
            return this.reversedContexts;
        }

        @Override
        public List<ContextHolder> getLifoAllContexts() {
            return this.reversedAllContexts;
        }

        @Override
        public List<ContextHolder> getContexts() {
            return this.contexts;
        }

        @Override
        public List<ContextHolder> getAllContexts() {
            return this.allContexts;
        }

        @Override
        public Context current() {
            return this;
        }
    }
}
