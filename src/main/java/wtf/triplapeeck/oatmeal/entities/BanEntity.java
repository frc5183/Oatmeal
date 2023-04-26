package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BanEntity {
    private @NotNull UserEntity user;
    private @NotNull String reason;
    private @NotNull long startTimestamp;
    private @Nullable long endTimestamp;
    private @NotNull boolean active;
    private @NotNull boolean isPermenant;

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
}
