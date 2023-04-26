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
    private @NotNull long id;

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
}
