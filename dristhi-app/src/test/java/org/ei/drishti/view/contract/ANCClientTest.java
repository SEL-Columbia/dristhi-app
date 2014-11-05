package org.ei.drishti.view.contract;

import org.ei.drishti.util.DateUtil;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ANCClientTest {

    private ANCClient getClient() {
        return new ANCClient("entity id 1", "village name 1", "anc name", "1234567", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25");
    }

    @Test
    public void shouldSatisfyCriteriaIfNameStartsWithDr() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("anc");

        assertTrue(filterMatches);
    }

    @Test
    public void shouldReturnFalseIfCriteriaDoesNotSatisfyWithAnyClientName() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("xyz");

        assertFalse(filterMatches);
    }

    @Test
    public void shouldSatisfyCriteriaIfThayiStartsWith123() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("123");

        assertTrue(filterMatches);
    }

    @Test
    public void shouldReturnFalseIfCriteriaDoesNotSatisfyWithAnyClientThayiNumber() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("456");

        assertFalse(filterMatches);
    }

    @Test
    public void shouldReturnTheDaysBetweenEDDAndToday() throws Exception {
        DateUtil.fakeIt(new LocalDate("2014-02-28"));

        String daysBetween = getClient().eddInDays();

        assertEquals("3", daysBetween);
    }
}