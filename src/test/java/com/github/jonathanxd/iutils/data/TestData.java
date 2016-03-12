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
package com.github.jonathanxd.iutils.data;

import com.github.jonathanxd.iutils.object.Reference;

import java.util.Optional;

/**
 * Created by jonathan on 11/03/16.
 */
public class TestData {

    public static void main(String[] args) {

        ReferenceData data = new ReferenceData();

        data.registerData(Reference.a(Optional.class).of(String.class).hold(Optional.of("Hi")).build());

        data.invoke(new TestData(), m -> m.getName().equals("hello"));
    }

    public void hello(Optional name) {
        System.out.println(name);
    }
}
