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
package com.github.jonathanxd.iutils.function.reflection;

import com.github.jonathanxd.iutils.extra.IMutableContainer;
import com.github.jonathanxd.iutils.extra.MutableContainer;
import com.github.jonathanxd.iutils.reflection.MethodSpecification;
import com.github.jonathanxd.iutils.reflection.Reflection;

import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * Created by jonathan on 10/03/16.
 */
public class Handler {

    public static <T> T get(Class<?> clazz, Object instance, String name, int argumentIndex, Object... arguments) {
        return get(clazz, instance, new MethodSpecification(name, null, null), argumentIndex, arguments);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<?> clazz, Object instance, MethodSpecification methodSpecification, int argumentIndex, Object... arguments) {

        if(argumentIndex <= -1)
            throw new RuntimeException();

        Method m = Reflection.getMethodByParam(clazz, methodSpecification, arguments);

        Class<?> type = m.getParameterTypes()[argumentIndex];

        Object[] args = new Object[argumentIndex+1 + arguments.length];

        ReturnConsumer returnConsumer = new ReturnConsumer();

        if(Consumer.class.isAssignableFrom(type)) {
            args[argumentIndex] = returnConsumer;
        }

        int pos = 0;

        for(int x = 0; x < args.length; ++x) {
            if(x != argumentIndex) {
                args[x] = arguments[pos];
                ++pos;
            }
        }
        Reflection.invoke(clazz, instance, methodSpecification, true, true, args);
        return (T) returnConsumer.getContainer().get();
    }

    public static void main(String[] args) {
        String d = Handler.get(Handler.class, null, "testMethod", 0);
        System.out.println("D = "+d);
    }

    public static void testMethod(Consumer<String> s) {
        s.accept("ABC");
    }

    public static class ReturnConsumer implements Consumer<Object> {

        private final IMutableContainer<Object> container = new MutableContainer<>();

        @Override
        public void accept(Object o) {
            container.set(o);
        }

        public IMutableContainer<Object> getContainer() {
            return container;
        }
    }

}
