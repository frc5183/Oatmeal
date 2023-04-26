package wtf.triplapeeck.oatmeal.storable;

import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;

import java.math.BigInteger;

public class MemberStorable extends Storable {
    private BigInteger rak= BigInteger.ZERO;
    public synchronized BigInteger getRak() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return rak;
    }
    public synchronized void setRak(BigInteger val) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        rak=val;
    }
    private int messageCount = 0;
    public synchronized int getMessageCount() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return messageCount;
    }
    public synchronized void setMessageCount(int val) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        messageCount=val;
    }

    @Override
    public void Save() {
        factory.saveStorable(this);
        if (accessCount==0) {
            StorableManager.removeMember(getID().toString());
        }
    }
    public void requestAccess() throws ClosedStorableError {
        Logger.customLog("Storable","Access Requested");
        if (closed) {throw new ClosedStorableError();}
        accessCount++;
    }

    public synchronized void relinquishAccess() {
        Logger.customLog("Storable","Access Relinquished");
        if (closed) {throw new ClosedStorableError();}
        accessCount--;
        Save();
    }
}
