package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.CommCareForm;

import java.util.HashMap;
import java.util.Map;

public class ProfileTodo {
    private String message;
    private CommCareForm formToOpen;

    private static Map<String, TodoDetail> map = new HashMap<String, TodoDetail>();

    static {
        map.put("ANC 1", new TodoDetail("ANC Visit #1", CommCareForm.ANC_SERVICES));
        map.put("ANC 2", new TodoDetail("ANC Visit #2", CommCareForm.ANC_SERVICES));
        map.put("ANC 3", new TodoDetail("ANC Visit #3", CommCareForm.ANC_SERVICES));
        map.put("ANC 4", new TodoDetail("ANC Visit #4", CommCareForm.ANC_SERVICES));
    }

    public ProfileTodo(Alert alert) {
        message = messageFor(alert.visitCode());
        formToOpen = formToOpenFor(alert.visitCode());
    }

    private String messageFor(String visitCode) {
        return todoDetailFor(visitCode).prefix();
    }

    private CommCareForm formToOpenFor(String visitCode) {
        return todoDetailFor(visitCode).formToOpen();
    }

    private TodoDetail todoDetailFor(String visitCode) {
        TodoDetail todoDetail = map.get(visitCode);
        return todoDetail == null ? new TodoDetail(visitCode, null) : todoDetail;
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

    private static class TodoDetail {
        private final String messagePrefix;
        private final CommCareForm formToOpen;

        public TodoDetail(String messagePrefix, CommCareForm formToOpen) {
            this.messagePrefix = messagePrefix;
            this.formToOpen = formToOpen;
        }

        public String prefix() {
            return messagePrefix;
        }

        public CommCareForm formToOpen() {
            return formToOpen;
        }
    }
}
