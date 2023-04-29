package wtf.triplapeeck.oatmeal.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Table(name = "oatmeal_guilds")
public class GuildEntity extends AccessableEntity {
    @Id
    private @NotNull String id;

    @Column
    private @Nullable ConcurrentHashMap<String, String> customCommands;

    @Column
    private @Nullable Long starboardId;

    @Column
    private @Nullable Integer starboardMin;

    @Column(nullable = false)
    private @NotNull Boolean currencyEnabled;

    @Column(nullable = false)
    private @NotNull Boolean testingEnabled;

    public GuildEntity(@NotNull String guildId) {
        this.id = guildId;
        this.starboardId = null;
        this.starboardMin = null;
        this.currencyEnabled = false;
        this.testingEnabled = false;
    }

    @Deprecated
    public GuildEntity() {}

    @NotNull
    public synchronized String getId() {
        return id;
    }

    @Nullable
    public synchronized ConcurrentHashMap<String, String> getCustomCommands() {
        return customCommands;
    }

    public synchronized void setCustomCommands(@Nullable ConcurrentHashMap<String, String> customCommands) {
        this.customCommands = customCommands;
    }

    public synchronized void addCustomCommand(@NotNull String name, @NotNull String description) {
        if (customCommands == null) {
            customCommands = new ConcurrentHashMap<>();
        }
        customCommands.put(name, description);
    }

    public synchronized void removeCustomCommand(@NotNull String name) {
        if (customCommands!= null) {
            customCommands.remove(name);
        }
    }

    @Nullable
    public synchronized Long getStarboardId() {
        return starboardId;
    }

    public synchronized void setStarboardId(@Nullable Long starboardId) {
        this.starboardId = starboardId;
    }

    @Nullable
    public synchronized Integer getStarboardMin() {
        return starboardMin;
    }

    public synchronized void setStarboardMin(@Nullable Integer starboardMin) {
        this.starboardMin = starboardMin;
    }

    @NotNull
    public synchronized Boolean getCurrencyEnabled() {
        return currencyEnabled;
    }

    public synchronized void setCurrencyEnabled(@NotNull Boolean currencyEnabled) {
        this.currencyEnabled = currencyEnabled;
    }

    @NotNull
    public synchronized Boolean getTestingEnabled() {
        return testingEnabled;
    }

    public synchronized void setTestingEnabled(@NotNull Boolean testingEnabled) {
        this.testingEnabled = testingEnabled;
    }


}
