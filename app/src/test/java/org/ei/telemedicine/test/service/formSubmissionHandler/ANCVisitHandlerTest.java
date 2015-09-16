package org.ei.telemedicine.test.service.formSubmissionHandler;

import android.test.AndroidTestCase;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.MotherService;
import org.ei.telemedicine.service.formSubmissionHandler.ANCVisitHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.ei.telemedicine.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ANCVisitHandlerTest {
    @Mock
    private MotherService motherService;

    private ANCVisitHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        handler = new ANCVisitHandler(motherService);
    }

    @Test
    public void testShouldDelegateFormSubmissionHandlingToMotherService() throws Exception {
        FormSubmission submission = create().withFormName("anc_visit").withInstanceId("instance id 1").withVersion("122").build();

        handler.handle(submission);

        verify(motherService).ancVisit(submission);
    }
}
