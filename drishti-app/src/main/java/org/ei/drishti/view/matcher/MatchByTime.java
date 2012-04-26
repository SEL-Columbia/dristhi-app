package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForTime;
import org.ei.drishti.view.DialogAction;

import java.util.Date;

import static org.ei.drishti.util.DateUtil.parseDate;

public class MatchByTime extends DialogMatcher<AlertFilterCriterionForTime> {
    public static final int MILLIS_IN_A_DAY = 3600 * 24 * 1000;

    public MatchByTime(DialogAction<AlertFilterCriterionForTime> dialogAction) {
        super(dialogAction, AlertFilterCriterionForTime.ShowAll);
    }

    public boolean matches(Alert alert) {
        long numberOfDays = (parseDate(alert.dueDate()).getTime() - new Date().getTime()) / MILLIS_IN_A_DAY;
        return currentValue().isInRange(numberOfDays);
    }
}
