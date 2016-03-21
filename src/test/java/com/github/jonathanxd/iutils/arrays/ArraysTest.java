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
package com.github.jonathanxd.iutils.arrays;

import org.junit.Test;

/**
 * Created by jonathan on 21/03/16.
 */
public class ArraysTest {

    String[] abd = new String[]{"A", "B", "D"};
    Arrays<String> abdArr = new Arrays<>("A", "B", "D");
    Arrays<String> abdImm = new ImmutableArrays<>("A", "B", "D");

    @Test
    public void SimpleArraysTest() {

        abd = Arrays.addToArray(abd, "X");

        System.out.println(java.util.Arrays.toString(abd));
    }


    @Test
    public void JwArraysTest() {
        abdArr.add("X");

        System.out.println(abdArr);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ImmutableTest() {
        abdImm.add("C");
        System.out.println(abdImm);

    }

}
