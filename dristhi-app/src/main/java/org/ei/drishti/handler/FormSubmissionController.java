package org.ei.drishti.handler;

import java.util.ArrayList;
import java.util.List;

public class FormSubmissionController {
    List<FormHandler> handlers = new ArrayList<FormHandler>();

    public FormSubmissionController() {
        this.handlers.add(new PNCVisitHandler());
    }

    public boolean onSubmit(String formType, String instanceId, String data) {
        FormHandler handler = findHandler(formType);
        if (handler != null)
            handler.process(instanceId, data);
        //else do with a generic data handler
        throw new RuntimeException("Not implemented");
    }

    private FormHandler findHandler(String formType) {
        for (FormHandler handler : handlers) {
            if (handler.appliesTo(formType)) {
                return handler;
            }
        }
        return null;
    }
}
