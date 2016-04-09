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
import com.github.jonathanxd.iutils.arrays.Arrays;
import com.github.jonathanxd.iutils.iterator.BackableIterator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Arguments {
	
	private final HashMap<String, Argument<?>> arguments = new HashMap<>();
	
	public synchronized Arguments registerArgument(String id, Argument<?> argument){
		arguments.put(getUnregisteredIdKey(id), argument);
		return this;
	}
	
	public synchronized Arguments removeArgument(String id, int idKey){
		arguments.remove(id+"#"+idKey);
		return this;
	}

	public synchronized Arguments removeAllArgumentsById(String id){
		for(int x = 0; x < 256+1; ++x){
			if(arguments.containsKey(id+"#"+x)){
				arguments.remove(id+"#"+x);
			}
		}
		return this;
	}
	
	public synchronized boolean argumentExistsById(String id){
		return getLastestIdKey(id) != null;
	}

	public synchronized boolean argumentExists(String id, int idKey){
		return arguments.containsKey(id+"#"+idKey);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <T> Argument<T> getLastestArgument(String id){
		String lastest;
		if((lastest = getLastestIdKey(id)) != null){
			return (Argument<T>) arguments.get(lastest);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public synchronized <T> Argument<T> getSpecificArgument(String id, int idKey){
		if(arguments.containsKey(id+"#"+idKey)){
			return (Argument<T>) arguments.get(id+"#"+idKey);
		}
		return null;
	}
	
	private String getUnregisteredIdKey(String id){
		for(int x = 0; x < 256+1; ++x){
			if(!arguments.containsKey(id+"#"+x)){
				return id+"#"+x;
			}
		}
		return id;
	}

	private String getLastestIdKey(String id){
		for(int x = 256; x > -1; --x){
			if(arguments.containsKey(id+"#"+x)){
				return id+"#"+x;
			}
		}
		return null;
	}
	
	public synchronized Map<String, Argument<?>> getArgMap(){
		return Collections.unmodifiableMap(arguments);
	}
	
	public synchronized int length(){
		return arguments.size();
	}
	
	public static Arguments parseArgArray(String[] args, String[] singleArgNames) throws ParseArgumentException {
		
		Arguments xArgs = new Arguments();
		Arrays<String> arrays = Arrays.ofG(args);
		Arrays<String> single = Arrays.ofG(singleArgNames);
		
		
		BackableIterator<String> iter = arrays.iterator();
		String opt;
		while(iter.hasNext()){
			opt = iter.next();
			if(opt.startsWith("-")){
				if(single.contains(opt)){
					xArgs.registerArgument(opt, Argument.getNullArgument());
				}else{
					if(iter.hasNext()){
						
						String key = opt;
						
						Arrays<String> argSend = new Arrays<>();
						
						while(iter.hasNext() && !(opt = iter.next()).startsWith("-")){							
							argSend.add(opt);
						}
						
						if(opt.startsWith("-")){
							iter.back();
						}
						
						Argument<Arrays<String>> argv = new Argument<Arrays<String>>(argSend);  
						xArgs.registerArgument(key, argv);						
					}else{
						throw new ParseArgumentException("No provided parameters for argument: ");
					}
				}				
			}
		}
		
		return xArgs;
	}
	
	public synchronized void checkArguments(String[] ids) throws UnsatisfiedArgumentError {
		Arrays<String> unsatisfiedArgList = new Arrays<>();
		for(String id : ids){
			if(!argumentExistsById(id)){
				unsatisfiedArgList.add(id);
			}
		}
		if(!unsatisfiedArgList.isEmpty()){
			throw new UnsatisfiedArgumentError("Unsatisfied Argument(s): "+unsatisfiedArgList);
		}
	}
	
}
