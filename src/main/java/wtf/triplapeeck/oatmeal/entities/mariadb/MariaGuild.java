package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.CustomResponseData;
import wtf.triplapeeck.oatmeal.entities.GuildData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@DatabaseTable(tableName = "oatmeal_guilds")
public class MariaGuild extends GuildData  {
    @ForeignCollectionField(eager=true)
    public Collection<MariaCustomResponse> customResponses;
    public void load() {
        try {
            HashMap<String, String> step = Main.dataManager.gson.fromJson(this.getJsonCustomCommands(), new TypeToken<HashMap<String, String>>(){}.getType());
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
            HashMap<String, String> step = Main.dataManager.gson.fromJson(this.getJsonStarboardLink(), new TypeToken<HashMap<String, String>>(){}.getType());
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
        this.customResponses = new ArrayList<>();
    }

    /**
     * This constructor is only used by ORMLite.
     */
    public MariaGuild() {}

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

    @Override
    public Collection<CustomResponseData> getCustomResponses() {
        ArrayList<CustomResponseData> list = new ArrayList<>(customResponses);
        return list;
    }

    @Override
    public void setCustomResponses(Collection<CustomResponseData> customResponses) {
        ArrayList<MariaCustomResponse> list = new ArrayList<>();
        for (CustomResponseData data: customResponses) {
            list.add((MariaCustomResponse) data);
        }
        this.customResponses = list;
    }

    @Override
    public void addCustomResponse(CustomResponseData customResponse) {
        this.customResponses.add((MariaCustomResponse) customResponse);
    }

    @Override
    public void removeCustomResponse(CustomResponseData customResponse) {
        this.customResponses.remove((MariaCustomResponse) customResponse);
    }
}
