
public class Client 
{
	int id;
	boolean isAlive;
	boolean isWriter;
	Server leader;
	String content = "";

	public Client(int id, boolean isWriter, Server leader)
	{
        this.id = id;
        this.isWriter = isWriter;
        this.leader = leader;
    }

	void requestLock()
	{	
		if (isWriter) {
            leader.acquireWriteLock(id);
        } else {
            leader.acquireReadLock(id);
        }
	}
	void writeAndReleaseLock() 
	{
		if (isWriter) {
            leader.write(id, "Data from Client-" + id);
            leader.releaseWriteLock(id);
        }	
	}
	void readContent()
	{
		if (!isWriter) {
            leader.read(id);
            leader.releaseReadLock(id);
	}
	void simulateCrash()
	{
		isAlive = false;
        System.out.println("Client-" + id + " crashed!");
	}

	public void run()
	{
        if (isWriter)
		{
            requestLock();
            writeAndReleaseLock();
        }
		else
		{
            requestLock();
            readContent();
        }
    }
}
