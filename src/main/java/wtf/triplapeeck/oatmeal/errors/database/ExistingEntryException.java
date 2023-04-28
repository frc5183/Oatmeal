package wtf.triplapeeck.oatmeal.errors.database;

public class ExistingEntryException extends Exception {
    public ExistingEntryException(String message) {
        super(message);
    }
}
