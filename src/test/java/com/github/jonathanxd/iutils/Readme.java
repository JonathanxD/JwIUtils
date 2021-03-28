/*
 *      JwIUtils - Java utilities library <https://github.com/JonathanxD/JwIUtils>
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
package com.github.jonathanxd.iutils;

import com.github.jonathanxd.iutils.function.stream.BiStream;
import com.github.jonathanxd.iutils.function.stream.BiStreams;
import com.github.jonathanxd.iutils.function.stream.MapStream;
import com.github.jonathanxd.iutils.string.ToStringHelper;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Readme {

    private static void print(Priority priority, Person person) {
        System.out.printf("Priority = %s, Person = %s%n", priority.toString(), person.toString());
    }

    @Test
    public void testMapStream() {
        // Priority Person Map
        Map<Priority, Person> personMap = new HashMap<>();

        // Put persons
        personMap.put(Priority.LOW, new Person("Jay", 33));
        personMap.put(Priority.HIGH, new Person("Amy", 15));
        personMap.put(Priority.NORMAL, new Person("Mark", 19));

        // Create stream
        BiStream<Priority, Person> biStream = BiStreams.mapStream(personMap);

        // Print elements

        biStream.forEach(Readme::print); // Terminal Operation

        System.out.println();
        System.out.println("Sort by priority");

        // Sort by priority
        BiStreams.mapStream(personMap)
                // Compare priorities
                .sorted((priority, person, priority2, person2) -> priority.compareTo(priority2))
                // Print
                .forEach(Readme::print);


        System.out.println();
        System.out.println("Sort by age");

        // Sort by age
        BiStreams.mapStream(personMap)
                // Compare ages
                .sorted((priority, person, priority2, person2) -> Integer.compare(person.getAge(), person2.getAge()))
                // Print
                .forEach(Readme::print);

        System.out.println();
        System.out.println("Sort by name");

        // Sort by name
        BiStreams.mapStream(personMap)
                // Compare Person names
                .sorted((priority, person, priority2, person2) -> person.getName().compareTo(person2.getName()))
                // Print
                .forEach(Readme::print);
    }

    private enum Priority {
        LOW,
        NORMAL,
        HIGH

    }

    private static class Person {
        private final String name;
        private final int age;

        private Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return this.age;
        }

        @Override
        public String toString() {
            return ToStringHelper.helper("Person", ", ", "[", "]")
                    .add("name", this.getName())
                    .add("age", this.getAge())
                    .toString();
        }
    }
}
