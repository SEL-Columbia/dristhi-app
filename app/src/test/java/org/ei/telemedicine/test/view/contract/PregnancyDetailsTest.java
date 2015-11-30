package org.ei.telemedicine.test.view.contract;

import org.ei.telemedicine.view.contract.PregnancyDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PregnancyDetailsTest {


    @Test
    public void testIsLastMonthOfPregnancy() throws Exception {
        PregnancyDetails pregnancyDetails = new PregnancyDetails("8", "2012-09-17", 0);
        assertTrue(pregnancyDetails.isLastMonthOfPregnancy());

        pregnancyDetails = new PregnancyDetails("7", "2012-09-17", 0);
        assertFalse(pregnancyDetails.isLastMonthOfPregnancy());
    }


}
