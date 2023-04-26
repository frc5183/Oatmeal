package wtf.triplapeeck.oatmeal.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "oatmeal_guilds")
public class Guild {
    @DatabaseField(canBeNull = false)
    public long id;
}
