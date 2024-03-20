package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaUser;

public abstract class ReminderData {
    public abstract String getText();
    public abstract Long getUnix();
    public abstract UserData getUser();
    public abstract Long getId();
    public ReminderData(String text, Long unix, UserData user) {

    }
    public ReminderData() {}

}
