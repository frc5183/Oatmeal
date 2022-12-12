package wtf.triplapeeck.sinon.backend.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.errors.UsedTableException;
import wtf.triplapeeck.sinon.backend.listeners.ThreadManager;
import wtf.triplapeeck.sinon.backend.storable.StorableFactory;
import wtf.triplapeeck.sinon.backend.storable.UserStorable;

import java.util.List;

public abstract class Command {
    public abstract void handler(GuildMessageReceivedEvent event, DataCarriage carriage, ThreadManager listener);

    public abstract @NotNull java.lang.String getDocumentation();
    public abstract @NotNull String getName();

    public abstract @NotNull boolean hasPermission(DataCarriage carriage, User user);
    public boolean ensureIsAdmin(@NotNull DataCarriage carriage) {
        if (carriage.userStorable.isAdmin()) {
            return true;
        } else {
            carriage.channel.sendMessage("You are not allowed to do this command.").queue();
            return false;
        }

    }
    public boolean isOwner(@NotNull DataCarriage carriage) {
        return carriage.userStorable.isOwner();
    }
    public boolean isAdmin(@NotNull DataCarriage carriage) {
        return carriage.userStorable.isAdmin();
    }
    public boolean ensureIsOwner(@NotNull DataCarriage carriage) {
        if (carriage.userStorable.isOwner()) {
            return true;
        } else {
            carriage.channel.sendMessage("You are not allowed to do this command.").queue();
            return false;
        }
    }

    public int taggedUserListLength(@NotNull DataCarriage carriage) {
        return carriage.message.getMentionedUsers().size();
    }
    public boolean ensureTaggedUserListNotEmpty(@NotNull DataCarriage carriage) {
        if (!carriage.message.getMentionedUsers().isEmpty()) {
            return true;
        } else {
            carriage.channel.sendMessage("You must tag someone").queue();
            return false;
        }
    }

    public boolean ensureOnlyOneTaggedUser(@NotNull DataCarriage carriage) {
        if (taggedUserListLength(carriage)==1) {
            return true;
        } else {
            carriage.channel.sendMessage("You must tag only one person").queue();
            return false;
        }
    }

    public boolean ensureOnlyOneTaggedIsNotTrip(@NotNull DataCarriage carriage) {
        if (carriage.message.getMentionedUsers().get(0).getIdLong()!=222517551257747456L)
            return true;
        else {
            carriage.channel.sendMessage("You cannot target Trip-kun with this command.").queue();
            return false;
        }
    }
    public boolean getOwnerStatusOfOnlyOneTagged(@NotNull DataCarriage carriage) {
        List<User> userList = carriage.message.getMentionedUsers();
        User user = userList.get(0);
        StorableFactory dsUsr = new StorableFactory(user.getIdLong());
        UserStorable usUsr = dsUsr.userStorable();
        return usUsr.isOwner();

    }
    public boolean ensureOnlyOneTaggedIsNotOwner(@NotNull DataCarriage carriage) {
        if (!getOwnerStatusOfOnlyOneTagged(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("You cannot target an Owner with this command.").queue();
            return false;
        }
    }
    public boolean ensureIsTrip(@NotNull DataCarriage carriage) {
        if (carriage.user.getIdLong()==222517551257747456L) {
            return true;
        } else {
            carriage.channel.sendMessage("You are not allowed to do this command.").queue();
            return false;
        }

    }

    public boolean isTrip(@NotNull DataCarriage carriage) {
        return carriage.user.getIdLong()==222517551257747456L;
    }
    public boolean currencyEnabled(@NotNull DataCarriage carriage) {
        return carriage.guildStorable.getCurrencyEnabled();
    }
    public boolean testingEnabled(@NotNull DataCarriage carriage) {
        return carriage.guildStorable.getTestingEnabled();
    }
    public boolean currencyPreference(@NotNull DataCarriage carriage) {
        return carriage.userStorable.getCurrencyPreference();
    }
    public boolean ensureCurrencyEnabled(@NotNull DataCarriage carriage) {
        if (currencyEnabled(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("Currency is disabled in this server.").queue();
            return false;
        }
    }
    public boolean isTableEmpty(@NotNull DataCarriage carriage) throws UsedTableException {
        boolean rtrn = (carriage.channelStorable.getTable()==null);
        carriage.channelStorable.relinquishTable();
        Logger.customLog("Command", "Requested And Relinquished Table.");
        return rtrn;
    }
    public boolean ensureTableIsEmpty(@NotNull DataCarriage carriage) {
        try {
            if (isTableEmpty(carriage)) {
                return true;
            } else {
                carriage.channel.sendMessage("There is already an active table.").queue();
                return false;
            }
        } catch (UsedTableException e) {
            carriage.channel.sendMessage("The table's state is undergoing change. Please wait until the table session has concluded.").queue();
            return false;
        }


    }




    public boolean isAdministrator(@NotNull DataCarriage carriage) {
        return carriage.message.getMember().hasPermission(Permission.ADMINISTRATOR);
    }
    public boolean canBan(@NotNull DataCarriage carriage) {
        return carriage.message.getMember().hasPermission(Permission.BAN_MEMBERS);
    }
    public boolean canKick(@NotNull DataCarriage carriage) {
        return carriage.message.getMember().hasPermission(Permission.KICK_MEMBERS);
    }
}
