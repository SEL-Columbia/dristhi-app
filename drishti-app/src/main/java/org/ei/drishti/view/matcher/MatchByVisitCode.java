package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForType;
import org.ei.drishti.view.DialogAction;

public class MatchByVisitCode extends DialogMatcher {
    public MatchByVisitCode(DialogAction dialogAction) {
        super(dialogAction, AlertFilterCriterionForType.All);
    }

    public boolean matches(Alert alert) {
        AlertFilterCriterionForType criterion = (AlertFilterCriterionForType) currentValue();
        return alert.visitCode().startsWith(criterion.visitCodePrefix());
    }
}
