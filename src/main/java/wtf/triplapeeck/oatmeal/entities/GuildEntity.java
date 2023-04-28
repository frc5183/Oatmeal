package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Table(name = "oatmeal_guilds")
public class GuildEntity {
    @Id
    private @NotNull final Long id;

    @Column(nullable = false)
    private @NotNull GuildConfig config;

    @Column(nullable = false)
    private @NotNull ArrayList<BanEntity> bans;

    @Column(nullable = false)
    private @NotNull ArrayList<MuteEntity> mutes;

    public GuildEntity(@NotNull Long guildId) {
        this.id = guildId;
        this.config = new GuildConfig(this);
        this.bans = new ArrayList<>();
        this.mutes = new ArrayList<>();
    }

    @NotNull
    public synchronized Long getId() {
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

    public synchronized void addBan(@NotNull BanEntity ban) {
        this.bans.add(ban);
    }

    public synchronized void removeBan(@NotNull BanEntity ban) {
        this.bans.remove(ban);
    }

    @NotNull
    public synchronized ArrayList<MuteEntity> getMutes() {
        return mutes;
    }

    public synchronized void setMutes(@NotNull ArrayList<MuteEntity> mutes) {
        this.mutes = mutes;
    }

    public synchronized void addMute(@NotNull MuteEntity mute) {
        this.mutes.add(mute);
    }

    public synchronized void removeMute(@NotNull MuteEntity mute) {
        this.mutes.remove(mute);
    }
}
