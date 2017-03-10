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

import com.github.jonathanxd.iutils.map.MapUtils;

import java.util.Collections;
import java.util.Map;

/**
 * A simple String that supports expression evaluation.
 *
 * Expressions must be added between
 * <pre>${</pre>
 * and
 * <br>
 * </pre>}</pre>.
 *
 * A map with available instances must be provided.
 */
public class JString implements CharSequence {

    /**
     * Original string.
     */
    private final String original;

    /**
     * Evaluated string.
     */
    private final String evaluated;

    /**
     * Creates a simple JString without provided variables.
     *
     * @param string Original string.
     */
    public JString(String string) {
        this(string, Collections.emptyMap());
    }

    /**
     * Creates a JString and provide variables.
     *
     * @param string    Original string.
     * @param variables Provided variables.
     * @see MapUtils#mapOf(Object...)
     */
    public JString(String string, Object... variables) {
        this(string, MapUtils.mapOf(variables));
    }

    /**
     * Creates a JString and provide variables.
     *
     * @param string    Original string.
     * @param variables Provided variables.
     */
    public JString(String string, Map<String, Object> variables) {
        this.original = string;
        this.evaluated = JStringUtil.evaluate(this.original, variables);
    }

    /**
     * Creates a JString and provide variables.
     *
     * @param string    Original string.
     * @param variables Provided variables.
     * @return New {@link JString}.
     * @see MapUtils#mapOf(Object...)
     */
    public static JString of(String string, Object... variables) {
        return new JString(string, variables);
    }

    @Override
    public int length() {
        return this.getEvaluated().length();
    }

    @Override
    public char charAt(int index) {
        return this.getEvaluated().charAt(length());
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.getEvaluated().subSequence(start, end);
    }

    /**
     * Gets the original string.
     *
     * @return Original string.
     */
    public String getOriginal() {
        return this.original;
    }

    /**
     * Gets the evaluated string.
     *
     * @return Evaluated string.
     */
    public String getEvaluated() {
        return this.evaluated;
    }

    @Override
    public String toString() {
        return this.getEvaluated();
    }

}
