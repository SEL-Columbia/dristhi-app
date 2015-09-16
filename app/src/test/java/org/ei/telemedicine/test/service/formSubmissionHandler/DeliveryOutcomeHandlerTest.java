package org.ei.telemedicine.test.service.formSubmissionHandler;

import android.test.AndroidTestCase;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.ChildService;
import org.ei.telemedicine.service.MotherService;
import org.ei.telemedicine.service.formSubmissionHandler.DeliveryOutcomeHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.ei.telemedicine.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryOutcomeHandlerTest {
    @Mock
    private MotherService motherService;
    @Mock
    private ChildService childService;

    private DeliveryOutcomeHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        handler = new DeliveryOutcomeHandler(motherService, childService);
    }

    @Test
    public void testShouldDelegateFormSubmissionHandlingToMotherService() throws Exception {
        FormSubmission submission = create().withFormName("delivery_outcome").withInstanceId("instance id 1").withVersion("122").build();

        handler.handle(submission);

        verify(motherService).deliveryOutcome(submission);
        verify(childService).register(submission);
    }
}
