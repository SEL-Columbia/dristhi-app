package org.ei.drishti.view.preProcessor;

import org.ei.drishti.view.contract.ExpectedVisit;
import org.ei.drishti.view.contract.PNCClient;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PNCClientPreProcessorTest {
    @Test @Ignore
    public void should_properly_calculate_the_expected_visits_day_date_values() throws Exception {
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "1122334", "2013-05-13");
        ExpectedVisit expectedVisit1 = new ExpectedVisit(1, "2013-05-14");
        ExpectedVisit expectedVisit2 = new ExpectedVisit(3, "2013-05-16");
        ExpectedVisit expectedVisit3 = new ExpectedVisit(7, "2013-05-20");
        List<ExpectedVisit> expectedVisits = Arrays.asList(expectedVisit1, expectedVisit2, expectedVisit3);

        PNCClient processedPNCClient = new PNCClientPreProcessor().preProcess(pncClient);

        assertEquals(expectedVisits, processedPNCClient.getExpectedVisits());
    }

}