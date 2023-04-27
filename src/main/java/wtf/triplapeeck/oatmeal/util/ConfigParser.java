package wtf.triplapeeck.oatmeal.util;

import wtf.triplapeeck.oatmeal.errors.MissingTokenException;

public class ConfigParser {
    public static String getToken(String[] args) throws MissingTokenException {
        if (!(args[0].equals(""))) {
            return args[0];
        }

        if (!(System.getenv("TOKEN").equals(""))) {
            return System.getenv("TOKEN");
        }

        throw new MissingTokenException();
    }
}
