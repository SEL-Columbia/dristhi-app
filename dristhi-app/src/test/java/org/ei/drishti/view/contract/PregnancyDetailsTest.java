package org.ei.drishti.view.contract;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class PregnancyDetailsTest {

    @Test
    public void isLastMonthOfPregnancy() throws Exception {
        PregnancyDetails pregnancyDetails = new PregnancyDetails("8", "2012-09-17");
        assertTrue(pregnancyDetails.isLastMonthOfPregnancy());

        pregnancyDetails = new PregnancyDetails("7", "2012-09-17");
        assertFalse(pregnancyDetails.isLastMonthOfPregnancy());
    }

}
