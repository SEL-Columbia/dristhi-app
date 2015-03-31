package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.util.Session;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.domain.SyncStatus.PENDING;
import static org.ei.drishti.domain.SyncStatus.SYNCED;

/**
 * Created by Dimas Ciputra on 3/22/15.
 */
public class FormsVersionRepositoryTest extends AndroidTestCase {

    private FormsVersionRepository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new FormsVersionRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);
    }

    public void testShouldCheckFormExistence() throws Exception {
        Map<String, String> jsonData = create("id", "1")
                .put("formName", "ec")
                .put("version", "1")
                .put("syncStatus", "SYNCED").map();

        repository.addFormVersion(jsonData);

        assertTrue(repository.formExists("ec"));
        assertFalse(repository.formExists("anc"));
    }

    public void testShouldSaveFormVersion() throws Exception {
        Map<String, String> params = create("formName", "ec").put("syncStatus", SYNCED.value()).put("version", "1").map();
        repository.addFormVersion(params);

        Map<String, String> actualFormsVersion = repository.fetchVersionByFormName("ec");
        assertNotNull(actualFormsVersion);
        assertEquals(actualFormsVersion.get("version"), params.get("version"));
    }

    public void testShouldAutoGenerateIdKeyWhenAddNewForm() throws Exception {
        Map<String, String> params = create("formName", "ec").put("syncStatus", SYNCED.value()).put("version", "1").map();
        repository.addFormVersion(params);

        Map<String, String> actualFormsVersion = repository.fetchVersionByFormName("ec");
        assertNotNull(actualFormsVersion);
        assertEquals(actualFormsVersion.get("version"), params.get("version"));
        assertNotNull(actualFormsVersion.get("id"));
    }

    public void testShouldUpdateVersionFormIfFormExist() throws Exception {
        Map<String, String> data1 = create("formName", "ec")
                .put("version", "1")
                .put("syncStatus", SYNCED.value()).map();

        repository.addFormVersion(data1);

        Map<String, String> actualData1 = repository.fetchVersionByFormName("ec");
        assertEquals("1", actualData1.get("version"));

        if(repository.formExists("ec")) {
            repository.updateServerVersion("ec", "2");
        }

        Map<String, String> actualData2 = repository.fetchVersionByFormName("ec");
        assertEquals("2", actualData2.get("version"));
    }

    public void testShouldUpdateSyncStatus() throws Exception {
        Map<String, String> firstForm = create("id", "1")
                .put("formName", "ec")
                .put("version", "1")
                .put("syncStatus", PENDING.value()).map();

        repository.addFormVersion(firstForm);
        Map<String, String> actualData = repository.fetchVersionByFormName("ec");

        assertEquals(PENDING.value(), actualData.get("syncStatus"));

        repository.updateSyncStatus("ec", SYNCED);
        actualData = repository.fetchVersionByFormName("ec");

        assertEquals(SYNCED.value(), actualData.get("syncStatus"));
    }

    public void testShouldGetAllPendingForms() throws Exception {

        Map<String, String> firstForm = create("id", "1")
                .put("formName", "ec")
                .put("version", "1")
                .put("syncStatus", PENDING.value()).map();
        Map<String, String> secondForm = create("id", "2")
                .put("formName", "anc")
                .put("version", "1")
                .put("syncStatus", PENDING.value()).map();
        Map<String, String> thirdForm = create("id", "3")
                .put("formName", "pnc")
                .put("version", "1")
                .put("syncStatus", SYNCED.value()).map();

        repository.addFormVersion(firstForm);
        repository.addFormVersion(secondForm);
        repository.addFormVersion(thirdForm);

        List<Map<String, String>> allPendingForms = repository.getAllFormWithSyncStatus(PENDING);
        List<Map<String, String>> allSyncedForms = repository.getAllFormWithSyncStatus(SYNCED);

        assertEquals(asList(firstForm, secondForm), allPendingForms);
        assertEquals(asList(thirdForm), allSyncedForms);
    }

    public void testShouldGetVersionByFormName() throws Exception {

        Map<String, String> firstForm = create("id", "1")
                .put("formName", "ec")
                .put("version", "1")
                .put("syncStatus", PENDING.value()).map();

        repository.addFormVersion(firstForm);

        String version = repository.getVersion("ec");

        assertEquals("1", version);
    }
}
