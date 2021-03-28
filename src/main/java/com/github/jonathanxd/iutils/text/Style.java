/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils.text;

import java.util.Objects;

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

    private Style() {
        this(false, false, false, false, false);
    }

    private Style(boolean obfuscated, boolean bold, boolean strikeThrough, boolean underline, boolean italic) {
        this.obfuscated = obfuscated;
        this.bold = bold;
        this.strikeThrough = strikeThrough;
        this.underline = underline;
        this.italic = italic;
    }

    // Public API
    public static Style createStyle(boolean obfuscated, boolean bold, boolean strikeThrough, boolean underline, boolean italic) {
        if (obfuscated && !(bold || strikeThrough || underline || italic))
            return Styles.OBFUSCATED;

        if (bold && !(obfuscated || strikeThrough || underline || italic))
            return Styles.BOLD;

        if (strikeThrough && !(bold || obfuscated || underline || italic))
            return Styles.STRIKE_THROUGH;

        if (underline && !(bold || obfuscated || strikeThrough || italic))
            return Styles.UNDERLINE;

        if (italic && !(bold || obfuscated || strikeThrough || underline))
            return Styles.ITALIC;

        if (!(bold || obfuscated || strikeThrough || underline))
            return Styles.RESET;

        return new Style(obfuscated, bold, strikeThrough, underline, italic);
    }

    public static Style getBold() {
        return Styles.BOLD;
    }

    // Private API

    static Style createReset() {
        return new Style();
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

    @Override
    public int hashCode() {
        return Objects.hash(this.isItalic(), this.isObfuscated(), this.isStrikeThrough(), this.isBold(), this.isUnderline());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Style))
            return super.equals(obj);

        return this.isItalic() == ((Style) obj).isItalic()
                && this.isObfuscated() == ((Style) obj).isObfuscated()
                && this.isStrikeThrough() == ((Style) obj).isStrikeThrough()
                && this.isBold() == ((Style) obj).isBold()
                && this.isUnderline() == ((Style) obj).isUnderline();
    }

    @Override
    public String toString() {
        if (obfuscated && !(bold || strikeThrough || underline || italic))
            return "Style[obfuscated]";

        if (bold && !(obfuscated || strikeThrough || underline || italic))
            return "Style[bold]";

        if (strikeThrough && !(bold || obfuscated || underline || italic))
            return "Style[strikeThrough]";

        if (underline && !(bold || obfuscated || strikeThrough || italic))
            return "Style[underline]";

        if (italic && !(bold || obfuscated || strikeThrough || underline))
            return "Style[italic]";

        if (!(bold || obfuscated || strikeThrough || underline))
            return "Style[reset]";

        return "Style[obfuscated=" + this.isObfuscated() + ", bold=" + this.isBold() + ", strikeThrough=" +
                this.isStrikeThrough() + ", underline=" + this.isUnderline() + ", italic=" + this.isItalic() + "]";
    }
}
