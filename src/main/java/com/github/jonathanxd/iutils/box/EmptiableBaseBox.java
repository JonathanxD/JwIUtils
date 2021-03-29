package com.github.jonathanxd.iutils.box;

/**
 * A type of {@link BaseBox} that could be emptied.
 *
 * @param <T> Type of data inside the box.
 */
public interface EmptiableBaseBox<T> extends BaseBox<T>, UnknownEmptiableBox<T> {
}
