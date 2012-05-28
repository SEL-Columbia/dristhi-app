package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.util.Session;

import java.util.Date;
import java.util.List;

public class RepositoryTest extends AndroidTestCase {
    public void testShouldCheckPassword() throws Exception {
        AlertRepository alertRepository = new AlertRepository();
        Session session = new Session().setPassword("password");
        Repository repository = new Repository(new RenamingDelegatingContext(getContext(), "test_"), "drishti.db" + new Date().getTime(), session, alertRepository);

        List<Alert> makeCallJustToInitializeRepository = alertRepository.allAlerts();

        assertTrue(repository.canUseThisPassword("password"));
        assertFalse(repository.canUseThisPassword("SOMETHING-ELSE"));
    }

    public void testShouldDeleteDatabaseCompletely() throws Exception {
        String dbName = "drishti.db" + new Date().getTime();
        AlertRepository alertRepository = new AlertRepository();
        Session session = new Session().setPassword("password");
        Repository repository = new Repository(new RenamingDelegatingContext(getContext(), "test_"), dbName, session, alertRepository);

        List<Alert> makeCallJustToInitializeRepository = alertRepository.allAlerts();
        assertTrue(repository.canUseThisPassword("password"));

        repository.deleteRepository();

        assertFalse(repository.canUseThisPassword("password"));

        SQLiteDatabase database = repository.getWritableDatabase();
        assertTrue(repository.canUseThisPassword("password"));
    }
}
