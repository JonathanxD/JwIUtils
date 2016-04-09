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
package com.github.jonathanxd.iutils.file;

import com.github.jonathanxd.iutils.object.ObjectUtils;

import java.io.File;

import static com.github.jonathanxd.iutils.object.ObjectUtils.deepFields;

/**
 * Created by jonathan on 16/02/16.
 */
public class NonexistentFileException extends RuntimeException {

    public NonexistentFileException(File file) {
        super(to(file));
    }

    public NonexistentFileException(File file, String message) {
        super(to(file, message));
    }

    public NonexistentFileException(File file, String message, Throwable cause) {
        super(to(file, message), cause);
    }

    public NonexistentFileException(File file, Throwable cause) {
        super(to(file), cause);
    }

    public NonexistentFileException(File file, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(to(file, message), cause, enableSuppression, writableStackTrace);
    }

    private static String to(File file) {
        return ObjectUtils.deepFields(file).toString();
    }

    private static String to(File file, String message) {
        return message + "(" + ObjectUtils.deepFields(file).toString() + ")";
    }
}
