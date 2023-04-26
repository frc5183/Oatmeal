package wtf.triplapeeck.sinon.backend.commands.sinon.owner;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.runnable.Waiting;

public class RebootSinon extends Command {
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureIsOwner(carriage)){
            Logger.basicLog(Logger.Level.INFO, "Sinon-Reboot Initiated.");
            listener.requestToEnd();

            carriage.channel.sendMessage("Rebooting. Please allow a moment for rebooting to finish.").complete();

            while (true) {
                if (listener.getState()== Thread.State.TERMINATED) {
                    break;
                }
            }
            carriage.api.shutdownNow();
        }



    }
    public @NotNull String getDocumentation() { return "Used by Trip-kun to reboot Sinon's code. Often used to push updates." +
            "\nUsage: s!reboot";}
    public @NotNull String getName() {
        return "reboot";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isOwner(carriage);
    }


    public RebootSinon() {
        Page.SinonOwner.addCommand(this);
    }
}
