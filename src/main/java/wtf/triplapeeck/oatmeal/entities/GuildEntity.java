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
    private @NotNull String guildId;

    private transient @Nullable ConcurrentHashMap<String, String> customCommands;

    private transient @Nullable ConcurrentHashMap<String, String> starboardLink;

    @Column
    private String jsonCustomCommands;

    @Column
    private String jsonStarboardLink;

    public String getJsonCustomCommands() {
        return jsonCustomCommands;
    }

    public String getJsonStarboardLink() {
        return jsonStarboardLink;
    }

    public void setJsonCustomCommands(String jsonCustomCommands) {
        this.jsonCustomCommands = jsonCustomCommands;
    }

    public void setJsonStarboardLink(String jsonStarboardLink) {
        this.jsonStarboardLink = jsonStarboardLink;
    }

    @Column
    private @NotNull Boolean starboardEnabled;

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
        this.guildId = guildId;
        this.starboardChannelID = null;
        this.starboardLimit = null;
        this.currencyEnabled = true;
        this.testingEnabled = false;
        this.starboardEnabled = false;
    }

    @Deprecated
    public GuildEntity() {}

    @NotNull
    public synchronized String getGuildId() {
        return guildId;
    }

    @Nullable
    public synchronized ConcurrentHashMap<String, String> getCustomCommands() {
        if (customCommands==null) {
            customCommands=new ConcurrentHashMap<>();
        }
        return customCommands;
    }

    public synchronized void setCustomCommands(ConcurrentHashMap<String, String> map) {
        customCommands=map;
    }

    public synchronized void setStarboardLink(ConcurrentHashMap<String, String> map) {
        starboardLink=map;
    }

    @Nullable
    public synchronized ConcurrentHashMap<String, String> getStarboardLink() {
        if (starboardLink==null) {
            starboardLink=new ConcurrentHashMap<>();
        }
        return starboardLink;
    }

    @Nullable
    public synchronized String getStarboardChannelID() {
        return starboardChannelID;
    }

    public synchronized void setStarboardChannelID(@Nullable String starboardChannelID) {
        this.starboardChannelID = starboardChannelID;
    }

    @Nullable
    public synchronized int getStarboardLimit() {
        return starboardLimit;
    }

    public synchronized void setStarboardLimit(@Nullable int starboardLimit) {
        this.starboardLimit = starboardLimit;
    }

    @NotNull
    public synchronized boolean isCurrencyEnabled() {
        return currencyEnabled;
    }

    public synchronized void setCurrencyEnabled(@NotNull boolean currencyEnabled) {
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
