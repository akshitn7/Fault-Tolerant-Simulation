
public class Client 
{
	int id;
	boolean isAlive = true;
	Server leader;
	String content = "";

	public Client(int id, Server leader)
	{
        this.id = id;
        this.leader = leader;
    }
	
	//Read Request from Client
	String readRequest()
	{
		if(!leader.isLocked) {
			content = leader.handleRead();
			return "Read Successful";
		}
		else {
			return "Can't Handle Read Request. File is locked by some writer Client.";
		}
	}
	//Lock Request from Client
	boolean requestLock()
	{	
		boolean granted = leader.handleLock(id);
		return granted;
	
	}
	//Write new Content and Release Lock
	void writeAndReleaseLock(String newContent) 
	{
		leader.handleWriteandRelease(newContent);
	}
	//Simulate crash : Toggle the alive state.
	void simulateCrash()
	{
		isAlive = !isAlive;
	}
}
