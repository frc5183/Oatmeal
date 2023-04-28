package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BanEntity {
    private @NotNull final UserEntity user;
    private @NotNull String reason;
    private @NotNull long startTimestamp;
    private @Nullable long endTimestamp;
    private @NotNull boolean active;
    private @NotNull boolean isPermanent;

    public BanEntity(UserEntity user, String reason, long endTimestamp) {
        this.user = user;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = endTimestamp;
        this.active = active;
    }

    public BanEntity(UserEntity user, String reason) {
        this.user = user;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = endTimestamp;
        this.active = active;
    }

    @NotNull
    public synchronized UserEntity getUser() {
        return user;
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
        return isPermanent;
    }

    public synchronized void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }
}
