package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.cards.Table;
import wtf.triplapeeck.oatmeal.entities.AccessibleEntity;
import wtf.triplapeeck.oatmeal.entities.ChannelData;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;
import wtf.triplapeeck.oatmeal.errors.ValidTableException;

@DatabaseTable(tableName = "oatmeal_channels")
public class MariaChannel extends ChannelData {
    @DatabaseField(generatedId = true)
    public @NotNull String id;

    @DatabaseField(canBeNull = true)
    public @Nullable String tableJson;

    public transient Table table;


    public MariaChannel(@NotNull String id) {
        super();
        this.id = id;
        this.table = null;
        this.tableCount = 0;
        this.tableRecruiting = false;
        this.tableHeld = false;
        this.tableInsuring = false;
    }

    public MariaChannel() {}


    @Nullable
    public synchronized Table getTable() throws UsedTableException {
        if (!tableHeld) {
            tableCount++;;
            tableHeld=true;
            return table;
        } else {
            throw new UsedTableException();
        }
    }

    public synchronized void setTable(@Nullable Table table) throws ValidTableException {
        if (this.table!=null) {throw new ValidTableException();}
            this.table=table;

    }

    @Override
    public void removeTable() throws UsedTableException {
        if (tableHeld) {throw new UsedTableException();}
        table=null;
    }

    @Override
    public synchronized void releaseTable() {
        tableCount--;
        tableHeld=false;
    }

    @Override
    public synchronized void load() {
        this.table = Main.dataManager.gson.fromJson(tableJson, Table.class);
    }

    @Override
    public String getID() {
        return null;
    }
}
