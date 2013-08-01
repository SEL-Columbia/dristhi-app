package org.ei.drishti.convertor;

import org.ei.drishti.dto.form.FormSubmissionDTO;

import java.util.ArrayList;
import java.util.List;

import static org.ei.drishti.domain.SyncStatus.SYNCED;

public class FormSubmissionConvertor {
    public static List<org.ei.drishti.domain.form.FormSubmission> toDomain(List<FormSubmissionDTO> formSubmissionsDto) {
        List<org.ei.drishti.domain.form.FormSubmission> submissions = new ArrayList<org.ei.drishti.domain.form.FormSubmission>();
        for (FormSubmissionDTO formSubmission : formSubmissionsDto) {
            submissions.add(new org.ei.drishti.domain.form.FormSubmission(
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
