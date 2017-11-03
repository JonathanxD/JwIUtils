/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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

import java.util.Optional;

/**
 * Helper class for toString methods.
 *
 * {@link #prefix Prefix} and {@link #suffix suffix} is appended when the method {@link #toString()}
 * is called.
 *
 * {@link #delimiter Delimiter} is appended when {@link #append(CharSequence)} method is called, and
 * the {@link #toString()} removes the last {@link #delimiter} appended.
 */
public final class ToStringHelper {

    private final StringBuilder stringBuilder;
    private final int startSize;
    private final String delimiter;
    private final String prefix;
    private final String suffix;

    private ToStringHelper(String start, String delimiter, String prefix, String suffix) {
        this.stringBuilder = new StringBuilder(start != null ? start : "");
        this.startSize = start != null ? start.length() : 0;
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * Create a helper instance with recommended settings.
     *
     * @return Helper instance with recommended settings.
     */
    public static ToStringHelper defaultHelper() {
        return new ToStringHelper("", ", ", "{", "}");
    }

    /**
     * Create a helper instance with recommended settings and a initial string.
     *
     * @param start Initial string (neither prefix, delimiter and suffix is appended).
     * @return Instance with recommended settings and initial string.
     */
    public static ToStringHelper defaultHelper(String start) {
        return new ToStringHelper(start, ", ", "{", "}");
    }

    /**
     * Create a helper instance without start, delimiter, prefix and suffix.
     *
     * @return Helper instance without start, delimiter, prefix and suffix.
     */
    public static ToStringHelper helper() {
        return new ToStringHelper("", null, null, null);
    }

    /**
     * Create helper instance with initial string, and no delimiter, prefix and suffix.
     *
     * @param start Initial string.
     * @return Helper instance with initial string, and no delimiter, prefix and suffix.
     */
    public static ToStringHelper helper(String start) {
        return new ToStringHelper(start, null, null, null);
    }

    /**
     * Create helper instance with initial string and a delimiter.
     *
     * @param start     Initial string (delimiter will not be appended)
     * @param delimiter Delimiter.
     * @return Helper instance with initial string and a delimiter.
     */
    public static ToStringHelper helper(String start, String delimiter) {
        return new ToStringHelper(start, delimiter, null, null);
    }

    /**
     * Create helper instance with initial string, delimiter, prefix and suffix.
     *
     * @param start     Initial string (delimiter will not be appended)
     * @param delimiter Delimiter.
     * @param prefix    Prefix (string that is appended at start of the builder (after initial
     *                  string).
     * @param suffix    Suffix (string that is appended at end of the builder).
     * @return Helper instance with initial string and a delimiter.
     */
    public static ToStringHelper helper(String start, String delimiter, String prefix, String suffix) {
        return new ToStringHelper(start, delimiter, prefix, suffix);
    }

    /**
     * Add a 'key to value' string (append {@code null} if value is null).
     *
     * This method adds quote to string values.
     *
     * @param key   Key.
     * @param value Value.
     * @return this.
     */
    public ToStringHelper add(String key, Object value) {

        if (value instanceof String) {
            value = "\"" + value + "\"";
        }

        this.append(key + " = " + String.valueOf(value));
        return this;
    }

    /**
     * Add a 'key to value' string, this method will unbox de value from {@link Optional} if
     * present, or append {@code null} if not.
     *
     * @param key   Key.
     * @param value Value.
     * @return this.
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public ToStringHelper addOptional(String key, Optional<Object> value) {
        this.add(key, value.orElse("null"));
        return this;
    }

    /**
     * Append a sequence to helper.
     *
     * @param sequence Sequence.
     * @return This.
     */
    public ToStringHelper append(CharSequence sequence) {
        this.stringBuilder.append(sequence);
        this.appendDelimiter();
        return this;
    }

    /**
     * Append delimiter.
     */
    private void appendDelimiter() {
        if (delimiter != null) {
            this.stringBuilder.append(delimiter);
        }
    }

    /**
     * Returns a string representation of this string helper.
     */
    @Override
    public String toString() {

        String s;
        int pos = this.stringBuilder.length();

        if (prefix != null) {
            this.stringBuilder.insert(this.startSize, prefix);
        }

        if (delimiter != null) {
            pos = this.stringBuilder.length() - this.delimiter.length();
        }

        if (suffix != null) {
            this.stringBuilder.insert(pos, suffix);
            pos += suffix.length();
        }

        if (delimiter != null) {
            s = this.stringBuilder.substring(0, pos);
        } else {
            s = this.stringBuilder.toString();
        }

        return s;
    }
}
