/*
 *      JwIUtils - Utility Library for Java <https://github.com/JonathanxD/>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.jonathanxd.iutils.containers;

import com.github.jonathanxd.iutils.containers.primitivecontainers.IntContainer;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
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
public interface BaseContainer<T> extends Comparable<BaseContainer<T>>, UnknownContainer<T> {

	default <R> R map(Function<T, R> function) {
		return function.apply(this.get());
	}

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

	@Override
	default BaseContainer<T> box() {
		return this;
	}

	@Override
	default Class<?> type() {
		return Object.class;
	}
}