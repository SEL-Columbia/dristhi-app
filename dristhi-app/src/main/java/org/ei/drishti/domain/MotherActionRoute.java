package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.dto.Action;

public enum MotherActionRoute {
    PNC_VISIT_HAPPENED("pncVisitHappened") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().pncVisitHappened(action);
        }
    };

    private String identifier;

    MotherActionRoute(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

    public abstract void direct(Action action);
}
