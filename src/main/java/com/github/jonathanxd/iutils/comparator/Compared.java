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
package com.github.jonathanxd.iutils.comparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by jonathan on 05/03/16.
 */
public class Compared<T> {

    private final List<T> comparedElements;

    public Compared(Collection<T> elements) {
        comparedElements = new ArrayList<>(elements);
    }

    public Optional<T> min() {

        if(comparedElements.isEmpty())
            return Optional.empty();

        return Optional.of(comparedElements.get(comparedElements.size()-1));
    }

    public Optional<T> max() {
        if(comparedElements.isEmpty())
            return Optional.empty();

        return Optional.of(comparedElements.get(0));
    }
}
