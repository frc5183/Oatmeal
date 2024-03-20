package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.j256.ormlite.field.DatabaseField;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.CustomResponseData;
import wtf.triplapeeck.oatmeal.entities.GuildData;

public class MariaCustomResponse extends CustomResponseData {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private @NotNull MariaGuild guild;
    @DatabaseField(canBeNull=false, width=5000)
    private @NotNull String trigger;
    @DatabaseField(canBeNull=false, width=5000)
    private @NotNull String response;

    public MariaCustomResponse(String trigger, String response, MariaGuild guild) {
        super(trigger, response, guild);
        this.trigger=trigger;
        this.response=response;
        this.guild=guild;
    }
    @NotNull
    @Override
    public String getTrigger() {
        return trigger;
    }
    @NotNull
    @Override
    public String getResponse() {
        return response;
    }
    @Override
    public GuildData getGuild() {
        return guild;
    }

    public Long getId() {
        return id;
    }

    public String getID() {
        return id.toString();
    }
    public void setId(@NotNull Long id) {
        this.id=id;
    }
}
