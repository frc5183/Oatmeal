package wtf.triplapeeck.oatmeal.entities;

public abstract class CustomResponseData extends AccessibleEntity implements DataID {
    public abstract String getTrigger();
    public abstract String getResponse();
    public abstract GuildData getGuild();
}
