package hu.idne.backend.exceptions;


import lombok.Getter;

public class PersistException extends RuntimeException {

    @Getter
    private final Throwable cause;

    public PersistException(String message) {
        super(message);
        this.cause = null;
    }

    public PersistException(String message, Throwable cause) {
        super(message, cause);
        this.cause = cause;
    }

    public PersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.cause = cause;
    }
}
