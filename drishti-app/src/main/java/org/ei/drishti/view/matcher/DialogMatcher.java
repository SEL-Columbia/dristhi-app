package org.ei.drishti.view.matcher;

import org.ei.drishti.view.AfterChangeListener;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.OnSelectionChangeListener;

public abstract class DialogMatcher<T> implements Matcher<T> {
    private DialogAction dialogForChoosingTime;
    private T currentValue;

    public DialogMatcher(DialogAction<T> dialogForChoosingTime, T defaultValue) {
        this.dialogForChoosingTime = dialogForChoosingTime;
        currentValue = defaultValue;
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        dialogForChoosingTime.setOnSelectionChangedListener(new OnSelectionChangeListener<T>() {
            public void selectionChanged(T selection) {
                currentValue = selection;
                afterChangeListener.afterChangeHappened();
            }
        });
    }

    public T currentValue() {
        return currentValue;
    }
}
