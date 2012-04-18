package org.ei.drishti.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static String formattedDueDate(String dueDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            return outputFormat.format(inputFormat.parse(dueDate));
        } catch (ParseException e) {
            return dueDate;
        }
    }
}
