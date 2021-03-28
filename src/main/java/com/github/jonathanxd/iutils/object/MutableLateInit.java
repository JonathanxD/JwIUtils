/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import com.github.jonathanxd.iutils.exception.InitializationException;

/**
 * Mutable {@link LateInit late init} factory.
 *
 * Mutable LateInit can be re-initialized without throwing {@link InitializationException}.
 */
public final class MutableLateInit {

    private MutableLateInit() {
    }

    /**
     * Creates a late init reference.
     *
     * @param name Identification name.
     * @param <E>  Type.
     * @return Late init reference.
     */
    public static <E> MutableLateInit.Ref<E> lateRef(String name) {
        return new MutableLateInit.Ref<>(name);
    }

    /**
     * Creates a late init lazy.
     *
     * @param name Identification name.
     * @param <E>  Type.
     * @return Late init lazy.
     */
    public static <E> MutableLateInit.LLazy<E> lateLazy(String name) {
        return new MutableLateInit.LLazy<>(name);
    }

    /**
     * Creates a late init boolean.
     *
     * @param name Identification name.
     * @return Late init reference boolean.
     */
    public static MutableLateInit.Bool lateBool(String name) {
        return new MutableLateInit.Bool(name);
    }

    /**
     * Creates a late init character.
     *
     * @param name Identification name.
     * @return Late init reference character.
     */
    public static MutableLateInit.Char lateChar(String name) {
        return new MutableLateInit.Char(name);
    }

    /**
     * Creates a late init byte.
     *
     * @param name Identification name.
     * @return Late init reference byte.
     */
    public static MutableLateInit.Byte lateByte(String name) {
        return new MutableLateInit.Byte(name);
    }

    /**
     * Creates a late init short.
     *
     * @param name Identification name.
     * @return Late init reference short.
     */
    public static MutableLateInit.Short lateShort(String name) {
        return new MutableLateInit.Short(name);
    }

    /**
     * Creates a late init int.
     *
     * @param name Identification name.
     * @return Late init reference int.
     */
    public static MutableLateInit.Int lateInt(String name) {
        return new MutableLateInit.Int(name);
    }

    /**
     * Creates a late init long.
     *
     * @param name Identification name.
     * @return Late init reference long.
     */
    public static MutableLateInit.Long lateLong(String name) {
        return new MutableLateInit.Long(name);
    }

    /**
     * Creates a late init float.
     *
     * @param name Identification name.
     * @return Late init reference float.
     */
    public static MutableLateInit.Float lateFloat(String name) {
        return new MutableLateInit.Float(name);
    }

    /**
     * Creates a late init double.
     *
     * @param name Identification name.
     * @return Late init reference double.
     */
    public static MutableLateInit.Double lateDouble(String name) {
        return new MutableLateInit.Double(name);
    }

    public static class Ref<E> extends LateInit.Ref<E> {
        Ref(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class LLazy<E> extends LateInit.LLazy<E> {
        LLazy(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Bool extends LateInit.Bool {
        Bool(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Char extends LateInit.Char {
        Char(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Byte extends LateInit.Byte {
        Byte(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Short extends LateInit.Short {
        Short(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Int extends LateInit.Int {
        Int(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Long extends LateInit.Long {
        Long(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Float extends LateInit.Float {
        Float(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }

    public static class Double extends LateInit.Double {
        Double(String name) {
            super(name);
        }

        @Override
        void initCheck() {
        }

        @Override
        public void deInit() {
            super.deInit();
        }
    }
}
