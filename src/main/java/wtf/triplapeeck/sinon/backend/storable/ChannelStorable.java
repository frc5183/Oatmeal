package wtf.triplapeeck.sinon.backend.storable;

import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.errors.ClosedStorableError;
import wtf.triplapeeck.sinon.backend.errors.UsedTableException;
import wtf.triplapeeck.sinon.backend.errors.ValidTableException;
import wtf.triplapeeck.sinon.backend.games.cards.Table;

public class ChannelStorable extends Storable {
    private Table table;
    private transient boolean tableHeld=false;

    private transient boolean tableRecruiting=false;

    public synchronized boolean isTableRecruiting() {
        return tableRecruiting;
    }

    public synchronized void setTableRecruiting(boolean val) {
        tableRecruiting=val;
    }

    private transient boolean tableInsuring=false;

    public synchronized boolean isTableInsuring() {
        return tableInsuring;
    }

    public synchronized void setTableInsuring(boolean val) {
        tableInsuring=val;
    }
    public synchronized Table getTable() throws UsedTableException {
        if (!tableHeld) {
            tableHeld=true;
            return table;
        } else {
            throw new UsedTableException();
        }
    }
    public synchronized void setTable(Table tbl) throws ValidTableException {
        if (table!=null) { throw new ValidTableException();}
        table=tbl;
    }
    public synchronized void removeTable() throws UsedTableException {
        if (tableHeld) {throw new UsedTableException();}
        table=null;
    }

    public synchronized void relinquishTable() {
        tableHeld=false;
    }
    @Override
    public void Save() {
        factory.saveStorable(this);
        if (accessCount==0) {
            StorableManager.removeChannel(getID().longValue());
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
