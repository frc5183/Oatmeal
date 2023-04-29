package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "oatmeal_bans")
public class MuteEntity extends AccessibleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private @NotNull String userId;

    @Column(nullable = false)
    private @NotNull String guildId;

    @Column(nullable = false)
    private @NotNull String reason;

    @Column(nullable = false)
    private @NotNull Long startTimestamp;

    @Column
    private @Nullable Long endTimestamp;

    @Column(nullable = false)
    private @NotNull Boolean active;

    @Column(nullable = false)
    private @NotNull Boolean permanent;

    public MuteEntity(@NotNull String userId, @NotNull String guildId, @NotNull String reason, @Nullable Long endTimestamp) {
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

    public MuteEntity(@NotNull String userId, @NotNull String guildId, @NotNull String reason) {
        super();
        this.userId = userId;
        this.guildId = guildId;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = null;
        this.active = true;
        this.permanent = true;
    }

    @Deprecated
    public MuteEntity() {}

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
