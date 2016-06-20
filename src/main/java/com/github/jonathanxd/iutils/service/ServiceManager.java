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
package com.github.jonathanxd.iutils.service;

import com.github.jonathanxd.iutils.arrays.JwArray;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
	
	private static Map<Class<? extends Service>, Service> services = new HashMap<>();
	private static final JwArray<Class<? extends Service>> loadWait = new JwArray<>();
	/**
	 * 
	 * @param service Service
	 * @return True if not registered, false if already registered
	 */
	public synchronized static boolean registerService(Service service){
		if(!services.containsKey(service.getClass())){
			if(service.getRelationsClass() != null &&
					service.getRelationsClass().length > 0){
				synchronized (loadWait) {
					loadWait.add(service.getClass());
				}
				int x = 0;
				while(x < service.getRelationsClass().length){
					Class<? extends Service> current = service.getRelationsClass()[x];
					if(current != null){
						System.out.println("--> Checking service: "+current.getName());
						if(getService(current) == null){
							if(!isWaitingForLoad(current)){
								Service s = service.makeRelation(current);
								if(s != null){
									System.out.println("---> Service not found, loading...");
									System.out.println("----> Registering needed service: "+s.getName()+" for service: "+service.getName());							
									registerService(s);
									System.out.println("----< Registered needed service: "+s.getName()+" for service: "+service.getName());																	
								}else{
									throw new RuntimeException(new Exception("Cannot load service from class: "+current.getName()));
								}
							}else{
								System.out.println("---< Load task for: "+current.getName()+" already running.");
							}
						}else{
							System.out.println("--< Service found, continue!");
						}
					}
					++x;
				}
				synchronized (loadWait) {
					loadWait.remove(service.getClass());
				}				
			}
			System.out.println();
			
			services.put(service.getClass(), service);
			return true;
		}
		
		return false;
	}
	
	public static boolean isWaitingForLoad(Class<? extends Service> wait){
		return loadWait.contains(wait);
	}
	
	public static <T extends Service> Service getService(Class<T> serviceClass){
		if(services.containsKey(serviceClass)){
			return services.get(serviceClass);					
		}
		
		return null;
	}
	
	public static <T extends Service> Service waitService(Class<T> serviceClass, int max){
		int x = 0;
		while(getService(serviceClass) == null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
			if(x >= max)
				break;
			++x;			
		}
		
		return getService(serviceClass);
	}
	

	public static <T extends Service> Service waitService(Class<T> serviceClass){
		return waitService(serviceClass, 60);
	}
	
	public static JwArray<Service> listServices(){
		JwArray<Service> xsServices = new JwArray<Service>();

		services.forEach((aClass, service) -> xsServices.add(service));

		return xsServices;
	}
	
}
