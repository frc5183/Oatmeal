package wtf.triplapeeck.sinon.backend.runnable;

import org.jetbrains.annotations.NotNull;
import wtf.triplapeeck.sinon.backend.DataCarriage;
import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;
import wtf.triplapeeck.sinon.backend.Utils;

import javax.annotation.Nullable;

import static java.lang.Thread.sleep;

public class Waiting implements Runnable {
    private DataCarriage carriage;
    Thread task;
    private long timed;
    public Waiting(@NotNull long time) {
        timed=time;
    }
    public Waiting(@NotNull long time, @Nullable Thread endTask) {timed=time; task=endTask;
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