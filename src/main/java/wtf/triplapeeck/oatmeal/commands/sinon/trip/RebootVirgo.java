package wtf.triplapeeck.oatmeal.commands.sinon.trip;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

import java.io.IOException;

public class RebootVirgo extends Command {
    public void reboot() {
        Runtime r = Runtime.getRuntime();
        try
        {
            r.exec("shutdown -r -t 5");
            Logger.basicLog(Logger.Level.INFO, "Restarting the Computer after 5 seconds.");
        }
        catch(IOException e)
        {
            Logger.basicLog(Logger.Level.ERROR, ("Exception: " +e));
        }
    }

    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureIsTrip(carriage)) {
            carriage.channel.sendMessage("Rebooting Virgo. This SHOULD automatically start my programming.").queue();
            reboot();
        }
    }
    public @NotNull String getDocumentation() { return "Used By Trip-kun to reboot Virgo, the server hosting myself and several other programs. This SHOULD automatically start myself." +
            "\nUsage:s!rebootvirgo";}
    public @NotNull String getName() {
        return "rebootvirgo";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isTrip(carriage);
    }


    public RebootVirgo() {
        Page.TripOnly.addCommand(this);
    }
}
