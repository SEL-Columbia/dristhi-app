package org.ei.telemedicine.service;

import org.ei.telemedicine.repository.FormDataRepository;

public class PendingFormSubmissionService {

    private FormDataRepository formDataRepository;

    public PendingFormSubmissionService(FormDataRepository formDataRepository) {
        this.formDataRepository = formDataRepository;
    }


    public long pendingFormSubmissionCount() {
        return formDataRepository.getPendingFormSubmissionsCount();
    }
}