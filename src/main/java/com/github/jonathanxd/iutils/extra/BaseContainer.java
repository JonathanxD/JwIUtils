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

import com.github.jonathanxd.iutils.extra.primitivecontainers.IntContainer;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Containers is a value holder, Containers make easy to modify values that needs be final, example: <br>
 * int x = 0;<br>
 * Consumer<Integer> consumer = (i) -> {<br>
 *     x += 1;<br>
 * };<br>
 * The value X needs to be "Local variable x defined in an enclosing scope must be final or effectively final"<br>
 * A way to solve this is using Container, see example using {@link Container}<br>
 * final Container<Integer> x = Container.of(0);<br>
 * Consumer<Integer> consumer = (i) -> {<br>
 *     x.set(x.get()+1);<br>
 * };<br>
 * also for int you can use {@link IntContainer}<br>
 * final IntContainer x = new IntContainer(0);<br>
 * Consumer<Integer> consumer = (i) -> {<br>
 *     x.add();<br>
 * };<br>
 * @author jonathan
 *
 * @param <T>
 */
public interface BaseContainer<T> extends Comparable<BaseContainer<T>>{

	void setApplier(BiFunction<BaseContainer<T>, T, T> applier);

	void apply(T value);

	T getValue();

	boolean isPresent();

	T getOrElse(T another);

	T getOr(BaseContainer<T> another);

	BaseContainer<T> getOrContainer(BaseContainer<T> another);

	void ifPresent(Consumer<? super T> consumer);

	BaseContainer<T> filter(Predicate<? super T> predicate);

	<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

	boolean equals(Object obj);

	int hashCode();

	String toString();

	default T get() {
		return getValue();
	}
	
	default Optional<T> toOptional() {
		if (!isPresent())
			return Optional.empty();
		return Optional.of(get());
	}
	
	@Override
	default int compareTo(BaseContainer<T> o) {
		if(getValue() instanceof Comparable){
			@SuppressWarnings("unchecked")
			Comparable<T> comparable = (Comparable<T>) getValue();
			return comparable.compareTo(o.get());
		}
		return -1;
	}
	
	
}