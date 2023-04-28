package wtf.triplapeeck.oatmeal.errors.database;

public class MissingEntryException extends Exception {
    public MissingEntryException(String message) {
        super(message);
    }
}