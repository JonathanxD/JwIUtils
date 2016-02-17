/*
 * 	JwIUtils - Utility Library for Java
 *     Copyright (C) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.iutils.object;

import java.util.StringJoiner;

/**
 * Created by jonathan on 16/02/16.
 */
public class StringHelper {


    String start = "";

    StringJoiner joiner = new StringJoiner(",", "{", "}");
    StringJoiner tmp = new StringJoiner("=");

    public StringHelper() {
    }

    public StringHelper(Object object) {
        start = String.valueOf(object.getClass().getSimpleName());
    }

    public StringHelper set(String field, Object value) {
        tmp.add(field).add("=").add(String.valueOf(value));

        joiner.merge(tmp);

        tmp.setEmptyValue("");
        return this;
    }

    @Override
    public String toString() {
        return "[" + start + "] = " + joiner.toString();
    }

}
