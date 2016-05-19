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
package com.github.jonathanxd.iutils.construct;

import com.github.jonathanxd.iutils.construct.annotation.Names;
import com.github.jonathanxd.iutils.construct.annotation.Property;
import com.github.jonathanxd.iutils.construct.annotation.PropertyContainer;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by jonathan on 02/05/16.
 */
public class Constructor {

    public static <T extends Constructable<T>> T create(Class<T> tClass, Properties properties) {
        return invokeConstructors(tClass, properties);
    }

    public static <T extends Constructable<T>> T create(Class<T> tClass, Prop... properties) {
        return create(tClass, new Properties(properties));
    }

    private static <T> T invoke(Set<Executable> executables, Properties props) {
        return invoke(executables, props, null);
    }

    @SuppressWarnings("unchecked")
    private static <T> T invoke(Set<Executable> executables, Properties props, Object instance) {
        List<Object> execParameters = new ArrayList<>();


        for (Executable executable : executables) {

            Parameter[] parameters = executable.getParameters();
            Annotation[][] parameterAnnotations = executable.getParameterAnnotations();

            String[] ids = new String[parameters.length];
            boolean[] required = new boolean[parameters.length];
            Class<?>[] types = new Class<?>[parameters.length];

            Names name = executable.getDeclaredAnnotation(Names.class);

            for (int i = 0; i < parameters.length; i++) {

                Annotation[] annotations = parameterAnnotations[i];
                Parameter parameter = parameters[i];
                types[i] = parameter.getType();

                Property[] annotation = Reflection.findAnnotations(Property.class, annotations);

                if (annotation.length == 0) {
                    if (name != null) {
                        String s = name.value()[i];
                        ids[i] = s;
                    } else {
                        ids[i] = null;
                    }
                    required[i] = false;
                } else {
                    PropertyContainer from = PropertyContainer.from(annotation[0], parameter.getType());

                    ids[i] = from.getPropertyId();
                    required[i] = from.isRequired();
                }
            }

            insert(ids, required, types, props, execParameters);

            if (!execParameters.isEmpty()) {
                return (T) Reflection.execute(executable, execParameters, instance);
            }

            execParameters.clear();
        }

        return null;
    }

    private static void insert(String[] names, boolean[] requireds, Class<?>[] types, Properties props, List<Object> execParameters) {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Class<?> type = types[i];
            boolean required = requireds.length > 0 && requireds[i];

            if (name == null) {
                if (!required) {
                    execParameters.add(null);
                } else {
                    execParameters.clear();
                    break;
                }
            }

            PropertyContainer from = new PropertyContainer(name, required, type);

            Object get = props.get(from);

            try {
                execParameters.add(get);
            } catch (CannotFindPropertyException e) {
                if (from.isRequired()) {
                    execParameters.clear();
                    break;
                } else {
                    throw e;
                }
            }
        }
    }

    private static <T> T invokeArray(Class<T> tClass, Properties properties, Executable[] executables) {
        Set<Executable> constructorSet = new TreeSet<>((c1, c2) -> Integer.compare(c1.getParameterCount(), c2.getParameterCount()));

        for (Executable executable : executables) {
            constructorSet.add(executable);
        }

        return invoke(constructorSet, properties);
    }

    private static <T> T invokeConstructors(Class<T> tClass, Properties properties) {

        return invokeArray(tClass, properties, tClass.getConstructors());
    }

}
