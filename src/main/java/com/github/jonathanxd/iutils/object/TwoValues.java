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

import java.util.Objects;
import java.util.Optional;

import com.github.jonathanxd.iutils.extra.BaseContainer;

/**
 * Created by jonathan on 14/02/16.
 */
public class TwoValues<E, E2> {

    private final E value1;
    private final E2 value2;

    public TwoValues(E value1, E2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public TwoValues(BaseContainer<E> container1, BaseContainer<E2> container2) {
        this.value1 = container1.orElseThrow(() -> new NullPointerException("Empty container 1!"));
        this.value2 = container2.orElseThrow(() -> new NullPointerException("Empty container 2!"));
    }

    public TwoValues(Optional<E> container1, Optional<E2> container2) {
        this.value1 = container1.orElseThrow(() -> new NullPointerException("Empty optional 1!"));
        this.value2 = container2.orElseThrow(() -> new NullPointerException("Empty optional 2!"));
    }

    public E getValue1() {
        return value1;
    }

    public E2 getValue2() {
        return value2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }
}
