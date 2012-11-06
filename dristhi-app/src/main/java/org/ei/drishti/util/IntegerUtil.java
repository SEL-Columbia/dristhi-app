package org.ei.drishti.util;

public class IntegerUtil {
    public static Integer tryParse(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
