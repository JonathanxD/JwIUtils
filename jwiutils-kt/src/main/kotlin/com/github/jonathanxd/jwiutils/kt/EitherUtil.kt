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
import com.github.jonathanxd.iutils.`object`.specialized.EitherObjBoolean

/**
 * Right value of [Either] or throw the left exception if the present value is on left side.
 */
val <R> Either<Exception, R>.rightOrFail: R
    get() = if (this.isLeft) throw this.left
    else this.right

/**
 * Right value of [Either] or throw the left exception if the present value is on left side.
 */
val EitherObjBoolean<Exception>.rightOrFail: Boolean
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
