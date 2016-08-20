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

import com.github.jonathanxd.iutils.exceptions.RethrowException;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Created by jonathan on 20/08/16.
 */
public final class Invokables {
    private Invokables() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a {@link Invokable} instance from Method
     *
     * @param method Method
     * @param <T>    Type
     * @return {@link Invokable} from method.
     */
    public static <T> Invokable<T> fromMethod(Method method) {
        return new MethodInvokable<>(method);
    }

    /**
     * Create a {@link Invokable} instance from Constructor
     *
     * @param constructor Constructor
     * @param <T>         Type
     * @return {@link Invokable} instance from Constructor
     */
    public static <T> Invokable<T> fromConstructor(Constructor<T> constructor) {
        return new ConstructorInvokable<>(constructor);
    }

    /**
     * Create a {@link Invokable} instance from Constructor (ignore first argument of invocation).
     *
     * @param constructor Constructor
     * @param <T>         Type
     * @return {@link Invokable} instance from Constructor
     */
    public static <T> Invokable<T> fromConstructorIF(Constructor<T> constructor) {
        return new ConstructorInvokableIF<>(constructor);
    }

    /**
     * Create a {@link Invokable} instance from field
     *
     * @param field Field
     * @param <T>   Type
     * @return {@link Invokable} instance from field
     */
    public static <T> Invokable<T> fromField(Field field) {
        return new FieldInvokable<>(field);
    }

    /**
     * Create a {@link Invokable} instance from MethodHandle
     *
     * @param methodHandle Method Handle
     * @param <T>          Type
     * @return {@link Invokable} instance from MethodHandle
     */
    public static <T> Invokable<T> fromMethodHandle(MethodHandle methodHandle) {
        return new MethodHandleInvokable<>(methodHandle);
    }

    /**
     * Create a {@link Invokable} from other {@link Invokable} (wrapper)
     *
     * @param invokable Invokable
     * @param <T>       Type
     * @return Wrapped {@link Invokable}
     */
    public static <T> Invokable<T> fromInvokable(Invokable<T> invokable) {
        return new WrapperInvokable<>(invokable);
    }

    /**
     * Create a {@link Invokable} that call {@code function} when {@link
     * Invokable#invoke(Object...)} is called, an pass the result of the {@code function} to wrapped
     * {@code invokable} instance.
     *
     * @param invokable Invokable
     * @param function  Function to modify arguments.
     * @param <T>       Type
     * @return Wrapped {@link Invokable} with an argument function.
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

    private static final class FieldInvokable<T> implements Invokable<T> {

        private final Field field;

        private FieldInvokable(Field field) {
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
