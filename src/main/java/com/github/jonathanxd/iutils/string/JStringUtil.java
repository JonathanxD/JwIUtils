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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jonathan on 27/05/16.
 */
public class JStringUtil {

    private static final Pattern VARIABLE_FORMAT_REFEX = Pattern.compile("\\$((?<svar>\\w+)|(\\{(?<var>\\w+)\\s*(\\.?\\s*(?<access>[^}]*))\\})?)");

    public static String apply(String original, Map<String, Object> variables) {

        Replacing replacing = new Replacing(original);

        Matcher matcher = VARIABLE_FORMAT_REFEX.matcher(original);

        while (matcher.find()) {
            String svar = matcher.group("svar");
            int start = matcher.start();
            int end = matcher.end();

            if (svar != null && !svar.isEmpty()) {

                Object val = variables.get(svar);

                if (val != null) {
                    replacing.replace(start, end, val.toString());

                    /*return apply(
                            matcher.replaceFirst(val.toString()),
                            variables);*/
                }
            } else {

                String var = matcher.group("var");
                String access = matcher.group("access");

                Object val = variables.get(var);

                if (val != null) {
                    if (access == null || access.isEmpty()) {

                        replacing.replace(start, end, val.toString());

                        /*return apply(
                                matcher.replaceFirst(val.toString()),
                                variables);*/
                    } else {
                        Object o = SimpleStringExpression.executeExpression(var + SimpleStringExpression.METHOD_INVOKE_SYMBOL + access, variables);
                        replacing.replace(start, end, o.toString());
                        /*return apply(
                                matcher.replaceFirst(o.toString()),
                                variables);*/
                    }
                } else {

                }
            }
        }

        return replacing.toString();
    }

    private static String rangeReplace(String target, int start, int end, String replacement) {
        StringBuilder sb = new StringBuilder(target);

        sb.replace(start, end, replacement);

        return sb.toString();
    }

    static final class Replacing {

        private final StringBuilder replaced;

        private int offset = 0;

        Replacing(String str) {
            this.replaced = new StringBuilder(str);
        }

        public void replace(int start, int end, String replacement) {
            replaced.replace(start-offset, end-offset, replacement);

            offset = (end - start) - replacement.length();
        }

        @Override
        public String toString() {
            return replaced.toString();
        }
    }
}
