package wtf.triplapeeck.oatmeal.storable;

import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;

import java.util.concurrent.ConcurrentHashMap;


public class GuildStorable extends Storable {
    private ConcurrentHashMap<String, String> customCommandList = new ConcurrentHashMap<String, String>();

    private ConcurrentHashMap<Long, Long> starboardLink = new ConcurrentHashMap<>();


    private long starboardChannelID=0;

    private int starboardLimit=2;
    public synchronized void setStarboardChannelID(Long id) {
        starboardChannelID=id;
    }
    public synchronized int getStarboardLimit() {
        return starboardLimit;
    }
    public synchronized void setStarboardLimit(int val) {
        starboardLimit=val;
    }
    public synchronized Long getStarboardChannelId() {
        return starboardChannelID;
    }
    public synchronized ConcurrentHashMap<String, String> getCustomCommandList() {
        return customCommandList;
    }

    public synchronized ConcurrentHashMap<Long, Long> getStarboardLink() {return starboardLink;}
    private boolean currencyEnabled=true;
    public synchronized boolean getCurrencyEnabled() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return currencyEnabled;
    }
    public synchronized void setCurrencyEnabled(boolean val) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        currencyEnabled=val;
    }
    private boolean testingEnabled=false;

    public synchronized boolean getTestingEnabled() throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        return testingEnabled;
    }
    public synchronized void setTestingEnabled(boolean val) throws ClosedStorableError {
        if (closed) {throw new ClosedStorableError();}
        testingEnabled=val;
    }

    @Override
    public void Save() {
        factory.saveStorable(this);
        if (accessCount==0) {
            StorableManager.removeGuild(getID().toString());
            closed=true;
        }
    }
    public synchronized void requestAccess() throws ClosedStorableError {
        Logger.customLog("GUILDStorable","Access Requested. New Count: "+ ++accessCount);
        if (closed) {throw new ClosedStorableError();}
    }

    public synchronized void relinquishAccess() {
        Logger.customLog("GUILDStorable","Access Relinquished. New Count: "+ --accessCount);
        if (closed) {throw new ClosedStorableError();}
        Save();
    }
}
