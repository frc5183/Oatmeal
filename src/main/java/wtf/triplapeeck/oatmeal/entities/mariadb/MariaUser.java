package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.google.gson.JsonSyntaxException;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;
import wtf.triplapeeck.oatmeal.entities.UserData;
import java.util.concurrent.ConcurrentHashMap;

@DatabaseTable(tableName = "oatmeal_users")
public class MariaUser extends UserData {
    @DatabaseField(id = true)
    public @NotNull String userId;

    @DatabaseField(canBeNull = false)
    private @NotNull boolean admin;

    @DatabaseField(canBeNull = false)
    private @NotNull boolean owner;

    @DatabaseField(canBeNull = false)
    private @NotNull boolean currencyPreference;

    public MariaUser(@NotNull String userId) {
        super();
        this.userId = userId;
        this.admin = false;
        this.owner = false;
    }

    @NotNull
    public synchronized String getID() {
        return userId;
    }



    @NotNull
    public synchronized Boolean isAdmin() {
        return (admin || isOwner());
    }

    @Override
    public void setAdmin(Boolean admin) {

    }

    public synchronized void setAdmin(@NotNull boolean admin) {
        this.admin = (admin || isOwner());
    }

    @NotNull
    public synchronized Boolean isOwner() {
        return (owner || Config.getConfig().owners.contains(Long.parseLong(userId)));
    }

    @Override
    public void setOwner(Boolean owner) {

    }

    public synchronized void setOwner(@NotNull boolean owner) {
        this.owner = (owner || Config.getConfig().owners.contains(userId));
    }

    public Boolean isCurrencyPreference() {
        return currencyPreference;
    }

    @Override
    public void setCurrencyPreference(Boolean currencyPreference) {

    }

    @Override
    public void load() {
    }

    public void setCurrencyPreference(boolean currencyPreference) {
        this.currencyPreference = currencyPreference;
    }
}
