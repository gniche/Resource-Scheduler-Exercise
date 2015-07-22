package ginogiuliani.resource_scheduler;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class GatewayImp implements Gateway {
	private final ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>> groupedMsgQueues = 
			new ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>>();

	public void send(Message msg) {
		int groupID = msg.getGroupID();
		if (groupedMsgQueues.containsKey(groupID)) {
			LinkedBlockingQueue<Message> queue = groupedMsgQueues.get(groupID);
			queue.add(msg);
		} else {
			LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
			try {
				queue.put(msg);
				if (!process.isAlive()) {
					process.start();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			groupedMsgQueues.put(groupID, queue);
		}
	}

	private final Thread process = new Thread("process") {
		public void run() {
			Iterator<Integer> groupIterator = null;
			while (true) {
				if (groupIterator == null) {
					groupIterator = groupedMsgQueues.keySet().iterator();
				} else if (!groupIterator.hasNext()) {
					groupIterator = groupedMsgQueues.keySet().iterator();
				}
				groupIterator.next();
				try {
					LinkedBlockingQueue<Message> queue = groupedMsgQueues.get(groupIterator);
					while (!queue.isEmpty()) {
						queue.take().completed();
					}

				} catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
		}
	};
}
