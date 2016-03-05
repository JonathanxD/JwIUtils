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
package com.github.jonathanxd.iutils.optional;

import java.util.function.Function;

/**
 * Created by jonathan on 13/02/16.
 */
public class Require {

    public static <T> T require(java.util.Optional<T> optional, String message) {
        if (optional == null || !optional.isPresent())
            throw new IllegalStateException(message);

        return optional.get();
    }

    public static <T> T require(java.util.Optional<T> optional) {
        return require(optional, "Optional cannot be EMPTY");
    }

    public static <T, R> java.util.Optional<R> ifPresent(java.util.Optional<T> optional, Function<T, R> function) {
        if (optional.isPresent())
            return java.util.Optional.of(function.apply(optional.get()));
        return java.util.Optional.empty();
    }

}
