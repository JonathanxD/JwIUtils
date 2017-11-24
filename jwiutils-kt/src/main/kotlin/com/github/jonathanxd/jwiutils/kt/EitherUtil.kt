/*
 *      JwIUtils-kt - Extension of JwIUtils for Kotlin <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.jwiutils.kt

import com.github.jonathanxd.iutils.`object`.Either
import com.github.jonathanxd.iutils.`object`.specialized.*

/**
 * Right value of [Either] or throw the left exception if the present value is on left side.
 */
val <R> Either<Exception, R>.rightOrFail: R
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Left value of [Either] or throw the right exception if the present value is on right side.
 */
val <L> Either<L, Exception>.leftOrFail: L
    get() = if (this.isRight) throw this.right
    else this.left

/**
 * Right value of [EitherObjBoolean] or throw the left exception if the present value is on left side.
 */
val EitherObjBoolean<Exception>.rightOrFail: Boolean
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [EitherObjByte] or throw the left exception if the present value is on left side.
 */
val EitherObjByte<Exception>.rightOrFail: Byte
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [EitherObjChar] or throw the left exception if the present value is on left side.
 */
val EitherObjChar<Exception>.rightOrFail: Char
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [EitherObjDouble] or throw the left exception if the present value is on left side.
 */
val EitherObjDouble<Exception>.rightOrFail: Double
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [EitherObjFloat] or throw the left exception if the present value is on left side.
 */
val EitherObjFloat<Exception>.rightOrFail: Float
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [EitherObjFloat] or throw the left exception if the present value is on left side.
 */
val EitherObjInt<Exception>.rightOrFail: Int
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [EitherObjFloat] or throw the left exception if the present value is on left side.
 */
val EitherObjLong<Exception>.rightOrFail: Long
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [EitherObjFloat] or throw the left exception if the present value is on left side.
 */
val EitherObjShort<Exception>.rightOrFail: Short
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Creates a [Either] with left side with value.
 */
fun <L, R> left(left: L): Either<L, R> = Either.left(left)

/**
 * Creates a [Either] with right side with value.
 */
fun <L, R> right(right: R): Either<L, R> = Either.right(right)

/**
 * Gets [Either.left], if [Either.isLeft] or [Either.right] if [Either.isRight].
 */
val <A, L : A, R : A> Either<L, R>.leftOrRight: A
    get() = when {
        this.isLeft -> this.left
        this.isRight -> this.right
        else -> throw IllegalStateException("What?")
    }

/**
 * Gets [Either.left], if [Either.isLeft] or [Either.right] if [Either.isRight].
 */
inline fun <A, L : A, R : A> Either<L, R>.consumeLeftOrRight(consumer: (A) -> Unit) =
        when {
            this.isLeft -> consumer(this.left)
            this.isRight -> consumer(this.right)
            else -> Unit
        }

/**
 * Inline version of [Either.mapRight]
 */
inline fun <V, L, R> Either<L, R>.mapRightSide(mapper: (R) -> V): Either<L, V> =
    if (this.isRight) right(mapper(this.right))
    else left(this.left)

/**
 * Inline version of [Either.mapLeft]
 */
inline fun <V, L, R> Either<L, R>.mapLeftSide(mapper: (L) -> V): Either<V, R> =
        if (this.isLeft) left(mapper(this.left))
        else right(this.right)

/**
 * Inline version of [Either.map]
 */
inline fun <L, R, L2, R2> Either<L, R>.mapLeftOrRight(leftMapper: (L) -> L2,
                                                      rightMapper: (R) -> R2): Either<L2, R2> =
        if (this.isLeft) left(leftMapper(this.left))
        else right(rightMapper(this.right))

/**
 * Inline version of [Either.ifRight]
 */
inline fun <L, R> Either<L, R>.ifRightSide(consumer: (R) -> Unit) =
        if (this.isRight) consumer(this.right)
        else Unit

/**
 * Inline version of [Either.ifLeft]
 */
inline fun <L, R> Either<L, R>.ifLeftSide(consumer: (L) -> Unit) =
        if (this.isLeft) consumer(this.left)
        else Unit


// Specialized

// Boolean

/**
 * Creates a [EitherObjBoolean] with left side with value.
 */
fun <L> leftBooleanObj(left: L): EitherObjBoolean<L> = EitherObjBoolean.left(left)

/**
 * Creates a [EitherObjBoolean] with right side with value.
 */
fun <L> right(right: Boolean): EitherObjBoolean<L> = EitherObjBoolean.right(right)

// Byte

/**
 * Creates a [EitherObjByte] with left side with value.
 */
fun <L> leftByteObj(left: L): EitherObjByte<L> = EitherObjByte.left(left)

/**
 * Creates a [EitherObjByte] with right side with value.
 */
fun <L> right(right: Byte): EitherObjByte<L> = EitherObjByte.right(right)

// Char

/**
 * Creates a [EitherObjChar] with left side with value.
 */
fun <L> leftCharObj(left: L): EitherObjChar<L> = EitherObjChar.left(left)

/**
 * Creates a [EitherObjChar] with right side with value.
 */
fun <L> right(right: Char): EitherObjChar<L> = EitherObjChar.right(right)

// Double

/**
 * Creates a [EitherObjDouble] with left side with value.
 */
fun <L> leftDoubleObj(left: L): EitherObjDouble<L> = EitherObjDouble.left(left)

/**
 * Creates a [EitherObjDouble] with right side with value.
 */
fun <L> right(right: Double): EitherObjDouble<L> = EitherObjDouble.right(right)

// Float

/**
 * Creates a [EitherObjFloat] with left side with value.
 */
fun <L> leftFloatObj(left: L): EitherObjFloat<L> = EitherObjFloat.left(left)

/**
 * Creates a [EitherObjFloat] with right side with value.
 */
fun <L> right(right: Float): EitherObjFloat<L> = EitherObjFloat.right(right)

// Int

/**
 * Creates a [EitherObjInt] with left side with value.
 */
fun <L> leftIntObj(left: L): EitherObjInt<L> = EitherObjInt.left(left)

/**
 * Creates a [EitherObjInt] with right side with value.
 */
fun <L> right(right: Int): EitherObjInt<L> = EitherObjInt.right(right)

// Long

/**
 * Creates a [EitherObjLong] with left side with value.
 */
fun <L> leftLongObj(left: L): EitherObjLong<L> = EitherObjLong.left(left)

/**
 * Creates a [EitherObjLong] with right side with value.
 */
fun <L> right(right: Long): EitherObjLong<L> = EitherObjLong.right(right)

// Short

/**
 * Creates a [EitherObjShort] with left side with value.
 */
fun <L> leftShortObj(left: L): EitherObjShort<L> = EitherObjShort.left(left)

/**
 * Creates a [EitherObjShort] with right side with value.
 */
fun <L> right(right: Short): EitherObjShort<L> = EitherObjShort.right(right)

// All Specialized

// ..Later..