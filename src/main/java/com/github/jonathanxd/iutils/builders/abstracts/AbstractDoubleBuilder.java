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
package com.github.jonathanxd.iutils.builders.abstracts;

import com.github.jonathanxd.iutils.sequence.DoubleSequence;

import java.util.Arrays;

import com.github.jonathanxd.iutils.interfaces.CapacityImplement;

public abstract class AbstractDoubleBuilder implements DoubleSequence, CapacityImplement {
	/**
	 * Double storage
	 */
	Double[] value;
	
	/**
	 * The length
	 */
	int count;
	
	/**
	 * Default constructor
	 */
	public AbstractDoubleBuilder() {		
	}
	
	/**
	 * Constructor with specific capacity.
	 */
	public AbstractDoubleBuilder(int capacity) {
		this.value = new Double[capacity];
	}
	
	/**
	 * Returns the length
	 */
	@Override
	public int length() {
		return count;
	}
	
	/**
	 * 
	 */
	@Override
	public int capacity() {		
		return value.length;
	}
	
	@Override
    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > 0)
            ensureCapacityInternal(minimumCapacity);
    }

    private void ensureCapacityInternal(int minimumCapacity) {
        if (minimumCapacity - value.length > 0)
            expandCapacity(minimumCapacity);
    }

    void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        if (newCapacity < 0) {
            if (minimumCapacity < 0)
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value, newCapacity);
    }

    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    public void setLength(int newLength) {
        if (newLength < 0)
            throw new IndexOutOfBoundsException("Double index out of range: " + newLength);
        ensureCapacityInternal(newLength);

        if (count < newLength) {
            Arrays.fill(value, count, newLength, '\0');
        }

        count = newLength;
    }
    
    @Override
    public Double doubleAt(int index) {
        if ((index < 0) || (index >= count))
        	throw new IndexOutOfBoundsException("Double index out of range: " + index);
        return value[index];
    }
    
    public void getDoubles(int srcBegin, int srcEnd, Double[] dst, int dstBegin)
    {
        if (srcBegin < 0)
        	throw new IndexOutOfBoundsException("Double index out of range: " + srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
        	throw new IndexOutOfBoundsException("Double index out of range: " + srcEnd);
        if (srcBegin > srcEnd)
        	throw new IndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    
    public void setBoolAt(int index, Double b) {
        if ((index < 0) || (index >= count))
        	throw new IndexOutOfBoundsException("Double index out of range: " + index);
        value[index] = b;
    }
    
    public AbstractDoubleBuilder append(Object obj) {
        return append(Double.valueOf(String.valueOf(obj)));
    }

    public AbstractDoubleBuilder append(Double[] doub) {
    	int len = doub.length;
    	ensureCapacityInternal(count + len);
    	System.arraycopy(doub, 0, value, count, len - 0);
    	count += len;
    	return this;
    }
    
    public AbstractDoubleBuilder append(Double doub) {
        ensureCapacityInternal(count + 1);
        value[count] = doub;
        count += 1;
        return this;
    }
    
    AbstractDoubleBuilder append(AbstractDoubleBuilder asb) {
        if (asb == null)
            return this;
        int len = asb.length();
        ensureCapacityInternal(count + len);
        asb.getDoubles(0, len, value, count);
        count += len;
        return this;
    }
    
    public Double[] subdouble(int start){
    	return subdouble(start, count);
    }

	public Double[] subdouble(int start, int end) {
		Double[] b = new Double[end];
		getDoubles(start, end, b, 0);
		return b;
	}
	
	@Override
	public DoubleSequence subSequence(int start, int end) {
		return new AbstractDoubleBuilder(end){}.append(subdouble(start, end));
	}
}
