package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Criterion;
import org.ei.drishti.view.AfterChangeListener;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.OnSelectionChangeListener;

public abstract class DialogMatcher implements Matcher {
    private DialogAction dialogForChoosingTime;
    private Criterion currentValue;

    public DialogMatcher(DialogAction dialogForChoosingTime, Criterion defaultValue) {
        this.dialogForChoosingTime = dialogForChoosingTime;
        currentValue = defaultValue;
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        dialogForChoosingTime.setOnSelectionChangedListener(new OnSelectionChangeListener() {
            public void selectionChanged(Criterion selection) {
                currentValue = selection;
                afterChangeListener.afterChangeHappened();
            }
        });
    }

    public Object currentValue() {
        return currentValue;
    }
}
