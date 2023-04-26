package wtf.triplapeeck.oatmeal.storable;

import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;

import java.util.concurrent.ConcurrentHashMap;

public class GenericStorable extends Storable {
    private ConcurrentHashMap<Long, Long> knownUserList = new ConcurrentHashMap<>();

    public synchronized ConcurrentHashMap<Long, Long> getKnownUserList() {
        return knownUserList;
    }

    @Override
    public void Save() {
        factory.saveStorable(this);
        if (accessCount==0) {
            StorableManager.removeGeneric(getID().longValue());
            closed=true;
        }
    }
    public synchronized void requestAccess() throws ClosedStorableError {
        accessCount++;
        Logger.customLog("GenericStorable","Access Requested. New Count: "+ accessCount);
        if (closed) {throw new ClosedStorableError();}
    }

    public synchronized void relinquishAccess() {
        accessCount--;
        Logger.customLog("GenericStorable","Access Relinquished. New Count: "+ accessCount);
        if (closed) {throw new ClosedStorableError();}
        Save();
    }
}
