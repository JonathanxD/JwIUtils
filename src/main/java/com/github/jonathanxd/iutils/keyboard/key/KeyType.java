/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
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
package com.github.jonathanxd.iutils.keyboard.key;

/**
 * Type of keys.
 */
public enum KeyType {

    /**
     * Manipulation keys, like space, enter, insert, etc...
     */
    MANIPULATION,

    /**
     * Control keys, like shift, break, etc, etc...
     */
    CONTROL,

    /**
     * Keyboard state key like caps lock, num lock, scroll lock, etc...
     */
    KEY_BOARD_STATES,

    /**
     * Position keys like direction arrows, page up, page down, etc...
     */
    POSITION,

    /**
     * Numbers (not num-pad numbers).
     */
    NUMBERS,

    /**
     * Num-pad numbers.
     */
    NUMPAD_NUMBERS,

    /**
     * Alphabet characters.
     */
    ALPHABET,

    /**
     * Super keys (left and right).
     */
    SUPER_KEYS,

    /**
     * Tool key
     */
    TOOL,

    /**
     * Math keys like plus, minus, decimal point (.)...
     */
    MATH_MANIPULATION,

    /**
     * Function keys (F1-F12)
     */
    FUNCTION,

    /**
     * Other keys like comma, period, bracket, quotes, right slash.
     */
    OTHERS
}
