package hu.idne.backend.utils.system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConverterUtils {

    private ConverterUtils() {
    }

    public Integer stringToInteger(String text) {
        Integer value = 0;
        try {
            value = Integer.parseInt(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static LocalDate stringToLocalDate(String text, DateTimeFormatter formatter) {
        LocalDate value = LocalDate.now();
        try {
            value = LocalDate.parse(text, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
