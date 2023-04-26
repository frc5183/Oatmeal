package wtf.triplapeeck.sinon.backend.runnable;

public interface NamedRunnable extends Runnable {
    String name = "DEFAULT";

    abstract String getName();
}
