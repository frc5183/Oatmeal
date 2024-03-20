package wtf.triplapeeck.oatmeal.commands.essential;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.Config;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.entities.CustomResponseData;
import wtf.triplapeeck.oatmeal.entities.mariadb.MariaCustomResponse;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class NewResponse extends Command {
    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        if (ensureAdministrator(carriage) && ensureFirstArgument(carriage) && ensureSecondArgument(carriage)) {
            String trigger = carriage.args[1];
            String content = carriage.textAfterSubcommand;
            Collection<MariaCustomResponse> list = carriage.guildEntity.getCustomResponses();
            Config config = Config.getConfig();
            if (list.size()>config.maxResponses) {
                carriage.channel.sendMessage("You can only have " + config.maxResponses +" custom responses! Search time isn't cheap, after all!").queue();
                return;
            }
            for (MariaCustomResponse data : list) {
                if (Objects.equals(data.getTrigger(), trigger)) {
                    carriage.guildEntity.removeCustomResponse(data);
                }
            }
            CustomResponseData data = Main.dataManager.createCustomResponse(trigger, content, carriage.guildEntity);
            carriage.guildEntity.addCustomResponse((MariaCustomResponse) data);
            carriage.channel.sendMessage("Added Custom Response!").queue();
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
        return """
                Used to create or update a custom guild response.
                Usage: s!response [name] [response]
                Create or updates the custom response that when it reads [name] anywhere in a message will respond with [response]""";
    }

    @NotNull
    @Override
    public String getName() {
        return "response";
    }

    @NotNull
    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return isAdministrator(carriage);
    }
}
