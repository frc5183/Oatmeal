package wtf.triplapeeck.oatmeal.storable;

import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorable extends Storable {
    private ConcurrentHashMap<Long, Remind.Reminder> reminderList = new ConcurrentHashMap<>();
    private boolean isOwner=false;
    private boolean isAdmin=false;
    private boolean currencyPreference=true;
    public synchronized boolean getCurrencyPreference() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return currencyPreference;
    }
    public synchronized ConcurrentHashMap<Long, Remind.Reminder> getReminderList() {
        return reminderList;
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
        Logger.customLog("Storable","Access Requested. New Count: "+ ++accessCount);
        if (closed) {throw new ClosedStorableError();}
    }

    public synchronized void relinquishAccess() {
        Logger.customLog("Storable","Access Relinquished. New Count: "+ --accessCount);
        if (closed) {throw new ClosedStorableError();}
        Save();
    }
}
