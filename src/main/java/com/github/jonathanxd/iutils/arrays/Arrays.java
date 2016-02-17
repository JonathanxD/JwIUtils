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
package com.github.jonathanxd.iutils.arrays;

import com.github.jonathanxd.iutils.iterator.BackableIterator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Arrays<E> implements Iterable<E>, Comparable<E[]>, Cloneable{

	E[] values;
	Iter ix = null;

	@SafeVarargs
	public Arrays(E... values) {
		this.values = values;
	}

	public Arrays<E> add(E value) {
		Objects.requireNonNull(value);
		final int len = values.length;
		values = java.util.Arrays.copyOf(values, len + 1);
	    values[len] = value;

		return this;
	}

	public Arrays<E> addAll(E[] value) {
		for(E e : value){
			add(e);
		}
		return this;
	}

	public Arrays<E> remove(E value){
		Objects.requireNonNull(value);
		values = removeElement(values, value);

		return this;
	}

	public Arrays<E> set(E value, int index){
		Objects.requireNonNull(value);
		if(index >= values.length)
			throw new IndexOutOfBoundsException("Set '"+value.getClass().getName()+"'. Index '"+index+"'. Length '"+values.length+"'");
		values[index] = value;
		return this;
	}

	public Collection<E> toCollection() {
		return new Arrays.ArrayList<>(values);
	}

	public java.util.ArrayList<E> toArrayList() {
		return new java.util.ArrayList<E>(toCollection());
	}

	public List<E> toList() {
		return new Arrays.ArrayList<>(values);
	}

	public ArraysAbstractList<E> toArraysList() {
		return new Arrays.ArrayList<>(values);
	}

	public E[] toGenericArray() {
		return values;
	}

	@SuppressWarnings("unchecked")
    public <T> T[] toGenericArrayOf(T[] t) {
		if (t.length < values.length)
			return (T[]) java.util.Arrays.copyOf(values, values.length, t.getClass());
		System.arraycopy(values, 0, t, 0, values.length);
		if (t.length > values.length)
			t[values.length] = null;
		return t;

	}

	public E getFirst(){
		return (values.length > 0 ? values[0] : null);
	}

	public E getLast(){
		return (values.length > 0 ? values[values.length-1] : null);
	}

	public int getFirstEqualsIndex(E elem){
		for(int x = 0; x < values.length; ++x){
			if(values[x].equals(elem))
				return x;
		}
		return -1;
	}

	public Arrays<E> copy(Arrays<E> array){
		for(E e : array){
			add(e);
		}

		return this;
	}

	public int length(){
		return this.values.length;
	}

	public boolean isEmpty(){
		return length() <= 0;
	}

	@Override
	public String toString() {
		return new Arrays.ArrayList<E>(values).toString();
	}

	public static <E> E[] removeFirstElement(E[] array, E element){
		return removeElement(array, element, false);
	}

	public static <E> E[] removeElement(E[] array, E element){
		return removeElement(array, element, true);
	}

	public static <E> boolean equalsArray(E element, E[] array){
		for(E elem : array){
			if(element.equals(elem)){
				return true;
			}
		}
		return false;
	}

	public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

	public static <E> E[] removeElement(E[] array, E element, boolean recursive){
		int len = (recursive ?
				array.length - find(array, element)
				: array.length-1);


		@SuppressWarnings("unchecked")
		E[] arrayCp = (E[]) Array.newInstance(array.getClass().getComponentType(), len);

		int l = 0;
		boolean found = false;

		for(int x = 0; x < array.length; ++x){
			E e = array[x];

			if(!e.equals(element) || (!recursive && found)){
				arrayCp[l] = e;
				++l;
			}else{
				found = true;
			}
		}

		return arrayCp;
	}

	public static <E> int find(E[] array, E element){
		Objects.requireNonNull(array);
		Objects.requireNonNull(element);
		int found = 0;
		for(E e : array){
			if(e.equals(element)){
				++found;
			}
		}
		return found;
	}

	public boolean contains(E o){
		if(length() < 1) return false;
		return compare(o) == 1;
	}

	private static class ArrayList<E> extends ArraysAbstractList<E> implements RandomAccess, java.io.Serializable {
		private static final long serialVersionUID = -2764017481108945198L;
		private final E[] a;

		@SafeVarargs
		ArrayList(E... array) {
			a = Objects.requireNonNull(array);
		}

		@Override
		public int size() {
			return a.length;
		}

		@Override
		public Object[] toArray() {
			return a.clone();
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] a) {
			int size = size();
			if (a.length < size)
				return java.util.Arrays.copyOf(this.a, size, (Class<? extends T[]>) a.getClass());
			System.arraycopy(this.a, 0, a, 0, size);
			if (a.length > size)
				a[size] = null;
			return a;
		}

		@Override
		public E get(int index) {
			return a[index];
		}

		@Override
		public E set(int index, E element) {
			E oldValue = a[index];
			a[index] = element;
			return oldValue;
		}

		@Override
		public int indexOf(Object o) {
			E[] a = this.a;
			if (o == null) {
				for (int i = 0; i < a.length; i++)
					if (a[i] == null)
						return i;
			} else {
				for (int i = 0; i < a.length; i++)
					if (o.equals(a[i]))
						return i;
			}
			return -1;
		}

		@Override
		public boolean contains(Object o) {
			return indexOf(o) != -1;
		}

		@Override
		public Spliterator<E> spliterator() {
			return Spliterators.spliterator(a, Spliterator.ORDERED);
		}

		@Override
		public void forEach(Consumer<? super E> action) {
			Objects.requireNonNull(action);
			for (E e : a) {
				action.accept(e);
			}
		}

		@Override
		public void replaceAll(UnaryOperator<E> operator) {
			Objects.requireNonNull(operator);
			E[] a = this.a;
			for (int i = 0; i < a.length; i++) {
				a[i] = operator.apply(a[i]);
			}
		}

		@Override
		public void sort(Comparator<? super E> c) {
			java.util.Arrays.sort(a, c);
		}

		@Override
		public Arrays<E> toArrays() {
			return new Arrays<>(a);
		}
	}


	@Override
	public BackableIterator<E> iterator() {
		if(ix != null){
			ix.reset();
			return ix;
		}
		return ix = new Iter();
	}

	private class Iter implements BackableIterator<E>{

		int current = -1;

		@Override
		public boolean hasNext() {
			return (current+1 < values.length);
		}

		@Override
		public E next() {
			++current;
			return values[current];
		}

		@Override
		public void forEachRemaining(Consumer<? super E> action) {
			while(hasNext())
				action.accept(next());
		}

		protected final void reset(){
			current = -1;
		}

		@Override
		public boolean hasBack() {
			return current-1 > -1;
		}

		@Override
		public E back() {
			--current;
			return values[current];
		}

		@Override
		public int getIndex() {
			return current;
		}
	}


	public static <E> Arrays<E> ofG(E[] values){
		Objects.requireNonNull(values);
		return new Arrays<>(values);
	}

	@SafeVarargs
	public static <E> Arrays<E> of(E... values){
		return new Arrays<>(values);
	}

	@SafeVarargs
	public static <E> E[] genericOf(E... values){
		return new Arrays<>(values).toGenericArray();
	}


	public static class PrimitiveArray {

		public static Byte[] fromPrimitive(byte[] primitive){
			Arrays<Byte> bytes = new Arrays<>();

			for(byte current : primitive){
				bytes.add(Byte.valueOf(current));
			}

			return bytes.toGenericArray();
		}

		public static Short[] fromPrimitive(short[] primitive){
			Arrays<Short> shorts = new Arrays<>();

			for(short current : primitive){
				shorts.add(Short.valueOf(current));
			}

			return shorts.toGenericArray();
		}

		public static Integer[] fromPrimitive(int[] primitive){
			Arrays<Integer> ints = new Arrays<>();

			for(int current : primitive){
				ints.add(Integer.valueOf(current));
			}

			return ints.toGenericArray();
		}

		public static Long[] fromPrimitive(long[] primitive){
			Arrays<Long> primitives = new Arrays<>();

			for(long current : primitive){
				primitives.add(Long.valueOf(current));
			}

			return primitives.toGenericArray();
		}

		public static Float[] fromPrimitive(float[] primitive){
			Arrays<Float> primitives = new Arrays<>();

			for(float current : primitive){
				primitives.add(Float.valueOf(current));
			}

			return primitives.toGenericArray();
		}

		public static Double[] fromPrimitive(double[] primitive){
			Arrays<Double> primitives = new Arrays<>();

			for(double current : primitive){
				primitives.add(Double.valueOf(current));
			}

			return primitives.toGenericArray();
		}

		public static Boolean[] fromPrimitive(boolean[] primitive){
			Arrays<Boolean> bools = new Arrays<>();

			for(boolean current : primitive){
				bools.add(Boolean.valueOf(current));
			}

			return bools.toGenericArray();
		}


		public static Character[] fromPrimitive(char[] primitive){
			Arrays<Character> chars = new Arrays<Character>();

			for(char current : primitive){
				chars.add(Character.valueOf(current));
			}

			return chars.toGenericArray();
		}




		public static byte[] toPrimitive(Byte[] noPrimitive){
			byte[] a2 = new byte[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}

		public static short[] toPrimitive(Short[] noPrimitive){
			short[] a2 = new short[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}

		public static int[] toPrimitive(Integer[] noPrimitive){
			int[] a2 = new int[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}

		public static long[] toPrimitive(Long[] noPrimitive){
			long[] a2 = new long[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}

		public static float[] toPrimitive(Float[] noPrimitive){
			float[] a2 = new float[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}

		public static double[] toPrimitive(Double[] noPrimitive){
			double[] a2 = new double[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}

		public static boolean[] toPrimitive(Boolean[] noPrimitive){
			boolean[] a2 = new boolean[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}


		public static char[] toPrimitive(Character[] noPrimitive){
			char[] a2 = new char[noPrimitive.length];

			for(int x = 0; x < noPrimitive.length; ++x){
				a2[x] = noPrimitive[x];
			}
			return a2;
		}

	}


	public int compare(E o) {
		int v = values.length-1;

		if(v > -1 && values[v].equals(o)){
			return 1;
		}

		int k = v/2;

		if(k > -1 && k != v && values[k].equals(o)){
			return 1;
		}

		for(int x = 0; x < v; ++x){
			if(values[x].equals(o)){
				return 1;
			}
		}

		return 0;
	}

	/**
	 * Returns -1 if the arrays size not equals
	 * Returns 0  if the arrays not equals
	 * Returns 1  if arrays equals
	 *
	 */
	@Override
	public int compareTo(E[] o) {
		if(o.length != values.length){
			return -1;
		}

		int v = values.length-1;

		if(v > -1 && values[v].equals(o[v])){
			return 1;
		}

		int k = v/2;

		if(k > -1 && k != v && values[k].equals(o[k])){
			return 1;
		}

		for(int x = 0; x < v; ++x){
			if(values[x].equals(o[x])){
				return 1;
			}
		}

		return 0;
	}

	public static <E> E[] addToArray(E[] array, E element){
		return Arrays.of(array).add(element).toGenericArray();
	}

	@Override
	public Object clone() {
		Arrays<E> arrays = new Arrays<>();
		arrays.addAll(this.values);
		return arrays;
	}
}
