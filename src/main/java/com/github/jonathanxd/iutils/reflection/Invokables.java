/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.reflection;

import com.github.jonathanxd.iutils.exception.RethrowException;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Invokables factory.
 */
public final class Invokables {

    private Invokables() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a {@link Invokable} instance from a {@link Method}.
     *
     * @param method Method.
     * @param <T>    Result type of method invocation.
     * @return {@link Invokable} from {@code method}.
     */
    public static <T> Invokable<T> fromMethod(Method method) {
        return new MethodInvokable<>(method);
    }

    /**
     * Create a {@link Invokable} instance from a {@link Constructor}.
     *
     * @param constructor Constructor.
     * @param <T>         Result type of constructor invocation.
     * @return {@link Invokable} instance from {@code constructor}.
     */
    public static <T> Invokable<T> fromConstructor(Constructor<T> constructor) {
        return new ConstructorInvokable<>(constructor);
    }

    /**
     * Create a {@link Invokable} instance from a {@link Constructor} (ignore first argument of
     * invocation).
     *
     * @param constructor Constructor.
     * @param <T>         Type
     * @return {@link Invokable} instance from {@code constructor}.
     */
    public static <T> Invokable<T> fromConstructorIF(Constructor<T> constructor) {
        return new ConstructorInvokableIF<>(constructor);
    }

    /**
     * Create a {@link Invokable} instance from a {@link Field} getter
     *
     * @param field Field
     * @param <T>   Type
     * @return {@link Invokable} instance from {@code field} getter
     */
    public static <T> Invokable<T> fromFieldGetter(Field field) {
        return new FieldGetterInvokable<>(field);
    }

    /**
     * Create a {@link Invokable} instance from {@link Field} setter
     *
     * @param field Field
     * @return {@link Invokable} instance from {@code field} setter.
     */
    public static Invokable<Void> fromFieldSetter(Field field) {
        return new FieldSetterInvokable(field);
    }

    /**
     * Create a {@link Invokable} instance from a {@link MethodHandle}.
     *
     * @param methodHandle Method Handle.
     * @param <T>          Result type of invocation of the {@code methodHandle}
     * @return {@link Invokable} instance from {@code methodHandle}.
     */
    public static <T> Invokable<T> fromMethodHandle(MethodHandle methodHandle) {
        return new MethodHandleInvokable<>(methodHandle);
    }

    /**
     * Create a {@link Invokable} from other {@link Invokable} (wrapper).
     *
     * @param invokable Invokable.
     * @param <T>       Result type of invocation.
     * @return Wrapped {@link Invokable}
     */
    public static <T> Invokable<T> fromInvokable(Invokable<T> invokable) {
        return new WrapperInvokable<>(invokable);
    }

    /**
     * Creates a {@link Invokable} that sends arguments to a transformer {@code function} before
     * invocation of the {@link Invokable}.
     *
     * @param invokable Invokable.
     * @param function  Function to modify arguments.
     * @param <T>       Result type.
     * @return Wrapped {@link Invokable} with an argument transformer.
     */
    public static <T> Invokable<T> modifyArgs(Invokable<T> invokable, Function<Object[], Object[]> function) {
        return new ArgsModifierInvokable<>(invokable, function);
    }

    private static final class MethodInvokable<T> implements Invokable<T> {

        private final Method method;

        private MethodInvokable(Method method) {
            this.method = method;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T invoke(Object... args) {

            Object instance = null;
            Object[] arguments = new Object[0];

            if (args.length != 0) {
                instance = args[0];
            }

            if (args.length > 1) {
                arguments = Arrays.copyOfRange(args, 1, args.length);
            }

            try {
                return (T) this.method.invoke(instance, arguments);
            } catch (IllegalAccessException | InvocationTargetException ignored) {
                throw new RethrowException(ignored);
            }
        }
    }

    private static final class MethodHandleInvokable<T> implements Invokable<T> {

        private final MethodHandle methodHandle;

        private MethodHandleInvokable(MethodHandle methodHandle) {
            this.methodHandle = methodHandle;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T invoke(Object... args) {

            Object instance = null;
            Object[] arguments = new Object[0];

            if (args.length != 0) {
                instance = args[0];
            }

            if (args.length > 1) {
                arguments = Arrays.copyOfRange(args, 1, args.length);
            }

            try {
                return (T) this.methodHandle.bindTo(instance).invokeWithArguments(arguments);
            } catch (Throwable throwable) {
                throw new RethrowException(throwable);
            }
        }
    }

    private static final class ConstructorInvokable<T> implements Invokable<T> {

        private final Constructor<T> constructor;

        private ConstructorInvokable(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T invoke(Object... args) {
            try {
                return this.constructor.newInstance(args);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RethrowException(e);
            }
        }
    }

    private static final class ConstructorInvokableIF<T> implements Invokable<T> {

        private final Constructor<T> constructor;

        private ConstructorInvokableIF(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T invoke(Object... args) {
            Object[] arguments = new Object[0];

            if (args.length > 1) {
                arguments = Arrays.copyOfRange(args, 1, args.length);
            }

            try {
                return this.constructor.newInstance(arguments);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RethrowException(e);
            }
        }
    }

    private static final class FieldGetterInvokable<T> implements Invokable<T> {

        private final Field field;

        private FieldGetterInvokable(Field field) {
            this.field = field;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T invoke(Object... args) {

            Object instance = null;

            if (args.length != 0) {
                instance = args[0];
            }

            try {
                return (T) this.field.get(instance);
            } catch (IllegalAccessException ignored) {
                throw new RethrowException(ignored);
            }
        }
    }

    private static final class FieldSetterInvokable implements Invokable<Void> {

        private final Field field;

        private FieldSetterInvokable(Field field) {
            this.field = field;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Void invoke(Object... args) {

            Object instance = null;

            if (args.length != 0) {
                instance = args[0];
            }

            try {
                this.field.set(instance, args[1]);
            } catch (IllegalAccessException ignored) {
                throw new RethrowException(ignored);
            }

            return null;
        }
    }

    private static class WrapperInvokable<T> implements Invokable<T> {

        private final Invokable<T> invokable;


        private WrapperInvokable(Invokable<T> invokable) {
            this.invokable = invokable;
        }

        @Override
        public T invoke(Object... args) {
            return this.invokable.invoke(args);
        }
    }

    private static final class ArgsModifierInvokable<T> extends WrapperInvokable<T> {

        private final Function<Object[], Object[]> argumentModifier;

        private ArgsModifierInvokable(Invokable<T> invokable, Function<Object[], Object[]> argumentModifier) {
            super(invokable);
            this.argumentModifier = argumentModifier;
        }

        @Override
        public T invoke(Object... args) {
            return super.invoke(this.argumentModifier.apply(args));
        }
    }

}
