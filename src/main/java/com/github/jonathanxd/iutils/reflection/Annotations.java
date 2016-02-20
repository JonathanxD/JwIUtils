/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) https://github.com/JonathanxD/ <jonathan.scripter@programmer.net>
 *
 * 	GNU GPLv3
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
