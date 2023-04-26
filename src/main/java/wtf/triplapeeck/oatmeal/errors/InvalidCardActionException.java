package wtf.triplapeeck.oatmeal.errors;

public class InvalidCardActionException extends Exception {
    private String msg;

    public InvalidCardActionException(String MSG) {
        msg=MSG;
    }
    @Override
    public String getMessage() {
        return msg;
    }
}
