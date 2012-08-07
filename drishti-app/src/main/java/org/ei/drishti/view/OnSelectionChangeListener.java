package org.ei.drishti.view;

import android.view.View;

public interface OnSelectionChangeListener<T> {

    void selectionChanged(View actionItemView, T selection);
}
