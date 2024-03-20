package wtf.triplapeeck.oatmeal.entities.mariadb;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.entities.CustomResponseData;
import wtf.triplapeeck.oatmeal.entities.GuildData;
@DatabaseTable(tableName = "oatmeal_custom_responses")
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
        this.trigger=trigger;
        this.response=response;
        this.guild=guild;
    }
    public MariaCustomResponse() {}
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

    @Override
    public void setTrigger(@NotNull String trigger) {
        this.trigger = trigger;
    }

    @Override
    public void setResponse(@NotNull String response) {
        this.response = response;
    }

    public String getID() {
        return id.toString();
    }
    public void setId(@NotNull Long id) {
        this.id=id;
    }
}
