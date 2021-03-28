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

import com.github.jonathanxd.iutils.matching.When;
import com.github.jonathanxd.iutils.opt.OptObject;

import org.junit.Assert;
import org.junit.Test;

import static com.github.jonathanxd.iutils.matching.When.Else;
import static com.github.jonathanxd.iutils.matching.When.EqualsTo;
import static com.github.jonathanxd.iutils.matching.When.Matches;
import static com.github.jonathanxd.iutils.matching.When.When;

public class MatchingTest {

    @Test
    public void test() {
        OptObject<String> object = When("Ms",
                EqualsTo("Ls", s -> {
                    System.out.println("0");
                    return "p";
                }),
                EqualsTo("Xs", s -> {
                    System.out.println("1");
                    return "nxs";
                }),
                Else(o -> {
                    System.out.println("2");
                    return "N";
                })
        ).evaluate();

        Assert.assertEquals("N", object.orElse(""));

    }

    @Test
    public void matching() {
        int input = 10;

        OptObject<String> opt = When(When.STANDARD,
                input,
                Matches(k -> k % 2 == 0, k -> "Even"),
                Else(k -> "Odd")
        ).evaluate();

        Assert.assertEquals("Even", opt.getValue());
    }

    @Test
    public void standard() {

        String[] str = new String[3];

        OptObject<String> object = When(When.STANDARD, "Ms",
                Matches(o -> {
                    str[0] = "a";
                    return o.length() > 1;
                }, o -> ">1"),
                Matches(o -> {
                    str[1] = "b";
                    return o.equals("Ms");
                }, o -> "=Ms"),
                Matches(o -> {
                    str[2] = "c";
                    return o.contains("s");
                }, o -> "contains(s)"),
                Else(o -> "X")
        ).evaluate();

        Assert.assertEquals(">1", object.orElse(""));
        Assert.assertArrayEquals(new String[]{"a", "b", "c"}, str);

    }

    @Test
    public void standardLast() {

        String[] str = new String[3];

        OptObject<String> object = When(When.LAST, "Ms",
                Matches(o -> {
                    str[0] = "a";
                    return o.length() > 1;
                }, o -> ">1"),
                Matches(o -> {
                    str[1] = "b";
                    return o.equals("Ms");
                }, o -> "=Ms"),
                Matches(o -> {
                    str[2] = "c";
                    return o.contains("s");
                }, o -> "contains(s)"),
                Else(o -> "X")
        ).evaluate();

        Assert.assertEquals("contains(s)", object.orElse(""));
        Assert.assertArrayEquals(new String[]{"a", "b", "c"}, str);

    }

    @Test
    public void orTest() {
        String name = "lower case";

        When<String, String> when = When(name,
                Matches(v -> Character.isUpperCase(v.charAt(0)), f -> "First character is upper case"),
                Matches(v -> Character.isDigit(v.charAt(0)), f -> "First character is a digit"),
                Else(f -> "No matches")
        );

        When<String, String> or = When.Or(when,
                Matches(v -> Character.isLowerCase(v.charAt(0)), f -> "First character is lower case")
        );

        OptObject<String> evaluate = or.evaluate();

        Assert.assertEquals(evaluate.getValue(), "First character is lower case");

    }
}
