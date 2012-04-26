package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.view.AfterChangeListener;

public interface Matcher<T> {
    void setOnChangeListener(AfterChangeListener afterChangeListener);

    T currentValue();

    boolean matches(Alert alert);
}
