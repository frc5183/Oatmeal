package wtf.triplapeeck.oatmeal.commands.oatmeal.admin;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.UserData;
import wtf.triplapeeck.oatmeal.util.Utils;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

import java.util.List;

public class SetAdmin extends Command {
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (
                ensureIsOwner(carriage) &&
                ensureTaggedUserListNotEmpty(carriage) &&
                ensureOnlyOneTaggedUser(carriage) &&
                ensureOnlyOneTaggedIsNotOwner(carriage)
        ) {


            List<User> userList = carriage.message.getMentions().getUsers();
            User user = userList.get(0);
            UserData usUsr = Main.dataManager.getUserData(user.getId());
            if (Config.getConfig().owners.contains(Long.valueOf(usUsr.getID()))) {
                carriage.channel.sendMessage(user.getName() + " is in the Owner Config, cannot change").queue();
                return;
            }
            usUsr.setAdmin(!usUsr.isAdmin());
            carriage.channel.sendMessage(user.getName() + " now " + Utils.isNot(usUsr.isAdmin()) + " an Admin").queue();


        }
    }

    @NotNull
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SINON_ADMIN;
    }

    public @NotNull String getDocumentation() { return "Used By Sinon's Owners to set the admin status of users" +
            "\n Usage: s!setadmin [Tagged Users (Just 1)]";}
    public @NotNull String getName() {
        return "setadmin";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isOwner(carriage);
    }
}
