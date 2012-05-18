package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.Context;
import org.ei.drishti.domain.Alert;

import java.util.Date;
import java.util.List;

public class RepositoryTest extends AndroidTestCase {
    public void testShouldCheckPassword() throws Exception {
        AlertRepository alertRepository = new AlertRepository();
        Repository repository = new Repository(new RenamingDelegatingContext(getContext(), "test_"), "drishti.db" + new Date().getTime(), alertRepository);
        Context.getInstance().setPassword("Hello");

        List<Alert> makeCallJustToInitializeRepository = alertRepository.allAlerts();

        assertTrue(repository.canUseThisPassword("Hello"));
        assertFalse(repository.canUseThisPassword("SOMETHING-ELSE"));
    }
}
