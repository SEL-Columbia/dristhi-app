package org.ei.drishti.repository;

import org.ei.drishti.domain.TimelineEvent;

import java.util.List;

public class AllTimelineEvents {
    private TimelineEventRepository repository;

    public AllTimelineEvents(TimelineEventRepository repository) {
        this.repository = repository;
    }

    public List<TimelineEvent> forCase(String ecCaseId) {
        return repository.allFor(ecCaseId);
    }
}
