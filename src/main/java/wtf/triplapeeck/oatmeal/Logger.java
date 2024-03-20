package wtf.triplapeeck.oatmeal;

import org.jetbrains.annotations.NotNull;

/**
 * A simple logger class
 */
public class Logger {
    private static boolean enabled = false;

    /**
     * The level of the log
     * INFO: Informational
     * WARN: Warning
     * ERROR: Error
     * FATAL: Fatal Error
     * TRIP: Trip Error (for when Trip-kun messes up, to be changed soon)
     * @see Logger#basicLog(Level, String)
     * @see Logger#customLog(String, String)
     * @see Logger#enable(boolean)
     */
    public enum Level {
        INFO,WARN,ERROR,FATAL;

        /**
         * @return A string representation of the level
         */
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

    /**
     * @param str The string to print.
     * This will only print if {@link Logger#enabled} is true.
     */
    private static synchronized void print(String str) {
        if (enabled) {
            System.out.println(str);
        }
    }

    /**
     * @param level The level of the log
     * @param message The message to log
     * @see Logger#Level
     * This will only print if {@link Logger#enabled} is true.
     */
    public static synchronized void basicLog(@NotNull Level level, @NotNull String message) {
        print("[" + level + "] " + message);
    }

    /**
     * @param name The custom "level" name of the log
     * @param message The message to log
     * This will only print if {@link Logger#enabled} is true.
     * @see Logger#basicLog(Level, String) for more standard logs
     */
    public static synchronized void customLog(@NotNull String name, @NotNull String message) {
        print("[" + name + "] " + message);
    }

    /** Enables or disables logging
     * @param on Whether to enable logging
     */
    public static synchronized void enable(boolean on) {
        enabled=on;
    }
}
