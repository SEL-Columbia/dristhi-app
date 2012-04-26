package org.ei.drishti.view.matcher;

import org.ei.drishti.view.AfterChangeListener;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.OnSelectionChangeListener;

public abstract class DialogMatcher implements Matcher {
    private DialogAction dialogForChoosingTime;
    private String currentValue;

    public DialogMatcher(DialogAction dialogForChoosingTime) {
        this.dialogForChoosingTime = dialogForChoosingTime;
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        dialogForChoosingTime.setOnSelectionChangedListener(new OnSelectionChangeListener() {
            public void selectionChanged(String selection) {
                currentValue = selection;
                afterChangeListener.afterChangeHappened();
            }
        });
    }

    public Object currentValue() {
        return currentValue;
    }
}
