package org.ei.drishti.domain;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public enum AlertFilterCriterionForTime {
     ShowAll("All", MIN_VALUE, MAX_VALUE), PastDue("Past Due", MIN_VALUE, 0), Upcoming("Upcoming", 1, MAX_VALUE);

    private String display;
    private final int startOfRangeInDaysFromToday;
    private final int endOfRangeInDaysFromToday;

    AlertFilterCriterionForTime(String display, int startOfRangeInDaysFromToday, int endOfRangeInDaysFromToday) {
        this.display = display;
        this.startOfRangeInDaysFromToday = startOfRangeInDaysFromToday;
        this.endOfRangeInDaysFromToday = endOfRangeInDaysFromToday;
    }

    public String toString() {
        return display;
    }
}
