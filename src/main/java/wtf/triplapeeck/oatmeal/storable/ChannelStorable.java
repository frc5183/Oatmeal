package wtf.triplapeeck.oatmeal.storable;

import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.ClosedStorableError;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.errors.ValidTableException;
import wtf.triplapeeck.oatmeal.cards.Table;

public class ChannelStorable extends Storable {
    private Table table;
    private transient boolean tableHeld=false;
    private transient int tableCount;

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
            tableCount++;
            Logger.customLog("TableUpdate2", String.valueOf( tableCount));
            tableHeld=true;
            return table;
        } else {
            throw new UsedTableException();
        }
    }
    public synchronized boolean isTableHeld() {
        return tableHeld;
    }
    public synchronized int tableCount() {
        return tableCount;
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
        tableCount--;
        Logger.customLog("CHANNELSTORABLE","Table relinquished");
        tableHeld=false;
    }
    @Override
    public synchronized void Save() {

        if (accessCount==0) {
            StorableManager.removeChannel(getIDLong());
            factory.saveStorable(this);
            Logger.customLog("CHANNELStorable","Saving with count: " + accessCount);
            Logger.customLog("CHANNELStorable","removed with count: " + accessCount);
            closed=true;
        } else {
            Logger.customLog("CHANNELStorable","Did NOT save because count is: " + accessCount);
        }

    }
    public synchronized void requestAccess() throws ClosedStorableError {
        accessCount++;
        Logger.customLog("CHANNELStorable","Access Requested. New Count: "+ accessCount);
        if (closed) {throw new ClosedStorableError();}
    }

    public synchronized void relinquishAccess() {
        accessCount--;
        Logger.customLog("CHANNELStorable","Access Relinquished. New Count: "+ accessCount);
        if (closed) {throw new ClosedStorableError();}
        Save();
    }
}
