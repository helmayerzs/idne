package hu.idne.backend.models.system;

import java.util.Locale;

@FunctionalInterface
public interface MessageFormatter {
    <T> String format(String messageKey, String defaultMessageFormat, T args, Locale locale);
}
