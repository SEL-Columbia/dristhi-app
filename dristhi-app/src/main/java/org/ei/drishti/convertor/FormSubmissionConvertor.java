package org.ei.drishti.convertor;

import org.ei.drishti.dto.form.FormSubmission;

import java.util.ArrayList;
import java.util.List;

import static org.ei.drishti.domain.SyncStatus.SYNCED;

public class FormSubmissionConvertor {
    public static List<org.ei.drishti.domain.FormSubmission> toDomain(List<FormSubmission> formSubmissionsDto) {
        List<org.ei.drishti.domain.FormSubmission> submissions = new ArrayList<org.ei.drishti.domain.FormSubmission>();
        for (FormSubmission formSubmission : formSubmissionsDto) {
            submissions.add(new org.ei.drishti.domain.FormSubmission(
                    formSubmission.instanceId(),
                    formSubmission.entityId(),
                    formSubmission.formName(),
                    formSubmission.instance(),
                    formSubmission.timeStamp(), SYNCED));
        }
        return submissions;
    }
}
