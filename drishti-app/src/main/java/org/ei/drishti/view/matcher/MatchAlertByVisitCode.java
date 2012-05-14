package org.ei.drishti.view.matcher;

import android.content.Context;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForType;
import org.ei.drishti.view.widget.TextCanvas;
import org.ei.drishti.view.DialogAction;

public class MatchAlertByVisitCode extends DialogMatcher<AlertFilterCriterionForType, Alert> {
    public MatchAlertByVisitCode(Context context, DialogAction<AlertFilterCriterionForType> dialogAction) {
        super(dialogAction, AlertFilterCriterionForType.All, TextCanvas.getInstance(context));
    }

    public boolean matches(Alert alert) {
        return alert.visitCode().startsWith(currentValue().visitCodePrefix());
    }

}
