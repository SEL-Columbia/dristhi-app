package org.ei.telemedicine.test.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.repository.FormDataRepository;
import org.ei.telemedicine.service.formSubmissionHandler.ANCCloseHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ANCInvestigationsHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ANCRegistrationHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ANCRegistrationOAHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ANCVisitHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ChildCloseHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ChildIllnessHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ChildImmunizationsHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ChildRegistrationECHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ChildRegistrationOAHandler;
import org.ei.telemedicine.service.formSubmissionHandler.DeliveryOutcomeHandler;
import org.ei.telemedicine.service.formSubmissionHandler.DeliveryPlanHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ECCloseHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ECEditHandler;
import org.ei.telemedicine.service.formSubmissionHandler.ECRegistrationHandler;
import org.ei.telemedicine.service.formSubmissionHandler.FPChangeHandler;
import org.ei.telemedicine.service.formSubmissionHandler.FPComplicationsHandler;
import org.ei.telemedicine.service.formSubmissionHandler.FormSubmissionRouter;
import org.ei.telemedicine.service.formSubmissionHandler.HBTestHandler;
import org.ei.telemedicine.service.formSubmissionHandler.IFAHandler;
import org.ei.telemedicine.service.formSubmissionHandler.PNCCloseHandler;
import org.ei.telemedicine.service.formSubmissionHandler.PNCRegistrationOAHandler;
import org.ei.telemedicine.service.formSubmissionHandler.PNCVisitHandler;
import org.ei.telemedicine.service.formSubmissionHandler.RenewFPProductHandler;
import org.ei.telemedicine.service.formSubmissionHandler.TTHandler;
import org.ei.telemedicine.service.formSubmissionHandler.TestSubmissionHandler;
import org.ei.telemedicine.service.formSubmissionHandler.VitaminAHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.ei.telemedicine.event.Event.FORM_SUBMITTED;
import static org.ei.telemedicine.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class FormSubmissionRouterTest {
    @Mock
    private FormDataRepository formDataRepository;
    @Mock
    private ECRegistrationHandler ecRegistrationHandler;
    @Mock
    private FPComplicationsHandler fpComplicationsHandler;
    @Mock
    private FPChangeHandler fpChangeHandler;
    @Mock
    private RenewFPProductHandler renewFPProductHandler;
    @Mock
    private ECCloseHandler ecCloseHandler;
    @Mock
    private ANCCloseHandler ancCloseHandler;
    @Mock
    private ANCRegistrationHandler ancRegistrationHandler;
    @Mock
    private ANCRegistrationOAHandler ancRegistrationOAHandler;
    @Mock
    private ANCVisitHandler ancVisitHandler;
    @Mock
    private TTHandler ttHandler;
    @Mock
    private IFAHandler ifaHandler;
    @Mock
    private HBTestHandler hbTestHandler;
    @Mock
    private DeliveryOutcomeHandler deliveryOutcomeHandler;
    @Mock
    private PNCRegistrationOAHandler pncRegistrationOAHandler;
    @Mock
    private PNCCloseHandler pncCloseHandler;
    @Mock
    private PNCVisitHandler pncVisitHandler;
    @Mock
    private ChildImmunizationsHandler childImmunizationsHandler;
    @Mock
    private ChildRegistrationECHandler childRegistrationECHandler;
    @Mock
    private ChildRegistrationOAHandler childRegistrationOAHandler;
    @Mock
    private ChildCloseHandler childCloseHandler;
    @Mock
    private ChildIllnessHandler childIllnessHandler;
    @Mock
    private VitaminAHandler vitaminAHandler;
    @Mock
    private DeliveryPlanHandler deliveryPlanHandler;
    @Mock
    private ECEditHandler ecEditHandler;
    @Mock
    private ANCInvestigationsHandler ancInvestigationsHandler;
    @Mock
    private Listener<String> formSubmittedListener;
    @Mock
    private TestSubmissionHandler testSubmissionHandler;

    private FormSubmissionRouter router;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        router = new FormSubmissionRouter(formDataRepository,
                ecRegistrationHandler,
                fpComplicationsHandler,
                fpChangeHandler,
                renewFPProductHandler,
                ecCloseHandler,
                ancRegistrationHandler,
                ancRegistrationOAHandler,
                ancVisitHandler,
                ancCloseHandler,
                ttHandler,
                ifaHandler,
                hbTestHandler,
                deliveryOutcomeHandler,
                pncRegistrationOAHandler,
                pncCloseHandler,
                pncVisitHandler,
                childImmunizationsHandler, childRegistrationECHandler, childRegistrationOAHandler, childCloseHandler,
                childIllnessHandler, vitaminAHandler, deliveryPlanHandler, ecEditHandler, ancInvestigationsHandler, testSubmissionHandler);
    }

    @Test
    public void testShouldNotifyFormSubmittedListenersWhenFormIsHandled() throws Exception {
        FormSubmission formSubmission = create().withFormName("ec_registration").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);
        FORM_SUBMITTED.addListener(formSubmittedListener);

        router.route("instance id 1");

        InOrder inOrder = inOrder(formDataRepository, ecRegistrationHandler, formSubmittedListener);
        inOrder.verify(formDataRepository).fetchFromSubmission("instance id 1");
        inOrder.verify(ecRegistrationHandler).handle(formSubmission);
        inOrder.verify(formSubmittedListener).onEvent("instance id 1");
    }

    @Test
    public void testShouldNotifyFormSubmittedListenersWhenThereIsNoHandlerForForm() throws Exception {
        FormSubmission formSubmission = create().withFormName("form-without-handler").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);
        FORM_SUBMITTED.addListener(formSubmittedListener);

        router.route("instance id 1");

        InOrder inOrder = inOrder(formDataRepository, formSubmittedListener);
        inOrder.verify(formDataRepository).fetchFromSubmission("instance id 1");
        inOrder.verify(formSubmittedListener).onEvent("instance id 1");
    }

    @Test
    public void testShouldDelegateECRegistrationFormSubmissionHandlingToECRegistrationHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("ec_registration").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);
        FORM_SUBMITTED.addListener(formSubmittedListener);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ecRegistrationHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateFPComplicationsFormSubmissionHandlingToFPComplicationsHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("fp_complications").withInstanceId("instance id 2").withVersion("123").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(fpComplicationsHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateRenewFPProductFormSubmissionHandlingToRenewFPProductHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("renew_fp_product").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(renewFPProductHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateECCloseFormSubmissionHandlingToECCloseHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("ec_close").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ecCloseHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateANCRegistrationFormSubmissionHandlingToANCRegistrationHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_registration").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancRegistrationHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateANCRegistrationOAFormSubmissionHandlingToANCRegistrationOAHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_registration_oa").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancRegistrationOAHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateANCVisitFormSubmissionHandlingToANCVisitHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_visit").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancVisitHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateANCCloseFormSubmissionHandlingToANCCloseHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_close").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancCloseHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateTTFormSubmissionHandlingToTTHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("tt").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ttHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateTTBoosterFormSubmissionHandlingToTTHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("tt_booster").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ttHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateTT1FormSubmissionHandlingToTTHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("tt_1").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ttHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateTT2FormSubmissionHandlingToTTHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("tt_2").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ttHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateIFAFormSubmissionHandlingToIFAHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("ifa").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ifaHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateHBTestFormSubmissionHandlingToHBTestHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("hb_test").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(hbTestHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateDeliveryOutcomeFormSubmissionHandlingToDeliveryOutcomeHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("delivery_outcome").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(deliveryOutcomeHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegatePNCRegistrationFormSubmissionHandlingToPNCRegistrationHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("pnc_registration_oa").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(pncRegistrationOAHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegatePNCVisitFormSubmissionHandlingToPNCVisitHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("pnc_visit").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(pncVisitHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegatePNCCloseFormSubmissionHandlingToPNCCloseHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("pnc_close").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(pncCloseHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateChildImmunizationsFormSubmissionHandlingToChildImmunizationsHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("child_immunizations").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(childImmunizationsHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateChildRegistrationECFormSubmissionHandlingToChildRegistrationECHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("child_registration_ec").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(childRegistrationECHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateChildCloseFormSubmissionHandlingToChildCloseHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("child_close").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(childCloseHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateChildIllnessFormSubmissionHandlingToChildIllnessHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("child_illness").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(childIllnessHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateVitaminAFormSubmissionHandlingToVitaminAHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("vitamin_a").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(vitaminAHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateChildRegistrationOAFormSubmissionHandlingToChildRegistrationOAHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("child_registration_oa").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(childRegistrationOAHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateDeliveryPlanFormSubmissionHandlingToDeliveryPlanHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("delivery_plan").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(deliveryPlanHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateECEditFormSubmissionHandlingToECEditHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("ec_edit").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ecEditHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateECEditFormSubmissionHandlingToTestSubmissionHanler() throws Exception {
        FormSubmission formSubmission = create().withFormName("cus_reg_form").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(testSubmissionHandler).handle(formSubmission);
    }

    @Test
    public void testShouldDelegateANCInvestigationsFormSubmissionHandlingToANCInvestigationsHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_investigations").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancInvestigationsHandler).handle(formSubmission);
    }
}
