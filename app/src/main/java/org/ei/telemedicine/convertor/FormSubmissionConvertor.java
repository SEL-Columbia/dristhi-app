package org.ei.telemedicine.convertor;

import org.ei.telemedicine.dto.form.FormSubmissionDTO;

import java.util.ArrayList;
import java.util.List;

import static org.ei.telemedicine.domain.SyncStatus.SYNCED;

public class FormSubmissionConvertor {
    public static List<org.ei.telemedicine.domain.form.FormSubmission> toDomain(List<FormSubmissionDTO> formSubmissionsDto) {
        List<org.ei.telemedicine.domain.form.FormSubmission> submissions = new ArrayList<org.ei.telemedicine.domain.form.FormSubmission>();
        for (FormSubmissionDTO formSubmission : formSubmissionsDto) {
            submissions.add(new org.ei.telemedicine.domain.form.FormSubmission(
                    formSubmission.instanceId(),
                    formSubmission.entityId(),
                    formSubmission.formName(),
                    formSubmission.instance(),
                    formSubmission.clientVersion(),
                    SYNCED,
                    formSubmission.formDataDefinitionVersion(),
                    formSubmission.serverVersion()
            ));
        }
        return submissions;
    }
}
