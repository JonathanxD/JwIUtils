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
