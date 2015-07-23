package ginogiuliani.resource_scheduler;

public class MessageImp implements Message, Comparable<MessageImp> {

	public int GROUP_ID;
	private String text;

	public MessageImp(String text, int groupID) throws IllegalArgumentException {
		this.text = text;
		if (groupID < 0)
			throw new IllegalArgumentException();
		GROUP_ID = groupID;
	}

	public void completed() {
		System.out.println(text + " Completed");
	}

	public int compareTo(MessageImp msg) {
		if (msg.GROUP_ID < GROUP_ID)
			return -1;
		if (msg.GROUP_ID > GROUP_ID)
			return 1;
		return 0;
	}

	public int getGroupID() {
		return GROUP_ID;
	}

}
