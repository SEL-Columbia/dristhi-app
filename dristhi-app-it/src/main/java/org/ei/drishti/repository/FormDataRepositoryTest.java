package org.ei.drishti.repository;


import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.FormSubmission;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.util.Session;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.SyncStatus.PENDING;
import static org.ei.drishti.domain.SyncStatus.SYNCED;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.EasyMap.mapOf;

public class FormDataRepositoryTest extends AndroidTestCase {
    private FormDataRepository repository;
    private EligibleCoupleRepository eligibleCoupleRepository;
    private MotherRepository motherRepository;

    @Override
    protected void setUp() throws Exception {
        repository = new FormDataRepository();
        AlertRepository alertRepository = new AlertRepository();
        TimelineEventRepository timelineEventRepository = new TimelineEventRepository();
        ChildRepository childRepository = new ChildRepository(timelineEventRepository, alertRepository);
        motherRepository = new MotherRepository(childRepository, timelineEventRepository, alertRepository);
        eligibleCoupleRepository = new EligibleCoupleRepository(motherRepository, timelineEventRepository, alertRepository);
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session,
                repository, eligibleCoupleRepository, alertRepository, timelineEventRepository, childRepository, motherRepository);
    }

    public void testShouldRunQueryAndGetUniqueResult() throws Exception {
        Map<String, String> details = create("Hello", "There").put("Also", "This").put("someKey", "someValue").map();
        EligibleCouple eligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", details);
        eligibleCoupleRepository.add(eligibleCouple);
        String sql = MessageFormat.format("select * from eligible_couple where eligible_couple.id = ''{0}''", eligibleCouple.caseId());

        String result = repository.queryUniqueResult(sql);

        Map<String, String> fieldValues = new Gson().fromJson(result, new TypeToken<Map<String, String>>() {
        }.getType());
        assertEquals(eligibleCouple.caseId(), fieldValues.get("id"));
        assertEquals(eligibleCouple.wifeName(), fieldValues.get("wifeName"));
        assertEquals("someValue", fieldValues.get("someKey"));
    }

    public void testShouldRunQueryAndGetListAsResult() throws Exception {
        Map<String, String> details = create("Hello", "There").put("Also", "This").put("someKey", "someValue").map();
        EligibleCouple firstEligibleCouple = new EligibleCouple("CASE X", "Wife 1", "Husband 1", "EC Number 1", "Village 1", "SubCenter 1", details);
        EligibleCouple secondEligibleCouple = new EligibleCouple("CASE Y", "Wife 2", "Husband 2", "EC Number 2", "Village 1", "SubCenter 1", details);
        eligibleCoupleRepository.add(firstEligibleCouple);
        eligibleCoupleRepository.add(secondEligibleCouple);
        String sql = MessageFormat.format("select * from eligible_couple where eligible_couple.village = ''{0}''", "Village 1");

        String results = repository.queryList(sql);

        List<Map<String, String>> fieldValues = new Gson().fromJson(results, new TypeToken<List<Map<String, String>>>() {
        }.getType());
        assertEquals(firstEligibleCouple.caseId(), fieldValues.get(0).get("id"));
        assertEquals(secondEligibleCouple.caseId(), fieldValues.get(1).get("id"));
    }

    public void testShouldSaveFormSubmission() throws Exception {
        Map<String, String> params = create("instanceId", "id 1").put("entityId", "entity id 1").put("formName", "form name").map();
        String paramsJSON = new Gson().toJson(params);

        repository.saveFormSubmission(paramsJSON, "instance");

        FormSubmission actualFormSubmission = repository.fetchFromSubmission("id 1");
        assertEquals(new FormSubmission("id 1", "entity id 1", "form name", "instance", "some version", PENDING), actualFormSubmission);
        assertNotNull(actualFormSubmission);
    }

    public void testShouldSaveNewEC() throws Exception {
        Map<String, String> fields =
                create("id", "entity id 1")
                        .put("wifeName", "asha")
                        .put("husbandName", "raja")
                        .put("ecNumber", "ec 123")
                        .put("currentMethod", "ocp")
                        .put("isHighPriority", "no")
                        .map();
        String fieldsJSON = new Gson().toJson(fields);

        String entityId = repository.saveEntity("eligible_couple", fieldsJSON);

        EligibleCouple savedEC = eligibleCoupleRepository.findByCaseID(entityId);
        Map<String, String> expectedDetails = create("currentMethod", "ocp").put("isHighPriority", "no").map();
        EligibleCouple expectedEligibleCouple = new EligibleCouple(entityId, "asha", "raja", "ec 123", null, null, expectedDetails);
        assertEquals(expectedEligibleCouple, savedEC);
    }

    public void testShouldUpdateEC() throws Exception {
        Map<String, String> fields =
                create("id", "entity id 1")
                        .put("husbandName", "raja")
                        .put("ecNumber", "ec 123")
                        .put("wifeName", "asha").put("village", "")
                        .put("currentMethod", "ocp")
                        .put("isHighPriority", "no")
                        .map();
        String fieldsJSON = new Gson().toJson(fields);
        Map<String, String> oldDetails = create("currentMethod", "condom")
                .put("isHighPriority", "yes")
                .put("bloodGroup", "o-ve")
                .map();
        EligibleCouple oldEC = new EligibleCouple("entity id 1", "old wife name", "old husband name", "ec 123", "old village", "sub center", oldDetails);
        eligibleCoupleRepository.add(oldEC);

        String entityId = repository.saveEntity("eligible_couple", fieldsJSON);

        assertEquals(entityId, "entity id 1");
        EligibleCouple savedEC = eligibleCoupleRepository.findByCaseID(entityId);
        Map<String, String> expectedDetails = create("currentMethod", "ocp")
                .put("isHighPriority", "no")
                .put("bloodGroup", "o-ve")
                .map();
        EligibleCouple expectedEligibleCouple = new EligibleCouple("entity id 1", "asha", "raja", "ec 123", "", "sub center", expectedDetails);
        assertEquals(expectedEligibleCouple, savedEC);
    }

    public void testShouldUpdateMotherEntity() throws Exception {
        Map<String, String> fields =
                create("id", "entity id 1")
                        .put("thaayiCardNumber", "thaayi1")
                        .put("referenceDate", "2013-01-05")
                        .put("ecCaseId", "ec 123")
                        .put("isHighPriority", "no")
                        .map();
        String fieldsJSON = new Gson().toJson(fields);
        Mother oldMother = new Mother("entity id 1", "ec 123", "thaayi2", "2013-01-01");
        motherRepository.add(oldMother);

        String entityId = repository.saveEntity("mother", fieldsJSON);

        assertEquals(entityId, "entity id 1");
        Mother savedMother = motherRepository.findById(entityId);
        Map<String, String> expectedDetails = mapOf("isHighPriority", "no");
        Mother expectedMother = new Mother("entity id 1", "ec 123", "thaayi1", "2013-01-05").withDetails(expectedDetails);
        assertEquals(expectedMother, savedMother);
    }

    public void testShouldFetchPendingFormSubmissions() throws Exception {
        FormSubmission firstSubmission = new FormSubmission("id 1", "entity id 1", "form name", "instance 1", "some version", PENDING);
        FormSubmission secondSubmission = new FormSubmission("id 2", "entity id 2", "form name", "instance 2", "some other version", PENDING);
        FormSubmission thirdSubmission = new FormSubmission("id 3", "entity id 3", "form name", "instance 3", "some other version", SYNCED);
        repository.saveFormSubmission(firstSubmission);
        repository.saveFormSubmission(secondSubmission);
        repository.saveFormSubmission(thirdSubmission);

        List<FormSubmission> pendingFormSubmissions = repository.getPendingFormSubmissions();

        assertEquals(asList(firstSubmission, secondSubmission), pendingFormSubmissions);
    }

    public void testShouldMarkPendingFormSubmissionsAsSynced() throws Exception {
        FormSubmission firstSubmission = new FormSubmission("id 1", "entity id 1", "form name", "instance 1", "some version", PENDING);
        FormSubmission secondSubmission = new FormSubmission("id 2", "entity id 2", "form name", "instance 2", "some other version", PENDING);
        FormSubmission thirdSubmission = new FormSubmission("id 3", "entity id 3", "form name", "instance 3", "some other version", PENDING);
        repository.saveFormSubmission(firstSubmission);
        repository.saveFormSubmission(secondSubmission);
        repository.saveFormSubmission(thirdSubmission);

        repository.markFormSubmissionAsSynced(asList(firstSubmission, secondSubmission));

        assertEquals(firstSubmission.setSyncStatus(SYNCED), repository.fetchFromSubmission("id 1"));
        assertEquals(secondSubmission.setSyncStatus(SYNCED), repository.fetchFromSubmission("id 2"));
        assertEquals(thirdSubmission, repository.fetchFromSubmission("id 3"));
    }
}