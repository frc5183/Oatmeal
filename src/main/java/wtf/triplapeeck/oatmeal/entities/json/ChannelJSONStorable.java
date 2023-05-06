package wtf.triplapeeck.oatmeal.entities.json;

import wtf.triplapeeck.oatmeal.FileRW;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.errors.ValidTableException;
import wtf.triplapeeck.oatmeal.cards.Table;

import java.math.BigInteger;

public class ChannelJSONStorable extends JSONStorable {
    protected transient JSONStorableFactory factory;
    public synchronized void setFactory(JSONStorableFactory factory1) {
        factory=factory1;
        idLong=id.longValue();
    }
    private BigInteger id;
    private transient long idLong;
    protected transient FileRW file = null;
    public synchronized void setFileRW(FileRW file1) {
        file=file1;
    }

    public synchronized void setID(BigInteger id1) {
        id=id1;
    }
    public synchronized String getID()  {
        return id.toString();
    }
    public synchronized long getIDLong() {
        Logger.customLog("CHANNELSTORABLE","IdLONG");
        return idLong;
    }
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

    public synchronized void Store() {

            factory.saveStorable(this);

    }


}
