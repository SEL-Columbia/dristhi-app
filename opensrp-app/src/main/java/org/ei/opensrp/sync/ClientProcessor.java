package org.ei.opensrp.sync;


import android.content.Context;
import android.util.Log;

import org.ei.opensrp.cloudant.models.Client;
import org.ei.opensrp.cloudant.models.Event;
import org.ei.opensrp.util.AssetHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ClientProcessor {

    private static ClientProcessor instance;
    private CloudantDataHandler mCloudantDataHandler;
    private static final String TAG = "ClientProcessor";

    Context mContext;

    public ClientProcessor(Context context){
        mContext = context;
        try{
            mCloudantDataHandler = CloudantDataHandler.getInstance(context);
        } catch (Exception e){
            Log.e(TAG, e.toString(), e);
        }

    }

    public ClientProcessor getInstance(Context context){
        if (instance == null){
            instance = new ClientProcessor(context);
        }
        return instance;
    }

    public void processClient(Event event) throws Exception{
        String clienClassificationStr = getFileContents("ec_client_classification.json");
        JSONObject clientClassificationJson = new JSONObject(clienClassificationStr);

        String baseEntityId = event.getBaseEntityId();
        Client client = mCloudantDataHandler.getClientByBaseEntityId(baseEntityId);

        //get the client type classification
        JSONArray clientClasses = clientClassificationJson.getJSONArray("client_type");
        for (int i = 0; i < clientClasses.length(); i++){
            JSONObject object = clientClasses.getJSONObject(i);
            String type = object.getString("type");

            JSONObject ruleObject = object.getJSONObject("rule");
            JSONArray fields = ruleObject.getJSONArray("fields");

            // keep checking if the event data matches the values expected by each rule, break the moment the rule fails
            for (int j = 0; j < fields.length(); j++){
                JSONObject fieldJson = fields.getJSONObject(j);
                String dataSegment = fieldJson.has("data_segment") ? fieldJson.getString("data_segment") : null;
                String fieldCode = fieldJson.getString("fieldCode");
                String requiredValue = fieldJson.getString("value");


            }
        }

    }



    private String getFileContents(String fileName){
        return  AssetHandler.readFileFromAssetsFolder(fileName, mContext);
    }
}
