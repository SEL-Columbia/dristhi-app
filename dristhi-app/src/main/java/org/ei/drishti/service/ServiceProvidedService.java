package org.ei.drishti.service;

import org.ei.drishti.domain.ServiceProvided;
import org.ei.drishti.repository.AllServicesProvided;

import java.util.List;

public class ServiceProvidedService {
    private AllServicesProvided allServiceProvided;

    public ServiceProvidedService(AllServicesProvided allServicesProvided) {
        this.allServiceProvided = allServicesProvided;
    }

    public List<ServiceProvided> findByEntityIdAndName(String entityId, String... names) {
        return allServiceProvided.findByEntityIdAndName(entityId, names);
    }
}
