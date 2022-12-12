package wtf.triplapeeck.sinon.backend.commands.sinon.owner;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Page;
import wtf.triplapeeck.sinon.backend.Utils;
import wtf.triplapeeck.sinon.backend.commands.Command;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.storable.StorableFactory;
import wtf.triplapeeck.sinon.backend.storable.UserStorable;

import java.util.List;

public class SetOwner extends Command {
    public void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureIsTrip(carriage) &&
            ensureTaggedUserListNotEmpty(carriage) &&
            ensureOnlyOneTaggedUser(carriage) &&
            ensureOnlyOneTaggedIsNotTrip(carriage)) {
            List<User> userList = carriage.message.getMentionedUsers();
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
