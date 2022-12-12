package wtf.triplapeeck.sinon.backend.storable;

import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.errors.ClosedStorableError;

public class GuildStorable extends Storable {

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
            StorableManager.removeGuild(getID().longValue());
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
