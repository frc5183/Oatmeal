package wtf.triplapeeck.oatmeal.entities.mariadb;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.ReminderData;

import javax.persistence.*;

@Entity
@Table(name = "oatmeal_reminders")
public class MariaReminder extends ReminderData {
    @Column
    private @NotNull String userId;
    @Column
    private String text;
    @Column
    private Long unix;
    @Id
    @GeneratedValue
    private Long id;

    public MariaReminder(@NotNull Long unix, @NotNull String text, @NotNull String userId) {
        super(text, unix, userId);
        this.unix=unix;
        this.text=text;
        this.userId=userId;
    }

    public MariaReminder() {
        super();

    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Long getUnix() {
        return unix;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
