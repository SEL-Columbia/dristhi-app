package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForType;
import org.ei.drishti.util.TextCanvas;
import org.ei.drishti.view.DialogAction;

public class MatchByVisitCode extends DialogMatcher<AlertFilterCriterionForType> {
    public MatchByVisitCode(DialogAction<AlertFilterCriterionForType> dialogAction) {
        super(dialogAction, AlertFilterCriterionForType.All, TextCanvas.getInstance());
    }

    public boolean matches(Alert alert) {
        return alert.visitCode().startsWith(currentValue().visitCodePrefix());
    }
}
