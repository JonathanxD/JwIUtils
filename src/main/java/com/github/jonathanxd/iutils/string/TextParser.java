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

import com.github.jonathanxd.iutils.text.Text;
import com.github.jonathanxd.iutils.text.TextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsers localization value into {@link TextComponent}.
 */
public class TextParser {

    public static TextComponent parse(String receiver) {
        List<TextComponent> components = new ArrayList<>();
        char[] chars = receiver.toCharArray();
        boolean lastIsEscape = false;
        boolean isVar = false;
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;

        for (char aChar : chars) {
            if (aChar == '\\') {
                if (lastIsEscape) {
                    stringBuilder.append(aChar);
                    lastIsEscape = false;
                } else {
                    lastIsEscape = true;
                }
            } else if (!Character.isUnicodeIdentifierPart(aChar)
                    && isVar
                    && !lastIsEscape) {
                String text = stringBuilder.toString();
                stringBuilder.setLength(0);
                components.add(Text.variable(text));
                isVar = false;
                stringBuilder.append(aChar);
            } else if (aChar == '$' && !lastIsEscape) {
                String text = stringBuilder.toString();
                stringBuilder.setLength(0);

                if (isVar) {
                    isVar = false;
                    components.add(Text.variable(text));
                } else {
                    isVar = true;
                    components.add(Text.of(text));
                }
            } else {
                stringBuilder.append(aChar);
            }
            ++index;
        }

        if (stringBuilder.length() != 0) {
            String text = stringBuilder.toString();
            stringBuilder.setLength(0);
            if (isVar) {
                components.add(Text.variable(text));
            } else {
                components.add(Text.of(text));
            }
        }

        return Text.of(components);
    }


}
