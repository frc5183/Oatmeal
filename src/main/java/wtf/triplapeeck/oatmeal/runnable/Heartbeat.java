package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import wtf.triplapeeck.oatmeal.entities.ReminderData;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;

public class Heartbeat implements NamedRunnable {
    public static Heartbeat beat = new Heartbeat();
    public static boolean requestToEnd=false;
    public static synchronized void requestToEnd() {
        requestToEnd=true;
    }
    @Override
    public String getName() {
        return "Heartbeat";
    }

    @Override
    public void run() {
        while (true) {
            Logger.customLog("Heartbeat", "beep");
            ArrayList<Long> temp = new ArrayList<>();
            for (ReminderData reminder : Main.dataManager.getAllReminderData()) {
                try {
                    if (Instant.now().compareTo(Instant.ofEpochSecond(reminder.getUnix())) > 0) {
                        temp.add(Long.valueOf(reminder.getId()));
                        String s = "I am here to remind you of the following: " + reminder.getText();
                        int y = s.length();
                        PrivateChannel channel = Main.api.openPrivateChannelById(reminder.getUser().getID()).complete();
                        do {
                            if (y <= 2000) {
                                channel.sendMessage(s).queue();
                                y -= y;
                                s = "";
                            } else {
                                channel.sendMessage(s.substring(0, 1999)).queue();
                                s = s.substring(2000);
                            }
                            y -= 2000;

                        } while (y > 0);
                    }
                } catch (DateTimeException e) {
                    temp.add(reminder.getId());
                }
            }
            for (Long r : temp) {
                Main.dataManager.removeReminderData(r);
            }
            temp.clear();
            Main.dataManager.saveAll();
            try {
                Thread.sleep(300000);
            } catch (InterruptedException ignored) {
            }

            if (requestToEnd) {
                break;
            }
        }

    }
}
