package org.ei.opensrp.indonesia.repository;

import org.apache.commons.lang3.tuple.Pair;
import org.ei.opensrp.DristhiConfiguration;
import org.ei.opensrp.indonesia.domain.Anak;
import org.ei.opensrp.indonesia.domain.Ibu;
import org.ei.opensrp.indonesia.domain.KartuIbu;
import org.ei.opensrp.indonesia.service.DummyNameService;
import org.ei.opensrp.repository.AlertRepository;
import org.ei.opensrp.repository.TimelineEventRepository;

import java.util.List;

import static org.ei.opensrp.indonesia.repository.IbuRepository.TYPE_ANC;
import static org.ei.opensrp.indonesia.repository.IbuRepository.TYPE_PNC;

/**
 * Created by Dimas Ciputra on 3/5/15.
 */
public class AllKohort {
    private AnakRepository anakRepository;
    private IbuRepository ibuRepository;
    private final AlertRepository alertRepository;
    private final TimelineEventRepository timelineEventRepository;
    private final DristhiConfiguration configuration;

    public AllKohort(IbuRepository ibuRepository, AnakRepository anakRepository,
                     AlertRepository alertRepository, TimelineEventRepository timelineEventRepository,
                     DristhiConfiguration configuration) {
        this.alertRepository = alertRepository;
        this.timelineEventRepository = timelineEventRepository;
        this.ibuRepository = ibuRepository;
        this.anakRepository = anakRepository;
        this.configuration = configuration;
    }

    public Ibu findIbu(String caseId) {
        List<Ibu> mothers = ibuRepository.findByCaseIds(caseId);
        if (mothers.isEmpty())
            return null;
        return mothers.get(0);
    }

    public long ancCount() {
        return ibuRepository.ancCount();
    }

    public long pncCount() { return ibuRepository.pncCount(); }

    public long anakCount() { return anakRepository.count(); }

    public List<Pair<Ibu, KartuIbu>> allANCsWithKartuIbu() {
        return ibuRepository.allIbuOfATypeWithKartuIbu(TYPE_ANC);
    }

    public List<Pair<Ibu, KartuIbu>> allPNCsWithKartuIbu() {
        return ibuRepository.allIbuOfATypeWithKartuIbu(TYPE_PNC);
    }

    public Ibu findIbuByKartuIbuId(String kartuIbuId) {
        List<Ibu> ibu = ibuRepository.findAllCasesForKartuIbu(kartuIbuId);
        if (ibu.isEmpty())
            return null;
        return ibu.get(0);
    }

    public List<Anak> findAllAnakByIbuId(String entityId) {
        return anakRepository.findByIbuCaseId(entityId);
    }

    public List<Anak> findAllAnakByCaseIds(List<String> caseIds) {
        return anakRepository.findAnakByCaseIds(caseIds.toArray(new String[caseIds.size()]));
    }

    public void updateIbu(Ibu ibu) {
        ibuRepository.update(ibu);
    }
    public void updateAnak(Anak anak) { anakRepository.update(anak); }

    public void closeIbu(String entityId) {
        alertRepository.deleteAllAlertsForEntity(entityId);
        timelineEventRepository.deleteAllTimelineEventsForEntity(entityId);
        ibuRepository.close(entityId);
    }

    public void closeAnak(String entityId) {
        alertRepository.deleteAllAlertsForEntity(entityId);
        timelineEventRepository.deleteAllTimelineEventsForEntity(entityId);
        anakRepository.close(entityId);
    }

    public void closeAllIbuForKartuIbu(String ecId) {
        List<Ibu> mothers = ibuRepository.findAllCasesForKartuIbu(ecId);
        if (mothers == null || mothers.isEmpty())
            return;
        for (Ibu ibu : mothers) {
            closeIbu(ibu.getId());
        }
    }

    public List<Anak> allAnakWithIbuAndKI() {
        return anakRepository.allAnakWithIbuAndKI();
    }

    public List<Anak> findAllAnakByKIId(String kiId) {
        return anakRepository.findAllByKIId(kiId);
    }


    public void switchIbuToPNC(String entityId) {
        ibuRepository.switchToPNC(entityId);
    }

    public void switchIbuToANC(String entityId) {
        ibuRepository.switchToANC(entityId);
    }

    public Ibu findIbuWithOpenStatus(String caseId) {
        return ibuRepository.findOpenCaseByCaseID(caseId);
    }

    public Ibu findIbuWithOpenStatusByKIId(String ecId) {
        return ibuRepository.findIbuWithOpenStatusByKIId(ecId);
    }

    public Anak findAnakWithCaseID(String caseId) {
        return anakRepository.find(caseId);
    }

    public boolean isPregnant(String kiId) {
        return ibuRepository.isPregnant(kiId);
    }

    public List<String> randomDummyANCName()  {
        return DummyNameService.getMotherDummyName(configuration, true);
    }

    public List<String> randomDummyPNCName()  {
        return DummyNameService.getMotherDummyName(configuration, true);

    }

    public List<String> randomDummyAnakName()  {
        return DummyNameService.getChildDummyName(configuration, true);
    }


}
