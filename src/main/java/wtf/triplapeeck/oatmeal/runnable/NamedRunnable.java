package wtf.triplapeeck.oatmeal.runnable;

public interface NamedRunnable extends Runnable {
    String name = "DEFAULT";

    String getName();
}
