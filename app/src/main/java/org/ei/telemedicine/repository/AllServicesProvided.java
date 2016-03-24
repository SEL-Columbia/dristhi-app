package org.ei.telemedicine.repository;

import org.ei.telemedicine.domain.ServiceProvided;

import java.util.List;

public class AllServicesProvided {
    private ServiceProvidedRepository repository;

    public AllServicesProvided(ServiceProvidedRepository repository) {
        this.repository = repository;
    }

    public List<ServiceProvided> findByEntityIdAndServiceNames(String entityId, String... names) {
        return repository.findByEntityIdAndServiceNames(entityId, names);
    }

    public void add(ServiceProvided serviceProvided) {
        repository.add(serviceProvided);
    }
}
