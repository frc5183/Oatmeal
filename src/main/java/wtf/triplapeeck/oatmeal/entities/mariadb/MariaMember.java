package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.MemberData;

import java.math.BigInteger;

@DatabaseTable(tableName = "oatmeal_members")
public class MariaMember extends MemberData {
    @DatabaseField(id = true)
    public @NotNull String id;

    @DatabaseField(canBeNull = false)
    public @NotNull BigInteger rak;

    @DatabaseField(canBeNull = false)
    public @NotNull Integer messageCount;

    public MariaMember(@NotNull String id) {
        super();
        this.id = id;
        this.rak = BigInteger.ZERO;
        this.messageCount = 0;
    }

    /**
     * This constructor is only used by ORMLite.
     */
    public MariaMember() {}

    @NotNull
    public synchronized BigInteger getRak() {
        return rak;
    }
    public synchronized void setRak(@NotNull BigInteger rak) {
        this.rak=rak;
    }
    public synchronized String getID() {
        return id;
    }
    public synchronized void load() {}
    @NotNull
    public synchronized Integer getMessageCount() {
        return messageCount;
    }
    public synchronized void setMessageCount(@NotNull Integer messageCount) {
        this.messageCount=messageCount;
    }
}
