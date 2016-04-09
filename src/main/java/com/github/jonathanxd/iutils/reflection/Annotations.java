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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Created by jonathan on 27/01/16.
 */
public class Annotations {

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> Deque<T> getAll(Class<T> annotationClass, AnnotatedElement annotatedElement, QueryType queryType) {
        T[] values;
        if (queryType == QueryType.DECLARED) {
            values = annotatedElement.getDeclaredAnnotationsByType(annotationClass);
        } else {
            values = annotatedElement.getAnnotationsByType(annotationClass);
        }

        return new LinkedList<>(Arrays.asList(values));

    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> Optional<T> get(Class<T> annotationClass, AnnotatedElement annotatedElement, QueryType queryType) {
        T value;
        if (queryType == QueryType.DECLARED) {
            value = annotatedElement.getDeclaredAnnotation(annotationClass);
        } else {
            value = annotatedElement.getAnnotation(annotationClass);
        }

        return Optional.ofNullable(value);

    }
}
