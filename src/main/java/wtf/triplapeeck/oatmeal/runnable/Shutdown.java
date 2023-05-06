package wtf.triplapeeck.oatmeal.runnable;

import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.listeners.ThreadManager;

import java.time.Duration;
import java.time.Instant;

public class Shutdown implements NamedRunnable {
    @Override
    public String getName() {
        return "SHUTDOWN";
    }
     DataCarriage carriage;
    ThreadManager listener;
    @Override
    public void run() {
        Logger.basicLog(Logger.Level.INFO, "Sinon-Reboot Initiated.");
        Main.listener.pause();
        Instant start = Instant.now();
        do {

        } while (Duration.between(start, Instant.now()).toSeconds() < 1);
        listener.requestToEnd();

        Main.dataManager.requestToEnd();

        carriage.channel.sendMessage("Rebooting. Please allow a moment for rebooting to finish.").complete();
        System.out.println("HELLO");
        while (true) {
            if (listener.getState()== Thread.State.TERMINATED) {
                System.out.println("HELLO2");
                break;
            }
        }
        carriage.api.shutdownNow();
    }
    public Shutdown(DataCarriage carriage, ThreadManager listener) {
        this.carriage=carriage;
        this.listener=listener;
    }
}
