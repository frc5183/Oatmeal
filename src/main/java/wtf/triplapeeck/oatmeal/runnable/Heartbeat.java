package wtf.triplapeeck.oatmeal.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import wtf.triplapeeck.oatmeal.entities.UserEntity;
import wtf.triplapeeck.oatmeal.storable.GenericStorable;
import wtf.triplapeeck.oatmeal.storable.StorableManager;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.commands.miscellaneous.Remind;

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
            GenericStorable gs = StorableManager.getGeneric(0L);
            for (Long i : gs.getKnownUserList().keySet()) {
                UserEntity us = Main.entityManager.getUserEntity(String.valueOf(i));

                for (String l: us.getReminders().keySet()) {
                    Remind.Reminder r = us.getReminders().get(l);
                    try {
                        if (Instant.now().compareTo(Instant.ofEpochSecond(r.getUnix())) >= 0) {
                            temp.add(Long.valueOf(l));
                            String s = "I am here to remind you of the following: " + r.getText();
                            int y = s.length();
                            PrivateChannel channel = Main.api.openPrivateChannelById(us.getID()).complete();
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
                        temp.add(Long.valueOf(l));
                    }
                }
                for (Long r: temp) {
                    us.getReminders().remove(String.valueOf(r));
                }
                temp.clear();
                us.release();
            }
            gs.relinquishAccess();
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
            }

            if (requestToEnd) {
                break;
            }
        }

    }
}
