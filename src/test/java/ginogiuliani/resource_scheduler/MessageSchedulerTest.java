package ginogiuliani.resource_scheduler;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for simple App.
 */
public class MessageSchedulerTest {

	private static final int NUMBER_OF_RESOURCES = 4;
	private MessageScheduler scheduler;
	private Message[] messages;

	private final int NUMBER_OF_MESSAGES_SENT = 10;
	private int groupID = 10;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */

	@Mock
	Gateway gateway;
	@Mock
	Message message;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		scheduler = new MessageScheduler(NUMBER_OF_RESOURCES, gateway);
		messages = new Message[NUMBER_OF_MESSAGES_SENT];
		for (int i = 0; i < NUMBER_OF_MESSAGES_SENT; i++) {
			messages[i] = new MessageImp(null, groupID);
		}
	}

	/**
	 * @return the suite of tests being tested
	 */
	@Test
	public void test_if_queued_when_mesage_recieved() {
		int i = 1;
		for (Message message : messages) {
			scheduler.recieve(message);
			System.out.println("messages " + i++);
		}
		int messagesRecieved = scheduler.recieved();

		assertTrue(NUMBER_OF_MESSAGES_SENT == messagesRecieved);
	}

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testApp() {
		assertTrue(true);
	}
}
