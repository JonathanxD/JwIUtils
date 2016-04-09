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
package com.github.jonathanxd.iutils.sync;

import com.github.jonathanxd.iutils.arguments.Argument;
import com.github.jonathanxd.iutils.arguments.Arguments;
import com.github.jonathanxd.iutils.workers.PersistWorker;
import com.github.jonathanxd.iutils.workers.WorkRunnable;

import java.util.Iterator;

public class SyncWorker implements PersistWorker {

	HashSetLock<Arguments> queue = new HashSetLock<>();
	
	boolean isWorking = false;
	
	public boolean isLocked() {
		return queue.isLocked();
	}
	
	
	@Override
	public boolean isWorking() {
		return isWorking;
	}
	
	@Override
	public synchronized void addToQueue(WorkRunnable work, Boolean persist) {
		queue.add(new Arguments()
				.registerArgument("work", Argument.of(work))
				.registerArgument("persist", Argument.of(persist)));			
	}
	
	@Override
	public synchronized void addToQueue(WorkRunnable work) {
		addToQueue(work, Boolean.FALSE);
	}

	/**
	 * Start work and clean elements
	 */
	@Override
	public synchronized void startWork() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isWorking()){ // Wait current work finish
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				isWorking = true;
				queue.lock();
				
				for(Arguments args : queue){
					WorkRunnable work = args.<WorkRunnable>getLastestArgument("work").getValue();
					work.doWork();
				}

				queue.unlock();
				isWorking = false;
				transfer();
			}
		}).start();
		
		
	}


	protected void transfer() {
		Iterator<Arguments> args = queue.iterator();
		while(args.hasNext()){
			Arguments arg = args.next();
			Boolean persist = arg.<Boolean>getLastestArgument("persist").getValue();
			if(!persist){
				args.remove();
			}
		}
		boolean b = queue.transfer();
		if(b){
			startWork();
		}
		
	}


	

}
