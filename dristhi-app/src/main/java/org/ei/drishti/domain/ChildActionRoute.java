package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.dto.Action;

public enum ChildActionRoute {
    REGISTER("register") {
        @Override
        protected void direct(Action action) {
            Context.getInstance().childService().register(action);
        }
    },
    PNC_VISIT_HAPPENED("pncVisitHappened") {
        @Override
        protected void direct(Action action) {
            Context.getInstance().childService().pncVisit(action);
        }
    },
    UPDATE_IMMUNIZATION("updateImmunizations") {
        @Override
        protected void direct(Action action) {
            Context.getInstance().childService().updateImmunizations(action);
        }
    },
    DELETE_CHILD("deleteChild") {
        @Override
        protected void direct(Action action) {
            Context.getInstance().childService().delete(action);
        }
    };

    private String identifier;

    ChildActionRoute(String identifier) {
        this.identifier = identifier;
    }

    private String identifier() {
        return identifier;
    }

    public static void directAction(Action action) {
        ChildActionRoute[] childActionRoutes = values();
        for (ChildActionRoute childActionRoute : childActionRoutes) {
            if (childActionRoute.identifier().equals(action.type())) {
                childActionRoute.direct(action);
                return;
            }
        }
    }

    protected abstract void direct(Action action);
}
