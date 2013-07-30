package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.repository.FormDataRepository;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.AllConstants.FormNames.*;
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
                                ANCCloseHandler ancCloseHandler,
                                TTHandler ttHandler,
                                IFAHandler ifaHandler,
                                HBTestHandler hbTestHandler,
                                DeliveryOutcomeHandler deliveryOutcomeHandler,
                                PNCRegistrationOAHandler pncRegistrationOAHandler,
                                PNCCloseHandler pncCloseHandler,
                                PNCVisitHandler pncVisitHandler,
                                ChildImmunizationsHandler childImmunizationsHandler,
                                ChildRegistrationECHandler childRegistrationECHandler,
                                ChildRegistrationOAHandler childRegistrationOAHandler,
                                ChildCloseHandler childCloseHandler, ChildIllnessHandler childIllnessHandler,
                                VitaminAHandler vitaminAHandler) {
        this.formDataRepository = formDataRepository;
        handlerMap = new HashMap<String, FormSubmissionHandler>();
        handlerMap.put(EC_REGISTRATION, ecRegistrationHandler);
        handlerMap.put(FP_COMPLICATIONS, fpComplicationsHandler);
        handlerMap.put(FP_CHANGE, fpChangeHandler);
        handlerMap.put(RENEW_FP_PRODUCT, renewFPProductHandler);
        handlerMap.put(EC_CLOSE, ecCloseHandler);
        handlerMap.put(ANC_REGISTRATION, ancRegistrationHandler);
        handlerMap.put(ANC_REGISTRATION_OA, ancRegistrationOAHandler);
        handlerMap.put(ANC_VISIT, ancVisitHandler);
        handlerMap.put(ANC_CLOSE, ancCloseHandler);
        handlerMap.put(TT_BOOSTER, ttHandler);
        handlerMap.put(TT_1, ttHandler);
        handlerMap.put(TT_2, ttHandler);
        handlerMap.put(IFA, ifaHandler);
        handlerMap.put(HB_TEST, hbTestHandler);
        handlerMap.put(DELIVERY_OUTCOME, deliveryOutcomeHandler);
        handlerMap.put(PNC_REGISTRATION_OA, pncRegistrationOAHandler);
        handlerMap.put(PNC_CLOSE, pncCloseHandler);
        handlerMap.put(PNC_VISIT, pncVisitHandler);
        handlerMap.put(CHILD_IMMUNIZATIONS, childImmunizationsHandler);
        handlerMap.put(CHILD_REGISTRATION_EC, childRegistrationECHandler);
        handlerMap.put(CHILD_REGISTRATION_OA, childRegistrationOAHandler);
        handlerMap.put(CHILD_CLOSE, childCloseHandler);
        handlerMap.put(CHILD_ILLNESS, childIllnessHandler);
        handlerMap.put(VITAMIN_A, vitaminAHandler);
    }

    public void route(String instanceId) {
        FormSubmission submission = formDataRepository.fetchFromSubmission(instanceId);
        FormSubmissionHandler handler = handlerMap.get(submission.formName());
        if (handler == null) {
            logWarn("Could not find a handler due to unknown form submission: " + submission);
        } else {
            handler.handle(submission);
        }
        FORM_SUBMITTED.notifyListeners(instanceId);
    }
}
