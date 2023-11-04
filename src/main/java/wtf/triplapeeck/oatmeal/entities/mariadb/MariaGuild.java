package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.google.gson.JsonSyntaxException;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.GuildData;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@DatabaseTable(tableName = "oatmeal_guilds")
public class MariaGuild extends GuildData  {
    public void load() {
        try {
            HashMap<String, String> step = Main.dataManager.gson.fromJson(this.getJsonCustomCommands(), HashMap.class);
            if (step==null) {
                step=new HashMap<>();
            }
            ConcurrentHashMap<String, String> out = new ConcurrentHashMap<>();
            for (String key: step.keySet()) {
                out.put(key, step.get(key));
            }
            this.setCustomCommands(out);
        } catch (JsonSyntaxException e) {
            this.setCustomCommands(new ConcurrentHashMap<>());
        }
        try {
            HashMap<String, String> step = Main.dataManager.gson.fromJson(this.getJsonStarboardLink(), HashMap.class);
            if (step==null) {
                step=new HashMap<>();
            }
            ConcurrentHashMap<String, String> out = new ConcurrentHashMap<>();
            for (String key: step.keySet()) {
                out.put(key, step.get(key));
            }
            this.setStarboardLink(out);
        } catch (JsonSyntaxException e) {
            this.setStarboardLink(new ConcurrentHashMap<>());
        }
    }


    @DatabaseField(id = true)
    private @NotNull String id;

    private transient @Nullable ConcurrentHashMap<String, String> customCommands;

    private transient @Nullable ConcurrentHashMap<String, String> starboardLink;

    @DatabaseField(canBeNull = true)
    private @Nullable String jsonCustomCommands;

    @DatabaseField(canBeNull = true)
    private @Nullable String jsonStarboardLink;


    @DatabaseField(canBeNull = true)
    private @Nullable String starboardChannelID;

    @DatabaseField(canBeNull = true)
    private @Nullable Integer starboardLimit;

    @DatabaseField(canBeNull = false)
    private @NotNull Boolean currencyEnabled;

    @DatabaseField(canBeNull = false)
    private @NotNull Boolean testingEnabled;

    public MariaGuild(@NotNull String guildId) {
        super();
        this.id = guildId;
        this.starboardChannelID = null;
        this.starboardLimit = null;
        this.currencyEnabled = true;
        this.testingEnabled = false;
        this.starboardLimit=2;
    }

    @NotNull
    public synchronized String getID() {
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

    @NotNull
    public synchronized Integer getStarboardLimit() {
        if (starboardLimit==null) {
            starboardLimit=2;
        }
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
    public synchronized Boolean isTestingEnabled() {
        return testingEnabled;
    }

    public synchronized void setTestingEnabled(@NotNull Boolean testingEnabled) {
        this.testingEnabled = testingEnabled;
    }


}
