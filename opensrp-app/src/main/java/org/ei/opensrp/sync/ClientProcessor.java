package org.ei.opensrp.sync;


import android.content.Context;
import android.util.Log;

import org.ei.opensrp.cloudant.models.Client;
import org.ei.opensrp.cloudant.models.Event;
import org.ei.opensrp.util.AssetHandler;
import org.ei.opensrp.util.FormUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ClientProcessor {

    private static ClientProcessor instance;
    private CloudantDataHandler mCloudantDataHandler;
    private static final String TAG = "ClientProcessor";

    Context mContext;

    public ClientProcessor(Context context) {
        mContext = context;
        try {
            mCloudantDataHandler = CloudantDataHandler.getInstance(context);
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }

    }

    public ClientProcessor getInstance(Context context) {
        if (instance == null) {
            instance = new ClientProcessor(context);
        }
        return instance;
    }

    public void processClient() throws Exception {
        CloudantDataHandler handler = CloudantDataHandler.getInstance(mContext);
        //this seems to be easy for now cloudant json to events model is crazy
        List<JSONObject> events = handler.getUpdatedEvents();
        String clienClassificationStr = getFileContents("ec_client_classification.json");
        JSONObject clientClassificationJson = new JSONObject(clienClassificationStr);
        JSONObject event = events.get(0);

        String baseEntityId = event.getString("base_entity_id");
        //for data integrity check if a client exists, if not pull one from cloudant and insert in drishti sqlite db

        //if(clientfromsqlite==null){
        Client client = mCloudantDataHandler.getClientByBaseEntityId(baseEntityId);
        // }

        //  String eventJsonString = FormUtils.getInstance(mContext).getEventAsJsonString(event);
        JSONObject eventJsonObject = event;

        //get the client type classification
        JSONArray clientClasses = clientClassificationJson.getJSONArray("client_type");
        for (int i = 0; i < clientClasses.length(); i++) {
            JSONObject object = clientClasses.getJSONObject(i);
            String type = object.getString("type");

            JSONObject ruleObject = object.getJSONObject("rule");
            JSONArray fields = ruleObject.getJSONArray("fields");

            // keep checking if the event data matches the values expected by each rule, break the moment the rule fails
            for (int j = 0; j < fields.length(); j++) {
                JSONObject fieldJson = fields.getJSONObject(j);
                String dataSegment = fieldJson.has("data_segment") ? fieldJson.getString("data_segment") : null;
                String fieldCode = fieldJson.getString("fieldCode");
                String requiredValue = fieldJson.getString("value");
                //some fields are in the main doc e.g event_type so fetch them from the main doc
                if (dataSegment != null && !dataSegment.isEmpty()) {
                    JSONArray jsonDataSegment = eventJsonObject.getJSONArray(dataSegment);
                    //iterate in the segment e.g obs segment
                    for (int k = 0; k < jsonDataSegment.length(); k++) {
                        JSONObject segmentJsonObject = jsonDataSegment.getJSONObject(k);
                        //let's discuss this further, to get the real value in the doc we've to use the keys 'fieldcode' and 'value'
                        String fieldCodeValue = segmentJsonObject.get("fieldCode") != null ? segmentJsonObject.get("fieldCode").toString() : "";
                        String requiredValueValue = segmentJsonObject.get("value") != null ? segmentJsonObject.get("value").toString() : "";
                        if (fieldCodeValue.equalsIgnoreCase(fieldCode) && requiredValue.equalsIgnoreCase(requiredValueValue)) {
                            //this is the event obs we're interested in put it in the respective bucket specified by type variable
                            if (type.equalsIgnoreCase("household")) {
                                //populate household bucket
                            } else if (type.equalsIgnoreCase("elco")) {
                                //populate elco bucket
                            } else if (type.equalsIgnoreCase("anc")) {
                                //populate anc bucket
                            } else if (type.equalsIgnoreCase("pnc")) {
                                //populate pnc bucket
                            } else if (type.equalsIgnoreCase("child")) {
                                //populate child bucket
                            }


                        }

                    }


                } else {
                    //fetch from the main doc
                }
            }
        }

    }


    private String getFileContents(String fileName) {
        return AssetHandler.readFileFromAssetsFolder(fileName, mContext);
    }
}
