package wtf.triplapeeck.sinon.backend.commands.sinon.admin;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.Utils;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.storable.StorableFactory;
import wtf.triplapeeck.sinon.backend.storable.UserStorable;

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
