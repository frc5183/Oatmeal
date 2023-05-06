package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;
import wtf.triplapeeck.oatmeal.entities.UserData;

import javax.persistence.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "oatmeal_users")
public class MariaUser extends UserData {
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

    public MariaUser(@NotNull String userId) {
        super();
        this.userId = userId;
        this.admin = false;
        this.owner = false;
    }


    public MariaUser() {}

    @NotNull
    public synchronized String getID() {
        return userId;
    }

    @Nullable
    public synchronized ConcurrentHashMap<String, Remind.Reminder> getReminders() {
        return reminders.getReminders();
    }

    @Override
    public void setReminders(ConcurrentHashMap<String, Remind.Reminder> reminders) {

    }


    @NotNull
    public synchronized Boolean isAdmin() {
        return admin;
    }

    @Override
    public void setAdmin(Boolean admin) {

    }

    public synchronized void setAdmin(@NotNull boolean admin) {
        this.admin = admin;
    }

    @NotNull
    public synchronized Boolean isOwner() {
        return owner;
    }

    @Override
    public void setOwner(Boolean owner) {

    }

    public synchronized void setOwner(@NotNull boolean owner) {
        this.owner = owner;
    }

    public Boolean isCurrencyPreference() {
        return currencyPreference;
    }

    @Override
    public void setCurrencyPreference(Boolean currencyPreference) {

    }

    @Override
    public void load() {
        MariaUser.ReminderMap out;
        try {
            out = Main.dataManager.gson.fromJson(this.getJsonReminders(), MariaUser.ReminderMap.class);
        } catch (JsonSyntaxException e) {
            out = new MariaUser.ReminderMap();
        }
        if (out==null) {
            out=new MariaUser.ReminderMap();
        }
        this.setReminderMap(out);
    }

    public void setCurrencyPreference(boolean currencyPreference) {
        this.currencyPreference = currencyPreference;
    }
}
