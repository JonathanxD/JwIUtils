/*
 *      JwIUtils-Properties - Properties module of JwIUtils <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.property;

import com.github.jonathanxd.iutils.property.value.CurrentValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class ValueHelper {

    // Primitive types

    public static UnaryOperator<Boolean> negateBooleanOp() {
        return b -> !b;
    }

    public static UnaryOperator<Character> incrementCharOp() {
        return ValueHelper.incrementCharOp((char) 1);
    }

    public static UnaryOperator<Byte> incrementByteOp() {
        return ValueHelper.incrementByteOp((byte) 1);
    }

    public static UnaryOperator<Short> incrementShortOp() {
        return ValueHelper.incrementShortOp((short) 1);
    }

    public static UnaryOperator<Integer> incrementIntOp() {
        return ValueHelper.incrementIntOp(1);
    }

    public static UnaryOperator<Long> incrementLongOp() {
        return ValueHelper.incrementLongOp(1L);
    }

    public static UnaryOperator<Float> incrementFloatOp() {
        return ValueHelper.incrementFloatOp(1.0F);
    }

    public static UnaryOperator<Double> incrementDoubleOp() {
        return ValueHelper.incrementDoubleOp(1.0D);
    }

    public static UnaryOperator<BigInteger> incrementBigIntOp() {
        return ValueHelper.incrementBigIntOp(BigInteger.ONE);
    }

    public static UnaryOperator<BigDecimal> incrementBigDecOp() {
        return ValueHelper.incrementBigDecOp(BigDecimal.ONE);
    }

    // N

    public static UnaryOperator<Character> incrementCharOp(char n) {
        return c -> (char) (c + n);
    }

    public static UnaryOperator<Byte> incrementByteOp(byte n) {
        return c -> (byte) (c + n);
    }

    public static UnaryOperator<Short> incrementShortOp(short n) {
        return c -> (short) (c + n);
    }

    public static UnaryOperator<Integer> incrementIntOp(int n) {
        return c -> c + n;
    }

    public static UnaryOperator<Long> incrementLongOp(long n) {
        return c -> c + n;
    }

    public static UnaryOperator<Float> incrementFloatOp(float n) {
        return c -> c + n;
    }

    public static UnaryOperator<Double> incrementDoubleOp(double n) {
        return c -> c + n;
    }

    public static UnaryOperator<BigInteger> incrementBigIntOp(BigInteger n) {
        return c -> c.add(n);
    }

    public static UnaryOperator<BigDecimal> incrementBigDecOp(BigDecimal n) {
        return c -> c.add(n);
    }

    // Operations

    public static <T> Consumer<? super CurrentValue<T>> apply(UnaryOperator<T> op) {
        return value -> value.apply(op);
    }
}
