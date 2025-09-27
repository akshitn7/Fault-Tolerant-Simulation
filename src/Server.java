import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
    int id;
    boolean isAlive = true;
    boolean isLeader = false;
    boolean lock = false;   // compatibility with your original code
    int lockedBy = -1;
    String content = "Initial Content";

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    // Simulate server crash
    void simulateCrash() {
        isAlive = false;
        System.out.println("Server-" + id + " crashed!");
    }

    // Reader methods
    public void acquireReadLock(int clientId) {
        if (!isAlive) {
            System.out.println("Server-" + id + " is down. Reader-" + clientId + " cannot acquire lock.");
            return;
        }
        rwLock.readLock().lock();
        lock = true;
        lockedBy = clientId;
        System.out.println("Reader-" + clientId + " acquired read lock on Server-" + id);
    }

    public void releaseReadLock(int clientId) {
        if (!isAlive) return;
        rwLock.readLock().unlock();
        lock = false;
        lockedBy = -1;
        System.out.println("Reader-" + clientId + " released read lock on Server-" + id);
    }

    public String read(int clientId) {
        if (!isAlive) {
            System.out.println("Server-" + id + " is down. Reader-" + clientId + " cannot read.");
            return null;
        }
        System.out.println("Reader-" + clientId + " read from Server-" + id + ": " + content);
        return content;
    }

    // Writer methods
    public void acquireWriteLock(int clientId) {
        if (!isAlive) {
            System.out.println("Server-" + id + " is down. Writer-" + clientId + " cannot acquire lock.");
            return;
        }
        rwLock.writeLock().lock();
        lock = true;
        lockedBy = clientId;
        System.out.println("Writer-" + clientId + " acquired write lock on Server-" + id);
    }

    public void releaseWriteLock(int clientId) {
        if (!isAlive) return;
        rwLock.writeLock().unlock();
        lock = false;
        lockedBy = -1;
        System.out.println("Writer-" + clientId + " released write lock on Server-" + id);
    }

    public void write(int clientId, String data) {
        if (!isAlive) {
            System.out.println("Server-" + id + " is down. Writer-" + clientId + " cannot write.");
            return;
        }
        System.out.println("Writer-" + clientId + " wrote to Server-" + id + ": " + data);
        content = data;
    }
}

