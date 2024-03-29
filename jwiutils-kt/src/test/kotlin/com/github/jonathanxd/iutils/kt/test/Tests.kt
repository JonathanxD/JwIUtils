/*
 *      JwIUtils-kt - Extension of JwIUtils for Kotlin <https://github.com/JonathanxD/JwIUtils/>
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
package com.github.jonathanxd.iutils.kt.test

import com.github.jonathanxd.iutils.kt.left
import com.github.jonathanxd.iutils.kt.right
import com.github.jonathanxd.iutils.kt.some
import com.github.jonathanxd.iutils.kt.typeInfo
import org.junit.Assert
import org.junit.Test

class Tests {

    @Test
    fun testOpt() {
        val value = some(9)

        Assert.assertTrue(value.isPresent)
    }

    @Test
    fun testEither() {
        val value = left<Int, String>(9)
        val value2 = right<Int, String>("9")

        Assert.assertTrue(value.isLeft)
        Assert.assertTrue(value2.isRight)
    }

    @Test
    fun testTInfo() {
        val t = typeInfo<Map<String, Map<String, String>>>()

        Assert.assertEquals("java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>", t.toFullString())
    }
}