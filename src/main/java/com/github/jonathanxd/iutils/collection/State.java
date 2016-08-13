package com.github.jonathanxd.iutils.collection;

/**
 * Created by jonathan on 10/08/16.
 */
public enum State {
    STARTED(true),
    NOT_STARTED(false);

    private final boolean bool;

    State(boolean bool) {
        this.bool = bool;
    }

    public boolean bool() {
        return this.bool;
    }
}
