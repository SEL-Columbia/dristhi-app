package org.ei.drishti.view.contract;

import org.robolectric.RobolectricTestRunner;
import junit.framework.TestCase;
import org.ei.drishti.util.DateUtil;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class ECClientTest extends TestCase {

    @Test
    public void shouldReturnTrueForIsHP() throws Exception {
        assertTrue(getClient().withIsHighPriority(true).isHighPriority());
    }

    @Test
    public void shouldReturnFalseForIsHP() throws Exception {
        assertFalse(getClient().isHighPriority());
    }

    @Test
    public void shouldReturnTrueForIsBPLWhenEconomicStatusIsBPL() throws Exception {
        ECClient client = getClient().withEconomicStatus("BPL");

        boolean isBPL = client.isBPL();

        assertTrue(isBPL);
    }

    @Test
    public void shouldReturnFalseForIsBPLWhenEconomicStatusIsBPL() throws Exception {
        ECClient client = getClient().withEconomicStatus("APL");
        boolean isBPL = client.isBPL();
        assertFalse(isBPL);

        client = getClient();
        isBPL = client.isBPL();
        assertFalse(isBPL);
    }

    @Test
    public void shouldReturnTrueWhenClienContainsCasteAsSC() throws Exception {
        ECClient SCClient = getClient().withCaste("SC");
        assertTrue(SCClient.isSC());
        assertFalse(SCClient.isST());
    }

    @Test
    public void shouldReturnTrueWhenClienContainsCasteAsST() throws Exception {
        ECClient STClient = getClient().withCaste("ST");
        assertFalse(STClient.isSC());
        assertTrue(STClient.isST());
    }

    @Test
    public void shouldReturn34YearsWhenDOBIs4_4_1980() {
        DateUtil.fakeIt(LocalDate.parse("2014-04-18"));

        final int age = getClient().withDateOfBirth(new LocalDate(1980, 4, 4).toString()).age();

        assertTrue(34 == age);
    }

    @Test
    public void shouldReturn0YearsWhenDOBIs4_4_2014() {
        DateUtil.fakeIt(LocalDate.parse("2014-04-18"));

        int age = getClient().withDateOfBirth(new LocalDate(2014, 4, 4).toString()).age();

        assertTrue(age == 0);
    }

    @Test
    public void ShouldReturn1YearsWhenDOBIs18_4_2013() {
        DateUtil.fakeIt(LocalDate.parse("2014-04-18"));

        final int age = getClient().withDateOfBirth(new LocalDate(2013, 4, 18).toString()).age();

        assertTrue(1 == age);
    }

    @Test
    public void ShouldReturnUpperCaseIUDPerson() {
        String iudPerson = getClient().withIUDPerson("iudperson").iudPerson();

        assertEquals(iudPerson, "IUDPERSON");
    }

    @Test
    public void ShouldReturnUpperCaseIUDPlace() {
        String iudPerson = getClient().withIUDPerson("iudplace").iudPerson();

        assertEquals(iudPerson, "IUDPLACE");
    }

    @Test
    public void shouldSatifyFilterForNameStartingWithSameCharacters() {

        boolean filterMatches = getClient().satisfiesFilter("Dri");

        assertTrue(filterMatches);
    }

    @Test
    public void shouldNotSatifyFilterForNameNotStartingWithSameCharacters() {

        boolean filterMatches = getClient().satisfiesFilter("shti");

        assertFalse(filterMatches);
    }

    @Test
    public void shouldNotSatifyFilterForBlankName() {

        boolean filterMatches = getClient().satisfiesFilter("");

        assertFalse(filterMatches);
    }

    @Test
    public void shouldSatifyFilterForECNumberStartingWithSameCharacters() {

        boolean filterMatches = getClient().satisfiesFilter("12");

        assertTrue(filterMatches);
    }

    @Test
    public void shouldNotSatifyFilterForECNumberNotStartingWithSameCharacters() {

        boolean filterMatches = getClient().satisfiesFilter("23");

        assertFalse(filterMatches);
    }

    @Test
    public void shouldNotSatifyFilterForBlankECNumber() {

        boolean filterMatches = getClient().satisfiesFilter("");

        assertFalse(filterMatches);
    }

    private ECClient getClient() {
        return new ECClient("abcd", "Drishti", "husband1", "village1", 123);
    }
}
