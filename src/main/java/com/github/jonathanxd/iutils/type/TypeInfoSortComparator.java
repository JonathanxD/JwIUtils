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
package com.github.jonathanxd.iutils.type;

import com.github.jonathanxd.iutils.reflection.ClassUtil;

import java.util.Comparator;
import java.util.List;

/**
 * Comparator that compares {@link TypeInfo} by inheritance level.
 */
public class TypeInfoSortComparator implements Comparator<TypeInfo<?>> {

    private final boolean returnZeroForEquality;

    /**
     * Constructs a {@link TypeInfo} comparator, which compares by inheritance.
     *
     * @param returnZeroForEquality Whether the comparator should return {@code 0} or not when both
     *                              {@link TypeInfo} have same inheritance level. In some cases,
     *                              using {@code false} may cause the comparator to change the order
     *                              of elements when they have same inheritance level.
     */
    public TypeInfoSortComparator(boolean returnZeroForEquality) {
        this.returnZeroForEquality = returnZeroForEquality;
    }

    /**
     * Constructs the comparator with {@code true} to {@link #returnZeroForEquality}.
     */
    public TypeInfoSortComparator() {
        this(true);
    }

    @Override
    public int compare(TypeInfo<?> o1, TypeInfo<?> o2) {
        if (o1.equals(o2) && this.returnZeroForEquality)
            return 0;

        final Class<?> o1TypeClass = o1.getTypeClass();
        final Class<?> o2TypeClass = o2.getTypeClass();

        final List<TypeInfo<?>> o1TypeParameters = o1.getTypeParameters();
        final List<TypeInfo<?>> o2TypeParameters = o2.getTypeParameters();

        if (o1TypeParameters.size() == o2TypeParameters.size()) {
            int comp = 0;

            if (o1TypeClass.equals(o2TypeClass) && o1TypeParameters.size() > 0) {
                for (int x = 0; x < o1TypeParameters.size(); ++x) {
                    comp += this.compare(o1TypeParameters.get(x), o2TypeParameters.get(x));
                }

                if (comp > 0)
                    comp = 1;
                else if (comp < 0)
                    comp = -1;

            } else {
                return ClassUtil.compare(o1TypeClass, o2TypeClass);
            }

            return comp == 0 && !this.returnZeroForEquality ? -1 : comp;
        }

        return ClassUtil.compare(o1TypeClass, o2TypeClass);
    }

}
