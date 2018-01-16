/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;

public class MapLocalizedOperators {

    /**
     * Returns an operator that adds a line jump ({@code \n}) after each component (excluding the
     * last).
     *
     * @return Operator that adds a line jump ({@code \n}) after each component (excluding the
     * last).
     */
    public static UnaryOperator<List<TextComponent>> lineJump() {
        return MapLocalizedOperators.join(Text.of("\n"));
    }

    /**
     * Returns an operator that adds {@code toJoin} after each component (excluding the last).
     *
     * @param toJoin Text to add after each element.
     * @return Operator that adds {@code toJoin} after each component (excluding the last).
     */
    public static UnaryOperator<List<TextComponent>> join(TextComponent toJoin) {
        return new Join(toJoin);
    }

    /**
     * Returns an operator that extract a component at {@code index}.
     *
     * @param index Index to extract element.
     * @return Operator that extract a component at {@code index}.
     */
    public static UnaryOperator<List<TextComponent>> extract(int index) {
        return l -> Collections.singletonList(l.get(index));
    }

    static final class Join implements UnaryOperator<List<TextComponent>> {
        private final TextComponent toJoin;

        Join(TextComponent toJoin) {
            this.toJoin = toJoin;
        }

        @Override
        public List<TextComponent> apply(List<TextComponent> textComponents) {
            if (textComponents.size() > 1) {
                List<TextComponent> withLine = new ArrayList<>();

                Iterator<TextComponent> iterator = textComponents.iterator();

                while (iterator.hasNext()) {
                    withLine.add(iterator.next());
                    if (iterator.hasNext())
                        withLine.add(toJoin);
                }

                textComponents = withLine;
            }

            return textComponents;
        }
    }
}
