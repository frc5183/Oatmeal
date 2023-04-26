package wtf.triplapeeck.oatmeal.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@DatabaseTable(tableName = "oatmeal_guilds")
public class GuildEntity {
    @DatabaseField(canBeNull = false)
    private @NotNull long id;

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
}
