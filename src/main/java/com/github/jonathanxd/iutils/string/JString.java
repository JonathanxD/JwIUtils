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
package com.github.jonathanxd.iutils.string;

import com.github.jonathanxd.iutils.construct.Properties;
import com.github.jonathanxd.iutils.map.MapUtils;

import java.util.Collections;
import java.util.Map;

/**
 * Created by jonathan on 27/05/16.
 */
public class JString implements CharSequence {

    private final String original;
    private final String replaced;

    public JString(String string) {
        this(string, Collections.emptyMap());
    }

    public JString(String string, Object... variables) {
        this(string, MapUtils.mapOf(variables));
    }

    public JString(String string, Properties props) {
        this(string, props.toMap());
    }

    public JString(String string, Map<String, Object> variables) {
        this.original = string;
        this.replaced = JStringUtil.apply(this.original, variables);
    }

    public static JString of(String string, Object... variables) {
        return new JString(string, variables);
    }

    @Override
    public int length() {
        return replaced.length();
    }

    @Override
    public char charAt(int index) {
        return replaced.charAt(length());
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return replaced.subSequence(start, end);
    }

    public String getOriginal() {
        return original;
    }

    public String getReplaced() {
        return replaced;
    }

    @Override
    public String toString() {
        return replaced;
    }
}
