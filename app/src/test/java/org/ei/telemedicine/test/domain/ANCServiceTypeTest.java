package org.ei.telemedicine.test.domain;

import org.ei.telemedicine.domain.ANCServiceType;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ANCServiceTypeTest {

    @Test
    public void shouldParseStringToACNServiceType() throws Exception {
        assertEquals(ANCServiceType.ANC_1, ANCServiceType.tryParse("anc 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.ANC_2, ANCServiceType.tryParse("anc 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.ANC_3, ANCServiceType.tryParse("anc 3", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.ANC_4, ANCServiceType.tryParse("anc 4", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.TT_1, ANCServiceType.tryParse("tt 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.TT_2, ANCServiceType.tryParse("tt 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.TT_BOOSTER, ANCServiceType.tryParse("TT Booster", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 3", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 1", ANCServiceType.EMPTY));

        assertEquals(ANCServiceType.HB_TEST, ANCServiceType.tryParse("HB Test 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.HB_TEST, ANCServiceType.tryParse("HB Test 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.DELIVERY_PLAN, ANCServiceType.tryParse("Delivery Plan", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.PNC, ANCServiceType.tryParse("PNC", ANCServiceType.EMPTY));

    }


    @Test
    public void shouldParseBlankStringAsDefaultANCServiceType() throws Exception {
        ANCServiceType defaultMethod = ANCServiceType.EMPTY;
        assertEquals(defaultMethod, ANCServiceType.tryParse("", defaultMethod));
    }

    @Test
    public void shouldParseInvalidStringAsDefaultANCServiceType() throws Exception {
        ANCServiceType defaultMethod = ANCServiceType.ANC_1;
        assertEquals(defaultMethod, ANCServiceType.tryParse("---", defaultMethod));
    }
}