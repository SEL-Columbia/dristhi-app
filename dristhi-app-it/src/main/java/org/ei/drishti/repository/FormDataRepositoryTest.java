package org.ei.drishti.repository;


import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.util.Session;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.ei.drishti.util.EasyMap.create;

public class FormDataRepositoryTest extends AndroidTestCase {
    private FormDataRepository repository;
    private EligibleCoupleRepository eligibleCoupleRepository;

    @Override
    protected void setUp() throws Exception {
        repository = new FormDataRepository();
        AlertRepository alertRepository = new AlertRepository();
        TimelineEventRepository timelineEventRepository = new TimelineEventRepository();
        ChildRepository childRepository = new ChildRepository(timelineEventRepository, alertRepository);
        MotherRepository motherRepository = new MotherRepository(childRepository, timelineEventRepository, alertRepository);
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
        Map<String, String> params = create("id", "id 1").put("formName", "form name").put("entityId", "entity id 1").map();
        String paramsJSON = new Gson().toJson(params);

        repository.saveFormSubmission(paramsJSON, "data");

        String actualFormSubmission = repository.fetchFromSubmission("id 1");
        assertEquals("data", actualFormSubmission);
    }
}
