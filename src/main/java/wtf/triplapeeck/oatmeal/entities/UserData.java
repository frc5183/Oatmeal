package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;

import java.util.concurrent.ConcurrentHashMap;

public abstract class UserData extends AccessibleEntity implements DataID  {
    public abstract Boolean isOwner();
    public abstract void setOwner(Boolean owner);
    public abstract Boolean isAdmin();
    public abstract void setAdmin(Boolean admin);
    public abstract Boolean isCurrencyPreference();
    public abstract void setCurrencyPreference(Boolean currencyPreference);
    public abstract void load();
}
