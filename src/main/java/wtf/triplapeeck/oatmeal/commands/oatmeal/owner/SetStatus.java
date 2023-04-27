package wtf.triplapeeck.oatmeal.commands.oatmeal.owner;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

public class SetStatus extends Command {

    @Override
    public void handler(@NotNull MessageReceivedEvent event, @NotNull DataCarriage carriage, ThreadManager listener ) {
        if (ensureIsOwner(carriage) && ensureFirstArgument(carriage)) {
            String content = event.getMessage().getContentRaw().substring(2+carriage.args[0].length()-1);
            carriage.api.getPresence().setActivity(Activity.playing(content));
            carriage.channel.sendMessage("Set My Status to: Playing " + content).queue();
        }
    }


    public @NotNull String getDocumentation() {
        return "Used to update my status." +
                "\nUsage: s!status [custom status here]" +
                "\nThere are limits to how long my status can be, don't be stupid.";
    }
    public @NotNull String getName() {return"status";}

    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return isOwner(carriage);
    }
    public SetStatus() {
        Page.SinonOwner.addCommand(this);
    }
}
