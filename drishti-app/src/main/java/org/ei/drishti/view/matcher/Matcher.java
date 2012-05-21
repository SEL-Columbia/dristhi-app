package org.ei.drishti.view.matcher;

import org.ei.drishti.domain.Displayable;
import org.ei.drishti.view.AfterChangeListener;

public interface Matcher<T extends Displayable, Entity> {
    void setOnChangeListener(AfterChangeListener afterChangeListener);

    T currentValue();

    boolean matches(Entity entity);

    boolean isActive();
}
