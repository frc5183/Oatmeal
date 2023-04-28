package wtf.triplapeeck.oatmeal.util;

public class Utils {
    public static long threadSeconds(long t) {
        return t*1000;
    }
    public static String[] parseCommands(String msg) {
        return msg.split("\\s+");
    }

    public static String isNot(boolean c) {
        if(c) {
            return "is";
        } else {
            return "is not";
        }
    }
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
