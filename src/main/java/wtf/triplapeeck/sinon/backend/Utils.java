package wtf.triplapeeck.sinon.backend;

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
}
