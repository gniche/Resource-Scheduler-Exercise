package ginogiuliani.resource_scheduler.old;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageHandler {
	
	private final int NUMBER_OF_RESOURCES;
	private final ExecutorService exec;
	private final BlockingQueue<Message> queue;
	private final Gateway gateway;
	protected boolean running = true;
	Integer resourcesInUse = 0;

	public MessageHandler(int numberOfResources, Gateway gateway) throws IllegalArgumentException{
		if( numberOfResources < 2 || gateway == null){
			throw new IllegalArgumentException();
		}
		NUMBER_OF_RESOURCES = numberOfResources;
		this.gateway = gateway;
		
		exec = Executors.newFixedThreadPool( NUMBER_OF_RESOURCES );
		queue = new ArrayBlockingQueue<Message>( NUMBER_OF_RESOURCES * 3 );
		process();

		
	}



	public void recieveMessage(Message message){;
		queue.add(message);
	}
	
	
	private void process(){
		
		
		Thread t = new Thread("Handler Thread"){
			public void run(){
				while(running  == true){
					try {
						final Message message = queue.take();
						Callable<Boolean> task = new MessageTask<Boolean>(this, gateway, message, NUMBER_OF_RESOURCES, resourcesInUse);
						if (NUMBER_OF_RESOURCES == resourcesInUse){
							wait();
						}
						exec.submit(task);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
		
	}

	int queued(){
		return queue.size();
	}
	

}


class MessageTask<T> implements Callable<T>{
			private Thread thread;
			private final int NUMBER_OF_RESOURCES;
			private Integer resourcesInUse;
			private Gateway gateway;
			private Message message;

			public MessageTask(Thread thread, Gateway gateway, Message message, 
					int numberOfResources, Integer resourcesInUse){
				this.thread = thread;
				this.gateway = gateway;
				this.message = message;
				this.NUMBER_OF_RESOURCES = numberOfResources;
				synchronized(this){
					this.resourcesInUse = resourcesInUse;
				}
				
			}
		

			public T call() throws Exception {
				synchronized(this){
					resourcesInUse++;

				}
				gateway.send(message);
				synchronized(this){
					resourcesInUse--;
					if (NUMBER_OF_RESOURCES > resourcesInUse) thread.notify();
				}
				return null;
			}
		}