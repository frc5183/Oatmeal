package wtf.triplapeeck.oatmeal.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;

import java.util.concurrent.ConcurrentHashMap;

@DatabaseTable(tableName = "oatmeal_users")
public class UserEntity {
    @DatabaseField(canBeNull = false)
    private @NotNull final long id;

    @DatabaseField(canBeNull = true)
    private @Nullable ConcurrentHashMap<Long, Remind.Reminder> reminders;

    @DatabaseField(canBeNull = false)
    private @NotNull boolean isAdmin;

    @DatabaseField(canBeNull = false)
    private @NotNull boolean isOwner;

    public UserEntity(long userId) {
        this.id = userId;
        this.isAdmin = false;
        this.isOwner = false;
    }

    public synchronized long getId() {
        return id;
    }

    @Nullable
    public synchronized ConcurrentHashMap<Long, Remind.Reminder> getReminders() {
        return reminders;
    }

    public synchronized void setReminders(@Nullable ConcurrentHashMap<Long, Remind.Reminder> reminders) {
        this.reminders = reminders;
    }

    public synchronized boolean isAdmin() {
        return isAdmin;
    }

    public synchronized void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public synchronized boolean isOwner() {
        return isOwner;
    }

    public synchronized void setOwner(boolean owner) {
        isOwner = owner;
    }
}
