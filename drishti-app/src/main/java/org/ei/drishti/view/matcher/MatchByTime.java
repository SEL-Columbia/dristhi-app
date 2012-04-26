package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.view.DialogAction;

public class MatchByTime extends DialogMatcher {
    public MatchByTime(DialogAction dialogAction) {
        super(dialogAction);
    }

    public boolean matches(Alert alert) {
        return true;
    }
}
