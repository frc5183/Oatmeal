package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "oatmeal_users")
public class UserEntity {
    @Id()
    @Column(nullable = false)
    public @NotNull Long id=0L;

    @Column
    private @Nullable ConcurrentHashMap<Long, Remind.Reminder> reminders;

    @Column(nullable = false)
    private @NotNull Boolean isAdmin;

    @Column(nullable = false)
    private @NotNull Boolean isOwner;

    private transient int accessCount=0;

    public UserEntity() {

    }

    public synchronized void request() {accessCount++;}
    public synchronized void release() {accessCount--;}
    public synchronized int getAccessCount() {return accessCount;}

    public UserEntity(@NotNull Long userId) {
        this.id = userId;
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
