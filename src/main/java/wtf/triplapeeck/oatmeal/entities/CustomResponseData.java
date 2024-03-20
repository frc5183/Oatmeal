package wtf.triplapeeck.oatmeal.entities;

import org.jetbrains.annotations.NotNull;

public abstract class CustomResponseData extends AccessibleEntity implements DataID {
    public abstract String getTrigger();
    public abstract String getResponse();
    public abstract GuildData getGuild();
    public abstract void setTrigger(@NotNull String trigger);
    public abstract void setResponse(@NotNull String response);
}
