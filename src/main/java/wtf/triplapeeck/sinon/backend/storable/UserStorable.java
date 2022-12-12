package wtf.triplapeeck.sinon.backend.storable;

import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.errors.ClosedStorableError;

import java.math.BigInteger;

public class UserStorable extends Storable {
    private boolean isOwner=false;
    private boolean isAdmin=false;
    private boolean currencyPreference=true;
    public synchronized boolean getCurrencyPreference() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return currencyPreference;
    }
    public synchronized  void setCurrencyPreference(boolean val) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        currencyPreference=val;
    }
    public synchronized boolean isAdmin()throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return (isAdmin || isOwner());
    }
    public synchronized boolean isOwner() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return (isOwner || BigInteger.valueOf(222517551257747456L).equals(getID()));
    }

    public synchronized void setOwner(boolean is) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        isOwner=(is || BigInteger.valueOf(222517551257747456L).equals(getID()));
    }

    public synchronized void setAdmin(boolean is) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        isAdmin=(is || BigInteger.valueOf(222517551257747456L).equals(getID()) || isOwner);
    }

    @Override
    public void Save() {
        factory.saveStorable(this);
        if (accessCount==0) {
            StorableManager.removeUser(getID().longValue());
            closed=true;
        }
    }
    public synchronized void requestAccess() throws ClosedStorableError {
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
