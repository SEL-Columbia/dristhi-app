package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.Alert;

public class ProfileTodo {
    private String message;
    private boolean isCompleted;
    private String visitCode;
    private String todoDate;

    public ProfileTodo(Alert alert) {
        visitCode = alert.visitCode();
        message = TodoDetail.from(visitCode).prefix();
        isCompleted = alert.isComplete();

        todoDate = alert.expiryDate();
        if (isCompleted) {
            todoDate = alert.completionDate();
        }
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
