package org.ei.telemedicine.test.view.contract;

import org.ei.telemedicine.util.DateUtil;
import org.ei.telemedicine.view.contract.ECClient;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ECClientTest {


    @Test
    public void testShouldReturnTrueForIsBPLWhenEconomicStatusIsBPL() throws Exception {
        ECClient client = getClient().withEconomicStatus("BPL");

        assertTrue(client.isBPL());
    }

    @Test
    public void testShouldReturnFalseForIsBPLWhenEconomicStatusIsAPLOrNothing() throws Exception {
        ECClient client = getClient().withEconomicStatus("APL");
        assertFalse(client.isBPL());

        client = getClient();
        assertFalse(client.isBPL());
    }

    @Test
    public void testShouldReturnTrueWhenClientContainsCasteAsSC() throws Exception {
        ECClient SCClient = getClient().withCaste("SC");

        assertTrue(SCClient.isSC());
        assertFalse(SCClient.isST());
    }

    @Test
    public void testShouldReturnTrueWhenClientContainsCasteAsST() throws Exception {
        ECClient STClient = getClient().withCaste("ST");

        assertFalse(STClient.isSC());
        assertTrue(STClient.isST());
    }

    @Test
    public void testShouldReturn34YearsWhenDOBIs4_4_1980() {
        DateUtil.fakeIt(LocalDate.parse("2014-04-18"));

        final int age = getClient().withDateOfBirth(new LocalDate(1980, 4, 4).toString()).age();

        assertEquals(34, age);
    }

    @Test
    public void testShouldReturn0YearsWhenDOBIs4_4_2014() {
        DateUtil.fakeIt(LocalDate.parse("2014-04-18"));

        int age = getClient().withDateOfBirth(new LocalDate(2014, 4, 4).toString()).age();

        assertEquals(0, age);
    }

    @Test
    public void testShouldReturn1YearsWhenDOBIs18_4_2013() {
        DateUtil.fakeIt(LocalDate.parse("2014-04-18"));

        final int age = getClient().withDateOfBirth(new LocalDate(2013, 4, 18).toString()).age();

        assertEquals(1, age);
    }

    @Test
    public void testShouldReturnUpperCaseIUDPerson() {
        String iudPerson = getClient().withIUDPerson("iudperson").iudPerson();

        assertEquals(iudPerson, "IUDPERSON");
    }

    @Test
    public void testShouldReturnUpperCaseIUDPlace() {
        String iudPerson = getClient().withIUDPerson("iudplace").iudPerson();

        assertEquals(iudPerson, "IUDPLACE");
    }

    @Test
    public void testShouldSatisfyFilterForNameStartingWithSameCharacters() {
        boolean filterMatches = getClient().satisfiesFilter("Dr");

        assertTrue(filterMatches);
    }

    @Test
    public void testShouldSatisfyFilterForECNumberStartingWithSameCharacters() {
        boolean filterMatches = getClient().satisfiesFilter("12");

        assertTrue(filterMatches);
    }

    @Test
    public void testShouldNotSatisfyFilterForNameNotStartingWithSameCharacters() {
        boolean filterMatches = getClient().satisfiesFilter("shti");

        assertFalse(filterMatches);
    }

    @Test
    public void testShouldSatisfyFilterForBlankName() {
        boolean filterMatches = getClient().satisfiesFilter("");

        assertTrue(filterMatches);
    }

    @Test
    public void testShouldNotSatisfyFilterForECNumberNotStartingWithSameCharacters() {
        boolean filterMatches = getClient().satisfiesFilter("23");

        assertFalse(filterMatches);
    }

    @Test
    public void testShouldSatisfyFilterForBlankECNumber() {
        boolean filterMatches = getClient().satisfiesFilter("");

        assertTrue(filterMatches);
    }

    private ECClient getClient() {
        return new ECClient("abcd", "Drishti", "husband1", "village1", 123);
    }
}
