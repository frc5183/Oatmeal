package wtf.triplapeeck.oatmeal.runnable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wtf.triplapeeck.oatmeal.DataCarriage;
import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.util.Utils;


import static java.lang.Thread.sleep;

public class Waiting implements NamedRunnable {
    String name = "WAITING";
    public String getName() {
        return name;
    }
    private DataCarriage carriage;
    NamedRunnable task;
    private long timed;
    public Waiting(@NotNull long time) {
        timed=time;
    }
    public Waiting(@NotNull long time, @Nullable NamedRunnable endTask) {timed=time; task=endTask;
    }
    @Override
    public void run() {
        Logger.customLog("Waiting","Starting to Wait " + timed + " Seconds.");
        try {
            sleep(Utils.threadSeconds(timed));
            if (task!=null) {
                Main.threadManager.addTask(task);
            }
        } catch (InterruptedException e) {
        }
        Logger.customLog("Waiting","Finished Waiting " + timed + " Seconds.");
        if (task!=null) {
            Logger.customLog("Waiting","Starting Queued Task.");
        }
    }
}