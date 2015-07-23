package ginogiuliani.resource_scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageScheduler {

	private final ExecutorService exec;
	protected boolean running = true;
	private int messagesRecieved = 0;
	private final Gateway gateway;

	public MessageScheduler(int allocatedResources, Gateway gateway) throws IllegalArgumentException {
		this.gateway = gateway;
		if (allocatedResources < 2 || gateway == null) {
			throw new IllegalArgumentException();
		}

		new ArrayBlockingQueue<Message>(allocatedResources);
		exec = Executors.newFixedThreadPool((int) (allocatedResources / 2));
	}

	public void recieve(Message message) {
		messagesRecieved++;
		exec.submit(new MessageTask<Boolean>(message, gateway));
	}

	int recieved() {
		synchronized (this) {
			return new Integer(messagesRecieved);
		}
	}

}
