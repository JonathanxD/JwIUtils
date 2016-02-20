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
package com.github.jonathanxd.iutils.sync;

import com.github.jonathanxd.iutils.arguments.Argument;
import com.github.jonathanxd.iutils.workers.PersistWorker;

import java.util.Iterator;

import com.github.jonathanxd.iutils.arguments.Arguments;
import com.github.jonathanxd.iutils.workers.WorkRunnable;

public class QueueSyncWorker implements PersistWorker {

	QueueLock<Arguments> queue = new QueueLock<>();
	
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
		queue.offer(new Arguments()
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
