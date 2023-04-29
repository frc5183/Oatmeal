package wtf.triplapeeck.oatmeal.entities;

import java.time.Instant;

public abstract class AccessibleEntity {
    private transient int accessCount = 0;
    private transient long epoch=0;


    public synchronized void request() {

        accessCount++;
    }
    public synchronized void resetEpoch() {
        epoch=0;
    }
    public synchronized void release() {

        accessCount--;
        if (accessCount==0) {
            epoch= Instant.now().getEpochSecond();
        }
    }
    public synchronized long getEpoch() {
        return epoch;
    }
    public synchronized int getAccessCount() {
        return accessCount;
    }
}
