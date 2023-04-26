package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.Page;

public class NewCustom extends Command {
    @Override
    public void handler(@NotNull MessageReceivedEvent event, @NotNull DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage) && ensureSecondArgument(carriage)) {
            String name = carriage.args[1];
            String content = carriage.textAfterSubcommand;
            carriage.guildStorable.getCustomCommandList().put(name, content);
            carriage.channel.sendMessage("Added new command s!" + name +" that responds with \"" + content +"\"").queue();
        }
    }
    public @NotNull String getDocumentation() {
        return "Used to create or update a custom guild command." +
                "\nUsage: s!custom [name] [response]"+
                "\nCreate or updates the custom command with usage s![name] that will respond with [response]";
    }
    public @NotNull String getName() {return"custom";}
    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }
    public NewCustom() {
        Page.Essential.addCommand(this);}
}
