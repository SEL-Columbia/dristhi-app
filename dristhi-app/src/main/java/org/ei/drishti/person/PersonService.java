package org.ei.drishti.person;

import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.util.EasyMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by user on 2/12/15.
 */
public class PersonService {
    private final AllPersons allPersons;
    private final AllTimelineEvents allTimelineEvents;

    public PersonService(AllPersons allPersons, AllTimelineEvents allTimelineEvents) {
        this.allPersons = allPersons;
        this.allTimelineEvents = allTimelineEvents;
    }

    public void register(FormSubmission submission) {
        if (isNotBlank(submission.getFieldValue(AllConstants.CommonFormFields.SUBMISSION_DATE))) {
            // TODO : add to timeline event repo
            allTimelineEvents.add(TimelineEvent.forTBRegistered(submission.entityId(), submission.getFieldValue(AllConstants.CommonFormFields.SUBMISSION_DATE)));
        }
    }

//
//    private Map<String, String> mapofsubmissions(FormSubmission submission) {
//        EasyMap<String,String> mapssub = new EasyMap<String, String>();
//
//        EasyMap.mapof(submission.getFieldValue(""));
//        return null;
//    }

}
