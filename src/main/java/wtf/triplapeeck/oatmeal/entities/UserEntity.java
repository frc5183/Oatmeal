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

    private transient ReminderMap reminders = new ReminderMap();
    @Column
    private String jsonReminders = "{}";

    public void setJsonReminders(String jsonReminders) {
        this.jsonReminders = jsonReminders;
    }

    public String getJsonReminders() {
        return jsonReminders;
    }

    public void setReminderMap(ReminderMap reminders) {
        this.reminders = reminders;
    }

    public ReminderMap getReminderMap() {
        return this.reminders;
    }
    public static class ReminderMap {
        private ConcurrentHashMap<String, Remind.Reminder> reminders = new ConcurrentHashMap<>();

        public ConcurrentHashMap<String, Remind.Reminder> getReminders() {
            return reminders;
        }

        public void setReminders(ConcurrentHashMap<String, Remind.Reminder> reminders) {
            this.reminders = reminders;
        }
    }
    @Column(nullable = false)
    private @NotNull boolean admin;

    @Column(nullable = false)
    private @NotNull boolean owner;

    @Column(nullable=false)
    private @NotNull boolean currencyPreference;

    public UserEntity(@NotNull String userId) {
        super();
        this.userId = userId;
        this.admin = false;
        this.owner = false;
    }


    public UserEntity() {}

    @NotNull
    public synchronized String getID() {
        return userId;
    }

    @Nullable
    public synchronized ConcurrentHashMap<String, Remind.Reminder> getReminders() {
        return reminders.getReminders();
    }


    @NotNull
    public synchronized boolean isAdmin() {
        return admin;
    }

    public synchronized void setAdmin(@NotNull boolean admin) {
        this.admin = admin;
    }

    @NotNull
    public synchronized boolean isOwner() {
        return owner;
    }

    public synchronized void setOwner(@NotNull boolean owner) {
        this.owner = owner;
    }

    public boolean isCurrencyPreference() {
        return currencyPreference;
    }

    public void setCurrencyPreference(boolean currencyPreference) {
        this.currencyPreference = currencyPreference;
    }
}
