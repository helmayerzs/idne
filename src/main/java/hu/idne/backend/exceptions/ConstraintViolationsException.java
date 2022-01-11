package hu.idne.backend.exceptions;

import am.ik.yavi.core.ConstraintViolations;
import lombok.Getter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.lang.NonNull;

public class ConstraintViolationsException extends RuntimeException {

    @Getter
    private final transient ConstraintViolations violations;
    private final transient Object actual;

    public ConstraintViolationsException(ConstraintViolations violations) {
        super("Validation failed");
        this.violations = violations;
        this.actual = null;
    }

    public ConstraintViolationsException(String message, ConstraintViolations violations) {
        super(message);
        this.violations = violations;
        this.actual = null;
    }

    public ConstraintViolationsException(ConstraintViolations violations, Object actual) {
        super("Validation failed");
        this.violations = violations;
        this.actual = actual;
    }

    public ConstraintViolationsException(String message, ConstraintViolations violations, Object actual) {
        super(message);
        this.violations = violations;
        this.actual = actual;
    }

    @SuppressWarnings("unchecked")
    public <T> T getActual(@NonNull Class<T> type) {
        if (actual == null) {
            return null;
        }

        if (type.isInstance(actual)) {
            return (T) actual;
        }

        throw new TypeMismatchException(actual, type);
    }
}
