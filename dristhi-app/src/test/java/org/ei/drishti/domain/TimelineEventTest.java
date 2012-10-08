package org.ei.drishti.domain;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TimelineEventTest {
    @Test
    public void shouldCreateTimelineEventForANCVisitWithDetails() throws Exception {
        Map<String, String> details = new HashMap<String, String>();
        details.put("bpSystolic", "120");
        details.put("bpDiastolic", "80");
        details.put("temperature", "98");
        details.put("weight", "48");
        details.put("hbLevel", "11");

        TimelineEvent timelineEvent = TimelineEvent.forANCCareProvided("CASE A", "1", "2012-01-01", details);

        assertTrue(timelineEvent.detail1().contains("BP: 120/80"));
        assertTrue(timelineEvent.detail1().contains("Temp: 98 °F"));
        assertTrue(timelineEvent.detail1().contains("Weight: 48 kg"));
        assertTrue(timelineEvent.detail1().contains("Hb Level: 11"));
    }

    @Test
    public void shouldCreateTimelineEventForANCVisitExcludingThoseDetailsWhichDoNotHaveValue() throws Exception {
        Map<String, String> details = new HashMap<String, String>();

        TimelineEvent timelineEvent = TimelineEvent.forANCCareProvided("CASE A", "1", "2012-01-01", details);

        assertFalse(timelineEvent.detail1().contains("BP:"));
        assertFalse(timelineEvent.detail1().contains("Temp:"));
        assertFalse(timelineEvent.detail1().contains("Weight:"));
        assertFalse(timelineEvent.detail1().contains("Hb Level:"));
    }
}
