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

import com.github.jonathanxd.iutils.exception.JStringApplyException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JString parser utility.
 */
public final class JStringUtil {

    private static final Pattern VARIABLE_FORMAT_REFEX = Pattern.compile("\\$((?<svar>\\w+)|(\\{(?<var>[^.}]+)\\s*(\\.\\s*(?<access>[^}]*))?\\})?)");

    private JStringUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Evaluates all expressions in {@code original} string and returns evaluated string.
     *
     * @param original  Original string.
     * @param variables Variables.
     * @return Evaluated string.
     */
    public static String evaluate(String original, Map<String, Object> variables) {

        StringBuffer replacing = new StringBuffer(original.length());

        Matcher matcher = VARIABLE_FORMAT_REFEX.matcher(original);

        while (matcher.find()) {
            String svar = matcher.group("svar");

            String toReplace = svar;

            if (svar != null && !svar.isEmpty()) {

                Object val = variables.get(svar);

                if (val != null) {
                    toReplace = val.toString();
                }
            } else {

                String var = matcher.group("var");
                String access = matcher.group("access");

                String inMap = var + "." + access;

                if (variables.containsKey(inMap)) {
                    toReplace = (String) variables.get(inMap);
                } else {

                    Object val = variables.get(var);

                    if (val != null) {
                        if (access == null || access.isEmpty()) {
                            toReplace = val.toString();
                        } else {
                            Object o = SimpleStringExpression.evaluateExpression(var + SimpleStringExpression.METHOD_INVOKE_SYMBOL + access, variables);

                            toReplace = o.toString();
                        }
                    }
                }
            }
            try {
                matcher.appendReplacement(replacing, Matcher.quoteReplacement(toReplace));
            } catch (Exception e) {
                throw new JStringApplyException("Failed to process String: '" + original + "'. group: '" + matcher.group() + "'. matcher: '" + matcher + "'.", e);
            }
        }

        matcher.appendTail(replacing);

        return replacing.toString();
    }

}
