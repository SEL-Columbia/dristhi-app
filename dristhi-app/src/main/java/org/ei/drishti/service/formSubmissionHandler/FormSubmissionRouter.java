package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.repository.FormDataRepository;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.AllConstants.EC_REGISTRATION;
import static org.ei.drishti.AllConstants.FP_COMPLICATIONS;
import static org.ei.drishti.util.Log.logWarn;

public class FormSubmissionRouter {
    private final Map<String, FormSubmissionHandler> handlerMap;
    private FormDataRepository formDataRepository;

    public FormSubmissionRouter(FormDataRepository formDataRepository,
                                ECRegistrationHandler ecRegistrationHandler,
                                FPComplicationsHandler fpComplicationsHandler) {
        this.formDataRepository = formDataRepository;
        handlerMap = new HashMap<String, FormSubmissionHandler>();
        handlerMap.put(EC_REGISTRATION, ecRegistrationHandler);
        handlerMap.put(FP_COMPLICATIONS, fpComplicationsHandler);

    }

    public void route(String instanceId) {
        FormSubmission submission = formDataRepository.fetchFromSubmission(instanceId);
        FormSubmissionHandler handler = handlerMap.get(submission.formName());
        if (handler == null) {
            logWarn("Could not find a handler due to unknown form submission: " + submission);
            return;
        }
        handler.handle(submission);
    }
}
