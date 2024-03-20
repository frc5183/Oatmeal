package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.entities.CustomResponseData;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

import java.util.List;
import java.util.Objects;

public class RemoveResponse extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage)) {
            String trigger = carriage.args[1];
            List<? extends CustomResponseData> list = Main.dataManager.getAllCustomResponseData(carriage.guildEntity);
            for (CustomResponseData data : list) {
                if (Objects.equals(data.getTrigger(), trigger)) {
                    Main.dataManager.removeCustomResponseData(data.getId());
                }
            }
        }
    }

    @NotNull
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.ESSENTIAL;
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to remove a custom guild response."+
                "\nUsage s!removeresponse [name]"+
                "\nRemove the custom response that responds to [name]";
    }

    @NotNull
    @Override
    public String getName() {
        return "removeresponse";
    }

    @NotNull
    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }
}
