package org.ei.drishti.service.formSubmissionHandler;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.repository.FormDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.ei.drishti.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
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
                ancCloseHandler);
    }

    @Test
    public void shouldDelegateFormSubmissionHandlingToHandlerBasedOnFormName() throws Exception {
        FormSubmission formSubmission = create().withFormName("ec_registration").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ecRegistrationHandler).handle(formSubmission);

        formSubmission = create().withFormName("fp_complications").withInstanceId("instance id 2").withVersion("123").build();
        when(formDataRepository.fetchFromSubmission("instance id 2")).thenReturn(formSubmission);

        router.route("instance id 2");

        verify(formDataRepository).fetchFromSubmission("instance id 2");
        verify(fpComplicationsHandler).handle(formSubmission);
    }

    @Test
    public void shouldDelegateRenewFPProductFormSubmissionHandlingToRenewFPProductHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("renew_fp_product").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(renewFPProductHandler).handle(formSubmission);
    }

    @Test
    public void shouldDelegateECCloseFormSubmissionHandlingToECCloseHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("ec_close").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ecCloseHandler).handle(formSubmission);
    }

    @Test
    public void shouldDelegateANCRegistrationFormSubmissionHandlingToANCRegistrationHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_registration").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancRegistrationHandler).handle(formSubmission);
    }

    @Test
    public void shouldDelegateANCRegistrationOAFormSubmissionHandlingToANCRegistrationOAHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_registration_oa").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancRegistrationOAHandler).handle(formSubmission);
    }

    @Test
    public void shouldDelegateANCVisitFormSubmissionHandlingToANCVisitHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_visit").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancVisitHandler).handle(formSubmission);
    }

    @Test
    public void shouldDelegateANCCloseFormSubmissionHandlingToANCCloseHandler() throws Exception {
        FormSubmission formSubmission = create().withFormName("anc_close").withInstanceId("instance id 1").withVersion("122").build();
        when(formDataRepository.fetchFromSubmission("instance id 1")).thenReturn(formSubmission);

        router.route("instance id 1");

        verify(formDataRepository).fetchFromSubmission("instance id 1");
        verify(ancCloseHandler).handle(formSubmission);
    }
}
