package org.ei.telemedicine.test.domain;

import org.ei.telemedicine.domain.Alert;
import org.ei.telemedicine.dto.AlertStatus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

//import org.ei.telemedicine.view.contract.AlertStatus;

/**
 * Created by naveen on 12/24/15.
 */
public class AlertTest {
    Alert alert;
    AlertStatus alertStatus;

    @Before
    public void setUp() {
        alert = new Alert("123", "anm1", "454", alertStatus, "12/08/2015", "18/09/2015");
    }

    @Test
    public void caseIdTest() {
        assertEquals("123", alert.caseId());
    }


    @Test
    public void expiryDateTest() {
        assertEquals("18/09/2015", alert.expiryDate());
    }

    @Test
    public void scheduleDateTest() {

        assertEquals("anm1", alert.scheduleName());
    }

    @Test
    public void completionDateTest() {
        alert.withCompletionDate("25/10/2016");
        assertEquals("25/10/2016", alert.completionDate());
    }
}
