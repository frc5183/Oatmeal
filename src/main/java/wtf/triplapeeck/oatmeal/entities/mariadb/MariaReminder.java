package wtf.triplapeeck.oatmeal.entities.mariadb;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.ReminderData;

@DatabaseTable(tableName = "oatmeal_reminders")
public class MariaReminder extends ReminderData {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private @NotNull MariaUser user;

    @DatabaseField(canBeNull = false)
    private @NotNull String text;

    @DatabaseField(canBeNull = false)
    private @NotNull Long unix;

    public MariaReminder(@NotNull Long unix, @NotNull String text, @NotNull MariaUser user) {
        super(text, unix, user);
        this.unix = unix;
        this.text = text;
        this.user = user;
    }

    /**
     * This constructor is only used by ORMLite.
     */
    public MariaReminder() {}

    @NotNull
    @Override
    public String getText() {
        return text;
    }

    @NotNull
    @Override
    public Long getUnix() {
        return unix;
    }

    @NotNull
    @Override
    public MariaUser getUser() {
        return user;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
