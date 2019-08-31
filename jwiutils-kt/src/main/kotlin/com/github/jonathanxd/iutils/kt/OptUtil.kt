/*
 *      JwIUtils-kt - Extension of JwIUtils for Kotlin <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.kt

import com.github.jonathanxd.iutils.`object`.Lazy
import com.github.jonathanxd.iutils.opt.specialized.*
import com.github.jonathanxd.iutils.opt.OptObject
import com.github.jonathanxd.iutils.opt.OptLazy

fun noneBoolean(): OptBoolean = OptBoolean.none()
fun noneByte(): OptByte = OptByte.none()
fun noneChar(): OptChar = OptChar.none()
fun noneDouble(): OptDouble = OptDouble.none()
fun noneFloat(): OptFloat = OptFloat.none()
fun noneInt(): OptInt = OptInt.none()
fun noneLong(): OptLong = OptLong.none()
fun noneShort(): OptShort = OptShort.none()
fun <T> noneLazy(): OptLazy<T> = OptLazy.none()
fun <T> noneObject(): OptObject<T> = OptObject.none()
fun <T> none(): OptObject<T> = OptObject.none()

fun someBoolean(value: Boolean): OptBoolean = OptBoolean.optBoolean(value)
fun someByte(value: Byte): OptByte = OptByte.optByte(value)
fun someChar(value: Char): OptChar = OptChar.optChar(value)
fun someDouble(value: Double): OptDouble = OptDouble.optDouble(value)
fun someFloat(value: Float): OptFloat = OptFloat.optFloat(value)
fun someInt(value: Int): OptInt = OptInt.optInt(value)
fun someLong(value: Long): OptLong = OptLong.optLong(value)
fun someShort(value: Short): OptShort = OptShort.optShort(value)
fun <T> someLazy(value: Lazy<T>): OptLazy<T> = OptLazy.optLazy(value)
fun <T> someObject(value: T): OptObject<T> = OptObject.optObject(value)

// Short Some
fun some(value: Boolean): OptBoolean = OptBoolean.optBoolean(value)
fun some(value: Byte): OptByte = OptByte.optByte(value)
fun some(value: Char): OptChar = OptChar.optChar(value)
fun some(value: Double): OptDouble = OptDouble.optDouble(value)
fun some(value: Float): OptFloat = OptFloat.optFloat(value)
fun some(value: Int): OptInt = OptInt.optInt(value)
fun some(value: Long): OptLong = OptLong.optLong(value)
fun some(value: Short): OptShort = OptShort.optShort(value)
fun <T> some(value: Lazy<T>): OptLazy<T> = OptLazy.optLazy(value)
fun <T> some(value: T): OptObject<T> = OptObject.optObject(value)
// /Short Some
