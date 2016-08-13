package com.github.jonathanxd.iutils.collection;

/**
 * Created by jonathan on 10/08/16.
 */
public class StringCharGrabber extends AbstractGrabber<Character> {
    private final String string;

    public StringCharGrabber(String string) {
        super(string.length());
        this.string = string;
    }


    @Override
    protected Character get(int index) {
        return string.charAt(index);
    }

    @Override
    AbstractGrabber<Character> makeNew() {
        return new StringCharGrabber(this.string);
    }

    @Override
    <U> AbstractGrabber<U> makeNewFromArray(U[] array) {
        return new ArrayGrabber<>(array);
    }
}
