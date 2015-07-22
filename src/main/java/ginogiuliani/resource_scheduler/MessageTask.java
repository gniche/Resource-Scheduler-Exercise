package ginogiuliani.resource_scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;

public class MessageTask<T> implements Callable<T> {

	private final Gateway gateway;
	private final BlockingQueue<Message> messageQueue;

	public MessageTask(PriorityBlockingQueue<Message> messageQueue, Gateway gateway) {
		this.messageQueue = messageQueue;
		this.gateway = gateway;
	}

	public T call() throws Exception {
		try {
			gateway.send(messageQueue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}