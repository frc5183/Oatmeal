package wtf.triplapeeck.oatmeal.commands.oatmeal.owner;

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

public class SetOwner extends Command {
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureIsTrip(carriage) &&
            ensureTaggedUserListNotEmpty(carriage) &&
            ensureOnlyOneTaggedUser(carriage) &&
            ensureOnlyOneTaggedIsNotTrip(carriage)) {
            List<User> userList = carriage.message.getMentions().getUsers();
            User user = userList.get(0);
            StorableFactory dsUsr = new StorableFactory(user.getIdLong());
            UserStorable usUsr = dsUsr.userStorable();
            usUsr.setOwner(!usUsr.isOwner());
            dsUsr.saveStorable(usUsr);
            carriage.channel.sendMessage(user.getName() + " now " + Utils.isNot(usUsr.isAdmin()) + " an Owner").queue();
        }
    }

    public @NotNull String getDocumentation() { return "Used by Trip-kun to set the owner status of users." +
            "\nUsage: s!setowner [Tagged Users (Just 1)]";}
    public @NotNull String getName() {
        return "setowner";
    }

    @Override
    public @NotNull boolean hasPermission(DataCarriage carriage, User user) {
        return isTrip(carriage);
    }

    public SetOwner() {
        Page.SinonOwner.addCommand(this);
    }
}
