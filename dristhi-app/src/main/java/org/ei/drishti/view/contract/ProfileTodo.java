package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.CommCareForm;

public class ProfileTodo {
    private String message;
    private CommCareForm formToOpen;
    private boolean isCompleted;
    private String visitCode;

    public ProfileTodo(Alert alert) {
        visitCode = alert.visitCode();
        message = TodoDetail.from(visitCode).prefix();
        formToOpen = TodoDetail.from(visitCode).formToOpen();
        isCompleted = alert.isClosed();
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
