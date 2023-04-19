package wtf.triplapeeck.sinon.backend.runnable;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.commands.miscellaneous.Remind;
import wtf.triplapeeck.sinon.backend.storable.GenericStorable;
import wtf.triplapeeck.sinon.backend.storable.StorableManager;
import wtf.triplapeeck.sinon.backend.storable.UserStorable;

import java.sql.Time;
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
                UserStorable us = StorableManager.getUser(i);

                for (Long l: us.getReminderList().keySet()) {
                    Remind.Reminder r = us.getReminderList().get(l);
                    try {
                        if (Instant.now().compareTo(Instant.ofEpochSecond(r.getUnix())) >= 0) {
                            temp.add(l);
                            String s = "I am here to remind you of the following: " + r.getText();
                            int y = s.length();
                            PrivateChannel channel = Main.api.openPrivateChannelById(us.getIDLong()).complete();
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
                        temp.add(l);
                    }
                }
                for (Long r: temp) {
                    us.getReminderList().remove(r);
                }
                temp.clear();
                us.relinquishAccess();
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
