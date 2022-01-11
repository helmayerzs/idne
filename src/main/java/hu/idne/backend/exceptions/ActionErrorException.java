package hu.idne.backend.exceptions;

import hu.idne.backend.formatters.DefaultMessageFormatter;
import hu.idne.backend.models.system.ActionError;
import hu.idne.backend.models.system.MessageFormatter;
import lombok.Getter;

@Getter
public class ActionErrorException extends RuntimeException {
    private static final MessageFormatter formatter = new DefaultMessageFormatter();

    private final transient ActionError<?> error;

    public ActionErrorException(ActionError<?> error) {
        super(error.format(formatter));
        this.error = error;
    }

    public ActionErrorException(Throwable cause, ActionError<?> error) {
        super(error.format(formatter), cause);
        this.error = error;
    }
}
