package hu.idne.backend.exceptions;

public class OwnDefaultException extends RuntimeException {

    public OwnDefaultException(String message) {
        super(message);
    }

    public OwnDefaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public OwnDefaultException(Throwable cause) {
        super(cause);
    }

    public OwnDefaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}