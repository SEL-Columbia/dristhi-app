package org.ei.drishti.util;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static DateUtility dateUtility = new RealDate();

    public static void fakeIt(LocalDate fakeDayAsToday) {
        dateUtility = new MockDate(fakeDayAsToday);
    }

    public static LocalDate today() {
        return dateUtility.today();
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
