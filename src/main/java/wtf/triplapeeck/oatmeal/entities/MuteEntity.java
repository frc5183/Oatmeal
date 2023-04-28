package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MuteEntity {
    private @NotNull final UserEntity user;
    private @NotNull String reason;
    private @NotNull Long startTimestamp;
    private @Nullable Long endTimestamp;
    private @NotNull Boolean active;
    private @NotNull Boolean isPermanent;

    public MuteEntity(@NotNull UserEntity user, @NotNull String reason, @NotNull long endTimestamp) {
        this.user = user;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = endTimestamp;
        this.active = true;
        this.isPermanent = false;
    }

    public MuteEntity(@NotNull UserEntity user, @NotNull String reason) {
        this.user = user;
        this.reason = reason;
        this.startTimestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
        this.endTimestamp = null;
        this.active = true;
        this.isPermanent = true;
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
        return isPermanent;
    }

    public synchronized void setPermanent(@NotNull Boolean permanent) {
        isPermanent = permanent;
    }
}
