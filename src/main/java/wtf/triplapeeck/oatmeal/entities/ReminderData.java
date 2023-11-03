package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;

public abstract class ReminderData {
    public abstract String getText();
    public abstract Long getUnix();
    public abstract String getUserId();
    public abstract Long getId();
    public ReminderData(String text, Long unix, String userId) {

    }
    public ReminderData() {}

}
