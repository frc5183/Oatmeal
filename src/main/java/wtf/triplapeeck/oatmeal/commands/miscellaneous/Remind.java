package wtf.triplapeeck.oatmeal.commands.miscellaneous;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.oatmeal.commands.Command;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Page;
import wtf.triplapeeck.oatmeal.util.Utils;

import java.time.Instant;

public class Remind extends Command {

    @Override
    public void handler(MessageReceivedEvent event, DataCarriage carriage, ThreadManager listener) {
        try {
            Long num = Long.valueOf(carriage.args[1]);
            Long time = Utils.parseTimeOffset(num, carriage.args[2]);
            Reminder r = new Reminder(Instant.now().getEpochSecond()+time, carriage.textAfterSubcommand.substring(carriage.args[2].length()+1));
            carriage.userStorable.getReminderList().put((long) carriage.userStorable.getReminderList().size()-1, r);
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
    public String getName() {
        return "remind";
    }
    public Remind() {
        Page.Miscellaneous.addCommand(this);
    }
    @NotNull
    @Override
    public boolean hasPermission(DataCarriage carriage, User user) {
        return true;
    }

    public class Reminder {
        public long unix;
        public String text;
        public Reminder(long unix, String text) {
            this.unix=unix;
            this.text=text;
        }

        public long getUnix() {
            return unix;
        }

        public String getText() {
            return text;
        }
    }
}
