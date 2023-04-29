package wtf.triplapeeck.oatmeal.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.GuildEntity;
import wtf.triplapeeck.oatmeal.errors.database.MissingEntryException;
import wtf.triplapeeck.oatmeal.runnable.NamedRunnable;

public class GuildAddEvent implements NamedRunnable {
    String name = "GUILDADDEVENT";
    GuildJoinEvent event;

    public GuildAddEvent(GuildJoinEvent event) {
        this.event = event;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        Guild g = this.event.getGuild();
        try {
            Main.entityManager.getGuildEntity(g.getId());
        } catch (MissingEntryException e) {
            GuildEntity ge = new GuildEntity(g.getId());
            Main.entityManager.updateGuildEntity(ge);
            ge.release();
        }
    }
}
