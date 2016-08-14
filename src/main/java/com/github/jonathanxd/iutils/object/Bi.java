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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.containers.BaseContainer;

import java.util.Objects;

/**
 * Created by jonathan on 28/05/16.
 */
public class Bi<T1, T2> {
    private final T1 value1;
    private final T2 value2;

    public Bi(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Bi(BaseContainer<T1> value1, BaseContainer<T2> value2) {
        this(value1.getOrElse(null), value2.getOrElse(null));
    }

    public T1 _1() {
        return value1;
    }

    public T2 _2() {
        return value2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Bi<?, ?>)
            return this._1().equals(((Bi) obj)._1()) && this._2().equals(((Bi) obj)._2());

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._1(), this._2());
    }
}
