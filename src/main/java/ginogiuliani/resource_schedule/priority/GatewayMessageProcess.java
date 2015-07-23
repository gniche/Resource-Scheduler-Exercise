package ginogiuliani.resource_schedule.priority;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import ginogiuliani.resource_scheduler.Message;

public class GatewayMessageProcess implements Runnable {
	private static Iterator<Integer> sharedGroupIterator = null;
	private static ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>> groupedMsgQueues;
	
	private static Object iteratorlock = new Object();
	private static Object queuelock = new Object();

	public GatewayMessageProcess(ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>> groupedMsgQueues) {
		GatewayMessageProcess.groupedMsgQueues = groupedMsgQueues;

		synchronized (iteratorlock) {
			if (sharedGroupIterator == null) {
				sharedGroupIterator = groupedMsgQueues.keySet().iterator();
			}
		}

	}

	public void run() {
		while (true) {
			int groupNum = 0;
			boolean groupSet = false;
			synchronized (iteratorlock) {
				if (!sharedGroupIterator.hasNext()) {
					sharedGroupIterator = groupedMsgQueues.keySet().iterator();
				} else if (sharedGroupIterator.hasNext()) {
					groupNum = sharedGroupIterator.next();
					groupSet = true;
				}
			}
			if (groupSet)
				try {
					LinkedBlockingQueue<Message> queue = groupedMsgQueues.get(groupNum);
					synchronized (queuelock) {
						while (!queue.isEmpty()) {
							queue.take().completed();
						}
					}

				} catch (InterruptedException e) {
					e.printStackTrace();

				}
		}
	}
}
