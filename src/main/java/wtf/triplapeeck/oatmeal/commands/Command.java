package wtf.triplapeeck.oatmeal.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.entities.UserData;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.errors.UsedTableException;

import java.util.List;

public abstract class Command {
    public enum CommandCategory {
        ESSENTIAL("Essential", 1, "Essential commands for the bot"),
        MISC("Miscellaneous", 2, "Miscellaneous commands for the bot"),
        CARD_GAMES("Card Games", 3, "Card games for the bot"),
        CURRENCY("Currency", 4, "Currency commands for the bot"),
        CUSTOM_COMMAND("Custom Commands", 5, "Custom commands for the bot"),
        SINON_ADMIN("Sinon Admin", 10, "Sinon Admin commands for the bot"),
        SINON_OWNER("Sinon Owner", 11, "Sinon Owner commands for the bot");

        private final String name;
        private final int id;
        private final String description;

        CommandCategory(String name, int id, String description) {
            this.name = name;
            this.id = id;
            this.description = description;
        }

        @Nullable
        public static CommandCategory fromInt(int id) {
            switch (id) {
                case 1 -> {
                    return ESSENTIAL;
                }
                case 2 -> {
                    return MISC;
                }
                case 3 -> {
                    return CARD_GAMES;
                }
                case 4 -> {
                    return CURRENCY;
                }
                case 5 -> {
                    return CUSTOM_COMMAND;
                }
                case 10 -> {
                    return SINON_ADMIN;
                }
                case 11 -> {
                    return SINON_OWNER;
                }
                default -> {
                    return null;
                }
            }
        }

        @Nullable
        public static CommandCategory fromString(String value) {
            switch (value.toLowerCase()) {
                case "essential" -> {
                    return ESSENTIAL;
                }
                case "misc", "miscellaneous" -> {
                    return MISC;
                }
                case "card games", "card" -> {
                    return CARD_GAMES;
                }
                case "currency" -> {
                    return CURRENCY;
                }
                case "custom commands", "custom command" -> {
                    return CUSTOM_COMMAND;
                }
                case "sinon admin", "admin" -> {
                    return SINON_ADMIN;
                }
                case "sinon owner", "owner" -> {
                    return SINON_OWNER;
                }
                default -> {
                    return null;
                }
            }
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

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

    public abstract @NotNull CommandCategory getCategory();
    public abstract @NotNull String getDocumentation();
    public abstract @NotNull String getName();

    public abstract @NotNull boolean hasPermission(DataCarriage carriage, User user);
    public boolean ensureIsAdmin(@NotNull DataCarriage carriage) {
        if (isAdmin(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("You are not allowed to do this command.").queue();
            return false;
        }

    }
    public boolean isOwner(@NotNull DataCarriage carriage) {
        return carriage.userEntity.isOwner();
    }
    public boolean isAdmin(@NotNull DataCarriage carriage) {
        return carriage.userEntity.isAdmin();
    }
    public boolean ensureIsOwner(@NotNull DataCarriage carriage) {
        if (isOwner(carriage)) {
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
    public boolean getOwnerStatusOfOnlyOneTagged(@NotNull DataCarriage carriage) {
        List<User> userList = carriage.message.getMentions().getUsers();
        User user = userList.get(0);
        UserData usUsr = Main.dataManager.getUserData(user.getId());
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
    public boolean currencyEnabled(@NotNull DataCarriage carriage) {
        return carriage.guildEntity.isCurrencyEnabled();
    }
    public boolean testingEnabled(@NotNull DataCarriage carriage) {
        return carriage.guildEntity.isTestingEnabled();
    }
    public boolean currencyPreference(@NotNull DataCarriage carriage) {
        return carriage.userEntity.isCurrencyPreference();
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
        carriage.channelStorable.releaseTable();
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
    public boolean isThreadable(@NotNull DataCarriage carriage) {
        ChannelType type = carriage.channel.getType();
        return type == ChannelType.TEXT;
    }
    public boolean ensureThreadable(@NotNull DataCarriage carriage) {
        if (isThreadable(carriage)) {
            return true;
        } else {
            carriage.channel.sendMessage("You cannot use this command unless threads are available, such as within normal guild channels!").queue();
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
        return carriage.message.getMember().hasPermission(Permission.BAN_MEMBERS) || isOwner(carriage);
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
