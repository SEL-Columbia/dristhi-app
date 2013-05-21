package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.dto.Action;

public enum MotherActionRoute {
    UPDATE_DETAILS("updateDetails") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().update(action);
        }
    },
    UPDATE_BIRTH_PLANNING("updateBirthPlanning") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().update(action);
        }
    },
    ANC_CARE_PROVIDED("ancCareProvided") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().ancCareProvided(action);
        }
    },
    UPDATE_ANC_OUTCOME("updateANCOutcome") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().updateANCOutcome(action);
        }
    },
    PNC_VISIT_HAPPENED("pncVisitHappened") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().pncVisitHappened(action);
        }
    },
    CLOSE("close") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().close(action);
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
