package wtf.triplapeeck.oatmeal.entities;

public abstract class CustomResponseData extends AccessibleEntity implements DataID {
    public abstract String getTrigger();
    public abstract String getResponse();
    public abstract GuildData getGuild();
    public abstract Long getId();
    public CustomResponseData(String trigger, String response, GuildData guild) {

    }
}
