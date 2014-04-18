package org.ei.drishti.util;

import org.joda.time.LocalDate;

public class DateUtil {
    private static DateUtility dateUtility = new RealDate();

    public static void fakeIt(LocalDate fakeDayAsToday) {
        dateUtility = new MockDate(fakeDayAsToday);
    }

    public static LocalDate today() {
        return dateUtility.today();
    }

    public static String formatDateForTimelineEvent(String unformattedDate) {
        return formatDate(unformattedDate, "dd-MM-yyyy");
    }

    public static String formatDate(String unformattedDate) {
        return formatDate(unformattedDate, "dd/MM/yyyy");
    }

    public static String formatDate(String date, String pattern) {
        try {
            return LocalDate.parse(date).toString(pattern);
        } catch (Exception e) {
            return "";
        }
    }
}

interface DateUtility {
    LocalDate today();
}

class RealDate implements DateUtility {
    @Override
    public LocalDate today() {
        return LocalDate.now();
    }
}

class MockDate implements DateUtility {
    private LocalDate fakeDay;

    MockDate(LocalDate fakeDay) {
        this.fakeDay = fakeDay;
    }

    @Override
    public LocalDate today() {
        return fakeDay;
    }
}
