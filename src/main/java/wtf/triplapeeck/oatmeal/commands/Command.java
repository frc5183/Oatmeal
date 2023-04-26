package wtf.triplapeeck.oatmeal.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.storable.StorableFactory;
import wtf.triplapeeck.oatmeal.storable.UserStorable;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;

import java.util.List;

public abstract class Command {
    public boolean ensureGuild(DataCarriage carriage) {
        if (carriage.guild==null) {
            carriage.channel.sendMessage("This command is not available outside of a server.").queue();
        }
        return true;
    }
    public boolean isGuild(DataCarriage carriage) {
        return carriage.guild != null;
    }
    public abstract void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener);

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
        return carriage.message.getMentions().getUsers().size();
    }
    public int taggedMemberListLength(@NotNull DataCarriage carriage) {
        return carriage.message.getMentions().getMembers().size();
    }
    public int taggedChannelListLength(@NotNull DataCarriage carriage) {
        return carriage.message.getMentions().getChannels().size();
    }
    public boolean ensureTaggedChannelListNotEmpty(@NotNull DataCarriage carriage) {
        if (!carriage.message.getMentions().getChannels().isEmpty()) {
            return true;
        } else {
            carriage.channel.sendMessage("You must tag a channel").queue();
            return false;
        }
    }
    public boolean ensureTaggedUserListNotEmpty(@NotNull DataCarriage carriage) {
        if (!carriage.message.getMentions().getUsers().isEmpty()) {
            return true;
        } else {
            carriage.channel.sendMessage("You must tag someone").queue();
            return false;
        }
    }
    public boolean ensureTaggedMemberListNotEmpty(@NotNull DataCarriage carriage) {
        if (!carriage.message.getMentions().getMembers().isEmpty()) {
            return true;
        } else {
            carriage.channel.sendMessage("You must tag someone.").queue();
            return false;
        }
    }
    public boolean ensureOnlyOneTaggedChannel(@NotNull DataCarriage carriage) {
        if (taggedChannelListLength(carriage)==1) {
            return true;
        } else {
            carriage.channel.sendMessage("You must tag only one channel").queue();
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
    public boolean ensureOnlyOneTaggedMember(@NotNull DataCarriage carriage) {
        if (taggedMemberListLength(carriage)==1) {
            return true;
        } else {
            carriage.channel.sendMessage("You must tag only one person").queue();
            return false;
        }
    }
    public boolean ensureOnlyOneTaggedIsNotTrip(@NotNull DataCarriage carriage) {
        if (carriage.message.getMentions().getUsers().get(0).getIdLong()!=222517551257747456L)
            return true;
        else {
            carriage.channel.sendMessage("You cannot target Trip-kun with this command.").queue();
            return false;
        }
    }
    public boolean getOwnerStatusOfOnlyOneTagged(@NotNull DataCarriage carriage) {
        List<User> userList = carriage.message.getMentions().getUsers();
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


    public boolean isFirstArgument(@NotNull DataCarriage carriage) {
        try {
            return (carriage.args[1] != "" && carriage.args[1] != null);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    public boolean isSecondArgument(@NotNull DataCarriage carriage) {
        try {
            return (isFirstArgument(carriage) && carriage.args[2] != "" && carriage.args[2] != null);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    public boolean ensureFirstArgument(@NotNull DataCarriage carriage) {
        if (isFirstArgument(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("You have to include at least 1 argument").queue();
            return false;
        }
    }
    public boolean ensureSecondArgument(@NotNull DataCarriage carriage) {
        if (isSecondArgument(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("You have to include at least 2 arguments").queue();
            return false;
        }
    }
    public boolean isAdministrator(@NotNull DataCarriage carriage) {
        if (!isGuild(carriage)) { return true;}
        return carriage.message.getMember().hasPermission(Permission.ADMINISTRATOR);
    }
    public boolean ensureAdministrator(@NotNull DataCarriage carriage) {
        if (isAdministrator(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("You must be administrator to use this command").queue();
            return false;
        }
    }
    public boolean canBan(@NotNull DataCarriage carriage) {
        if (!isGuild(carriage)) { return false;}
        return carriage.message.getMember().hasPermission(Permission.BAN_MEMBERS);
    }
    public boolean ensureBan(@NotNull DataCarriage carriage) {
        if (canBan(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("You must have the ban members permission to use this command.").queue();
            return false;
        }
    }
    public boolean canKick(@NotNull DataCarriage carriage) {
        if (!isGuild(carriage)) { return false;}
        return carriage.message.getMember().hasPermission(Permission.KICK_MEMBERS);
    }
}
