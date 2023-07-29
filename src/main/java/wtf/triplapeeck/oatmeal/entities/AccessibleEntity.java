package wtf.triplapeeck.oatmeal.entities;

import java.time.Instant;

public abstract class AccessibleEntity {
    private transient int accessCount;
    private transient long epoch;
    private transient boolean saved;

    public AccessibleEntity() {
        this.accessCount = 0;
        this.epoch = 0L;
        this.saved=false;
    }

    public synchronized void request() {
        accessCount++;
        saved=false;
        epoch=0L;
    }
    public synchronized void resetEpoch() {
        epoch = 0L;
    }
    public synchronized void release() {
        accessCount--;
        if (accessCount <= 0) {
            accessCount = 0;
            epoch = Instant.now().getEpochSecond();
        }
    }
    public synchronized long getEpoch() {
        return epoch;
    }
    public synchronized int getAccessCount() {
        return accessCount;
    }
    public synchronized boolean getSaved() {
        return saved;
    }
    public synchronized void saved() {
        saved=true;
    }
}
