package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.Page;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ban extends Command {

    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (
                ensureBan(carriage) && ensureGuild(carriage)
        ) {
            if (taggedUserListLength(carriage) > 0) {


                if (ensureOnlyOneTaggedUser(carriage)) {
                    List<User> userList = carriage.message.getMentions().getUsers();
                    User user = userList.get(0);
                    carriage.guild.ban(user, 0, TimeUnit.DAYS);
                    carriage.channel.sendMessage(user.getAsMention() + " is now banned.").queue();
                }
            } else {
                if (ensureFirstArgument(carriage)) {
                    try {
                        long userID = Long.parseLong(carriage.args[1]);
                        User user = carriage.api.retrieveUserById(userID).complete();
                        carriage.guild.ban(user, 0, TimeUnit.DAYS).queue();
                        carriage.channel.sendMessage(user.getAsMention() + " is now banned.").queue();
                    } catch (NumberFormatException e) {
                        carriage.channel.sendMessage(carriage.args[1] + " is not a valid user id");
                    }


                }
            }
        }
    }
    public @NotNull String getDocumentation() { return "Used By Members with the ban members permission to ban users." +
            "\n Usage: s!ban [Tagged User (Just 1)]";}
    public @NotNull String getName() {
        return "ban";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return canBan(carriage) && isGuild(carriage);
    }

    public Ban() {
        Page.Essential.addCommand(this);}
}
