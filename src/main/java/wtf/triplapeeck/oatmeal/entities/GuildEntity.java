package wtf.triplapeeck.oatmeal.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@DatabaseTable(tableName = "oatmeal_guilds")
public class GuildEntity {
    @DatabaseField(canBeNull = false)
    private @NotNull final long id;

    @DatabaseField(canBeNull = false)
    private @NotNull GuildConfig config;

    @DatabaseField(canBeNull = false)
    private @NotNull ArrayList<BanEntity> bans;

    @DatabaseField(canBeNull = false)
    private @NotNull ArrayList<MuteEntity> mutes;

    public GuildEntity(long guildId) {
        this.id = guildId;
        this.config = new GuildConfig(this);
        this.bans = new ArrayList<>();
        this.mutes = new ArrayList<>();
    }

    public synchronized long getId() {
        return id;
    }

    @NotNull
    public synchronized GuildConfig getConfig() {
        return config;
    }

    public synchronized void setConfig(@NotNull GuildConfig config) {
        this.config = config;
    }

    @NotNull
    public synchronized ArrayList<BanEntity> getBans() {
        return bans;
    }

    public synchronized void setBans(@NotNull ArrayList<BanEntity> bans) {
        this.bans = bans;
    }

    @NotNull
    public synchronized ArrayList<MuteEntity> getMutes() {
        return mutes;
    }

    public synchronized void setMutes(@NotNull ArrayList<MuteEntity> mutes) {
        this.mutes = mutes;
    }
}
