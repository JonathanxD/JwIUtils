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
package com.github.jonathanxd.iutils.arguments;

import com.github.jonathanxd.iutils.arguments.exception.ParseArgumentException;
import com.github.jonathanxd.iutils.arguments.exception.UnsatisfiedArgumentError;
import com.github.jonathanxd.iutils.arrays.JwArray;
import com.github.jonathanxd.iutils.iterator.BackableIterator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EasyArguments {
	
	private final HashMap<String, JwArray<String>> arguments = new HashMap<>();
	
	private synchronized EasyArguments registerArgument(String id, JwArray<String> argument){
		arguments.put(id, argument);
		return this;
	}
	
	@SuppressWarnings("unused")
	private synchronized EasyArguments removeArgument(String id){
		arguments.remove(id);
		return this;
	}

	public synchronized boolean argumentExists(String id){
		return arguments.containsKey(id);
	}

	
	public synchronized JwArray<String> getLastestArgument(String id){
		return arguments.get(id);
	}

	public synchronized Map<String, JwArray<String>> getArgMap(){
		return Collections.unmodifiableMap(arguments);
	}
	
	public synchronized int length(){
		return arguments.size();
	}
	
	public static EasyArguments parseArgArray(String[] args, String[] singleArgNames) throws ParseArgumentException {
		
		EasyArguments xArgs = new EasyArguments();
		JwArray<String> jwArray = JwArray.ofG(args);
		JwArray<String> single = JwArray.ofG(singleArgNames);
		
		
		BackableIterator<String> iter = jwArray.iterator();
		String opt;
		while(iter.hasNext()){
			opt = iter.next();
			if(opt.startsWith("-")){
				if(!single.isEmpty() && single.contains(opt)){
					xArgs.registerArgument(opt.substring(1), single);
					
				}else{
					if(iter.hasNext()){
						
						String key = opt;
						
						JwArray<String> argSend = new JwArray<>();
						
						while(iter.hasNext() && !(opt = iter.next()).startsWith("-")){							
							argSend.add(opt);
						}
						
						if(opt.startsWith("-")){
							iter.back();
						}
						
						xArgs.registerArgument(key.substring(1), argSend);
					}else{
						throw new ParseArgumentException("No provided parameters for argument: ");
					}
				}				
			}
		}
		
		return xArgs;
	}
	
	public synchronized void checkArguments(String[] ids) throws UnsatisfiedArgumentError{
		JwArray<String> unsatisfiedArgList = new JwArray<>();
		for(String id : ids){
			if(!arguments.containsKey(id)){
				unsatisfiedArgList.add(id);
			}
		}
		if(!unsatisfiedArgList.isEmpty()){
			throw new UnsatisfiedArgumentError("Unsatisfied Argument(s): "+unsatisfiedArgList);
		}
	}
	
}
