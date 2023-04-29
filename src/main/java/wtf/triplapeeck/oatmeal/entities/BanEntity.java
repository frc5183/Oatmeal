package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "oatmeal_bans")
public class BanEntity extends AccessibleEntity {
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

    public BanEntity(String userId, String guildId, String reason, Long endTimestamp) {
        super();
        this.userId = userId;
        this.guildId = guildId;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = endTimestamp;
        this.active = true;
        this.permanent = false;
    }

    public BanEntity(String userId, String guildId, String reason) {
        this.userId = userId;
        this.guildId = guildId;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = null;
        this.active = true;
        this.permanent = true;
        super.request();
    }

    @Deprecated
    public BanEntity() {}

    public synchronized long getId() {
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

    public synchronized long getStartTimestamp() {
        return startTimestamp;
    }

    public synchronized void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public synchronized long getEndTimestamp() {
        return endTimestamp;
    }

    public synchronized void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public synchronized boolean isPermanent() {
        return permanent;
    }

    public synchronized void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }
}
