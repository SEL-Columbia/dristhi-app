package org.ei.drishti.util;

public class IntegerUtil {
    public static Integer tryParse(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String tryParse(String value, String defaultValue) {
        try {
            return String.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
