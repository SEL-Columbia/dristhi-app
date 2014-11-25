package org.ei.drishti.view.preProcessor;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.ExpectedVisit;
import org.ei.drishti.view.contract.PNCClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PNCClientPreProcessorTest {
    @Mock
    Context mockedContext;
    private Context realContext;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        realContext = Context.getInstance();
        Context.setInstance(mockedContext);
        when(mockedContext.getStringResource(R.string.str_pnc_circle_type_expected)).thenReturn("expected");
        when(mockedContext.getStringResource(R.string.str_pnc_circle_type_actual)).thenReturn("actual");
    }

    @After
    public void tearDown() throws Exception {
        Context.setInstance(realContext);
    }

    @Test
    public void should_properly_calculate_the_expected_visits_day_date_values() throws Exception {
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "1122334", "2013-05-13");
        ExpectedVisit expectedVisit1 = new ExpectedVisit(1, "2013-05-14");
        ExpectedVisit expectedVisit2 = new ExpectedVisit(3, "2013-05-16");
        ExpectedVisit expectedVisit3 = new ExpectedVisit(7, "2013-05-20");
        List<ExpectedVisit> expectedVisits = Arrays.asList(expectedVisit1, expectedVisit2, expectedVisit3);

        PNCClient processedPNCClient = new PNCClientPreProcessor(pncClient).preProcess();

        assertEquals(expectedVisits, processedPNCClient.getExpectedVisits());
    }

}