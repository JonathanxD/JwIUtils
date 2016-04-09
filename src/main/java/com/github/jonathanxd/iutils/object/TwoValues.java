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

import java.util.Objects;
import java.util.Optional;

import com.github.jonathanxd.iutils.extra.BaseContainer;

/**
 * Created by jonathan on 14/02/16.
 */
public class TwoValues<E, E2> {

    private final E value1;
    private final E2 value2;

    public TwoValues(E value1, E2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public TwoValues(BaseContainer<E> container1, BaseContainer<E2> container2) {
        this.value1 = container1.orElseThrow(() -> new NullPointerException("Empty container 1!"));
        this.value2 = container2.orElseThrow(() -> new NullPointerException("Empty container 2!"));
    }

    public TwoValues(Optional<E> container1, Optional<E2> container2) {
        this.value1 = container1.orElseThrow(() -> new NullPointerException("Empty optional 1!"));
        this.value2 = container2.orElseThrow(() -> new NullPointerException("Empty optional 2!"));
    }

    public E getValue1() {
        return value1;
    }

    public E2 getValue2() {
        return value2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }
}
