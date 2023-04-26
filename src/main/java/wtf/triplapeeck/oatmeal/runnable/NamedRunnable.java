package wtf.triplapeeck.oatmeal.runnable;

public interface NamedRunnable extends Runnable {
    String name = "DEFAULT";

    abstract String getName();
}
