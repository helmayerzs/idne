package hu.idne.backend.models.system;

import hu.idne.backend.exceptions.ActionErrorException;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Getter
public class ActionError<T> {
    private final String messageKey;
    private final String defaultMessageFormat;
    private final T body;

    @Setter
    private Throwable cause;

    public ActionError(String messageKey, String defaultMessageFormat, T body) {
        this.defaultMessageFormat = defaultMessageFormat;
        this.messageKey = messageKey;
        this.body = body;
    }

    @SuppressWarnings("squid:S3740")
    public static ActionError of(MessageKey messageKey, String defaultMessageFormat) {
        return new ActionError(messageKey.key(), defaultMessageFormat, null);
    }

    public static <T> ActionError<T> of(MessageKey messageKey, String defaultMessageFormat, T body) {
        return new ActionError<>(messageKey.key(), defaultMessageFormat, body);
    }

    public String format(MessageFormatter formatter, Locale locale) {
        return formatter.format(messageKey, defaultMessageFormat, body, locale);
    }

    public String format(MessageFormatter formatter) {
        return formatter.format(messageKey, defaultMessageFormat, body, Locale.getDefault());
    }

    public void throwError() {
        throw new ActionErrorException(this);
    }

    public void throwError(Throwable cause) {
        this.cause = cause;
        throw new ActionErrorException(cause, this);
    }

    public ActionErrorException obtainException() {
        return new ActionErrorException(this);
    }

    public ActionErrorException obtainException(Throwable cause) {
        this.cause = cause;
        return new ActionErrorException(this);
    }
}
