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
package com.github.jonathanxd.iutils.opt;

import com.github.jonathanxd.iutils.object.Lazy;

import java.util.Objects;

/**
 * Base class of specialized {@code None} and {@code Some} holders.
 *
 * All implementations of {@link ValueHolder} must provide specialized {@code getValue} methods.
 * {@code getValue} methods may throw {@link UnsupportedOperationException} when called on {@code
 * None} holders. A check should be made through {@link #hasSome()} to ensure that the {@link
 * ValueHolder} has value. Whenever {@link #hasSome()} returns {@code true}, {@code getValue} must
 * not throw any exception.
 */
public interface ValueHolder {

    /**
     * Returns {@code true} if the holder has some value, {@code false} otherwise.
     *
     * @return {@code true} if the holder has some value, {@code false} otherwise.
     */
    boolean hasSome();

    interface SomeValueHolder extends ValueHolder {
        @Override
        default boolean hasSome() {
            return true;
        }
    }

    interface NoneValueHolder extends ValueHolder {
        @Override
        default boolean hasSome() {
            return false;
        }
    }

    interface ObjectValueHolder<T> extends ValueHolder {
        /**
         * Gets the object value.
         *
         * @return Object value.
         * @see ValueHolder
         */
        T getValue();

        final class Some<T> implements ObjectValueHolder<T>, SomeValueHolder {
            private final T value;

            public Some(T value) {
                this.value = value;
            }

            @Override
            public T getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return this.getValue().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some<?> && Objects.equals(this.getValue(), ((Some) obj).getValue()))
                        || super.equals(obj);
            }
        }

        final class None<T> implements ObjectValueHolder<T>, NoneValueHolder {

            private static final None<?> INSTANCE = new None<>();

            private None() {
            }

            @SuppressWarnings("unchecked")
            public static <T> None<T> getInstance() {
                return (None<T>) INSTANCE;
            }

            @Override
            public T getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None<?> || super.equals(obj);
            }
        }
    }

    interface LazyValueHolder<T> extends ObjectValueHolder<T> {

        /**
         * Gets the lazy instance.
         *
         * @return Lazy instance.
         */
        Lazy<T> getLazy();

        final class Some<T> implements LazyValueHolder<T>, SomeValueHolder {
            private final Lazy<T> lazy;

            public Some(Lazy<T> lazy) {
                this.lazy = lazy;
            }

            @Override
            public T getValue() {
                return this.lazy.get();
            }

            @Override
            public Lazy<T> getLazy() {
                return this.lazy;
            }

            @Override
            public int hashCode() {
                return Objects.hash(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof LazyValueHolder.Some<?>
                        && Objects.equals(this.getValue(), ((LazyValueHolder.Some) obj).getValue()))
                        || super.equals(obj);
            }
        }

        final class None<T> implements LazyValueHolder<T>, NoneValueHolder {

            private static final LazyValueHolder.None<?> INSTANCE = new LazyValueHolder.None<>();

            private None() {
            }

            @SuppressWarnings("unchecked")
            public static <T> LazyValueHolder.None<T> getInstance() {
                return (LazyValueHolder.None<T>) INSTANCE;
            }

            @Override
            public T getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public Lazy<T> getLazy() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof LazyValueHolder.None<?> && super.equals(obj);
            }
        }
    }

    interface BooleanValueHolder extends ValueHolder {
        /**
         * Gets the boolean value.
         *
         * @return Boolean value.
         * @see ValueHolder
         */
        boolean getValue();

        final class Some implements BooleanValueHolder, SomeValueHolder {
            private final boolean value;

            public Some(boolean value) {
                this.value = value;
            }

            @Override
            public boolean getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return Boolean.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue()) || super.equals(obj);
            }
        }

        final class None implements BooleanValueHolder, NoneValueHolder {

            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }

            @Override
            public boolean getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }

    interface ByteValueHolder extends ValueHolder {

        /**
         * Gets the byte value.
         *
         * @return Byte value.
         * @see ValueHolder
         */
        byte getValue();

        final class Some implements ByteValueHolder, SomeValueHolder {
            private final byte value;

            public Some(byte value) {
                this.value = value;
            }

            @Override
            public byte getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return Byte.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue())
                        || super.equals(obj);
            }
        }

        final class None implements ByteValueHolder, NoneValueHolder {
            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }

            @Override
            public byte getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }

    interface CharValueHolder extends ValueHolder {

        /**
         * Gets the char value.
         *
         * @return Char value.
         * @see ValueHolder
         */
        char getValue();

        final class Some implements CharValueHolder, SomeValueHolder {
            private final char value;

            public Some(char value) {
                this.value = value;
            }

            @Override
            public char getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return Character.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue())
                        || super.equals(obj);
            }
        }

        final class None implements CharValueHolder, NoneValueHolder {
            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }

            @Override
            public char getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }

    interface DoubleValueHolder extends ValueHolder {

        /**
         * Gets the double value.
         *
         * @return Double value.
         * @see ValueHolder
         */
        double getValue();

        final class Some implements DoubleValueHolder, SomeValueHolder {
            private final double value;

            public Some(double value) {
                this.value = value;
            }

            @Override
            public double getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return Double.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue())
                        || super.equals(obj);
            }
        }

        final class None implements DoubleValueHolder, NoneValueHolder {
            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }

            @Override
            public double getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }

    interface FloatValueHolder extends ValueHolder {
        /**
         * Gets the float value.
         *
         * @return Float value.
         * @see ValueHolder
         */
        float getValue();

        final class Some implements FloatValueHolder, SomeValueHolder {
            private final float value;

            public Some(float value) {
                this.value = value;
            }

            @Override
            public float getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return Float.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue())
                        || super.equals(obj);
            }
        }

        final class None implements FloatValueHolder, NoneValueHolder {
            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }

            @Override
            public float getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }

    interface IntValueHolder extends ValueHolder {
        /**
         * Gets the int value.
         *
         * @return Int value.
         * @see ValueHolder
         */
        int getValue();

        final class Some implements IntValueHolder, SomeValueHolder {
            private final int value;

            public Some(int value) {
                this.value = value;
            }

            @Override
            public int getValue() {
                return this.value;
            }

            @Override
            public boolean hasSome() {
                return true;
            }

            @Override
            public int hashCode() {
                return Integer.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue())
                        || super.equals(obj);
            }

        }

        final class None implements IntValueHolder, NoneValueHolder {
            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }


            @Override
            public int getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }

    interface LongValueHolder extends ValueHolder {
        /**
         * Gets the long value.
         *
         * @return Long value.
         * @see ValueHolder
         */
        long getValue();

        final class Some implements LongValueHolder, SomeValueHolder {
            private final long value;

            public Some(long value) {
                this.value = value;
            }

            @Override
            public long getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return Long.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue())
                        || super.equals(obj);
            }
        }

        final class None implements LongValueHolder, NoneValueHolder {
            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }

            @Override
            public long getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }

    interface ShortValueHolder extends ValueHolder {
        /**
         * Gets the short value.
         *
         * @return Short value.
         * @see ValueHolder
         */
        short getValue();

        final class Some implements ShortValueHolder, SomeValueHolder {
            private final short value;

            public Some(short value) {
                this.value = value;
            }

            @Override
            public short getValue() {
                return this.value;
            }

            @Override
            public int hashCode() {
                return Short.hashCode(this.getValue());
            }

            @Override
            public boolean equals(Object obj) {
                return (obj instanceof Some && this.getValue() == ((Some) obj).getValue())
                        || super.equals(obj);
            }
        }

        final class None implements ShortValueHolder, NoneValueHolder {
            private static final None INSTANCE = new None();

            private None() {
            }

            public static None getInstance() {
                return INSTANCE;
            }

            @Override
            public short getValue() {
                throw new UnsupportedOperationException("No value present");
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof None || super.equals(obj);
            }
        }
    }
}
