package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;

public class RemoveCustom extends Command {
    @Override
    public void handler(@NotNull MessageReceivedEvent event, @NotNull DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage)) {
            String name = carriage.args[1];
            carriage.guildStorable.getCustomCommandList().remove(name);
            carriage.channel.sendMessage("Removed custom command s!" + name).queue();
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to remove a custom guild command."+
                "\nUsage s!removecustom [name]"+
                "\nRemove the custom command s![name]";
    }

    @Override
    public @NotNull String getName() {
        return "removecustom";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }
    public RemoveCustom() {
        Page.Essential.addCommand(this);
    }
}
