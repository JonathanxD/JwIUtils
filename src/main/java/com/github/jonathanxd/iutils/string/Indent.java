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
package com.github.jonathanxd.iutils.string;

/**
 * Indentation helper
 */
public final class Indent {

    /**
     * Number of spaces, example, 2, 4, 6, 8, etc...
     */
    private final int indentations;
    /**
     * Builder with indentations.
     */
    private final StringBuilder stringBuilder = new StringBuilder();
    /**
     * Amount of indentations.
     */
    private int amount = 0;

    /**
     * Constructs a indentation helper.
     *
     * @param indentations Number of spaces.
     */
    public Indent(int indentations) {
        this.indentations = indentations;
    }

    /**
     * Adds indentation.
     *
     * @return this.
     */
    public Indent addIndent() {
        this.amount++;

        for (int i = 0; i < this.indentations; i++) {
            this.stringBuilder.append(' ');
        }

        return this;
    }

    /**
     * Removes indentation.
     *
     * @return this.
     */
    public Indent removeIndent() {
        this.amount--;
        this.stringBuilder.setLength(this.stringBuilder.length() - this.indentations);

        return this;
    }

    /**
     * Creates a copy of this {@link Indent} with same {@link #amount}.
     *
     * @return A copy of this indent.
     */
    public Indent copy() {
        Indent indent = new Indent(this.indentations);

        indent.amount = this.amount;

        return indent;
    }

    /**
     * Build indentation string.
     *
     * @return Built indentation string.
     */
    public String buildIndentString() {
        return this.stringBuilder.toString();
    }

    public String appendAndReturn(String s) {
        return this.getIndentationBuilder().append(s).toString();
    }

    /**
     * Gets indentation builder (new instance).
     *
     * @return Gets indentation builder with indentation spaces (new instance).
     */
    public StringBuilder getIndentationBuilder() {
        return new StringBuilder(this.stringBuilder);
    }

    @Override
    public String toString() {
        return this.buildIndentString();
    }
}
