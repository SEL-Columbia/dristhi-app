package org.ei.opensrp.sync;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.ei.opensrp.commonregistry.CommonRepository;
import org.ei.opensrp.repository.DetailsRepository;
import org.ei.opensrp.util.AssetHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClientProcessor {

    private static ClientProcessor instance;
    private CloudantDataHandler mCloudantDataHandler;
    private static final String TAG = "ClientProcessor";
    private static final String baseEntityIdJSONKey = "baseEntityId";

    Context mContext;

    public ClientProcessor(Context context) {
        mContext = context;
        try {
            mCloudantDataHandler = CloudantDataHandler.getInstance(context);
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }

    }

    public static ClientProcessor getInstance(Context context) {
        if (instance == null) {
            instance = new ClientProcessor(context);
        }
        return instance;
    }

    public void processClient() throws Exception {
        CloudantDataHandler handler = CloudantDataHandler.getInstance(mContext);
        //this seems to be easy for now cloudant json to events model is crazy
        List<JSONObject> events = handler.getUpdatedEventsDocs();
        String clientClassificationStr = getFileContents("ec_client_classification.json");
        JSONObject clientClassificationJson = new JSONObject(clientClassificationStr);
        //iterate through the events
        loopEvents(events, clientClassificationJson);

    }

    private void loopEvents(List<JSONObject> events, JSONObject clientClassificationJson) throws Exception {
        for (JSONObject event : events) {
            // JSONObject event = events.get(0);

            String baseEntityId = event.getString(baseEntityIdJSONKey);
            //for data integrity check if a client exists, if not pull one from cloudant and insert in drishti sqlite db

            //if(clientfromsqlite==null){
            JSONObject client = mCloudantDataHandler.getClientByBaseEntityId(baseEntityId);
            // }

            //  String eventJsonString = FormUtils.getInstance(mContext).getEventAsJsonString(event);
            JSONObject eventJsonObject = event;

            //get the client type classification
            JSONArray clientClasses = clientClassificationJson.getJSONArray("case_classification_rules");
            loopClientClasses(clientClasses, eventJsonObject, event, client);

        }
    }

    private void loopClientClasses(JSONArray clientClasses, JSONObject eventJsonObject, JSONObject event, JSONObject client) throws Exception {
        for (int i = 0; i < clientClasses.length(); i++) {
            JSONObject object = clientClasses.getJSONObject(i);

            JSONObject ruleObject = object.getJSONObject("rule");
            JSONArray fields = ruleObject.getJSONArray("fields");
            loopFields(fields, eventJsonObject, event, client);

        }
    }

    private void loopFields(JSONArray fields, JSONObject eventJsonObject, JSONObject event, JSONObject client) throws Exception {

        // keep checking if the event data matches the values expected by each rule, break the moment the rule fails
        for (int i = 0; i < fields.length(); i++) {
            JSONObject fieldJson = fields.getJSONObject(i);
            String dataSegment = null;
            String fieldName = fieldJson.has("field") ? fieldJson.getString("field") : null;
            String fieldValue = fieldJson.has("field_value") ? fieldJson.getString("field_value") : null;
            String responseKey = null;
            if (fieldName != null && fieldName.contains(".")) {
                String fieldNameArray[] = fieldName.split("\\.");
                dataSegment = fieldNameArray[0];
                fieldName = fieldNameArray[1];
                String concept = fieldJson.has("concept") ? fieldJson.getString("concept") : null;
                if (concept != null) {
                    fieldValue = concept;
                    responseKey = "value";
                }
            }

            JSONArray responseValue = fieldJson.has(responseKey) ? fieldJson.getJSONArray(responseKey) : null;
            JSONArray createsCase = fieldJson.has("creates_case") ? fieldJson.getJSONArray("creates_case") : null;
            JSONArray closesCase = fieldJson.has("closes_case") ? fieldJson.getJSONArray("closes_case") : null;

            List<String> responseValues = getValues(responseValue);


            //some fields are in the main doc e.g event_type so fetch them from the main doc
            if (dataSegment != null && !dataSegment.isEmpty()) {
                JSONArray jsonDataSegment = eventJsonObject.getJSONArray(dataSegment);
                //iterate in the segment e.g obs segment
                for (int j = 0; j < jsonDataSegment.length(); j++) {
                    JSONObject segmentJsonObject = jsonDataSegment.getJSONObject(j);
                    //let's discuss this further, to get the real value in the doc we've to use the keys 'fieldcode' and 'value'

                    String docSegmentFieldValue = segmentJsonObject.get(fieldName) != null ? segmentJsonObject.get(fieldName).toString() : "";
                    String docSegmentResponseValue = segmentJsonObject.get(responseKey) != null ? segmentJsonObject.get(responseKey).toString() : "";


                    if (docSegmentFieldValue.equalsIgnoreCase(fieldValue) && responseValues.contains(docSegmentResponseValue)) {
                        //this is the event obs we're interested in put it in the respective bucket specified by type variable
                        processCaseModel(event, client, createsCase);
                        closeCase(client, closesCase);
                    }

                }


            } else {
                //fetch from the main doc
                String docSegmentFieldValue = eventJsonObject.get(fieldName) != null ? eventJsonObject.get(fieldName).toString() : "";

                if (docSegmentFieldValue.equalsIgnoreCase(fieldValue)) {

                    processCaseModel(event, client, createsCase);
                    closeCase(client, closesCase);
                }

            }
        }
    }

    private void closeCase(JSONObject client, JSONArray closesCase) {
        try {
            if (closesCase == null || closesCase.length() == 0) {
                return;
            }
            String baseEntityId = client.getString(baseEntityIdJSONKey);

            for (int i = 0; i < closesCase.length(); i++) {
                String tableName = closesCase.getString(i);
                CommonRepository cr = org.ei.opensrp.Context.getInstance().commonrepository(tableName);
                cr.closeCase(baseEntityId, tableName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processCaseModel(JSONObject event, JSONObject client, JSONArray createsCase) {
        try {

            if (createsCase == null || createsCase.length() == 0) {
                return;
            }
            for (int openCase = 0; openCase < createsCase.length(); openCase++) {

                String clientType = createsCase.getString(openCase);

                JSONObject columnMappings = getColumnMappings(clientType);
                JSONArray columns = columnMappings.getJSONArray("columns");
                String baseEntityId = client.getString(baseEntityIdJSONKey);
                String expectedEncounterType = event.has("eventType") ? event.getString("eventType") : null;

                ContentValues contentValues = new ContentValues();
                //Add the base_entity_id
                contentValues.put("base_entity_id", baseEntityId);
                contentValues.put("is_closed", 0);

                for (int i = 0; i < columns.length(); i++) {
                    JSONObject colObject = columns.getJSONObject(i);
                    String docType = colObject.getString("type");
                    String columnName = colObject.getString("column_name");
                    JSONObject jsonMapping = colObject.getJSONObject("json_mapping");
                    String dataSegment = null;
                    String fieldName = jsonMapping.getString("field");
                    String fieldValue = null;
                    String responseKey = null;
                    if (fieldName != null && fieldName.contains(".")) {
                        String fieldNameArray[] = fieldName.split("\\.");
                        dataSegment = fieldNameArray[0];
                        fieldName = fieldNameArray[1];
                        fieldValue = jsonMapping.has("concept") ? jsonMapping.getString("concept") : null;
                        if (fieldValue != null) {
                            responseKey = "value";
                        }
                    }

                    String encounterType = jsonMapping.has("event_type") ? jsonMapping.getString("event_type") : null;


                    JSONObject jsonDocument = docType.equalsIgnoreCase("Event") ? event : client;

                    Object jsonDocSegment = null;

                    if (dataSegment != null) {
                        //pick data from a specific section of the doc
                        jsonDocSegment = jsonDocument.get(dataSegment);

                    } else {
                        //else the use the main doc as the doc segment
                        jsonDocSegment = jsonDocument;

                    }

                    //special handler needed to process address,
                    if (dataSegment != null && dataSegment.equalsIgnoreCase("adresses")) {
                        Map<String, String> addressMap = getClientAddressAsMap(client);
                        if (addressMap.containsKey(fieldName)) {
                            contentValues.put(columnName, addressMap.get(fieldName).toString());
                        }
                        continue;
                    }
                    //special handler for relationalid
                    if (dataSegment != null && dataSegment.equalsIgnoreCase("relationships")) {
                        JSONObject relationshipsObject = jsonDocument.getJSONObject("relationships");
                        JSONArray relationshipsArray = relationshipsObject.getJSONArray(fieldName);
                        if (relationshipsArray != null && relationshipsArray.length() > 0) {
                            List<String> relationalIds = getValues(relationshipsArray);
                            contentValues.put(columnName, relationalIds.get(0));

                        }
                        continue;
                    }

                    if (jsonDocSegment instanceof JSONArray) {

                        JSONArray jsonDocSegmentArray = (JSONArray) jsonDocSegment;

                        for (int j = 0; j < jsonDocSegmentArray.length(); j++) {
                            JSONObject jsonDocObject = jsonDocSegmentArray.getJSONObject(j);
                            String columnValue = null;
                            if (fieldValue == null) {
                                //this means field_value and response_key are null so pick the value from the json object for the field_name
                                columnValue = jsonDocObject.getString(fieldName);
                            } else {
                                //this means field_value and response_key are not null e.g when retrieving some value in the events obs section
                                String expectedFieldValue = jsonDocObject.getString(fieldName);
                                //some events can only be differentiated by the event_type value eg pnc1,pnc2, anc1,anc2
                                //check if encountertype (the one in ec_client_fields.json) is null or it matches the encounter type from the ec doc we're processing
                                boolean encounterTypeMatches = (encounterType == null) || (encounterType != null && encounterType.equalsIgnoreCase(expectedEncounterType));

                                if (encounterTypeMatches && expectedFieldValue.equalsIgnoreCase(fieldValue)) {
                                    columnValue = jsonDocObject.get(responseKey).toString();
                                }
                            }
                            // after successfully retrieving the column name and value store it in Content value
                            if (columnValue != null) {
                                columnValue = getHumanReadableConceptResponse(columnValue, jsonDocObject);
                                contentValues.put(columnName, columnValue);
                            }
                        }

                    } else {
                        //e.g client attributes section
                        String columnValue = null;
                        JSONObject jsonDocSegmentObject = (JSONObject) jsonDocSegment;
                        columnValue = jsonDocSegmentObject.has(fieldName) ? jsonDocSegmentObject.getString(fieldName) : "";
                        // after successfully retrieving the column name and value store it in Content value
                        if (columnValue != null) {
                            columnValue = getHumanReadableConceptResponse(columnValue, jsonDocSegmentObject);
                            contentValues.put(columnName, columnValue);
                        }

                    }


                }

                // save the values to db
                Long id = executeInsertStatement(contentValues, clientType);
                Long timestamp = event.getLong("eventDate");
                addContentValuesToDetailsTable(contentValues, timestamp);
                updateClientDetailsTable(event, client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Save the populated content values to details table
     *
     * @param values
     * @param eventDate
     */
    private void addContentValuesToDetailsTable(ContentValues values, Long eventDate) {
        try {
            String baseEntityId = values.getAsString("base_entity_id");
            Iterator<String> it = values.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = values.getAsString(key);
                saveClientDetails(baseEntityId, key, value, eventDate);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }
    }


    /**
     * Update the details table with the new info, All the obs are extracted and saved as key value <br/>
     * with the key being the formSubmissionField and value being the value field <br/>
     * If the key value already exists then the row will simply be updated with the value if the event date is most recent
     *
     * @param event
     * @param client
     */
    public void updateClientDetailsTable(JSONObject event, JSONObject client) {
        try {
            String baseEntityId = client.getString(baseEntityIdJSONKey);
            Long timestamp = event.getLong("eventDate");

            Map<String, String> addressInfo = getClientAddressAsMap(client);
            saveClientDetails(baseEntityId, addressInfo, timestamp);

            Map<String, String> attributes = getClientAttributes(client);
            saveClientDetails(baseEntityId, attributes, timestamp);

            Map<String, String> obs = getObsFromEvent(event);
            saveClientDetails(baseEntityId, obs, timestamp);

            //save the other misc, client info date of birth...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the obs as key value pair <br/>
     * Key being the formSubmissionField and value being entered value
     *
     * @param event
     * @return
     */
    private Map<String, String> getObsFromEvent(JSONObject event) {
        Map<String, String> obs = new HashMap<String, String>();
        try {
            String obsKey = "obs";
            if (event.has(obsKey)) {
                JSONArray obsArray = event.getJSONArray(obsKey);
                if (obsArray != null && obsArray.length() > 0) {
                    for (int i = 0; i < obsArray.length(); i++) {
                        JSONObject object = obsArray.getJSONObject(i);
                        String key = object.has("formSubmissionField") ? object.getString("formSubmissionField") : null;
                        String value = object.has("value") ? object.getString("value") :
                                object.has("values") ? object.get("values").toString() : null;
                        value = getHumanReadableConceptResponse(value, object);
                        if (key != null && value != null) {
                            obs.put(key, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obs;
    }

    private Map<String, String> getClientAttributes(JSONObject client) {
        Map<String, String> attributes = new HashMap<String, String>();
        try {
            String attributesKey = "attributes";
            if (client.has(attributesKey)) {
                JSONObject attributesJson = client.getJSONObject(attributesKey);
                if (attributesJson != null && attributesJson.length() > 0) {
                    //retrieve the other fields as well
                    Iterator<String> it = attributesJson.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        boolean shouldSkipNode = attributesJson.get(key) instanceof JSONArray || attributesJson.get(key) instanceof JSONObject;
                        if (!shouldSkipNode) {
                            String value = attributesJson.getString(key);
                            attributes.put(key, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attributes;
    }

    private void saveClientDetails(String baseEntityId, Map<String, String> values, Long timestamp) {
        Iterator<String> it = values.keySet().iterator();
        if (it != null) {
            while (it.hasNext()) {
                String key = it.next();
                String value = values.get(key);
                saveClientDetails(baseEntityId, key, value, timestamp);
            }
        }
    }

    /**
     * Save a single details row to the db
     *
     * @param baseEntityId
     * @param key
     * @param value
     * @param timestamp
     */
    private void saveClientDetails(String baseEntityId, String key, String value, Long timestamp) {
        DetailsRepository detailsRepository = org.ei.opensrp.Context.getInstance().detailsRepository();
        detailsRepository.add(baseEntityId, key, value, timestamp);
    }

    /**
     * Get human readable values from the json doc humanreadablevalues key if the key is empty return value
     *
     * @param value
     * @param jsonDocObject
     * @return
     * @throws Exception
     */
    private String getHumanReadableConceptResponse(String value, JSONObject jsonDocObject) throws Exception {

        JSONArray humanReadableValues = jsonDocObject.has("humanReadableValues") ? jsonDocObject.getJSONArray("humanReadableValues") : null;

        if (jsonDocObject == null || humanReadableValues == null || humanReadableValues.length() == 0) {
            return value;
        }

        String humanReadableValue = humanReadableValues.length() == 1 ? humanReadableValues.get(0).toString() : humanReadableValues.toString();

        return humanReadableValue;
    }

    /**
     * convert concept responses to human readable values based on the concept mappings values
     * attached to the column json mappings in ec_client_fields.json
     *
     * @param value
     * @param conceptMappings
     * @return
     * @throws Exception
     */
    private String humanizeConceptResponse(String value, JSONObject conceptMappings) throws Exception {
        if (conceptMappings == null) {
            return value;
        }
        String humanReadableValue = null;
        if (value.startsWith("[")) {
            JSONArray jsonArray = new JSONArray(value);
            if (jsonArray.length() == 1) {
                value = jsonArray.get(0).toString();
                humanReadableValue = conceptMappings.has(value) ? conceptMappings.getString(value) : null;

            } else {
                JSONArray humanReadableValues = new JSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String val = conceptMappings.has(jsonArray.get(i).toString()) ? conceptMappings.getString(jsonArray.get(i).toString()) : null;
                    humanReadableValues.put(val);
                }
                humanReadableValue = humanReadableValues.toString();
            }
        } else {
            humanReadableValue = conceptMappings.has(value) ? conceptMappings.getString(value) : null;
        }
        return humanReadableValue;
    }

    public String queryTableForColumnValue(String query, String tableName, String column) {
        String columnValue = null;
        Cursor cursor = null;
        try {
            cursor = queryTable(query, tableName);
            if (cursor != null && cursor.moveToFirst()) { // entity exists in table
                columnValue = cursor.getString(cursor.getColumnIndex(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return columnValue;
    }

    public Map<String, String> getClientAddressAsMap(JSONObject client) {
        Map<String, String> addressMap = new HashMap<String, String>();
        try {
            String addressFieldsKey = "addressFields";
            String adressesKey = "adresses";

            if (client.has(adressesKey)) {
                JSONArray addressJsonArray = client.getJSONArray(adressesKey);
                if (addressJsonArray != null && addressJsonArray.length() > 0) {
                    JSONObject addressJson = addressJsonArray.getJSONObject(0);// Need to handle multiple addresses as well
                    if (addressJson.has(addressFieldsKey)) {
                        JSONObject addressFields = addressJson.getJSONObject(addressFieldsKey);
                        Iterator<String> it = addressFields.keys();
                        while (it.hasNext()) {
                            String key = it.next();
                            String value = addressFields.getString(key);
                            addressMap.put(key, value);
                        }
                    }

                    //retrieve the other fields as well
                    Iterator<String> it = addressJson.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        boolean shouldSkipNode = addressJson.get(key) instanceof JSONArray || addressJson.get(key) instanceof JSONObject;
                        if (!shouldSkipNode) {
                            String value = addressJson.getString(key);
                            addressMap.put(key, value);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressMap;
    }

    /**
     * Insert the a new record to the database and returns its id
     **/
    private Long executeInsertStatement(ContentValues values, String tableName) {
        CommonRepository cr = org.ei.opensrp.Context.getInstance().commonrepository(tableName);
        Long id = cr.executeInsertStatement(values, tableName);
        return id;
    }

    private Cursor queryTable(String sql, String tableName) {
        CommonRepository cr = org.ei.opensrp.Context.getInstance().commonrepository(tableName);
        Cursor c = cr.queryTable(sql);
        return c;
    }

    private JSONObject getColumnMappings(String registerName) {

        try {
            String clientClassificationStr = getFileContents("ec_client_fields.json");
            JSONObject clientClassificationJson = new JSONObject(clientClassificationStr);
            JSONArray bindObjects = clientClassificationJson.getJSONArray("bindobjects");

            for (int i = 0; i < bindObjects.length(); i++) {

                JSONObject bindObject = bindObjects.getJSONObject(i);
                if (bindObject.getString("name").equalsIgnoreCase(registerName)) {
                    return bindObject;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFileContents(String fileName) {
        return AssetHandler.readFileFromAssetsFolder(fileName, mContext);
    }

    private List<String> getValues(JSONArray jsonArray) throws JSONException {
        List<String> values = new ArrayList<String>();
        if (jsonArray == null) {
            return values;
        }


        for (int i = 0; i < jsonArray.length(); i++) {
            values.add((String) jsonArray.get(i));
        }
        return values;
    }
}
