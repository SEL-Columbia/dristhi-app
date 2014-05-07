package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.ChildService;
import org.ei.drishti.service.MotherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static org.ei.drishti.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class PNCRegistrationHandlerTest {
    @Mock
    private MotherService motherService;
    @Mock
    private ChildService childService;

    private PNCRegistrationHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        handler = new PNCRegistrationHandler(motherService, childService);
    }

    @Test
    public void shouldDelegateFormSubmissionHandlingToMotherService() throws Exception {
        FormSubmission submission = create().withFormName("pnc_registration").withInstanceId("instance id 1").withVersion("122").build();

        handler.handle(submission);

        verify(motherService).deliveryOutcome(submission);
        verify(childService).register(submission);
    }
}
