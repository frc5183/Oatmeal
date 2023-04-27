package wtf.triplapeeck.oatmeal.errors;

public class ArgumentException extends Exception {
    public String message;
    public ArgumentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
