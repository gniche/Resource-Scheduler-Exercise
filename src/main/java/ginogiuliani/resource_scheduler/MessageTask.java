package ginogiuliani.resource_scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class MessageTask<V> implements Callable<Boolean> {

	private final Gateway gateway;
	private final BlockingQueue<Message> messageQueue;

	public MessageTask(ArrayBlockingQueue<Message> queue, Gateway gateway) {
		this.messageQueue = queue;
		this.gateway = gateway;
	}

	public Boolean call() throws Exception {
			gateway.send(messageQueue.take());
		return true;
	}
}