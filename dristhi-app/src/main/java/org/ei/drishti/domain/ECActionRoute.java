package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.dto.Action;

public enum ECActionRoute {
    REGISTER("createEC") {
        @Override
        public void direct(Action action) {
            Context.getInstance().eligibleCoupleService().register(action);
        }
    },
    UPDATE_DETAILS("updateDetails") {
        @Override
        public void direct(Action action) {
            Context.getInstance().eligibleCoupleService().updateDetails(action);
        }
    },
    DELETE("deleteEC") {
        @Override
        public void direct(Action action) {
            Context.getInstance().eligibleCoupleService().delete(action);
        }
    };

    private String identifier;

    ECActionRoute(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

    public abstract void direct(Action action);
}
