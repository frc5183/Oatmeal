package wtf.triplapeeck.oatmeal.util;

import wtf.triplapeeck.oatmeal.errors.ArgumentException;

public class ConfigParser {
    public static String getToken(String[] args) throws ArgumentException {
        if (!(args[0].equals(""))) {
            return args[0];
        }

        if (!(System.getenv("TOKEN").equals(""))) {
            return System.getenv("TOKEN");
        }

        throw new ArgumentException("Missing Token");
    }
}
