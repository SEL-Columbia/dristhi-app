package org.ei.drishti.domain;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public enum AlertFilterCriterionForTime {
     ShowAll("All", MIN_VALUE, MAX_VALUE), PastDue("Past Due", MIN_VALUE, -1), Upcoming("Upcoming", 0, MAX_VALUE);

    private String display;
    private final long startOfRangeInDaysFromToday;
    private final long endOfRangeInDaysFromToday;

    AlertFilterCriterionForTime(String display, long startOfRangeInDaysFromToday, long endOfRangeInDaysFromToday) {
        this.display = display;
        this.startOfRangeInDaysFromToday = startOfRangeInDaysFromToday;
        this.endOfRangeInDaysFromToday = endOfRangeInDaysFromToday;
    }

    public String toString() {
        return display;
    }

    public boolean isInRange(long days) {
        return startOfRangeInDaysFromToday <= days && days <= endOfRangeInDaysFromToday;
    }
}
