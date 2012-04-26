package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForTime;
import org.ei.drishti.view.DialogAction;

public class MatchByTime extends DialogMatcher<AlertFilterCriterionForTime> {
    public MatchByTime(DialogAction<AlertFilterCriterionForTime> dialogAction) {
        super(dialogAction, AlertFilterCriterionForTime.ShowAll);
    }

    public boolean matches(Alert alert) {
        return true;
    }
}
