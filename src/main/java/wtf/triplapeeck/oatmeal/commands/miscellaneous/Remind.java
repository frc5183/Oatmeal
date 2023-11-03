package wtf.triplapeeck.oatmeal.commands.miscellaneous;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.entities.ReminderData;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.util.Utils;

import java.time.Instant;

public class Remind extends Command {

    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        try {
            Long num = Long.valueOf(carriage.args[1]);
            Long time = Utils.parseTimeOffset(num, carriage.args[2]);
            Reminder r = new Reminder(Instant.now().getEpochSecond()+time, carriage.textAfterSubcommand.substring(carriage.args[2].length()+1));
            Main.dataManager.saveReminderData(Main.dataManager.createReminder(r.text, r.unix, carriage.user.getId()));
            carriage.channel.sendMessage("Will remind you in " + num + " " + carriage.args[2]+ "!"+
                    "\nNote: the reminder system only updates every 5 minutes" +
                    "\nNote: the reminder system should not be relied on for important reminders. " +
                    "The developer of this bot is not responsible for any missed reminders.").queue();
        } catch (NumberFormatException e) {
            carriage.channel.sendMessage("Invalid number: " + carriage.args[1]).queue();
        } catch (Utils.TimeFormatException e) {
            carriage.channel.sendMessage(e.toString()).queue();
        } catch (ArrayIndexOutOfBoundsException e) {
            carriage.channel.sendMessage("You haven't included enough arguments for a reminder").queue();
        } catch (StringIndexOutOfBoundsException e) {
            carriage.channel.sendMessage("You haven't included enough arguments for a reminder").queue();
        }
    }

    @NotNull
    @Override
    public String getDocumentation() {
        return "Used to remind yourself of something in the future" +
                "\nUsage: s!remind [amount] [unit] [reminder]" +
                "\nFor Example: `s!remind 5 hours Do my homework` will remind you to Do your homework in 5 hours"
                +"\nNOT Valid: `s!remind 5hours Do my homework`"
                +"\nValid time units for seconds: s,sec,seconds,second" +
                "\nValid time units for minutes: m,min,minutes,minute" +
                "\nValid time units for hours: h,hr,hour,hours,hrs" +
                "\nValid time unites for days: d,days,day" +
                "\nNote: the reminder system only updates every 5 minutes" +
                "\nNote: the reminder system should not be relied on for important reminders. " +
                "The developer of this bot is not responsible for any missed reminders.";
    }

    @NotNull
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MISC;
    }

    @NotNull
    @Override
    public String getName() {
        return "remind";
    }
    @NotNull
    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return true;
    }

    public class Reminder {
        public @NotNull Long unix;
        public @NotNull String text;

        public Reminder(@NotNull Long unix, @NotNull String text) {
            this.unix=unix;
            this.text=text;
        }

        @NotNull
        public synchronized Long getUnix() {
            return unix;
        }

        @NotNull
        public synchronized String getText() {
            return text;
        }

        @Override
        public String toString() {
            GsonBuilder jsonBuilder = new GsonBuilder();
            Gson json = jsonBuilder.create();
            return json.toJson(this);
        }

        public static Reminder fromString(String json) {
            GsonBuilder jsonBuilder = new GsonBuilder();
            Gson gson = jsonBuilder.create();
            return gson.fromJson(json, Reminder.class);
        }
    }
}
