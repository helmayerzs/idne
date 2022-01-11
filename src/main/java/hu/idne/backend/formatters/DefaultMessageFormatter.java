package hu.idne.backend.formatters;

import hu.idne.backend.models.system.MessageFormatter;
import hu.idne.backend.utils.system.BeanUtil;
import org.apache.commons.text.StringSubstitutor;

import java.util.Locale;

public class DefaultMessageFormatter implements MessageFormatter {

    public static final MessageFormatter INSTANCE = new DefaultMessageFormatter();

    @Override
    public <T> String format(String messageKey, String defaultMessageFormat, T args, Locale locale) {
        if (defaultMessageFormat == null) {
            return messageKey;
        }

        if (args == null) {
            return defaultMessageFormat;
        }

        return new StringSubstitutor(BeanUtil.toProperties(args))
                .replace(defaultMessageFormat);
    }
}
