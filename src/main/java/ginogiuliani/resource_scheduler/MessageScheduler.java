package ginogiuliani.resource_scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class MessageScheduler {

	private final int NUMBER_OF_RESOURCES;
	private final ExecutorService exec;
	private final PriorityBlockingQueue<Message> queue;
	protected boolean running = true;
	private int messagesRecieved = 0;

	public MessageScheduler(int allocatedResources, Gateway gateway) throws IllegalArgumentException {
		if (allocatedResources < 2 || gateway == null) {
			throw new IllegalArgumentException();
		}
		NUMBER_OF_RESOURCES = allocatedResources;
		queue = new PriorityBlockingQueue<Message>(NUMBER_OF_RESOURCES);
		exec = Executors.newFixedThreadPool((int) (NUMBER_OF_RESOURCES / 2));
		while(true)
			exec.submit(new MessageTask<Boolean>(queue, gateway));
	}

	public void recieve(Message message) {
		synchronized (this) {
			messagesRecieved++;
		}
		;
		queue.add(message);
	}

	int recieved() {
		synchronized (this) {
			return new Integer(messagesRecieved);
		}

	}

}
