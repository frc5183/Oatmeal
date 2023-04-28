package wtf.triplapeeck.oatmeal.entities;

public abstract class AccessableEntity {
    private transient int accessCount = 0;

    public synchronized void request() {
        accessCount++;
    }
    public synchronized void release() {
        accessCount--;
    }
    public synchronized int getAccessCount() {
        return accessCount;
    }
}
