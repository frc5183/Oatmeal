package wtf.triplapeeck.sinon.backend.storable;

import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Trivia;
import wtf.triplapeeck.sinon.backend.errors.ClosedStorableError;

import java.util.ArrayList;
import java.util.List;

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
