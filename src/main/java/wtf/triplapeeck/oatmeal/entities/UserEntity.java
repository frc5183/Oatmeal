package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "oatmeal_users")
public class UserEntity {
    @Column(nullable = false)
    private @NotNull final Long id;

    @Column
    private @Nullable ConcurrentHashMap<Long, Remind.Reminder> reminders;

    @Column(nullable = false)
    private @NotNull Boolean isAdmin;

    @Column(nullable = false)
    private @NotNull Boolean isOwner;

    public UserEntity(@NotNull Long userId) {
        this.id = userId;
        this.isAdmin = false;
        this.isOwner = false;
    }

    @NotNull
    public synchronized Long getId() {
        return id;
    }

    @Nullable
    public synchronized ConcurrentHashMap<Long, Remind.Reminder> getReminders() {
        return reminders;
    }

    public synchronized void setReminders(@Nullable ConcurrentHashMap<Long, Remind.Reminder> reminders) {
        this.reminders = reminders;
    }

    @NotNull
    public synchronized Boolean getAdmin() {
        return isAdmin;
    }

    public synchronized void setAdmin(@NotNull Boolean admin) {
        isAdmin = admin;
    }

    @NotNull
    public synchronized Boolean getOwner() {
        return isOwner;
    }

    public synchronized void setOwner(@NotNull Boolean owner) {
        isOwner = owner;
    }
}
