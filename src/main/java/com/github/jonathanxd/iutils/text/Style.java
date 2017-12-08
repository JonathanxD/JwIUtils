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
package com.github.jonathanxd.iutils.text;

/**
 * Style, this may or may not be supported by the receiver of the text, unsupported styles are
 * commonly translated to default style, which is determined by the receiver.
 */
public class Style implements TextComponent {

    private final boolean obfuscated;
    private final boolean bold;
    private final boolean strikeThrough;
    private final boolean underline;
    private final boolean italic;

    public Style() {
        this(false, false, false, false, false);
    }

    public Style(boolean obfuscated, boolean bold, boolean strikeThrough, boolean underline, boolean italic) {
        this.obfuscated = obfuscated;
        this.bold = bold;
        this.strikeThrough = strikeThrough;
        this.underline = underline;
        this.italic = italic;
    }

    static Style createObfuscated() {
        return new Style(true, false, false, false, false);
    }

    static Style createBold() {
        return new Style(false, true, false, false, false);
    }

    static Style createStrikeThrough() {
        return new Style(false, false, true, false, false);
    }

    static Style createUnderline() {
        return new Style(false, false, false, true, false);
    }

    static Style createItalic() {
        return new Style(false, false, false, false, true);
    }

    public boolean isObfuscated() {
        return this.obfuscated;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isStrikeThrough() {
        return this.strikeThrough;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public boolean isItalic() {
        return this.italic;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
