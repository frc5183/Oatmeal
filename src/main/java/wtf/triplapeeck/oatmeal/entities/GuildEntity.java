package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.ConcurrentHashMap;

@Table(name = "oatmeal_guilds")
public class GuildEntity extends AccessibleEntity {
    @Id
    private @NotNull String id;

    private transient @Nullable ConcurrentHashMap<String, String> customCommands;

    private transient @Nullable ConcurrentHashMap<String, String> starboardLink;

    @Column
    private @Nullable String jsonCustomCommands;

    @Column
    private @Nullable String jsonStarboardLink;


    @Column
    private @Nullable String starboardChannelID;

    @Column
    private @Nullable Integer starboardLimit;

    @Column(nullable = false)
    private @NotNull Boolean currencyEnabled;

    @Column(nullable = false)
    private @NotNull Boolean testingEnabled;

    public GuildEntity(@NotNull String guildId) {
        super();
        this.id = guildId;
        this.starboardChannelID = null;
        this.starboardLimit = null;
        this.currencyEnabled = true;
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
        if (customCommands==null) {
            customCommands=new ConcurrentHashMap<>();
        }
        return customCommands;
    }

    public synchronized void setCustomCommands(@Nullable ConcurrentHashMap<String, String> customCommands) {
        this.customCommands = customCommands;
    }

    @Nullable
    public synchronized ConcurrentHashMap<String, String> getStarboardLink() {
        if (starboardLink==null) {
            starboardLink=new ConcurrentHashMap<>();
        }
        return starboardLink;
    }

    public synchronized void setStarboardLink(@Nullable ConcurrentHashMap<String, String> starboardLink) {
        this.starboardLink = starboardLink;
    }

    @Nullable
    public String getJsonCustomCommands() {
        return jsonCustomCommands;
    }

    public void setJsonCustomCommands(@Nullable String jsonCustomCommands) {
        this.jsonCustomCommands = jsonCustomCommands;
    }

    @Nullable
    public String getJsonStarboardLink() {
        return jsonStarboardLink;
    }

    public void setJsonStarboardLink(@Nullable String jsonStarboardLink) {
        this.jsonStarboardLink = jsonStarboardLink;
    }

    @Nullable
    public synchronized String getStarboardChannelID() {
        return starboardChannelID;
    }

    public synchronized void setStarboardChannelID(@Nullable String starboardChannelID) {
        this.starboardChannelID = starboardChannelID;
    }

    @Nullable
    public synchronized Integer getStarboardLimit() {
        return starboardLimit;
    }

    public synchronized void setStarboardLimit(@Nullable Integer starboardLimit) {
        this.starboardLimit = starboardLimit;
    }



    @NotNull
    public synchronized Boolean isCurrencyEnabled() {
        return currencyEnabled;
    }

    public synchronized void setCurrencyEnabled(@NotNull Boolean currencyEnabled) {
        this.currencyEnabled = currencyEnabled;
    }

    @NotNull
    public synchronized boolean isTestingEnabled() {
        return testingEnabled;
    }

    public synchronized void setTestingEnabled(@NotNull Boolean testingEnabled) {
        this.testingEnabled = testingEnabled;
    }


}
