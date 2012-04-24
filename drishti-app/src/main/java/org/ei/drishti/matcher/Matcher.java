package org.ei.drishti.matcher;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.util.AfterChangeListener;

public interface Matcher {
    boolean matches(Alert alert);

    void setOnChangeListener(AfterChangeListener afterChangeListener);

    Object currentValue();
}
