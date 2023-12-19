package wtf.triplapeeck.oatmeal.util;

/**
 * A class containing utility methods
 */
public class Utils {
    /**
     * @param t The time in seconds
     * @return The time in milliseconds
     */
    public static long threadSeconds(long t) {
        return t*1000;
    }

    /** This method is no longer used, but was used for parsing commands
     * @deprecated This method is deprecated and will be removed in a future version.
     * @param msg The message to parse
     * @return The message split by spaces
     */
    @Deprecated
    public static String[] parseCommands(String msg) {
        return msg.split("\\s+");
    }

    /** A simple boolean to language converter
     * @param c The boolean to check
     * @return "is" if true, "is not" if false
     */
    public static String isNot(boolean c) {
        if(c) {
            return "is";
        } else {
            return "is not";
        }
    }

    /** A method to convert many types of time to seconds
     * @param number The number to parse
     * @param type The type of time to parse
     * @return The time in seconds
     * @throws TimeFormatException
     */
    public static long parseTimeOffset(long number, String type) throws TimeFormatException  {
        int mul=1;
        switch (type) {
            case "s":
            case "sec":
            case "seconds":
            case "second":
                mul=1;
                break;
            case "m":
            case "min":
            case "minutes":
            case "minute":
                mul=60;
                break;
            case "h":
            case "hr":
            case "hrs":
            case "hour":
            case "hours":
                mul=3600;
                break;
            case "d":
            case "day":
            case "days":
                mul=24*3600;
                break;
            default:
                throw new TimeFormatException(type);
        }
        return number*mul;
    }

    /**
     * An exception thrown when a time unit is not recognized
     */
    public static class TimeFormatException extends Exception {
        private String s;
        public TimeFormatException(String s) {
            this.s=s;
        }

        @Override
        public String toString() {
            return "Unknown or unsupported time unit: " + s;
        }
    }
}
