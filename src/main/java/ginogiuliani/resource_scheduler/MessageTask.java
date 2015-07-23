package ginogiuliani.resource_scheduler;

import java.util.concurrent.Callable;

public class MessageTask<V> implements Callable<Boolean> {

	private final Gateway gateway;
	private final Message message;

	public MessageTask(Message message, Gateway gateway) {
		this.message = message;
		this.gateway = gateway;
	}

	public Boolean call() throws Exception {
			gateway.send(message);
		return true;
	}
}