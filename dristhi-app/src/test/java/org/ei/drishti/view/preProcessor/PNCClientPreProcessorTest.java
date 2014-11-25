package org.ei.drishti.view.preProcessor;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
        ServiceProvidedDTO expectedVisit1 = new ServiceProvidedDTO("PNC", 1, "2013-05-14");
        ServiceProvidedDTO expectedVisit2 = new ServiceProvidedDTO("PNC", 3, "2013-05-16");
        ServiceProvidedDTO expectedVisit3 = new ServiceProvidedDTO("PNC", 7, "2013-05-20");
        List<ServiceProvidedDTO> expectedVisits = Arrays.asList(expectedVisit1, expectedVisit2, expectedVisit3);

        PNCClient processedPNCClient = new PNCClientPreProcessor(pncClient).preProcess();

        assertEquals(expectedVisits, processedPNCClient.getExpectedVisits());
    }

    @Test
    @Ignore
    public void should_generate_a_list_of_visits_within_the_first_7_days_sorted_by_visit_date() throws Exception {
        ServiceProvidedDTO serviceProvidedDTO1 = new ServiceProvidedDTO("PNC", "2013-05-26", null);
        ServiceProvidedDTO serviceProvidedDTO2 = new ServiceProvidedDTO("PNC", "2013-05-15", null);
        ServiceProvidedDTO serviceProvidedDTO3 = new ServiceProvidedDTO("PNC", "2013-05-14", null);
        ServiceProvidedDTO serviceProvidedDTO4 = new ServiceProvidedDTO("PNC", "2013-05-17", null);
        ServiceProvidedDTO serviceProvidedDTO5 = new ServiceProvidedDTO("PNC", "2013-05-20", null);
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "1122334", "2013-05-13")
                .withServicesProvided(Arrays.asList(serviceProvidedDTO1, serviceProvidedDTO2, serviceProvidedDTO3,
                        serviceProvidedDTO4, serviceProvidedDTO5));
        ServiceProvidedDTO expectedServiceProvidedDTO1 = new ServiceProvidedDTO("PNC", 1, "2013-05-14");
        ServiceProvidedDTO expectedServiceProvidedDTO2 = new ServiceProvidedDTO("PNC", 2, "2013-05-15");
        ServiceProvidedDTO expectedServiceProvidedDTO3 = new ServiceProvidedDTO("PNC", 4, "2013-05-17");
        ServiceProvidedDTO expectedServiceProvidedDTO4 = new ServiceProvidedDTO("PNC", 7, "2013-05-20");
        List<ServiceProvidedDTO> expectedVisits = Arrays.asList(
                expectedServiceProvidedDTO1, expectedServiceProvidedDTO2, expectedServiceProvidedDTO3, expectedServiceProvidedDTO4);

        PNCClient processedPNCClient = new PNCClientPreProcessor(pncClient).preProcess();

        assertEquals(expectedVisits.size(), processedPNCClient.getExpectedVisits().size());
        assertEquals(expectedVisits, processedPNCClient.getExpectedVisits());
    }

    @Test
    public void shouldGenerateViewElementsWhenTodayIsTheDeliveryDate() throws Exception {
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "theyiNo", DateUtil.today().toString());
        PNCClient processedClient = new PNCClientPreProcessor(pncClient).preProcess();

        List<PNCCircleDatum> expectedCircleData = Arrays.asList(
                new PNCCircleDatum(1, PNCVisitType.EXPECTED, false),
                new PNCCircleDatum(3, PNCVisitType.EXPECTED, false),
                new PNCCircleDatum(7, PNCVisitType.EXPECTED, false)
        );
        List<PNCTickDatum> expectedTickData = Arrays.asList(
                new PNCTickDatum(2, PNCVisitType.EXPECTED),
                new PNCTickDatum(4, PNCVisitType.EXPECTED),
                new PNCTickDatum(5, PNCVisitType.EXPECTED),
                new PNCTickDatum(6, PNCVisitType.EXPECTED)
        );
        List<PNCVisitDaysDatum> expectedVisitDaysData = Arrays.asList(
                new PNCVisitDaysDatum(1, PNCVisitType.EXPECTED),
                new PNCVisitDaysDatum(3, PNCVisitType.EXPECTED),
                new PNCVisitDaysDatum(7, PNCVisitType.EXPECTED)
        );

        assertEquals(expectedCircleData, processedClient.pncCircleData());
        assertEquals(0, pncClient.pncStatusData().size());
        assertEquals(R.color.pnc_circle_green, pncClient.pncVisitStatusColor());
        assertEquals(expectedTickData, pncClient.pncTickData());
        assertEquals(new PNCLineDatum(1, 7, PNCVisitType.EXPECTED), pncClient.pncLineData());
        assertEquals(expectedVisitDaysData, pncClient.visitDaysData());
    }
}