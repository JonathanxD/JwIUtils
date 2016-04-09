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
package com.github.jonathanxd.iutils.exceptions;

import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.reflection.Reflection;

public class JwIUtilsRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3820066586895196852L;

	public JwIUtilsRuntimeException(Class<?> involved, String exceptionMessage) {
		this(involved, exceptionMessage, 0);
	}
	
	public JwIUtilsRuntimeException(Class<?> involved, String exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
		Arrays<StackTraceElement> arr = Arrays.ofG(super.getStackTrace());
		arr.add(new StackTraceElement(involved.getName(), "<? unknown method>", involved.getSimpleName(), 0));
		super.setStackTrace(arr.toGenericArray());
		
	}
	
	public JwIUtilsRuntimeException(Class<?> involved, String exceptionMessage, int offset) {
		super(exceptionMessage);
		
		StackTraceElement ste = Reflection.getCallInformations(involved);
		Arrays<StackTraceElement> arr = Arrays.ofG(super.getStackTrace());
		arr.add(new StackTraceElement(ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber()+offset));
		super.setStackTrace(arr.toGenericArray());
	}
	
}
