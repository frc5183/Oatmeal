package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;

import javax.persistence.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "oatmeal_users")
public class UserEntity extends AccessibleEntity {
    @Id
    public @NotNull String userId;

    @ElementCollection
    private @Nullable ConcurrentHashMap<Long, String> reminders;

    @Column(nullable = false)
    private @NotNull Boolean admin;

    @Column(nullable = false)
    private @NotNull Boolean owner;

    public UserEntity(@NotNull String userId) {
        super();
        this.userId = userId;
        this.admin = false;
        this.owner = false;
    }

    @Deprecated
    public UserEntity() {}

    @NotNull
    public synchronized String getUserId() {
        return userId;
    }

    @Nullable
    public synchronized ConcurrentHashMap<Long, Remind.Reminder> getReminders() {
        ConcurrentHashMap<Long, Remind.Reminder> newReminders = new ConcurrentHashMap<>();
        for (Long reminder : this.reminders.keySet()) {
            String reminderString = reminders.get(reminder);
            Remind.Reminder newReminder = Remind.Reminder.fromString(reminderString);
            newReminders.put(reminder, newReminder);
        }
        return newReminders;
    }

    public synchronized void setReminders(@Nullable ConcurrentHashMap<Long, Remind.Reminder> reminders) {
        ConcurrentHashMap<Long, String> newReminders = new ConcurrentHashMap<>();
        for (Long reminder : reminders.keySet()) {
            Remind.Reminder reminderObject = reminders.get(reminder);
            String reminderString = reminderObject.toString();
            newReminders.put(reminder, reminderString);
        }
        this.reminders = newReminders;
    }

    public synchronized void addReminder(@NotNull Remind.Reminder reminder) {
        if (reminders == null) {
            reminders = new ConcurrentHashMap<>();
        }
        reminders.put(reminder.getUnix(), reminder.toString());
    }

    public synchronized void removeReminder(@NotNull Long reminderTime) {
        if (reminders != null) {
            reminders.remove(reminderTime);
        }
    }

    @NotNull
    public synchronized Boolean isAdmin() {
        return admin;
    }

    public synchronized void setAdmin(@NotNull Boolean admin) {
        this.admin = admin;
    }

    @NotNull
    public synchronized Boolean isOwner() {
        return owner;
    }

    public synchronized void setOwner(@NotNull Boolean owner) {
        this.owner = owner;
    }
}
