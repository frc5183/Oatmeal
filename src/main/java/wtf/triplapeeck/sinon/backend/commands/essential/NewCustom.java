package wtf.triplapeeck.sinon.backend.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;

public class NewCustom extends Command {
    @Override
    public void handler(@NotNull GuildMessageReceivedEvent event, @NotNull DataCarriage carriage, ThreadManager listener) {
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
