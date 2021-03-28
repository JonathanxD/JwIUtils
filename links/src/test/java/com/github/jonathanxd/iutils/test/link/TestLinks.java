/*
 *      JwIUtils-Links - Link module of JwIUtils <https://github.com/JonathanxD/JwIUtils/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2021 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.test.link;

import com.github.jonathanxd.iutils.link.Invokable;
import com.github.jonathanxd.iutils.link.Invokables;
import com.github.jonathanxd.iutils.link.Link;
import com.github.jonathanxd.iutils.link.Links;
import com.github.jonathanxd.iutils.type.TypeInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jonathan on 20/08/16.
 */
public class TestLinks {

    private Person person;
    private Link<String> getPersonName;

    @Before
    public void init() throws NoSuchMethodException {
        person = new Person("Mary");

        Person person = new Person("Bob");
        Invokable<String> invokable = Invokables.fromMethod(Person.class.getDeclaredMethod("getName"));

        this.getPersonName = Links.named(
                Links.ofInvokable(invokable).bind(person),
                "getName",
                TypeInfo.of(String.class)
        );
    }

    @Test
    public void testMethod() {

        String name = getPersonName.invoke();
        Assert.assertEquals("Bob", name);
    }

    @Test
    public void testLambda() {
        Link<String> link = Links.ofInvokable(args -> person.getName());
        this.invoke(link);
    }

    @Test
    public void testReflection() throws NoSuchMethodException {
        Method getName = Person.class.getDeclaredMethod("getName");
        Link<String> link = Links.ofInvokable(Invokables.fromMethod(getName));

        this.invoke(link);
    }

    @Test
    public void testField() throws NoSuchFieldException {
        Field name = Person.class.getDeclaredField("name");
        name.setAccessible(true);
        Link<String> link = Links.ofInvokable(Invokables.fromFieldGetter(name));

        this.invoke(link);
    }

    @Test
    public void testMethodHandle() throws NoSuchMethodException, IllegalAccessException {
        MethodHandle getName = MethodHandles.lookup().findVirtual(Person.class, "getName", MethodType.methodType(String.class));
        Link<String> link = Links.ofInvokable(Invokables.fromMethodHandle(getName));

        this.invoke(link);
    }

    public void invoke(Link<String> link) {
        Assert.assertEquals("Mary", link.invoke(person));
    }


    public static class Person {
        private final String name;

        Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
