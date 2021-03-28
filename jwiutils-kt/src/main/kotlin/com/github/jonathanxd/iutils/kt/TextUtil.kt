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
package com.github.jonathanxd.iutils.kt

import com.github.jonathanxd.iutils.localization.Locale
import com.github.jonathanxd.iutils.text.Text
import com.github.jonathanxd.iutils.text.TextComponent
import com.github.jonathanxd.iutils.text.TextUtil
import com.github.jonathanxd.iutils.text.localizer.Localizer
import com.github.jonathanxd.iutils.text.localizer.SingleLocalizer
import com.github.jonathanxd.iutils.text.localizer.TextLocalizer

/**
 * Creates a string text component.
 */
fun String.asText() = Text.single(this)

/**
 * Parses to text representation using [TextUtil.parse].
 */
fun String.toText() = TextUtil.parse(this)

/**
 * Creates a localizable component.
 */
fun String.asLocalizableText() = Text.localizable(this)

/**
 * Creates a variable component.
 */
fun String.asTextVariable() = Text.variable(this)

/**
 * Creates a text representation and apply [args] to it.
 */
fun String.applyTextArgs(args: Map<String, TextComponent>) = this.toText().apply(args)

/**
 * Creates a string text component.
 */
fun textOf(str: String) = Text.single(str)

/**
 * Creates a text component from string representation.
 */
fun parseText(str: String) = TextUtil.parse(str)

/**
 * Creates a localizable component.
 */
fun localizableText(localization: String) = Text.localizable(localization)

/**
 * Creates a variable component.
 */
fun variableText(variable: String) = Text.variable(variable)

operator fun TextComponent.plus(other: TextComponent): TextComponent = this.append(other)
operator fun TextComponent.plus(other: Any): TextComponent = this.append(other)

operator fun TextLocalizer.get(component: TextComponent): String =
        this.localize(component)

operator fun TextLocalizer.get(component: TextComponent, locale: Locale): String =
        this.localize(component, locale)

operator fun SingleLocalizer.get(component: TextComponent): String =
        this.localize(component)

operator fun Localizer.get(component: TextComponent): String =
        this.localize(component)
