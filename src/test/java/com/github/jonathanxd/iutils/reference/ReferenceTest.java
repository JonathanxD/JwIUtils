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
package com.github.jonathanxd.iutils.reference;

import com.github.jonathanxd.iutils.object.Reference;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by jonathan on 02/04/16.
 */
public class ReferenceTest {

    @Test
    public void testRef() {
        Reference<Map> reference = Reference.a(Map.class).of(String.class).and(Reference.a(List.class).of(Reference.a(Map.class).of(String.class).and(Integer[].class))).build();

        String fullString = reference.toFullString();

        System.out.println(fullString);

        try {
            System.out.println(Reference.fromFullString(fullString));
            System.out.println(Reference.fromFullString("java.util.Map"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Assert.assertEquals(Reference.fromFullString("java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>, java.util.Map<java.lang.String, java.util.List<java.util.Map<java.lang.String, [Ljava.lang.Integer;>>>").toString(), "[Map<String, List<Map<String, Integer[]>>>, Map<String, List<Map<String, Integer[]>>>]");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
