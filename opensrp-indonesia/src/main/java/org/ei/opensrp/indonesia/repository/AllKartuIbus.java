package org.ei.opensrp.indonesia.repository;

import org.ei.opensrp.DristhiConfiguration;
import org.ei.opensrp.indonesia.domain.KartuIbu;
import org.ei.opensrp.indonesia.service.DummyNameService;
import org.ei.opensrp.repository.AlertRepository;
import org.ei.opensrp.repository.TimelineEventRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by Dimas Ciputra on 2/16/15.
 */
public class AllKartuIbus {

    private KartuIbuRepository kartuIbuRepository;
    private final TimelineEventRepository timelineEventRepository;
    private final AlertRepository alertRepository;
    private final DristhiConfiguration configuration;

    public AllKartuIbus(KartuIbuRepository kartuIbuRepository, AlertRepository alertRepository,
                        TimelineEventRepository timelineEventRepository, DristhiConfiguration configuration) {
        this.kartuIbuRepository = kartuIbuRepository;
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;
        this.configuration = configuration;
    }

    public List<KartuIbu> all() {
        return kartuIbuRepository.allKartuIbus();
    }

    public List<KartuIbu> allWithOutOfArea() {
        return kartuIbuRepository.allKartuIbusWithOA();
    }

    public KartuIbu findByCaseID(String caseId) {
        return kartuIbuRepository.findByCaseID(caseId);
    }

    public long count() {
        return all().size();
    }

    public long kbCount() { return kartuIbuRepository.kbCount(); }

    public List<String> randomDummyName()  {
        return DummyNameService.getMotherDummyName(configuration, true);
    }

    public List<String> villages() {
        return kartuIbuRepository.villages();
    }

    public List<KartuIbu> findByCaseIDs(List<String> caseIds) {
        return kartuIbuRepository.findByCaseIDs(caseIds.toArray(new String[caseIds.size()]));
    }

    public void close(String entityId) {
        alertRepository.deleteAllAlertsForEntity(entityId);
        timelineEventRepository.deleteAllTimelineEventsForEntity(entityId);
        kartuIbuRepository.close(entityId);
    }

    public void mergeDetails(String entityId, Map<String, String> details) {
        kartuIbuRepository.mergeDetails(entityId, details);
    }
}
