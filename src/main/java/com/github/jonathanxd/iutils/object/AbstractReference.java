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

import com.github.jonathanxd.iutils.reflection.RClass;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.reflect.ParameterizedType;

/**
 * Created by jonathan on 08/04/16.
 */
public abstract class AbstractReference<T> extends Reference<T> {


    @SuppressWarnings("unchecked")
    public AbstractReference() {
        super();

        Reference<T> reference = (Reference<T>) TypeUtil.toReference(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        try {
            Reflection.changeFinalField(RClass.getRClass(Reference.class, this), "aClass", reference.getAClass());
            Reflection.changeFinalField(RClass.getRClass(Reference.class, this), "related", reference.getRelated());
            Reflection.changeFinalField(RClass.getRClass(Reference.class, this), "hold", reference.get());
        } catch (Exception e) {
            throw new Error(e);
        }

    }

}
