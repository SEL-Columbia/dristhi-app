package org.ei.telemedicine.domain;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.dto.Action;
import org.ei.telemedicine.event.Event;

public enum MotherActionRoute {
    CLOSE("close") {
        @Override
        public void direct(Action action) {
            Context.getInstance().motherService().close(action.caseID(), action.get("reasonForClose"));
            Event.ACTION_HANDLED.notifyListeners("Mother closed");
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