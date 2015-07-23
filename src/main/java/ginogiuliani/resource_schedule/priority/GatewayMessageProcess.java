package ginogiuliani.resource_schedule.priority;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import ginogiuliani.resource_scheduler.Message;

public class GatewayMessageProcess implements Runnable {
	private static Iterator<Integer> sharedGroupIterator = null;
	private static ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>> groupedMsgQueues;
	
	private static Object iteratorlock = new Object();
	private int thrNum;

	public GatewayMessageProcess(ConcurrentHashMap<Integer, LinkedBlockingQueue<Message>> groupedMsgQueues) {
		GatewayMessageProcess.groupedMsgQueues = groupedMsgQueues;

		synchronized (iteratorlock) {
			if (sharedGroupIterator == null) {
				sharedGroupIterator = groupedMsgQueues.keySet().iterator();
				thrNum = 0;
			}else
				thrNum= 1;
		}
	}

	public void run() {
		while (true) {
			int groupNum = 0;
			boolean groupSet = false;
			synchronized (sharedGroupIterator) {
				if (!sharedGroupIterator.hasNext()) {
					sharedGroupIterator = groupedMsgQueues.keySet().iterator();
				} else if (sharedGroupIterator.hasNext()) {
					groupNum = sharedGroupIterator.next();
					groupSet = true;
				}
			}
			if (groupSet)
				try {
					LinkedBlockingQueue<Message> queue        = groupedMsgQueues.get(groupNum);
					System.out.println("Thread: "+ thrNum);
					synchronized (queue) {
						while (!queue.isEmpty()) {
							
							Message msg = queue.take();
							msg.appendMessage("Thread: "+ thrNum);
							msg.completed();
						}
					}

				} catch (InterruptedException e) {
					e.printStackTrace();

				}
		}
	}
}
