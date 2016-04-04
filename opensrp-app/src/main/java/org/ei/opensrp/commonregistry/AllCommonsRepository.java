package org.ei.opensrp.commonregistry;


import android.content.ContentValues;

import org.ei.opensrp.repository.AlertRepository;
import org.ei.opensrp.repository.TimelineEventRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by Raihan Ahmed on 2/12/15.
 */
public class AllCommonsRepository {
    private CommonRepository personRepository;
    private final TimelineEventRepository timelineEventRepository;
    private final AlertRepository alertRepository;

    public AllCommonsRepository(CommonRepository personRepository, AlertRepository alertRepository, TimelineEventRepository timelineEventRepository) {
        this.personRepository = personRepository;
        this.timelineEventRepository = timelineEventRepository;
        this.alertRepository = alertRepository;
    }

    public List<CommonPersonObject> all() {
        return personRepository.allcommon();
    }

    public CommonPersonObject findByCaseID(String caseId) {
        return personRepository.findByCaseID(caseId);
    }

    public long count() {
        return personRepository.count();
    }




    public List<CommonPersonObject> findByCaseIDs(List<String> caseIds) {
        return personRepository.findByCaseIDs(caseIds.toArray(new String[caseIds.size()]));
    }

    public List<CommonPersonObject> findByRelationalIDs(List<String> RelationalID) {
        return personRepository.findByRelationalIDs(RelationalID.toArray(new String[RelationalID.size()]));
    }

    public void close(String entityId) {
        alertRepository.deleteAllAlertsForEntity(entityId);
        timelineEventRepository.deleteAllTimelineEventsForEntity(entityId);
        personRepository.close(entityId);
    }

    public void mergeDetails(String entityId, Map<String, String> details) {
        personRepository.mergeDetails(entityId, details);
    }

    public void update(String tableName,ContentValues contentValues,String caseId){
        personRepository.updateColumn(tableName, contentValues, caseId);
    }

    public List<CommonPersonObject> customQuery(String sql , String[] selections , String tableName){
        return personRepository.customQuery(sql,selections,tableName);
    }

    public List<CommonPersonObject> customQueryForCompleteRow(String sql , String[] selections , String tableName){
        return personRepository.customQueryForCompleteRow(sql,selections,tableName);
    }
}
