package wtf.triplapeeck.sinon.backend.errors;

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
