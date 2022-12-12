package wtf.triplapeeck.sinon.backend.listeners;

import wtf.triplapeeck.sinon.backend.Logger;
import wtf.triplapeeck.sinon.backend.Main;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class ThreadManager extends Thread {
    DefaultListener listener;
    boolean requestToEnd=false;

    public ArrayList<Thread> threadList= new ArrayList<>();
    public LinkedBlockingQueue<Thread> queue = new LinkedBlockingQueue<>();
    @Override
    public void run() {
        Logger.customLog("ThreadManager", "Started");
        listener = Main.listener;

        while (true) {
            queue.drainTo(threadList);
            boolean stillFinishing=false;
            for (Thread t : threadList) {

                if (t.getState()== State.NEW) {
                    stillFinishing=true;
                    Logger.customLog("ThreadManager", "Started New Thread");
                    t.start();
                } else if (t.getState()== State.RUNNABLE) {
                    stillFinishing=true;
                } else if (t.getState()==State.WAITING) {
                    stillFinishing=true;
                } else if (t.getState()==State.TIMED_WAITING) {
                    stillFinishing=true;
                } else if (t.getState()==State.BLOCKED) {
                    stillFinishing=true;
                }
            }
            if ((!stillFinishing) && requestToEnd) {
                break;
            }
        }
        Logger.customLog("ThreadManager", "Complete");
    }
    public synchronized void requestToEnd() {
        requestToEnd=true;
        Logger.customLog("ThreadManager", "Ending Soon: Waiting On Threads");
    }

    public synchronized int addTask(Thread t) {
        Logger.customLog("ThreadManager", "New Thread Received. Added to Queue.");
        var i = threadList.size();
        queue.offer(t);
        return i;
    }
}
