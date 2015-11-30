package org.ei.telemedicine.test.service.formSubmissionHandler;

import android.test.AndroidTestCase;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.EligibleCoupleService;
import org.ei.telemedicine.service.formSubmissionHandler.RenewFPProductHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.ei.telemedicine.util.FormSubmissionBuilder.create;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class RenewFPProductHandlerTest {
    @Mock
    private EligibleCoupleService ecService;

    private RenewFPProductHandler handler;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        handler = new RenewFPProductHandler(ecService);
    }

    @Test
    public void testShouldDelegateFormSubmissionHandlingToECService() throws Exception {
        FormSubmission submission = create().withFormName("renew_fp_product").withInstanceId("instance id 1").withVersion("122").build();

        handler.handle(submission);

        verify(ecService).renewFPProduct(submission);
    }

}
