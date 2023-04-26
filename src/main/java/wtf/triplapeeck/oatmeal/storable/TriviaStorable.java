package wtf.triplapeeck.oatmeal.storable;

import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;

public class TriviaStorable extends Storable {
    @Override
    public void Save() {
        factory.saveStorable(this);
        if (accessCount==0) {
            StorableManager.removeTrivia(getID().longValue());
            closed=true;
        }
    }
    public synchronized void requestAccess() throws ClosedStorableError {
        Logger.customLog("TriviaStorable","Access Requested. New Count: "+ ++accessCount);
        if (closed) {throw new ClosedStorableError();}
    }

    public synchronized void relinquishAccess() {
        Logger.customLog("TriviaStorable","Access Relinquished. New Count: "+ --accessCount);
        if (closed) {throw new ClosedStorableError();}
        Save();
    }
}
