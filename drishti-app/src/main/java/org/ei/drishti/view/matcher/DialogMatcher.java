package org.ei.drishti.view.matcher;

import android.view.View;
import org.ei.drishti.domain.Displayable;
import org.ei.drishti.util.TextCanvas;
import org.ei.drishti.view.AfterChangeListener;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.OnSelectionChangeListener;

public abstract class DialogMatcher<T extends Displayable> implements Matcher<T> {
    private DialogAction<T> dialogForChoosingAnOption;
    private T currentValue;
    private final T defaultValue;
    private final TextCanvas textCanvas;

    public DialogMatcher(DialogAction<T> dialogForChoosingAnOption, T defaultValue, TextCanvas textCanvas) {
        this.dialogForChoosingAnOption = dialogForChoosingAnOption;
        this.defaultValue = defaultValue;
        this.textCanvas = textCanvas;
        currentValue = this.defaultValue;
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        dialogForChoosingAnOption.setOnSelectionChangedListener(new OnSelectionChangeListener<T>() {
            public void selectionChanged(View actionItemView, T selection) {
                currentValue = selection;
                actionItemView.setSelected(!selection.equals(defaultValue));
                actionItemView.setBackgroundDrawable(textCanvas.drawableFor(selection.displayValue()));
                afterChangeListener.afterChangeHappened();
            }
        });
    }

    public T currentValue() {
        return currentValue;
    }
}
