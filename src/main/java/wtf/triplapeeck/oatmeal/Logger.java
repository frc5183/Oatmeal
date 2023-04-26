package wtf.triplapeeck.oatmeal;

import org.jetbrains.annotations.NotNull;

public class Logger {

    public enum Level {
        INFO,WARN,ERROR,FATAL;

        public String toString() {
            if (this==INFO) {
                return "INFO";
            } else if (this==WARN) {
                return "WARN";
            } else if (this==ERROR) {
                return "ERROR";
            } else if (this==FATAL) {
                return "FATAL";
            }
            return "TRIP";
        }
    }
    private static synchronized void print(String str) {
        //System.out.println(str);
    }

    public static synchronized void basicLog(@NotNull Level level, @NotNull String message) {
        print("[" + level + "] " + message);
    }
    public static synchronized void customLog(@NotNull String name, @NotNull String message) {
        print("[" + name + "] " + message);
    }
}
