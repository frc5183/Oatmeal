package wtf.triplapeeck.oatmeal.entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;

@DatabaseTable(tableName = "oatmeal_guild_configs")
public class GuildConfig {
    @DatabaseField(canBeNull = false)
    private @NotNull final GuildEntity guild;

    @DatabaseField(canBeNull = true)
    private @Nullable ConcurrentHashMap<String, String> customCommands;

    @DatabaseField(canBeNull = true)
    private @Nullable long starboardId;

    @DatabaseField(canBeNull = true)
    private @Nullable int starboardMin;

    @DatabaseField(canBeNull = false)
    private @NotNull boolean currencyEnabled;

    @DatabaseField(canBeNull = false)
    private @NotNull boolean testingEnabled;

    public GuildConfig(@NotNull GuildEntity guild) {
        this.guild = guild;
        this.currencyEnabled = false;
        this.testingEnabled = false;
    }

    @NotNull
    public synchronized GuildEntity getGuild() {
        return guild;
    }

    @Nullable
    public synchronized ConcurrentHashMap<String, String> getCustomCommands() {
        return customCommands;
    }

    public synchronized void setCustomCommands(@Nullable ConcurrentHashMap<String, String> customCommands) {
        this.customCommands = customCommands;
    }

    public synchronized long getStarboardId() {
        return starboardId;
    }

    public synchronized void setStarboardId(long starboardId) {
        this.starboardId = starboardId;
    }

    public synchronized int getStarboardMin() {
        return starboardMin;
    }

    public synchronized void setStarboardMin(int starboardMin) {
        this.starboardMin = starboardMin;
    }

    public synchronized boolean isCurrencyEnabled() {
        return currencyEnabled;
    }

    public synchronized void setCurrencyEnabled(boolean currencyEnabled) {
        this.currencyEnabled = currencyEnabled;
    }

    public synchronized boolean isTestingEnabled() {
        return testingEnabled;
    }

    public synchronized void setTestingEnabled(boolean testingEnabled) {
        this.testingEnabled = testingEnabled;
    }
}
