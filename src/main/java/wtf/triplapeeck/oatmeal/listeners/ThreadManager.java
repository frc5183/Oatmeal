package wtf.triplapeeck.oatmeal.listeners;

import wtf.triplapeeck.oatmeal.Logger;
import wtf.triplapeeck.oatmeal.Main;
import wtf.triplapeeck.oatmeal.runnable.Heartbeat;
import wtf.triplapeeck.oatmeal.runnable.NamedRunnable;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class ThreadManager extends Thread {
    DefaultListener listener;
    boolean requestToEnd=false;

    public ArrayList<NamedThread> threadList= new ArrayList<>();
    public LinkedBlockingQueue<NamedThread> queue = new LinkedBlockingQueue<>();
    private class NamedThread {
        public NamedRunnable name;
        public Thread t;
        public NamedThread(NamedRunnable nr, Thread thread) {
            name=nr;
            t=thread;
        }
    }
    @Override
    public void run() {
        Logger.customLog("ThreadManager", "Started");
        listener = Main.listener;

        while (true) {
            queue.drainTo(threadList);
            boolean stillFinishing=false;
            ArrayList<NamedThread> finishedList= new ArrayList<>();
            for (NamedThread t : threadList) {
                if (t.t.getName()=="Heartbeat" && requestToEnd) {
                    Heartbeat.requestToEnd();
                }
                if (t.t.getState()== State.NEW) {

                    Logger.customLog("ThreadManager", "Started New Thread with name: "+t.name.getName());
                    t.t.start();
                } else if (t.t.getState()== State.RUNNABLE) {

                } else if (t.t.getState()==State.WAITING) {

                } else if (t.t.getState()==State.TIMED_WAITING) {

                } else if (t.t.getState()==State.BLOCKED) {

                } else if (t.t.getState()==State.TERMINATED)          {
                    Logger.customLog("ThreadManager", "Finished Thread with name: " + t.name.getName() + " and id: " + threadList.indexOf(t));
                    finishedList.add(t);
                }
            }
            for (NamedThread i: finishedList) {
                threadList.remove(i);
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

    public synchronized int addTask(NamedRunnable t) {
        var i = threadList.size();
        Logger.customLog("ThreadManager", "New Thread Received. Added to Queue. Name:" + t.getName() + " Id: " + i);

        queue.offer(new NamedThread(t,new Thread(t)));
        return i;
    }
    public synchronized boolean containsType(NamedRunnable t) {
        boolean out = false;
        for (NamedThread i: threadList) {
            out = out || (i.name.getName() == t.getName() );
        }
        return out;
    }
}
