package wtf.triplapeeck.oatmeal.commands.oatmeal.admin;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.util.Utils;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.storable.StorableFactory;
import wtf.triplapeeck.oatmeal.storable.UserStorable;

import java.util.List;

public class SetAdmin extends Command {
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (
                ensureIsOwner(carriage) &&
                ensureTaggedUserListNotEmpty(carriage) &&
                ensureOnlyOneTaggedUser(carriage) &&
                ensureOnlyOneTaggedIsNotTrip(carriage) &&
                ensureOnlyOneTaggedIsNotOwner(carriage)
        ) {


            List<User> userList = carriage.message.getMentions().getUsers();
            User user = userList.get(0);
            StorableFactory dsUsr = new StorableFactory(user.getIdLong());
            UserStorable usUsr = dsUsr.userStorable();
            usUsr.setAdmin(!usUsr.isAdmin());
            dsUsr.saveStorable(usUsr);
            carriage.channel.sendMessage(user.getName() + " now " + Utils.isNot(usUsr.isAdmin()) + " an Admin").queue();


        }
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

    public SetAdmin() {
        Page.SinonAdmin.addCommand(this);
    }
}
