package org.ei.opensrp.gizi.gizi;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

/**
 + * Created by Iq on 15/09/16.
 + */
public class UniqueIdHandler implements FormSubmissionHandler {
    static String bindobject = "anak";
    public UniqueIdHandler() {

    }

    @Override
    public void handle(FormSubmission submission) {
        //  getUniqueId
        String entityID = submission.entityId();
        AllCommonsRepository childRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");
        CommonPersonObject uniqueIdObj = childRepository.findByCaseID(entityID);
        String unique_id = uniqueIdObj.getColumnmaps().get("uniqueId");
        if(unique_id.equals("null")){

        }
        else{
            //create new
        }
    }

}