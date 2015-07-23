package ginogiuliani.resource_scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MessageScheduler {

	private final ExecutorService exec;
	private final ArrayBlockingQueue<Message> queue;
	protected boolean running = true;
	private int messagesRecieved = 0;

	public MessageScheduler(int allocatedResources, final Gateway gateway) throws IllegalArgumentException {
		if (allocatedResources < 2 || gateway == null) {
			throw new IllegalArgumentException();
		}

		queue = new ArrayBlockingQueue<Message>(allocatedResources);
		exec = Executors.newFixedThreadPool((int) (allocatedResources / 2));
		
		int i = 0;
		
		Thread schedulerThread = new Thread("Scheduler") {
			public void run() {
				while (true) {
					Future<Boolean> skip = exec.submit(new MessageTask<Boolean>(queue, gateway));
				}
			}
		};
		schedulerThread.start();

	}

	public void recieve(Message message) {
		messagesRecieved++;
		try {
			queue.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int recieved() {
		synchronized (this) {
			return new Integer(messagesRecieved);
		}

	}

}
