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
package com.github.jonathanxd.iutils.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by jonathan on 27/01/16.
 */
public class MethodSpecification {

    private final Optional<String> name;
    private final Optional<Class<?>[]> parameterTypes;
    private final Optional<Class<?>> returnType;

    public MethodSpecification(String name, Class<?>[] parameterTypes, Class<?> returnType) {
        this.name = Optional.ofNullable(name);
        this.parameterTypes = Optional.ofNullable(parameterTypes);
        this.returnType = Optional.ofNullable(returnType);
    }

    public boolean match(Method m) {
        m.setAccessible(true);
        if (this.name.isPresent()) {
            if (!m.getName().equals(this.name.get())) {
                return false;
            }
        }
        if (this.parameterTypes.isPresent()) {
            if (!Arrays.deepEquals(m.getParameterTypes(), parameterTypes.get())) {
                return false;
            }
        }
        if(this.returnType.isPresent()) {
            if(!m.getReturnType().equals(this.returnType.get())) {
                return false;
            }
        }
        return true;
    }
}
