package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.ServiceProvided;
import org.ei.drishti.util.Session;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.ei.drishti.util.EasyMap.mapOf;

public class ServiceProvidedRepositoryTest extends AndroidTestCase {
    private ServiceProvidedRepository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new ServiceProvidedRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, repository);
    }

    public void testShouldInsertServiceProvidedIntoRepository() throws Exception {
        ServiceProvided serviceProvided = new ServiceProvided("entity id 1", "name", "2013-01-02", mapOf("key 1", "value 1"));

        repository.add(serviceProvided);

        assertEquals(asList(serviceProvided), repository.all());
    }
}
