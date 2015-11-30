package org.ei.telemedicine.test.view.contract;

import org.ei.telemedicine.util.DateUtil;
import org.ei.telemedicine.view.contract.ECChildClient;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ECChildClientTest {

    private LocalDate today;

    @Before
    public void setup() {
        today = LocalDate.parse("2014-04-27");
        DateUtil.fakeIt(today);
    }

    @Test
    public void testShouldReturnZeroDaysForNullCurrentDate() {
        ECChildClient child = new ECChildClient("abcd1", "male", null);
        assertEquals("0d", child.getAgeInString());
    }

    @Test
    public void testShouldReturnZeroDaysForEmptyCurrentDate() {
        ECChildClient child = new ECChildClient("abcd1", "male", "");
        assertEquals("0d", child.getAgeInString());
    }

    @Test
    public void testShouldReturnZeroDaysForCurrentDate() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.toString());
        assertEquals("0d", child.getAgeInString());
    }

    @Test
    public void testShouldReturn5DaysForCurrentDateMinus5Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(5).toString());
        assertEquals("5d", child.getAgeInString());
    }

    @Test
    public void testShouldReturn27DaysForCurrentDateMinus27Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(27).toString());
        assertEquals("27d", child.getAgeInString());
    }

    @Test
    public void testShouldReturn4WeeksForCurrentDateMinus28Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(28).toString());
        assertEquals("4w", child.getAgeInString());
    }

    @Test
    public void testShouldReturn16WeeksForCurrentDateMinus118Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(118).toString());
        assertEquals("16w", child.getAgeInString());
    }

    @Test
    public void testShouldReturn3MonthsForCurrentDateMinus119Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(119).toString());
        assertEquals("3m", child.getAgeInString());
    }

    @Test
    public void testShouldReturn23MonthsForCurrentDateMinus719Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(719).toString());
        assertEquals("23m", child.getAgeInString());
    }

    @Test
    public void testShouldReturn1YearsForCurrentDateMinus720Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(720).toString());
        assertEquals("1y", child.getAgeInString());
    }

    @Test
    public void testShouldReturn1YearsForCurrentDateMinus1000Days() {
        ECChildClient child = new ECChildClient("abcd1", "male", today.minusDays(1000).toString());
        assertEquals("2y", child.getAgeInString());
    }
}
