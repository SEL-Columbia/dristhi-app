package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.repository.FormDataRepository;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.AllConstants.*;
import static org.ei.drishti.event.Event.FORM_SUBMITTED;
import static org.ei.drishti.util.Log.logWarn;

public class FormSubmissionRouter {
    private final Map<String, FormSubmissionHandler> handlerMap;
    private FormDataRepository formDataRepository;

    public FormSubmissionRouter(FormDataRepository formDataRepository,
                                ECRegistrationHandler ecRegistrationHandler,
                                FPComplicationsHandler fpComplicationsHandler,
                                FPChangeHandler fpChangeHandler,
                                RenewFPProductHandler renewFPProductHandler,
                                ECCloseHandler ecCloseHandler,
                                ANCRegistrationHandler ancRegistrationHandler,
                                ANCRegistrationOAHandler ancRegistrationOAHandler,
                                ANCVisitHandler ancVisitHandler,
                                ANCCloseHandler ancCloseHandler) {
        this.formDataRepository = formDataRepository;
        handlerMap = new HashMap<String, FormSubmissionHandler>();
        handlerMap.put(EC_REGISTRATION_FORM_NAME, ecRegistrationHandler);
        handlerMap.put(FP_COMPLICATIONS_FORM_NAME, fpComplicationsHandler);
        handlerMap.put(FP_CHANGE_FORM_NAME, fpChangeHandler);
        handlerMap.put(RENEW_FP_PRODUCT_FORM_NAME, renewFPProductHandler);
        handlerMap.put(EC_CLOSE_FORM_NAME, ecCloseHandler);
        handlerMap.put(ANC_REGISTRATION_FORM_NAME, ancRegistrationHandler);
        handlerMap.put(ANC_REGISTRATION_OA_FORM_NAME, ancRegistrationOAHandler);
        handlerMap.put(ANC_VISIT_FORM_NAME, ancVisitHandler);
        handlerMap.put(ANC_CLOSE_FORM_NAME, ancCloseHandler);
    }

    public void route(String instanceId) {
        FORM_SUBMITTED.notifyListeners(instanceId);
        FormSubmission submission = formDataRepository.fetchFromSubmission(instanceId);
        FormSubmissionHandler handler = handlerMap.get(submission.formName());
        if (handler == null) {
            logWarn("Could not find a handler due to unknown form submission: " + submission);
            return;
        }
        handler.handle(submission);
    }
}
