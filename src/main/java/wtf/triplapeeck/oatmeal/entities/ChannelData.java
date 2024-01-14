package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.errors.ValidTableException;


public abstract class ChannelData extends AccessibleEntity implements DataID {


    protected transient boolean tableHeld=false;
    protected transient int tableCount;
    protected transient boolean tableRecruiting=false;
    protected transient boolean tableInsuring=false;
    public synchronized Boolean isTableRecruiting() {
        return tableRecruiting;
    }
    public synchronized void setTableRecruiting(Boolean val) {
        tableRecruiting=val;
    }
    public synchronized Boolean isTableInsuring() {
        return tableInsuring;
    }
    public synchronized void setTableInsuring(Boolean val) {
        tableInsuring=val;
    }
    public synchronized @Nullable Table loadTable() {
        Table table;
        while (true) {
            boolean complete = false;

            try {
                table = this.getTable();
                return table;
            } catch (UsedTableException ignored) {
            }

        }
    }

    public abstract Table getTable() throws UsedTableException;
    public synchronized Boolean isTableHeld() {
        return tableHeld;
    }
    public synchronized Integer tableCount() {
        return tableCount;
    }
    public abstract void setTable(Table tbl) throws ValidTableException;
    public abstract void removeTable() throws UsedTableException;
    public abstract void releaseTable();
    public abstract void load();
}
