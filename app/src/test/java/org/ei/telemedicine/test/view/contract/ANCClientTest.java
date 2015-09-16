package org.ei.telemedicine.test.view.contract;

import org.ei.telemedicine.view.contract.ANCClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ANCClientTest
{


    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    private ANCClient getClient() {
        return new ANCClient("entity id 1", "village name 1", "anc name", "1234567", "Tue, 25 Feb 2014 00:00:00 GMT", "2013-05-25");
    }

    @Test
    public void testShouldSatisfyCriteriaIfNameStartsWithDr() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("anc");

        assertTrue(filterMatches);
    }

    @Test
    public void testShouldReturnFalseIfCriteriaDoesNotSatisfyWithAnyClientName() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("xyz");

        assertFalse(filterMatches);
    }

    @Test
    public void testShouldSatisfyCriteriaIfThayiStartsWith123() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("123");

        assertTrue(filterMatches);
    }

    @Test
    public void testShouldReturnFalseIfCriteriaDoesNotSatisfyWithAnyClientThayiNumber() throws Exception {
        boolean filterMatches = getClient().satisfiesFilter("456");

        assertFalse(filterMatches);
    }

}