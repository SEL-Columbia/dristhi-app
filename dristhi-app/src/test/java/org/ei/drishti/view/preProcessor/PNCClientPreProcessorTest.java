package org.ei.drishti.view.preProcessor;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.ei.drishti.view.contract.pnc.*;
import org.joda.time.LocalDate;
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
        ServiceProvidedDTO expectedVisit1 = new ServiceProvidedDTO("PNC", 1, "2013-05-14");
        ServiceProvidedDTO expectedVisit2 = new ServiceProvidedDTO("PNC", 3, "2013-05-16");
        ServiceProvidedDTO expectedVisit3 = new ServiceProvidedDTO("PNC", 7, "2013-05-20");
        List<ServiceProvidedDTO> expectedVisits = Arrays.asList(expectedVisit1, expectedVisit2, expectedVisit3);

        PNCClient processedPNCClient = new PNCClientPreProcessor(pncClient).preProcess();

        assertEquals(expectedVisits, processedPNCClient.expectedVisits());
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
        assertEquals(PNCStatusColor.GREEN, pncClient.pncVisitStatusColor());
        assertEquals(expectedTickData, pncClient.pncTickData());
        assertEquals(Arrays.asList(new PNCLineDatum(1, 7, PNCVisitType.EXPECTED)), pncClient.pncLineData());
        assertEquals(expectedVisitDaysData, pncClient.visitDaysData());
    }

    @Test
    public void shouldGenerateViewElements_whenTodayIs2DaysFromDeliveryDate() throws Exception {
        DateUtil.fakeIt(new LocalDate("2013-05-15"));
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "thayiNo", "2013-05-13");

        PNCClient processedClient = new PNCClientPreProcessor(pncClient).preProcess();

        List<PNCCircleDatum> expectedCircleData = Arrays.asList(
                new PNCCircleDatum(1, PNCVisitType.EXPECTED, true),
                new PNCCircleDatum(3, PNCVisitType.EXPECTED, false),
                new PNCCircleDatum(7, PNCVisitType.EXPECTED, false)
        );
        List<PNCTickDatum> expectedTickData = Arrays.asList(
                new PNCTickDatum(2, PNCVisitType.ACTUAL),
                new PNCTickDatum(4, PNCVisitType.EXPECTED),
                new PNCTickDatum(5, PNCVisitType.EXPECTED),
                new PNCTickDatum(6, PNCVisitType.EXPECTED)
        );
        List<PNCLineDatum> expectedLineData = Arrays.asList(
                new PNCLineDatum(1, 2, PNCVisitType.ACTUAL),
                new PNCLineDatum(2, 7, PNCVisitType.EXPECTED)
        );
        List<PNCVisitDaysDatum> expectedVisitDays = Arrays.asList(
                new PNCVisitDaysDatum(3, PNCVisitType.EXPECTED),
                new PNCVisitDaysDatum(7, PNCVisitType.EXPECTED)
        );
        assertEquals(expectedCircleData, processedClient.pncCircleData());
        assertEquals(Arrays.asList(new PNCStatusDatum(1, PNCVisitStatus.MISSED)), processedClient.pncStatusData());
        assertEquals(PNCStatusColor.RED, processedClient.pncVisitStatusColor());
        assertEquals(expectedTickData, processedClient.pncTickData());
        assertEquals(expectedLineData, processedClient.pncLineData());
        assertEquals(expectedVisitDays, processedClient.visitDaysData());
    }

    @Test
    public void shouldGenerateViewElements_whenTodayIs3DaysFromDeliveryDate_AndServiceIsProvidedOnDay2() throws Exception {
        DateUtil.fakeIt(new LocalDate("2013-05-16"));
        PNCClient pncClient = new PNCClient("entityId", "village", "name", "thayiNo", "2013-05-13")
                .withServicesProvided(Arrays.asList(new ServiceProvidedDTO("PNC", "2013-05-15", null)));

        PNCClient processedClient = new PNCClientPreProcessor(pncClient).preProcess();

        List<PNCCircleDatum> expectedCircleData = Arrays.asList(
                new PNCCircleDatum(1, PNCVisitType.EXPECTED, true),
                new PNCCircleDatum(3, PNCVisitType.EXPECTED, false),
                new PNCCircleDatum(7, PNCVisitType.EXPECTED, false),
                new PNCCircleDatum(2, PNCVisitType.ACTUAL, true)
        );
        List<PNCTickDatum> expectedPNCTickData = Arrays.asList(
                new PNCTickDatum(4, PNCVisitType.EXPECTED),
                new PNCTickDatum(5, PNCVisitType.EXPECTED),
                new PNCTickDatum(6, PNCVisitType.EXPECTED)
        );
        List<PNCLineDatum> expectedLineData = Arrays.asList(
                new PNCLineDatum(1, 3, PNCVisitType.ACTUAL),
                new PNCLineDatum(3, 7, PNCVisitType.EXPECTED)
        );
        List<PNCVisitDaysDatum> expectedvisitDaysData = Arrays.asList(
                new PNCVisitDaysDatum(7, PNCVisitType.EXPECTED),
                new PNCVisitDaysDatum(2, PNCVisitType.ACTUAL)
        );
        List<PNCStatusDatum> expectedStatusData = Arrays.asList(new PNCStatusDatum(1, PNCVisitStatus.MISSED));
        assertEquals("create a circle of type actual on day 2",
                expectedCircleData, processedClient.pncCircleData());
        assertEquals("create a missed status on day 1",
                expectedStatusData, processedClient.pncStatusData());
        assertEquals("set active color to yellow",
                PNCStatusColor.YELLOW, processedClient.pncVisitStatusColor());
        assertEquals("should not create a tick on day 2 with grey ticks on the remainder",
                expectedPNCTickData, processedClient.pncTickData());
        assertEquals("create an actual line from 1 to 3 and an expected line from 3 to 7",
                expectedLineData, processedClient.pncLineData());
        assertEquals("show day nos on days 2 and 7",
                expectedvisitDaysData, processedClient.visitDaysData());
    }

    @Test
    public void test_8th_day_with_all_services_provided_on_time() throws Exception {
        DateUtil.fakeIt(new LocalDate("2013-05-21"));
        PNCClient client = new PNCClient("entityId", "village", "name", "thayiNo", "2013-05-13")
                .withServicesProvided(Arrays.asList(
                        new ServiceProvidedDTO("PNC", "2013-05-26", null),
                        new ServiceProvidedDTO("PNC", "2013-05-20", null),
                        new ServiceProvidedDTO("PNC", "2013-05-16", null),
                        new ServiceProvidedDTO("PNC", "2013-05-16", null),
                        new ServiceProvidedDTO("PNC", "2013-05-15", null),
                        new ServiceProvidedDTO("PNC", "2013-05-14", null)
                ));

        PNCClient processedClient = new PNCClientPreProcessor(client).preProcess();

        List<PNCCircleDatum> expectedCircleData = Arrays.asList(
                new PNCCircleDatum(1, PNCVisitType.ACTUAL, true),
                new PNCCircleDatum(2, PNCVisitType.ACTUAL, true),
                new PNCCircleDatum(3, PNCVisitType.ACTUAL, true),
                new PNCCircleDatum(7, PNCVisitType.ACTUAL, true)
                );
        List<PNCStatusDatum> expectedStatusData= Arrays.asList(
                new PNCStatusDatum(1, PNCVisitStatus.DONE),
                new PNCStatusDatum(3, PNCVisitStatus.DONE),
                new PNCStatusDatum(7, PNCVisitStatus.DONE)
        );
        List<PNCTickDatum> expectedTickData = Arrays.asList(
                new PNCTickDatum(4, PNCVisitType.ACTUAL),
                new PNCTickDatum(5, PNCVisitType.ACTUAL),
                new PNCTickDatum(6, PNCVisitType.ACTUAL)
        );
        List<PNCVisitDaysDatum> expectedVisitDaysData = Arrays.asList(
                new PNCVisitDaysDatum(1, PNCVisitType.ACTUAL),
                new PNCVisitDaysDatum(2, PNCVisitType.ACTUAL),
                new PNCVisitDaysDatum(3, PNCVisitType.ACTUAL),
                new PNCVisitDaysDatum(7, PNCVisitType.ACTUAL)
        );
        assertEquals("create circles of type actual on each expected day",
                expectedCircleData, processedClient.pncCircleData());
        assertEquals("should create done statuses for each expected day",
                expectedStatusData, processedClient.pncStatusData());
        assertEquals("should set active color to green",
                PNCStatusColor.GREEN, processedClient.pncVisitStatusColor());
        assertEquals("should create ticks on days 4, 5 and 6",
                expectedTickData, processedClient.pncTickData());
        assertEquals("should create an actual line from 1 to 7",
                Arrays.asList(new PNCLineDatum(1,7,PNCVisitType.ACTUAL)), processedClient.pncLineData());
        assertEquals("should show day nos 1, 2, 3 and 7 as actual",
                expectedVisitDaysData, processedClient.visitDaysData());
    }

    @Test
    public void test_8th_day_with_no_services_provided() throws Exception {
        DateUtil.fakeIt(new LocalDate("2013-05-21"));
        PNCClient client = new PNCClient("entityId", "village", "name", "thayino", "2013-05-13");

        PNCClient processedClient = new PNCClientPreProcessor(client).preProcess();

        List<PNCCircleDatum> expectedCircleData = Arrays.asList(
                new PNCCircleDatum(1, PNCVisitType.EXPECTED, true),
                new PNCCircleDatum(3, PNCVisitType.EXPECTED, true),
                new PNCCircleDatum(7, PNCVisitType.EXPECTED, true)
        );
        List<PNCStatusDatum> expectedStatusData = Arrays.asList(
                new PNCStatusDatum(1, PNCVisitStatus.MISSED),
                new PNCStatusDatum(3, PNCVisitStatus.MISSED),
                new PNCStatusDatum(7, PNCVisitStatus.MISSED)
        );
        List<PNCTickDatum> expectedTickData = Arrays.asList(
                new PNCTickDatum(2,PNCVisitType.ACTUAL),
                new PNCTickDatum(4,PNCVisitType.ACTUAL),
                new PNCTickDatum(5,PNCVisitType.ACTUAL),
                new PNCTickDatum(6,PNCVisitType.ACTUAL)
        );
        assertEquals("should create circles of type expected on each expected day",
                expectedCircleData, processedClient.pncCircleData());
        assertEquals("should create missed statuses for each expected day",
                expectedStatusData, processedClient.pncStatusData());
        assertEquals("should set active color to red",
                PNCStatusColor.RED, processedClient.pncVisitStatusColor());
        assertEquals("should create ticks on days 2,4,5,6",
                expectedTickData, processedClient.pncTickData());
    }
}