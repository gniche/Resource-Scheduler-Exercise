package ginogiuliani.resource_scheduler;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for simple App.
 */
public class MessageSchedulerTest {

	private static final int NUMBER_OF_RESOURCES = 10;
	private MessageScheduler scheduler;
	private Message[] messages;

	private final int NUMBER_OF_MESSAGES_SENT = 1000;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */

	@Mock
	Message message;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		final GatewayImp gateway = new GatewayImp(2);
		Thread schedulerThread = new Thread("Scheduler") {
			public void run() {
				scheduler = new MessageScheduler(NUMBER_OF_RESOURCES, gateway);
			}
		};
		schedulerThread.start();
		messages = new Message[NUMBER_OF_MESSAGES_SENT];
		for (int i = 0; i < NUMBER_OF_MESSAGES_SENT; i++) {
			Random rand = new Random();
			int randomGroupID =  rand.nextInt(4);
			messages[i] = new MessageImp("Message: " + i + " Group: " + randomGroupID, randomGroupID);
		}
	}

	/**
	 * @return the suite of tests being tested
	 */
	@Test
	public void test_if_queued_when_mesage_recieved() {
		System.out.println("test_if_queued_when_mesage_recieved()");
		Mockito.when(message.getGroupID()).thenReturn(2);

		int i = 1;
		for (Message message : messages) {
			scheduler.recieve(message);
		}

		assertTrue(NUMBER_OF_MESSAGES_SENT == scheduler.recieved());
	}

	/**
	 * Rigourous Test :-)
	 */
}
