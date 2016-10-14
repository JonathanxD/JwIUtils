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

import com.github.jonathanxd.iutils.array.ArrayUtils;
import com.github.jonathanxd.iutils.type.TypeInfo;

/**
 * Created by jonathan on 20/08/16.
 */
public final class Links {
    private Links() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create a {@link Link} from a {@link Invokable}
     *
     * @param invokable Invokable
     * @param <T>       Type
     * @return {@link Link}
     */
    public static <T> Link<T> ofInvokable(Invokable<T> invokable) {
        return new InvokableLink<>(invokable);
    }

    /**
     * Create a {@link NamedLink} from a {@link Link} (Wrapped)
     *
     * @param link      Link
     * @param name      Name
     * @param tTypeInfo TypeInfo
     * @param <T>       Type
     * @return Named link.
     */
    public static <T> NamedLink<T> named(Link<T> link, String name, TypeInfo<T> tTypeInfo) {
        return new WrapperNamedLink<>(link, name, tTypeInfo);
    }

    /**
     * Create a {@link NamedBindLink} from a {@link BindLink} (Wrapped)
     *
     * @param link      Link
     * @param name      Name
     * @param tTypeInfo TypeInfo
     * @param <T>       Type
     * @return Named link.
     */
    public static <U, T> NamedBindLink<U, T> bindNamed(BindLink<U, T> link, String name, TypeInfo<T> tTypeInfo) {
        return new WrappedNamedBindLink<>(link, name, tTypeInfo);
    }

    private static final class InvokableLink<T> implements Link<T> {
        private final Invokable<T> invokable;

        private InvokableLink(Invokable<T> invokable) {
            this.invokable = invokable;
        }

        @Override
        public <U> BindLink<U, T> bind(U instance) {
            return new WrapperBindLink<>(instance, this);
        }

        @Override
        public T invoke(Object... args) {
            return this.invokable.invoke(args);
        }
    }

    private static final class WrapperBindLink<U, T> implements BindLink<U, T> {
        private final U instance;
        private final Link<T> link;

        private WrapperBindLink(U instance, Link<T> link) {
            this.instance = instance;
            this.link = link;
        }

        @Override
        public U getBind() {
            return this.instance;
        }

        @Override
        public <V> BindLink<V, T> bind(V instance) {
            return new WrapperBindLink<>(instance, this);
        }

        @Override
        public T invoke(Object... args) {
            return this.link.invoke(ArrayUtils.addAllToArray(new Object[]{this.getBind()}, args));
        }
    }

    private static class WrapperNamedLink<T> implements NamedLink<T> {

        private final Link<T> link;
        private final String name;
        private final TypeInfo<T> tTypeInfo;

        private WrapperNamedLink(Link<T> link, String name, TypeInfo<T> tTypeInfo) {
            this.link = link;
            this.name = name;
            this.tTypeInfo = tTypeInfo;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public TypeInfo<T> getTypeInfo() {
            return this.tTypeInfo;
        }

        @Override
        public <U> NamedBindLink<U, T> bind(U instance) {
            return new WrapperNamedBindLink<>(this, this.getName(), this.getTypeInfo(), instance);
        }

        @Override
        public T invoke(Object... args) {
            return this.link.invoke(args);
        }
    }

    private static final class WrapperNamedBindLink<U, T> extends WrapperNamedLink<T> implements NamedBindLink<U, T> {

        private final U bind;

        private WrapperNamedBindLink(Link<T> link, String name, TypeInfo<T> tTypeInfo, U bind) {
            super(link, name, tTypeInfo);
            this.bind = bind;
        }

        @Override
        public U getBind() {
            return this.bind;
        }

        @Override
        public T invoke(Object... args) {
            return super.link.invoke(ArrayUtils.addAllToArray(new Object[]{this.getBind()}, args));
        }

    }

    private static final class WrappedNamedBindLink<U, T> extends WrapperNamedLink<T> implements NamedBindLink<U, T> {

        private final BindLink<U, T> bindLink;

        private WrappedNamedBindLink(BindLink<U, T> bindLink, String name, TypeInfo<T> tTypeInfo) {
            super(bindLink, name, tTypeInfo);
            this.bindLink = bindLink;
        }

        @Override
        public U getBind() {
            return this.bindLink.getBind();
        }

        @Override
        public T invoke(Object... args) {
            return super.link.invoke(args);
        }

    }
}
