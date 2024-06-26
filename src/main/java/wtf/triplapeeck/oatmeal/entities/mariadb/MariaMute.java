package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.entities.AccessibleEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@DatabaseTable(tableName = "oatmeal_mutes")
public class MariaMute extends AccessibleEntity {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false)
    private @NotNull String userId;

    @DatabaseField(canBeNull = false)
    private @NotNull String guildId;

    @DatabaseField(canBeNull = false)
    private @NotNull String reason;

    @DatabaseField(canBeNull = false)
    private @NotNull Long startTimestamp;

    @DatabaseField(canBeNull = true)
    private @Nullable Long endTimestamp;

    @DatabaseField(canBeNull = false)
    private @NotNull Boolean active;

    @DatabaseField(canBeNull = false)
    private @NotNull Boolean permanent;

    public MariaMute(@NotNull String userId, @NotNull String guildId, @NotNull String reason, @Nullable Long endTimestamp) {
        super();
        this.userId = userId;
        this.guildId = guildId;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = endTimestamp;
        this.active = true;
        this.permanent = false;
        super.request();
    }

    public MariaMute(@NotNull String userId, @NotNull String guildId, @NotNull String reason) {
        super();
        this.userId = userId;
        this.guildId = guildId;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = null;
        this.active = true;
        this.permanent = true;
    }

    /**
     * This constructor is only used by ORMLite.
     */
    public MariaMute() {}

    @NotNull
    public synchronized Long getId() {
        return id;
    }

    @NotNull
    public synchronized String getUserId() {
        return userId;
    }

    @NotNull
    public synchronized String getGuildId() {
        return guildId;
    }

    @NotNull
    public synchronized String getReason() {
        return reason;
    }

    public synchronized void setReason(@NotNull String reason) {
        this.reason = reason;
    }

    @NotNull
    public synchronized Long getStartTimestamp() {
        return startTimestamp;
    }

    public synchronized void setStartTimestamp(@NotNull Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @Nullable
    public synchronized Long getEndTimestamp() {
        return endTimestamp;
    }

    public synchronized void setEndTimestamp(@Nullable Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @NotNull
    public synchronized Boolean getActive() {
        return active;
    }

    public synchronized void setActive(@NotNull Boolean active) {
        this.active = active;
    }

    @NotNull
    public synchronized Boolean getPermanent() {
        return permanent;
    }

    public synchronized void setPermanent(@NotNull Boolean permanent) {
        this.permanent = permanent;
    }
}
