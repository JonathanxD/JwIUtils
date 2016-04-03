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
package com.github.jonathanxd.iutils.object;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.reflection.RClass;
import com.github.jonathanxd.iutils.reflection.Reflection;

/**
 * Created by jonathan on 02/04/16.
 */
public class DynamicReference<T> extends Reference<T> {
    DynamicReference(Class<? extends T> aClass, Reference[] related, Object hold) {
        super(aClass, related, hold);
    }

    public void addRelated(Reference<?> reference) {
        try {
            Reflection.changeFinalField(RClass.getRClass(Reference.class, this), "related", Arrays.addToArray(getRelated(), reference));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Reference<T> toReference() {
        return new Reference<>(this.getAClass(), this.getRelated(), this.get());
    }
}
