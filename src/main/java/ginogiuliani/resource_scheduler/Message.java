package ginogiuliani.resource_scheduler;

public interface Message {

	public void completed();
	
	public int getGroupID();
	
	public void appendMessage(String message);

}
