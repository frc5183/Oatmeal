package wtf.triplapeeck.oatmeal.entities;

import java.time.Instant;

public abstract class AccessibleEntity {
    private transient int accessCount;
    private transient long epoch;

    public AccessibleEntity() {
        this.accessCount = 0;
        this.epoch = 0L;
    }

    public synchronized void request() {
        accessCount++;
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
}
