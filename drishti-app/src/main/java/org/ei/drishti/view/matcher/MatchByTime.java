package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForTime;
import org.ei.drishti.view.DialogAction;

public class MatchByTime extends DialogMatcher {
    public MatchByTime(DialogAction dialogAction) {
        super(dialogAction, AlertFilterCriterionForTime.ShowAll);
    }

    public boolean matches(Alert alert) {
        AlertFilterCriterionForTime criterion = (AlertFilterCriterionForTime) currentValue();
        return true;
    }
}
