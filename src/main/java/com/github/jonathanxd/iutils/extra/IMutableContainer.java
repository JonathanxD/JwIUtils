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
package com.github.jonathanxd.iutils.extra;

import com.github.jonathanxd.iutils.annotations.Named;

import java.util.function.Function;

public interface IMutableContainer<T> extends HistoryContainer<T> {
    void setValue(T value);

    default void set(T value) {
        this.setValue(value);
    }

    default <R> R applyAndSet(Function<@Named("Current value") T, @Named("Apply to new value") R> function, Function<@Named("Apply result") R, @Named("New value") T> newValFunction) {
        R applied = function.apply(get());

        set(newValFunction.apply(applied));

        return applied;
    }

}
