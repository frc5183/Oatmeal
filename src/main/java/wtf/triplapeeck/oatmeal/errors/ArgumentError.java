package wtf.triplapeeck.oatmeal.errors;

public class ArgumentError extends Error {
    public String message;
    public ArgumentError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
