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
package com.github.jonathanxd.iutils.function.stream.walkable;

import com.github.jonathanxd.iutils.collection.Walkable;
import com.github.jonathanxd.iutils.function.stream.BiStream;

/**
 * Created by jonathan on 05/03/16.
 */
public abstract class WalkableBiStream<T, U, W extends Walkable> implements BiStream<T, U> {
    private final W walkable;
    private final Runnable closeRunnable;

    protected WalkableBiStream(W walkable) {
        this.walkable = walkable;
        this.closeRunnable = null;
    }

    protected WalkableBiStream(W walkable, Runnable closeRunnable) {
        this.walkable = walkable;
        this.closeRunnable = closeRunnable;
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    protected W getWalkable() {
        return walkable;
    }

    protected void updateState() {
        if(!getWalkable().hasNext())
            close();
    }

    @Override
    public void close() {

        if(getWalkable().hasNext())
            getWalkable().walkToEnd();

        if(closeRunnable != null)
            closeRunnable.run();
    }
}
