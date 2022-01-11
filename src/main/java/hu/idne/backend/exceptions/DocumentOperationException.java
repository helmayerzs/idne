package hu.idne.backend.exceptions;

public class DocumentOperationException extends RuntimeException {

    public DocumentOperationException(String message) {
        super(message);
    }

    public DocumentOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentOperationException(Throwable cause) {
        super(cause);
    }

    public DocumentOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
